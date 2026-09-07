package com.drsi.njmsc.dto.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;

public class ThreeStationsDtos {
   private Long id;
   @ApiModelProperty("设备编码")
   private String deviceCode;
   @ApiModelProperty("当前位置（0-合位 1-中间位 2-接地位）")
   private Integer position;
   @ApiModelProperty("触发点时间")
   private Date triggerPointDate;
   private Float peak_value;
   private Float valley_value;
   private Float actionTime;
   private List<Float> current;

   public Long getId() {
      return this.id;
   }

   public String getDeviceCode() {
      return this.deviceCode;
   }

   public Integer getPosition() {
      return this.position;
   }

   public Date getTriggerPointDate() {
      return this.triggerPointDate;
   }

   public Float getPeak_value() {
      return this.peak_value;
   }

   public Float getValley_value() {
      return this.valley_value;
   }

   public Float getActionTime() {
      return this.actionTime;
   }

   public List<Float> getCurrent() {
      return this.current;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public void setDeviceCode(final String deviceCode) {
      this.deviceCode = deviceCode;
   }

   public void setPosition(final Integer position) {
      this.position = position;
   }

   public void setTriggerPointDate(final Date triggerPointDate) {
      this.triggerPointDate = triggerPointDate;
   }

   public void setPeak_value(final Float peak_value) {
      this.peak_value = peak_value;
   }

   public void setValley_value(final Float valley_value) {
      this.valley_value = valley_value;
   }

   public void setActionTime(final Float actionTime) {
      this.actionTime = actionTime;
   }

   public void setCurrent(final List<Float> current) {
      this.current = current;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof ThreeStationsDtos)) {
         return false;
      }

      ThreeStationsDtos other = (ThreeStationsDtos)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$id = this.getId();
      Object other$id = other.getId();
      if (this$id == null ? other$id == null : this$id.equals(other$id)) {
         Object this$position = this.getPosition();
         Object other$position = other.getPosition();
         if (this$position == null ? other$position == null : this$position.equals(other$position)) {
            Object this$peak_value = this.getPeak_value();
            Object other$peak_value = other.getPeak_value();
            if (this$peak_value == null ? other$peak_value == null : this$peak_value.equals(other$peak_value)) {
               Object this$valley_value = this.getValley_value();
               Object other$valley_value = other.getValley_value();
               if (this$valley_value == null ? other$valley_value == null : this$valley_value.equals(other$valley_value)) {
                  Object this$actionTime = this.getActionTime();
                  Object other$actionTime = other.getActionTime();
                  if (this$actionTime == null ? other$actionTime == null : this$actionTime.equals(other$actionTime)) {
                     Object this$deviceCode = this.getDeviceCode();
                     Object other$deviceCode = other.getDeviceCode();
                     if (this$deviceCode == null ? other$deviceCode == null : this$deviceCode.equals(other$deviceCode)) {
                        Object this$triggerPointDate = this.getTriggerPointDate();
                        Object other$triggerPointDate = other.getTriggerPointDate();
                        if (this$triggerPointDate == null ? other$triggerPointDate == null : this$triggerPointDate.equals(other$triggerPointDate)) {
                           Object this$current = this.getCurrent();
                           Object other$current = other.getCurrent();
                           return this$current == null ? other$current == null : this$current.equals(other$current);
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
      return other instanceof ThreeStationsDtos;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $position = this.getPosition();
      result = result * 59 + ($position == null ? 43 : $position.hashCode());
      Object $peak_value = this.getPeak_value();
      result = result * 59 + ($peak_value == null ? 43 : $peak_value.hashCode());
      Object $valley_value = this.getValley_value();
      result = result * 59 + ($valley_value == null ? 43 : $valley_value.hashCode());
      Object $actionTime = this.getActionTime();
      result = result * 59 + ($actionTime == null ? 43 : $actionTime.hashCode());
      Object $deviceCode = this.getDeviceCode();
      result = result * 59 + ($deviceCode == null ? 43 : $deviceCode.hashCode());
      Object $triggerPointDate = this.getTriggerPointDate();
      result = result * 59 + ($triggerPointDate == null ? 43 : $triggerPointDate.hashCode());
      Object $current = this.getCurrent();
      return result * 59 + ($current == null ? 43 : $current.hashCode());
   }

   @Override
   public String toString() {
      return "ThreeStationsDtos(id="
         + this.getId()
         + ", deviceCode="
         + this.getDeviceCode()
         + ", position="
         + this.getPosition()
         + ", triggerPointDate="
         + this.getTriggerPointDate()
         + ", peak_value="
         + this.getPeak_value()
         + ", valley_value="
         + this.getValley_value()
         + ", actionTime="
         + this.getActionTime()
         + ", current="
         + this.getCurrent()
         + ")";
   }
}
