package com.drsi.njmsc.dto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class MscCurrentAnalysisReqDto {
   private String deviceType;
   private Integer dataType;
   private List<Float> rawData;

   public String getDeviceType() {
      return this.deviceType;
   }

   public Integer getDataType() {
      return this.dataType;
   }

   public List<Float> getRawData() {
      return this.rawData;
   }

   public void setDeviceType(final String deviceType) {
      this.deviceType = deviceType;
   }

   public void setDataType(final Integer dataType) {
      this.dataType = dataType;
   }

   public void setRawData(final List<Float> rawData) {
      this.rawData = rawData;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof MscCurrentAnalysisReqDto)) {
         return false;
      }

      MscCurrentAnalysisReqDto other = (MscCurrentAnalysisReqDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$dataType = this.getDataType();
      Object other$dataType = other.getDataType();
      if (this$dataType == null ? other$dataType == null : this$dataType.equals(other$dataType)) {
         Object this$deviceType = this.getDeviceType();
         Object other$deviceType = other.getDeviceType();
         if (this$deviceType == null ? other$deviceType == null : this$deviceType.equals(other$deviceType)) {
            Object this$rawData = this.getRawData();
            Object other$rawData = other.getRawData();
            return this$rawData == null ? other$rawData == null : this$rawData.equals(other$rawData);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof MscCurrentAnalysisReqDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $dataType = this.getDataType();
      result = result * 59 + ($dataType == null ? 43 : $dataType.hashCode());
      Object $deviceType = this.getDeviceType();
      result = result * 59 + ($deviceType == null ? 43 : $deviceType.hashCode());
      Object $rawData = this.getRawData();
      return result * 59 + ($rawData == null ? 43 : $rawData.hashCode());
   }

   @Override
   public String toString() {
      return "MscCurrentAnalysisReqDto(deviceType=" + this.getDeviceType() + ", dataType=" + this.getDataType() + ", rawData=" + this.getRawData() + ")";
   }
}
