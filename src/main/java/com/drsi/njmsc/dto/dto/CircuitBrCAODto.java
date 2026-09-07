package com.drsi.njmsc.dto.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "CircuitBrCAODto对象", description = "断路器 分合闸数据")
public class CircuitBrCAODto {
   private Float time;
   private Float distance;
   private Float current;

   public Float getTime() {
      return this.time;
   }

   public Float getDistance() {
      return this.distance;
   }

   public Float getCurrent() {
      return this.current;
   }

   public void setTime(final Float time) {
      this.time = time;
   }

   public void setDistance(final Float distance) {
      this.distance = distance;
   }

   public void setCurrent(final Float current) {
      this.current = current;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof CircuitBrCAODto)) {
         return false;
      }

      CircuitBrCAODto other = (CircuitBrCAODto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$time = this.getTime();
      Object other$time = other.getTime();
      if (this$time == null ? other$time == null : this$time.equals(other$time)) {
         Object this$distance = this.getDistance();
         Object other$distance = other.getDistance();
         if (this$distance == null ? other$distance == null : this$distance.equals(other$distance)) {
            Object this$current = this.getCurrent();
            Object other$current = other.getCurrent();
            return this$current == null ? other$current == null : this$current.equals(other$current);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof CircuitBrCAODto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $time = this.getTime();
      result = result * 59 + ($time == null ? 43 : $time.hashCode());
      Object $distance = this.getDistance();
      result = result * 59 + ($distance == null ? 43 : $distance.hashCode());
      Object $current = this.getCurrent();
      return result * 59 + ($current == null ? 43 : $current.hashCode());
   }

   @Override
   public String toString() {
      return "CircuitBrCAODto(time=" + this.getTime() + ", distance=" + this.getDistance() + ", current=" + this.getCurrent() + ")";
   }
}
