package com.drsi.njmsc.dto.model;

public class BaseTaskInfoModel {
   private String collectCode;
   private String deviceCodes;
   private String sensorCode;
   private String type;
   private String portName;
   private Integer slaveId;
   private Integer baudRate;
   private Integer dataBits;
   private Integer stopBits;
   private Integer parity;
   private String ip;
   private Integer port;
   private Boolean keepAlive;
   private Integer lingerTime;
   private String url;

   public String getCollectCode() {
      return this.collectCode;
   }

   public String getDeviceCodes() {
      return this.deviceCodes;
   }

   public String getSensorCode() {
      return this.sensorCode;
   }

   public String getType() {
      return this.type;
   }

   public String getPortName() {
      return this.portName;
   }

   public Integer getSlaveId() {
      return this.slaveId;
   }

   public Integer getBaudRate() {
      return this.baudRate;
   }

   public Integer getDataBits() {
      return this.dataBits;
   }

   public Integer getStopBits() {
      return this.stopBits;
   }

   public Integer getParity() {
      return this.parity;
   }

   public String getIp() {
      return this.ip;
   }

   public Integer getPort() {
      return this.port;
   }

   public Boolean getKeepAlive() {
      return this.keepAlive;
   }

   public Integer getLingerTime() {
      return this.lingerTime;
   }

   public String getUrl() {
      return this.url;
   }

   public void setCollectCode(final String collectCode) {
      this.collectCode = collectCode;
   }

   public void setDeviceCodes(final String deviceCodes) {
      this.deviceCodes = deviceCodes;
   }

   public void setSensorCode(final String sensorCode) {
      this.sensorCode = sensorCode;
   }

   public void setType(final String type) {
      this.type = type;
   }

   public void setPortName(final String portName) {
      this.portName = portName;
   }

   public void setSlaveId(final Integer slaveId) {
      this.slaveId = slaveId;
   }

   public void setBaudRate(final Integer baudRate) {
      this.baudRate = baudRate;
   }

   public void setDataBits(final Integer dataBits) {
      this.dataBits = dataBits;
   }

   public void setStopBits(final Integer stopBits) {
      this.stopBits = stopBits;
   }

   public void setParity(final Integer parity) {
      this.parity = parity;
   }

   public void setIp(final String ip) {
      this.ip = ip;
   }

   public void setPort(final Integer port) {
      this.port = port;
   }

   public void setKeepAlive(final Boolean keepAlive) {
      this.keepAlive = keepAlive;
   }

   public void setLingerTime(final Integer lingerTime) {
      this.lingerTime = lingerTime;
   }

