package com.drsi.njmsc.dto.dto;

import io.swagger.annotations.ApiModelProperty;

public class MonitorIndexDto {
   @ApiModelProperty("设备编码")
   private String deviceCode;
   @ApiModelProperty("分闸线圈电流")
   private Float openingCoilCurrent;
   @ApiModelProperty("合闸线圈电流")
   private Float closingCoilCurrent;
   @ApiModelProperty("储能电机电流")
   private Float energyStorageMotorCurrent;
   @ApiModelProperty("三工位1电流")
   private Float threeStationsOneCurrent;
   @ApiModelProperty("三工位2电流")
   private Float threeStationsTwoCurrent;
   @ApiModelProperty("断路器室温度")
   private Float tempOfCbr;
   @ApiModelProperty("断路器室压力")
   private Float pressureOfCbr;
   @ApiModelProperty("断路器室密度")
   private Float densityOfCbr;
   @ApiModelProperty("母线室温度")
   private Float tempOfBr;
   @ApiModelProperty("母线室压力")
   private Float pressureOfBr;
   @ApiModelProperty("母线室密度")
   private Float densityOfBr;
   @ApiModelProperty("环境温度")
   private Float tempOfEnv;
   @ApiModelProperty("环境湿度")
   private Float humOfEnv;
   @ApiModelProperty("A相放电计数值")
   private Float dischargeCurrentMeterA;
   @ApiModelProperty("A相泄露电流值")
   private Float leakageCurrentMeterA;
   @ApiModelProperty("B相放电计数值")
   private Float dischargeCurrentMeterB;
   @ApiModelProperty("B相泄露电流值")
   private Float leakageCurrentMeterB;
   @ApiModelProperty("C相放电计数值")
   private Float dischargeCurrentMeterC;
   @ApiModelProperty("C相泄露电流值")
   private Float leakageCurrentMeterC;
   @ApiModelProperty("断路器合闸次数")
   private Long cbCloseSwitch;
   @ApiModelProperty("断路器分闸次数")
   private Long cbOpenSwitch;
   private Integer remainingLife;
   private Integer remainingNum;
   @ApiModelProperty("三工位隔离动作次数")
   private Long tsQuarantineSwitch;
   @ApiModelProperty("三工位接地动作次数")
   private Long tsGroundingSwitch;
   @ApiModelProperty("创建时间")
   private Long createDate;
   private Float startingCurrent;
   private Float idleElectricCurrent;
   private Float outputCurrent;
   private Float startingTime;
   private Float idleElectricTime;
   private Float outputTime;
   private Float actionTime;

   public String getDeviceCode() {
      return this.deviceCode;
   }

   public Float getOpeningCoilCurrent() {
      return this.openingCoilCurrent;
   }

   public Float getClosingCoilCurrent() {
      return this.closingCoilCurrent;
   }

   public Float getEnergyStorageMotorCurrent() {
      return this.energyStorageMotorCurrent;
   }

   public Float getThreeStationsOneCurrent() {
      return this.threeStationsOneCurrent;
   }

   public Float getThreeStationsTwoCurrent() {
      return this.threeStationsTwoCurrent;
   }

   public Float getTempOfCbr() {
      return this.tempOfCbr;
   }

   public Float getPressureOfCbr() {
      return this.pressureOfCbr;
   }

   public Float getDensityOfCbr() {
      return this.densityOfCbr;
   }

   public Float getTempOfBr() {
      return this.tempOfBr;
   }

   public Float getPressureOfBr() {
      return this.pressureOfBr;
   }

   public Float getDensityOfBr() {
      return this.densityOfBr;
   }

   public Float getTempOfEnv() {
      return this.tempOfEnv;
   }

   public Float getHumOfEnv() {
      return this.humOfEnv;
   }

   public Float getDischargeCurrentMeterA() {
      return this.dischargeCurrentMeterA;
   }

   public Float getLeakageCurrentMeterA() {
      return this.leakageCurrentMeterA;
   }

   public Float getDischargeCurrentMeterB() {
      return this.dischargeCurrentMeterB;
   }

   public Float getLeakageCurrentMeterB() {
      return this.leakageCurrentMeterB;
   }

   public Float getDischargeCurrentMeterC() {
      return this.dischargeCurrentMeterC;
   }

