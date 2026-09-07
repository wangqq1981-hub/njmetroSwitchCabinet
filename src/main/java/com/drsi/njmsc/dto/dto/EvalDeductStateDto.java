package com.drsi.njmsc.dto.dto;

import java.util.List;

public class EvalDeductStateDto {
   private Float deduct;
   private List<String> jItems;
   private String evalState;

   public Float getDeduct() {
      return this.deduct;
   }

   public List<String> getJItems() {
      return this.jItems;
   }

   public String getEvalState() {
      return this.evalState;
   }

   public void setDeduct(final Float deduct) {
      this.deduct = deduct;
   }

   public void setJItems(final List<String> jItems) {
      this.jItems = jItems;
   }

   public void setEvalState(final String evalState) {
      this.evalState = evalState;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof EvalDeductStateDto)) {
         return false;
      }

      EvalDeductStateDto other = (EvalDeductStateDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$deduct = this.getDeduct();
      Object other$deduct = other.getDeduct();
      if (this$deduct == null ? other$deduct == null : this$deduct.equals(other$deduct)) {
         Object this$jItems = this.getJItems();
         Object other$jItems = other.getJItems();
         if (this$jItems == null ? other$jItems == null : this$jItems.equals(other$jItems)) {
            Object this$evalState = this.getEvalState();
            Object other$evalState = other.getEvalState();
            return this$evalState == null ? other$evalState == null : this$evalState.equals(other$evalState);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof EvalDeductStateDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $deduct = this.getDeduct();
      result = result * 59 + ($deduct == null ? 43 : $deduct.hashCode());
      Object $jItems = this.getJItems();
      result = result * 59 + ($jItems == null ? 43 : $jItems.hashCode());
      Object $evalState = this.getEvalState();
      return result * 59 + ($evalState == null ? 43 : $evalState.hashCode());
   }

   @Override
   public String toString() {
      return "EvalDeductStateDto(deduct=" + this.getDeduct() + ", jItems=" + this.getJItems() + ", evalState=" + this.getEvalState() + ")";
   }
}
