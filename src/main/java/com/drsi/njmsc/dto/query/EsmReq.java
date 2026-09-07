package com.drsi.njmsc.dto.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "EsmReq对象", description = "储能电机模块数据")
public class EsmReq extends BaseReq {
   @ApiModelProperty("储能电机模块数据id")
   private Long id;
   @ApiModelProperty("设备编码")
   private String deviceCode;

   public Long getId() {
      return this.id;
   }

   public String getDeviceCode() {
      return this.deviceCode;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public void setDeviceCode(final String deviceCode) {
      this.deviceCode = deviceCode;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EsmReq)) {
         return false;
      } else {
         EsmReq other = (EsmReq)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$id = this.getId();
            Object other$id = other.getId();
            if (this$id == null ? other$id == null : this$id.equals(other$id)) {
               Object this$deviceCode = this.getDeviceCode();
               Object other$deviceCode = other.getDeviceCode();
               return this$deviceCode == null ? other$deviceCode == null : this$deviceCode.equals(other$deviceCode);
            } else {
               return false;
            }
         }
      }
   }

   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof EsmReq;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $deviceCode = this.getDeviceCode();
      return result * 59 + ($deviceCode == null ? 43 : $deviceCode.hashCode());
   }

   @Override
   public String toString() {
      return "EsmReq(id=" + this.getId() + ", deviceCode=" + this.getDeviceCode() + ")";
   }
}
