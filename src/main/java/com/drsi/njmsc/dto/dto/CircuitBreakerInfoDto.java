package com.drsi.njmsc.dto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CircuitBreakerInfoDto对象", description = "断路器 基本信息")
public class CircuitBreakerInfoDto {
   @ApiModelProperty("合闸次数")
   private Integer closeOperateNum;
   @ApiModelProperty("分闸次数")
   private Integer openOperateNum;
   @ApiModelProperty("剩余寿命(%)")
   private Double remainingLife;
   @ApiModelProperty("剩余次数")
   private Integer remainingNum;
   @ApiModelProperty("设备状态 0-正常，1-异常")
   private Integer deviceStatus;

   public Integer getCloseOperateNum() {
      return this.closeOperateNum;
   }

   public Integer getOpenOperateNum() {
      return this.openOperateNum;
   }

   public Double getRemainingLife() {
      return this.remainingLife;
   }

   public Integer getRemainingNum() {
      return this.remainingNum;
   }

   public Integer getDeviceStatus() {
      return this.deviceStatus;
   }

   public void setCloseOperateNum(final Integer closeOperateNum) {
      this.closeOperateNum = closeOperateNum;
   }

   public void setOpenOperateNum(final Integer openOperateNum) {
      this.openOperateNum = openOperateNum;
   }

   public void setRemainingLife(final Double remainingLife) {
      this.remainingLife = remainingLife;
   }

   public void setRemainingNum(final Integer remainingNum) {
      this.remainingNum = remainingNum;
   }

   public void setDeviceStatus(final Integer deviceStatus) {
      this.deviceStatus = deviceStatus;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof CircuitBreakerInfoDto)) {
         return false;
      }

      CircuitBreakerInfoDto other = (CircuitBreakerInfoDto)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$closeOperateNum = this.getCloseOperateNum();
      Object other$closeOperateNum = other.getCloseOperateNum();
      if (this$closeOperateNum == null ? other$closeOperateNum == null : this$closeOperateNum.equals(other$closeOperateNum)) {
         Object this$openOperateNum = this.getOpenOperateNum();
         Object other$openOperateNum = other.getOpenOperateNum();
         if (this$openOperateNum == null ? other$openOperateNum == null : this$openOperateNum.equals(other$openOperateNum)) {
            Object this$remainingLife = this.getRemainingLife();
            Object other$remainingLife = other.getRemainingLife();
            if (this$remainingLife == null ? other$remainingLife == null : this$remainingLife.equals(other$remainingLife)) {
               Object this$remainingNum = this.getRemainingNum();
               Object other$remainingNum = other.getRemainingNum();
               if (this$remainingNum == null ? other$remainingNum == null : this$remainingNum.equals(other$remainingNum)) {
                  Object this$deviceStatus = this.getDeviceStatus();
                  Object other$deviceStatus = other.getDeviceStatus();
                  return this$deviceStatus == null ? other$deviceStatus == null : this$deviceStatus.equals(other$deviceStatus);
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
      return other instanceof CircuitBreakerInfoDto;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $closeOperateNum = this.getCloseOperateNum();
      result = result * 59 + ($closeOperateNum == null ? 43 : $closeOperateNum.hashCode());
      Object $openOperateNum = this.getOpenOperateNum();
      result = result * 59 + ($openOperateNum == null ? 43 : $openOperateNum.hashCode());
      Object $remainingLife = this.getRemainingLife();
      result = result * 59 + ($remainingLife == null ? 43 : $remainingLife.hashCode());
      Object $remainingNum = this.getRemainingNum();
      result = result * 59 + ($remainingNum == null ? 43 : $remainingNum.hashCode());
      Object $deviceStatus = this.getDeviceStatus();
      return result * 59 + ($deviceStatus == null ? 43 : $deviceStatus.hashCode());
   }

   @Override
   public String toString() {
      return "CircuitBreakerInfoDto(closeOperateNum="
         + this.getCloseOperateNum()
         + ", openOperateNum="
         + this.getOpenOperateNum()
         + ", remainingLife="
         + this.getRemainingLife()
         + ", remainingNum="
         + this.getRemainingNum()
         + ", deviceStatus="
         + this.getDeviceStatus()
         + ")";
   }
}