   public void setUrl(final String url) {
      this.url = url;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof BaseTaskInfoModel)) {
         return false;
      }

      BaseTaskInfoModel other = (BaseTaskInfoModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$slaveId = this.getSlaveId();
      Object other$slaveId = other.getSlaveId();
      if (this$slaveId == null ? other$slaveId == null : this$slaveId.equals(other$slaveId)) {
         Object this$baudRate = this.getBaudRate();
         Object other$baudRate = other.getBaudRate();
         if (this$baudRate == null ? other$baudRate == null : this$baudRate.equals(other$baudRate)) {
            Object this$dataBits = this.getDataBits();
            Object other$dataBits = other.getDataBits();
            if (this$dataBits == null ? other$dataBits == null : this$dataBits.equals(other$dataBits)) {
               Object this$stopBits = this.getStopBits();
               Object other$stopBits = other.getStopBits();
               if (this$stopBits == null ? other$stopBits == null : this$stopBits.equals(other$stopBits)) {
                  Object this$parity = this.getParity();
                  Object other$parity = other.getParity();
                  if (this$parity == null ? other$parity == null : this$parity.equals(other$parity)) {
                     Object this$port = this.getPort();
                     Object other$port = other.getPort();
                     if (this$port == null ? other$port == null : this$port.equals(other$port)) {
                        Object this$keepAlive = this.getKeepAlive();
                        Object other$keepAlive = other.getKeepAlive();
                        if (this$keepAlive == null ? other$keepAlive == null : this$keepAlive.equals(other$keepAlive)) {
                           Object this$lingerTime = this.getLingerTime();
                           Object other$lingerTime = other.getLingerTime();
                           if (this$lingerTime == null ? other$lingerTime == null : this$lingerTime.equals(other$lingerTime)) {
                              Object this$collectCode = this.getCollectCode();
                              Object other$collectCode = other.getCollectCode();
                              if (this$collectCode == null ? other$collectCode == null : this$collectCode.equals(other$collectCode)) {
                                 Object this$deviceCodes = this.getDeviceCodes();
                                 Object other$deviceCodes = other.getDeviceCodes();
                                 if (this$deviceCodes == null ? other$deviceCodes == null : this$deviceCodes.equals(other$deviceCodes)) {
                                    Object this$sensorCode = this.getSensorCode();
                                    Object other$sensorCode = other.getSensorCode();
                                    if (this$sensorCode == null ? other$sensorCode == null : this$sensorCode.equals(other$sensorCode)) {
                                       Object this$type = this.getType();
                                       Object other$type = other.getType();
                                       if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                          Object this$portName = this.getPortName();
                                          Object other$portName = other.getPortName();
                                          if (this$portName == null ? other$portName == null : this$portName.equals(other$portName)) {
                                             Object this$ip = this.getIp();
                                             Object other$ip = other.getIp();
                                             if (this$ip == null ? other$ip == null : this$ip.equals(other$ip)) {
                                                Object this$url = this.getUrl();
                                                Object other$url = other.getUrl();
                                                return this$url == null ? other$url == null : this$url.equals(other$url);
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
      return other instanceof BaseTaskInfoModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $slaveId = this.getSlaveId();
      result = result * 59 + ($slaveId == null ? 43 : $slaveId.hashCode());
      Object $baudRate = this.getBaudRate();
      result = result * 59 + ($baudRate == null ? 43 : $baudRate.hashCode());
      Object $dataBits = this.getDataBits();
      result = result * 59 + ($dataBits == null ? 43 : $dataBits.hashCode());
      Object $stopBits = this.getStopBits();
      result = result * 59 + ($stopBits == null ? 43 : $stopBits.hashCode());
      Object $parity = this.getParity();
      result = result * 59 + ($parity == null ? 43 : $parity.hashCode());
      Object $port = this.getPort();
      result = result * 59 + ($port == null ? 43 : $port.hashCode());
      Object $keepAlive = this.getKeepAlive();
      result = result * 59 + ($keepAlive == null ? 43 : $keepAlive.hashCode());
      Object $lingerTime = this.getLingerTime();
      result = result * 59 + ($lingerTime == null ? 43 : $lingerTime.hashCode());
      Object $collectCode = this.getCollectCode();
      result = result * 59 + ($collectCode == null ? 43 : $collectCode.hashCode());
      Object $deviceCodes = this.getDeviceCodes();
      result = result * 59 + ($deviceCodes == null ? 43 : $deviceCodes.hashCode());
      Object $sensorCode = this.getSensorCode();
      result = result * 59 + ($sensorCode == null ? 43 : $sensorCode.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $portName = this.getPortName();
      result = result * 59 + ($portName == null ? 43 : $portName.hashCode());
      Object $ip = this.getIp();
      result = result * 59 + ($ip == null ? 43 : $ip.hashCode());
      Object $url = this.getUrl();
      return result * 59 + ($url == null ? 43 : $url.hashCode());
   }

   @Override
   public String toString() {
      return "BaseTaskInfoModel(collectCode="
         + this.getCollectCode()
         + ", deviceCodes="
         + this.getDeviceCodes()
         + ", sensorCode="
         + this.getSensorCode()
         + ", type="
         + this.getType()
         + ", portName="
         + this.getPortName()
         + ", slaveId="
         + this.getSlaveId()
         + ", baudRate="
         + this.getBaudRate()
         + ", dataBits="
         + this.getDataBits()
         + ", stopBits="
         + this.getStopBits()
         + ", parity="
         + this.getParity()
         + ", ip="
         + this.getIp()
         + ", port="
         + this.getPort()
         + ", keepAlive="
         + this.getKeepAlive()
         + ", lingerTime="
         + this.getLingerTime()
         + ", url="
         + this.getUrl()
         + ")";
   }
}
