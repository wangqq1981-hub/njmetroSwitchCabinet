package com.drsi.njmsc.dto.dto;

import com.drsi.njmsc.dto.model.ComtradeCfgModel;
import com.drsi.njmsc.dto.model.ComtradeChannelDataModel;
import com.drsi.njmsc.dto.model.RawIecRemoteSignalingModel;
import com.drsi.njmsc.dto.model.RawIecTelemetryModel;
import com.drsi.njmsc.handle.NjmscIec104MutationHandle;
import com.drsi.njmsc.scheduled.Iec104ClientConnectionEventListener;
import com.dsri.iec104.ies.SunStation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import org.openmuc.j60870.Connection;

@JsonInclude(Include.NON_NULL)
public class DeviceRawIecTelemtryDto {
   private Connection connection;
   private Iec104ClientConnectionEventListener iec104ClientConnectionEventListener;
   private NjmscIec104MutationHandle njmscIec104MutationHandle;
   private Map<String, CountDownLatch> endOfInterrogationCD;
   private Map<String, SunStation> endOfInterrogationSS;
   private AtomicBoolean connectionClosedFlag;
   private Map<String, ComtradeCfgModel> mapComtradeCfgModels = new HashMap<>();
   private Map<String, List<ComtradeChannelDataModel>> mapComtradeChannelDataModels = new HashMap<>();
   @ApiModelProperty("设备类型")
   private String deviceType;
   @ApiModelProperty("设备编码")
   private String deviceCode;
   @ApiModelProperty("ip")
   private String ip;
   @ApiModelProperty("port")
   private Integer port;
   @ApiModelProperty("port")
   private Integer commonAddressOfUpwardDelivery;
   private RawIecTelemetryModel rawIecTelemetryModel;
   private RawIecRemoteSignalingModel rawIecRemoteSignalingModel;
   @ApiModelProperty("开关合位")
   private Integer switchClosedPosition;
   @ApiModelProperty("开关分位")
   private Integer switchDivision;
   @ApiModelProperty("三工位隔刀合位")
   private Integer threePositionKnifeClosingPosition;
   @ApiModelProperty("三工位地刀合位")
   private Integer threeStationKnifeClosingPosition;
   private Integer subsectionIsolationThQuarantineClosing;
   private Integer subsectionIsolationThQuarantineMiddle;
   private Integer subsectionIsolationThQuarantineOpening;
   private Integer openingCoilCurrentStart;
   private Integer startOfClosingCoilCurrent;
   private Integer energyStorageMotorCurrentStarting;
   private Integer threeStationOneCurrentStart;
   private Integer threeStationTwoCurrentStart;
   private Integer waveRecordingStart;
   private Boolean waveRecordingFinishFlag;
   private Boolean waveRecordingHandleFlag = false;

   public Connection getConnection() {
      return this.connection;
   }

   public Iec104ClientConnectionEventListener getIec104ClientConnectionEventListener() {
      return this.iec104ClientConnectionEventListener;
   }

   public NjmscIec104MutationHandle getNjmscIec104MutationHandle() {
      return this.njmscIec104MutationHandle;
   }

   public Map<String, CountDownLatch> getEndOfInterrogationCD() {
      return this.endOfInterrogationCD;
   }

   public Map<String, SunStation> getEndOfInterrogationSS() {
      return this.endOfInterrogationSS;
   }

   public AtomicBoolean getConnectionClosedFlag() {
      return this.connectionClosedFlag;
   }

   public Map<String, ComtradeCfgModel> getMapComtradeCfgModels() {
      return this.mapComtradeCfgModels;
   }

   public Map<String, List<ComtradeChannelDataModel>> getMapComtradeChannelDataModels() {
      return this.mapComtradeChannelDataModels;
   }

   public String getDeviceType() {
      return this.deviceType;
   }

   public String getDeviceCode() {
      return this.deviceCode;
   }

   public String getIp() {
      return this.ip;
   }

   public Integer getPort() {
      return this.port;
   }

   public Integer getCommonAddressOfUpwardDelivery() {
      return this.commonAddressOfUpwardDelivery;
   }

   public RawIecTelemetryModel getRawIecTelemetryModel() {
      return this.rawIecTelemetryModel;
   }

   public RawIecRemoteSignalingModel getRawIecRemoteSignalingModel() {
      return this.rawIecRemoteSignalingModel;
   }

