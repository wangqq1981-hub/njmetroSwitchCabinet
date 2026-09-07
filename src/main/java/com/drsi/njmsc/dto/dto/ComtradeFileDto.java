package com.drsi.njmsc.dto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Arrays;

@JsonInclude(Include.NON_NULL)
public class ComtradeFileDto {
   private String cfcFileName;
   private byte[] cfcFileBytes;
   private Integer cfcFileBytesSize;
   private String datFileName;
   private byte[] datFileBytes;
   private Integer datFileBytesSize;

   public String getCfcFileName() {
      return this.cfcFileName;
   }

   public byte[] getCfcFileBytes() {
      return this.cfcFileBytes;
   }

   public Integer getCfcFileBytesSize() {
      return this.cfcFileBytesSize;
   }

   public String getDatFileName() {
      return this.datFileName;
   }

   public byte[] getDatFileBytes() {
      return this.datFileBytes;
   }

   public Integer getDatFileBytesSize() {
      return this.datFileBytesSize;
   }

   public ComtradeFileDto setCfcFileName(final String cfcFileName) {
      this.cfcFileName = cfcFileName;
      return this;
   }

   public ComtradeFileDto setCfcFileBytes(final byte[] cfcFileBytes) {
      this.cfcFileBytes = cfcFileBytes;
      return this;
   }

   public ComtradeFileDto setCfcFileBytesSize(final Integer cfcFileBytesSize) {
      this.cfcFileBytesSize = cfcFileBytesSize;
      return this;
   }

   public ComtradeFileDto setDatFileName(final String datFileName) {
      this.datFileName = datFileName;
      return this;
   }

   public ComtradeFileDto setDatFileBytes(final byte[] datFileBytes) {
      this.datFileBytes = datFileBytes;
      return this;
   }

   public ComtradeFileDto setDatFileBytesSize(final Integer datFileBytesSize) {
      this.datFileBytesSize = datFileBytesSize;
      return this;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof ComtradeFileDto)) {
         return false;
      }

      ComtradeFileDto other = (ComtradeFileDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$cfcFileBytesSize = this.getCfcFileBytesSize();
      Object other$cfcFileBytesSize = other.getCfcFileBytesSize();
      if (this$cfcFileBytesSize == null ? other$cfcFileBytesSize == null : this$cfcFileBytesSize.equals(other$cfcFileBytesSize)) {
         Object this$datFileBytesSize = this.getDatFileBytesSize();
         Object other$datFileBytesSize = other.getDatFileBytesSize();
         if (this$datFileBytesSize == null ? other$datFileBytesSize == null : this$datFileBytesSize.equals(other$datFileBytesSize)) {
            Object this$cfcFileName = this.getCfcFileName();
            Object other$cfcFileName = other.getCfcFileName();
            if (this$cfcFileName == null ? other$cfcFileName == null : this$cfcFileName.equals(other$cfcFileName)) {
               if (!Arrays.equals(this.getCfcFileBytes(), other.getCfcFileBytes())) {
                  return false;
               }

               Object this$datFileName = this.getDatFileName();
               Object other$datFileName = other.getDatFileName();
               return (this$datFileName == null ? other$datFileName == null : this$datFileName.equals(other$datFileName))
                  ? Arrays.equals(this.getDatFileBytes(), other.getDatFileBytes())
                  : false;
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof ComtradeFileDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $cfcFileBytesSize = this.getCfcFileBytesSize();
      result = result * 59 + ($cfcFileBytesSize == null ? 43 : $cfcFileBytesSize.hashCode());
      Object $datFileBytesSize = this.getDatFileBytesSize();
      result = result * 59 + ($datFileBytesSize == null ? 43 : $datFileBytesSize.hashCode());
      Object $cfcFileName = this.getCfcFileName();
      result = result * 59 + ($cfcFileName == null ? 43 : $cfcFileName.hashCode());
      result = result * 59 + Arrays.hashCode(this.getCfcFileBytes());
      Object $datFileName = this.getDatFileName();
      result = result * 59 + ($datFileName == null ? 43 : $datFileName.hashCode());
      return result * 59 + Arrays.hashCode(this.getDatFileBytes());
   }

   @Override
   public String toString() {
      return "ComtradeFileDto(cfcFileName="
         + this.getCfcFileName()
         + ", cfcFileBytes="
         + Arrays.toString(this.getCfcFileBytes())
         + ", cfcFileBytesSize="
         + this.getCfcFileBytesSize()
         + ", datFileName="
         + this.getDatFileName()
         + ", datFileBytes="
         + Arrays.toString(this.getDatFileBytes())
         + ", datFileBytesSize="
         + this.getDatFileBytesSize()
         + ")";
   }
}
