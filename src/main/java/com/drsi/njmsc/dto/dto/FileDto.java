package com.drsi.njmsc.dto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Arrays;

@JsonInclude(Include.NON_NULL)
public class FileDto {
   private String fileName;
   private String attribute;
   private Integer fileSize;
   private String fileTimeFormat;
   private byte[] fileBytes;
   private Boolean fileReadFinFlag;

   public String getFileName() {
      return this.fileName;
   }

   public String getAttribute() {
      return this.attribute;
   }

   public Integer getFileSize() {
      return this.fileSize;
   }

   public String getFileTimeFormat() {
      return this.fileTimeFormat;
   }

   public byte[] getFileBytes() {
      return this.fileBytes;
   }

   public Boolean getFileReadFinFlag() {
      return this.fileReadFinFlag;
   }

   public FileDto setFileName(final String fileName) {
      this.fileName = fileName;
      return this;
   }

   public FileDto setAttribute(final String attribute) {
      this.attribute = attribute;
      return this;
   }

   public FileDto setFileSize(final Integer fileSize) {
      this.fileSize = fileSize;
      return this;
   }

   public FileDto setFileTimeFormat(final String fileTimeFormat) {
      this.fileTimeFormat = fileTimeFormat;
      return this;
   }

   public FileDto setFileBytes(final byte[] fileBytes) {
      this.fileBytes = fileBytes;
      return this;
   }

   public FileDto setFileReadFinFlag(final Boolean fileReadFinFlag) {
      this.fileReadFinFlag = fileReadFinFlag;
      return this;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof FileDto)) {
         return false;
      }

      FileDto other = (FileDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$fileSize = this.getFileSize();
      Object other$fileSize = other.getFileSize();
      if (this$fileSize == null ? other$fileSize == null : this$fileSize.equals(other$fileSize)) {
         Object this$fileReadFinFlag = this.getFileReadFinFlag();
         Object other$fileReadFinFlag = other.getFileReadFinFlag();
         if (this$fileReadFinFlag == null ? other$fileReadFinFlag == null : this$fileReadFinFlag.equals(other$fileReadFinFlag)) {
            Object this$fileName = this.getFileName();
            Object other$fileName = other.getFileName();
            if (this$fileName == null ? other$fileName == null : this$fileName.equals(other$fileName)) {
               Object this$attribute = this.getAttribute();
               Object other$attribute = other.getAttribute();
               if (this$attribute == null ? other$attribute == null : this$attribute.equals(other$attribute)) {
                  Object this$fileTimeFormat = this.getFileTimeFormat();
                  Object other$fileTimeFormat = other.getFileTimeFormat();
                  return (this$fileTimeFormat == null ? other$fileTimeFormat == null : this$fileTimeFormat.equals(other$fileTimeFormat))
                     ? Arrays.equals(this.getFileBytes(), other.getFileBytes())
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
      } else {
         return false;
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof FileDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $fileSize = this.getFileSize();
      result = result * 59 + ($fileSize == null ? 43 : $fileSize.hashCode());
      Object $fileReadFinFlag = this.getFileReadFinFlag();
      result = result * 59 + ($fileReadFinFlag == null ? 43 : $fileReadFinFlag.hashCode());
      Object $fileName = this.getFileName();
      result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
      Object $attribute = this.getAttribute();
      result = result * 59 + ($attribute == null ? 43 : $attribute.hashCode());
      Object $fileTimeFormat = this.getFileTimeFormat();
      result = result * 59 + ($fileTimeFormat == null ? 43 : $fileTimeFormat.hashCode());
      return result * 59 + Arrays.hashCode(this.getFileBytes());
   }

   @Override
   public String toString() {
      return "FileDto(fileName="
         + this.getFileName()
         + ", attribute="
         + this.getAttribute()
         + ", fileSize="
         + this.getFileSize()
         + ", fileTimeFormat="
         + this.getFileTimeFormat()
         + ", fileBytes="
         + Arrays.toString(this.getFileBytes())
         + ", fileReadFinFlag="
         + this.getFileReadFinFlag()
         + ")";
   }
}
