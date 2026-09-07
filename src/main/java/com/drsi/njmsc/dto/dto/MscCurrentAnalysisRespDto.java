package com.drsi.njmsc.dto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MscCurrentAnalysisRespDto {
   private Integer starOfRawData;
   private Integer tsStartOffset;
   private Integer tsEndOffset;
   private Integer tiStartOffset;
   private Integer tiEndOffset;
   private Integer t0StartOffset;
   private Integer t0EndOffset;
   private Integer tcStartOffset;
   private Integer tcEndOffset;
   private Integer tbStartOffset;
   private Integer tbEndOffset;
   @JsonProperty("iMax")
   private Float iMax;

   public Integer getStarOfRawData() {
      return this.starOfRawData;
   }

   public Integer getTsStartOffset() {
      return this.tsStartOffset;
   }

   public Integer getTsEndOffset() {
      return this.tsEndOffset;
   }

   public Integer getTiStartOffset() {
      return this.tiStartOffset;
   }

   public Integer getTiEndOffset() {
      return this.tiEndOffset;
   }

   public Integer getT0StartOffset() {
      return this.t0StartOffset;
   }

   public Integer getT0EndOffset() {
      return this.t0EndOffset;
   }

   public Integer getTcStartOffset() {
      return this.tcStartOffset;
   }

   public Integer getTcEndOffset() {
      return this.tcEndOffset;
   }

   public Integer getTbStartOffset() {
      return this.tbStartOffset;
   }

   public Integer getTbEndOffset() {
      return this.tbEndOffset;
   }

   public Float getIMax() {
      return this.iMax;
   }

   public void setStarOfRawData(final Integer starOfRawData) {
      this.starOfRawData = starOfRawData;
   }

   public void setTsStartOffset(final Integer tsStartOffset) {
      this.tsStartOffset = tsStartOffset;
   }

   public void setTsEndOffset(final Integer tsEndOffset) {
      this.tsEndOffset = tsEndOffset;
   }

   public void setTiStartOffset(final Integer tiStartOffset) {
      this.tiStartOffset = tiStartOffset;
   }

   public void setTiEndOffset(final Integer tiEndOffset) {
      this.tiEndOffset = tiEndOffset;
   }

   public void setT0StartOffset(final Integer t0StartOffset) {
      this.t0StartOffset = t0StartOffset;
   }

   public void setT0EndOffset(final Integer t0EndOffset) {
      this.t0EndOffset = t0EndOffset;
   }

   public void setTcStartOffset(final Integer tcStartOffset) {
      this.tcStartOffset = tcStartOffset;
   }

   public void setTcEndOffset(final Integer tcEndOffset) {
      this.tcEndOffset = tcEndOffset;
   }

   public void setTbStartOffset(final Integer tbStartOffset) {
      this.tbStartOffset = tbStartOffset;
   }

   public void setTbEndOffset(final Integer tbEndOffset) {
      this.tbEndOffset = tbEndOffset;
   }

   @JsonProperty("iMax")
   public void setIMax(final Float iMax) {
      this.iMax = iMax;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof MscCurrentAnalysisRespDto)) {
         return false;
      }

      MscCurrentAnalysisRespDto other = (MscCurrentAnalysisRespDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$starOfRawData = this.getStarOfRawData();
      Object other$starOfRawData = other.getStarOfRawData();
      if (this$starOfRawData == null ? other$starOfRawData == null : this$starOfRawData.equals(other$starOfRawData)) {
         Object this$tsStartOffset = this.getTsStartOffset();
         Object other$tsStartOffset = other.getTsStartOffset();
         if (this$tsStartOffset == null ? other$tsStartOffset == null : this$tsStartOffset.equals(other$tsStartOffset)) {
            Object this$tsEndOffset = this.getTsEndOffset();
            Object other$tsEndOffset = other.getTsEndOffset();
            if (this$tsEndOffset == null ? other$tsEndOffset == null : this$tsEndOffset.equals(other$tsEndOffset)) {
               Object this$tiStartOffset = this.getTiStartOffset();
               Object other$tiStartOffset = other.getTiStartOffset();
               if (this$tiStartOffset == null ? other$tiStartOffset == null : this$tiStartOffset.equals(other$tiStartOffset)) {
                  Object this$tiEndOffset = this.getTiEndOffset();
                  Object other$tiEndOffset = other.getTiEndOffset();
                  if (this$tiEndOffset == null ? other$tiEndOffset == null : this$tiEndOffset.equals(other$tiEndOffset)) {
                     Object this$t0StartOffset = this.getT0StartOffset();
                     Object other$t0StartOffset = other.getT0StartOffset();
                     if (this$t0StartOffset == null ? other$t0StartOffset == null : this$t0StartOffset.equals(other$t0StartOffset)) {
                        Object this$t0EndOffset = this.getT0EndOffset();
                        Object other$t0EndOffset = other.getT0EndOffset();
                        if (this$t0EndOffset == null ? other$t0EndOffset == null : this$t0EndOffset.equals(other$t0EndOffset)) {
                           Object this$tcStartOffset = this.getTcStartOffset();
                           Object other$tcStartOffset = other.getTcStartOffset();
                           if (this$tcStartOffset == null ? other$tcStartOffset == null : this$tcStartOffset.equals(other$tcStartOffset)) {
                              Object this$tcEndOffset = this.getTcEndOffset();
                              Object other$tcEndOffset = other.getTcEndOffset();
                              if (this$tcEndOffset == null ? other$tcEndOffset == null : this$tcEndOffset.equals(other$tcEndOffset)) {
                                 Object this$tbStartOffset = this.getTbStartOffset();
                                 Object other$tbStartOffset = other.getTbStartOffset();
                                 if (this$tbStartOffset == null ? other$tbStartOffset == null : this$tbStartOffset.equals(other$tbStartOffset)) {
                                    Object this$tbEndOffset = this.getTbEndOffset();
                                    Object other$tbEndOffset = other.getTbEndOffset();
                                    if (this$tbEndOffset == null ? other$tbEndOffset == null : this$tbEndOffset.equals(other$tbEndOffset)) {
                                       Object this$iMax = this.getIMax();
                                       Object other$iMax = other.getIMax();
                                       return this$iMax == null ? other$iMax == null : this$iMax.equals(other$iMax);
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
      return other instanceof MscCurrentAnalysisRespDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $starOfRawData = this.getStarOfRawData();
      result = result * 59 + ($starOfRawData == null ? 43 : $starOfRawData.hashCode());
      Object $tsStartOffset = this.getTsStartOffset();
      result = result * 59 + ($tsStartOffset == null ? 43 : $tsStartOffset.hashCode());
      Object $tsEndOffset = this.getTsEndOffset();
      result = result * 59 + ($tsEndOffset == null ? 43 : $tsEndOffset.hashCode());
      Object $tiStartOffset = this.getTiStartOffset();
      result = result * 59 + ($tiStartOffset == null ? 43 : $tiStartOffset.hashCode());
      Object $tiEndOffset = this.getTiEndOffset();
      result = result * 59 + ($tiEndOffset == null ? 43 : $tiEndOffset.hashCode());
      Object $t0StartOffset = this.getT0StartOffset();
      result = result * 59 + ($t0StartOffset == null ? 43 : $t0StartOffset.hashCode());
      Object $t0EndOffset = this.getT0EndOffset();
      result = result * 59 + ($t0EndOffset == null ? 43 : $t0EndOffset.hashCode());
      Object $tcStartOffset = this.getTcStartOffset();
      result = result * 59 + ($tcStartOffset == null ? 43 : $tcStartOffset.hashCode());
      Object $tcEndOffset = this.getTcEndOffset();
      result = result * 59 + ($tcEndOffset == null ? 43 : $tcEndOffset.hashCode());
      Object $tbStartOffset = this.getTbStartOffset();
      result = result * 59 + ($tbStartOffset == null ? 43 : $tbStartOffset.hashCode());
      Object $tbEndOffset = this.getTbEndOffset();
      result = result * 59 + ($tbEndOffset == null ? 43 : $tbEndOffset.hashCode());
      Object $iMax = this.getIMax();
      return result * 59 + ($iMax == null ? 43 : $iMax.hashCode());
   }

   @Override
   public String toString() {
      return "MscCurrentAnalysisRespDto(starOfRawData="
         + this.getStarOfRawData()
         + ", tsStartOffset="
         + this.getTsStartOffset()
         + ", tsEndOffset="
         + this.getTsEndOffset()
         + ", tiStartOffset="
         + this.getTiStartOffset()
         + ", tiEndOffset="
         + this.getTiEndOffset()
         + ", t0StartOffset="
         + this.getT0StartOffset()
         + ", t0EndOffset="
         + this.getT0EndOffset()
         + ", tcStartOffset="
         + this.getTcStartOffset()
         + ", tcEndOffset="
         + this.getTcEndOffset()
         + ", tbStartOffset="
         + this.getTbStartOffset()
         + ", tbEndOffset="
         + this.getTbEndOffset()
         + ", iMax="
         + this.getIMax()
         + ")";
   }
}
