package com.drsi.njmsc.dto.RawDataDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DistanceRawDataDto {
   private Integer signalQuality;
   private Float processValue;
   private Boolean signalQualityFlag;
   private Boolean switchingOutputQ2Flag;
   private Boolean switchingOutputQ1Flag;

   public DistanceRawDataDto setSignalQuality(final Integer signalQuality) {
      this.signalQuality = signalQuality;
      return this;
   }

   public DistanceRawDataDto setProcessValue(final Float processValue) {
      this.processValue = processValue;
      return this;
   }

   public DistanceRawDataDto setSignalQualityFlag(final Boolean signalQualityFlag) {
      this.signalQualityFlag = signalQualityFlag;
      return this;
   }

   public DistanceRawDataDto setSwitchingOutputQ2Flag(final Boolean switchingOutputQ2Flag) {
      this.switchingOutputQ2Flag = switchingOutputQ2Flag;
      return this;
   }

   public DistanceRawDataDto setSwitchingOutputQ1Flag(final Boolean switchingOutputQ1Flag) {
      this.switchingOutputQ1Flag = switchingOutputQ1Flag;
      return this;
   }

   public Integer getSignalQuality() {
      return this.signalQuality;
   }

   public Float getProcessValue() {
      return this.processValue;
   }

   public Boolean getSignalQualityFlag() {
      return this.signalQualityFlag;
   }

   public Boolean getSwitchingOutputQ2Flag() {
      return this.switchingOutputQ2Flag;
   }

   public Boolean getSwitchingOutputQ1Flag() {
      return this.switchingOutputQ1Flag;
   }
}
