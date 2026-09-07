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

@TableName("three_stations")
@JsonInclude(Include.NON_NULL)
public class ThreeStationsModel implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(type = IdType.AUTO)
   private Long id;
   @ApiModelProperty("设备编码")
   private String deviceCode;
   @ApiModelProperty("数据类别（3-本体柜三工位 4-隔离柜三工位）")
   private Integer dataType;
   @ApiModelProperty("当前位置（0-合位 1-中间位 2-接地位）")
   private Integer position;
   @ApiModelProperty("原位置（0-合位 1-中间位 2-接地位）")
   private Integer originalPosition;
   @ApiModelProperty("隔刀位置（0-合闸 1-分闸）")
   private Integer lsolatedPosition;
   @ApiModelProperty("接地位置（0-合闸 1-分闸）")
   private Integer groundingPosition;
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
   private Float peakValue;
   private Float valleyValue;
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

   public Integer getPosition() {
      return this.position;
   }

   public Integer getOriginalPosition() {
      return this.originalPosition;
   }

   public Integer getLsolatedPosition() {
      return this.lsolatedPosition;
   }

   public Integer getGroundingPosition() {
      return this.groundingPosition;
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

   public Float getPeakValue() {
      return this.peakValue;
   }

   public Float getValleyValue() {
      return this.valleyValue;
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

   public void setPosition(final Integer position) {
      this.position = position;
   }

   public void setOriginalPosition(final Integer originalPosition) {
      this.originalPosition = originalPosition;
   }

   public void setLsolatedPosition(final Integer lsolatedPosition) {
      this.lsolatedPosition = lsolatedPosition;
   }

   public void setGroundingPosition(final Integer groundingPosition) {
      this.groundingPosition = groundingPosition;
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

   public void setPeakValue(final Float peakValue) {
      this.peakValue = peakValue;
   }

   public void setValleyValue(final Float valleyValue) {
      this.valleyValue = valleyValue;
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

      if (!(o instanceof ThreeStationsModel)) {
         return false;
      }

      ThreeStationsModel other = (ThreeStationsModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$id = this.getId();
      Object other$id = other.getId();
      if (this$id == null ? other$id == null : this$id.equals(other$id)) {
         Object this$dataType = this.getDataType();
         Object other$dataType = other.getDataType();
         if (this$dataType == null ? other$dataType == null : this$dataType.equals(other$dataType)) {
            Object this$position = this.getPosition();
            Object other$position = other.getPosition();
            if (this$position == null ? other$position == null : this$position.equals(other$position)) {
               Object this$originalPosition = this.getOriginalPosition();
               Object other$originalPosition = other.getOriginalPosition();
               if (this$originalPosition == null ? other$originalPosition == null : this$originalPosition.equals(other$originalPosition)) {
                  Object this$lsolatedPosition = this.getLsolatedPosition();
                  Object other$lsolatedPosition = other.getLsolatedPosition();
                  if (this$lsolatedPosition == null ? other$lsolatedPosition == null : this$lsolatedPosition.equals(other$lsolatedPosition)) {
                     Object this$groundingPosition = this.getGroundingPosition();
                     Object other$groundingPosition = other.getGroundingPosition();
                     if (this$groundingPosition == null ? other$groundingPosition == null : this$groundingPosition.equals(other$groundingPosition)) {
                        Object this$primaryValue = this.getPrimaryValue();
                        Object other$primaryValue = other.getPrimaryValue();
                        if (this$primaryValue == null ? other$primaryValue == null : this$primaryValue.equals(other$primaryValue)) {
                           Object this$secondaryValue = this.getSecondaryValue();
                           Object other$secondaryValue = other.getSecondaryValue();
                           if (this$secondaryValue == null ? other$secondaryValue == null : this$secondaryValue.equals(other$secondaryValue)) {
                              Object this$samp = this.getSamp();
                              Object other$samp = other.getSamp();
                              if (this$samp == null ? other$samp == null : this$samp.equals(other$samp)) {
                                 Object this$peakValue = this.getPeakValue();
                                 Object other$peakValue = other.getPeakValue();
                                 if (this$peakValue == null ? other$peakValue == null : this$peakValue.equals(other$peakValue)) {
                                    Object this$valleyValue = this.getValleyValue();
                                    Object other$valleyValue = other.getValleyValue();
                                    if (this$valleyValue == null ? other$valleyValue == null : this$valleyValue.equals(other$valleyValue)) {
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
      } else {
         return false;
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof ThreeStationsModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $dataType = this.getDataType();
      result = result * 59 + ($dataType == null ? 43 : $dataType.hashCode());
      Object $position = this.getPosition();
      result = result * 59 + ($position == null ? 43 : $position.hashCode());
      Object $originalPosition = this.getOriginalPosition();
      result = result * 59 + ($originalPosition == null ? 43 : $originalPosition.hashCode());
      Object $lsolatedPosition = this.getLsolatedPosition();
      result = result * 59 + ($lsolatedPosition == null ? 43 : $lsolatedPosition.hashCode());
      Object $groundingPosition = this.getGroundingPosition();
      result = result * 59 + ($groundingPosition == null ? 43 : $groundingPosition.hashCode());
      Object $primaryValue = this.getPrimaryValue();
      result = result * 59 + ($primaryValue == null ? 43 : $primaryValue.hashCode());
      Object $secondaryValue = this.getSecondaryValue();
      result = result * 59 + ($secondaryValue == null ? 43 : $secondaryValue.hashCode());
      Object $samp = this.getSamp();
      result = result * 59 + ($samp == null ? 43 : $samp.hashCode());
      Object $peakValue = this.getPeakValue();
      result = result * 59 + ($peakValue == null ? 43 : $peakValue.hashCode());
      Object $valleyValue = this.getValleyValue();
      result = result * 59 + ($valleyValue == null ? 43 : $valleyValue.hashCode());
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
      return "ThreeStationsModel(id="
         + this.getId()
         + ", deviceCode="
         + this.getDeviceCode()
         + ", dataType="
         + this.getDataType()
         + ", position="
         + this.getPosition()
         + ", originalPosition="
         + this.getOriginalPosition()
         + ", lsolatedPosition="
         + this.getLsolatedPosition()
         + ", groundingPosition="
         + this.getGroundingPosition()
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
         + ", peakValue="
         + this.getPeakValue()
         + ", valleyValue="
         + this.getValleyValue()
         + ", actionTime="
         + this.getActionTime()
         + ", createDate="
         + this.getCreateDate()
         + ", currentData="
         + this.getCurrentData()
         + ")";
   }
}
