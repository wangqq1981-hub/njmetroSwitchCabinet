package com.drsi.njmsc.dto.model;

import java.time.LocalDateTime;
import java.util.List;

public class ComtradeCfgModel {
   private String stationName;
   private String recDevId;
   private String revYear;
   private Integer channelTotalNum;
   private Integer analogChannelTotalNum;
   private Integer digitalChannelTotalNum;
   private List<AnalogChannelInfoModel> analogChannelInfos;
   private List<DigitalChannelInfoModel> digitalChannelInfos;
   private Integer channelFrequency;
   private Integer nartes;
   private Integer samp;
   private Integer endSamp;
   private LocalDateTime tfdDate;
   private LocalDateTime tpDate;
   private String ft;
   private Integer timemult;

   public String getStationName() {
      return this.stationName;
   }

   public String getRecDevId() {
      return this.recDevId;
   }

   public String getRevYear() {
      return this.revYear;
   }

   public Integer getChannelTotalNum() {
      return this.channelTotalNum;
   }

   public Integer getAnalogChannelTotalNum() {
      return this.analogChannelTotalNum;
   }

   public Integer getDigitalChannelTotalNum() {
      return this.digitalChannelTotalNum;
   }

   public List<AnalogChannelInfoModel> getAnalogChannelInfos() {
      return this.analogChannelInfos;
   }

   public List<DigitalChannelInfoModel> getDigitalChannelInfos() {
      return this.digitalChannelInfos;
   }

   public Integer getChannelFrequency() {
      return this.channelFrequency;
   }

   public Integer getNartes() {
      return this.nartes;
   }

   public Integer getSamp() {
      return this.samp;
   }

   public Integer getEndSamp() {
      return this.endSamp;
   }

   public LocalDateTime getTfdDate() {
      return this.tfdDate;
   }

   public LocalDateTime getTpDate() {
      return this.tpDate;
   }

   public String getFt() {
      return this.ft;
   }

   public Integer getTimemult() {
      return this.timemult;
   }

   public void setStationName(final String stationName) {
      this.stationName = stationName;
   }

   public void setRecDevId(final String recDevId) {
      this.recDevId = recDevId;
   }

   public void setRevYear(final String revYear) {
      this.revYear = revYear;
   }

   public void setChannelTotalNum(final Integer channelTotalNum) {
      this.channelTotalNum = channelTotalNum;
   }

   public void setAnalogChannelTotalNum(final Integer analogChannelTotalNum) {
      this.analogChannelTotalNum = analogChannelTotalNum;
   }

   public void setDigitalChannelTotalNum(final Integer digitalChannelTotalNum) {
      this.digitalChannelTotalNum = digitalChannelTotalNum;
   }

   public void setAnalogChannelInfos(final List<AnalogChannelInfoModel> analogChannelInfos) {
      this.analogChannelInfos = analogChannelInfos;
   }

   public void setDigitalChannelInfos(final List<DigitalChannelInfoModel> digitalChannelInfos) {
      this.digitalChannelInfos = digitalChannelInfos;
   }

   public void setChannelFrequency(final Integer channelFrequency) {
      this.channelFrequency = channelFrequency;
   }

   public void setNartes(final Integer nartes) {
      this.nartes = nartes;
   }

   public void setSamp(final Integer samp) {
      this.samp = samp;
   }

   public void setEndSamp(final Integer endSamp) {
      this.endSamp = endSamp;
   }

   public void setTfdDate(final LocalDateTime tfdDate) {
      this.tfdDate = tfdDate;
   }

   public void setTpDate(final LocalDateTime tpDate) {
      this.tpDate = tpDate;
   }

   public void setFt(final String ft) {
      this.ft = ft;
   }

   public void setTimemult(final Integer timemult) {
      this.timemult = timemult;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      }

      if (!(o instanceof ComtradeCfgModel)) {
         return false;
      }

      ComtradeCfgModel other = (ComtradeCfgModel)o;
      if (!other.canEqual(this)) {
         return false;
      }

