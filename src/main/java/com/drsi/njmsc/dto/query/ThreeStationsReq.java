package com.drsi.njmsc.dto.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ThreeStationsReq对象", description = "三工位模块数据")
public class ThreeStationsReq extends BaseReq {
   @ApiModelProperty("三工位模块数据id")
   private Long id;
   @ApiModelProperty("设备编码")
   private String deviceCode;
   @ApiModelProperty("数据类别（0-合闸 1-分闸 ）")
   private Integer dataType;
   @ApiModelProperty("设备编号（3-本体柜三工位 4-隔离柜三工位 ）")
   private Integer deviceNum;

   public Long getId() {
      return this.id;
   }

   public String getDeviceCode() {
      return this.deviceCode;
   }

   public Integer getDataType() {
      return this.dataType;
   }

   public Integer getDeviceNum() {
      return this.deviceNum;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public void setDeviceCode(final String deviceCode) {
      this.deviceCode = deviceCode;
   }

   public void setDataType(final Integer dataType) {
      this.dataType = dataType;
   }

   public void setDeviceNum(final Integer deviceNum) {
      this.deviceNum = deviceNum;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof ThreeStationsReq)) {
         return false;
      }

      ThreeStationsReq other = (ThreeStationsReq)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$id = this.getId();
      Object other$id = other.getId();
      if (this$id == null ? other$id == null : this$id.equals(other$id)) {
         Object this$dataType = this.getDataType();
         Object other$dataType = other.getDataType();
         if (this$dataType == null ? other$dataType == null : this$dataType.equals(other$dataType)) {
            Object this$deviceNum = this.getDeviceNum();
            Object other$deviceNum = other.getDeviceNum();
            if (this$deviceNum == null ? other$deviceNum == null : this$deviceNum.equals(other$deviceNum)) {
               Object this$deviceCode = this.getDeviceCode();
               Object other$deviceCode = other.getDeviceCode();
               return this$deviceCode == null ? other$deviceCode == null : this$deviceCode.equals(other$deviceCode);
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

   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof ThreeStationsReq;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $dataType = this.getDataType();
      result = result * 59 + ($dataType == null ? 43 : $dataType.hashCode());
      Object $deviceNum = this.getDeviceNum();
      result = result * 59 + ($deviceNum == null ? 43 : $deviceNum.hashCode());
      Object $deviceCode = this.getDeviceCode();
      return result * 59 + ($deviceCode == null ? 43 : $deviceCode.hashCode());
   }

   @Override
   public String toString() {
      return "ThreeStationsReq(id="
         + this.getId()
         + ", deviceCode="
         + this.getDeviceCode()
         + ", dataType="
         + this.getDataType()
         + ", deviceNum="
         + this.getDeviceNum()
         + ")";
   }
}
