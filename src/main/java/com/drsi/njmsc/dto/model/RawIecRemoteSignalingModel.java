package com.drsi.njmsc.dto.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@TableName("raw_iec_remote_signaling")
@JsonInclude(Include.NON_NULL)
public class RawIecRemoteSignalingModel implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(type = IdType.AUTO)
   private Long id;
   @ApiModelProperty("设备编码")
   private String deviceCode;
   @ApiModelProperty("开关合位")
   private Integer switchClosedPosition;
   @ApiModelProperty("开关分位")
   private Integer switchDivision;
   @ApiModelProperty("三工位隔刀合位")
   private Integer threePositionKnifeClosingPosition;
   @ApiModelProperty("三工位地刀合位")
   private Integer threeStationKnifeClosingPosition;
   private Integer openingCoilCurrentStart;
   private Integer startOfClosingCoilCurrent;
   private Integer energyStorageMotorCurrentStarting;
   private Integer threeStationOneCurrentStart;
   private Integer threeStationTwoCurrentStart;
   private Integer waveRecordingStart;
   private Integer moduleSelfTestAbnormality;
   private Integer abnormalSamplingData;
   private Integer ramSelfTestAbnormality;
   private Integer openSelfTestAbnormality;
   private Integer fixedValueSelfTestAbnormality;
   private Integer ptDisconnection;
   private Integer deviceLocking;
   private Integer groundingAlarm;
   private Integer communicationInterruption;
   private Integer cbInterruption;
   private Integer bInterruption;
   private Integer envInterruption;
   private Integer arresterInterruption;
   private Integer isolationPressureInterruption;
   private Integer isolationRemoteSignalingInterruption;
   private Integer subsectionIsolationThQuarantineClosing;
   private Integer subsectionIsolationThQuarantineMiddle;
   private Integer subsectionIsolationThQuarantineOpening;
   @ApiModelProperty("创建时间")
   private Date createDate;

   public Long getId() {
      return this.id;
   }

   public String getDeviceCode() {
      return this.deviceCode;
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

   public Integer getModuleSelfTestAbnormality() {
      return this.moduleSelfTestAbnormality;
   }

   public Integer getAbnormalSamplingData() {
      return this.abnormalSamplingData;
   }

   public Integer getRamSelfTestAbnormality() {
      return this.ramSelfTestAbnormality;
   }

   public Integer getOpenSelfTestAbnormality() {
      return this.openSelfTestAbnormality;
   }

   public Integer getFixedValueSelfTestAbnormality() {
      return this.fixedValueSelfTestAbnormality;
   }

   public Integer getPtDisconnection() {
      return this.ptDisconnection;
   }

   public Integer getDeviceLocking() {
      return this.deviceLocking;
   }

   public Integer getGroundingAlarm() {
      return this.groundingAlarm;
   }

   public Integer getCommunicationInterruption() {
      return this.communicationInterruption;
   }

   public Integer getCbInterruption() {
      return this.cbInterruption;
   }

   public Integer getBInterruption() {
      return this.bInterruption;
   }

   public Integer getEnvInterruption() {
      return this.envInterruption;
   }

   public Integer getArresterInterruption() {
      return this.arresterInterruption;
   }

   public Integer getIsolationPressureInterruption() {
      return this.isolationPressureInterruption;
   }

   public Integer getIsolationRemoteSignalingInterruption() {
      return this.isolationRemoteSignalingInterruption;
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

   public Date getCreateDate() {
      return this.createDate;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public void setDeviceCode(final String deviceCode) {
      this.deviceCode = deviceCode;
   }

   public void setSwitchClosedPosition(final Integer switchClosedPosition) {
      this.switchClosedPosition = switchClosedPosition;
   }

   public void setSwitchDivision(final Integer switchDivision) {
      this.switchDivision = switchDivision;
   }

   public void setThreePositionKnifeClosingPosition(final Integer threePositionKnifeClosingPosition) {
      this.threePositionKnifeClosingPosition = threePositionKnifeClosingPosition;
   }

   public void setThreeStationKnifeClosingPosition(final Integer threeStationKnifeClosingPosition) {
      this.threeStationKnifeClosingPosition = threeStationKnifeClosingPosition;
   }

   public void setOpeningCoilCurrentStart(final Integer openingCoilCurrentStart) {
      this.openingCoilCurrentStart = openingCoilCurrentStart;
   }

   public void setStartOfClosingCoilCurrent(final Integer startOfClosingCoilCurrent) {
      this.startOfClosingCoilCurrent = startOfClosingCoilCurrent;
   }

   public void setEnergyStorageMotorCurrentStarting(final Integer energyStorageMotorCurrentStarting) {
      this.energyStorageMotorCurrentStarting = energyStorageMotorCurrentStarting;
   }

   public void setThreeStationOneCurrentStart(final Integer threeStationOneCurrentStart) {
      this.threeStationOneCurrentStart = threeStationOneCurrentStart;
   }

   public void setThreeStationTwoCurrentStart(final Integer threeStationTwoCurrentStart) {
      this.threeStationTwoCurrentStart = threeStationTwoCurrentStart;
   }

   public void setWaveRecordingStart(final Integer waveRecordingStart) {
      this.waveRecordingStart = waveRecordingStart;
   }

   public void setModuleSelfTestAbnormality(final Integer moduleSelfTestAbnormality) {
      this.moduleSelfTestAbnormality = moduleSelfTestAbnormality;
   }

   public void setAbnormalSamplingData(final Integer abnormalSamplingData) {
      this.abnormalSamplingData = abnormalSamplingData;
   }

   public void setRamSelfTestAbnormality(final Integer ramSelfTestAbnormality) {
      this.ramSelfTestAbnormality = ramSelfTestAbnormality;
   }

   public void setOpenSelfTestAbnormality(final Integer openSelfTestAbnormality) {
      this.openSelfTestAbnormality = openSelfTestAbnormality;
   }

   public void setFixedValueSelfTestAbnormality(final Integer fixedValueSelfTestAbnormality) {
      this.fixedValueSelfTestAbnormality = fixedValueSelfTestAbnormality;
   }

   public void setPtDisconnection(final Integer ptDisconnection) {
      this.ptDisconnection = ptDisconnection;
   }

   public void setDeviceLocking(final Integer deviceLocking) {
      this.deviceLocking = deviceLocking;
   }

   public void setGroundingAlarm(final Integer groundingAlarm) {
      this.groundingAlarm = groundingAlarm;
   }

   public void setCommunicationInterruption(final Integer communicationInterruption) {
      this.communicationInterruption = communicationInterruption;
   }

   public void setCbInterruption(final Integer cbInterruption) {
      this.cbInterruption = cbInterruption;
   }

   public void setBInterruption(final Integer bInterruption) {
      this.bInterruption = bInterruption;
   }

   public void setEnvInterruption(final Integer envInterruption) {
      this.envInterruption = envInterruption;
   }

   public void setArresterInterruption(final Integer arresterInterruption) {
      this.arresterInterruption = arresterInterruption;
   }

   public void setIsolationPressureInterruption(final Integer isolationPressureInterruption) {
      this.isolationPressureInterruption = isolationPressureInterruption;
   }

   public void setIsolationRemoteSignalingInterruption(final Integer isolationRemoteSignalingInterruption) {
      this.isolationRemoteSignalingInterruption = isolationRemoteSignalingInterruption;
   }

   public void setSubsectionIsolationThQuarantineClosing(final Integer subsectionIsolationThQuarantineClosing) {
      this.subsectionIsolationThQuarantineClosing = subsectionIsolationThQuarantineClosing;
   }

   public void setSubsectionIsolationThQuarantineMiddle(final Integer subsectionIsolationThQuarantineMiddle) {
      this.subsectionIsolationThQuarantineMiddle = subsectionIsolationThQuarantineMiddle;
   }

   public void setSubsectionIsolationThQuarantineOpening(final Integer subsectionIsolationThQuarantineOpening) {
      this.subsectionIsolationThQuarantineOpening = subsectionIsolationThQuarantineOpening;
   }

   public void setCreateDate(final Date createDate) {
      this.createDate = createDate;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof RawIecRemoteSignalingModel)) {
         return false;
      }

      RawIecRemoteSignalingModel other = (RawIecRemoteSignalingModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$id = this.getId();
      Object other$id = other.getId();
      if (this$id == null ? other$id == null : this$id.equals(other$id)) {
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
                                       Object this$moduleSelfTestAbnormality = this.getModuleSelfTestAbnormality();
                                       Object other$moduleSelfTestAbnormality = other.getModuleSelfTestAbnormality();
                                       if (this$moduleSelfTestAbnormality == null
                                          ? other$moduleSelfTestAbnormality == null
                                          : this$moduleSelfTestAbnormality.equals(other$moduleSelfTestAbnormality)) {
                                          Object this$abnormalSamplingData = this.getAbnormalSamplingData();
                                          Object other$abnormalSamplingData = other.getAbnormalSamplingData();
                                          if (this$abnormalSamplingData == null
                                             ? other$abnormalSamplingData == null
                                             : this$abnormalSamplingData.equals(other$abnormalSamplingData)) {
                                             Object this$ramSelfTestAbnormality = this.getRamSelfTestAbnormality();
                                             Object other$ramSelfTestAbnormality = other.getRamSelfTestAbnormality();
                                             if (this$ramSelfTestAbnormality == null
                                                ? other$ramSelfTestAbnormality == null
                                                : this$ramSelfTestAbnormality.equals(other$ramSelfTestAbnormality)) {
                                                Object this$openSelfTestAbnormality = this.getOpenSelfTestAbnormality();
                                                Object other$openSelfTestAbnormality = other.getOpenSelfTestAbnormality();
                                                if (this$openSelfTestAbnormality == null
                                                   ? other$openSelfTestAbnormality == null
                                                   : this$openSelfTestAbnormality.equals(other$openSelfTestAbnormality)) {
                                                   Object this$fixedValueSelfTestAbnormality = this.getFixedValueSelfTestAbnormality();
                                                   Object other$fixedValueSelfTestAbnormality = other.getFixedValueSelfTestAbnormality();
                                                   if (this$fixedValueSelfTestAbnormality == null
                                                      ? other$fixedValueSelfTestAbnormality == null
                                                      : this$fixedValueSelfTestAbnormality.equals(other$fixedValueSelfTestAbnormality)) {
                                                      Object this$ptDisconnection = this.getPtDisconnection();
                                                      Object other$ptDisconnection = other.getPtDisconnection();
                                                      if (this$ptDisconnection == null
                                                         ? other$ptDisconnection == null
                                                         : this$ptDisconnection.equals(other$ptDisconnection)) {
                                                         Object this$deviceLocking = this.getDeviceLocking();
                                                         Object other$deviceLocking = other.getDeviceLocking();
                                                         if (this$deviceLocking == null
                                                            ? other$deviceLocking == null
                                                            : this$deviceLocking.equals(other$deviceLocking)) {
                                                            Object this$groundingAlarm = this.getGroundingAlarm();
                                                            Object other$groundingAlarm = other.getGroundingAlarm();
                                                            if (this$groundingAlarm == null
                                                               ? other$groundingAlarm == null
                                                               : this$groundingAlarm.equals(other$groundingAlarm)) {
                                                               Object this$communicationInterruption = this.getCommunicationInterruption();
                                                               Object other$communicationInterruption = other.getCommunicationInterruption();
                                                               if (this$communicationInterruption == null
                                                                  ? other$communicationInterruption == null
                                                                  : this$communicationInterruption.equals(other$communicationInterruption)) {
                                                                  Object this$cbInterruption = this.getCbInterruption();
                                                                  Object other$cbInterruption = other.getCbInterruption();
                                                                  if (this$cbInterruption == null
                                                                     ? other$cbInterruption == null
                                                                     : this$cbInterruption.equals(other$cbInterruption)) {
                                                                     Object this$bInterruption = this.getBInterruption();
                                                                     Object other$bInterruption = other.getBInterruption();
                                                                     if (this$bInterruption == null
                                                                        ? other$bInterruption == null
                                                                        : this$bInterruption.equals(other$bInterruption)) {
                                                                        Object this$envInterruption = this.getEnvInterruption();
                                                                        Object other$envInterruption = other.getEnvInterruption();
                                                                        if (this$envInterruption == null
                                                                           ? other$envInterruption == null
                                                                           : this$envInterruption.equals(other$envInterruption)) {
                                                                           Object this$arresterInterruption = this.getArresterInterruption();
                                                                           Object other$arresterInterruption = other.getArresterInterruption();
                                                                           if (this$arresterInterruption == null
                                                                              ? other$arresterInterruption == null
                                                                              : this$arresterInterruption.equals(other$arresterInterruption)) {
                                                                              Object this$isolationPressureInterruption = this.getIsolationPressureInterruption();
                                                                              Object other$isolationPressureInterruption = other.getIsolationPressureInterruption();
                                                                              if (this$isolationPressureInterruption == null
                                                                                 ? other$isolationPressureInterruption == null
                                                                                 : this$isolationPressureInterruption.equals(
                                                                                    other$isolationPressureInterruption
                                                                                 )) {
                                                                                 Object this$isolationRemoteSignalingInterruption = this.getIsolationRemoteSignalingInterruption();
                                                                                 Object other$isolationRemoteSignalingInterruption = other.getIsolationRemoteSignalingInterruption();
                                                                                 if (this$isolationRemoteSignalingInterruption == null
                                                                                    ? other$isolationRemoteSignalingInterruption == null
                                                                                    : this$isolationRemoteSignalingInterruption.equals(
                                                                                       other$isolationRemoteSignalingInterruption
                                                                                    )) {
                                                                                    Object this$subsectionIsolationThQuarantineClosing = this.getSubsectionIsolationThQuarantineClosing();
                                                                                    Object other$subsectionIsolationThQuarantineClosing = other.getSubsectionIsolationThQuarantineClosing();
                                                                                    if (this$subsectionIsolationThQuarantineClosing == null
                                                                                       ? other$subsectionIsolationThQuarantineClosing == null
                                                                                       : this$subsectionIsolationThQuarantineClosing.equals(
                                                                                          other$subsectionIsolationThQuarantineClosing
                                                                                       )) {
                                                                                       Object this$subsectionIsolationThQuarantineMiddle = this.getSubsectionIsolationThQuarantineMiddle();
                                                                                       Object other$subsectionIsolationThQuarantineMiddle = other.getSubsectionIsolationThQuarantineMiddle();
                                                                                       if (this$subsectionIsolationThQuarantineMiddle == null
                                                                                          ? other$subsectionIsolationThQuarantineMiddle == null
                                                                                          : this$subsectionIsolationThQuarantineMiddle.equals(
                                                                                             other$subsectionIsolationThQuarantineMiddle
                                                                                          )) {
                                                                                          Object this$subsectionIsolationThQuarantineOpening = this.getSubsectionIsolationThQuarantineOpening();
                                                                                          Object other$subsectionIsolationThQuarantineOpening = other.getSubsectionIsolationThQuarantineOpening();
                                                                                          if (this$subsectionIsolationThQuarantineOpening == null
                                                                                             ? other$subsectionIsolationThQuarantineOpening == null
                                                                                             : this$subsectionIsolationThQuarantineOpening.equals(
                                                                                                other$subsectionIsolationThQuarantineOpening
                                                                                             )) {
                                                                                             Object this$deviceCode = this.getDeviceCode();
                                                                                             Object other$deviceCode = other.getDeviceCode();
                                                                                             if (this$deviceCode == null
                                                                                                ? other$deviceCode == null
                                                                                                : this$deviceCode.equals(other$deviceCode)) {
                                                                                                Object this$createDate = this.getCreateDate();
                                                                                                Object other$createDate = other.getCreateDate();
                                                                                                return this$createDate == null
                                                                                                   ? other$createDate == null
                                                                                                   : this$createDate.equals(other$createDate);
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
      } else {
         return false;
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof RawIecRemoteSignalingModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $switchClosedPosition = this.getSwitchClosedPosition();
      result = result * 59 + ($switchClosedPosition == null ? 43 : $switchClosedPosition.hashCode());
      Object $switchDivision = this.getSwitchDivision();
      result = result * 59 + ($switchDivision == null ? 43 : $switchDivision.hashCode());
      Object $threePositionKnifeClosingPosition = this.getThreePositionKnifeClosingPosition();
      result = result * 59 + ($threePositionKnifeClosingPosition == null ? 43 : $threePositionKnifeClosingPosition.hashCode());
      Object $threeStationKnifeClosingPosition = this.getThreeStationKnifeClosingPosition();
      result = result * 59 + ($threeStationKnifeClosingPosition == null ? 43 : $threeStationKnifeClosingPosition.hashCode());
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
      Object $moduleSelfTestAbnormality = this.getModuleSelfTestAbnormality();
      result = result * 59 + ($moduleSelfTestAbnormality == null ? 43 : $moduleSelfTestAbnormality.hashCode());
      Object $abnormalSamplingData = this.getAbnormalSamplingData();
      result = result * 59 + ($abnormalSamplingData == null ? 43 : $abnormalSamplingData.hashCode());
      Object $ramSelfTestAbnormality = this.getRamSelfTestAbnormality();
      result = result * 59 + ($ramSelfTestAbnormality == null ? 43 : $ramSelfTestAbnormality.hashCode());
      Object $openSelfTestAbnormality = this.getOpenSelfTestAbnormality();
      result = result * 59 + ($openSelfTestAbnormality == null ? 43 : $openSelfTestAbnormality.hashCode());
      Object $fixedValueSelfTestAbnormality = this.getFixedValueSelfTestAbnormality();
      result = result * 59 + ($fixedValueSelfTestAbnormality == null ? 43 : $fixedValueSelfTestAbnormality.hashCode());
      Object $ptDisconnection = this.getPtDisconnection();
      result = result * 59 + ($ptDisconnection == null ? 43 : $ptDisconnection.hashCode());
      Object $deviceLocking = this.getDeviceLocking();
      result = result * 59 + ($deviceLocking == null ? 43 : $deviceLocking.hashCode());
      Object $groundingAlarm = this.getGroundingAlarm();
      result = result * 59 + ($groundingAlarm == null ? 43 : $groundingAlarm.hashCode());
      Object $communicationInterruption = this.getCommunicationInterruption();
      result = result * 59 + ($communicationInterruption == null ? 43 : $communicationInterruption.hashCode());
      Object $cbInterruption = this.getCbInterruption();
      result = result * 59 + ($cbInterruption == null ? 43 : $cbInterruption.hashCode());
      Object $bInterruption = this.getBInterruption();
      result = result * 59 + ($bInterruption == null ? 43 : $bInterruption.hashCode());
      Object $envInterruption = this.getEnvInterruption();
      result = result * 59 + ($envInterruption == null ? 43 : $envInterruption.hashCode());
      Object $arresterInterruption = this.getArresterInterruption();
      result = result * 59 + ($arresterInterruption == null ? 43 : $arresterInterruption.hashCode());
      Object $isolationPressureInterruption = this.getIsolationPressureInterruption();
      result = result * 59 + ($isolationPressureInterruption == null ? 43 : $isolationPressureInterruption.hashCode());
      Object $isolationRemoteSignalingInterruption = this.getIsolationRemoteSignalingInterruption();
      result = result * 59 + ($isolationRemoteSignalingInterruption == null ? 43 : $isolationRemoteSignalingInterruption.hashCode());
      Object $subsectionIsolationThQuarantineClosing = this.getSubsectionIsolationThQuarantineClosing();
      result = result * 59 + ($subsectionIsolationThQuarantineClosing == null ? 43 : $subsectionIsolationThQuarantineClosing.hashCode());
      Object $subsectionIsolationThQuarantineMiddle = this.getSubsectionIsolationThQuarantineMiddle();
      result = result * 59 + ($subsectionIsolationThQuarantineMiddle == null ? 43 : $subsectionIsolationThQuarantineMiddle.hashCode());
      Object $subsectionIsolationThQuarantineOpening = this.getSubsectionIsolationThQuarantineOpening();
      result = result * 59 + ($subsectionIsolationThQuarantineOpening == null ? 43 : $subsectionIsolationThQuarantineOpening.hashCode());
      Object $deviceCode = this.getDeviceCode();
      result = result * 59 + ($deviceCode == null ? 43 : $deviceCode.hashCode());
      Object $createDate = this.getCreateDate();
      return result * 59 + ($createDate == null ? 43 : $createDate.hashCode());
   }

   @Override
   public String toString() {
      return "RawIecRemoteSignalingModel(id="
         + this.getId()
         + ", deviceCode="
         + this.getDeviceCode()
         + ", switchClosedPosition="
         + this.getSwitchClosedPosition()
         + ", switchDivision="
         + this.getSwitchDivision()
         + ", threePositionKnifeClosingPosition="
         + this.getThreePositionKnifeClosingPosition()
         + ", threeStationKnifeClosingPosition="
         + this.getThreeStationKnifeClosingPosition()
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
         + ", moduleSelfTestAbnormality="
         + this.getModuleSelfTestAbnormality()
         + ", abnormalSamplingData="
         + this.getAbnormalSamplingData()
         + ", ramSelfTestAbnormality="
         + this.getRamSelfTestAbnormality()
         + ", openSelfTestAbnormality="
         + this.getOpenSelfTestAbnormality()
         + ", fixedValueSelfTestAbnormality="
         + this.getFixedValueSelfTestAbnormality()
         + ", ptDisconnection="
         + this.getPtDisconnection()
         + ", deviceLocking="
         + this.getDeviceLocking()
         + ", groundingAlarm="
         + this.getGroundingAlarm()
         + ", communicationInterruption="
         + this.getCommunicationInterruption()
         + ", cbInterruption="
         + this.getCbInterruption()
         + ", bInterruption="
         + this.getBInterruption()
         + ", envInterruption="
         + this.getEnvInterruption()
         + ", arresterInterruption="
         + this.getArresterInterruption()
         + ", isolationPressureInterruption="
         + this.getIsolationPressureInterruption()
         + ", isolationRemoteSignalingInterruption="
         + this.getIsolationRemoteSignalingInterruption()
         + ", subsectionIsolationThQuarantineClosing="
         + this.getSubsectionIsolationThQuarantineClosing()
         + ", subsectionIsolationThQuarantineMiddle="
         + this.getSubsectionIsolationThQuarantineMiddle()
         + ", subsectionIsolationThQuarantineOpening="
         + this.getSubsectionIsolationThQuarantineOpening()
         + ", createDate="
         + this.getCreateDate()
         + ")";
   }
}