   public Float getLeakageCurrentMeterC() {
      return this.leakageCurrentMeterC;
   }

   public Long getCbCloseSwitch() {
      return this.cbCloseSwitch;
   }

   public Long getCbOpenSwitch() {
      return this.cbOpenSwitch;
   }

   public Integer getRemainingLife() {
      return this.remainingLife;
   }

   public Integer getRemainingNum() {
      return this.remainingNum;
   }

   public Long getTsQuarantineSwitch() {
      return this.tsQuarantineSwitch;
   }

   public Long getTsGroundingSwitch() {
      return this.tsGroundingSwitch;
   }

   public Long getCreateDate() {
      return this.createDate;
   }

   public Float getStartingCurrent() {
      return this.startingCurrent;
   }

   public Float getIdleElectricCurrent() {
      return this.idleElectricCurrent;
   }

   public Float getOutputCurrent() {
      return this.outputCurrent;
   }

   public Float getStartingTime() {
      return this.startingTime;
   }

   public Float getIdleElectricTime() {
      return this.idleElectricTime;
   }

   public Float getOutputTime() {
      return this.outputTime;
   }

   public Float getActionTime() {
      return this.actionTime;
   }

   public void setDeviceCode(final String deviceCode) {
      this.deviceCode = deviceCode;
   }

   public void setOpeningCoilCurrent(final Float openingCoilCurrent) {
      this.openingCoilCurrent = openingCoilCurrent;
   }

   public void setClosingCoilCurrent(final Float closingCoilCurrent) {
      this.closingCoilCurrent = closingCoilCurrent;
   }

   public void setEnergyStorageMotorCurrent(final Float energyStorageMotorCurrent) {
      this.energyStorageMotorCurrent = energyStorageMotorCurrent;
   }

   public void setThreeStationsOneCurrent(final Float threeStationsOneCurrent) {
      this.threeStationsOneCurrent = threeStationsOneCurrent;
   }

   public void setThreeStationsTwoCurrent(final Float threeStationsTwoCurrent) {
      this.threeStationsTwoCurrent = threeStationsTwoCurrent;
   }

   public void setTempOfCbr(final Float tempOfCbr) {
      this.tempOfCbr = tempOfCbr;
   }

   public void setPressureOfCbr(final Float pressureOfCbr) {
      this.pressureOfCbr = pressureOfCbr;
   }

   public void setDensityOfCbr(final Float densityOfCbr) {
      this.densityOfCbr = densityOfCbr;
   }

   public void setTempOfBr(final Float tempOfBr) {
      this.tempOfBr = tempOfBr;
   }

   public void setPressureOfBr(final Float pressureOfBr) {
      this.pressureOfBr = pressureOfBr;
   }

   public void setDensityOfBr(final Float densityOfBr) {
      this.densityOfBr = densityOfBr;
   }

   public void setTempOfEnv(final Float tempOfEnv) {
      this.tempOfEnv = tempOfEnv;
   }

   public void setHumOfEnv(final Float humOfEnv) {
      this.humOfEnv = humOfEnv;
   }

   public void setDischargeCurrentMeterA(final Float dischargeCurrentMeterA) {
      this.dischargeCurrentMeterA = dischargeCurrentMeterA;
   }

   public void setLeakageCurrentMeterA(final Float leakageCurrentMeterA) {
      this.leakageCurrentMeterA = leakageCurrentMeterA;
   }

   public void setDischargeCurrentMeterB(final Float dischargeCurrentMeterB) {
      this.dischargeCurrentMeterB = dischargeCurrentMeterB;
   }

   public void setLeakageCurrentMeterB(final Float leakageCurrentMeterB) {
      this.leakageCurrentMeterB = leakageCurrentMeterB;
   }

   public void setDischargeCurrentMeterC(final Float dischargeCurrentMeterC) {
      this.dischargeCurrentMeterC = dischargeCurrentMeterC;
   }

   public void setLeakageCurrentMeterC(final Float leakageCurrentMeterC) {
      this.leakageCurrentMeterC = leakageCurrentMeterC;
   }

   public void setCbCloseSwitch(final Long cbCloseSwitch) {
      this.cbCloseSwitch = cbCloseSwitch;
   }

   public void setCbOpenSwitch(final Long cbOpenSwitch) {
      this.cbOpenSwitch = cbOpenSwitch;
   }

