package com.drsi.njmsc.dto.dto;

public class ThrComparisonRespDto {
   private String describe;
   private Boolean is_alarm;
   private ThrComparisonReqDto.ThreePhaseData max_region;
   private ThrComparisonReqDto.ThreePhaseData min_region;
   private Integer status;

   public String getDescribe() {
      return this.describe;
   }

   public Boolean getIs_alarm() {
      return this.is_alarm;
   }

   public ThrComparisonReqDto.ThreePhaseData getMax_region() {
      return this.max_region;
   }

   public ThrComparisonReqDto.ThreePhaseData getMin_region() {
      return this.min_region;
   }

   public Integer getStatus() {
      return this.status;
   }

   public void setDescribe(final String describe) {
      this.describe = describe;
   }

   public void setIs_alarm(final Boolean is_alarm) {
      this.is_alarm = is_alarm;
   }

   public void setMax_region(final ThrComparisonReqDto.ThreePhaseData max_region) {
      this.max_region = max_region;
   }

   public void setMin_region(final ThrComparisonReqDto.ThreePhaseData min_region) {
      this.min_region = min_region;
   }

   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof ThrComparisonRespDto)) {
         return false;
      }

      ThrComparisonRespDto other = (ThrComparisonRespDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$is_alarm = this.getIs_alarm();
      Object other$is_alarm = other.getIs_alarm();
      if (this$is_alarm == null ? other$is_alarm == null : this$is_alarm.equals(other$is_alarm)) {
         Object this$status = this.getStatus();
         Object other$status = other.getStatus();
         if (this$status == null ? other$status == null : this$status.equals(other$status)) {
            Object this$describe = this.getDescribe();
            Object other$describe = other.getDescribe();
            if (this$describe == null ? other$describe == null : this$describe.equals(other$describe)) {
               Object this$max_region = this.getMax_region();
               Object other$max_region = other.getMax_region();
               if (this$max_region == null ? other$max_region == null : this$max_region.equals(other$max_region)) {
                  Object this$min_region = this.getMin_region();
                  Object other$min_region = other.getMin_region();
                  return this$min_region == null ? other$min_region == null : this$min_region.equals(other$min_region);
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
      return other instanceof ThrComparisonRespDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $is_alarm = this.getIs_alarm();
      result = result * 59 + ($is_alarm == null ? 43 : $is_alarm.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $describe = this.getDescribe();
      result = result * 59 + ($describe == null ? 43 : $describe.hashCode());
      Object $max_region = this.getMax_region();
      result = result * 59 + ($max_region == null ? 43 : $max_region.hashCode());
      Object $min_region = this.getMin_region();
      return result * 59 + ($min_region == null ? 43 : $min_region.hashCode());
   }

   @Override
   public String toString() {
      return "ThrComparisonRespDto(describe="
         + this.getDescribe()
         + ", is_alarm="
         + this.getIs_alarm()
         + ", max_region="
         + this.getMax_region()
         + ", min_region="
         + this.getMin_region()
         + ", status="
         + this.getStatus()
         + ")";
   }
}
