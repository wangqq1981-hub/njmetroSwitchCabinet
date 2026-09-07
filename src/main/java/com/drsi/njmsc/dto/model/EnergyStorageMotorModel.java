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

@TableName("energy_storage_motor")
@JsonInclude(Include.NON_NULL)
public class EnergyStorageMotorModel implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(type = IdType.AUTO)
   private Long id;
   @ApiModelProperty("设备编码")
   private String deviceCode;
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
   private Float startingCurrent;
   private Float idleElectricCurrent;
   private Float outputCurrent;
   private Float startingTime;
   private Float idleElectricTime;
   private Float outputTime;
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

      if (!(o instanceof EnergyStorageMotorModel)) {
         return false;
      }

      EnergyStorageMotorModel other = (EnergyStorageMotorModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$id = this.getId();
      Object other$id = other.getId();
      if (this$id == null ? other$id == null : this$id.equals(other$id)) {
         Object this$primaryValue = this.getPrimaryValue();
         Object other$primaryValue = other.getPrimaryValue();
         if (this$primaryValue == null ? other$primaryValue == null : this$primaryValue.equals(other$primaryValue)) {
            Object this$secondaryValue = this.getSecondaryValue();
            Object other$secondaryValue = other.getSecondaryValue();
            if (this$secondaryValue == null ? other$secondaryValue == null : this$secondaryValue.equals(other$secondaryValue)) {
               Object this$samp = this.getSamp();
               Object other$samp = other.getSamp();
               if (this$samp == null ? other$samp == null : this$samp.equals(other$samp)) {
                  Object this$startingCurrent = this.getStartingCurrent();
                  Object other$startingCurrent = other.getStartingCurrent();
                  if (this$startingCurrent == null ? other$startingCurrent == null : this$startingCurrent.equals(other$startingCurrent)) {
                     Object this$idleElectricCurrent = this.getIdleElectricCurrent();
                     Object other$idleElectricCurrent = other.getIdleElectricCurrent();
                     if (this$idleElectricCurrent == null ? other$idleElectricCurrent == null : this$idleElectricCurrent.equals(other$idleElectricCurrent)) {
                        Object this$outputCurrent = this.getOutputCurrent();
                        Object other$outputCurrent = other.getOutputCurrent();
                        if (this$outputCurrent == null ? other$outputCurrent == null : this$outputCurrent.equals(other$outputCurrent)) {
                           Object this$startingTime = this.getStartingTime();
                           Object other$startingTime = other.getStartingTime();
                           if (this$startingTime == null ? other$startingTime == null : this$startingTime.equals(other$startingTime)) {
                              Object this$idleElectricTime = this.getIdleElectricTime();
                              Object other$idleElectricTime = other.getIdleElectricTime();
                              if (this$idleElectricTime == null ? other$idleElectricTime == null : this$idleElectricTime.equals(other$idleElectricTime)) {
                                 Object this$outputTime = this.getOutputTime();
                                 Object other$outputTime = other.getOutputTime();
                                 if (this$outputTime == null ? other$outputTime == null : this$outputTime.equals(other$outputTime)) {
                                    Object this$actionTime = this.getActionTime();
                                    Object other$actionTime = other.getActionTime();
                                    if (this$actionTime == null ? other$actionTime == null : this$actionTime.equals(other$actionTime)) {
                                       Object this$deviceCode = this.getDeviceCode();
                                       Object other$deviceCode = other.getDeviceCode();
                                       if (this$deviceCode == null ? other$deviceCode == null : this$deviceCode.equals(other$deviceCode)) {
                                          Object this$ps = this.getPs();
                                          Object other$ps = other.getPs();
                                          if (this$ps == null ? other$ps == null : this$ps.equals(other$ps)) {
                                             Object this$truncationData = this.getTruncationData();
                                             Object other$truncationData = other.getTruncationData();
                                             if (this$truncationData == null ? other$truncationData == null : this$truncationData.equals(other$truncationData)) {
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
                                                      return this$createDate == null ? other$createDate == null : this$createDate.equals(other$createDate);
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
      return other instanceof EnergyStorageMotorModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $primaryValue = this.getPrimaryValue();
      result = result * 59 + ($primaryValue == null ? 43 : $primaryValue.hashCode());
      Object $secondaryValue = this.getSecondaryValue();
      result = result * 59 + ($secondaryValue == null ? 43 : $secondaryValue.hashCode());
      Object $samp = this.getSamp();
      result = result * 59 + ($samp == null ? 43 : $samp.hashCode());
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
      result = result * 59 + ($deviceCode == null ? 43 : $deviceCode.hashCode());
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
      return "EnergyStorageMotorModel(id="
         + this.getId()
         + ", deviceCode="
         + this.getDeviceCode()
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
         + ", createDate="
         + this.getCreateDate()
         + ", currentData="
         + this.getCurrentData()
         + ")";
   }
}
