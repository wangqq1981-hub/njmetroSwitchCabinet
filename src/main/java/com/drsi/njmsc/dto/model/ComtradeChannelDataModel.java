package com.drsi.njmsc.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class ComtradeChannelDataModel {
   private Integer n;
   private String chId;
   private String ph;
   private String ccbm;
   private String uu;
   private List<ChannelDataModel> waveRawData;
   private Integer y;
   private Integer tpOffset;

   public Integer getN() {
      return this.n;
   }

   public String getChId() {
      return this.chId;
   }

   public String getPh() {
      return this.ph;
   }

   public String getCcbm() {
      return this.ccbm;
   }

   public String getUu() {
      return this.uu;
   }

   public List<ChannelDataModel> getWaveRawData() {
      return this.waveRawData;
   }

   public Integer getY() {
      return this.y;
   }

   public Integer getTpOffset() {
      return this.tpOffset;
   }

   public ComtradeChannelDataModel setN(final Integer n) {
      this.n = n;
      return this;
   }

   public ComtradeChannelDataModel setChId(final String chId) {
      this.chId = chId;
      return this;
   }

   public ComtradeChannelDataModel setPh(final String ph) {
      this.ph = ph;
      return this;
   }

   public ComtradeChannelDataModel setCcbm(final String ccbm) {
      this.ccbm = ccbm;
      return this;
   }

   public ComtradeChannelDataModel setUu(final String uu) {
      this.uu = uu;
      return this;
   }

   public ComtradeChannelDataModel setWaveRawData(final List<ChannelDataModel> waveRawData) {
      this.waveRawData = waveRawData;
      return this;
   }

   public ComtradeChannelDataModel setY(final Integer y) {
      this.y = y;
      return this;
   }

   public ComtradeChannelDataModel setTpOffset(final Integer tpOffset) {
      this.tpOffset = tpOffset;
      return this;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof ComtradeChannelDataModel)) {
         return false;
      }

      ComtradeChannelDataModel other = (ComtradeChannelDataModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$n = this.getN();
      Object other$n = other.getN();
      if (this$n == null ? other$n == null : this$n.equals(other$n)) {
         Object this$y = this.getY();
         Object other$y = other.getY();
         if (this$y == null ? other$y == null : this$y.equals(other$y)) {
            Object this$tpOffset = this.getTpOffset();
            Object other$tpOffset = other.getTpOffset();
            if (this$tpOffset == null ? other$tpOffset == null : this$tpOffset.equals(other$tpOffset)) {
               Object this$chId = this.getChId();
               Object other$chId = other.getChId();
               if (this$chId == null ? other$chId == null : this$chId.equals(other$chId)) {
                  Object this$ph = this.getPh();
                  Object other$ph = other.getPh();
                  if (this$ph == null ? other$ph == null : this$ph.equals(other$ph)) {
                     Object this$ccbm = this.getCcbm();
                     Object other$ccbm = other.getCcbm();
                     if (this$ccbm == null ? other$ccbm == null : this$ccbm.equals(other$ccbm)) {
                        Object this$uu = this.getUu();
                        Object other$uu = other.getUu();
                        if (this$uu == null ? other$uu == null : this$uu.equals(other$uu)) {
                           Object this$waveRawData = this.getWaveRawData();
                           Object other$waveRawData = other.getWaveRawData();
                           return this$waveRawData == null ? other$waveRawData == null : this$waveRawData.equals(other$waveRawData);
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
      return other instanceof ComtradeChannelDataModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $n = this.getN();
      result = result * 59 + ($n == null ? 43 : $n.hashCode());
      Object $y = this.getY();
      result = result * 59 + ($y == null ? 43 : $y.hashCode());
      Object $tpOffset = this.getTpOffset();
      result = result * 59 + ($tpOffset == null ? 43 : $tpOffset.hashCode());
      Object $chId = this.getChId();
      result = result * 59 + ($chId == null ? 43 : $chId.hashCode());
      Object $ph = this.getPh();
      result = result * 59 + ($ph == null ? 43 : $ph.hashCode());
      Object $ccbm = this.getCcbm();
      result = result * 59 + ($ccbm == null ? 43 : $ccbm.hashCode());
      Object $uu = this.getUu();
      result = result * 59 + ($uu == null ? 43 : $uu.hashCode());
      Object $waveRawData = this.getWaveRawData();
      return result * 59 + ($waveRawData == null ? 43 : $waveRawData.hashCode());
   }

   @Override
   public String toString() {
      return "ComtradeChannelDataModel(n="
         + this.getN()
         + ", chId="
         + this.getChId()
         + ", ph="
         + this.getPh()
         + ", ccbm="
         + this.getCcbm()
         + ", uu="
         + this.getUu()
         + ", waveRawData="
         + this.getWaveRawData()
         + ", y="
         + this.getY()
         + ", tpOffset="
         + this.getTpOffset()
         + ")";
   }
}
