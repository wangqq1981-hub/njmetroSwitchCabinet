package com.drsi.njmsc.dto.dto;

public class EvalReportDto {
   public String deviceName;
   public String deviceType;
   public String usedDate;
   public String evalDate;
   public Float score;
   public String evalState;
   public String jitemDetails;
   public String repairSubject;

   public String getDeviceName() {
      return this.deviceName;
   }

   public String getDeviceType() {
      return this.deviceType;
   }

   public String getUsedDate() {
      return this.usedDate;
   }

   public String getEvalDate() {
      return this.evalDate;
   }

   public Float getScore() {
      return this.score;
   }

   public String getEvalState() {
      return this.evalState;
   }

   public String getJitemDetails() {
      return this.jitemDetails;
   }

   public String getRepairSubject() {
      return this.repairSubject;
   }

   public void setDeviceName(final String deviceName) {
      this.deviceName = deviceName;
   }

   public void setDeviceType(final String deviceType) {
      this.deviceType = deviceType;
   }

   public void setUsedDate(final String usedDate) {
      this.usedDate = usedDate;
   }

   public void setEvalDate(final String evalDate) {
      this.evalDate = evalDate;
   }

   public void setScore(final Float score) {
      this.score = score;
   }

   public void setEvalState(final String evalState) {
      this.evalState = evalState;
   }

   public void setJitemDetails(final String jitemDetails) {
      this.jitemDetails = jitemDetails;
   }

   public void setRepairSubject(final String repairSubject) {
      this.repairSubject = repairSubject;
   }
}
