package com.drsi.njmsc.dto.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@TableName("raw_iec_telemetry")
@JsonInclude(Include.NON_NULL)
public class RawIecTelemetryModel implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(type = IdType.AUTO)
   private Long id;
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
   @ApiModelProperty("母联-隔离温度")
   private Float tempOfMui;
   @ApiModelProperty("母联-隔离压力")
   private Float pressureOfMui;
   @ApiModelProperty("母联-隔离密度")
   private Float densityOfMui;
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
   @ApiModelProperty("创建时间")
   private Date createDate;

   public Long getId() {
      return this.id;
   }

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

   public Float getTempOfMui() {
      return this.tempOfMui;
   }

   public Float getPressureOfMui() {
      return this.pressureOfMui;
   }

   public Float getDensityOfMui() {
      return this.densityOfMui;
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

   public Date getCreateDate() {
      return this.createDate;
   }

   public void setId(final Long id) {
      this.id = id;
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

   public void setTempOfMui(final Float tempOfMui) {
      this.tempOfMui = tempOfMui;
   }

   public void setPressureOfMui(final Float pressureOfMui) {
      this.pressureOfMui = pressureOfMui;
   }

   public void setDensityOfMui(final Float densityOfMui) {
      this.densityOfMui = densityOfMui;
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

   public void setCreateDate(final Date createDate) {
      this.createDate = createDate;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof RawIecTelemetryModel)) {
         return false;
      }

      RawIecTelemetryModel other = (RawIecTelemetryModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$id = this.getId();
      Object other$id = other.getId();
      if (this$id == null ? other$id == null : this$id.equals(other$id)) {
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
                                                Object this$tempOfMui = this.getTempOfMui();
                                                Object other$tempOfMui = other.getTempOfMui();
                                                if (this$tempOfMui == null ? other$tempOfMui == null : this$tempOfMui.equals(other$tempOfMui)) {
                                                   Object this$pressureOfMui = this.getPressureOfMui();
                                                   Object other$pressureOfMui = other.getPressureOfMui();
                                                   if (this$pressureOfMui == null
                                                      ? other$pressureOfMui == null
                                                      : this$pressureOfMui.equals(other$pressureOfMui)) {
                                                      Object this$densityOfMui = this.getDensityOfMui();
                                                      Object other$densityOfMui = other.getDensityOfMui();
                                                      if (this$densityOfMui == null ? other$densityOfMui == null : this$densityOfMui.equals(other$densityOfMui)
                                                         )
                                                       {
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
   }

   protected boolean canEqual(final Object other) {
      return other instanceof RawIecTelemetryModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
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
      Object $tempOfMui = this.getTempOfMui();
      result = result * 59 + ($tempOfMui == null ? 43 : $tempOfMui.hashCode());
      Object $pressureOfMui = this.getPressureOfMui();
      result = result * 59 + ($pressureOfMui == null ? 43 : $pressureOfMui.hashCode());
      Object $densityOfMui = this.getDensityOfMui();
      result = result * 59 + ($densityOfMui == null ? 43 : $densityOfMui.hashCode());
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
      Object $deviceCode = this.getDeviceCode();
      result = result * 59 + ($deviceCode == null ? 43 : $deviceCode.hashCode());
      Object $createDate = this.getCreateDate();
      return result * 59 + ($createDate == null ? 43 : $createDate.hashCode());
   }

   @Override
   public String toString() {
      return "RawIecTelemetryModel(id="
         + this.getId()
         + ", deviceCode="
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
         + ", tempOfMui="
         + this.getTempOfMui()
         + ", pressureOfMui="
         + this.getPressureOfMui()
         + ", densityOfMui="
         + this.getDensityOfMui()
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
         + ", createDate="
         + this.getCreateDate()
         + ")";
   }
}