      Object this$channelTotalNum = this.getChannelTotalNum();
      Object other$channelTotalNum = other.getChannelTotalNum();
      if (this$channelTotalNum == null ? other$channelTotalNum == null : this$channelTotalNum.equals(other$channelTotalNum)) {
         Object this$analogChannelTotalNum = this.getAnalogChannelTotalNum();
         Object other$analogChannelTotalNum = other.getAnalogChannelTotalNum();
         if (this$analogChannelTotalNum == null ? other$analogChannelTotalNum == null : this$analogChannelTotalNum.equals(other$analogChannelTotalNum)) {
            Object this$digitalChannelTotalNum = this.getDigitalChannelTotalNum();
            Object other$digitalChannelTotalNum = other.getDigitalChannelTotalNum();
            if (this$digitalChannelTotalNum == null ? other$digitalChannelTotalNum == null : this$digitalChannelTotalNum.equals(other$digitalChannelTotalNum)) {
               Object this$channelFrequency = this.getChannelFrequency();
               Object other$channelFrequency = other.getChannelFrequency();
               if (this$channelFrequency == null ? other$channelFrequency == null : this$channelFrequency.equals(other$channelFrequency)) {
                  Object this$nartes = this.getNartes();
                  Object other$nartes = other.getNartes();
                  if (this$nartes == null ? other$nartes == null : this$nartes.equals(other$nartes)) {
                     Object this$samp = this.getSamp();
                     Object other$samp = other.getSamp();
                     if (this$samp == null ? other$samp == null : this$samp.equals(other$samp)) {
                        Object this$endSamp = this.getEndSamp();
                        Object other$endSamp = other.getEndSamp();
                        if (this$endSamp == null ? other$endSamp == null : this$endSamp.equals(other$endSamp)) {
                           Object this$timemult = this.getTimemult();
                           Object other$timemult = other.getTimemult();
                           if (this$timemult == null ? other$timemult == null : this$timemult.equals(other$timemult)) {
                              Object this$stationName = this.getStationName();
                              Object other$stationName = other.getStationName();
                              if (this$stationName == null ? other$stationName == null : this$stationName.equals(other$stationName)) {
                                 Object this$recDevId = this.getRecDevId();
                                 Object other$recDevId = other.getRecDevId();
                                 if (this$recDevId == null ? other$recDevId == null : this$recDevId.equals(other$recDevId)) {
                                    Object this$revYear = this.getRevYear();
                                    Object other$revYear = other.getRevYear();
                                    if (this$revYear == null ? other$revYear == null : this$revYear.equals(other$revYear)) {
                                       Object this$analogChannelInfos = this.getAnalogChannelInfos();
                                       Object other$analogChannelInfos = other.getAnalogChannelInfos();
                                       if (this$analogChannelInfos == null
                                          ? other$analogChannelInfos == null
                                          : this$analogChannelInfos.equals(other$analogChannelInfos)) {
                                          Object this$digitalChannelInfos = this.getDigitalChannelInfos();
                                          Object other$digitalChannelInfos = other.getDigitalChannelInfos();
                                          if (this$digitalChannelInfos == null
                                             ? other$digitalChannelInfos == null
                                             : this$digitalChannelInfos.equals(other$digitalChannelInfos)) {
                                             Object this$tfdDate = this.getTfdDate();
                                             Object other$tfdDate = other.getTfdDate();
                                             if (this$tfdDate == null ? other$tfdDate == null : this$tfdDate.equals(other$tfdDate)) {
                                                Object this$tpDate = this.getTpDate();
                                                Object other$tpDate = other.getTpDate();
                                                if (this$tpDate == null ? other$tpDate == null : this$tpDate.equals(other$tpDate)) {
                                                   Object this$ft = this.getFt();
                                                   Object other$ft = other.getFt();
                                                   return this$ft == null ? other$ft == null : this$ft.equals(other$ft);
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
      } else {
         return false;
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof ComtradeCfgModel;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $channelTotalNum = this.getChannelTotalNum();
      result = result * 59 + ($channelTotalNum == null ? 43 : $channelTotalNum.hashCode());
      Object $analogChannelTotalNum = this.getAnalogChannelTotalNum();
      result = result * 59 + ($analogChannelTotalNum == null ? 43 : $analogChannelTotalNum.hashCode());
      Object $digitalChannelTotalNum = this.getDigitalChannelTotalNum();
      result = result * 59 + ($digitalChannelTotalNum == null ? 43 : $digitalChannelTotalNum.hashCode());
      Object $channelFrequency = this.getChannelFrequency();
      result = result * 59 + ($channelFrequency == null ? 43 : $channelFrequency.hashCode());
      Object $nartes = this.getNartes();
      result = result * 59 + ($nartes == null ? 43 : $nartes.hashCode());
      Object $samp = this.getSamp();
      result = result * 59 + ($samp == null ? 43 : $samp.hashCode());
      Object $endSamp = this.getEndSamp();
      result = result * 59 + ($endSamp == null ? 43 : $endSamp.hashCode());
      Object $timemult = this.getTimemult();
      result = result * 59 + ($timemult == null ? 43 : $timemult.hashCode());
      Object $stationName = this.getStationName();
      result = result * 59 + ($stationName == null ? 43 : $stationName.hashCode());
      Object $recDevId = this.getRecDevId();
      result = result * 59 + ($recDevId == null ? 43 : $recDevId.hashCode());
      Object $revYear = this.getRevYear();
      result = result * 59 + ($revYear == null ? 43 : $revYear.hashCode());
      Object $analogChannelInfos = this.getAnalogChannelInfos();
      result = result * 59 + ($analogChannelInfos == null ? 43 : $analogChannelInfos.hashCode());
      Object $digitalChannelInfos = this.getDigitalChannelInfos();
      result = result * 59 + ($digitalChannelInfos == null ? 43 : $digitalChannelInfos.hashCode());
      Object $tfdDate = this.getTfdDate();
      result = result * 59 + ($tfdDate == null ? 43 : $tfdDate.hashCode());
      Object $tpDate = this.getTpDate();
      result = result * 59 + ($tpDate == null ? 43 : $tpDate.hashCode());
      Object $ft = this.getFt();
      return result * 59 + ($ft == null ? 43 : $ft.hashCode());
   }

   @Override
   public String toString() {
      return "ComtradeCfgModel(stationName="
         + this.getStationName()
         + ", recDevId="
         + this.getRecDevId()
         + ", revYear="
         + this.getRevYear()
         + ", channelTotalNum="
         + this.getChannelTotalNum()
         + ", analogChannelTotalNum="
         + this.getAnalogChannelTotalNum()
         + ", digitalChannelTotalNum="
         + this.getDigitalChannelTotalNum()
         + ", analogChannelInfos="
         + this.getAnalogChannelInfos()
         + ", digitalChannelInfos="
         + this.getDigitalChannelInfos()
         + ", channelFrequency="
         + this.getChannelFrequency()
         + ", nartes="
         + this.getNartes()
         + ", samp="
         + this.getSamp()
         + ", endSamp="
         + this.getEndSamp()
         + ", tfdDate="
         + this.getTfdDate()
         + ", tpDate="
         + this.getTpDate()
         + ", ft="
         + this.getFt()
         + ", timemult="
         + this.getTimemult()
         + ")";
   }
}
