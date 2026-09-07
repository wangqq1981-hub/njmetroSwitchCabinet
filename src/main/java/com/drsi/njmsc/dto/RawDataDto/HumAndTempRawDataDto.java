package com.drsi.njmsc.dto.RawDataDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class HumAndTempRawDataDto extends RawDataDto {
   private Float humidity;
   private Float temp;
   private Integer status;
   private String describe;
   private Float forecast;

   public void setHumidity(final Float humidity) {
      this.humidity = humidity;
   }

   public void setTemp(final Float temp) {
      this.temp = temp;
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

   public Float getHumidity() {
      return this.humidity;
   }

   public Float getTemp() {
      return this.temp;
   }

   public Integer getStatus() {
      return this.status;
   }

   public String getDescribe() {
      return this.describe;
   }

   public Float getForecast() {
      return this.forecast;
   }
}
