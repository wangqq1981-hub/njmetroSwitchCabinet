package com.drsi.njmsc.dto.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TableName("circuit_breaker")
@JsonInclude(Include.NON_NULL)
public class CircuitBreakerModel implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(type = IdType.AUTO)
   private Long id;
   @ApiModelProperty("设备编码")
   private String deviceCode;
   @ApiModelProperty("数据类别（0-合闸 1-分闸）")
   private Integer dataType;
   @ApiModelProperty("储能电机电流id")
   private String energyStorageMotorId;
   private String ps;
   @ApiModelProperty("线圈电流")
   private String truncationData;
   private Float primaryValue;
   private Float secondaryValue;
   private Integer samp;
   @ApiModelProperty("截断开始时间")
   private Date truncationDate;
   @ApiModelProperty("触发点时间")
   private Date triggerPointDate;
   private Float ironCoreStartingCurrent;
   private Float ironCoreStoppingCurrent;
   private Float workingCurrentOfCoil;
   private Float ironCoreStartingTime;
   private Float ironCoreStoppingTime;
   private Float ironCoreWorkingTime;
   private Float actionTime;
   private Date createDate;
   @ApiModelProperty("电流曲线数据")
   private transient List<Float> currentData;

   public Long getId() {
      return this.id;
   }

   public String getDeviceCode() {
      return this.deviceCode;
   }

   public Integer getDataType() {
      return this.dataType;
   }

   public String getEnergyStorageMotorId() {
      return this.energyStorageMotorId;
   }

   public String getPs() {
      return this.ps;
   }

   public String getTruncationData() {
      return this.truncationData;
   }

   public Float getPrimaryValue() {
      return this.primaryValue;
   }

   public Float getSecondaryValue() {
      return this.secondaryValue;
   }

   public Integer getSamp() {
      return this.samp;
   }

   public Date getTruncationDate() {
      return this.truncationDate;
   }

   public Date getTriggerPointDate() {
      return this.triggerPointDate;
   }

   public Float getIronCoreStartingCurrent() {
      return this.ironCoreStartingCurrent;
   }

   public Float getIronCoreStoppingCurrent() {
      return this.ironCoreStoppingCurrent;
   }

   public Float getWorkingCurrentOfCoil() {
      return this.workingCurrentOfCoil;
   }

   public Float getIronCoreStartingTime() {
      return this.ironCoreStartingTime;
   }

   public Float getIronCoreStoppingTime() {
      return this.ironCoreStoppingTime;
   }

   public Float getIronCoreWorkingTime() {
      return this.ironCoreWorkingTime;
   }

   public Float getActionTime() {
      return this.actionTime;
   }

   public Date getCreateDate() {
      return this.createDate;
   }

   public List<Float> getCurrentData() {
      return this.currentData;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public void setDeviceCode(final String deviceCode) {
      this.deviceCode = deviceCode;
   }

   public void setDataType(final Integer dataType) {
      this.dataType = dataType;
   }

   public void setEnergyStorageMotorId(final String energyStorageMotorId) {
      this.energyStorageMotorId = energyStorageMotorId;
   }

   public void setPs(final String ps) {
      this.ps = ps;
   }

   public void setTruncationData(final String truncationData) {
      this.truncationData = truncationData;
   }

   public void setPrimaryValue(final Float primaryValue) {
      this.primaryValue = primaryValue;
   }

   public void setSecondaryValue(final Float secondaryValue) {
      this.secondaryValue = secondaryValue;
   }

   public void setSamp(final Integer samp) {
      this.samp = samp;
   }

   public void setTruncationDate(final Date truncationDate) {
      this.truncationDate = truncationDate;
   }

   public void setTriggerPointDate(final Date triggerPointDate) {
      this.triggerPointDate = triggerPointDate;
   }

   public void setIronCoreStartingCurrent(final Float ironCoreStartingCurrent) {
      this.ironCoreStartingCurrent = ironCoreStartingCurrent;
   }

   public void setIronCoreStoppingCurrent(final Float ironCoreStoppingCurrent) {
      this.ironCoreStoppingCurrent = ironCoreStoppingCurrent;
   }

   public void setWorkingCurrentOfCoil(final Float workingCurrentOfCoil) {
      this.workingCurrentOfCoil = workingCurrentOfCoil;
   }

   public void setIronCoreStartingTime(final Float ironCoreStartingTime) {
      this.ironCoreStartingTime = ironCoreStartingTime;
   }

   public void setIronCoreStoppingTime(final Float ironCoreStoppingTime) {
      this.ironCoreStoppingTime = ironCoreStoppingTime;
   }

   public void setIronCoreWorkingTime(final Float ironCoreWorkingTime) {
      this.ironCoreWorkingTime = ironCoreWorkingTime;
   }

   public void setActionTime(final Float actionTime) {
      this.actionTime = actionTime;
   }

   public void setCreateDate(final Date createDate) {
      this.createDate = createDate;
   }

   public void setCurrentData(final List<Float> currentData) {
      this.currentData = currentData;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof CircuitBreakerModel)) {
         return false;
      }

      CircuitBreakerModel other = (CircuitBreakerModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$id = this.getId();
      Object other$id = other.getId();
      if (this$id == null ? other$id == null : this$id.equals(other$id)) {
         Object this$dataType = this.getDataType();
         Object other$dataType = other.getDataType();
         if (this$dataType == null ? other$dataType == null : this$dataType.equals(other$dataType)) {
            Object this$primaryValue = this.getPrimaryValue();
            Object other$primaryValue = other.getPrimaryValue();
            if (this$primaryValue == null ? other$primaryValue == null : this$primaryValue.equals(other$primaryValue)) {
               Object this$secondaryValue = this.getSecondaryValue();
               Object other$secondaryValue = other.getSecondaryValue();
               if (this$secondaryValue == null ? other$secondaryValue == null : this$secondaryValue.equals(other$secondaryValue)) {
                  Object this$samp = this.getSamp();
                  Object other$samp = other.getSamp();
                  if (this$samp == null ? other$samp == null : this$samp.equals(other$samp)) {
                     Object this$ironCoreStartingCurrent = this.getIronCoreStartingCurrent();
                     Object other$ironCoreStartingCurrent = other.getIronCoreStartingCurrent();
                     if (this$ironCoreStartingCurrent == null
                        ? other$ironCoreStartingCurrent == null
                        : this$ironCoreStartingCurrent.equals(other$ironCoreStartingCurrent)) {
                        Object this$ironCoreStoppingCurrent = this.getIronCoreStoppingCurrent();
                        Object other$ironCoreStoppingCurrent = other.getIronCoreStoppingCurrent();
                        if (this$ironCoreStoppingCurrent == null
                           ? other$ironCoreStoppingCurrent == null
                           : this$ironCoreStoppingCurrent.equals(other$ironCoreStoppingCurrent)) {
                           Object this$workingCurrentOfCoil = this.getWorkingCurrentOfCoil();
                           Object other$workingCurrentOfCoil = other.getWorkingCurrentOfCoil();
                           if (this$workingCurrentOfCoil == null
                              ? other$workingCurrentOfCoil == null
                              : this$workingCurrentOfCoil.equals(other$workingCurrentOfCoil)) {
                              Object this$ironCoreStartingTime = this.getIronCoreStartingTime();
                              Object other$ironCoreStartingTime = other.getIronCoreStartingTime();
                              if (this$ironCoreStartingTime == null
                                 ? other$ironCoreStartingTime == null
                                 : this$ironCoreStartingTime.equals(other$ironCoreStartingTime)) {
                                 Object this$ironCoreStoppingTime = this.getIronCoreStoppingTime();
                                 Object other$ironCoreStoppingTime = other.getIronCoreStoppingTime();
                                 if (this$ironCoreStoppingTime == null
                                    ? other$ironCoreStoppingTime == null
                                    : this$ironCoreStoppingTime.equals(other$ironCoreStoppingTime)) {
                                    Object this$ironCoreWorkingTime = this.getIronCoreWorkingTime();
                                    Object other$ironCoreWorkingTime = other.getIronCoreWorkingTime();
                                    if (this$ironCoreWorkingTime == null
                                       ? other$ironCoreWorkingTime == null
                                       : this$ironCoreWorkingTime.equals(other$ironCoreWorkingTime)) {
                                       Object this$actionTime = this.getActionTime();
                                       Object other$actionTime = other.getActionTime();
                                       if (this$actionTime == null ? other$actionTime == null : this$actionTime.equals(other$actionTime)) {
                                          Object this$deviceCode = this.getDeviceCode();
                                          Object other$deviceCode = other.getDeviceCode();
                                          if (this$deviceCode == null ? other$deviceCode == null : this$deviceCode.equals(other$deviceCode)) {
                                             Object this$energyStorageMotorId = this.getEnergyStorageMotorId();
                                             Object other$energyStorageMotorId = other.getEnergyStorageMotorId();
                                             if (this$energyStorageMotorId == null
                                                ? other$energyStorageMotorId == null
                                                : this$energyStorageMotorId.equals(other$energyStorageMotorId)) {
                                                Object this$ps = this.getPs();
                                                Object other$ps = other.getPs();
                                                if (this$ps == null ? other$ps == null : this$ps.equals(other$ps)) {
                                                   Object this$truncationData = this.getTruncationData();
                                                   Object other$truncationData = other.getTruncationData();
                                                   if (this$truncationData == null
                                                      ? other$truncationData == null
                                                      : this$truncationData.equals(other$truncationData)) {
                                                      Object this$truncationDate = this.getTruncationDate();
                                                      Object other$truncationDate = other.getTruncationDate();
                                                      if (this$truncationDate == null
                                                         ? other$truncationDate == null
                                                         : this$truncationDate.equals(other$truncationDate)) {
                                                         Object this$triggerPointDate = this.getTriggerPointDate();
                                                         Object other$triggerPointDate = other.getTriggerPointDate();
                                                         if (this$triggerPointDate == null
                                                            ? other$triggerPointDate == null
                                                            : this$triggerPointDate.equals(other$triggerPointDate)) {
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
   }

   protected boolean canEqual(final Object other) {
      return other instanceof CircuitBreakerModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $dataType = this.getDataType();
      result = result * 59 + ($dataType == null ? 43 : $dataType.hashCode());
      Object $primaryValue = this.getPrimaryValue();
      result = result * 59 + ($primaryValue == null ? 43 : $primaryValue.hashCode());
      Object $secondaryValue = this.getSecondaryValue();
      result = result * 59 + ($secondaryValue == null ? 43 : $secondaryValue.hashCode());
      Object $samp = this.getSamp();
      result = result * 59 + ($samp == null ? 43 : $samp.hashCode());
      Object $ironCoreStartingCurrent = this.getIronCoreStartingCurrent();
      result = result * 59 + ($ironCoreStartingCurrent == null ? 43 : $ironCoreStartingCurrent.hashCode());
      Object $ironCoreStoppingCurrent = this.getIronCoreStoppingCurrent();
      result = result * 59 + ($ironCoreStoppingCurrent == null ? 43 : $ironCoreStoppingCurrent.hashCode());
      Object $workingCurrentOfCoil = this.getWorkingCurrentOfCoil();
      result = result * 59 + ($workingCurrentOfCoil == null ? 43 : $workingCurrentOfCoil.hashCode());
      Object $ironCoreStartingTime = this.getIronCoreStartingTime();
      result = result * 59 + ($ironCoreStartingTime == null ? 43 : $ironCoreStartingTime.hashCode());
      Object $ironCoreStoppingTime = this.getIronCoreStoppingTime();
      result = result * 59 + ($ironCoreStoppingTime == null ? 43 : $ironCoreStoppingTime.hashCode());
      Object $ironCoreWorkingTime = this.getIronCoreWorkingTime();
      result = result * 59 + ($ironCoreWorkingTime == null ? 43 : $ironCoreWorkingTime.hashCode());
      Object $actionTime = this.getActionTime();
      result = result * 59 + ($actionTime == null ? 43 : $actionTime.hashCode());
      Object $deviceCode = this.getDeviceCode();
      result = result * 59 + ($deviceCode == null ? 43 : $deviceCode.hashCode());
      Object $energyStorageMotorId = this.getEnergyStorageMotorId();
      result = result * 59 + ($energyStorageMotorId == null ? 43 : $energyStorageMotorId.hashCode());
      Object $ps = this.getPs();
      result = result * 59 + ($ps == null ? 43 : $ps.hashCode());
      Object $truncationData = this.getTruncationData();
      result = result * 59 + ($truncationData == null ? 43 : $truncationData.hashCode());
      Object $truncationDate = this.getTruncationDate();
      result = result * 59 + ($truncationDate == null ? 43 : $truncationDate.hashCode());
      Object $triggerPointDate = this.getTriggerPointDate();
      result = result * 59 + ($triggerPointDate == null ? 43 : $triggerPointDate.hashCode());
      Object $createDate = this.getCreateDate();
      return result * 59 + ($createDate == null ? 43 : $createDate.hashCode());
   }

   @Override
   public String toString() {
      return "CircuitBreakerModel(id="
         + this.getId()
         + ", deviceCode="
         + this.getDeviceCode()
         + ", dataType="
         + this.getDataType()
         + ", energyStorageMotorId="
         + this.getEnergyStorageMotorId()
         + ", ps="
         + this.getPs()
         + ", truncationData="
         + this.getTruncationData()
         + ", primaryValue="
         + this.getPrimaryValue()
         + ", secondaryValue="
         + this.getSecondaryValue()
         + ", samp="
         + this.getSamp()
         + ", truncationDate="
         + this.getTruncationDate()
         + ", triggerPointDate="
         + this.getTriggerPointDate()
         + ", ironCoreStartingCurrent="
         + this.getIronCoreStartingCurrent()
         + ", ironCoreStoppingCurrent="
         + this.getIronCoreStoppingCurrent()
         + ", workingCurrentOfCoil="
         + this.getWorkingCurrentOfCoil()
         + ", ironCoreStartingTime="
         + this.getIronCoreStartingTime()
         + ", ironCoreStoppingTime="
         + this.getIronCoreStoppingTime()
         + ", ironCoreWorkingTime="
         + this.getIronCoreWorkingTime()
         + ", actionTime="
         + this.getActionTime()
         + ", createDate="
         + this.getCreateDate()
         + ", currentData="
         + this.getCurrentData()
         + ")";
   }
}
