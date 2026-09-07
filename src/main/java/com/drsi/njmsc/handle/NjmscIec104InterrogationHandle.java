package com.drsi.njmsc.handle;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.drsi.njmsc.dto.dto.DeviceRawIecTelemtryDto;
import com.drsi.njmsc.dto.model.RawIecRemoteSignalingModel;
import com.drsi.njmsc.dto.model.RawIecTelemetryModel;
import com.drsi.njmsc.mapper.RawIecRemoteSignalingMapper;
import com.drsi.njmsc.mapper.RawIecTelemetryMapper;
import com.dsri.iec104.ies.SunStation;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NjmscIec104InterrogationHandle {
   private static final Logger log = LoggerFactory.getLogger(NjmscIec104InterrogationHandle.class);
   @Resource
   private RawIecTelemetryMapper rawIecTelemetryMapper;
   @Resource
   private RawIecRemoteSignalingMapper rawIecRemoteSignalingMapper;

   public void interrogationHandle(SunStation sunStation) {
   }

   public void interrogationHandle(DeviceRawIecTelemtryDto deviceRawIecTelemtryDto) {
   }

   public void allInterrogationHandle(Map<String, DeviceRawIecTelemtryDto> rawIecTelemtryDtos) {
      Date date = new Date();

      for (Entry<String, DeviceRawIecTelemtryDto> entry : rawIecTelemtryDtos.entrySet()) {
         String deviceCode = entry.getKey();
         LambdaQueryWrapper<RawIecRemoteSignalingModel> rawIecRemoteSignalingModelLambdaQueryWrapper = Wrappers.lambdaQuery(RawIecRemoteSignalingModel.class);
         rawIecRemoteSignalingModelLambdaQueryWrapper.eq(RawIecRemoteSignalingModel::getDeviceCode, deviceCode);
         RawIecRemoteSignalingModel oldRawIecRemoteSignalingModel = (RawIecRemoteSignalingModel)this.rawIecRemoteSignalingMapper
            .selectOne(rawIecRemoteSignalingModelLambdaQueryWrapper);
         if (Objects.isNull(oldRawIecRemoteSignalingModel)) {
            entry.getValue().getRawIecRemoteSignalingModel().setCreateDate(date);
            this.rawIecRemoteSignalingMapper.insert(entry.getValue().getRawIecRemoteSignalingModel());
         } else {
            entry.getValue().getRawIecRemoteSignalingModel().setCreateDate(date);
            RawIecRemoteSignalingModel cacheRawIecRemoteSignalingModel = entry.getValue().getRawIecRemoteSignalingModel();
            cacheRawIecRemoteSignalingModel.setId(oldRawIecRemoteSignalingModel.getId());
            this.rawIecRemoteSignalingMapper.updateById(cacheRawIecRemoteSignalingModel);
         }

         LambdaQueryWrapper<RawIecTelemetryModel> rawIecTelemetryModelLambdaQueryWrapper = Wrappers.lambdaQuery(RawIecTelemetryModel.class);
         rawIecTelemetryModelLambdaQueryWrapper.eq(RawIecTelemetryModel::getDeviceCode, deviceCode);
         RawIecTelemetryModel oldRawIecTelemetryModel = (RawIecTelemetryModel)this.rawIecTelemetryMapper.selectOne(rawIecTelemetryModelLambdaQueryWrapper);
         if (Objects.isNull(oldRawIecTelemetryModel)) {
            entry.getValue().getRawIecTelemetryModel().setCreateDate(date);
            this.rawIecTelemetryMapper.insert(entry.getValue().getRawIecTelemetryModel());
         } else {
            entry.getValue().getRawIecTelemetryModel().setCreateDate(date);
            RawIecTelemetryModel cacheRawIecTelemetryModel = entry.getValue().getRawIecTelemetryModel();
            cacheRawIecTelemetryModel.setId(oldRawIecTelemetryModel.getId());
            this.rawIecTelemetryMapper.updateById(cacheRawIecTelemetryModel);
         }
      }
   }
}