   public void setRemainingLife(final Integer remainingLife) {
      this.remainingLife = remainingLife;
   }

   public void setRemainingNum(final Integer remainingNum) {
      this.remainingNum = remainingNum;
   }

   public void setTsQuarantineSwitch(final Long tsQuarantineSwitch) {
      this.tsQuarantineSwitch = tsQuarantineSwitch;
   }

   public void setTsGroundingSwitch(final Long tsGroundingSwitch) {
      this.tsGroundingSwitch = tsGroundingSwitch;
   }

   public void setCreateDate(final Long createDate) {
      this.createDate = createDate;
   }

   public void setStartingCurrent(final Float startingCurrent) {
      this.startingCurrent = startingCurrent;
   }

   public void setIdleElectricCurrent(final Float idleElectricCurrent) {
      this.idleElectricCurrent = idleElectricCurrent;
   }

   public void setOutputCurrent(final Float outputCurrent) {
      this.outputCurrent = outputCurrent;
   }

   public void setStartingTime(final Float startingTime) {
      this.startingTime = startingTime;
   }

   public void setIdleElectricTime(final Float idleElectricTime) {
      this.idleElectricTime = idleElectricTime;
   }

   public void setOutputTime(final Float outputTime) {
      this.outputTime = outputTime;
   }

