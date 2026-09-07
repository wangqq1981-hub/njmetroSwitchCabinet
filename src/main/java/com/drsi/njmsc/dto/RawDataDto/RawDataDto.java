package com.drsi.njmsc.dto.RawDataDto;

import java.util.Date;

public class RawDataDto {
   private String collectCode;
   private String deviceCode;
   private Date acquisitionTime;

   public String getCollectCode() {
      return this.collectCode;
   }

   public String getDeviceCode() {
      return this.deviceCode;
   }

   public Date getAcquisitionTime() {
      return this.acquisitionTime;
   }

   public RawDataDto setCollectCode(final String collectCode) {
      this.collectCode = collectCode;
      return this;
   }

   public RawDataDto setDeviceCode(final String deviceCode) {
      this.deviceCode = deviceCode;
      return this;
   }

   public RawDataDto setAcquisitionTime(final Date acquisitionTime) {
      this.acquisitionTime = acquisitionTime;
      return this;
   }
}
