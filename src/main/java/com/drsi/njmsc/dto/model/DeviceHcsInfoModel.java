package com.drsi.njmsc.dto.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@TableName("device_hcs_info")
@JsonInclude(Include.NON_NULL)
public class DeviceHcsInfoModel implements Serializable {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty("id")
   private Long id;
   @ApiModelProperty("设备编码")
   private String deviceCode;
   @ApiModelProperty("ip")
   private String hcsIp;
   @ApiModelProperty("port")
   private Integer hcsPort;
   @ApiModelProperty("commonAddressOfUpwardDelivery")
   private Integer commonAddressOfUpwardDelivery;

   public Long getId() {
      return this.id;
   }

   public String getDeviceCode() {
      return this.deviceCode;
   }

   public String getHcsIp() {
      return this.hcsIp;
   }

   public Integer getHcsPort() {
      return this.hcsPort;
   }

   public Integer getCommonAddressOfUpwardDelivery() {
      return this.commonAddressOfUpwardDelivery;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public void setDeviceCode(final String deviceCode) {
      this.deviceCode = deviceCode;
   }

   public void setHcsIp(final String hcsIp) {
      this.hcsIp = hcsIp;
   }

   public void setHcsPort(final Integer hcsPort) {
      this.hcsPort = hcsPort;
   }

   public void setCommonAddressOfUpwardDelivery(final Integer commonAddressOfUpwardDelivery) {
      this.commonAddressOfUpwardDelivery = commonAddressOfUpwardDelivery;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof DeviceHcsInfoModel)) {
         return false;
      }

      DeviceHcsInfoModel other = (DeviceHcsInfoModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$id = this.getId();
      Object other$id = other.getId();
      if (this$id == null ? other$id == null : this$id.equals(other$id)) {
         Object this$hcsPort = this.getHcsPort();
         Object other$hcsPort = other.getHcsPort();
         if (this$hcsPort == null ? other$hcsPort == null : this$hcsPort.equals(other$hcsPort)) {
            Object this$commonAddressOfUpwardDelivery = this.getCommonAddressOfUpwardDelivery();
            Object other$commonAddressOfUpwardDelivery = other.getCommonAddressOfUpwardDelivery();
            if (this$commonAddressOfUpwardDelivery == null
               ? other$commonAddressOfUpwardDelivery == null
               : this$commonAddressOfUpwardDelivery.equals(other$commonAddressOfUpwardDelivery)) {
               Object this$deviceCode = this.getDeviceCode();
               Object other$deviceCode = other.getDeviceCode();
               if (this$deviceCode == null ? other$deviceCode == null : this$deviceCode.equals(other$deviceCode)) {
                  Object this$hcsIp = this.getHcsIp();
                  Object other$hcsIp = other.getHcsIp();
                  return this$hcsIp == null ? other$hcsIp == null : this$hcsIp.equals(other$hcsIp);
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
      return other instanceof DeviceHcsInfoModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $hcsPort = this.getHcsPort();
      result = result * 59 + ($hcsPort == null ? 43 : $hcsPort.hashCode());
      Object $commonAddressOfUpwardDelivery = this.getCommonAddressOfUpwardDelivery();
      result = result * 59 + ($commonAddressOfUpwardDelivery == null ? 43 : $commonAddressOfUpwardDelivery.hashCode());
      Object $deviceCode = this.getDeviceCode();
      result = result * 59 + ($deviceCode == null ? 43 : $deviceCode.hashCode());
      Object $hcsIp = this.getHcsIp();
      return result * 59 + ($hcsIp == null ? 43 : $hcsIp.hashCode());
   }

   @Override
   public String toString() {
      return "DeviceHcsInfoModel(id="
         + this.getId()
         + ", deviceCode="
         + this.getDeviceCode()
         + ", hcsIp="
         + this.getHcsIp()
         + ", hcsPort="
         + this.getHcsPort()
         + ", commonAddressOfUpwardDelivery="
         + this.getCommonAddressOfUpwardDelivery()
         + ")";
   }
}
