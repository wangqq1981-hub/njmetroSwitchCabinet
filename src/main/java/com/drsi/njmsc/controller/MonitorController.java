package com.drsi.njmsc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.drsi.njmsc.annotation.CheckToken;
import com.drsi.njmsc.dto.dto.DeviceRawIecTelemtryDto;
import com.drsi.njmsc.dto.dto.MonitorIndexDto;
import com.drsi.njmsc.dto.dto.MonitorSwitchIndexDto;
import com.drsi.njmsc.dto.model.CircuitBreakerModel;
import com.drsi.njmsc.dto.model.EnergyStorageMotorModel;
import com.drsi.njmsc.dto.model.RawIecRemoteSignalingModel;
import com.drsi.njmsc.dto.model.RawIecTelemetryModel;
import com.drsi.njmsc.dto.model.ThreeStationsModel;
import com.drsi.njmsc.mapper.CircuitBreakerMapper;
import com.drsi.njmsc.mapper.EnergyStorageMotorMapper;
import com.drsi.njmsc.mapper.RawIecRemoteSignalingMapper;
import com.drsi.njmsc.mapper.RawIecTelemetryMapper;
import com.drsi.njmsc.mapper.ThreeStationsMapper;
import com.drsi.njmsc.scheduled.Iec104Scheduled;
import com.zhixin.common.core.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Date;
import java.util.Objects;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "监测页面api")
@RequestMapping("/monitor")
@RestController
public class MonitorController {
   private static final Logger log = LoggerFactory.getLogger(MonitorController.class);
   @Resource
   private RawIecTelemetryMapper rawIecTelemetryMapper;
   @Resource
   private RawIecRemoteSignalingMapper rawIecRemoteSignalingMapper;
   @Resource
   private CircuitBreakerMapper circuitBreakerMapper;
   @Resource
   private ThreeStationsMapper threeStationsMapper;
   @Resource
   private EnergyStorageMotorMapper energyStorageMotorMapper;

   @ApiOperation("获取监控页面数据")
   @GetMapping("/index/telemetryLast")
   public Result getMonitorTelemetryLast(@RequestParam String deviceCode) {
      MonitorIndexDto monitorIndexDto = new MonitorIndexDto();
      Date date = new Date();
      LambdaQueryWrapper<RawIecTelemetryModel> wrapper = Wrappers.lambdaQuery(RawIecTelemetryModel.class);
      wrapper.eq(RawIecTelemetryModel::getDeviceCode, deviceCode).orderByDesc(RawIecTelemetryModel::getCreateDate).last("limit 1");
      RawIecTelemetryModel rawIecTelemetryModel = this.rawIecTelemetryMapper.selectOne(wrapper);
      if (!Objects.isNull(rawIecTelemetryModel)) {
         BeanUtils.copyProperties(rawIecTelemetryModel, monitorIndexDto);
      } else {
         monitorIndexDto.setDeviceCode(deviceCode);
      }
      LambdaQueryWrapper<CircuitBreakerModel> closeCircuitBreakerModelLambdaQueryWrapper = Wrappers.lambdaQuery(CircuitBreakerModel.class);
      closeCircuitBreakerModelLambdaQueryWrapper.eq(CircuitBreakerModel::getDeviceCode, deviceCode).eq(CircuitBreakerModel::getDataType, 0);
      Long cbCloseLong = this.circuitBreakerMapper.selectCount(closeCircuitBreakerModelLambdaQueryWrapper);
      LambdaQueryWrapper<CircuitBreakerModel> openCircuitBreakerModelLambdaQueryWrapper = Wrappers.lambdaQuery(CircuitBreakerModel.class);
      openCircuitBreakerModelLambdaQueryWrapper.eq(CircuitBreakerModel::getDeviceCode, deviceCode).eq(CircuitBreakerModel::getDataType, 1);
      Long openLong = this.circuitBreakerMapper.selectCount(openCircuitBreakerModelLambdaQueryWrapper);
      monitorIndexDto.setCbCloseSwitch(cbCloseLong);
      monitorIndexDto.setCbOpenSwitch(openLong);
      int remainingNum = (int)Math.max(0L, 20000L - cbCloseLong - openLong);
      monitorIndexDto.setRemainingNum(remainingNum);
      LambdaQueryWrapper<ThreeStationsModel> tsQuarantineSwitchWrapper = Wrappers.lambdaQuery(ThreeStationsModel.class);
      tsQuarantineSwitchWrapper.eq(ThreeStationsModel::getDeviceCode, deviceCode)
         .eq(ThreeStationsModel::getDataType, 3)
         .and(
            w -> w.or(we -> we.eq(ThreeStationsModel::getPosition, 0).eq(ThreeStationsModel::getOriginalPosition, 1))
               .or(we -> we.eq(ThreeStationsModel::getPosition, 1).eq(ThreeStationsModel::getOriginalPosition, 0))
         );
      Long tsQuarantineSwitch = this.threeStationsMapper.selectCount(tsQuarantineSwitchWrapper);
      monitorIndexDto.setTsQuarantineSwitch(tsQuarantineSwitch);
      LambdaQueryWrapper<ThreeStationsModel> tsGroundingSwitchWrapper = Wrappers.lambdaQuery(ThreeStationsModel.class);
      tsGroundingSwitchWrapper.eq(ThreeStationsModel::getDeviceCode, deviceCode)
         .eq(ThreeStationsModel::getDataType, 3)
         .and(
            w -> w.or(we -> we.eq(ThreeStationsModel::getPosition, 1).eq(ThreeStationsModel::getOriginalPosition, 2))
               .or(we -> we.eq(ThreeStationsModel::getPosition, 2).eq(ThreeStationsModel::getOriginalPosition, 1))
         );
      Long tsGroundingSwitch = this.threeStationsMapper.selectCount(tsGroundingSwitchWrapper);
      monitorIndexDto.setTsGroundingSwitch(tsGroundingSwitch);
      monitorIndexDto.setCreateDate(date.getTime());
      LambdaQueryWrapper<EnergyStorageMotorModel> wrapperEnergyStorage = Wrappers.lambdaQuery(EnergyStorageMotorModel.class);
      wrapperEnergyStorage.eq(EnergyStorageMotorModel::getDeviceCode, deviceCode)
         .orderByDesc(EnergyStorageMotorModel::getCreateDate)
         .last("limit 1");
      EnergyStorageMotorModel energyStorageMotorModel = this.energyStorageMotorMapper.selectOne(wrapperEnergyStorage);
      if (!Objects.isNull(energyStorageMotorModel)) {
         monitorIndexDto.setStartingCurrent(energyStorageMotorModel.getStartingCurrent());
         monitorIndexDto.setIdleElectricCurrent(energyStorageMotorModel.getIdleElectricCurrent());
         monitorIndexDto.setOutputCurrent(energyStorageMotorModel.getOutputCurrent());
         monitorIndexDto.setStartingTime(energyStorageMotorModel.getStartingTime());
         monitorIndexDto.setIdleElectricTime(energyStorageMotorModel.getIdleElectricTime());
         monitorIndexDto.setOutputTime(energyStorageMotorModel.getOutputTime());
         monitorIndexDto.setActionTime(energyStorageMotorModel.getActionTime());
      }

      return Result.successWithData(monitorIndexDto);
   }

