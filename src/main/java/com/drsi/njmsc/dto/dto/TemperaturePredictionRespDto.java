package com.drsi.njmsc.dto.dto;

public class TemperaturePredictionRespDto {
   private Integer status;
   private String describe;
   private Float forecast;

   public Integer getStatus() {
      return this.status;
   }

   public String getDescribe() {
      return this.describe;
   }

   public Float getForecast() {
      return this.forecast;
   }

   public void setStatus(final Integer status) {
      this.status = status;
   }

   public void setDescribe(final String describe) {
      this.describe = describe;
   }

   public void setForecast(final Float forecast) {
      this.forecast = forecast;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof TemperaturePredictionRespDto)) {
         return false;
      }

      TemperaturePredictionRespDto other = (TemperaturePredictionRespDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$status = this.getStatus();
      Object other$status = other.getStatus();
      if (this$status == null ? other$status == null : this$status.equals(other$status)) {
         Object this$forecast = this.getForecast();
         Object other$forecast = other.getForecast();
         if (this$forecast == null ? other$forecast == null : this$forecast.equals(other$forecast)) {
            Object this$describe = this.getDescribe();
            Object other$describe = other.getDescribe();
            return this$describe == null ? other$describe == null : this$describe.equals(other$describe);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof TemperaturePredictionRespDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $forecast = this.getForecast();
      result = result * 59 + ($forecast == null ? 43 : $forecast.hashCode());
      Object $describe = this.getDescribe();
      return result * 59 + ($describe == null ? 43 : $describe.hashCode());
   }

   @Override
   public String toString() {
      return "TemperaturePredictionRespDto(status=" + this.getStatus() + ", describe=" + this.getDescribe() + ", forecast=" + this.getForecast() + ")";
   }
}
