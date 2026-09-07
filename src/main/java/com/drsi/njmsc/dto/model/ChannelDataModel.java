package com.drsi.njmsc.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ChannelDataModel {
   private Integer offset;
   private Integer value;
   private Float pValue;
   private Float sValue;
   private Long timestamp;

   public Integer getOffset() {
      return this.offset;
   }

   public Integer getValue() {
      return this.value;
   }

   public Float getPValue() {
      return this.pValue;
   }

   public Float getSValue() {
      return this.sValue;
   }

   public Long getTimestamp() {
      return this.timestamp;
   }

   public ChannelDataModel setOffset(final Integer offset) {
      this.offset = offset;
      return this;
   }

   public ChannelDataModel setValue(final Integer value) {
      this.value = value;
      return this;
   }

   public ChannelDataModel setPValue(final Float pValue) {
      this.pValue = pValue;
      return this;
   }

   public ChannelDataModel setSValue(final Float sValue) {
      this.sValue = sValue;
      return this;
   }

   public ChannelDataModel setTimestamp(final Long timestamp) {
      this.timestamp = timestamp;
      return this;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof ChannelDataModel)) {
         return false;
      }

      ChannelDataModel other = (ChannelDataModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$offset = this.getOffset();
      Object other$offset = other.getOffset();
      if (this$offset == null ? other$offset == null : this$offset.equals(other$offset)) {
         Object this$value = this.getValue();
         Object other$value = other.getValue();
         if (this$value == null ? other$value == null : this$value.equals(other$value)) {
            Object this$pValue = this.getPValue();
            Object other$pValue = other.getPValue();
            if (this$pValue == null ? other$pValue == null : this$pValue.equals(other$pValue)) {
               Object this$sValue = this.getSValue();
               Object other$sValue = other.getSValue();
               if (this$sValue == null ? other$sValue == null : this$sValue.equals(other$sValue)) {
                  Object this$timestamp = this.getTimestamp();
                  Object other$timestamp = other.getTimestamp();
                  return this$timestamp == null ? other$timestamp == null : this$timestamp.equals(other$timestamp);
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
      return other instanceof ChannelDataModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $offset = this.getOffset();
      result = result * 59 + ($offset == null ? 43 : $offset.hashCode());
      Object $value = this.getValue();
      result = result * 59 + ($value == null ? 43 : $value.hashCode());
      Object $pValue = this.getPValue();
      result = result * 59 + ($pValue == null ? 43 : $pValue.hashCode());
      Object $sValue = this.getSValue();
      result = result * 59 + ($sValue == null ? 43 : $sValue.hashCode());
      Object $timestamp = this.getTimestamp();
      return result * 59 + ($timestamp == null ? 43 : $timestamp.hashCode());
   }

   @Override
   public String toString() {
      return "ChannelDataModel(offset="
         + this.getOffset()
         + ", value="
         + this.getValue()
         + ", pValue="
         + this.getPValue()
         + ", sValue="
         + this.getSValue()
         + ", timestamp="
         + this.getTimestamp()
         + ")";
   }
}