   public Integer getSwitchClosedPosition() {
      return this.switchClosedPosition;
   }

   public Integer getSwitchDivision() {
      return this.switchDivision;
   }

   public Integer getThreePositionKnifeClosingPosition() {
      return this.threePositionKnifeClosingPosition;
   }

   public Integer getThreeStationKnifeClosingPosition() {
      return this.threeStationKnifeClosingPosition;
   }

   public Integer getSubsectionIsolationThQuarantineClosing() {
      return this.subsectionIsolationThQuarantineClosing;
   }

   public Integer getSubsectionIsolationThQuarantineMiddle() {
      return this.subsectionIsolationThQuarantineMiddle;
   }

   public Integer getSubsectionIsolationThQuarantineOpening() {
      return this.subsectionIsolationThQuarantineOpening;
   }

   public Integer getOpeningCoilCurrentStart() {
      return this.openingCoilCurrentStart;
   }

   public Integer getStartOfClosingCoilCurrent() {
      return this.startOfClosingCoilCurrent;
   }

   public Integer getEnergyStorageMotorCurrentStarting() {
      return this.energyStorageMotorCurrentStarting;
   }

   public Integer getThreeStationOneCurrentStart() {
      return this.threeStationOneCurrentStart;
   }

   public Integer getThreeStationTwoCurrentStart() {
      return this.threeStationTwoCurrentStart;
   }

   public Integer getWaveRecordingStart() {
      return this.waveRecordingStart;
   }

   public Boolean getWaveRecordingFinishFlag() {
      return this.waveRecordingFinishFlag;
   }

   public Boolean getWaveRecordingHandleFlag() {
      return this.waveRecordingHandleFlag;
   }

   public DeviceRawIecTelemtryDto setConnection(final Connection connection) {
      this.connection = connection;
      return this;
   }

   public DeviceRawIecTelemtryDto setIec104ClientConnectionEventListener(final Iec104ClientConnectionEventListener iec104ClientConnectionEventListener) {
      this.iec104ClientConnectionEventListener = iec104ClientConnectionEventListener;
      return this;
   }

   public DeviceRawIecTelemtryDto setNjmscIec104MutationHandle(final NjmscIec104MutationHandle njmscIec104MutationHandle) {
      this.njmscIec104MutationHandle = njmscIec104MutationHandle;
      return this;
   }

   public DeviceRawIecTelemtryDto setEndOfInterrogationCD(final Map<String, CountDownLatch> endOfInterrogationCD) {
      this.endOfInterrogationCD = endOfInterrogationCD;
      return this;
   }

   public DeviceRawIecTelemtryDto setEndOfInterrogationSS(final Map<String, SunStation> endOfInterrogationSS) {
      this.endOfInterrogationSS = endOfInterrogationSS;
      return this;
   }

   public DeviceRawIecTelemtryDto setConnectionClosedFlag(final AtomicBoolean connectionClosedFlag) {
      this.connectionClosedFlag = connectionClosedFlag;
      return this;
   }

   public DeviceRawIecTelemtryDto setMapComtradeCfgModels(final Map<String, ComtradeCfgModel> mapComtradeCfgModels) {
      this.mapComtradeCfgModels = mapComtradeCfgModels;
      return this;
   }

   public DeviceRawIecTelemtryDto setMapComtradeChannelDataModels(final Map<String, List<ComtradeChannelDataModel>> mapComtradeChannelDataModels) {
      this.mapComtradeChannelDataModels = mapComtradeChannelDataModels;
      return this;
   }

   public DeviceRawIecTelemtryDto setDeviceType(final String deviceType) {
      this.deviceType = deviceType;
      return this;
   }

   public DeviceRawIecTelemtryDto setDeviceCode(final String deviceCode) {
      this.deviceCode = deviceCode;
      return this;
   }

   public DeviceRawIecTelemtryDto setIp(final String ip) {
      this.ip = ip;
      return this;
   }

   public DeviceRawIecTelemtryDto setPort(final Integer port) {
      this.port = port;
      return this;
   }

   public DeviceRawIecTelemtryDto setCommonAddressOfUpwardDelivery(final Integer commonAddressOfUpwardDelivery) {
      this.commonAddressOfUpwardDelivery = commonAddressOfUpwardDelivery;
      return this;
   }

