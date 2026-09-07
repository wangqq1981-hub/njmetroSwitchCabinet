package com.drsi.njmsc.scheduled;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.drsi.njmsc.constant.ThresholdStants;
import com.drsi.njmsc.dto.dto.DeviceRawIecTelemtryDto;
import com.drsi.njmsc.dto.model.CircuitBreakerModel;
import com.drsi.njmsc.dto.model.DeviceHcsInfoModel;
import com.drsi.njmsc.dto.model.EnergyStorageMotorModel;
import com.drsi.njmsc.dto.model.RawIecRemoteSignalingModel;
import com.drsi.njmsc.dto.model.RawIecTelemetryModel;
import com.drsi.njmsc.dto.model.ThreeStationsModel;
import com.drsi.njmsc.handle.NjmscIec104InterrogationHandle;
import com.drsi.njmsc.handle.NjmscIec104MutationHandle;
import com.drsi.njmsc.mapper.CircuitBreakerMapper;
import com.drsi.njmsc.mapper.DeviceHcsInfoMapper;
import com.drsi.njmsc.mapper.EnergyStorageMotorMapper;
import com.drsi.njmsc.mapper.RawIecRemoteSignalingMapper;
import com.drsi.njmsc.mapper.RawIecTelemetryMapper;
import com.drsi.njmsc.mapper.ThreeStationsMapper;
import com.dsri.iec104.ies.SunStation;
import com.dsri.iec104.ies.SunTerminal;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Resource;
import org.openmuc.j60870.CauseOfTransmission;
import org.openmuc.j60870.ClientConnectionBuilder;
import org.openmuc.j60870.Connection;
import org.openmuc.j60870.ie.IeQualifierOfInterrogation;
import org.openmuc.j60870.ie.IeQuality;
import org.openmuc.j60870.ie.IeShortFloat;
import org.openmuc.j60870.ie.IeSinglePointWithQuality;
import org.openmuc.j60870.ie.InformationElement;
import org.openmuc.j60870.ie.InformationObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("'pro'.equalsIgnoreCase('${spring.profiles.active}')")
public class Iec104Scheduled extends AbstractScheduled {
   private static final Logger log = LoggerFactory.getLogger(Iec104Scheduled.class);
   @Value("${iec104.client.serverHost:localhost}")
   private String serverHost;
   @Value("${iec104.client.serverPort:2404}")
   private int serverPort;
   @Resource
   private SunTerminal sunTerminal;
   @Resource
   private NjmscIec104InterrogationHandle njmscIec104InterrogationHandle;
   @Resource
   private DeviceHcsInfoMapper deviceHcsInfoMapper;
   @Resource
   private NjmscIec104MutationHandle njmscIec104MutationHandle;
   @Resource
   private CircuitBreakerMapper circuitBreakerMapper;
   @Resource
   private EnergyStorageMotorMapper energyStorageMotorMapper;
   @Resource
   private ThreeStationsMapper threeStationsMapper;
   @Resource
   private RawIecRemoteSignalingMapper remoteSignalingMapper;
   @Resource
   private RawIecTelemetryMapper rawIecTelemetryMapper;
   public static Map<String, DeviceRawIecTelemtryDto> rawIecTelemtryDtos;
   @Resource
   private RedisTemplate<String, Object> redisTemplate;
   public static final ExecutorService WORK_POOL = new ThreadPoolExecutor(
      Runtime.getRuntime().availableProcessors() * 2,
      Runtime.getRuntime().availableProcessors() * 2,
      0L,
      TimeUnit.SECONDS,
      new LinkedBlockingQueue<>(),
      new Iec104Scheduled.BaseThreadFactory()
   );
   private Connection connection;
   private Iec104ClientConnectionEventListener iec104ClientConnectionEventListener;
   private Map<String, CountDownLatch> endOfInterrogationCD;
   private Map<String, SunStation> endOfInterrogationSS;
   private AtomicBoolean connectionClosedFlag;
   public static final String cDir = "COMTRADE";
   public static final Integer commonAddress = 93;
   private static final Integer connectionTimeout = 30000;
   public static final boolean isOneAsdu = true;
   public static final Integer diNum = 38;
   public static final Integer aiNum = 58;

   @Override
   public void init() {
      log.info("init start  系统接多个hcs-710  Map key deviceCode value is Connection ");
      rawIecTelemtryDtos = new ConcurrentHashMap<>();
      LambdaQueryWrapper<DeviceHcsInfoModel> wrapper = Wrappers.lambdaQuery(DeviceHcsInfoModel.class);

      for (DeviceHcsInfoModel deviceHcsInfoModel : this.deviceHcsInfoMapper.selectList(wrapper)) {
         DeviceRawIecTelemtryDto deviceRawIecTelemtryDto = new DeviceRawIecTelemtryDto();
         deviceRawIecTelemtryDto.setDeviceCode(deviceHcsInfoModel.getDeviceCode());
         deviceRawIecTelemtryDto.setIp(deviceHcsInfoModel.getHcsIp());
         deviceRawIecTelemtryDto.setPort(deviceHcsInfoModel.getHcsPort());
         deviceRawIecTelemtryDto.setCommonAddressOfUpwardDelivery(deviceHcsInfoModel.getCommonAddressOfUpwardDelivery());
         deviceRawIecTelemtryDto.setNjmscIec104MutationHandle(this.njmscIec104MutationHandle);
         deviceRawIecTelemtryDto.setOpeningCoilCurrentStart(0);
         deviceRawIecTelemtryDto.setStartOfClosingCoilCurrent(0);
         deviceRawIecTelemtryDto.setEnergyStorageMotorCurrentStarting(0);
         deviceRawIecTelemtryDto.setThreeStationOneCurrentStart(0);
         deviceRawIecTelemtryDto.setThreeStationTwoCurrentStart(0);
         deviceRawIecTelemtryDto.setWaveRecordingStart(0);
         deviceRawIecTelemtryDto.setWaveRecordingFinishFlag(false);
         RawIecTelemetryModel rawIecTelemetryModel = new RawIecTelemetryModel();
         rawIecTelemetryModel.setDeviceCode(deviceHcsInfoModel.getDeviceCode());
         deviceRawIecTelemtryDto.setRawIecTelemetryModel(rawIecTelemetryModel);
         RawIecRemoteSignalingModel rawIecRemoteSignalingModel = new RawIecRemoteSignalingModel();
         rawIecRemoteSignalingModel.setDeviceCode(deviceHcsInfoModel.getDeviceCode());
         deviceRawIecTelemtryDto.setRawIecRemoteSignalingModel(rawIecRemoteSignalingModel);

         try {
            this.initConnection(deviceRawIecTelemtryDto);
         } catch (IOException e) {
            log.error(
               "deviceCode:{} ip:{} port:{}",
               new Object[]{deviceHcsInfoModel.getDeviceCode(), deviceHcsInfoModel.getHcsIp(), deviceHcsInfoModel.getHcsPort(), e}
            );
         }

         rawIecTelemtryDtos.put(deviceHcsInfoModel.getDeviceCode(), deviceRawIecTelemtryDto);
         log.info("init  end  rawIecTelemtryDtos:{} , connection size :{}", JSON.toJSONString(rawIecTelemtryDtos), rawIecTelemtryDtos.size());
      }
   }

