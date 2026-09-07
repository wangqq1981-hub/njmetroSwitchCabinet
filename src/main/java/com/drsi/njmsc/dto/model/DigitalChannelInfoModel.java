package com.drsi.njmsc.dto.model;

public class DigitalChannelInfoModel {
   private Integer dn;
   private String chId;
   private String ph;
   private String ccbm;
   private Integer y;

   public Integer getDn() {
      return this.dn;
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

   public Integer getY() {
      return this.y;
   }

   public void setDn(final Integer dn) {
      this.dn = dn;
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

   public void setY(final Integer y) {
      this.y = y;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof DigitalChannelInfoModel)) {
         return false;
      }

      DigitalChannelInfoModel other = (DigitalChannelInfoModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$dn = this.getDn();
      Object other$dn = other.getDn();
      if (this$dn == null ? other$dn == null : this$dn.equals(other$dn)) {
         Object this$y = this.getY();
         Object other$y = other.getY();
         if (this$y == null ? other$y == null : this$y.equals(other$y)) {
            Object this$chId = this.getChId();
            Object other$chId = other.getChId();
            if (this$chId == null ? other$chId == null : this$chId.equals(other$chId)) {
               Object this$ph = this.getPh();
               Object other$ph = other.getPh();
               if (this$ph == null ? other$ph == null : this$ph.equals(other$ph)) {
                  Object this$ccbm = this.getCcbm();
                  Object other$ccbm = other.getCcbm();
                  return this$ccbm == null ? other$ccbm == null : this$ccbm.equals(other$ccbm);
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
      return other instanceof DigitalChannelInfoModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $dn = this.getDn();
      result = result * 59 + ($dn == null ? 43 : $dn.hashCode());
      Object $y = this.getY();
      result = result * 59 + ($y == null ? 43 : $y.hashCode());
      Object $chId = this.getChId();
      result = result * 59 + ($chId == null ? 43 : $chId.hashCode());
      Object $ph = this.getPh();
      result = result * 59 + ($ph == null ? 43 : $ph.hashCode());
      Object $ccbm = this.getCcbm();
      return result * 59 + ($ccbm == null ? 43 : $ccbm.hashCode());
   }

   @Override
   public String toString() {
      return "DigitalChannelInfoModel(dn="
         + this.getDn()
         + ", chId="
         + this.getChId()
         + ", ph="
         + this.getPh()
         + ", ccbm="
         + this.getCcbm()
         + ", y="
         + this.getY()
         + ")";
   }
}