   public DeviceRawIecTelemtryDto setRawIecTelemetryModel(final RawIecTelemetryModel rawIecTelemetryModel) {
      this.rawIecTelemetryModel = rawIecTelemetryModel;
      return this;
   }

   public DeviceRawIecTelemtryDto setRawIecRemoteSignalingModel(final RawIecRemoteSignalingModel rawIecRemoteSignalingModel) {
      this.rawIecRemoteSignalingModel = rawIecRemoteSignalingModel;
      return this;
   }

   public DeviceRawIecTelemtryDto setSwitchClosedPosition(final Integer switchClosedPosition) {
      this.switchClosedPosition = switchClosedPosition;
      return this;
   }

   public DeviceRawIecTelemtryDto setSwitchDivision(final Integer switchDivision) {
      this.switchDivision = switchDivision;
      return this;
   }

   public DeviceRawIecTelemtryDto setThreePositionKnifeClosingPosition(final Integer threePositionKnifeClosingPosition) {
      this.threePositionKnifeClosingPosition = threePositionKnifeClosingPosition;
      return this;
   }

   public DeviceRawIecTelemtryDto setThreeStationKnifeClosingPosition(final Integer threeStationKnifeClosingPosition) {
      this.threeStationKnifeClosingPosition = threeStationKnifeClosingPosition;
      return this;
   }

   public DeviceRawIecTelemtryDto setSubsectionIsolationThQuarantineClosing(final Integer subsectionIsolationThQuarantineClosing) {
      this.subsectionIsolationThQuarantineClosing = subsectionIsolationThQuarantineClosing;
      return this;
   }

   public DeviceRawIecTelemtryDto setSubsectionIsolationThQuarantineMiddle(final Integer subsectionIsolationThQuarantineMiddle) {
      this.subsectionIsolationThQuarantineMiddle = subsectionIsolationThQuarantineMiddle;
      return this;
   }

   public DeviceRawIecTelemtryDto setSubsectionIsolationThQuarantineOpening(final Integer subsectionIsolationThQuarantineOpening) {
      this.subsectionIsolationThQuarantineOpening = subsectionIsolationThQuarantineOpening;
      return this;
   }

   public DeviceRawIecTelemtryDto setOpeningCoilCurrentStart(final Integer openingCoilCurrentStart) {
      this.openingCoilCurrentStart = openingCoilCurrentStart;
      return this;
   }

   public DeviceRawIecTelemtryDto setStartOfClosingCoilCurrent(final Integer startOfClosingCoilCurrent) {
      this.startOfClosingCoilCurrent = startOfClosingCoilCurrent;
      return this;
   }

   public DeviceRawIecTelemtryDto setEnergyStorageMotorCurrentStarting(final Integer energyStorageMotorCurrentStarting) {
      this.energyStorageMotorCurrentStarting = energyStorageMotorCurrentStarting;
      return this;
   }

   public DeviceRawIecTelemtryDto setThreeStationOneCurrentStart(final Integer threeStationOneCurrentStart) {
      this.threeStationOneCurrentStart = threeStationOneCurrentStart;
      return this;
   }

   public DeviceRawIecTelemtryDto setThreeStationTwoCurrentStart(final Integer threeStationTwoCurrentStart) {
      this.threeStationTwoCurrentStart = threeStationTwoCurrentStart;
      return this;
   }

   public DeviceRawIecTelemtryDto setWaveRecordingStart(final Integer waveRecordingStart) {
      this.waveRecordingStart = waveRecordingStart;
      return this;
   }

   public DeviceRawIecTelemtryDto setWaveRecordingFinishFlag(final Boolean waveRecordingFinishFlag) {
      this.waveRecordingFinishFlag = waveRecordingFinishFlag;
      return this;
   }

