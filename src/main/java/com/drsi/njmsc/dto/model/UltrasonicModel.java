package com.drsi.njmsc.dto.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@TableName("ultrasonic")
@JsonInclude(Include.NON_NULL)
public class UltrasonicModel implements Serializable {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty("id")
   private Long id;
   @ApiModelProperty("local_dhg")
   private Float localDhg;
   @ApiModelProperty("prpd_id")
   private Long prpdId;
   @ApiModelProperty("state")
   private Integer state;
   @ApiModelProperty("ch_num")
   private Integer chNum;
   @ApiModelProperty("data_name")
   private String dataName;
   @ApiModelProperty("device_id")
   private String deviceId;
   @ApiModelProperty("data_time")
   private Date dataTime;

   public Long getId() {
      return this.id;
   }

   public Float getLocalDhg() {
      return this.localDhg;
   }

   public Long getPrpdId() {
      return this.prpdId;
   }

   public Integer getState() {
      return this.state;
   }

   public Integer getChNum() {
      return this.chNum;
   }

   public String getDataName() {
      return this.dataName;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public Date getDataTime() {
      return this.dataTime;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public void setLocalDhg(final Float localDhg) {
      this.localDhg = localDhg;
   }

   public void setPrpdId(final Long prpdId) {
      this.prpdId = prpdId;
   }

   public void setState(final Integer state) {
      this.state = state;
   }

   public void setChNum(final Integer chNum) {
      this.chNum = chNum;
   }

   public void setDataName(final String dataName) {
      this.dataName = dataName;
   }

   public void setDeviceId(final String deviceId) {
      this.deviceId = deviceId;
   }

   public void setDataTime(final Date dataTime) {
      this.dataTime = dataTime;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof UltrasonicModel)) {
         return false;
      }

      UltrasonicModel other = (UltrasonicModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$id = this.getId();
      Object other$id = other.getId();
      if (this$id == null ? other$id == null : this$id.equals(other$id)) {
         Object this$localDhg = this.getLocalDhg();
         Object other$localDhg = other.getLocalDhg();
         if (this$localDhg == null ? other$localDhg == null : this$localDhg.equals(other$localDhg)) {
            Object this$prpdId = this.getPrpdId();
            Object other$prpdId = other.getPrpdId();
            if (this$prpdId == null ? other$prpdId == null : this$prpdId.equals(other$prpdId)) {
               Object this$state = this.getState();
               Object other$state = other.getState();
               if (this$state == null ? other$state == null : this$state.equals(other$state)) {
                  Object this$chNum = this.getChNum();
                  Object other$chNum = other.getChNum();
                  if (this$chNum == null ? other$chNum == null : this$chNum.equals(other$chNum)) {
                     Object this$dataName = this.getDataName();
                     Object other$dataName = other.getDataName();
                     if (this$dataName == null ? other$dataName == null : this$dataName.equals(other$dataName)) {
                        Object this$deviceId = this.getDeviceId();
                        Object other$deviceId = other.getDeviceId();
                        if (this$deviceId == null ? other$deviceId == null : this$deviceId.equals(other$deviceId)) {
                           Object this$dataTime = this.getDataTime();
                           Object other$dataTime = other.getDataTime();
                           return this$dataTime == null ? other$dataTime == null : this$dataTime.equals(other$dataTime);
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
      return other instanceof UltrasonicModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $localDhg = this.getLocalDhg();
      result = result * 59 + ($localDhg == null ? 43 : $localDhg.hashCode());
      Object $prpdId = this.getPrpdId();
      result = result * 59 + ($prpdId == null ? 43 : $prpdId.hashCode());
      Object $state = this.getState();
      result = result * 59 + ($state == null ? 43 : $state.hashCode());
      Object $chNum = this.getChNum();
      result = result * 59 + ($chNum == null ? 43 : $chNum.hashCode());
      Object $dataName = this.getDataName();
      result = result * 59 + ($dataName == null ? 43 : $dataName.hashCode());
      Object $deviceId = this.getDeviceId();
      result = result * 59 + ($deviceId == null ? 43 : $deviceId.hashCode());
      Object $dataTime = this.getDataTime();
      return result * 59 + ($dataTime == null ? 43 : $dataTime.hashCode());
   }

   @Override
   public String toString() {
      return "UltrasonicModel(id="
         + this.getId()
         + ", localDhg="
         + this.getLocalDhg()
         + ", prpdId="
         + this.getPrpdId()
         + ", state="
         + this.getState()
         + ", chNum="
         + this.getChNum()
         + ", dataName="
         + this.getDataName()
         + ", deviceId="
         + this.getDeviceId()
         + ", dataTime="
         + this.getDataTime()
         + ")";
   }
}
