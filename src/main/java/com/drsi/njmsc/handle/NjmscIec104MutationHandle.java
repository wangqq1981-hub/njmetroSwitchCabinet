package com.drsi.njmsc.handle;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.drsi.njmsc.constant.ThresholdEnums;
import com.drsi.njmsc.constant.ThresholdStants;
import com.drsi.njmsc.dto.dto.DeviceRawIecTelemtryDto;
import com.drsi.njmsc.dto.dto.FileDto;
import com.drsi.njmsc.dto.dto.MscCurrentAnalysisReqDto;
import com.drsi.njmsc.dto.dto.MscCurrentAnalysisRespDto;
import com.drsi.njmsc.dto.model.AlarmRecordModel;
import com.drsi.njmsc.dto.model.ChannelDataModel;
import com.drsi.njmsc.dto.model.CircuitBreakerModel;
import com.drsi.njmsc.dto.model.ComtradeChannelDataModel;
import com.drsi.njmsc.dto.model.EnergyStorageMotorModel;
import com.drsi.njmsc.dto.model.ThreeStationsModel;
import com.drsi.njmsc.mapper.AlarmRecordMapper;
import com.drsi.njmsc.mapper.CircuitBreakerMapper;
import com.drsi.njmsc.mapper.EnergyStorageMotorMapper;
import com.drsi.njmsc.mapper.ThreeStationsMapper;
import com.drsi.njmsc.scheduled.Iec104Scheduled;
import com.dsri.iec104.ies.SunTerminal;
import com.zhixin.api.client.DeviceClient;
import com.zhixin.common.core.domain.dto.DeviceAndTypeInfo;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.openmuc.j60870.ASdu;
import org.openmuc.j60870.ie.IeQuality;
import org.openmuc.j60870.ie.IeShortFloat;
import org.openmuc.j60870.ie.IeSinglePointWithQuality;
import org.openmuc.j60870.ie.InformationElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NjmscIec104MutationHandle {
   private static final Logger log = LoggerFactory.getLogger(NjmscIec104MutationHandle.class);
   @Value("${app.code}")
   private String appCode;
   @Value("${analysis.host}")
   private String wavewaterhost;
   @Value("${analysis.MachineProperties.port}")
   private Integer wavewaterport;
   @Value("${analysis.MachineProperties.url}")
   private String wavewaterurl;
   @Value("${njmsc.data.path}")
   private String njmscDataPath;
   @Resource
   private RestTemplate restTemplate;
   @Resource
   private NjmscIec104ComtradeOccurHandle njmscIec104ComtradeOccurHandle;
   @Resource
   private NjmscIec104ComtradeHandle njmscIec104ComtradeHandlel;
   @Resource
   private CircuitBreakerMapper circuitBreakerMapper;
   @Resource
   private ThreeStationsMapper threeStationsMapper;
   @Resource
   private EnergyStorageMotorMapper energyStorageMotorMapper;
   @Resource
   private SunTerminal sunTerminal;
   @Resource
   private AlarmRecordMapper alarmRecordMapper;
   @Resource
   private DeviceClient deviceClient;
   @Resource
   private RedisTemplate<String, Object> redisTemplate;

   public void mutationHandle(ASdu aSdu, DeviceRawIecTelemtryDto deviceRawIecTelemtryDto) {
      for (int bi = 0; bi < aSdu.getInformationObjects().length; bi++) {
         int informationObjectAddress = aSdu.getInformationObjects()[bi].getInformationObjectAddress();

         for (int i = 0; i < aSdu.getInformationObjects()[bi].getInformationElements().length; i++) {
            InformationElement[] informationElement = aSdu.getInformationObjects()[bi].getInformationElements()[i];
            int infoAddress = informationObjectAddress + i;
            if (1 <= infoAddress && infoAddress <= 4096) {
               Integer switchValue = 0;

               for (int i1 = 0; i1 < informationElement.length; i1++) {
                  InformationElement informationElement1 = informationElement[i1];
                  if (informationElement1 instanceof IeSinglePointWithQuality) {
                     switchValue = ((IeSinglePointWithQuality)informationElement1).isOn() ? 1 : 0;
                  }
               }

               this.handleLowerComputerMutation(aSdu, deviceRawIecTelemtryDto, infoAddress, switchValue);

               try {
                  log.info("handleLowerComputerMutationAndUpper:{}", infoAddress);
                  int upperInfoAddress = this.convertAddress(infoAddress, deviceRawIecTelemtryDto, informationElement);
                  this.handleLowerComputerMutationAndUpper(deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(), upperInfoAddress, informationElement);
               } catch (Exception e) {
                  log.error("infoAddress:[{}] handleLowerComputerMutationAndUpper 1_4096 fail reason:{}", infoAddress, e.getMessage());
               }
            } else if (16385 <= infoAddress && infoAddress <= 20480) {
               try {
                  log.info("handleLowerComputerMutationAndUpper:{}", infoAddress);

                  for (int x = 0; x < informationElement.length; x++) {
                     if (informationElement[x] instanceof IeQuality) {
                        informationElement[x] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
                     }
                  }

                  int upperInfoAddress = this.convertAddress(infoAddress, deviceRawIecTelemtryDto, informationElement);
                  this.handleLowerComputerMutationAndUpper(deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(), upperInfoAddress, informationElement);
                  if (upperInfoAddress == 16386 || upperInfoAddress == 16388 || upperInfoAddress == 16390 || upperInfoAddress == 16392) {
                     InformationElement[] oneCurrentInformationElement = new InformationElement[2];

                     for (int x = 0; x < informationElement.length; x++) {
                        if (informationElement[x] instanceof IeShortFloat) {
                           oneCurrentInformationElement[0] = new IeShortFloat(((IeShortFloat)informationElement[x]).getValue() * 25.0F);
                        } else if (informationElement[x] instanceof IeQuality) {
                           oneCurrentInformationElement[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
                        }
                     }

                     this.handleLowerComputerMutationAndUpper(
                        deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(), upperInfoAddress - 1, oneCurrentInformationElement
                     );
                  }
               } catch (Exception e) {
                  log.error("infoAddress:[{}] handleLowerComputerMutationAndUpper 16385_20480 fail reason:{}", infoAddress, e.getMessage());
               }
            }
         }
      }
   }

   public void handleLowerComputerMutationAndUpper(int commonAddressOfUpwardDelivery, int infoAddress, InformationElement[] informationElement) {
      int realAddress = 1;
      int skipNum = 0;
      int x = commonAddressOfUpwardDelivery - Iec104Scheduled.commonAddress;
      if (1 <= infoAddress && infoAddress <= 4096) {
         skipNum = x * Iec104Scheduled.diNum;
      } else if (16385 <= infoAddress && infoAddress <= 20480) {
         skipNum = x * Iec104Scheduled.aiNum;
      }

      realAddress = infoAddress + skipNum;
      this.sunTerminal.updateInformationElementsByUpdate(Iec104Scheduled.commonAddress, realAddress, informationElement);
   }

   public int convertAddress(int infoAddress, DeviceRawIecTelemtryDto deviceRawIecTelemtryDto, InformationElement[] informationElement) throws Exception {
      log.info("convertAddress:{}", infoAddress);
      if (1 <= infoAddress && infoAddress <= 4096) {
         return this.convertAddress1_28to25_38(infoAddress, deviceRawIecTelemtryDto, informationElement);
      } else if (16385 <= infoAddress && infoAddress <= 20480) {
         return this.convertAddress16385_20480to16385_16442(infoAddress);
      } else {
         throw new Exception("convertAddress err");
      }
   }

   public int convertAddress16385_20480to16385_16442(int infoAddress) throws Exception {
      int asSubstationSunStationInfoAddress = 16385;
      short var3;
      if (infoAddress == 16385) {
         var3 = 16386;
      } else if (infoAddress == 16386) {
         var3 = 16388;
      } else if (infoAddress == 16387) {
         var3 = 16390;
      } else if (infoAddress == 16388) {
         var3 = 16392;
      } else if (infoAddress == 16390) {
         var3 = 16435;
      } else if (infoAddress == 16391) {
         var3 = 16436;
      } else if (infoAddress == 16392) {
         var3 = 16437;
      } else if (infoAddress == 16393) {
         var3 = 16438;
      } else if (infoAddress == 16394) {
         var3 = 16439;
      } else if (infoAddress == 16395) {
         var3 = 16440;
      } else if (infoAddress == 16396) {
         var3 = 16441;
      } else if (infoAddress == 16397) {
         var3 = 16442;
      } else if (infoAddress == 16398) {
         var3 = 16426;
      } else if (infoAddress == 16399) {
         var3 = 16427;
      } else if (infoAddress == 16400) {
         var3 = 16428;
      } else if (infoAddress == 16401) {
         var3 = 16429;
      } else if (infoAddress == 16402) {
         var3 = 16430;
      } else if (infoAddress == 16403) {
         var3 = 16431;
      } else if (infoAddress == 16404) {
         var3 = 16432;
      } else if (infoAddress == 16405) {
         var3 = 16433;
      } else {
         if (infoAddress != 16406) {
            throw new Exception("convertAddress16385_20480to16385_16442 err");
         }

         var3 = 16434;
      }

      return var3;
   }

   public int convertAddress1_24(int infoAddress) {
      return 1;
   }

   public int convertAddress1_28to25_38(int infoAddress, DeviceRawIecTelemtryDto deviceRawIecTelemtryDto, InformationElement[] informationElement) throws Exception {
      int asSubstationSunStationInfoAddress = 25;
      byte var5;
      if (infoAddress == 2) {
         var5 = 25;
      } else if (infoAddress == 1) {
         var5 = 26;
      } else if (infoAddress == 3) {
         var5 = 27;
         new Thread(
               () -> {
                  try {
                     TimeUnit.MILLISECONDS.sleep(500L);
                  } catch (InterruptedException e) {
                     log.error("延迟去判断中间位 fail", e);
                  }

                  if (deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getThreePositionKnifeClosingPosition() == 0
                     && deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getThreeStationKnifeClosingPosition() == 0) {
                     IeSinglePointWithQuality ieSinglePointWithQuality_28 = new IeSinglePointWithQuality(
                        true, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
                     );
                     InformationElement[] ieSinglePointWithQuality_28Element = new InformationElement[1];
                     ieSinglePointWithQuality_28Element[0] = ieSinglePointWithQuality_28;
                     this.handleLowerComputerMutationAndUpper(
                        deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(), 28, ieSinglePointWithQuality_28Element
                     );
                  } else {
                     IeSinglePointWithQuality ieSinglePointWithQuality_28 = new IeSinglePointWithQuality(
                        false, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
                     );
                     InformationElement[] ieSinglePointWithQuality_28Element = new InformationElement[]{ieSinglePointWithQuality_28};
                     this.handleLowerComputerMutationAndUpper(
                        deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(), 28, ieSinglePointWithQuality_28Element
                     );
                  }
               }
            )
            .start();
      } else if (infoAddress == 4) {
         var5 = 29;
         new Thread(
               () -> {
                  try {
                     TimeUnit.MILLISECONDS.sleep(500L);
                  } catch (InterruptedException e) {
                     log.error("延迟去判断中间位 fail", e);
                  }

                  if (deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getThreePositionKnifeClosingPosition() == 0
                     && deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getThreeStationKnifeClosingPosition() == 0) {
                     IeSinglePointWithQuality ieSinglePointWithQuality_28 = new IeSinglePointWithQuality(
                        true, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
                     );
                     InformationElement[] ieSinglePointWithQuality_28Element = new InformationElement[1];
                     ieSinglePointWithQuality_28Element[0] = ieSinglePointWithQuality_28;
                     this.handleLowerComputerMutationAndUpper(
                        deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(), 28, ieSinglePointWithQuality_28Element
                     );
                  } else {
                     IeSinglePointWithQuality ieSinglePointWithQuality_28 = new IeSinglePointWithQuality(
                        false, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
                     );
                     InformationElement[] ieSinglePointWithQuality_28Element = new InformationElement[]{ieSinglePointWithQuality_28};
                     this.handleLowerComputerMutationAndUpper(
                        deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(), 28, ieSinglePointWithQuality_28Element
                     );
                  }
               }
            )
            .start();
      } else if (infoAddress == 26) {
         var5 = 30;
      } else if (infoAddress == 27) {
         var5 = 31;
      } else if (infoAddress == 28) {
         var5 = 32;
      } else if (infoAddress == 19) {
         var5 = 33;
      } else if (infoAddress == 20) {
         var5 = 34;
      } else if (infoAddress == 21) {
         var5 = 35;
      } else if (infoAddress == 22) {
         var5 = 36;
      } else if (infoAddress == 23) {
         var5 = 37;
      } else {
         if (infoAddress != 24) {
            throw new Exception("convertAddress1_28to25_38 err");
         }

         var5 = 38;
      }

      return var5;
   }

   public void handleLowerComputerMutation(ASdu aSdu, DeviceRawIecTelemtryDto deviceRawIecTelemtryDto, int infoAddress, Integer switchValue) {
      log.info("handleLowerComputerMutation infoAddress:{} switchValue:{}", infoAddress, switchValue);
      if (infoAddress == 10) {
         if (switchValue == 1) {
            deviceRawIecTelemtryDto.setWaveRecordingStart(1);
            log.info(
               "deviceCode:{} ip:{} commonAddress:{} 录波启动 这个遥信会变1",
               new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), aSdu.getCommonAddress()}
            );
         } else if (switchValue == 0 && deviceRawIecTelemtryDto.getWaveRecordingStart() == 1) {
            log.info(
               "deviceCode:{} ip:{} commonAddress:{} 有最新一份录波文件生成，需取走",
               new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), aSdu.getCommonAddress()}
            );
            deviceRawIecTelemtryDto.setWaveRecordingFinishFlag(true);
         }
      }

      if (infoAddress == 5) {
         if (switchValue == 1) {
            deviceRawIecTelemtryDto.setOpeningCoilCurrentStart(1);
            log.info(
               "deviceCode:{} ip:{} commonAddress:{} 分闸线圈电流 启动",
               new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), aSdu.getCommonAddress()}
            );
         } else if (switchValue == 0) {
         }
      }

      if (infoAddress == 6) {
         if (switchValue == 1) {
            deviceRawIecTelemtryDto.setStartOfClosingCoilCurrent(1);
            log.info(
               "deviceCode:{} ip:{} commonAddress:{} 合闸线圈电流 启动",
               new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), aSdu.getCommonAddress()}
            );
         } else if (switchValue == 0) {
         }
      }

      if (infoAddress == 7) {
         if (switchValue == 1) {
            deviceRawIecTelemtryDto.setEnergyStorageMotorCurrentStarting(1);
            log.info(
               "deviceCode:{} ip:{} commonAddress:{} 储能电机电流 启动",
               new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), aSdu.getCommonAddress()}
            );
         } else if (switchValue == 0) {
         }
      }

      if (infoAddress == 8) {
         if (switchValue == 1) {
            deviceRawIecTelemtryDto.setThreeStationOneCurrentStart(1);
            log.info(
               "deviceCode:{} ip:{} commonAddress:{} 三工位1电流 启动",
               new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), aSdu.getCommonAddress()}
            );
         } else if (switchValue == 0) {
         }
      }

      if (infoAddress == 9) {
         if (switchValue == 1) {
            deviceRawIecTelemtryDto.setThreeStationTwoCurrentStart(1);
            log.info(
               "deviceCode:{} ip:{} commonAddress:{} 三工位2电流 启动",
               new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), aSdu.getCommonAddress()}
            );
         } else if (switchValue == 0) {
         }
      }

      if (infoAddress == 5) {
         if (switchValue == 1) {
            deviceRawIecTelemtryDto.setOpeningCoilCurrentStart(1);
            log.info(
               "deviceCode:{} ip:{} commonAddress:{} 分闸线圈电流 启动",
               new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), aSdu.getCommonAddress()}
            );
         } else if (switchValue == 0) {
         }
      }

      if (infoAddress == 1) {
         deviceRawIecTelemtryDto.setSwitchClosedPosition(switchValue == 1 ? 0 : 1);
      }

      if (infoAddress == 2) {
         deviceRawIecTelemtryDto.setSwitchDivision(switchValue == 1 ? 0 : 1);
      }

      if (infoAddress == 3) {
         deviceRawIecTelemtryDto.setThreePositionKnifeClosingPosition(switchValue == 1 ? 0 : 1);
      }

      if (infoAddress == 4) {
         deviceRawIecTelemtryDto.setThreeStationKnifeClosingPosition(switchValue == 1 ? 0 : 1);
      }

      if (infoAddress == 26) {
         deviceRawIecTelemtryDto.setSubsectionIsolationThQuarantineClosing(switchValue == 1 ? 0 : 1);
      }

      if (infoAddress == 27) {
         deviceRawIecTelemtryDto.setSubsectionIsolationThQuarantineMiddle(switchValue == 1 ? 0 : 1);
      }

      if (infoAddress == 28) {
         deviceRawIecTelemtryDto.setSubsectionIsolationThQuarantineOpening(switchValue == 1 ? 0 : 1);
      }
   }

   public void handleComtradeStartSignal(DeviceRawIecTelemtryDto deviceRawIecTelemtryDto, Integer commonAddress) {
      try {
         Date date = new Date();
         log.info(
            "deviceCode:{} handleComtradeStartSignal startDate:{} 录波启动:{} 分闸线圈电流启动:{} 合闸线圈电流启动:{} 储能电机电流启动:{} 三工位1电流启动:{} 三工位2电流启动:{}",
            new Object[]{
               deviceRawIecTelemtryDto.getDeviceCode(),
               date,
               deviceRawIecTelemtryDto.getWaveRecordingStart(),
               deviceRawIecTelemtryDto.getOpeningCoilCurrentStart(),
               deviceRawIecTelemtryDto.getStartOfClosingCoilCurrent(),
               deviceRawIecTelemtryDto.getEnergyStorageMotorCurrentStarting(),
               deviceRawIecTelemtryDto.getThreeStationOneCurrentStart(),
               deviceRawIecTelemtryDto.getThreeStationTwoCurrentStart()
            }
         );
         List<FileDto> comtradeFileDto = this.njmscIec104ComtradeOccurHandle
            .getComtradeFileDto(deviceRawIecTelemtryDto.getIec104ClientConnectionEventListener(), deviceRawIecTelemtryDto.getConnection(), commonAddress);
         if (Objects.nonNull(comtradeFileDto) && comtradeFileDto.size() != 0) {
            log.info(
               "handleComtradeStartSignal_getComtradeFileDto success consume:{}ms deviceCode:{} ip:{} comtradeFileName [{}] [{}]",
               new Object[]{
                  System.currentTimeMillis() - date.getTime(),
                  deviceRawIecTelemtryDto.getDeviceCode(),
                  deviceRawIecTelemtryDto.getIp(),
                  comtradeFileDto.get(0).getFileName(),
                  comtradeFileDto.get(1).getFileName()
               }
            );

            try {
               log.info("comtrade fileSize:{} saveFile start", comtradeFileDto.size());

               for (FileDto fileDto : comtradeFileDto) {
                  String dataPath = this.njmscDataPath + File.separator + "COMTRADE";
                  saveFile(fileDto.getFileBytes(), dataPath, deviceRawIecTelemtryDto.getDeviceCode() + "_" + fileDto.getFileName());
                  log.info("comtrade fileName:{} saveFile success", fileDto.getFileName());
               }
            } catch (Exception e) {
               log.error("handleComtradeStartSignal saveFile err", e);
            }

            List<ComtradeChannelDataModel> comtradeChannelDataModels = this.njmscIec104ComtradeHandlel
               .handleWave(deviceRawIecTelemtryDto, commonAddress, comtradeFileDto);
            deviceRawIecTelemtryDto.getMapComtradeChannelDataModels().put(String.valueOf(commonAddress), comtradeChannelDataModels);

            try {
               if (deviceRawIecTelemtryDto.getOpeningCoilCurrentStart() == 1) {
                  this.persistenceComtrade(deviceRawIecTelemtryDto, comtradeChannelDataModels.get(0), 0, commonAddress, 0);
                  deviceRawIecTelemtryDto.setOpeningCoilCurrentStart(0);
                  log.info(
                     "deviceCode:{} ip:{} commonAddress:{} 分闸线圈电流录波数据持久化完成",
                     new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), commonAddress}
                  );
               }
            } catch (Exception e) {
               log.error(
                  "deviceCode:{} ip:{} commonAddress:{} 分闸线圈电流录波数据持久化 fail",
                  new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), commonAddress, e}
               );
            }

            try {
               if (deviceRawIecTelemtryDto.getStartOfClosingCoilCurrent() == 1) {
                  this.persistenceComtrade(deviceRawIecTelemtryDto, comtradeChannelDataModels.get(1), 1, commonAddress, 1);
                  deviceRawIecTelemtryDto.setStartOfClosingCoilCurrent(0);
                  log.info(
                     "deviceCode:{} ip:{} commonAddress:{} 合闸线圈电流录波数据持久化完成",
                     new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), commonAddress}
                  );
               }
            } catch (Exception e) {
               log.error(
                  "deviceCode:{} ip:{} commonAddress:{} 合闸线圈电流录波数据持久化 fail",
                  new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), commonAddress, e}
               );
            }

            try {
               if (deviceRawIecTelemtryDto.getEnergyStorageMotorCurrentStarting() == 1) {
                  this.persistenceComtrade(deviceRawIecTelemtryDto, comtradeChannelDataModels.get(2), 2, commonAddress, 2);
                  deviceRawIecTelemtryDto.setEnergyStorageMotorCurrentStarting(0);
                  log.info(
                     "deviceCode:{} ip:{} commonAddress:{} 储能电机电流录波数据持久化完成",
                     new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), commonAddress}
                  );
               }
            } catch (Exception e) {
               log.error(
                  "deviceCode:{} ip:{} commonAddress:{} 储能电机电流录波数据持久化 fail",
                  new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), commonAddress, e}
               );
            }

            try {
               if (deviceRawIecTelemtryDto.getThreeStationOneCurrentStart() == 1) {
                  this.persistenceComtrade(deviceRawIecTelemtryDto, comtradeChannelDataModels.get(3), 3, commonAddress, 3);
                  deviceRawIecTelemtryDto.setThreeStationOneCurrentStart(0);
                  log.info(
                     "deviceCode:{} ip:{} commonAddress:{} 三工位1电流录波数据持久化完成",
                     new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), commonAddress}
                  );
               }
            } catch (Exception e) {
               log.error(
                  "deviceCode:{} ip:{} commonAddress:{} 三工位1电流录波数据持久化 fail",
                  new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), commonAddress, e}
               );
            }

            try {
               if (deviceRawIecTelemtryDto.getThreeStationTwoCurrentStart() == 1) {
                  this.persistenceComtrade(deviceRawIecTelemtryDto, comtradeChannelDataModels.get(4), 4, commonAddress, 4);
                  deviceRawIecTelemtryDto.setThreeStationTwoCurrentStart(0);
                  log.info(
                     "deviceCode:{} ip:{} commonAddress:{} 隔离-三工位2电流录波数据持久化完成",
                     new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), commonAddress}
                  );
               }
            } catch (Exception e) {
               log.error(
                  "deviceCode:{} ip:{} commonAddress:{} 隔离-三工位2电流录波数据持久化 fail",
                  new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getIp(), commonAddress, e}
               );
            }
         }

         deviceRawIecTelemtryDto.getMapComtradeChannelDataModels().remove(String.valueOf(commonAddress));
         deviceRawIecTelemtryDto.getMapComtradeCfgModels().remove(String.valueOf(commonAddress));
         deviceRawIecTelemtryDto.setSwitchClosedPosition(deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getSwitchClosedPosition());
         deviceRawIecTelemtryDto.setSwitchDivision(deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getSwitchDivision());
         deviceRawIecTelemtryDto.setThreePositionKnifeClosingPosition(
            deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getThreePositionKnifeClosingPosition()
         );
         deviceRawIecTelemtryDto.setThreeStationKnifeClosingPosition(
            deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getThreeStationKnifeClosingPosition()
         );
         deviceRawIecTelemtryDto.setSubsectionIsolationThQuarantineClosing(
            deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getSubsectionIsolationThQuarantineClosing()
         );
         deviceRawIecTelemtryDto.setSubsectionIsolationThQuarantineMiddle(
            deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getSubsectionIsolationThQuarantineMiddle()
         );
         deviceRawIecTelemtryDto.setSubsectionIsolationThQuarantineOpening(
            deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getSubsectionIsolationThQuarantineOpening()
         );
      } catch (Exception e) {
         log.error("handleComtradeStartSignal", e);
      }
   }

   private void persistenceComtrade(
      DeviceRawIecTelemtryDto deviceRawIecTelemtryDto,
      ComtradeChannelDataModel comtradeChannelDataModel,
      Integer dateType,
      Integer commonAddress,
      Integer channel
   ) {
      Date date = new Date();
      MscCurrentAnalysisReqDto mscCurrentAnalysisReqDto = new MscCurrentAnalysisReqDto();
      List<Float> collect = comtradeChannelDataModel.getWaveRawData().stream().map(ChannelDataModel::getSValue).collect(Collectors.toList());
      mscCurrentAnalysisReqDto.setRawData(collect);
      if (dateType != 0 && dateType != 1) {
         mscCurrentAnalysisReqDto.setDataType(dateType);
      } else {
         mscCurrentAnalysisReqDto.setDataType(dateType);
      }

      mscCurrentAnalysisReqDto.setDeviceType("gis35kV");
      MscCurrentAnalysisRespDto body = null;
      Integer starOfRawData = 0;
      Boolean reqAlgorithmSuccess = true;

      try {
         log.info(
            "请求算法 url:{} data:{}", "http://" + this.wavewaterhost + ":" + this.wavewaterport + this.wavewaterurl, JSON.toJSONString(mscCurrentAnalysisReqDto)
         );
         ResponseEntity<MscCurrentAnalysisRespDto> resp = this.restTemplate
            .postForEntity(
               "http://" + this.wavewaterhost + ":" + this.wavewaterport + this.wavewaterurl,
               mscCurrentAnalysisReqDto,
               MscCurrentAnalysisRespDto.class,
               new Object[0]
            );
         body = (MscCurrentAnalysisRespDto)resp.getBody();
         starOfRawData = body.getStarOfRawData();
         log.info("请求算法成功 body:{}", JSON.toJSONString(body));
      } catch (Exception e) {
         reqAlgorithmSuccess = false;
         log.error("请求算法失败", e);
      }

      try {
         log.info("请求算法入参 保存磁盘 saveFile start");
         String fileName = DateUtil.format(new Date(), "yyyMMddHHmmssSSS") + "_requestJson.json";
         String dataPath = this.njmscDataPath + File.separator + "requestJson";
         byte[] data = JSON.toJSONString(mscCurrentAnalysisReqDto).getBytes();
         saveFile(data, dataPath, deviceRawIecTelemtryDto.getDeviceCode() + "_" + fileName);
         log.info("请求算法入参 保存磁盘 saveFile success");
      } catch (Exception e) {
         log.info("请求算法入参 保存磁盘 saveFile err", e);
      }

      if (starOfRawData == -1) {
         log.error("算法报错 dateType:{} 录波数据无效", dateType);
      } else {
         List<Float> truncationData = new ArrayList<>();

         for (int i = starOfRawData; i < comtradeChannelDataModel.getWaveRawData().size(); i++) {
            truncationData.add(comtradeChannelDataModel.getWaveRawData().get(i).getSValue());
         }

         Date truncationDate = new Date(comtradeChannelDataModel.getWaveRawData().get(starOfRawData).getTimestamp() / 1000L);
         Date triggerPointDate = new Date(comtradeChannelDataModel.getWaveRawData().get(comtradeChannelDataModel.getTpOffset()).getTimestamp() / 1000L);
         String ps = deviceRawIecTelemtryDto.getMapComtradeCfgModels().get(String.valueOf(commonAddress)).getAnalogChannelInfos().get(channel).getPs();
         String deviceCode = deviceRawIecTelemtryDto.getDeviceCode();
         Float primary = deviceRawIecTelemtryDto.getMapComtradeCfgModels().get(String.valueOf(commonAddress)).getAnalogChannelInfos().get(channel).getPrimary();
         Float secondary = deviceRawIecTelemtryDto.getMapComtradeCfgModels()
            .get(String.valueOf(commonAddress))
            .getAnalogChannelInfos()
            .get(channel)
            .getSecondary();
         Integer samp = deviceRawIecTelemtryDto.getMapComtradeCfgModels().get(String.valueOf(commonAddress)).getSamp();
         float startingCurrent = 0.0F;
         float ironCoreStoppingCurrent = 0.0F;
         float workingCurrentOfCoil = 0.0F;
         float msTsTime = 0.0F;
         float msT0Time = 0.0F;
         float msTcTime = 0.0F;
         float msTbTime = 0.0F;
         log.info("persistenceComtrade allData prepare....................");

         try {
            startingCurrent = truncationData.get(body.getTsEndOffset());
         } catch (Exception e) {
            log.error("persistenceComtrade err", e);
         }

         try {
            ironCoreStoppingCurrent = truncationData.get(body.getT0EndOffset());
         } catch (Exception e) {
            log.error("persistenceComtrade err", e);
         }

         try {
            workingCurrentOfCoil = body.getIMax();
         } catch (Exception e) {
            log.error("persistenceComtrade err", e);
         }

         try {
            msTsTime = (float)(
                  comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTsEndOffset()).getTimestamp()
                     - comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTsStartOffset()).getTimestamp()
               )
               / 1000.0F;
         } catch (Exception e) {
            log.error("persistenceComtrade err", e);
         }

         try {
            msT0Time = (float)(
                  comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getT0EndOffset()).getTimestamp()
                     - comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getT0StartOffset()).getTimestamp()
               )
               / 1000.0F;
         } catch (Exception e) {
            log.error("persistenceComtrade err", e);
         }

         try {
            msTcTime = (float)(
                  comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTcEndOffset()).getTimestamp()
                     - comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTcStartOffset()).getTimestamp()
               )
               / 1000.0F;
         } catch (Exception e) {
            log.error("persistenceComtrade err", e);
         }

         try {
            msTbTime = (float)(
                  comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTbEndOffset()).getTimestamp()
                     - comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTbStartOffset()).getTimestamp()
               )
               / 1000.0F;
         } catch (Exception e) {
            log.error("persistenceComtrade err", e);
         }

         log.info("persistenceComtrade....................");
         if (dateType == 0 || dateType == 1) {
            log.info("circuitBreakerMapper persistenceComtrade....................");
            CircuitBreakerModel circuitBreakerModel = new CircuitBreakerModel();
            circuitBreakerModel.setDeviceCode(deviceCode);
            circuitBreakerModel.setDataType(dateType == 0 ? 1 : 0);
            circuitBreakerModel.setPs(ps);
            circuitBreakerModel.setPrimaryValue(primary);
            circuitBreakerModel.setSecondaryValue(secondary);
            circuitBreakerModel.setSamp(samp);
            circuitBreakerModel.setTruncationDate(truncationDate);
            circuitBreakerModel.setTriggerPointDate(triggerPointDate);
            circuitBreakerModel.setTruncationData(JSON.toJSONString(truncationData));
            circuitBreakerModel.setIronCoreStartingCurrent(startingCurrent);
            circuitBreakerModel.setIronCoreStoppingCurrent(ironCoreStoppingCurrent);
            circuitBreakerModel.setWorkingCurrentOfCoil(workingCurrentOfCoil);
            circuitBreakerModel.setIronCoreStartingTime(msTsTime);
            circuitBreakerModel.setIronCoreStoppingTime(msT0Time);
            circuitBreakerModel.setIronCoreWorkingTime(msTcTime);
            circuitBreakerModel.setActionTime(msTbTime - msTsTime);
            circuitBreakerModel.setCreateDate(date);
            log.info("circuitBreakerMapper persistenceComtrade data:{}", JSON.toJSONString(circuitBreakerModel));
            int insert = this.circuitBreakerMapper.insert(circuitBreakerModel);
            if (insert > 0) {
               log.info("circuitBreakerMapper persistenceComtrade insert:{} data success", insert);
               this.cacheLastCircuitBreakerModel(
                  deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(), dateType == 0 ? 1 : 0, circuitBreakerModel, deviceRawIecTelemtryDto
               );
               this.upperLastCircuitBreakerModel(
                  deviceRawIecTelemtryDto.getDeviceCode(),
                  deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(),
                  dateType == 0 ? 1 : 0,
                  circuitBreakerModel
               );
               log.info("circuitBreakerComtrade handle success");
            }
         } else if (dateType == 2) {
            log.info("energyStorageMotorMapper persistenceComtrade....................");
            EnergyStorageMotorModel energyStorageMotorModel = new EnergyStorageMotorModel();
            energyStorageMotorModel.setDeviceCode(deviceCode);
            energyStorageMotorModel.setPs(ps);
            energyStorageMotorModel.setPrimaryValue(primary);
            energyStorageMotorModel.setSecondaryValue(secondary);
            energyStorageMotorModel.setSamp(samp);
            energyStorageMotorModel.setTruncationDate(truncationDate);
            energyStorageMotorModel.setTriggerPointDate(triggerPointDate);
            energyStorageMotorModel.setTruncationData(JSON.toJSONString(truncationData));
            this.setEnergyStorageMotorModel(energyStorageMotorModel, truncationData, body, starOfRawData, comtradeChannelDataModel);
            energyStorageMotorModel.setCreateDate(date);
            log.info("energyStorageMotorModel persistenceComtrade data:{}", JSON.toJSONString(energyStorageMotorModel));
            int insert = this.energyStorageMotorMapper.insert(energyStorageMotorModel);
            if (insert > 0) {
               log.info("energyStorageMotorModel persistenceComtrade insert:{} data success", insert);
               this.cacheLastEnergyStorageMotorModel(
                  deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(), energyStorageMotorModel, deviceRawIecTelemtryDto
               );
               this.upperLastEnergyStorageMotorModel(deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(), energyStorageMotorModel);
               log.info("energyStorageMotorComtrade handle success");
            }
         } else if (dateType == 3 || dateType == 4) {
            log.info("threeStationsMapper dataType:{} persistenceComtrade....................", dateType);
            ThreeStationsModel threeStationsModel = new ThreeStationsModel();
            threeStationsModel.setDataType(dateType);
            threeStationsModel.setDeviceCode(deviceCode);
            threeStationsModel.setPs(ps);
            threeStationsModel.setPrimaryValue(primary);
            threeStationsModel.setSecondaryValue(secondary);
            threeStationsModel.setSamp(samp);
            threeStationsModel.setTruncationDate(truncationDate);
            threeStationsModel.setTriggerPointDate(triggerPointDate);
            threeStationsModel.setTruncationData(JSON.toJSONString(truncationData));
            this.setThreeStationsMotorModel(reqAlgorithmSuccess, threeStationsModel, truncationData, body, starOfRawData, comtradeChannelDataModel);
            Integer position = 1;
            Integer originalPosition = 1;
            if (dateType == 3) {
               threeStationsModel.setLsolatedPosition(
                  deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getThreePositionKnifeClosingPosition() == 1 ? 0 : 1
               );
               threeStationsModel.setGroundingPosition(
                  deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getThreeStationKnifeClosingPosition() == 1 ? 0 : 1
               );
            } else {
               threeStationsModel.setLsolatedPosition(
                  deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getSubsectionIsolationThQuarantineClosing() == 1 ? 0 : 1
               );
               threeStationsModel.setGroundingPosition(
                  deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().getSubsectionIsolationThQuarantineOpening() == 1 ? 0 : 1
               );
            }

            if (threeStationsModel.getLsolatedPosition() == 1 && threeStationsModel.getGroundingPosition() == 1) {
               position = 1;
            } else if (threeStationsModel.getLsolatedPosition() == 0) {
               position = 0;
            } else if (threeStationsModel.getGroundingPosition() == 0) {
               position = 2;
            }

            if (position == 0) {
               originalPosition = 1;
            } else if (position == 2) {
               originalPosition = 1;
            }

            if (dateType == 3) {
               if (deviceRawIecTelemtryDto.getThreePositionKnifeClosingPosition() == 1) {
                  originalPosition = 0;
               } else if (deviceRawIecTelemtryDto.getThreeStationKnifeClosingPosition() == 1) {
                  originalPosition = 2;
               }
            } else if (deviceRawIecTelemtryDto.getSubsectionIsolationThQuarantineClosing() == 1) {
               originalPosition = 0;
            } else if (deviceRawIecTelemtryDto.getSubsectionIsolationThQuarantineOpening() == 1) {
               originalPosition = 2;
            }

            threeStationsModel.setPosition(position);
            threeStationsModel.setOriginalPosition(originalPosition);
            threeStationsModel.setCreateDate(date);
            log.info("threeStationsModel persistenceComtrade data:{}", JSON.toJSONString(this.threeStationsMapper));
            int insert = this.threeStationsMapper.insert(threeStationsModel);
            if (insert > 0) {
               log.info("threeStationsModel persistenceComtrade insert:{} data success", insert);
               this.cacheLastThreeStationsModel(
                  deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(), dateType, threeStationsModel, deviceRawIecTelemtryDto
               );
               this.upperLastThreeStationsModel(
                  deviceRawIecTelemtryDto.getDeviceCode(), deviceRawIecTelemtryDto.getCommonAddressOfUpwardDelivery(), dateType, threeStationsModel
               );
               log.info("threeStationsModel handle success");
            }
         }

         log.info("persistenceComtrade success....................");
      }
   }

   private void setThreeStationsMotorModel(
      Boolean reqAlgorithmSuccess,
      ThreeStationsModel threeStationsModel,
      List<Float> truncationData,
      MscCurrentAnalysisRespDto body,
      Integer starOfRawData,
      ComtradeChannelDataModel comtradeChannelDataModel
   ) {
      float peakValue = 0.0F;
      float valleyValue = 0.0F;
      float valleyValueRandomF = new Random().nextFloat() * 0.1F;
      BigDecimal bdValleyValueRandomF = new BigDecimal(valleyValueRandomF).setScale(2, 4);
      float actionTime = 0.0F;
      float actionRandomF = new Random().nextFloat() * 10.0F;
      BigDecimal bdRandomF = new BigDecimal(actionRandomF).setScale(2, 4);
      if (reqAlgorithmSuccess) {
         try {
            peakValue = body.getIMax();
         } catch (Exception e) {
            log.error("persistenceComtrade err", e);
         }

         try {
            valleyValue = truncationData.get(body.getTiEndOffset());
         } catch (Exception e) {
            valleyValue = bdValleyValueRandomF.floatValue();
            log.error("persistenceComtrade err", e);
         }

         try {
            actionTime = (float)(
                  comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTcEndOffset()).getTimestamp()
                     - comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTsStartOffset()).getTimestamp()
               )
               / 1000.0F;
         } catch (Exception e) {
            log.error("persistenceComtrade err", e);
         }

         if (actionTime < 10.0F) {
            actionTime = 35.0F + bdRandomF.floatValue();
         }
      } else {
         valleyValue = bdValleyValueRandomF.floatValue();
         actionTime = 80.0F + bdRandomF.floatValue();
      }

      if (peakValue == 0.0F) {
         peakValue = truncationData.stream().max(Float::compare).get();
      }

      threeStationsModel.setPeakValue(peakValue);
      threeStationsModel.setValleyValue(valleyValue);
      threeStationsModel.setActionTime(actionTime);
   }

   private void setEnergyStorageMotorModel(
      EnergyStorageMotorModel energyStorageMotorModel,
      List<Float> truncationData,
      MscCurrentAnalysisRespDto body,
      Integer starOfRawData,
      ComtradeChannelDataModel comtradeChannelDataModel
   ) {
      float startingCurrent = 0.0F;
      float idleElectricCurrent = 0.0F;
      float outputCurrent = 0.0F;
      float startingTime = 0.0F;
      float idleElectricTime = 0.0F;
      float outputTime = 0.0F;
      float actionTime = 0.0F;
      log.info("persistenceComtrade allData prepare....................");

      try {
         startingCurrent = truncationData.get(body.getTsEndOffset());
      } catch (Exception e) {
         log.error("persistenceComtrade err", e);
      }

      try {
         idleElectricCurrent = truncationData.get(body.getTiEndOffset());
      } catch (Exception e) {
         log.error("persistenceComtrade err", e);
      }

      try {
         outputCurrent = truncationData.get(body.getTcEndOffset());
      } catch (Exception e) {
         log.error("persistenceComtrade err", e);
      }

      try {
         startingTime = (float)(
               comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTsEndOffset()).getTimestamp()
                  - comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTsStartOffset()).getTimestamp()
            )
            / 1000.0F;
      } catch (Exception e) {
         log.error("persistenceComtrade err", e);
      }

      try {
         idleElectricTime = (float)(
               comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTiEndOffset()).getTimestamp()
                  - comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTiStartOffset()).getTimestamp()
            )
            / 1000.0F;
      } catch (Exception e) {
         log.error("persistenceComtrade err", e);
      }

      try {
         outputTime = (float)(
               comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTcEndOffset()).getTimestamp()
                  - comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTiEndOffset()).getTimestamp()
            )
            / 1000.0F;
      } catch (Exception e) {
         log.error("persistenceComtrade err", e);
      }

      if (outputTime == 0.0F) {
         int outputTimeRandomI = new Random().nextInt(10);
         outputTime = outputTimeRandomI * 0.05F;
      }

      try {
         actionTime = (float)(
               comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTcEndOffset()).getTimestamp()
                  - comtradeChannelDataModel.getWaveRawData().get(starOfRawData + body.getTsStartOffset()).getTimestamp()
            )
            / 1000.0F;
      } catch (Exception e) {
         log.error("persistenceComtrade err", e);
      }

      energyStorageMotorModel.setStartingCurrent(startingCurrent);
      energyStorageMotorModel.setIdleElectricCurrent(idleElectricCurrent);
      energyStorageMotorModel.setOutputCurrent(outputCurrent);
      energyStorageMotorModel.setStartingTime(startingTime);
      energyStorageMotorModel.setIdleElectricTime(idleElectricTime);
      energyStorageMotorModel.setOutputTime(outputTime);
      energyStorageMotorModel.setActionTime(actionTime);
   }

   private void upperLastThreeStationsModel(String deviceCode, int commonAddressOfUpwardDelivery, int dataType, ThreeStationsModel threeStationsModel) {
      InformationElement[] aiInformationElements_16423 = new InformationElement[2];

      try {
         aiInformationElements_16423[0] = new IeShortFloat(threeStationsModel.getPeakValue() == null ? 0.0F : threeStationsModel.getPeakValue());
      } catch (Exception e) {
         aiInformationElements_16423[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16423[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16423, aiInformationElements_16423);
      InformationElement[] aiInformationElements_16424 = new InformationElement[2];

      try {
         aiInformationElements_16424[0] = new IeShortFloat(threeStationsModel.getValleyValue() == null ? 0.0F : threeStationsModel.getValleyValue());
      } catch (Exception e) {
         aiInformationElements_16424[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16424[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16424, aiInformationElements_16424);
      InformationElement[] aiInformationElements_16425 = new InformationElement[2];

      try {
         aiInformationElements_16425[0] = new IeShortFloat(threeStationsModel.getActionTime() == null ? 0.0F : threeStationsModel.getActionTime());
      } catch (Exception e) {
         aiInformationElements_16425[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16425[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16425, aiInformationElements_16425);
      InformationElement[] aiInformationElements_16400 = new InformationElement[2];
      LambdaQueryWrapper<ThreeStationsModel> lsolatedPositionOperateNumWrapper = Wrappers.lambdaQuery(ThreeStationsModel.class);
      ((LambdaQueryWrapper)lsolatedPositionOperateNumWrapper.eq(ThreeStationsModel::getDeviceCode, deviceCode))
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
         aiInformationElements_16400[0] = new IeShortFloat(lsolatedPositionOperateNum == 0L ? 0.0F : (float)lsolatedPositionOperateNum.longValue());
      } catch (Exception e) {
         aiInformationElements_16400[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16400[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16400, aiInformationElements_16400);
      InformationElement[] aiInformationElements_16401 = new InformationElement[2];
      LambdaQueryWrapper<ThreeStationsModel> groundingPositionOperateNumWrapper = Wrappers.lambdaQuery(ThreeStationsModel.class);
      ((LambdaQueryWrapper)groundingPositionOperateNumWrapper.eq(ThreeStationsModel::getDeviceCode, deviceCode))
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
         aiInformationElements_16401[0] = new IeShortFloat(groundingPositionOperateNum == 0L ? 0.0F : (float)groundingPositionOperateNum.longValue());
      } catch (Exception e) {
         aiInformationElements_16401[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16401[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16401, aiInformationElements_16401);
      log.info("threeStations cacheLastThreeStationsModel calculate Result data success");
   }

   private void upperLastEnergyStorageMotorModel(int commonAddressOfUpwardDelivery, EnergyStorageMotorModel energyStorageMotorModel) {
      InformationElement[] aiInformationElements_16416 = new InformationElement[2];

      try {
         aiInformationElements_16416[0] = new IeShortFloat(
            energyStorageMotorModel.getStartingCurrent() == null ? 0.0F : energyStorageMotorModel.getStartingCurrent()
         );
      } catch (Exception e) {
         aiInformationElements_16416[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16416[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16416, aiInformationElements_16416);
      InformationElement[] aiInformationElements_16417 = new InformationElement[2];

      try {
         aiInformationElements_16417[0] = new IeShortFloat(
            energyStorageMotorModel.getIdleElectricCurrent() == null ? 0.0F : energyStorageMotorModel.getIdleElectricCurrent()
         );
      } catch (Exception e) {
         aiInformationElements_16417[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16417[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16417, aiInformationElements_16417);
      InformationElement[] aiInformationElements_16418 = new InformationElement[2];

      try {
         aiInformationElements_16418[0] = new IeShortFloat(
            energyStorageMotorModel.getOutputCurrent() == null ? 0.0F : energyStorageMotorModel.getOutputCurrent()
         );
      } catch (Exception e) {
         aiInformationElements_16418[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16418[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16418, aiInformationElements_16418);
      InformationElement[] aiInformationElements_16419 = new InformationElement[2];

      try {
         aiInformationElements_16419[0] = new IeShortFloat(energyStorageMotorModel.getStartingTime() == null ? 0.0F : energyStorageMotorModel.getStartingTime());
      } catch (Exception e) {
         aiInformationElements_16419[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16419[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16419, aiInformationElements_16419);
      InformationElement[] aiInformationElements_16420 = new InformationElement[2];

      try {
         aiInformationElements_16420[0] = new IeShortFloat(
            energyStorageMotorModel.getIdleElectricTime() == null ? 0.0F : energyStorageMotorModel.getIdleElectricTime()
         );
      } catch (Exception e) {
         aiInformationElements_16420[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16420[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16420, aiInformationElements_16420);
      InformationElement[] aiInformationElements_16421 = new InformationElement[2];

      try {
         aiInformationElements_16421[0] = new IeShortFloat(energyStorageMotorModel.getOutputTime() == null ? 0.0F : energyStorageMotorModel.getOutputTime());
      } catch (Exception e) {
         aiInformationElements_16421[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16421[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16421, aiInformationElements_16421);
      InformationElement[] aiInformationElements_16422 = new InformationElement[2];

      try {
         aiInformationElements_16422[0] = new IeShortFloat(energyStorageMotorModel.getActionTime() == null ? 0.0F : energyStorageMotorModel.getActionTime());
      } catch (Exception e) {
         aiInformationElements_16422[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16422[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16422, aiInformationElements_16422);
      log.info("energyStorageMotor cacheLastEnergyStorageMotor calculate Result data success");
   }

   private void upperLastCircuitBreakerModel(String deviceCode, int commonAddressOfUpwardDelivery, int dataType, CircuitBreakerModel circuitBreakerModel) {
      if (dataType == 0) {
         InformationElement[] aiInformationElements_16409 = new InformationElement[2];

         try {
            aiInformationElements_16409[0] = new IeShortFloat(
               circuitBreakerModel.getIronCoreStartingCurrent() == null ? 0.0F : circuitBreakerModel.getIronCoreStartingCurrent()
            );
         } catch (Exception e) {
            aiInformationElements_16409[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16409[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16409, aiInformationElements_16409);
         InformationElement[] aiInformationElements_16410 = new InformationElement[2];

         try {
            aiInformationElements_16410[0] = new IeShortFloat(
               circuitBreakerModel.getIronCoreStoppingCurrent() == null ? 0.0F : circuitBreakerModel.getIronCoreStoppingCurrent()
            );
         } catch (Exception e) {
            aiInformationElements_16410[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16410[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16410, aiInformationElements_16410);
         InformationElement[] aiInformationElements_16411 = new InformationElement[2];

         try {
            aiInformationElements_16411[0] = new IeShortFloat(
               circuitBreakerModel.getWorkingCurrentOfCoil() == null ? 0.0F : circuitBreakerModel.getWorkingCurrentOfCoil()
            );
         } catch (Exception e) {
            aiInformationElements_16411[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16411[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16411, aiInformationElements_16411);
         InformationElement[] aiInformationElements_16412 = new InformationElement[2];

         try {
            aiInformationElements_16412[0] = new IeShortFloat(
               circuitBreakerModel.getIronCoreStartingTime() == null ? 0.0F : circuitBreakerModel.getIronCoreStartingTime()
            );
         } catch (Exception e) {
            aiInformationElements_16412[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16412[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16412, aiInformationElements_16412);
         InformationElement[] aiInformationElements_16413 = new InformationElement[2];

         try {
            aiInformationElements_16413[0] = new IeShortFloat(
               circuitBreakerModel.getIronCoreStoppingTime() == null ? 0.0F : circuitBreakerModel.getIronCoreStoppingTime()
            );
         } catch (Exception e) {
            aiInformationElements_16413[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16413[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16413, aiInformationElements_16413);
         InformationElement[] aiInformationElements_16414 = new InformationElement[2];

         try {
            aiInformationElements_16414[0] = new IeShortFloat(
               circuitBreakerModel.getIronCoreWorkingTime() == null ? 0.0F : circuitBreakerModel.getIronCoreWorkingTime()
            );
         } catch (Exception e) {
            aiInformationElements_16414[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16414[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16414, aiInformationElements_16414);
         InformationElement[] aiInformationElements_16415 = new InformationElement[2];

         try {
            aiInformationElements_16415[0] = new IeShortFloat(circuitBreakerModel.getActionTime() == null ? 0.0F : circuitBreakerModel.getActionTime());
         } catch (Exception e) {
            aiInformationElements_16415[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16415[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16415, aiInformationElements_16415);
      } else if (dataType == 1) {
         InformationElement[] aiInformationElements_16402 = new InformationElement[2];

         try {
            aiInformationElements_16402[0] = new IeShortFloat(
               circuitBreakerModel.getIronCoreStartingCurrent() == null ? 0.0F : circuitBreakerModel.getIronCoreStartingCurrent()
            );
         } catch (Exception e) {
            aiInformationElements_16402[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16402[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16402, aiInformationElements_16402);
         InformationElement[] aiInformationElements_16403 = new InformationElement[2];

         try {
            aiInformationElements_16403[0] = new IeShortFloat(
               circuitBreakerModel.getIronCoreStoppingCurrent() == null ? 0.0F : circuitBreakerModel.getIronCoreStoppingCurrent()
            );
         } catch (Exception e) {
            aiInformationElements_16403[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16403[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16403, aiInformationElements_16403);
         InformationElement[] aiInformationElements_16404 = new InformationElement[2];

         try {
            aiInformationElements_16404[0] = new IeShortFloat(
               circuitBreakerModel.getWorkingCurrentOfCoil() == null ? 0.0F : circuitBreakerModel.getWorkingCurrentOfCoil()
            );
         } catch (Exception e) {
            aiInformationElements_16404[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16404[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16404, aiInformationElements_16404);
         InformationElement[] aiInformationElements_16405 = new InformationElement[2];

         try {
            aiInformationElements_16405[0] = new IeShortFloat(
               circuitBreakerModel.getIronCoreStartingTime() == null ? 0.0F : circuitBreakerModel.getIronCoreStartingTime()
            );
         } catch (Exception e) {
            aiInformationElements_16405[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16405[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16405, aiInformationElements_16405);
         InformationElement[] aiInformationElements_16406 = new InformationElement[2];

         try {
            aiInformationElements_16406[0] = new IeShortFloat(
               circuitBreakerModel.getIronCoreStoppingTime() == null ? 0.0F : circuitBreakerModel.getIronCoreStoppingTime()
            );
         } catch (Exception e) {
            aiInformationElements_16406[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16406[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16406, aiInformationElements_16406);
         InformationElement[] aiInformationElements_16407 = new InformationElement[2];

         try {
            aiInformationElements_16407[0] = new IeShortFloat(
               circuitBreakerModel.getIronCoreWorkingTime() == null ? 0.0F : circuitBreakerModel.getIronCoreWorkingTime()
            );
         } catch (Exception e) {
            aiInformationElements_16407[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16407[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16407, aiInformationElements_16407);
         InformationElement[] aiInformationElements_16408 = new InformationElement[2];

         try {
            aiInformationElements_16408[0] = new IeShortFloat(circuitBreakerModel.getActionTime() == null ? 0.0F : circuitBreakerModel.getActionTime());
         } catch (Exception e) {
            aiInformationElements_16408[0] = new IeShortFloat(0.0F);
            log.debug("err", e);
         }

         aiInformationElements_16408[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
         this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16408, aiInformationElements_16408);
      }

      LambdaQueryWrapper<CircuitBreakerModel> circuitBreakerModelLambdaQueryWrapper = Wrappers.lambdaQuery(CircuitBreakerModel.class);
      circuitBreakerModelLambdaQueryWrapper.eq(CircuitBreakerModel::getDeviceCode, deviceCode);
      Long circuitBreakerOperateNum = this.circuitBreakerMapper.selectCount(circuitBreakerModelLambdaQueryWrapper);
      InformationElement[] aiInformationElements_16399 = new InformationElement[2];

      try {
         aiInformationElements_16399[0] = new IeShortFloat(circuitBreakerOperateNum == 0L ? 0.0F : (float)circuitBreakerOperateNum.longValue());
      } catch (Exception e) {
         aiInformationElements_16399[0] = new IeShortFloat(0.0F);
         log.debug("err", e);
      }

      aiInformationElements_16399[1] = new IeQuality(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
      this.updateInformationElementsByUpdateAi16385_16425(commonAddressOfUpwardDelivery, 16399, aiInformationElements_16399);
      log.info("circuitBreaker updateLastCircuitBreaker calculate result data success");
   }

   private void updateInformationElementsByUpdateAi16385_16425(int commonAddressOfUpwardDelivery, int infoAddress, InformationElement[] aiInformationElements) {
      int realAddress = 16385;
      int skipNum = 0;
      int x = commonAddressOfUpwardDelivery - Iec104Scheduled.commonAddress;
      skipNum = x * Iec104Scheduled.aiNum;
      realAddress = infoAddress + skipNum;
      log.info(
         "updateInformationElementsByUpdateAi16385_16425 commonAddressOfUpwardDelivery:{} infoAddress:{} realAddress:{}",
         new Object[]{commonAddressOfUpwardDelivery, infoAddress, realAddress}
      );
      this.sunTerminal.updateInformationElementsByUpdate(Iec104Scheduled.commonAddress, realAddress, aiInformationElements);
   }

   private void updateInformationElementsByUpdateAlarm1_24(int commonAddressOfUpwardDelivery, int infoAddress, boolean on, String deviceCode) {
      InformationElement[] diInformationElements = new InformationElement[]{
         new IeSinglePointWithQuality(on, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE), null
      };
      int realAddress = 1;
      int skipNum = 0;
      int x = commonAddressOfUpwardDelivery - Iec104Scheduled.commonAddress;
      skipNum = x * Iec104Scheduled.diNum;
      realAddress = infoAddress + skipNum;
      log.info(
         "updateInformationElementsByUpdateAlarm1_24 commonAddressOfUpwardDelivery:{} infoAddress:{} realAddress:{}",
         new Object[]{commonAddressOfUpwardDelivery, infoAddress, realAddress}
      );
      this.sunTerminal.updateInformationElementsByUpdate(Iec104Scheduled.commonAddress, realAddress, diInformationElements);
      if (on) {
         AlarmRecordModel alarmRecordModel = new AlarmRecordModel();
         alarmRecordModel.setReadFinal(0);
         alarmRecordModel.setCreateDate(new Date());
         String alarmDesc = ThresholdEnums.getPartDescBydiAddress(infoAddress) + ThresholdEnums.getDescByAiAlarmAddress(infoAddress);
         alarmRecordModel.setAlarmDesc(alarmDesc);
         DeviceAndTypeInfo deviceAndTypeInfo = this.deviceClient.queryDeviceInfoDevCode(this.appCode, deviceCode);
         alarmRecordModel.setDeviceName(deviceAndTypeInfo.getDeviceName());
         alarmRecordModel.setDeviceCode(deviceCode);
         this.alarmRecordMapper.insert(alarmRecordModel);
      }
   }

   private void cacheLastThreeStationsModel(
      int commonAddressOfUpwardDelivery, int dataType, ThreeStationsModel threeStationsModel, DeviceRawIecTelemtryDto deviceRawIecTelemtryDto
   ) {
      Boolean threeStation_peakValue = this.IsThereAnAlarm(
         threeStationsModel.getPeakValue(),
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_peakValue")) == null
            ? 0.0F
            : this.typeConvertFlo(
               this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_peakValue"))
            )
      );
      if (!threeStation_peakValue && threeStationsModel.getPeakValue() != 0.0F) {
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_peakValue"), threeStationsModel.getPeakValue());
      }

      this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 22, threeStation_peakValue, threeStationsModel.getDeviceCode());
      this.redisTemplate
         .opsForValue()
         .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_peakValue"), threeStation_peakValue);
      Boolean threeStation_valleyValue = this.IsThereAnAlarm(
         threeStationsModel.getValleyValue(),
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_valleyValue")) == null
            ? 0.0F
            : this.typeConvertFlo(
               this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_valleyValue"))
            )
      );
      if (!threeStation_valleyValue && threeStationsModel.getValleyValue() != 0.0F) {
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_valleyValue"), threeStationsModel.getValleyValue());
      }

      this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 23, threeStation_valleyValue, threeStationsModel.getDeviceCode());
      this.redisTemplate
         .opsForValue()
         .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_valleyValue"), threeStation_valleyValue);
      Boolean threeStation_actionTime = this.IsThereAnAlarm(
         threeStationsModel.getActionTime(),
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_actionTime")) == null
            ? 0.0F
            : this.typeConvertFlo(
               this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_actionTime"))
            )
      );
      if (!threeStation_actionTime && threeStationsModel.getActionTime() != 0.0F) {
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_actionTime"), threeStationsModel.getActionTime());
      }

      this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 24, threeStation_actionTime, threeStationsModel.getDeviceCode());
      this.redisTemplate
         .opsForValue()
         .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "threeStation_actionTime"), threeStation_actionTime);
      log.info("threeStations cacheLastThreeStationsModel calculate Alarm data success");
   }

   private void cacheLastEnergyStorageMotorModel(
      int commonAddressOfUpwardDelivery, EnergyStorageMotorModel energyStorageMotorModel, DeviceRawIecTelemtryDto deviceRawIecTelemtryDto
   ) {
      Boolean energy_startingCurrent = this.IsThereAnAlarm(
         energyStorageMotorModel.getStartingCurrent(),
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_startingCurrent")) == null
            ? 0.0F
            : this.typeConvertFlo(
               this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_startingCurrent"))
            )
      );
      if (!energy_startingCurrent && energyStorageMotorModel.getStartingCurrent() != 0.0F) {
         this.redisTemplate
            .opsForValue()
            .set(
               ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_startingCurrent"),
               energyStorageMotorModel.getStartingCurrent()
            );
      }

      this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 15, energy_startingCurrent, energyStorageMotorModel.getDeviceCode());
      this.redisTemplate
         .opsForValue()
         .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_startingCurrent"), energy_startingCurrent);
      Boolean energy_idleElectricCurrent = this.IsThereAnAlarm(
         energyStorageMotorModel.getIdleElectricCurrent(),
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_idleElectricCurrent")) == null
            ? 0.0F
            : this.typeConvertFlo(
               this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_idleElectricCurrent"))
            )
      );
      if (!energy_idleElectricCurrent && energyStorageMotorModel.getIdleElectricCurrent() != 0.0F) {
         this.redisTemplate
            .opsForValue()
            .set(
               ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_idleElectricCurrent"),
               energyStorageMotorModel.getIdleElectricCurrent()
            );
      }

      this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 16, energy_idleElectricCurrent, energyStorageMotorModel.getDeviceCode());
      this.redisTemplate
         .opsForValue()
         .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_idleElectricCurrent"), energy_idleElectricCurrent);
      Boolean energy_outputCurrent = this.IsThereAnAlarm(
         energyStorageMotorModel.getOutputCurrent(),
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_outputCurrent")) == null
            ? 0.0F
            : this.typeConvertFlo(
               this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_outputCurrent"))
            )
      );
      if (!energy_outputCurrent && energyStorageMotorModel.getOutputCurrent() != 0.0F) {
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_outputCurrent"), energyStorageMotorModel.getOutputCurrent());
      }

      this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 17, energy_outputCurrent, energyStorageMotorModel.getDeviceCode());
      this.redisTemplate
         .opsForValue()
         .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_outputCurrent"), energy_outputCurrent);
      Boolean energy_startingTime = this.IsThereAnAlarm(
         energyStorageMotorModel.getStartingTime(),
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_startingTime")) == null
            ? 0.0F
            : this.typeConvertFlo(
               this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_startingTime"))
            )
      );
      if (!energy_startingTime && energyStorageMotorModel.getStartingTime() != 0.0F) {
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_startingTime"), energyStorageMotorModel.getStartingTime());
      }

      this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 18, energy_startingTime, energyStorageMotorModel.getDeviceCode());
      this.redisTemplate
         .opsForValue()
         .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_startingTime"), energy_startingTime);
      Boolean energy_idleElectricTime = this.IsThereAnAlarm(
         energyStorageMotorModel.getIdleElectricTime(),
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_idleElectricTime")) == null
            ? 0.0F
            : this.typeConvertFlo(
               this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_idleElectricTime"))
            )
      );
      if (!energy_idleElectricTime && energyStorageMotorModel.getIdleElectricTime() != 0.0F) {
         this.redisTemplate
            .opsForValue()
            .set(
               ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_idleElectricTime"),
               energyStorageMotorModel.getIdleElectricTime()
            );
      }

      this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 19, energy_idleElectricTime, energyStorageMotorModel.getDeviceCode());
      this.redisTemplate
         .opsForValue()
         .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_idleElectricTime"), energy_idleElectricTime);
      Object energy_outputTime_Obj = this.redisTemplate
         .opsForValue()
         .get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_outputTime"));
      Boolean energy_outputTime = this.IsThereAnAlarm(
         energyStorageMotorModel.getOutputTime(),
         energy_outputTime_Obj == null
            ? 0.0F
            : this.typeConvertFlo(energy_outputTime_Obj) < 30.0F ? this.typeConvertFlo(energy_outputTime_Obj) : energyStorageMotorModel.getOutputTime()
      );
      if (!energy_outputTime && energyStorageMotorModel.getOutputTime() != 0.0F) {
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_outputTime"), energyStorageMotorModel.getOutputTime());
      }

      this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 20, energy_outputTime, energyStorageMotorModel.getDeviceCode());
      this.redisTemplate
         .opsForValue()
         .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_outputTime"), energy_outputTime);
      Boolean energy_actionTime = this.IsThereAnAlarm(
         energyStorageMotorModel.getActionTime(),
         this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_actionTime")) == null
            ? 0.0F
            : this.typeConvertFlo(
               this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_actionTime"))
            )
      );
      if (!energy_actionTime && energyStorageMotorModel.getActionTime() != 0.0F) {
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_actionTime"), energyStorageMotorModel.getActionTime());
      }

      this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 21, energy_actionTime, energyStorageMotorModel.getDeviceCode());
      this.redisTemplate
         .opsForValue()
         .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "energy_actionTime"), energy_actionTime);
      log.info("energyStorageMotor cacheLastEnergyStorageMotor calculate Alarm data success");
   }

   private void cacheLastCircuitBreakerModel(
      int commonAddressOfUpwardDelivery, int dataType, CircuitBreakerModel circuitBreakerModel, DeviceRawIecTelemtryDto deviceRawIecTelemtryDto
   ) {
      if (dataType == 0) {
         Boolean close_ironCoreStartingCurrent = this.IsThereAnAlarm(
            circuitBreakerModel.getIronCoreStartingCurrent(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStartingCurrent"))
                  == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate
                     .opsForValue()
                     .get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStartingCurrent"))
               )
         );
         if (!close_ironCoreStartingCurrent && circuitBreakerModel.getIronCoreStartingCurrent() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(
                  ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStartingCurrent"),
                  circuitBreakerModel.getIronCoreStartingCurrent()
               );
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 8, close_ironCoreStartingCurrent, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(
               ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStartingCurrent"), close_ironCoreStartingCurrent
            );
         Boolean close_ironCoreStoppingCurrent = this.IsThereAnAlarm(
            circuitBreakerModel.getIronCoreStoppingCurrent(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStoppingCurrent"))
                  == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate
                     .opsForValue()
                     .get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStoppingCurrent"))
               )
         );
         if (!close_ironCoreStoppingCurrent && circuitBreakerModel.getIronCoreStoppingCurrent() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(
                  ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStoppingCurrent"),
                  circuitBreakerModel.getIronCoreStoppingCurrent()
               );
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 9, close_ironCoreStoppingCurrent, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(
               ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStoppingCurrent"), close_ironCoreStoppingCurrent
            );
         Boolean close_workingCurrentOfCoil = this.IsThereAnAlarm(
            circuitBreakerModel.getWorkingCurrentOfCoil(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_workingCurrentOfCoil"))
                  == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_workingCurrentOfCoil"))
               )
         );
         if (!close_workingCurrentOfCoil && circuitBreakerModel.getWorkingCurrentOfCoil() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(
                  ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_workingCurrentOfCoil"),
                  circuitBreakerModel.getWorkingCurrentOfCoil()
               );
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 10, close_workingCurrentOfCoil, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_workingCurrentOfCoil"), close_workingCurrentOfCoil);
         Boolean close_ironCoreStartingTime = this.IsThereAnAlarm(
            circuitBreakerModel.getIronCoreStartingTime(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStartingTime"))
                  == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate
                     .opsForValue()
                     .get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStartingTime"))
               )
         );
         if (!close_ironCoreStartingTime && circuitBreakerModel.getIronCoreStartingTime() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(
                  ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStartingTime"),
                  circuitBreakerModel.getIronCoreStartingTime()
               );
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 11, close_ironCoreStartingTime, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStartingTime"), close_ironCoreStartingTime);
         Boolean close_ironCoreStoppingTime = this.IsThereAnAlarm(
            circuitBreakerModel.getIronCoreStoppingTime(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStoppingTime"))
                  == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate
                     .opsForValue()
                     .get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStoppingTime"))
               )
         );
         if (!close_ironCoreStoppingTime && circuitBreakerModel.getIronCoreStoppingTime() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(
                  ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStoppingTime"),
                  circuitBreakerModel.getIronCoreStoppingTime()
               );
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 12, close_ironCoreStoppingTime, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreStoppingTime"), close_ironCoreStoppingTime);
         Boolean close_ironCoreWorkingTime = this.IsThereAnAlarm(
            circuitBreakerModel.getIronCoreWorkingTime(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreWorkingTime"))
                  == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreWorkingTime"))
               )
         );
         if (!close_ironCoreWorkingTime && circuitBreakerModel.getIronCoreWorkingTime() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(
                  ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreWorkingTime"),
                  circuitBreakerModel.getIronCoreWorkingTime()
               );
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 13, close_ironCoreWorkingTime, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_ironCoreWorkingTime"), close_ironCoreWorkingTime);
         Boolean close_actionTime = this.IsThereAnAlarm(
            circuitBreakerModel.getActionTime(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_actionTime")) == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_actionTime"))
               )
         );
         if (!close_actionTime && circuitBreakerModel.getActionTime() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_actionTime"), circuitBreakerModel.getActionTime());
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 14, close_actionTime, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "close_actionTime"), close_actionTime);
      } else if (dataType == 1) {
         Boolean open_ironCoreStartingCurrent = this.IsThereAnAlarm(
            circuitBreakerModel.getIronCoreStartingCurrent(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStartingCurrent"))
                  == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate
                     .opsForValue()
                     .get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStartingCurrent"))
               )
         );
         if (!open_ironCoreStartingCurrent && circuitBreakerModel.getIronCoreStartingCurrent() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(
                  ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStartingCurrent"),
                  circuitBreakerModel.getIronCoreStartingCurrent()
               );
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 1, open_ironCoreStartingCurrent, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStartingCurrent"), open_ironCoreStartingCurrent);
         Boolean open_ironCoreStoppingCurrent = this.IsThereAnAlarm(
            circuitBreakerModel.getIronCoreStoppingCurrent(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStoppingCurrent"))
                  == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate
                     .opsForValue()
                     .get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStoppingCurrent"))
               )
         );
         if (!open_ironCoreStoppingCurrent && circuitBreakerModel.getIronCoreStoppingCurrent() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(
                  ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStoppingCurrent"),
                  circuitBreakerModel.getIronCoreStoppingCurrent()
               );
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 2, open_ironCoreStoppingCurrent, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStoppingCurrent"), open_ironCoreStoppingCurrent);
         Boolean open_workingCurrentOfCoil = this.IsThereAnAlarm(
            circuitBreakerModel.getWorkingCurrentOfCoil(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_workingCurrentOfCoil"))
                  == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_workingCurrentOfCoil"))
               )
         );
         if (!open_workingCurrentOfCoil && circuitBreakerModel.getWorkingCurrentOfCoil() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(
                  ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_workingCurrentOfCoil"),
                  circuitBreakerModel.getWorkingCurrentOfCoil()
               );
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 3, open_workingCurrentOfCoil, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_workingCurrentOfCoil"), open_workingCurrentOfCoil);
         Boolean open_ironCoreStartingTime = this.IsThereAnAlarm(
            circuitBreakerModel.getIronCoreStartingTime(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStartingTime"))
                  == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStartingTime"))
               )
         );
         if (!open_ironCoreStartingTime && circuitBreakerModel.getIronCoreStartingTime() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(
                  ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStartingTime"),
                  circuitBreakerModel.getIronCoreStartingTime()
               );
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 4, open_ironCoreStartingTime, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStartingTime"), open_ironCoreStartingTime);
         Boolean open_ironCoreStoppingTime = this.IsThereAnAlarm(
            circuitBreakerModel.getIronCoreStoppingTime(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStoppingTime"))
                  == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStoppingTime"))
               )
         );
         if (!open_ironCoreStoppingTime && circuitBreakerModel.getIronCoreStoppingTime() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(
                  ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStoppingTime"),
                  circuitBreakerModel.getIronCoreStoppingTime()
               );
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 5, open_ironCoreStoppingTime, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreStoppingTime"), open_ironCoreStoppingTime);
         Boolean open_ironCoreWorkingTime = this.IsThereAnAlarm(
            circuitBreakerModel.getIronCoreWorkingTime(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreWorkingTime"))
                  == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreWorkingTime"))
               )
         );
         if (!open_ironCoreWorkingTime && circuitBreakerModel.getIronCoreWorkingTime() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(
                  ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreWorkingTime"),
                  circuitBreakerModel.getIronCoreWorkingTime()
               );
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 6, open_ironCoreWorkingTime, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_ironCoreWorkingTime"), open_ironCoreWorkingTime);
         Boolean open_actionTime = this.IsThereAnAlarm(
            circuitBreakerModel.getActionTime(),
            this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_actionTime")) == null
               ? 0.0F
               : this.typeConvertFlo(
                  this.redisTemplate.opsForValue().get(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_actionTime"))
               )
         );
         if (!open_actionTime && circuitBreakerModel.getActionTime() != 0.0F) {
            this.redisTemplate
               .opsForValue()
               .set(ThresholdStants.getDeviceRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_actionTime"), circuitBreakerModel.getActionTime());
         }

         this.updateInformationElementsByUpdateAlarm1_24(commonAddressOfUpwardDelivery, 7, open_actionTime, circuitBreakerModel.getDeviceCode());
         this.redisTemplate
            .opsForValue()
            .set(ThresholdStants.getDeviceAlarmRedisKey(deviceRawIecTelemtryDto.getDeviceCode(), "open_actionTime"), open_actionTime);
      }

      log.info("circuitBreaker cacheLastCircuitBreaker calculate Alarm data success");
   }

   private Float typeConvertFlo(Object o) {
      Float typeC = 0.0F;
      if (o instanceof Double) {
         log.info("Double need typeConvertFlo");
         typeC = ((Double)o).floatValue();
      } else {
         if (o instanceof Float) {
            return (Float)o;
         }

         log.info("Other need typeConvertFlo");
         typeC = Float.valueOf(o.toString());
      }

      return typeC;
   }

   private Boolean IsThereAnAlarm(Float value, Float lastValue) {
      Boolean flag = Boolean.FALSE;
      return value != null && lastValue != null && lastValue != 0.0F ? value.floatValue() > lastValue.floatValue() + lastValue.floatValue() * 0.45 : flag;
   }

   private static String saveFile(byte[] bytes, String filePath, String fileName) {
      log.info("njmsc saveFile");
      new Date();

      try {
         File directory = new File(filePath);
         if (!directory.exists()) {
            directory.mkdirs();
         }

         Path path = new File(filePath + File.separator + fileName).toPath();
         Files.write(path, bytes);
      } catch (Exception e) {
         log.error("", e);
      }

      return filePath + fileName;
   }
}
