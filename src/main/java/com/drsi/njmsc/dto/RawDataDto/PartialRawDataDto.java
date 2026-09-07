package com.drsi.njmsc.dto.RawDataDto;

import java.util.List;
import java.util.Map;

public class PartialRawDataDto extends RawDataDto {
   private String id;
   private Integer sensorChannel;
   private String sensorType;
   private Integer partialTimes;
   private Float maxPartial;
   private Map<String, List<String>> partialData;
   private String states;
   private String descripe;
   private List<List<Float>> dataX;
   private List<List<Float>> dataY;

   public String getId() {
      return this.id;
   }

   public Integer getSensorChannel() {
      return this.sensorChannel;
   }

   public String getSensorType() {
      return this.sensorType;
   }

   public Integer getPartialTimes() {
      return this.partialTimes;
   }

   public Float getMaxPartial() {
      return this.maxPartial;
   }

   public Map<String, List<String>> getPartialData() {
      return this.partialData;
   }

   public String getStates() {
      return this.states;
   }

   public String getDescripe() {
      return this.descripe;
   }

   public List<List<Float>> getDataX() {
      return this.dataX;
   }

   public List<List<Float>> getDataY() {
      return this.dataY;
   }

   public PartialRawDataDto setId(final String id) {
      this.id = id;
      return this;
   }

   public PartialRawDataDto setSensorChannel(final Integer sensorChannel) {
      this.sensorChannel = sensorChannel;
      return this;
   }

   public PartialRawDataDto setSensorType(final String sensorType) {
      this.sensorType = sensorType;
      return this;
   }

   public PartialRawDataDto setPartialTimes(final Integer partialTimes) {
      this.partialTimes = partialTimes;
      return this;
   }

   public PartialRawDataDto setMaxPartial(final Float maxPartial) {
      this.maxPartial = maxPartial;
      return this;
   }

   public PartialRawDataDto setPartialData(final Map<String, List<String>> partialData) {
      this.partialData = partialData;
      return this;
   }

   public PartialRawDataDto setStates(final String states) {
      this.states = states;
      return this;
   }

   public PartialRawDataDto setDescripe(final String descripe) {
      this.descripe = descripe;
      return this;
   }

   public PartialRawDataDto setDataX(final List<List<Float>> dataX) {
      this.dataX = dataX;
      return this;
   }

   public PartialRawDataDto setDataY(final List<List<Float>> dataY) {
      this.dataY = dataY;
      return this;
   }
}
