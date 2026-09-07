package com.drsi.njmsc.dto.RawDataDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CircuitBreakerRawDataDto extends RawDataDto {
   private Integer closeSwitch;
   private Integer openSwitch;
   private Integer remainingLife;
   private Integer deviceStatus;
   private String exceptionCategory;
   private Float current;
   private Float storageTime;

   public void setCloseSwitch(final Integer closeSwitch) {
      this.closeSwitch = closeSwitch;
   }

   public void setOpenSwitch(final Integer openSwitch) {
      this.openSwitch = openSwitch;
   }

   public void setRemainingLife(final Integer remainingLife) {
      this.remainingLife = remainingLife;
   }

   public void setDeviceStatus(final Integer deviceStatus) {
      this.deviceStatus = deviceStatus;
   }

   public void setExceptionCategory(final String exceptionCategory) {
      this.exceptionCategory = exceptionCategory;
   }

   public void setCurrent(final Float current) {
      this.current = current;
   }

   public void setStorageTime(final Float storageTime) {
      this.storageTime = storageTime;
   }

   public Integer getCloseSwitch() {
      return this.closeSwitch;
   }

   public Integer getOpenSwitch() {
      return this.openSwitch;
   }

   public Integer getRemainingLife() {
      return this.remainingLife;
   }

   public Integer getDeviceStatus() {
      return this.deviceStatus;
   }

   public String getExceptionCategory() {
      return this.exceptionCategory;
   }

   public Float getCurrent() {
      return this.current;
   }

   public Float getStorageTime() {
      return this.storageTime;
   }
}