   public void initConnection(DeviceRawIecTelemtryDto deviceRawIecTelemtryDto) throws IOException {
      ClientConnectionBuilder clientConnectionBuilder = new ClientConnectionBuilder(InetAddress.getByName(deviceRawIecTelemtryDto.getIp()));
      clientConnectionBuilder.setConnectionTimeout(connectionTimeout);
      clientConnectionBuilder.setPort(deviceRawIecTelemtryDto.getPort());
      Connection connection = clientConnectionBuilder.build();
      Map<String, CountDownLatch> endOfInterrogationCD = new HashMap<>();
      Map<String, SunStation> endOfInterrogationSS = new HashMap<>();
      AtomicBoolean connectionClosedFlag = new AtomicBoolean(false);
      Iec104ClientConnectionEventListener iec104ClientConnectionEventListener = new Iec104ClientConnectionEventListener(
         deviceRawIecTelemtryDto, endOfInterrogationCD, endOfInterrogationSS, connectionClosedFlag
      );
      connection.startDataTransfer(iec104ClientConnectionEventListener);
      deviceRawIecTelemtryDto.setConnection(connection);
      deviceRawIecTelemtryDto.setEndOfInterrogationCD(endOfInterrogationCD);
      deviceRawIecTelemtryDto.setEndOfInterrogationSS(endOfInterrogationSS);
      deviceRawIecTelemtryDto.setConnectionClosedFlag(connectionClosedFlag);
      deviceRawIecTelemtryDto.setIec104ClientConnectionEventListener(iec104ClientConnectionEventListener);
   }

   @Scheduled(cron = "0 0/1 * * * ?")
   public void collectIec104Data() {
      log.info("start General call.........................");

      for (Entry<String, DeviceRawIecTelemtryDto> stringDeviceRawIecTelemtryDtoEntry : rawIecTelemtryDtos.entrySet()) {
         WORK_POOL.execute(
            () -> {
               try {
                  if (Objects.isNull(stringDeviceRawIecTelemtryDtoEntry.getValue().getConnectionClosedFlag())
                     || stringDeviceRawIecTelemtryDtoEntry.getValue().getConnectionClosedFlag().get()) {
                     this.initConnection(stringDeviceRawIecTelemtryDtoEntry.getValue());
                  }

                  long start = System.currentTimeMillis();
                  this.setup();
                  IeQualifierOfInterrogation ieQualifierOfInterrogation = new IeQualifierOfInterrogation(20);
                  CountDownLatch endOfInter = new CountDownLatch(1);
                  stringDeviceRawIecTelemtryDtoEntry.getValue().getEndOfInterrogationCD().put(String.valueOf(commonAddress), endOfInter);
                  stringDeviceRawIecTelemtryDtoEntry.getValue()
                     .getConnection()
                     .interrogation(commonAddress, CauseOfTransmission.ACTIVATION, ieQualifierOfInterrogation);
                  log.info("iec104 召唤命令");
                  endOfInter.await(10L, TimeUnit.SECONDS);
                  SunStation sunStation = stringDeviceRawIecTelemtryDtoEntry.getValue().getEndOfInterrogationSS().get(String.valueOf(commonAddress));
                  log.info(
                     "HCS-710装置 deviceCode:{} ip:{} port:{} 数据总召唤 共计用时：{}ms",
                     new Object[]{
                        stringDeviceRawIecTelemtryDtoEntry.getKey(),
                        stringDeviceRawIecTelemtryDtoEntry.getValue().getIp(),
                        stringDeviceRawIecTelemtryDtoEntry.getValue().getPort(),
                        System.currentTimeMillis() - start
                     }
                  );
                  this.njmscIec104InterrogationHandle.interrogationHandle(stringDeviceRawIecTelemtryDtoEntry.getValue());
                  log.info("HCS-710装置 数据处理 完成...");
               } catch (IOException | InterruptedException e) {
                  log.info(
                     "HCS-710装置 deviceCode:{} ip:{} port:{} 数据总召唤 err",
                     new Object[]{
                        stringDeviceRawIecTelemtryDtoEntry.getKey(),
                        stringDeviceRawIecTelemtryDtoEntry.getValue().getIp(),
                        stringDeviceRawIecTelemtryDtoEntry.getValue().getPort(),
                        e
                     }
                  );
               } finally {
                  this.cleanup();
               }
            }
         );
      }

      this.njmscIec104InterrogationHandle.allInterrogationHandle(rawIecTelemtryDtos);
   }

   @Scheduled(cron = "0/10 * * * * ?")
   public void inspectComtradeFlag() {
      log.info("start inspectComtradeFlag.........................");

      for (Entry<String, DeviceRawIecTelemtryDto> entry : rawIecTelemtryDtos.entrySet()) {
         WORK_POOL.execute(
            () -> {
               if (Objects.nonNull(entry.getValue().getWaveRecordingFinishFlag())
                  && entry.getValue().getWaveRecordingFinishFlag()
                  && !entry.getValue().getWaveRecordingHandleFlag()) {
                  try {
                     entry.getValue().setWaveRecordingHandleFlag(true);
                     TimeUnit.SECONDS.sleep(2L);
                     NjmscIec104MutationHandle njmscIec104MutationHandle = entry.getValue().getNjmscIec104MutationHandle();
                     njmscIec104MutationHandle.handleComtradeStartSignal(entry.getValue(), commonAddress);
                     log.info(
                        "deviceCode:{} ip:{} commonAddress:{} 最新录波处理完毕",
                        new Object[]{entry.getValue().getDeviceCode(), entry.getValue().getIp(), commonAddress}
                     );
                  } catch (Exception e) {
                     log.info(
                        "deviceCode:{} ip:{} port:{} inspectComtradeFlag fail",
                        new Object[]{entry.getValue().getDeviceCode(), entry.getValue().getIp(), entry.getValue().getPort(), e}
                     );
                  } finally {
                     entry.getValue().setWaveRecordingFinishFlag(false);
                     entry.getValue().setWaveRecordingStart(0);
                     entry.getValue().setWaveRecordingHandleFlag(false);
                     entry.getValue().getIec104ClientConnectionEventListener().setFileDirsReadFlag(false);
                     entry.getValue().getIec104ClientConnectionEventListener().getFileDirs().clear();
                     log.info(
                        "deviceCode:{} ip:{} port:{}  inspectComtradeFlag reset the flag bit",
                        new Object[]{entry.getValue().getDeviceCode(), entry.getValue().getIp(), commonAddress}
                     );
                  }
               }
            }
         );
      }
   }

   @Scheduled(cron = "10 0/1 * * * ?")
   public void refreshData() {
      log.info("refreshData Update the cache information uploaded ......更新上送的缓存信息...................");
      this.updateOneAsduSunTerminal();
   }