   public DeviceRawIecTelemtryDto setWaveRecordingHandleFlag(final Boolean waveRecordingHandleFlag) {
      this.waveRecordingHandleFlag = waveRecordingHandleFlag;
      return this;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof DeviceRawIecTelemtryDto)) {
         return false;
      }

      DeviceRawIecTelemtryDto other = (DeviceRawIecTelemtryDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$port = this.getPort();
      Object other$port = other.getPort();
      if (this$port == null ? other$port == null : this$port.equals(other$port)) {
         Object this$commonAddressOfUpwardDelivery = this.getCommonAddressOfUpwardDelivery();
         Object other$commonAddressOfUpwardDelivery = other.getCommonAddressOfUpwardDelivery();
         if (this$commonAddressOfUpwardDelivery == null
            ? other$commonAddressOfUpwardDelivery == null
            : this$commonAddressOfUpwardDelivery.equals(other$commonAddressOfUpwardDelivery)) {
            Object this$switchClosedPosition = this.getSwitchClosedPosition();
            Object other$switchClosedPosition = other.getSwitchClosedPosition();
            if (this$switchClosedPosition == null ? other$switchClosedPosition == null : this$switchClosedPosition.equals(other$switchClosedPosition)) {
               Object this$switchDivision = this.getSwitchDivision();
               Object other$switchDivision = other.getSwitchDivision();
               if (this$switchDivision == null ? other$switchDivision == null : this$switchDivision.equals(other$switchDivision)) {
                  Object this$threePositionKnifeClosingPosition = this.getThreePositionKnifeClosingPosition();
                  Object other$threePositionKnifeClosingPosition = other.getThreePositionKnifeClosingPosition();
                  if (this$threePositionKnifeClosingPosition == null
                     ? other$threePositionKnifeClosingPosition == null
                     : this$threePositionKnifeClosingPosition.equals(other$threePositionKnifeClosingPosition)) {
                     Object this$threeStationKnifeClosingPosition = this.getThreeStationKnifeClosingPosition();
                     Object other$threeStationKnifeClosingPosition = other.getThreeStationKnifeClosingPosition();
                     if (this$threeStationKnifeClosingPosition == null
                        ? other$threeStationKnifeClosingPosition == null
                        : this$threeStationKnifeClosingPosition.equals(other$threeStationKnifeClosingPosition)) {
                        Object this$subsectionIsolationThQuarantineClosing = this.getSubsectionIsolationThQuarantineClosing();
                        Object other$subsectionIsolationThQuarantineClosing = other.getSubsectionIsolationThQuarantineClosing();
                        if (this$subsectionIsolationThQuarantineClosing == null
                           ? other$subsectionIsolationThQuarantineClosing == null
                           : this$subsectionIsolationThQuarantineClosing.equals(other$subsectionIsolationThQuarantineClosing)) {
                           Object this$subsectionIsolationThQuarantineMiddle = this.getSubsectionIsolationThQuarantineMiddle();
                           Object other$subsectionIsolationThQuarantineMiddle = other.getSubsectionIsolationThQuarantineMiddle();
                           if (this$subsectionIsolationThQuarantineMiddle == null
                              ? other$subsectionIsolationThQuarantineMiddle == null
                              : this$subsectionIsolationThQuarantineMiddle.equals(other$subsectionIsolationThQuarantineMiddle)) {
                              Object this$subsectionIsolationThQuarantineOpening = this.getSubsectionIsolationThQuarantineOpening();
                              Object other$subsectionIsolationThQuarantineOpening = other.getSubsectionIsolationThQuarantineOpening();
                              if (this$subsectionIsolationThQuarantineOpening == null
                                 ? other$subsectionIsolationThQuarantineOpening == null
                                 : this$subsectionIsolationThQuarantineOpening.equals(other$subsectionIsolationThQuarantineOpening)) {
                                 Object this$openingCoilCurrentStart = this.getOpeningCoilCurrentStart();
                                 Object other$openingCoilCurrentStart = other.getOpeningCoilCurrentStart();
                                 if (this$openingCoilCurrentStart == null
                                    ? other$openingCoilCurrentStart == null
                                    : this$openingCoilCurrentStart.equals(other$openingCoilCurrentStart)) {
                                    Object this$startOfClosingCoilCurrent = this.getStartOfClosingCoilCurrent();
                                    Object other$startOfClosingCoilCurrent = other.getStartOfClosingCoilCurrent();
                                    if (this$startOfClosingCoilCurrent == null
                                       ? other$startOfClosingCoilCurrent == null
                                       : this$startOfClosingCoilCurrent.equals(other$startOfClosingCoilCurrent)) {
                                       Object this$energyStorageMotorCurrentStarting = this.getEnergyStorageMotorCurrentStarting();
                                       Object other$energyStorageMotorCurrentStarting = other.getEnergyStorageMotorCurrentStarting();
                                       if (this$energyStorageMotorCurrentStarting == null
                                          ? other$energyStorageMotorCurrentStarting == null
                                          : this$energyStorageMotorCurrentStarting.equals(other$energyStorageMotorCurrentStarting)) {
                                          Object this$threeStationOneCurrentStart = this.getThreeStationOneCurrentStart();
                                          Object other$threeStationOneCurrentStart = other.getThreeStationOneCurrentStart();
                                          if (this$threeStationOneCurrentStart == null
                                             ? other$threeStationOneCurrentStart == null
                                             : this$threeStationOneCurrentStart.equals(other$threeStationOneCurrentStart)) {
                                             Object this$threeStationTwoCurrentStart = this.getThreeStationTwoCurrentStart();
                                             Object other$threeStationTwoCurrentStart = other.getThreeStationTwoCurrentStart();
                                             if (this$threeStationTwoCurrentStart == null
                                                ? other$threeStationTwoCurrentStart == null
                                                : this$threeStationTwoCurrentStart.equals(other$threeStationTwoCurrentStart)) {
                                                Object this$waveRecordingStart = this.getWaveRecordingStart();
                                                Object other$waveRecordingStart = other.getWaveRecordingStart();
                                                if (this$waveRecordingStart == null
                                                   ? other$waveRecordingStart == null
                                                   : this$waveRecordingStart.equals(other$waveRecordingStart)) {
                                                   Object this$waveRecordingFinishFlag = this.getWaveRecordingFinishFlag();
                                                   Object other$waveRecordingFinishFlag = other.getWaveRecordingFinishFlag();
                                                   if (this$waveRecordingFinishFlag == null
                                                      ? other$waveRecordingFinishFlag == null
                                                      : this$waveRecordingFinishFlag.equals(other$waveRecordingFinishFlag)) {
                                                      Object this$waveRecordingHandleFlag = this.getWaveRecordingHandleFlag();
                                                      Object other$waveRecordingHandleFlag = other.getWaveRecordingHandleFlag();
                                                      if (this$waveRecordingHandleFlag == null
                                                         ? other$waveRecordingHandleFlag == null
                                                         : this$waveRecordingHandleFlag.equals(other$waveRecordingHandleFlag)) {
                                                         Object this$connection = this.getConnection();
                                                         Object other$connection = other.getConnection();
                                                         if (this$connection == null ? other$connection == null : this$connection.equals(other$connection)) {
                                                            Object this$iec104ClientConnectionEventListener = this.getIec104ClientConnectionEventListener();
                                                            Object other$iec104ClientConnectionEventListener = other.getIec104ClientConnectionEventListener();
                                                            if (this$iec104ClientConnectionEventListener == null
                                                               ? other$iec104ClientConnectionEventListener == null
                                                               : this$iec104ClientConnectionEventListener.equals(other$iec104ClientConnectionEventListener)) {
                                                               Object this$njmscIec104MutationHandle = this.getNjmscIec104MutationHandle();
                                                               Object other$njmscIec104MutationHandle = other.getNjmscIec104MutationHandle();
                                                               if (this$njmscIec104MutationHandle == null
                                                                  ? other$njmscIec104MutationHandle == null
                                                                  : this$njmscIec104MutationHandle.equals(other$njmscIec104MutationHandle)) {
                                                                  Object this$endOfInterrogationCD = this.getEndOfInterrogationCD();
                                                                  Object other$endOfInterrogationCD = other.getEndOfInterrogationCD();
                                                                  if (this$endOfInterrogationCD == null
                                                                     ? other$endOfInterrogationCD == null
                                                                     : this$endOfInterrogationCD.equals(other$endOfInterrogationCD)) {
                                                                     Object this$endOfInterrogationSS = this.getEndOfInterrogationSS();
                                                                     Object other$endOfInterrogationSS = other.getEndOfInterrogationSS();
                                                                     if (this$endOfInterrogationSS == null
                                                                        ? other$endOfInterrogationSS == null
                                                                        : this$endOfInterrogationSS.equals(other$endOfInterrogationSS)) {
                                                                        Object this$connectionClosedFlag = this.getConnectionClosedFlag();
                                                                        Object other$connectionClosedFlag = other.getConnectionClosedFlag();
                                                                        if (this$connectionClosedFlag == null
                                                                           ? other$connectionClosedFlag == null
                                                                           : this$connectionClosedFlag.equals(other$connectionClosedFlag)) {
                                                                           Object this$mapComtradeCfgModels = this.getMapComtradeCfgModels();
                                                                           Object other$mapComtradeCfgModels = other.getMapComtradeCfgModels();
                                                                           if (this$mapComtradeCfgModels == null
                                                                              ? other$mapComtradeCfgModels == null
                                                                              : this$mapComtradeCfgModels.equals(other$mapComtradeCfgModels)) {
                                                                              Object this$mapComtradeChannelDataModels = this.getMapComtradeChannelDataModels();
                                                                              Object other$mapComtradeChannelDataModels = other.getMapComtradeChannelDataModels();
                                                                              if (this$mapComtradeChannelDataModels == null
                                                                                 ? other$mapComtradeChannelDataModels == null
                                                                                 : this$mapComtradeChannelDataModels.equals(other$mapComtradeChannelDataModels)
                                                                                 )
                                                                               {
                                                                                 Object this$deviceType = this.getDeviceType();
                                                                                 Object other$deviceType = other.getDeviceType();
                                                                                 if (this$deviceType == null
                                                                                    ? other$deviceType == null
                                                                                    : this$deviceType.equals(other$deviceType)) {
                                                                                    Object this$deviceCode = this.getDeviceCode();
                                                                                    Object other$deviceCode = other.getDeviceCode();
                                                                                    if (this$deviceCode == null
                                                                                       ? other$deviceCode == null
                                                                                       : this$deviceCode.equals(other$deviceCode)) {
                                                                                       Object this$ip = this.getIp();
                                                                                       Object other$ip = other.getIp();
                                                                                       if (this$ip == null ? other$ip == null : this$ip.equals(other$ip)) {
                                                                                          Object this$rawIecTelemetryModel = this.getRawIecTelemetryModel();
                                                                                          Object other$rawIecTelemetryModel = other.getRawIecTelemetryModel();
                                                                                          if (this$rawIecTelemetryModel == null
                                                                                             ? other$rawIecTelemetryModel == null
                                                                                             : this$rawIecTelemetryModel.equals(other$rawIecTelemetryModel)) {
                                                                                             Object this$rawIecRemoteSignalingModel = this.getRawIecRemoteSignalingModel();
                                                                                             Object other$rawIecRemoteSignalingModel = other.getRawIecRemoteSignalingModel();
                                                                                             return this$rawIecRemoteSignalingModel == null
                                                                                                ? other$rawIecRemoteSignalingModel == null
                                                                                                : this$rawIecRemoteSignalingModel.equals(
                                                                                                   other$rawIecRemoteSignalingModel
                                                                                                );
                                                                                          } else {
                                                                                             return false;
                                                                                          }
                                                                                       } else {
                                                                                          return false;
                                                                                       }
                                                                                    } else {
                                                                                       return false;
                                                                                    }
                                                                                 } else {
                                                                                    return false;
                                                                                 }
                                                                              } else {
                                                                                 return false;
                                                                              }
                                                                           } else {
                                                                              return false;
                                                                           }
                                                                        } else {
                                                                           return false;
                                                                        }
                                                                     } else {
                                                                        return false;
                                                                     }
                                                                  } else {
                                                                     return false;
                                                                  }
                                                               } else {
                                                                  return false;
                                                               }
                                                            } else {
                                                               return false;
                                                            }
                                                         } else {
                                                            return false;
                                                         }
                                                      } else {
                                                         return false;
                                                      }
                                                   } else {
                                                      return false;
                                                   }
                                                } else {
                                                   return false;
                                                }
                                             } else {
                                                return false;
                                             }
                                          } else {
                                             return false;
                                          }
                                       } else {
                                          return false;
                                       }
                                    } else {
                                       return false;
                                    }
                                 } else {
                                    return false;
                                 }
                              } else {
                                 return false;
                              }
                           } else {
                              return false;
                           }
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof DeviceRawIecTelemtryDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $port = this.getPort();
      result = result * 59 + ($port == null ? 43 : $port.hashCode());
      Object $commonAddressOfUpwardDelivery = this.getCommonAddressOfUpwardDelivery();
      result = result * 59 + ($commonAddressOfUpwardDelivery == null ? 43 : $commonAddressOfUpwardDelivery.hashCode());
      Object $switchClosedPosition = this.getSwitchClosedPosition();
      result = result * 59 + ($switchClosedPosition == null ? 43 : $switchClosedPosition.hashCode());
      Object $switchDivision = this.getSwitchDivision();
      result = result * 59 + ($switchDivision == null ? 43 : $switchDivision.hashCode());
      Object $threePositionKnifeClosingPosition = this.getThreePositionKnifeClosingPosition();
      result = result * 59 + ($threePositionKnifeClosingPosition == null ? 43 : $threePositionKnifeClosingPosition.hashCode());
      Object $threeStationKnifeClosingPosition = this.getThreeStationKnifeClosingPosition();
      result = result * 59 + ($threeStationKnifeClosingPosition == null ? 43 : $threeStationKnifeClosingPosition.hashCode());
      Object $subsectionIsolationThQuarantineClosing = this.getSubsectionIsolationThQuarantineClosing();
      result = result * 59 + ($subsectionIsolationThQuarantineClosing == null ? 43 : $subsectionIsolationThQuarantineClosing.hashCode());
      Object $subsectionIsolationThQuarantineMiddle = this.getSubsectionIsolationThQuarantineMiddle();
      result = result * 59 + ($subsectionIsolationThQuarantineMiddle == null ? 43 : $subsectionIsolationThQuarantineMiddle.hashCode());
      Object $subsectionIsolationThQuarantineOpening = this.getSubsectionIsolationThQuarantineOpening();
      result = result * 59 + ($subsectionIsolationThQuarantineOpening == null ? 43 : $subsectionIsolationThQuarantineOpening.hashCode());
      Object $openingCoilCurrentStart = this.getOpeningCoilCurrentStart();
      result = result * 59 + ($openingCoilCurrentStart == null ? 43 : $openingCoilCurrentStart.hashCode());
      Object $startOfClosingCoilCurrent = this.getStartOfClosingCoilCurrent();
      result = result * 59 + ($startOfClosingCoilCurrent == null ? 43 : $startOfClosingCoilCurrent.hashCode());
      Object $energyStorageMotorCurrentStarting = this.getEnergyStorageMotorCurrentStarting();
      result = result * 59 + ($energyStorageMotorCurrentStarting == null ? 43 : $energyStorageMotorCurrentStarting.hashCode());
      Object $threeStationOneCurrentStart = this.getThreeStationOneCurrentStart();
      result = result * 59 + ($threeStationOneCurrentStart == null ? 43 : $threeStationOneCurrentStart.hashCode());
      Object $threeStationTwoCurrentStart = this.getThreeStationTwoCurrentStart();
      result = result * 59 + ($threeStationTwoCurrentStart == null ? 43 : $threeStationTwoCurrentStart.hashCode());
      Object $waveRecordingStart = this.getWaveRecordingStart();
      result = result * 59 + ($waveRecordingStart == null ? 43 : $waveRecordingStart.hashCode());
      Object $waveRecordingFinishFlag = this.getWaveRecordingFinishFlag();
      result = result * 59 + ($waveRecordingFinishFlag == null ? 43 : $waveRecordingFinishFlag.hashCode());
      Object $waveRecordingHandleFlag = this.getWaveRecordingHandleFlag();
      result = result * 59 + ($waveRecordingHandleFlag == null ? 43 : $waveRecordingHandleFlag.hashCode());
      Object $connection = this.getConnection();
      result = result * 59 + ($connection == null ? 43 : $connection.hashCode());
      Object $iec104ClientConnectionEventListener = this.getIec104ClientConnectionEventListener();
      result = result * 59 + ($iec104ClientConnectionEventListener == null ? 43 : $iec104ClientConnectionEventListener.hashCode());
      Object $njmscIec104MutationHandle = this.getNjmscIec104MutationHandle();
      result = result * 59 + ($njmscIec104MutationHandle == null ? 43 : $njmscIec104MutationHandle.hashCode());
      Object $endOfInterrogationCD = this.getEndOfInterrogationCD();
      result = result * 59 + ($endOfInterrogationCD == null ? 43 : $endOfInterrogationCD.hashCode());
      Object $endOfInterrogationSS = this.getEndOfInterrogationSS();
      result = result * 59 + ($endOfInterrogationSS == null ? 43 : $endOfInterrogationSS.hashCode());
      Object $connectionClosedFlag = this.getConnectionClosedFlag();
      result = result * 59 + ($connectionClosedFlag == null ? 43 : $connectionClosedFlag.hashCode());
      Object $mapComtradeCfgModels = this.getMapComtradeCfgModels();
      result = result * 59 + ($mapComtradeCfgModels == null ? 43 : $mapComtradeCfgModels.hashCode());
      Object $mapComtradeChannelDataModels = this.getMapComtradeChannelDataModels();
      result = result * 59 + ($mapComtradeChannelDataModels == null ? 43 : $mapComtradeChannelDataModels.hashCode());
      Object $deviceType = this.getDeviceType();
      result = result * 59 + ($deviceType == null ? 43 : $deviceType.hashCode());
      Object $deviceCode = this.getDeviceCode();
      result = result * 59 + ($deviceCode == null ? 43 : $deviceCode.hashCode());
      Object $ip = this.getIp();
      result = result * 59 + ($ip == null ? 43 : $ip.hashCode());
      Object $rawIecTelemetryModel = this.getRawIecTelemetryModel();
      result = result * 59 + ($rawIecTelemetryModel == null ? 43 : $rawIecTelemetryModel.hashCode());
      Object $rawIecRemoteSignalingModel = this.getRawIecRemoteSignalingModel();
      return result * 59 + ($rawIecRemoteSignalingModel == null ? 43 : $rawIecRemoteSignalingModel.hashCode());
   }

   @Override
   public String toString() {
      return "DeviceRawIecTelemtryDto(connection="
         + this.getConnection()
         + ", iec104ClientConnectionEventListener="
         + this.getIec104ClientConnectionEventListener()
         + ", njmscIec104MutationHandle="
         + this.getNjmscIec104MutationHandle()
         + ", endOfInterrogationCD="
         + this.getEndOfInterrogationCD()
         + ", endOfInterrogationSS="
         + this.getEndOfInterrogationSS()
         + ", connectionClosedFlag="
         + this.getConnectionClosedFlag()
         + ", mapComtradeCfgModels="
         + this.getMapComtradeCfgModels()
         + ", mapComtradeChannelDataModels="
         + this.getMapComtradeChannelDataModels()
         + ", deviceType="
         + this.getDeviceType()
         + ", deviceCode="
         + this.getDeviceCode()
         + ", ip="
         + this.getIp()
         + ", port="
         + this.getPort()
         + ", commonAddressOfUpwardDelivery="
         + this.getCommonAddressOfUpwardDelivery()
         + ", rawIecTelemetryModel="
         + this.getRawIecTelemetryModel()
         + ", rawIecRemoteSignalingModel="
         + this.getRawIecRemoteSignalingModel()
         + ", switchClosedPosition="
         + this.getSwitchClosedPosition()
         + ", switchDivision="
         + this.getSwitchDivision()
         + ", threePositionKnifeClosingPosition="
         + this.getThreePositionKnifeClosingPosition()
         + ", threeStationKnifeClosingPosition="
         + this.getThreeStationKnifeClosingPosition()
         + ", subsectionIsolationThQuarantineClosing="
         + this.getSubsectionIsolationThQuarantineClosing()
         + ", subsectionIsolationThQuarantineMiddle="
         + this.getSubsectionIsolationThQuarantineMiddle()
         + ", subsectionIsolationThQuarantineOpening="
         + this.getSubsectionIsolationThQuarantineOpening()
         + ", openingCoilCurrentStart="
         + this.getOpeningCoilCurrentStart()
         + ", startOfClosingCoilCurrent="
         + this.getStartOfClosingCoilCurrent()
         + ", energyStorageMotorCurrentStarting="
         + this.getEnergyStorageMotorCurrentStarting()
         + ", threeStationOneCurrentStart="
         + this.getThreeStationOneCurrentStart()
         + ", threeStationTwoCurrentStart="
         + this.getThreeStationTwoCurrentStart()
         + ", waveRecordingStart="
         + this.getWaveRecordingStart()
         + ", waveRecordingFinishFlag="
         + this.getWaveRecordingFinishFlag()
         + ", waveRecordingHandleFlag="
         + this.getWaveRecordingHandleFlag()
         + ")";
   }
}