   public void setActionTime(final Float actionTime) {
      this.actionTime = actionTime;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof MonitorIndexDto)) {
         return false;
      }

      MonitorIndexDto other = (MonitorIndexDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$openingCoilCurrent = this.getOpeningCoilCurrent();
      Object other$openingCoilCurrent = other.getOpeningCoilCurrent();
      if (this$openingCoilCurrent == null ? other$openingCoilCurrent == null : this$openingCoilCurrent.equals(other$openingCoilCurrent)) {
         Object this$closingCoilCurrent = this.getClosingCoilCurrent();
         Object other$closingCoilCurrent = other.getClosingCoilCurrent();
         if (this$closingCoilCurrent == null ? other$closingCoilCurrent == null : this$closingCoilCurrent.equals(other$closingCoilCurrent)) {
            Object this$energyStorageMotorCurrent = this.getEnergyStorageMotorCurrent();
            Object other$energyStorageMotorCurrent = other.getEnergyStorageMotorCurrent();
            if (this$energyStorageMotorCurrent == null
               ? other$energyStorageMotorCurrent == null
               : this$energyStorageMotorCurrent.equals(other$energyStorageMotorCurrent)) {
               Object this$threeStationsOneCurrent = this.getThreeStationsOneCurrent();
               Object other$threeStationsOneCurrent = other.getThreeStationsOneCurrent();
               if (this$threeStationsOneCurrent == null
                  ? other$threeStationsOneCurrent == null
                  : this$threeStationsOneCurrent.equals(other$threeStationsOneCurrent)) {
                  Object this$threeStationsTwoCurrent = this.getThreeStationsTwoCurrent();
                  Object other$threeStationsTwoCurrent = other.getThreeStationsTwoCurrent();
                  if (this$threeStationsTwoCurrent == null
                     ? other$threeStationsTwoCurrent == null
                     : this$threeStationsTwoCurrent.equals(other$threeStationsTwoCurrent)) {
                     Object this$tempOfCbr = this.getTempOfCbr();
                     Object other$tempOfCbr = other.getTempOfCbr();
                     if (this$tempOfCbr == null ? other$tempOfCbr == null : this$tempOfCbr.equals(other$tempOfCbr)) {
                        Object this$pressureOfCbr = this.getPressureOfCbr();
                        Object other$pressureOfCbr = other.getPressureOfCbr();
                        if (this$pressureOfCbr == null ? other$pressureOfCbr == null : this$pressureOfCbr.equals(other$pressureOfCbr)) {
                           Object this$densityOfCbr = this.getDensityOfCbr();
                           Object other$densityOfCbr = other.getDensityOfCbr();
                           if (this$densityOfCbr == null ? other$densityOfCbr == null : this$densityOfCbr.equals(other$densityOfCbr)) {
                              Object this$tempOfBr = this.getTempOfBr();
                              Object other$tempOfBr = other.getTempOfBr();
                              if (this$tempOfBr == null ? other$tempOfBr == null : this$tempOfBr.equals(other$tempOfBr)) {
                                 Object this$pressureOfBr = this.getPressureOfBr();
                                 Object other$pressureOfBr = other.getPressureOfBr();
                                 if (this$pressureOfBr == null ? other$pressureOfBr == null : this$pressureOfBr.equals(other$pressureOfBr)) {
                                    Object this$densityOfBr = this.getDensityOfBr();
                                    Object other$densityOfBr = other.getDensityOfBr();
                                    if (this$densityOfBr == null ? other$densityOfBr == null : this$densityOfBr.equals(other$densityOfBr)) {
                                       Object this$tempOfEnv = this.getTempOfEnv();
                                       Object other$tempOfEnv = other.getTempOfEnv();
                                       if (this$tempOfEnv == null ? other$tempOfEnv == null : this$tempOfEnv.equals(other$tempOfEnv)) {
                                          Object this$humOfEnv = this.getHumOfEnv();
                                          Object other$humOfEnv = other.getHumOfEnv();
                                          if (this$humOfEnv == null ? other$humOfEnv == null : this$humOfEnv.equals(other$humOfEnv)) {
                                             Object this$dischargeCurrentMeterA = this.getDischargeCurrentMeterA();
                                             Object other$dischargeCurrentMeterA = other.getDischargeCurrentMeterA();
                                             if (this$dischargeCurrentMeterA == null
                                                ? other$dischargeCurrentMeterA == null
                                                : this$dischargeCurrentMeterA.equals(other$dischargeCurrentMeterA)) {
                                                Object this$leakageCurrentMeterA = this.getLeakageCurrentMeterA();
                                                Object other$leakageCurrentMeterA = other.getLeakageCurrentMeterA();
                                                if (this$leakageCurrentMeterA == null
                                                   ? other$leakageCurrentMeterA == null
                                                   : this$leakageCurrentMeterA.equals(other$leakageCurrentMeterA)) {
                                                   Object this$dischargeCurrentMeterB = this.getDischargeCurrentMeterB();
                                                   Object other$dischargeCurrentMeterB = other.getDischargeCurrentMeterB();
                                                   if (this$dischargeCurrentMeterB == null
                                                      ? other$dischargeCurrentMeterB == null
                                                      : this$dischargeCurrentMeterB.equals(other$dischargeCurrentMeterB)) {
                                                      Object this$leakageCurrentMeterB = this.getLeakageCurrentMeterB();
                                                      Object other$leakageCurrentMeterB = other.getLeakageCurrentMeterB();
                                                      if (this$leakageCurrentMeterB == null
                                                         ? other$leakageCurrentMeterB == null
                                                         : this$leakageCurrentMeterB.equals(other$leakageCurrentMeterB)) {
                                                         Object this$dischargeCurrentMeterC = this.getDischargeCurrentMeterC();
                                                         Object other$dischargeCurrentMeterC = other.getDischargeCurrentMeterC();
                                                         if (this$dischargeCurrentMeterC == null
                                                            ? other$dischargeCurrentMeterC == null
                                                            : this$dischargeCurrentMeterC.equals(other$dischargeCurrentMeterC)) {
                                                            Object this$leakageCurrentMeterC = this.getLeakageCurrentMeterC();
                                                            Object other$leakageCurrentMeterC = other.getLeakageCurrentMeterC();
                                                            if (this$leakageCurrentMeterC == null
                                                               ? other$leakageCurrentMeterC == null
                                                               : this$leakageCurrentMeterC.equals(other$leakageCurrentMeterC)) {
                                                               Object this$cbCloseSwitch = this.getCbCloseSwitch();
                                                               Object other$cbCloseSwitch = other.getCbCloseSwitch();
                                                               if (this$cbCloseSwitch == null
                                                                  ? other$cbCloseSwitch == null
                                                                  : this$cbCloseSwitch.equals(other$cbCloseSwitch)) {
                                                                  Object this$cbOpenSwitch = this.getCbOpenSwitch();
                                                                  Object other$cbOpenSwitch = other.getCbOpenSwitch();
                                                                  if (this$cbOpenSwitch == null
                                                                     ? other$cbOpenSwitch == null
                                                                     : this$cbOpenSwitch.equals(other$cbOpenSwitch)) {
                                                                     Object this$remainingLife = this.getRemainingLife();
                                                                     Object other$remainingLife = other.getRemainingLife();
                                                                     if (this$remainingLife == null
                                                                        ? other$remainingLife == null
                                                                        : this$remainingLife.equals(other$remainingLife)) {
                                                                        Object this$remainingNum = this.getRemainingNum();
                                                                        Object other$remainingNum = other.getRemainingNum();
                                                                        if (this$remainingNum == null
                                                                           ? other$remainingNum == null
                                                                           : this$remainingNum.equals(other$remainingNum)) {
                                                                           Object this$tsQuarantineSwitch = this.getTsQuarantineSwitch();
                                                                           Object other$tsQuarantineSwitch = other.getTsQuarantineSwitch();
                                                                           if (this$tsQuarantineSwitch == null
                                                                              ? other$tsQuarantineSwitch == null
                                                                              : this$tsQuarantineSwitch.equals(other$tsQuarantineSwitch)) {
                                                                              Object this$tsGroundingSwitch = this.getTsGroundingSwitch();
                                                                              Object other$tsGroundingSwitch = other.getTsGroundingSwitch();
                                                                              if (this$tsGroundingSwitch == null
                                                                                 ? other$tsGroundingSwitch == null
                                                                                 : this$tsGroundingSwitch.equals(other$tsGroundingSwitch)) {
                                                                                 Object this$createDate = this.getCreateDate();
                                                                                 Object other$createDate = other.getCreateDate();
                                                                                 if (this$createDate == null
                                                                                    ? other$createDate == null
                                                                                    : this$createDate.equals(other$createDate)) {
                                                                                    Object this$startingCurrent = this.getStartingCurrent();
                                                                                    Object other$startingCurrent = other.getStartingCurrent();
                                                                                    if (this$startingCurrent == null
                                                                                       ? other$startingCurrent == null
                                                                                       : this$startingCurrent.equals(other$startingCurrent)) {
                                                                                       Object this$idleElectricCurrent = this.getIdleElectricCurrent();
                                                                                       Object other$idleElectricCurrent = other.getIdleElectricCurrent();
                                                                                       if (this$idleElectricCurrent == null
                                                                                          ? other$idleElectricCurrent == null
                                                                                          : this$idleElectricCurrent.equals(other$idleElectricCurrent)) {
                                                                                          Object this$outputCurrent = this.getOutputCurrent();
                                                                                          Object other$outputCurrent = other.getOutputCurrent();
                                                                                          if (this$outputCurrent == null
                                                                                             ? other$outputCurrent == null
                                                                                             : this$outputCurrent.equals(other$outputCurrent)) {
                                                                                             Object this$startingTime = this.getStartingTime();
                                                                                             Object other$startingTime = other.getStartingTime();
                                                                                             if (this$startingTime == null
                                                                                                ? other$startingTime == null
                                                                                                : this$startingTime.equals(other$startingTime)) {
                                                                                                Object this$idleElectricTime = this.getIdleElectricTime();
                                                                                                Object other$idleElectricTime = other.getIdleElectricTime();
                                                                                                if (this$idleElectricTime == null
                                                                                                   ? other$idleElectricTime == null
                                                                                                   : this$idleElectricTime.equals(other$idleElectricTime)) {
                                                                                                   Object this$outputTime = this.getOutputTime();
                                                                                                   Object other$outputTime = other.getOutputTime();
                                                                                                   if (this$outputTime == null
                                                                                                      ? other$outputTime == null
                                                                                                      : this$outputTime.equals(other$outputTime)) {
                                                                                                      Object this$actionTime = this.getActionTime();
                                                                                                      Object other$actionTime = other.getActionTime();
                                                                                                      if (this$actionTime == null
                                                                                                         ? other$actionTime == null
                                                                                                         : this$actionTime.equals(other$actionTime)) {
                                                                                                         Object this$deviceCode = this.getDeviceCode();
                                                                                                         Object other$deviceCode = other.getDeviceCode();
                                                                                                         return this$deviceCode == null
                                                                                                            ? other$deviceCode == null
                                                                                                            : this$deviceCode.equals(other$deviceCode);
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
      return other instanceof MonitorIndexDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $openingCoilCurrent = this.getOpeningCoilCurrent();
      result = result * 59 + ($openingCoilCurrent == null ? 43 : $openingCoilCurrent.hashCode());
      Object $closingCoilCurrent = this.getClosingCoilCurrent();
      result = result * 59 + ($closingCoilCurrent == null ? 43 : $closingCoilCurrent.hashCode());
      Object $energyStorageMotorCurrent = this.getEnergyStorageMotorCurrent();
      result = result * 59 + ($energyStorageMotorCurrent == null ? 43 : $energyStorageMotorCurrent.hashCode());
      Object $threeStationsOneCurrent = this.getThreeStationsOneCurrent();
      result = result * 59 + ($threeStationsOneCurrent == null ? 43 : $threeStationsOneCurrent.hashCode());
      Object $threeStationsTwoCurrent = this.getThreeStationsTwoCurrent();
      result = result * 59 + ($threeStationsTwoCurrent == null ? 43 : $threeStationsTwoCurrent.hashCode());
      Object $tempOfCbr = this.getTempOfCbr();
      result = result * 59 + ($tempOfCbr == null ? 43 : $tempOfCbr.hashCode());
      Object $pressureOfCbr = this.getPressureOfCbr();
      result = result * 59 + ($pressureOfCbr == null ? 43 : $pressureOfCbr.hashCode());
      Object $densityOfCbr = this.getDensityOfCbr();
      result = result * 59 + ($densityOfCbr == null ? 43 : $densityOfCbr.hashCode());
      Object $tempOfBr = this.getTempOfBr();
      result = result * 59 + ($tempOfBr == null ? 43 : $tempOfBr.hashCode());
      Object $pressureOfBr = this.getPressureOfBr();
      result = result * 59 + ($pressureOfBr == null ? 43 : $pressureOfBr.hashCode());
      Object $densityOfBr = this.getDensityOfBr();
      result = result * 59 + ($densityOfBr == null ? 43 : $densityOfBr.hashCode());
      Object $tempOfEnv = this.getTempOfEnv();
      result = result * 59 + ($tempOfEnv == null ? 43 : $tempOfEnv.hashCode());
      Object $humOfEnv = this.getHumOfEnv();
      result = result * 59 + ($humOfEnv == null ? 43 : $humOfEnv.hashCode());
      Object $dischargeCurrentMeterA = this.getDischargeCurrentMeterA();
      result = result * 59 + ($dischargeCurrentMeterA == null ? 43 : $dischargeCurrentMeterA.hashCode());
      Object $leakageCurrentMeterA = this.getLeakageCurrentMeterA();
      result = result * 59 + ($leakageCurrentMeterA == null ? 43 : $leakageCurrentMeterA.hashCode());
      Object $dischargeCurrentMeterB = this.getDischargeCurrentMeterB();
      result = result * 59 + ($dischargeCurrentMeterB == null ? 43 : $dischargeCurrentMeterB.hashCode());
      Object $leakageCurrentMeterB = this.getLeakageCurrentMeterB();
      result = result * 59 + ($leakageCurrentMeterB == null ? 43 : $leakageCurrentMeterB.hashCode());
      Object $dischargeCurrentMeterC = this.getDischargeCurrentMeterC();
      result = result * 59 + ($dischargeCurrentMeterC == null ? 43 : $dischargeCurrentMeterC.hashCode());
      Object $leakageCurrentMeterC = this.getLeakageCurrentMeterC();
      result = result * 59 + ($leakageCurrentMeterC == null ? 43 : $leakageCurrentMeterC.hashCode());
      Object $cbCloseSwitch = this.getCbCloseSwitch();
      result = result * 59 + ($cbCloseSwitch == null ? 43 : $cbCloseSwitch.hashCode());
      Object $cbOpenSwitch = this.getCbOpenSwitch();
      result = result * 59 + ($cbOpenSwitch == null ? 43 : $cbOpenSwitch.hashCode());
      Object $remainingLife = this.getRemainingLife();
      result = result * 59 + ($remainingLife == null ? 43 : $remainingLife.hashCode());
      Object $remainingNum = this.getRemainingNum();
      result = result * 59 + ($remainingNum == null ? 43 : $remainingNum.hashCode());
      Object $tsQuarantineSwitch = this.getTsQuarantineSwitch();
      result = result * 59 + ($tsQuarantineSwitch == null ? 43 : $tsQuarantineSwitch.hashCode());
      Object $tsGroundingSwitch = this.getTsGroundingSwitch();
      result = result * 59 + ($tsGroundingSwitch == null ? 43 : $tsGroundingSwitch.hashCode());
      Object $createDate = this.getCreateDate();
      result = result * 59 + ($createDate == null ? 43 : $createDate.hashCode());
      Object $startingCurrent = this.getStartingCurrent();
      result = result * 59 + ($startingCurrent == null ? 43 : $startingCurrent.hashCode());
      Object $idleElectricCurrent = this.getIdleElectricCurrent();
      result = result * 59 + ($idleElectricCurrent == null ? 43 : $idleElectricCurrent.hashCode());
      Object $outputCurrent = this.getOutputCurrent();
      result = result * 59 + ($outputCurrent == null ? 43 : $outputCurrent.hashCode());
      Object $startingTime = this.getStartingTime();
      result = result * 59 + ($startingTime == null ? 43 : $startingTime.hashCode());
      Object $idleElectricTime = this.getIdleElectricTime();
      result = result * 59 + ($idleElectricTime == null ? 43 : $idleElectricTime.hashCode());
      Object $outputTime = this.getOutputTime();
      result = result * 59 + ($outputTime == null ? 43 : $outputTime.hashCode());
      Object $actionTime = this.getActionTime();
      result = result * 59 + ($actionTime == null ? 43 : $actionTime.hashCode());
      Object $deviceCode = this.getDeviceCode();
      return result * 59 + ($deviceCode == null ? 43 : $deviceCode.hashCode());
   }

   @Override
   public String toString() {
      return "MonitorIndexDto(deviceCode="
         + this.getDeviceCode()
         + ", openingCoilCurrent="
         + this.getOpeningCoilCurrent()
         + ", closingCoilCurrent="
         + this.getClosingCoilCurrent()
         + ", energyStorageMotorCurrent="
         + this.getEnergyStorageMotorCurrent()
         + ", threeStationsOneCurrent="
         + this.getThreeStationsOneCurrent()
         + ", threeStationsTwoCurrent="
         + this.getThreeStationsTwoCurrent()
         + ", tempOfCbr="
         + this.getTempOfCbr()
         + ", pressureOfCbr="
         + this.getPressureOfCbr()
         + ", densityOfCbr="
         + this.getDensityOfCbr()
         + ", tempOfBr="
         + this.getTempOfBr()
         + ", pressureOfBr="
         + this.getPressureOfBr()
         + ", densityOfBr="
         + this.getDensityOfBr()
         + ", tempOfEnv="
         + this.getTempOfEnv()
         + ", humOfEnv="
         + this.getHumOfEnv()
         + ", dischargeCurrentMeterA="
         + this.getDischargeCurrentMeterA()
         + ", leakageCurrentMeterA="
         + this.getLeakageCurrentMeterA()
         + ", dischargeCurrentMeterB="
         + this.getDischargeCurrentMeterB()
         + ", leakageCurrentMeterB="
         + this.getLeakageCurrentMeterB()
         + ", dischargeCurrentMeterC="
         + this.getDischargeCurrentMeterC()
         + ", leakageCurrentMeterC="
         + this.getLeakageCurrentMeterC()
         + ", cbCloseSwitch="
         + this.getCbCloseSwitch()
         + ", cbOpenSwitch="
         + this.getCbOpenSwitch()
         + ", remainingLife="
         + this.getRemainingLife()
         + ", remainingNum="
         + this.getRemainingNum()
         + ", tsQuarantineSwitch="
         + this.getTsQuarantineSwitch()
         + ", tsGroundingSwitch="
         + this.getTsGroundingSwitch()
         + ", createDate="
         + this.getCreateDate()
         + ", startingCurrent="
         + this.getStartingCurrent()
         + ", idleElectricCurrent="
         + this.getIdleElectricCurrent()
         + ", outputCurrent="
         + this.getOutputCurrent()
         + ", startingTime="
         + this.getStartingTime()
         + ", idleElectricTime="
         + this.getIdleElectricTime()
         + ", outputTime="
         + this.getOutputTime()
         + ", actionTime="
         + this.getActionTime()
         + ")";
   }
}