   private void updateOneAsduSunTerminal() {
      SunStation sunStationByCommonAddress = this.sunTerminal.getSunStationByCommonAddress(commonAddress);
      InformationElement[][] diInformationElements = new InformationElement[diNum * rawIecTelemtryDtos.keySet().size()][1];
      InformationElement[][] aiInformationElements = new InformationElement[aiNum * rawIecTelemtryDtos.keySet().size()][2];

      for (Entry<String, DeviceRawIecTelemtryDto> entry : rawIecTelemtryDtos.entrySet()) {
         int diSkipInformationElementAddress = (entry.getValue().getCommonAddressOfUpwardDelivery() - commonAddress) * diNum;
         int aiSkipInformationElementAddress = (entry.getValue().getCommonAddressOfUpwardDelivery() - commonAddress) * aiNum;
         LambdaQueryWrapper<RawIecRemoteSignalingModel> rawIecRemoteSignalingModelLambdaQueryWrapper = Wrappers.lambdaQuery(RawIecRemoteSignalingModel.class);
         rawIecRemoteSignalingModelLambdaQueryWrapper.eq(RawIecRemoteSignalingModel::getDeviceCode, entry.getValue().getDeviceCode());
         RawIecRemoteSignalingModel rawIecRemoteSignalingModel = Objects.nonNull(entry.getValue().getRawIecTelemetryModel())
               && Objects.nonNull(entry.getValue().getRawIecTelemetryModel().getOpeningCoilCurrent())
            ? entry.getValue().getRawIecRemoteSignalingModel()
            : (RawIecRemoteSignalingModel)this.remoteSignalingMapper.selectOne(rawIecRemoteSignalingModelLambdaQueryWrapper);
         LambdaQueryWrapper<RawIecTelemetryModel> rawIecTelemetryModelLambdaQueryWrapper = Wrappers.lambdaQuery(RawIecTelemetryModel.class);
         rawIecTelemetryModelLambdaQueryWrapper.eq(RawIecTelemetryModel::getDeviceCode, entry.getValue().getDeviceCode());
         RawIecTelemetryModel rawIecTelemetryModel = Objects.nonNull(entry.getValue().getRawIecTelemetryModel())
               && Objects.nonNull(entry.getValue().getRawIecTelemetryModel().getOpeningCoilCurrent())
            ? entry.getValue().getRawIecTelemetryModel()
            : (RawIecTelemetryModel)this.rawIecTelemetryMapper.selectOne(rawIecTelemetryModelLambdaQueryWrapper);
         LambdaQueryWrapper<EnergyStorageMotorModel> energyStorageMotorModelLambdaQueryWrapper = Wrappers.lambdaQuery(EnergyStorageMotorModel.class);
         ((LambdaQueryWrapper)((LambdaQueryWrapper)energyStorageMotorModelLambdaQueryWrapper.eq(
                  EnergyStorageMotorModel::getDeviceCode, entry.getValue().getDeviceCode()
               ))
               .orderByDesc(EnergyStorageMotorModel::getCreateDate))
            .last("limit 1");
         EnergyStorageMotorModel energyStorageMotorModel = (EnergyStorageMotorModel)this.energyStorageMotorMapper
            .selectOne(energyStorageMotorModelLambdaQueryWrapper);
         LambdaQueryWrapper<ThreeStationsModel> threeStationsModelLambdaQueryWrapper = Wrappers.lambdaQuery(ThreeStationsModel.class);
         ((LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)threeStationsModelLambdaQueryWrapper.eq(
                     ThreeStationsModel::getDeviceCode, entry.getValue().getDeviceCode()
                  ))
                  .eq(ThreeStationsModel::getDataType, 3))
               .orderByDesc(ThreeStationsModel::getCreateDate))
            .last("limit 1");
         ThreeStationsModel threeStationsModel = (ThreeStationsModel)this.threeStationsMapper.selectOne(threeStationsModelLambdaQueryWrapper);
         LambdaQueryWrapper<CircuitBreakerModel> openCircuitBreakerModelLambdaQueryWrapper = Wrappers.lambdaQuery(CircuitBreakerModel.class);
         ((LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)openCircuitBreakerModelLambdaQueryWrapper.eq(
                     CircuitBreakerModel::getDeviceCode, entry.getValue().getDeviceCode()
                  ))
                  .eq(CircuitBreakerModel::getDataType, 1))
               .orderByDesc(CircuitBreakerModel::getCreateDate))
            .last("limit 1");
         CircuitBreakerModel openCircuitBreakerModel = (CircuitBreakerModel)this.circuitBreakerMapper.selectOne(openCircuitBreakerModelLambdaQueryWrapper);
         LambdaQueryWrapper<CircuitBreakerModel> closeCircuitBreakerModelLambdaQueryWrapper = Wrappers.lambdaQuery(CircuitBreakerModel.class);
         ((LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)closeCircuitBreakerModelLambdaQueryWrapper.eq(
                     CircuitBreakerModel::getDeviceCode, entry.getValue().getDeviceCode()
                  ))
                  .eq(CircuitBreakerModel::getDataType, 0))
               .orderByDesc(CircuitBreakerModel::getCreateDate))
            .last("limit 1");
         CircuitBreakerModel closeCircuitBreakerModel = (CircuitBreakerModel)this.circuitBreakerMapper.selectOne(closeCircuitBreakerModelLambdaQueryWrapper);
         this.rawIecRemoteSignalingModelData1_24(diSkipInformationElementAddress, diInformationElements, entry.getValue());
         this.rawIecRemoteSignalingModelData25_38(diSkipInformationElementAddress, rawIecRemoteSignalingModel, diInformationElements);
         this.circuitBreakerOpening(aiSkipInformationElementAddress, openCircuitBreakerModel, rawIecTelemetryModel, aiInformationElements);
         this.circuitBreakerClosing(aiSkipInformationElementAddress, closeCircuitBreakerModel, rawIecTelemetryModel, aiInformationElements);
         this.energyStorageMotor(aiSkipInformationElementAddress, energyStorageMotorModel, rawIecTelemetryModel, aiInformationElements);
         this.threeStations(aiSkipInformationElementAddress, threeStationsModel, rawIecTelemetryModel, aiInformationElements);
         LambdaQueryWrapper<CircuitBreakerModel> circuitBreakerModelLambdaQueryWrapper = Wrappers.lambdaQuery(CircuitBreakerModel.class);
         circuitBreakerModelLambdaQueryWrapper.eq(CircuitBreakerModel::getDeviceCode, entry.getValue().getDeviceCode());
         Long circuitBreakerOperateNum = this.circuitBreakerMapper.selectCount(circuitBreakerModelLambdaQueryWrapper);

         try {
            aiInformationElements[14 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               circuitBreakerOperateNum == 0L ? 0.0F : (float)circuitBreakerOperateNum.longValue()
            );
         } catch (Exception e) {
            aiInformationElements[14 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[14 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );
         LambdaQueryWrapper<ThreeStationsModel> lsolatedPositionOperateNumWrapper = Wrappers.lambdaQuery(ThreeStationsModel.class);
         ((LambdaQueryWrapper)lsolatedPositionOperateNumWrapper.eq(ThreeStationsModel::getDeviceCode, entry.getValue().getDeviceCode()))
            .and(
               w -> {
                  LambdaQueryWrapper var10000 = (LambdaQueryWrapper)((LambdaQueryWrapper)w.or(
                        we -> {
                           LambdaQueryWrapper var10000x = (LambdaQueryWrapper)((LambdaQueryWrapper)we.eq(ThreeStationsModel::getPosition, 0))
                              .eq(ThreeStationsModel::getOriginalPosition, 1);
                        }
                     ))
                     .or(
                        we -> {
                           LambdaQueryWrapper var10000x = (LambdaQueryWrapper)((LambdaQueryWrapper)we.eq(ThreeStationsModel::getPosition, 1))
                              .eq(ThreeStationsModel::getOriginalPosition, 0);
                        }
                     );
               }
            );
         Long lsolatedPositionOperateNum = this.threeStationsMapper.selectCount(lsolatedPositionOperateNumWrapper);

         try {
            aiInformationElements[15 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               lsolatedPositionOperateNum == 0L ? 0.0F : (float)lsolatedPositionOperateNum.longValue()
            );
         } catch (Exception e) {
            aiInformationElements[15 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[15 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );
         LambdaQueryWrapper<ThreeStationsModel> groundingPositionOperateNumWrapper = Wrappers.lambdaQuery(ThreeStationsModel.class);
         ((LambdaQueryWrapper)groundingPositionOperateNumWrapper.eq(ThreeStationsModel::getDeviceCode, entry.getValue().getDeviceCode()))
            .and(
               w -> {
                  LambdaQueryWrapper var10000 = (LambdaQueryWrapper)((LambdaQueryWrapper)w.or(
                        we -> {
                           LambdaQueryWrapper var10000x = (LambdaQueryWrapper)((LambdaQueryWrapper)we.eq(ThreeStationsModel::getPosition, 1))
                              .eq(ThreeStationsModel::getOriginalPosition, 2);
                        }
                     ))
                     .or(
                        we -> {
                           LambdaQueryWrapper var10000x = (LambdaQueryWrapper)((LambdaQueryWrapper)we.eq(ThreeStationsModel::getPosition, 2))
                              .eq(ThreeStationsModel::getOriginalPosition, 1);
                        }
                     );
               }
            );
         Long groundingPositionOperateNum = this.threeStationsMapper.selectCount(groundingPositionOperateNumWrapper);

         try {
            aiInformationElements[16 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               groundingPositionOperateNum == 0L ? 0.0F : (float)groundingPositionOperateNum.longValue()
            );
         } catch (Exception e) {
            aiInformationElements[16 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[16 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[41 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getTempOfCbr() == 0.0F ? 0.0F : rawIecTelemetryModel.getTempOfCbr()
            );
         } catch (Exception e) {
            aiInformationElements[41 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[41 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[42 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getPressureOfCbr() == 0.0F ? 0.0F : rawIecTelemetryModel.getPressureOfCbr()
            );
         } catch (Exception e) {
            aiInformationElements[42 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[42 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[43 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getDensityOfCbr() == 0.0F ? 0.0F : rawIecTelemetryModel.getDensityOfCbr()
            );
         } catch (Exception e) {
            aiInformationElements[43 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[43 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[44 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getTempOfBr() == 0.0F ? 0.0F : rawIecTelemetryModel.getTempOfBr()
            );
         } catch (Exception e) {
            aiInformationElements[44 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[44 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[45 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getPressureOfBr() == 0.0F ? 0.0F : rawIecTelemetryModel.getPressureOfBr()
            );
         } catch (Exception e) {
            aiInformationElements[45 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[45 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[46 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getDensityOfBr() == 0.0F ? 0.0F : rawIecTelemetryModel.getDensityOfBr()
            );
         } catch (Exception e) {
            aiInformationElements[46 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[46 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[47 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getTempOfMui() == 0.0F ? 0.0F : rawIecTelemetryModel.getTempOfMui()
            );
         } catch (Exception e) {
            aiInformationElements[47 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[47 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[48 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getPressureOfMui() == 0.0F ? 0.0F : rawIecTelemetryModel.getPressureOfMui()
            );
         } catch (Exception e) {
            aiInformationElements[48 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[48 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[49 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getDensityOfMui() == 0.0F ? 0.0F : rawIecTelemetryModel.getDensityOfMui()
            );
         } catch (Exception e) {
            aiInformationElements[49 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[49 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[50 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getTempOfEnv() == 0.0F ? 0.0F : rawIecTelemetryModel.getTempOfEnv()
            );
         } catch (Exception e) {
            aiInformationElements[50 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getTempOfEnv() == 0.0F ? 0.0F : rawIecTelemetryModel.getTempOfEnv()
            );
            log.debug("err", e);
         }

         aiInformationElements[50 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[51 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getHumOfEnv() == 0.0F ? 0.0F : rawIecTelemetryModel.getHumOfEnv()
            );
         } catch (Exception e) {
            aiInformationElements[51 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[51 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[52 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getDischargeCurrentMeterA() == 0.0F ? 0.0F : rawIecTelemetryModel.getDischargeCurrentMeterA()
            );
         } catch (Exception e) {
            aiInformationElements[52 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[52 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[53 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getDischargeCurrentMeterB() == 0.0F ? 0.0F : rawIecTelemetryModel.getDischargeCurrentMeterB()
            );
         } catch (Exception e) {
            aiInformationElements[53 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[53 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[54 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getDischargeCurrentMeterC() == 0.0F ? 0.0F : rawIecTelemetryModel.getDischargeCurrentMeterC()
            );
         } catch (Exception e) {
            aiInformationElements[54 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[54 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[55 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getLeakageCurrentMeterA() == 0.0F ? 0.0F : rawIecTelemetryModel.getLeakageCurrentMeterA()
            );
         } catch (Exception e) {
            aiInformationElements[55 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[55 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[56 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getLeakageCurrentMeterB() == 0.0F ? 0.0F : rawIecTelemetryModel.getLeakageCurrentMeterB()
            );
         } catch (Exception e) {
            aiInformationElements[56 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[56 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );

         try {
            aiInformationElements[57 + aiSkipInformationElementAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getLeakageCurrentMeterC() == 0.0F ? 0.0F : rawIecTelemetryModel.getLeakageCurrentMeterC()
            );
         } catch (Exception e) {
            aiInformationElements[57 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[57 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );
         aiInformationElements[8 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[8 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );
         aiInformationElements[9 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[9 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );
         aiInformationElements[10 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[10 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );
         aiInformationElements[11 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[11 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );
         aiInformationElements[12 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[12 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );
         aiInformationElements[13 + aiSkipInformationElementAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[13 + aiSkipInformationElementAddress][1] = new IeQuality(
            Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
         );
         log.info("refreshData success for deviceCode : {}  ", entry.getValue().getDeviceCode());
      }

      InformationObject diObject = new InformationObject(1, diInformationElements);
      InformationObject aiObject = new InformationObject(16385, aiInformationElements);
      sunStationByCommonAddress.diObject = diObject;
      sunStationByCommonAddress.aiObject = aiObject;
   }

   private void updateMoreAsduSunTerminal() {
      for (Entry<String, DeviceRawIecTelemtryDto> entry : rawIecTelemtryDtos.entrySet()) {
         int skipInformationElementAddress = 0;
         SunStation sunStationByCommonAddress = this.sunTerminal.getSunStationByCommonAddress(entry.getValue().getCommonAddressOfUpwardDelivery());
         InformationElement[][] diInformationElements = new InformationElement[diNum][1];
         InformationElement[][] aiInformationElements = new InformationElement[aiNum][2];
         LambdaQueryWrapper<RawIecRemoteSignalingModel> rawIecRemoteSignalingModelLambdaQueryWrapper = Wrappers.lambdaQuery(RawIecRemoteSignalingModel.class);
         rawIecRemoteSignalingModelLambdaQueryWrapper.eq(RawIecRemoteSignalingModel::getDeviceCode, entry.getValue().getDeviceCode());
         RawIecRemoteSignalingModel rawIecRemoteSignalingModel = Objects.nonNull(entry.getValue().getRawIecTelemetryModel())
               && Objects.nonNull(entry.getValue().getRawIecTelemetryModel().getOpeningCoilCurrent())
            ? entry.getValue().getRawIecRemoteSignalingModel()
            : (RawIecRemoteSignalingModel)this.remoteSignalingMapper.selectOne(rawIecRemoteSignalingModelLambdaQueryWrapper);
         LambdaQueryWrapper<RawIecTelemetryModel> rawIecTelemetryModelLambdaQueryWrapper = Wrappers.lambdaQuery(RawIecTelemetryModel.class);
         rawIecTelemetryModelLambdaQueryWrapper.eq(RawIecTelemetryModel::getDeviceCode, entry.getValue().getDeviceCode());
         RawIecTelemetryModel rawIecTelemetryModel = Objects.nonNull(entry.getValue().getRawIecTelemetryModel())
               && Objects.nonNull(entry.getValue().getRawIecTelemetryModel().getOpeningCoilCurrent())
            ? entry.getValue().getRawIecTelemetryModel()
            : (RawIecTelemetryModel)this.rawIecTelemetryMapper.selectOne(rawIecTelemetryModelLambdaQueryWrapper);
         LambdaQueryWrapper<EnergyStorageMotorModel> energyStorageMotorModelLambdaQueryWrapper = Wrappers.lambdaQuery(EnergyStorageMotorModel.class);
         ((LambdaQueryWrapper)((LambdaQueryWrapper)energyStorageMotorModelLambdaQueryWrapper.eq(
                  EnergyStorageMotorModel::getDeviceCode, entry.getValue().getDeviceCode()
               ))
               .orderByDesc(EnergyStorageMotorModel::getCreateDate))
            .last("limit 1");
         EnergyStorageMotorModel energyStorageMotorModel = (EnergyStorageMotorModel)this.energyStorageMotorMapper
            .selectOne(energyStorageMotorModelLambdaQueryWrapper);
         LambdaQueryWrapper<ThreeStationsModel> threeStationsModelLambdaQueryWrapper = Wrappers.lambdaQuery(ThreeStationsModel.class);
         ((LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)threeStationsModelLambdaQueryWrapper.eq(
                     ThreeStationsModel::getDeviceCode, entry.getValue().getDeviceCode()
                  ))
                  .eq(ThreeStationsModel::getDataType, 3))
               .orderByDesc(ThreeStationsModel::getCreateDate))
            .last("limit 1");
         ThreeStationsModel threeStationsModel = (ThreeStationsModel)this.threeStationsMapper.selectOne(threeStationsModelLambdaQueryWrapper);
         LambdaQueryWrapper<CircuitBreakerModel> openCircuitBreakerModelLambdaQueryWrapper = Wrappers.lambdaQuery(CircuitBreakerModel.class);
         ((LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)openCircuitBreakerModelLambdaQueryWrapper.eq(
                     CircuitBreakerModel::getDeviceCode, entry.getValue().getDeviceCode()
                  ))
                  .eq(CircuitBreakerModel::getDataType, 1))
               .orderByDesc(CircuitBreakerModel::getCreateDate))
            .last("limit 1");
         CircuitBreakerModel openCircuitBreakerModel = (CircuitBreakerModel)this.circuitBreakerMapper.selectOne(openCircuitBreakerModelLambdaQueryWrapper);
         LambdaQueryWrapper<CircuitBreakerModel> closeCircuitBreakerModelLambdaQueryWrapper = Wrappers.lambdaQuery(CircuitBreakerModel.class);
         ((LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)closeCircuitBreakerModelLambdaQueryWrapper.eq(
                     CircuitBreakerModel::getDeviceCode, entry.getValue().getDeviceCode()
                  ))
                  .eq(CircuitBreakerModel::getDataType, 0))
               .orderByDesc(CircuitBreakerModel::getCreateDate))
            .last("limit 1");
         CircuitBreakerModel closeCircuitBreakerModel = (CircuitBreakerModel)this.circuitBreakerMapper.selectOne(closeCircuitBreakerModelLambdaQueryWrapper);
         this.rawIecRemoteSignalingModelData1_24(skipInformationElementAddress, diInformationElements, entry.getValue());
         this.rawIecRemoteSignalingModelData25_38(skipInformationElementAddress, rawIecRemoteSignalingModel, diInformationElements);
         this.circuitBreakerOpening(skipInformationElementAddress, openCircuitBreakerModel, rawIecTelemetryModel, aiInformationElements);
         this.circuitBreakerClosing(skipInformationElementAddress, closeCircuitBreakerModel, rawIecTelemetryModel, aiInformationElements);
         this.energyStorageMotor(skipInformationElementAddress, energyStorageMotorModel, rawIecTelemetryModel, aiInformationElements);
         this.threeStations(skipInformationElementAddress, threeStationsModel, rawIecTelemetryModel, aiInformationElements);
         LambdaQueryWrapper<CircuitBreakerModel> circuitBreakerModelLambdaQueryWrapper = Wrappers.lambdaQuery(CircuitBreakerModel.class);
         circuitBreakerModelLambdaQueryWrapper.eq(CircuitBreakerModel::getDeviceCode, entry.getValue().getDeviceCode());
         Long circuitBreakerOperateNum = this.circuitBreakerMapper.selectCount(circuitBreakerModelLambdaQueryWrapper);

         try {
            aiInformationElements[14][0] = new IeShortFloat(circuitBreakerOperateNum == 0L ? 0.0F : (float)circuitBreakerOperateNum.longValue());
         } catch (Exception e) {
            aiInformationElements[14][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[14][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         LambdaQueryWrapper<ThreeStationsModel> lsolatedPositionOperateNumWrapper = Wrappers.lambdaQuery(ThreeStationsModel.class);
         ((LambdaQueryWrapper)lsolatedPositionOperateNumWrapper.eq(ThreeStationsModel::getDeviceCode, entry.getValue().getDeviceCode()))
            .and(
               w -> {
                  LambdaQueryWrapper var10000 = (LambdaQueryWrapper)((LambdaQueryWrapper)w.or(
                        we -> {
                           LambdaQueryWrapper var10000x = (LambdaQueryWrapper)((LambdaQueryWrapper)we.eq(ThreeStationsModel::getPosition, 0))
                              .eq(ThreeStationsModel::getOriginalPosition, 1);
                        }
                     ))
                     .or(
                        we -> {
                           LambdaQueryWrapper var10000x = (LambdaQueryWrapper)((LambdaQueryWrapper)we.eq(ThreeStationsModel::getPosition, 1))
                              .eq(ThreeStationsModel::getOriginalPosition, 0);
                        }
                     );
               }
            );
         Long lsolatedPositionOperateNum = this.threeStationsMapper.selectCount(lsolatedPositionOperateNumWrapper);

         try {
            aiInformationElements[15][0] = new IeShortFloat(lsolatedPositionOperateNum == 0L ? 0.0F : (float)lsolatedPositionOperateNum.longValue());
         } catch (Exception e) {
            aiInformationElements[15][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[15][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         LambdaQueryWrapper<ThreeStationsModel> groundingPositionOperateNumWrapper = Wrappers.lambdaQuery(ThreeStationsModel.class);
         ((LambdaQueryWrapper)groundingPositionOperateNumWrapper.eq(ThreeStationsModel::getDeviceCode, entry.getValue().getDeviceCode()))
            .and(
               w -> {
                  LambdaQueryWrapper var10000 = (LambdaQueryWrapper)((LambdaQueryWrapper)w.or(
                        we -> {
                           LambdaQueryWrapper var10000x = (LambdaQueryWrapper)((LambdaQueryWrapper)we.eq(ThreeStationsModel::getPosition, 1))
                              .eq(ThreeStationsModel::getOriginalPosition, 2);
                        }
                     ))
                     .or(
                        we -> {
                           LambdaQueryWrapper var10000x = (LambdaQueryWrapper)((LambdaQueryWrapper)we.eq(ThreeStationsModel::getPosition, 2))
                              .eq(ThreeStationsModel::getOriginalPosition, 1);
                        }
                     );
               }
            );
         Long groundingPositionOperateNum = this.threeStationsMapper.selectCount(groundingPositionOperateNumWrapper);

         try {
            aiInformationElements[16][0] = new IeShortFloat(groundingPositionOperateNum == 0L ? 0.0F : (float)groundingPositionOperateNum.longValue());
         } catch (Exception e) {
            aiInformationElements[16][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[16][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[41][0] = new IeShortFloat(rawIecTelemetryModel.getTempOfCbr() == 0.0F ? 0.0F : rawIecTelemetryModel.getTempOfCbr());
         } catch (Exception e) {
            aiInformationElements[41][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[41][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[42][0] = new IeShortFloat(rawIecTelemetryModel.getPressureOfCbr() == 0.0F ? 0.0F : rawIecTelemetryModel.getPressureOfCbr());
         } catch (Exception e) {
            aiInformationElements[42][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[42][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[43][0] = new IeShortFloat(rawIecTelemetryModel.getDensityOfCbr() == 0.0F ? 0.0F : rawIecTelemetryModel.getDensityOfCbr());
         } catch (Exception e) {
            aiInformationElements[43][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[43][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[44][0] = new IeShortFloat(rawIecTelemetryModel.getTempOfBr() == 0.0F ? 0.0F : rawIecTelemetryModel.getTempOfBr());
         } catch (Exception e) {
            aiInformationElements[44][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[44][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[45][0] = new IeShortFloat(rawIecTelemetryModel.getPressureOfBr() == 0.0F ? 0.0F : rawIecTelemetryModel.getPressureOfBr());
         } catch (Exception e) {
            aiInformationElements[45][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[45][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[46][0] = new IeShortFloat(rawIecTelemetryModel.getDensityOfBr() == 0.0F ? 0.0F : rawIecTelemetryModel.getDensityOfBr());
         } catch (Exception e) {
            aiInformationElements[46][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[46][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[47][0] = new IeShortFloat(rawIecTelemetryModel.getTempOfMui() == 0.0F ? 0.0F : rawIecTelemetryModel.getTempOfMui());
         } catch (Exception e) {
            aiInformationElements[47][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[47][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[48][0] = new IeShortFloat(rawIecTelemetryModel.getPressureOfMui() == 0.0F ? 0.0F : rawIecTelemetryModel.getPressureOfMui());
         } catch (Exception e) {
            aiInformationElements[48][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[48][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[49][0] = new IeShortFloat(rawIecTelemetryModel.getDensityOfMui() == 0.0F ? 0.0F : rawIecTelemetryModel.getDensityOfMui());
         } catch (Exception e) {
            aiInformationElements[49][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[49][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[50][0] = new IeShortFloat(rawIecTelemetryModel.getTempOfEnv() == 0.0F ? 0.0F : rawIecTelemetryModel.getTempOfEnv());
         } catch (Exception e) {
            aiInformationElements[50][0] = new IeShortFloat(rawIecTelemetryModel.getTempOfEnv() == 0.0F ? 0.0F : rawIecTelemetryModel.getTempOfEnv());
            log.debug("err", e);
         }

         aiInformationElements[50][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[51][0] = new IeShortFloat(rawIecTelemetryModel.getHumOfEnv() == 0.0F ? 0.0F : rawIecTelemetryModel.getHumOfEnv());
         } catch (Exception e) {
            aiInformationElements[51][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[51][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[52][0] = new IeShortFloat(
               rawIecTelemetryModel.getDischargeCurrentMeterA() == 0.0F ? 0.0F : rawIecTelemetryModel.getDischargeCurrentMeterA()
            );
         } catch (Exception e) {
            aiInformationElements[52][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[52][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[53][0] = new IeShortFloat(
               rawIecTelemetryModel.getDischargeCurrentMeterB() == 0.0F ? 0.0F : rawIecTelemetryModel.getDischargeCurrentMeterB()
            );
         } catch (Exception e) {
            aiInformationElements[53][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[53][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[54][0] = new IeShortFloat(
               rawIecTelemetryModel.getDischargeCurrentMeterC() == 0.0F ? 0.0F : rawIecTelemetryModel.getDischargeCurrentMeterC()
            );
         } catch (Exception e) {
            aiInformationElements[54][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[54][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[55][0] = new IeShortFloat(
               rawIecTelemetryModel.getLeakageCurrentMeterA() == 0.0F ? 0.0F : rawIecTelemetryModel.getLeakageCurrentMeterA()
            );
         } catch (Exception e) {
            aiInformationElements[55][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[55][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[56][0] = new IeShortFloat(
               rawIecTelemetryModel.getLeakageCurrentMeterB() == 0.0F ? 0.0F : rawIecTelemetryModel.getLeakageCurrentMeterB()
            );
         } catch (Exception e) {
            aiInformationElements[56][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[56][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[57][0] = new IeShortFloat(
               rawIecTelemetryModel.getLeakageCurrentMeterC() == 0.0F ? 0.0F : rawIecTelemetryModel.getLeakageCurrentMeterC()
            );
         } catch (Exception e) {
            aiInformationElements[57][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[57][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[8][0] = new IeShortFloat(0.0F);
         aiInformationElements[8][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[9][0] = new IeShortFloat(0.0F);
         aiInformationElements[9][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[10][0] = new IeShortFloat(0.0F);
         aiInformationElements[10][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[11][0] = new IeShortFloat(0.0F);
         aiInformationElements[11][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[12][0] = new IeShortFloat(0.0F);
         aiInformationElements[12][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[13][0] = new IeShortFloat(0.0F);
         aiInformationElements[13][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         InformationObject diObject = new InformationObject(1, diInformationElements);
         InformationObject aiObject = new InformationObject(16385, aiInformationElements);
         sunStationByCommonAddress.diObject = diObject;
         sunStationByCommonAddress.aiObject = aiObject;
         log.info("refreshData success for deviceCode : {} ", entry.getValue().getDeviceCode());
      }
   }

   private void rawIecRemoteSignalingModelData1_24(
      int skipAddress, InformationElement[][] diInformationElements, DeviceRawIecTelemtryDto deviceRawIecTelemtryDto
   ) {
      log.info("Iec104 scheduled refreshData rawIecRemoteSignalingModelData 1 to 23  start ");
      diInformationElements[0 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStartingCurrent"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStartingCurrent")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[1 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStoppingCurrent"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStoppingCurrent")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[2 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_workingCurrentOfCoil"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_workingCurrentOfCoil")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[3 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStartingTime"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStartingTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[4 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStoppingTime"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStoppingTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[5 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreWorkingTime"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreWorkingTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[6 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_actionTime")) == null
            ? false
            : (Boolean)this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_actionTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[7 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStartingCurrent"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStartingCurrent")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[8 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStoppingCurrent"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStoppingCurrent")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[9 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_workingCurrentOfCoil"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_workingCurrentOfCoil")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[10 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStartingTime"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStartingTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[11 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStoppingTime"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStoppingTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[12 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreWorkingTime"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreWorkingTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[13 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_actionTime")) == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_actionTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[14 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_startingCurrent"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_startingCurrent")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[15 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_idleElectricCurrent"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_idleElectricCurrent")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[16 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_outputCurrent")) == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_outputCurrent")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[17 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_startingTime")) == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_startingTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[18 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_idleElectricTime"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_idleElectricTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[19 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_outputTime")) == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_outputTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[20 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_actionTime")) == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_actionTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[21 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_peakValue"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_peakValue")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[22 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_valleyValue"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_valleyValue")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[23 + skipAddress][0] = new IeSinglePointWithQuality(
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_actionTime"))
               == null
            ? false
            : (Boolean)this.redisTemplate
               .opsForValue()
               .get(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_actionTime")),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
   }

   private void threeStations(
      int skipAddress, ThreeStationsModel threeStationsModel, RawIecTelemetryModel rawIecTelemetryModel, InformationElement[][] aiInformationElements
   ) {
      log.info("Iec104 scheduled refreshData threestations  start ");
      if (threeStationsModel != null) {
         try {
            aiInformationElements[6 + skipAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getThreeStationsOneCurrent() == null ? 0.0F : rawIecTelemetryModel.getThreeStationsOneCurrent() * 25.0F
            );
         } catch (Exception e) {
            aiInformationElements[6 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[6 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[7 + skipAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getThreeStationsOneCurrent() == null ? 0.0F : rawIecTelemetryModel.getThreeStationsOneCurrent()
            );
         } catch (Exception e) {
            aiInformationElements[7 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[7 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[38 + skipAddress][0] = new IeShortFloat(threeStationsModel.getPeakValue() == null ? 0.0F : threeStationsModel.getPeakValue());
         } catch (Exception e) {
            aiInformationElements[38 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[38 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[39 + skipAddress][0] = new IeShortFloat(
               threeStationsModel.getValleyValue() == null ? 0.0F : threeStationsModel.getValleyValue()
            );
         } catch (Exception e) {
            aiInformationElements[39 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[39 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[40 + skipAddress][0] = new IeShortFloat(
               threeStationsModel.getActionTime() == null ? 0.0F : threeStationsModel.getActionTime()
            );
         } catch (Exception e) {
            aiInformationElements[40 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[40 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      } else {
         aiInformationElements[6 + skipAddress][0] = new IeShortFloat(
            rawIecTelemetryModel.getThreeStationsOneCurrent() == null ? 0.0F : rawIecTelemetryModel.getThreeStationsOneCurrent() * 25.0F
         );
         aiInformationElements[6 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[7 + skipAddress][0] = new IeShortFloat(
            rawIecTelemetryModel.getThreeStationsOneCurrent() == null ? 0.0F : rawIecTelemetryModel.getThreeStationsOneCurrent()
         );
         aiInformationElements[7 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[38 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[38 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[39 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[39 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[40 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[40 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      }
   }

   private void energyStorageMotor(
      int skipAddress, EnergyStorageMotorModel energyStorageMotorModel, RawIecTelemetryModel rawIecTelemetryModel, InformationElement[][] aiInformationElements
   ) {
      log.info("Iec104 scheduled refreshData energyStorageMotor  start ");
      if (energyStorageMotorModel != null) {
         try {
            aiInformationElements[4 + skipAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getEnergyStorageMotorCurrent() == null ? 0.0F : rawIecTelemetryModel.getEnergyStorageMotorCurrent() * 25.0F
            );
         } catch (Exception e) {
            aiInformationElements[4 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[4 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[5 + skipAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getEnergyStorageMotorCurrent() == null ? 0.0F : rawIecTelemetryModel.getEnergyStorageMotorCurrent()
            );
         } catch (Exception e) {
            aiInformationElements[5 + skipAddress][0] = new IeShortFloat(0.0F);
            log.error("err", e);
         }

         aiInformationElements[5 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[31 + skipAddress][0] = new IeShortFloat(
               energyStorageMotorModel.getStartingCurrent() == null ? 0.0F : energyStorageMotorModel.getStartingCurrent()
            );
         } catch (Exception e) {
            aiInformationElements[31 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[31 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[32 + skipAddress][0] = new IeShortFloat(
               energyStorageMotorModel.getIdleElectricCurrent() == null ? 0.0F : energyStorageMotorModel.getIdleElectricCurrent()
            );
         } catch (Exception e) {
            aiInformationElements[32 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[32 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[33 + skipAddress][0] = new IeShortFloat(
               energyStorageMotorModel.getOutputCurrent() == null ? 0.0F : energyStorageMotorModel.getOutputCurrent()
            );
         } catch (Exception e) {
            aiInformationElements[33 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[33 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[34 + skipAddress][0] = new IeShortFloat(
               energyStorageMotorModel.getStartingTime() == null ? 0.0F : energyStorageMotorModel.getStartingTime()
            );
         } catch (Exception e) {
            aiInformationElements[34 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[34 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[35 + skipAddress][0] = new IeShortFloat(
               energyStorageMotorModel.getIdleElectricTime() == null ? 0.0F : energyStorageMotorModel.getIdleElectricTime()
            );
         } catch (Exception e) {
            aiInformationElements[35 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[35 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[36 + skipAddress][0] = new IeShortFloat(
               energyStorageMotorModel.getOutputTime() == null ? 0.0F : energyStorageMotorModel.getOutputTime()
            );
         } catch (Exception e) {
            aiInformationElements[36 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[36 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[37 + skipAddress][0] = new IeShortFloat(
               energyStorageMotorModel.getActionTime() == null ? 0.0F : energyStorageMotorModel.getActionTime()
            );
         } catch (Exception e) {
            aiInformationElements[37 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[37 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      } else {
         aiInformationElements[4 + skipAddress][0] = new IeShortFloat(
            rawIecTelemetryModel.getEnergyStorageMotorCurrent() == null ? 0.0F : rawIecTelemetryModel.getEnergyStorageMotorCurrent() * 25.0F
         );
         aiInformationElements[4 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[5 + skipAddress][0] = new IeShortFloat(
            rawIecTelemetryModel.getEnergyStorageMotorCurrent() == null ? 0.0F : rawIecTelemetryModel.getEnergyStorageMotorCurrent()
         );
         aiInformationElements[5 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[31 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[31 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[32 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[32 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[33 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[33 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[34 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[34 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[35 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[35 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[36 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[36 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[37 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[37 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      }
   }

   private void circuitBreakerClosing(
      int skipAddress, CircuitBreakerModel closeCircuitBreakerModel, RawIecTelemetryModel rawIecTelemetryModel, InformationElement[][] aiInformationElements
   ) {
      log.info("Iec104 scheduled refreshData circuitBreakerClosing  start ");
      if (closeCircuitBreakerModel != null) {
         try {
            aiInformationElements[2 + skipAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getClosingCoilCurrent() == null ? 0.0F : rawIecTelemetryModel.getClosingCoilCurrent() * 25.0F
            );
         } catch (Exception e) {
            aiInformationElements[2 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[2 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[3 + skipAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getClosingCoilCurrent() == null ? 0.0F : rawIecTelemetryModel.getClosingCoilCurrent()
            );
         } catch (Exception e) {
            aiInformationElements[3 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[3 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[24 + skipAddress][0] = new IeShortFloat(
               closeCircuitBreakerModel.getIronCoreStartingCurrent() == null ? 0.0F : closeCircuitBreakerModel.getIronCoreStartingCurrent()
            );
         } catch (Exception e) {
            aiInformationElements[24 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[24 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[25 + skipAddress][0] = new IeShortFloat(
               closeCircuitBreakerModel.getIronCoreStoppingCurrent() == null ? 0.0F : closeCircuitBreakerModel.getIronCoreStoppingCurrent()
            );
         } catch (Exception e) {
            aiInformationElements[25 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[25 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[26 + skipAddress][0] = new IeShortFloat(
               closeCircuitBreakerModel.getWorkingCurrentOfCoil() == null ? 0.0F : closeCircuitBreakerModel.getWorkingCurrentOfCoil()
            );
         } catch (Exception e) {
            aiInformationElements[26 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[26 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[27 + skipAddress][0] = new IeShortFloat(
               closeCircuitBreakerModel.getIronCoreStartingTime() == null ? 0.0F : closeCircuitBreakerModel.getIronCoreStartingTime()
            );
         } catch (Exception e) {
            aiInformationElements[27 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[27 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[28 + skipAddress][0] = new IeShortFloat(
               closeCircuitBreakerModel.getIronCoreStoppingTime() == null ? 0.0F : closeCircuitBreakerModel.getIronCoreStoppingTime()
            );
         } catch (Exception e) {
            aiInformationElements[28 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[28 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[29 + skipAddress][0] = new IeShortFloat(
               closeCircuitBreakerModel.getIronCoreWorkingTime() == null ? 0.0F : closeCircuitBreakerModel.getIronCoreWorkingTime()
            );
         } catch (Exception e) {
            aiInformationElements[29 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[29 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[30 + skipAddress][0] = new IeShortFloat(
               closeCircuitBreakerModel.getActionTime() == null ? 0.0F : closeCircuitBreakerModel.getActionTime()
            );
         } catch (Exception e) {
            aiInformationElements[30 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[30 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      } else {
         aiInformationElements[2 + skipAddress][0] = new IeShortFloat(
            rawIecTelemetryModel.getClosingCoilCurrent() == null ? 0.0F : rawIecTelemetryModel.getClosingCoilCurrent() * 25.0F
         );
         aiInformationElements[2 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[3 + skipAddress][0] = new IeShortFloat(
            rawIecTelemetryModel.getClosingCoilCurrent() == null ? 0.0F : rawIecTelemetryModel.getClosingCoilCurrent()
         );
         aiInformationElements[3 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[24 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[24 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[25 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[25 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[26 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[26 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[27 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[27 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[28 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[28 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[29 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[29 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[30 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[30 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      }
   }

   private void circuitBreakerOpening(
      int skipAddress, CircuitBreakerModel openCircuitBreakerModel, RawIecTelemetryModel rawIecTelemetryModel, InformationElement[][] aiInformationElements
   ) {
      log.info("Iec104 scheduled refreshData circuitBreakerOpening  start ");
      if (openCircuitBreakerModel != null) {
         try {
            aiInformationElements[0 + skipAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getOpeningCoilCurrent() == null ? 0.0F : rawIecTelemetryModel.getOpeningCoilCurrent() * 25.0F
            );
         } catch (Exception e) {
            aiInformationElements[0 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[0 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[1 + skipAddress][0] = new IeShortFloat(
               rawIecTelemetryModel.getOpeningCoilCurrent() == null ? 0.0F : rawIecTelemetryModel.getOpeningCoilCurrent()
            );
         } catch (Exception e) {
            aiInformationElements[1 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[1 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[17 + skipAddress][0] = new IeShortFloat(
               openCircuitBreakerModel.getIronCoreStartingCurrent() == null ? 0.0F : openCircuitBreakerModel.getIronCoreStartingCurrent()
            );
         } catch (Exception e) {
            aiInformationElements[17 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[17 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[18 + skipAddress][0] = new IeShortFloat(
               openCircuitBreakerModel.getIronCoreStoppingCurrent() == null ? 0.0F : openCircuitBreakerModel.getIronCoreStoppingCurrent()
            );
         } catch (Exception e) {
            aiInformationElements[18 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[18 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[19 + skipAddress][0] = new IeShortFloat(
               openCircuitBreakerModel.getWorkingCurrentOfCoil() == null ? 0.0F : openCircuitBreakerModel.getWorkingCurrentOfCoil()
            );
         } catch (Exception e) {
            aiInformationElements[19 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[19 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[20 + skipAddress][0] = new IeShortFloat(
               openCircuitBreakerModel.getIronCoreStartingTime() == null ? 0.0F : openCircuitBreakerModel.getIronCoreStartingTime()
            );
         } catch (Exception e) {
            aiInformationElements[20 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[20 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[21 + skipAddress][0] = new IeShortFloat(
               openCircuitBreakerModel.getIronCoreStoppingTime() == null ? 0.0F : openCircuitBreakerModel.getIronCoreStoppingTime()
            );
         } catch (Exception e) {
            aiInformationElements[21 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[21 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[22 + skipAddress][0] = new IeShortFloat(
               openCircuitBreakerModel.getIronCoreWorkingTime() == null ? 0.0F : openCircuitBreakerModel.getIronCoreWorkingTime()
            );
         } catch (Exception e) {
            aiInformationElements[22 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[22 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

         try {
            aiInformationElements[23 + skipAddress][0] = new IeShortFloat(
               openCircuitBreakerModel.getActionTime() == null ? 0.0F : openCircuitBreakerModel.getActionTime()
            );
         } catch (Exception e) {
            aiInformationElements[23 + skipAddress][0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements[23 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      } else {
         aiInformationElements[0 + skipAddress][0] = new IeShortFloat(
            rawIecTelemetryModel.getOpeningCoilCurrent() == null ? 0.0F : rawIecTelemetryModel.getOpeningCoilCurrent() * 25.0F
         );
         aiInformationElements[0 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[1 + skipAddress][0] = new IeShortFloat(
            rawIecTelemetryModel.getOpeningCoilCurrent() == null ? 0.0F : rawIecTelemetryModel.getOpeningCoilCurrent()
         );
         aiInformationElements[1 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[17 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[17 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[18 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[18 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[19 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[19 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[20 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[20 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[21 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[21 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[22 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[22 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         aiInformationElements[23 + skipAddress][0] = new IeShortFloat(0.0F);
         aiInformationElements[23 + skipAddress][1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      }
   }

   private void rawIecRemoteSignalingModelData25_38(
      int skipAddress, RawIecRemoteSignalingModel rawIecRemoteSignalingModel, InformationElement[][] diInformationElements
   ) {
      log.info("Iec104 scheduled refreshData rawIecRemoteSignalingModelData 25 to 38 start ");
      diInformationElements[24 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getSwitchDivision() == null ? false : rawIecRemoteSignalingModel.getSwitchDivision() != 0,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[25 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getSwitchClosedPosition() == null ? false : rawIecRemoteSignalingModel.getSwitchClosedPosition() != 0,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[26 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getThreePositionKnifeClosingPosition() == null
            ? false
            : rawIecRemoteSignalingModel.getThreePositionKnifeClosingPosition() == 1,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[27 + skipAddress][0] = new IeSinglePointWithQuality(
         (rawIecRemoteSignalingModel.getThreePositionKnifeClosingPosition() == null || rawIecRemoteSignalingModel.getThreePositionKnifeClosingPosition() == 0)
            && (
               rawIecRemoteSignalingModel.getThreeStationKnifeClosingPosition() == null
                  || rawIecRemoteSignalingModel.getThreeStationKnifeClosingPosition() == 0
            ),
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[28 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getThreeStationKnifeClosingPosition() == null
            ? false
            : rawIecRemoteSignalingModel.getThreeStationKnifeClosingPosition() == 1,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[29 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getSubsectionIsolationThQuarantineClosing() == null
            ? false
            : rawIecRemoteSignalingModel.getSubsectionIsolationThQuarantineClosing() == 1,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[30 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getSubsectionIsolationThQuarantineMiddle() == null
            ? false
            : rawIecRemoteSignalingModel.getSubsectionIsolationThQuarantineMiddle() == 1,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[31 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getSubsectionIsolationThQuarantineOpening() == null
            ? false
            : rawIecRemoteSignalingModel.getSubsectionIsolationThQuarantineOpening() == 1,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[32 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getCommunicationInterruption() == null ? false : rawIecRemoteSignalingModel.getCommunicationInterruption() != 0,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[33 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getCbInterruption() == null ? false : rawIecRemoteSignalingModel.getCbInterruption() != 0,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[34 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getBInterruption() == null ? false : rawIecRemoteSignalingModel.getBInterruption() != 0,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[35 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getEnvInterruption() == null ? false : rawIecRemoteSignalingModel.getEnvInterruption() != 0,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[36 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getArresterInterruption() == null ? false : rawIecRemoteSignalingModel.getArresterInterruption() != 0,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
      diInformationElements[37 + skipAddress][0] = new IeSinglePointWithQuality(
         rawIecRemoteSignalingModel.getIsolationPressureInterruption() == null ? false : rawIecRemoteSignalingModel.getIsolationPressureInterruption() != 0,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE,
         Boolean.FALSE
      );
   }

   static class BaseThreadFactory implements ThreadFactory {
      private static final AtomicInteger poolNumber = new AtomicInteger(1);
      private final ThreadGroup group;
      private final AtomicInteger threadNumber = new AtomicInteger(1);
      private final String namePrefix;

      BaseThreadFactory() {
         this.group = new ThreadGroup("WorkThreadPool-" + poolNumber.get());
         this.namePrefix = "WorkThreadPool-" + poolNumber.getAndIncrement() + "-thread-";
      }

      @Override
      public Thread newThread(Runnable r) {
         Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
         if (t.isDaemon()) {
            t.setDaemon(false);
         }

         if (t.getPriority() != 5) {
            t.setPriority(5);
         }

         return t;
      }
   }
}