   @CheckToken
   @ApiOperation("获取监控页面信号数据")
   @GetMapping("/index/remoteSignalingLast")
   public Result getMonitorRemoteSignalingLast(@RequestParam String deviceCode) {
      MonitorSwitchIndexDto monitorSwitchIndexDto = new MonitorSwitchIndexDto();
      RawIecRemoteSignalingModel rawIecRemoteSignalingModel = null;

      if (Iec104Scheduled.rawIecTelemtryDtos != null) {
         for (Entry<String, DeviceRawIecTelemtryDto> entry : Iec104Scheduled.rawIecTelemtryDtos.entrySet()) {
            DeviceRawIecTelemtryDto dto = entry.getValue();
            if (dto != null && deviceCode.equals(dto.getDeviceCode())) {
               if (dto.getRawIecTelemetryModel() != null
                  && dto.getRawIecRemoteSignalingModel() != null
                  && Objects.nonNull(dto.getRawIecTelemetryModel().getOpeningCoilCurrent())
                  && Objects.nonNull(dto.getRawIecRemoteSignalingModel().getSwitchDivision())
                  && Objects.nonNull(dto.getRawIecRemoteSignalingModel().getThreePositionKnifeClosingPosition())
                  && Objects.nonNull(dto.getRawIecRemoteSignalingModel().getThreeStationKnifeClosingPosition())
                  && Objects.nonNull(dto.getSubsectionIsolationThQuarantineClosing())
                  && Objects.nonNull(dto.getSubsectionIsolationThQuarantineMiddle())
                  && Objects.nonNull(dto.getSubsectionIsolationThQuarantineOpening())) {
                  rawIecRemoteSignalingModel = dto.getRawIecRemoteSignalingModel();
               }
               break;
            }
         }
      }

      if (Objects.isNull(rawIecRemoteSignalingModel)) {
         LambdaQueryWrapper<RawIecRemoteSignalingModel> rawIecRemoteSignalingModelWrapper = Wrappers.lambdaQuery(RawIecRemoteSignalingModel.class);
         rawIecRemoteSignalingModelWrapper.eq(RawIecRemoteSignalingModel::getDeviceCode, deviceCode);
         rawIecRemoteSignalingModelWrapper.orderByDesc(RawIecRemoteSignalingModel::getCreateDate);
         rawIecRemoteSignalingModelWrapper.last(" limit 1");
         rawIecRemoteSignalingModel = (RawIecRemoteSignalingModel)this.rawIecRemoteSignalingMapper.selectOne(rawIecRemoteSignalingModelWrapper);
      }

      if (Objects.isNull(rawIecRemoteSignalingModel)) {
         monitorSwitchIndexDto.setDeviceCode(deviceCode);
         return Result.successWithData(monitorSwitchIndexDto);
      }

      monitorSwitchIndexDto.setCircuitState(rawIecRemoteSignalingModel.getSwitchDivision());
      if (Integer.valueOf(1).equals(rawIecRemoteSignalingModel.getThreePositionKnifeClosingPosition())) {
         monitorSwitchIndexDto.setTsPosition(0);
      } else if (Integer.valueOf(1).equals(rawIecRemoteSignalingModel.getThreeStationKnifeClosingPosition())) {
         monitorSwitchIndexDto.setTsPosition(2);
      } else {
         monitorSwitchIndexDto.setTsPosition(1);
      }

      if (Integer.valueOf(1).equals(rawIecRemoteSignalingModel.getSubsectionIsolationThQuarantineClosing())) {
         monitorSwitchIndexDto.setTsIsolationTwoPosition(0);
      } else if (Integer.valueOf(1).equals(rawIecRemoteSignalingModel.getSubsectionIsolationThQuarantineMiddle())) {
         monitorSwitchIndexDto.setTsIsolationTwoPosition(1);
      } else {
         monitorSwitchIndexDto.setTsIsolationTwoPosition(2);
      }

      monitorSwitchIndexDto.setDeviceCode(deviceCode);
      return Result.successWithData(monitorSwitchIndexDto);
   }
}
