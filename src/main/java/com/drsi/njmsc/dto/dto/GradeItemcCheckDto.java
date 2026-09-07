package com.drsi.njmsc.dto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(value = "GradeItemcCheckDto对象", description = "在线勾选评估导则参数对象")
public class GradeItemcCheckDto {
   @ApiModelProperty("业务系统编码")
   private String appCode;
   @ApiModelProperty("设备编码")
   private String deviceCode;
   @ApiModelProperty("勾选导则id列表")
   private List<Integer> ids;

   public String getAppCode() {
      return this.appCode;
   }

   public String getDeviceCode() {
      return this.deviceCode;
   }

   public List<Integer> getIds() {
      return this.ids;
   }

   public void setAppCode(final String appCode) {
      this.appCode = appCode;
   }

   public void setDeviceCode(final String deviceCode) {
      this.deviceCode = deviceCode;
   }

   public void setIds(final List<Integer> ids) {
      this.ids = ids;
   }
}
