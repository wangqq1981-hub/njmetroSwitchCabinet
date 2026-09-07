package com.drsi.njmsc.dto.dto;

public class ThrComparisonReqDto {
   private ThrComparisonReqDto.ThreePhaseData temp_region;
   private Integer thresh;
   private Float thresh_p;

   public ThrComparisonReqDto(Float a, Float b, Float c) {
      this.temp_region = new ThrComparisonReqDto.ThreePhaseData(a, b, c);
      this.thresh = 10;
      this.thresh_p = 0.1F;
   }

   public ThrComparisonReqDto.ThreePhaseData getTemp_region() {
      return this.temp_region;
   }

   public Integer getThresh() {
      return this.thresh;
   }

   public Float getThresh_p() {
      return this.thresh_p;
   }

   public void setTemp_region(final ThrComparisonReqDto.ThreePhaseData temp_region) {
      this.temp_region = temp_region;
   }

   public void setThresh(final Integer thresh) {
      this.thresh = thresh;
   }

   public void setThresh_p(final Float thresh_p) {
      this.thresh_p = thresh_p;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof ThrComparisonReqDto)) {
         return false;
      }

      ThrComparisonReqDto other = (ThrComparisonReqDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$thresh = this.getThresh();
      Object other$thresh = other.getThresh();
      if (this$thresh == null ? other$thresh == null : this$thresh.equals(other$thresh)) {
         Object this$thresh_p = this.getThresh_p();
         Object other$thresh_p = other.getThresh_p();
         if (this$thresh_p == null ? other$thresh_p == null : this$thresh_p.equals(other$thresh_p)) {
            Object this$temp_region = this.getTemp_region();
            Object other$temp_region = other.getTemp_region();
            return this$temp_region == null ? other$temp_region == null : this$temp_region.equals(other$temp_region);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof ThrComparisonReqDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $thresh = this.getThresh();
      result = result * 59 + ($thresh == null ? 43 : $thresh.hashCode());
      Object $thresh_p = this.getThresh_p();
      result = result * 59 + ($thresh_p == null ? 43 : $thresh_p.hashCode());
      Object $temp_region = this.getTemp_region();
      return result * 59 + ($temp_region == null ? 43 : $temp_region.hashCode());
   }

   @Override
   public String toString() {
      return "ThrComparisonReqDto(temp_region=" + this.getTemp_region() + ", thresh=" + this.getThresh() + ", thresh_p=" + this.getThresh_p() + ")";
   }

   public static class ThreePhaseData {
      private Float A;
      private Float B;
      private Float C;

      public ThreePhaseData() {
      }

      public ThreePhaseData(Float a, Float b, Float c) {
         this.A = a;
         this.B = b;
         this.C = c;
      }

      public Float getA() {
         return this.A;
      }

      public Float getB() {
         return this.B;
      }

      public Float getC() {
         return this.C;
      }

      public void setA(final Float A) {
         this.A = A;
      }

      public void setB(final Float B) {
         this.B = B;
      }

      public void setC(final Float C) {
         this.C = C;
      }

      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         }

         if (!(o instanceof ThrComparisonReqDto.ThreePhaseData)) {
            return false;
         }

         ThrComparisonReqDto.ThreePhaseData other = (ThrComparisonReqDto.ThreePhaseData)o;
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$A = this.getA();
         Object other$A = other.getA();
         if (this$A == null ? other$A == null : this$A.equals(other$A)) {
            Object this$B = this.getB();
            Object other$B = other.getB();
            if (this$B == null ? other$B == null : this$B.equals(other$B)) {
               Object this$C = this.getC();
               Object other$C = other.getC();
               return this$C == null ? other$C == null : this$C.equals(other$C);
            } else {
               return false;
            }
         } else {
            return false;
         }
      }

      protected boolean canEqual(final Object other) {
         return other instanceof ThrComparisonReqDto.ThreePhaseData;
      }

      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $A = this.getA();
         result = result * 59 + ($A == null ? 43 : $A.hashCode());
         Object $B = this.getB();
         result = result * 59 + ($B == null ? 43 : $B.hashCode());
         Object $C = this.getC();
         return result * 59 + ($C == null ? 43 : $C.hashCode());
      }

      @Override
      public String toString() {
         return "ThrComparisonReqDto.ThreePhaseData(A=" + this.getA() + ", B=" + this.getB() + ", C=" + this.getC() + ")";
      }
   }
}
