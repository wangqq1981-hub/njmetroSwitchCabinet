package com.drsi.njmsc.dto.RawDataDto;

public class ThrPositionSwitchgearRawDataDto extends RawDataDto {
   private Float speed;
   private Float current;

   public Float getSpeed() {
      return this.speed;
   }

   public Float getCurrent() {
      return this.current;
   }

   public ThrPositionSwitchgearRawDataDto setSpeed(final Float speed) {
      this.speed = speed;
      return this;
   }

   public ThrPositionSwitchgearRawDataDto setCurrent(final Float current) {
      this.current = current;
      return this;
   }
}
