package com.drsi.njmsc.dto.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@TableName("alarm_record")
@JsonInclude(Include.NON_NULL)
public class AlarmRecordModel implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(type = IdType.AUTO)
   private Long id;
   @ApiModelProperty("设备编码")
   private String deviceCode;
   @ApiModelProperty("设备名称")
   private String deviceName;
   @ApiModelProperty("告警描述")
   private String alarmDesc;
   @ApiModelProperty("是否已读 0-未读 1-已读")
   private Integer readFinal;
   private Date createDate;

   public Long getId() {
      return this.id;
   }

   public String getDeviceCode() {
      return this.deviceCode;
   }

   public String getDeviceName() {
      return this.deviceName;
   }

   public String getAlarmDesc() {
      return this.alarmDesc;
   }

   public Integer getReadFinal() {
      return this.readFinal;
   }

   public Date getCreateDate() {
      return this.createDate;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public void setDeviceCode(final String deviceCode) {
      this.deviceCode = deviceCode;
   }

   public void setDeviceName(final String deviceName) {
      this.deviceName = deviceName;
   }

   public void setAlarmDesc(final String alarmDesc) {
      this.alarmDesc = alarmDesc;
   }

   public void setReadFinal(final Integer readFinal) {
      this.readFinal = readFinal;
   }

   public void setCreateDate(final Date createDate) {
      this.createDate = createDate;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof AlarmRecordModel)) {
         return false;
      }

      AlarmRecordModel other = (AlarmRecordModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$id = this.getId();
      Object other$id = other.getId();
      if (this$id == null ? other$id == null : this$id.equals(other$id)) {
         Object this$readFinal = this.getReadFinal();
         Object other$readFinal = other.getReadFinal();
         if (this$readFinal == null ? other$readFinal == null : this$readFinal.equals(other$readFinal)) {
            Object this$deviceCode = this.getDeviceCode();
            Object other$deviceCode = other.getDeviceCode();
            if (this$deviceCode == null ? other$deviceCode == null : this$deviceCode.equals(other$deviceCode)) {
               Object this$deviceName = this.getDeviceName();
               Object other$deviceName = other.getDeviceName();
               if (this$deviceName == null ? other$deviceName == null : this$deviceName.equals(other$deviceName)) {
                  Object this$alarmDesc = this.getAlarmDesc();
                  Object other$alarmDesc = other.getAlarmDesc();
                  if (this$alarmDesc == null ? other$alarmDesc == null : this$alarmDesc.equals(other$alarmDesc)) {
                     Object this$createDate = this.getCreateDate();
                     Object other$createDate = other.getCreateDate();
                     return this$createDate == null ? other$createDate == null : this$createDate.equals(other$createDate);
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
      return other instanceof AlarmRecordModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $readFinal = this.getReadFinal();
      result = result * 59 + ($readFinal == null ? 43 : $readFinal.hashCode());
      Object $deviceCode = this.getDeviceCode();
      result = result * 59 + ($deviceCode == null ? 43 : $deviceCode.hashCode());
      Object $deviceName = this.getDeviceName();
      result = result * 59 + ($deviceName == null ? 43 : $deviceName.hashCode());
      Object $alarmDesc = this.getAlarmDesc();
      result = result * 59 + ($alarmDesc == null ? 43 : $alarmDesc.hashCode());
      Object $createDate = this.getCreateDate();
      return result * 59 + ($createDate == null ? 43 : $createDate.hashCode());
   }

   @Override
   public String toString() {
      return "AlarmRecordModel(id="
         + this.getId()
         + ", deviceCode="
         + this.getDeviceCode()
         + ", deviceName="
         + this.getDeviceName()
         + ", alarmDesc="
         + this.getAlarmDesc()
         + ", readFinal="
         + this.getReadFinal()
         + ", createDate="
         + this.getCreateDate()
         + ")";
   }
}
