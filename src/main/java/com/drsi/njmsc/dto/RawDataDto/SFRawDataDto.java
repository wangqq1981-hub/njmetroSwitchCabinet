package com.drsi.njmsc.dto.RawDataDto;

public class SFRawDataDto extends RawDataDto {
   private Float ppm;
   private Float temperature;

   public Float getPpm() {
      return this.ppm;
   }

   public Float getTemperature() {
      return this.temperature;
   }

   public SFRawDataDto setPpm(final Float ppm) {
      this.ppm = ppm;
      return this;
   }

   public SFRawDataDto setTemperature(final Float temperature) {
      this.temperature = temperature;
      return this;
   }
}
