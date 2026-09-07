package com.drsi.njmsc.dto.dto;

import java.util.List;

public class TemperaturePredictionReqDto {
   private List<Float> real_temp;

   public List<Float> getReal_temp() {
      return this.real_temp;
   }

   public void setReal_temp(final List<Float> real_temp) {
      this.real_temp = real_temp;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof TemperaturePredictionReqDto)) {
         return false;
      }

      TemperaturePredictionReqDto other = (TemperaturePredictionReqDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$real_temp = this.getReal_temp();
      Object other$real_temp = other.getReal_temp();
      return this$real_temp == null ? other$real_temp == null : this$real_temp.equals(other$real_temp);
   }

   protected boolean canEqual(final Object other) {
      return other instanceof TemperaturePredictionReqDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $real_temp = this.getReal_temp();
      return result * 59 + ($real_temp == null ? 43 : $real_temp.hashCode());
   }

   @Override
   public String toString() {
      return "TemperaturePredictionReqDto(real_temp=" + this.getReal_temp() + ")";
   }
}
