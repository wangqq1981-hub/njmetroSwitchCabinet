package com.drsi.njmsc.dto.model;

public class AnalogChannelInfoModel {
   private Integer an;
   private String chId;
   private String ph;
   private String ccbm;
   private String uu;
   private Float a;
   private Float b;
   private Float skew;
   private Integer min;
   private Integer max;
   private Float primary;
   private Float secondary;
   private String ps;

   public Integer getAn() {
      return this.an;
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

   public Float getA() {
      return this.a;
   }

   public Float getB() {
      return this.b;
   }

   public Float getSkew() {
      return this.skew;
   }

   public Integer getMin() {
      return this.min;
   }

   public Integer getMax() {
      return this.max;
   }

   public Float getPrimary() {
      return this.primary;
   }

   public Float getSecondary() {
      return this.secondary;
   }

   public String getPs() {
      return this.ps;
   }

   public void setAn(final Integer an) {
      this.an = an;
   }

   public void setChId(final String chId) {
      this.chId = chId;
   }

   public void setPh(final String ph) {
      this.ph = ph;
   }

   public void setCcbm(final String ccbm) {
      this.ccbm = ccbm;
   }

   public void setUu(final String uu) {
      this.uu = uu;
   }

   public void setA(final Float a) {
      this.a = a;
   }

   public void setB(final Float b) {
      this.b = b;
   }

   public void setSkew(final Float skew) {
      this.skew = skew;
   }

   public void setMin(final Integer min) {
      this.min = min;
   }

   public void setMax(final Integer max) {
      this.max = max;
   }

   public void setPrimary(final Float primary) {
      this.primary = primary;
   }

   public void setSecondary(final Float secondary) {
      this.secondary = secondary;
   }

   public void setPs(final String ps) {
      this.ps = ps;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof AnalogChannelInfoModel)) {
         return false;
      }

      AnalogChannelInfoModel other = (AnalogChannelInfoModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$an = this.getAn();
      Object other$an = other.getAn();
      if (this$an == null ? other$an == null : this$an.equals(other$an)) {
         Object this$a = this.getA();
         Object other$a = other.getA();
         if (this$a == null ? other$a == null : this$a.equals(other$a)) {
            Object this$b = this.getB();
            Object other$b = other.getB();
            if (this$b == null ? other$b == null : this$b.equals(other$b)) {
               Object this$skew = this.getSkew();
               Object other$skew = other.getSkew();
               if (this$skew == null ? other$skew == null : this$skew.equals(other$skew)) {
                  Object this$min = this.getMin();
                  Object other$min = other.getMin();
                  if (this$min == null ? other$min == null : this$min.equals(other$min)) {
                     Object this$max = this.getMax();
                     Object other$max = other.getMax();
                     if (this$max == null ? other$max == null : this$max.equals(other$max)) {
                        Object this$primary = this.getPrimary();
                        Object other$primary = other.getPrimary();
                        if (this$primary == null ? other$primary == null : this$primary.equals(other$primary)) {
                           Object this$secondary = this.getSecondary();
                           Object other$secondary = other.getSecondary();
                           if (this$secondary == null ? other$secondary == null : this$secondary.equals(other$secondary)) {
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
                                          Object this$ps = this.getPs();
                                          Object other$ps = other.getPs();
                                          return this$ps == null ? other$ps == null : this$ps.equals(other$ps);
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
      } else {
         return false;
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof AnalogChannelInfoModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $an = this.getAn();
      result = result * 59 + ($an == null ? 43 : $an.hashCode());
      Object $a = this.getA();
      result = result * 59 + ($a == null ? 43 : $a.hashCode());
      Object $b = this.getB();
      result = result * 59 + ($b == null ? 43 : $b.hashCode());
      Object $skew = this.getSkew();
      result = result * 59 + ($skew == null ? 43 : $skew.hashCode());
      Object $min = this.getMin();
      result = result * 59 + ($min == null ? 43 : $min.hashCode());
      Object $max = this.getMax();
      result = result * 59 + ($max == null ? 43 : $max.hashCode());
      Object $primary = this.getPrimary();
      result = result * 59 + ($primary == null ? 43 : $primary.hashCode());
      Object $secondary = this.getSecondary();
      result = result * 59 + ($secondary == null ? 43 : $secondary.hashCode());
      Object $chId = this.getChId();
      result = result * 59 + ($chId == null ? 43 : $chId.hashCode());
      Object $ph = this.getPh();
      result = result * 59 + ($ph == null ? 43 : $ph.hashCode());
      Object $ccbm = this.getCcbm();
      result = result * 59 + ($ccbm == null ? 43 : $ccbm.hashCode());
      Object $uu = this.getUu();
      result = result * 59 + ($uu == null ? 43 : $uu.hashCode());
      Object $ps = this.getPs();
      return result * 59 + ($ps == null ? 43 : $ps.hashCode());
   }

   @Override
   public String toString() {
      return "AnalogChannelInfoModel(an="
         + this.getAn()
         + ", chId="
         + this.getChId()
         + ", ph="
         + this.getPh()
         + ", ccbm="
         + this.getCcbm()
         + ", uu="
         + this.getUu()
         + ", a="
         + this.getA()
         + ", b="
         + this.getB()
         + ", skew="
         + this.getSkew()
         + ", min="
         + this.getMin()
         + ", max="
         + this.getMax()
         + ", primary="
         + this.getPrimary()
         + ", secondary="
         + this.getSecondary()
         + ", ps="
         + this.getPs()
         + ")";
   }
}
