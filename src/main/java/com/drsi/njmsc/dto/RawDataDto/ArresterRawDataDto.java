package com.drsi.njmsc.dto.RawDataDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ArresterRawDataDto extends RawDataDto {
   private Float dischargeCurrentMeterA;
   private Float leakageCurrentMeterA;
   private Float dischargeCurrentMeterB;
   private Float leakageCurrentMeterB;
   private Float dischargeCurrentMeterC;
   private Float leakageCurrentMeterC;

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
}
