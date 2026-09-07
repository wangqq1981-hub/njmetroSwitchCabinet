package com.drsi.njmsc.dto.dto;

import io.swagger.annotations.ApiModelProperty;

public class MonitorSwitchIndexDto {
   @ApiModelProperty("设备编码")
   private String deviceCode;
   @ApiModelProperty("断路器分合闸状态")
   private Integer circuitState;
   @ApiModelProperty("三工位1-位置状态")
   private Integer tsPosition;
   @ApiModelProperty("隔离三工位-位置状态")
   private Integer tsIsolationTwoPosition;

   public String getDeviceCode() {
      return this.deviceCode;
   }

   public Integer getCircuitState() {
      return this.circuitState;
   }

   public Integer getTsPosition() {
      return this.tsPosition;
   }

   public Integer getTsIsolationTwoPosition() {
      return this.tsIsolationTwoPosition;
   }

   public void setDeviceCode(final String deviceCode) {
      this.deviceCode = deviceCode;
   }

   public void setCircuitState(final Integer circuitState) {
      this.circuitState = circuitState;
   }

   public void setTsPosition(final Integer tsPosition) {
      this.tsPosition = tsPosition;
   }

   public void setTsIsolationTwoPosition(final Integer tsIsolationTwoPosition) {
      this.tsIsolationTwoPosition = tsIsolationTwoPosition;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof MonitorSwitchIndexDto)) {
         return false;
      }

      MonitorSwitchIndexDto other = (MonitorSwitchIndexDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$circuitState = this.getCircuitState();
      Object other$circuitState = other.getCircuitState();
      if (this$circuitState == null ? other$circuitState == null : this$circuitState.equals(other$circuitState)) {
         Object this$tsPosition = this.getTsPosition();
         Object other$tsPosition = other.getTsPosition();
         if (this$tsPosition == null ? other$tsPosition == null : this$tsPosition.equals(other$tsPosition)) {
            Object this$tsIsolationTwoPosition = this.getTsIsolationTwoPosition();
            Object other$tsIsolationTwoPosition = other.getTsIsolationTwoPosition();
            if (this$tsIsolationTwoPosition == null ? other$tsIsolationTwoPosition == null : this$tsIsolationTwoPosition.equals(other$tsIsolationTwoPosition)) {
               Object this$deviceCode = this.getDeviceCode();
               Object other$deviceCode = other.getDeviceCode();
               return this$deviceCode == null ? other$deviceCode == null : this$deviceCode.equals(other$deviceCode);
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
      return other instanceof MonitorSwitchIndexDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $circuitState = this.getCircuitState();
      result = result * 59 + ($circuitState == null ? 43 : $circuitState.hashCode());
      Object $tsPosition = this.getTsPosition();
      result = result * 59 + ($tsPosition == null ? 43 : $tsPosition.hashCode());
      Object $tsIsolationTwoPosition = this.getTsIsolationTwoPosition();
      result = result * 59 + ($tsIsolationTwoPosition == null ? 43 : $tsIsolationTwoPosition.hashCode());
      Object $deviceCode = this.getDeviceCode();
      return result * 59 + ($deviceCode == null ? 43 : $deviceCode.hashCode());
   }

   @Override
   public String toString() {
      return "MonitorSwitchIndexDto(deviceCode="
         + this.getDeviceCode()
         + ", circuitState="
         + this.getCircuitState()
         + ", tsPosition="
         + this.getTsPosition()
         + ", tsIsolationTwoPosition="
         + this.getTsIsolationTwoPosition()
         + ")";
   }
}
