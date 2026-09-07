package com.drsi.njmsc.dto.RawDataDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class ThermographyRawDataDto extends RawDataDto {
   @ApiModelProperty("image_name")
   private String imageName;
   @ApiModelProperty("max_temper_a")
   private Float maxTemperA;
   @ApiModelProperty("max_temper_b")
   private Float maxTemperB;
   @ApiModelProperty("max_temper_c")
   private Float maxTemperC;
   @ApiModelProperty("max_temper")
   private Float maxTemper;
   @ApiModelProperty("avg_temper")
   private Float avgTemper;
   @ApiModelProperty("local_dhg")
   private Float localDhg;

   public String getImageName() {
      return this.imageName;
   }

   public Float getMaxTemperA() {
      return this.maxTemperA;
   }

   public Float getMaxTemperB() {
      return this.maxTemperB;
   }

   public Float getMaxTemperC() {
      return this.maxTemperC;
   }

   public Float getMaxTemper() {
      return this.maxTemper;
   }

   public Float getAvgTemper() {
      return this.avgTemper;
   }

   public Float getLocalDhg() {
      return this.localDhg;
   }

   public ThermographyRawDataDto setImageName(final String imageName) {
      this.imageName = imageName;
      return this;
   }

   public ThermographyRawDataDto setMaxTemperA(final Float maxTemperA) {
      this.maxTemperA = maxTemperA;
      return this;
   }

   public ThermographyRawDataDto setMaxTemperB(final Float maxTemperB) {
      this.maxTemperB = maxTemperB;
      return this;
   }

   public ThermographyRawDataDto setMaxTemperC(final Float maxTemperC) {
      this.maxTemperC = maxTemperC;
      return this;
   }

   public ThermographyRawDataDto setMaxTemper(final Float maxTemper) {
      this.maxTemper = maxTemper;
      return this;
   }

   public ThermographyRawDataDto setAvgTemper(final Float avgTemper) {
      this.avgTemper = avgTemper;
      return this;
   }

   public ThermographyRawDataDto setLocalDhg(final Float localDhg) {
      this.localDhg = localDhg;
      return this;
   }
}
