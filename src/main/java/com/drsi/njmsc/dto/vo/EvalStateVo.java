package com.drsi.njmsc.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(value = "EvalStateVo对象", description = "设备状态评估结果对象")
public class EvalStateVo {
   @ApiModelProperty("评估分数（百分百）,【正常：100~80，关注：80~60，维修：60~40，抢修：低于40")
   public Float score;
   @ApiModelProperty("评估等级【正常：0、关注：1、维修：2、抢修:3】")
   public String evalState;
   @ApiModelProperty("异常项")
   public List<String> jitemDetails;
   @ApiModelProperty("维修策略")
   public List<String> repairSubject;

   public Float getScore() {
      return this.score;
   }

   public String getEvalState() {
      return this.evalState;
   }

   public List<String> getJitemDetails() {
      return this.jitemDetails;
   }

   public List<String> getRepairSubject() {
      return this.repairSubject;
   }

   public void setScore(final Float score) {
      this.score = score;
   }

   public void setEvalState(final String evalState) {
      this.evalState = evalState;
   }

   public void setJitemDetails(final List<String> jitemDetails) {
      this.jitemDetails = jitemDetails;
   }

   public void setRepairSubject(final List<String> repairSubject) {
      this.repairSubject = repairSubject;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof EvalStateVo)) {
         return false;
      }

      EvalStateVo other = (EvalStateVo)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$score = this.getScore();
      Object other$score = other.getScore();
      if (this$score == null ? other$score == null : this$score.equals(other$score)) {
         Object this$evalState = this.getEvalState();
         Object other$evalState = other.getEvalState();
         if (this$evalState == null ? other$evalState == null : this$evalState.equals(other$evalState)) {
            Object this$jitemDetails = this.getJitemDetails();
            Object other$jitemDetails = other.getJitemDetails();
            if (this$jitemDetails == null ? other$jitemDetails == null : this$jitemDetails.equals(other$jitemDetails)) {
               Object this$repairSubject = this.getRepairSubject();
               Object other$repairSubject = other.getRepairSubject();
               return this$repairSubject == null ? other$repairSubject == null : this$repairSubject.equals(other$repairSubject);
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
      return other instanceof EvalStateVo;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $score = this.getScore();
      result = result * 59 + ($score == null ? 43 : $score.hashCode());
      Object $evalState = this.getEvalState();
      result = result * 59 + ($evalState == null ? 43 : $evalState.hashCode());
      Object $jitemDetails = this.getJitemDetails();
      result = result * 59 + ($jitemDetails == null ? 43 : $jitemDetails.hashCode());
      Object $repairSubject = this.getRepairSubject();
      return result * 59 + ($repairSubject == null ? 43 : $repairSubject.hashCode());
   }

   @Override
   public String toString() {
      return "EvalStateVo(score="
         + this.getScore()
         + ", evalState="
         + this.getEvalState()
         + ", jitemDetails="
         + this.getJitemDetails()
         + ", repairSubject="
         + this.getRepairSubject()
         + ")";
   }
}
