package com.drsi.njmsc.dto.RawDataDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class DisplacementRawDataDto extends RawDataDto {
   private List<DistanceRawDataDto> distanceRawDataList;

   public void setDistanceRawDataList(final List<DistanceRawDataDto> distanceRawDataList) {
      this.distanceRawDataList = distanceRawDataList;
   }

   public List<DistanceRawDataDto> getDistanceRawDataList() {
      return this.distanceRawDataList;
   }
}
