package com.drsi.njmsc.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.JSONReader.Feature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drsi.njmsc.constant.DeviceCollectConstants;
import com.drsi.njmsc.dto.dto.CircuitBreakerInfoDto;
import com.drsi.njmsc.dto.model.CircuitBreakerModel;
import com.drsi.njmsc.dto.query.CircuitBreakerReq;
import com.drsi.njmsc.mapper.CircuitBreakerMapper;
import com.drsi.njmsc.runner.InitApplicationRunner;
import com.drsi.njmsc.service.CircuitBreakerService;
import com.drsi.njmsc.util.RemainingLifeUtil;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CircuitBreakerServiceImpl extends ServiceImpl<CircuitBreakerMapper, CircuitBreakerModel> implements CircuitBreakerService {
   @Resource
   private RedisTemplate<String, Object> redisTemplate;
   @Resource
   private CircuitBreakerMapper circuitBreakerMapper;

   @Override
   public CircuitBreakerModel getCircuitBreakerModel(CircuitBreakerReq circuitBreakerReq) {
      LambdaQueryWrapper<CircuitBreakerModel> wrapper = Wrappers.lambdaQuery(CircuitBreakerModel.class);
      if (Objects.nonNull(circuitBreakerReq.getId())) {
         wrapper.eq(CircuitBreakerModel::getId, circuitBreakerReq.getId());
      } else {
         if (Objects.isNull(circuitBreakerReq.getDataType())) {
            circuitBreakerReq.setDataType(0);
         }

         wrapper.eq(CircuitBreakerModel::getDataType, circuitBreakerReq.getDataType())
            .eq(CircuitBreakerModel::getDeviceCode, circuitBreakerReq.getDeviceCode())
            .orderByDesc(CircuitBreakerModel::getCreateDate)
            .last("limit 1");
      }

      CircuitBreakerModel circuitBreakerModel = (CircuitBreakerModel)this.circuitBreakerMapper.selectOne(wrapper);
      if (Objects.isNull(circuitBreakerModel)) {
         return new CircuitBreakerModel();
      }

      if (StringUtils.isNotBlank(circuitBreakerModel.getTruncationData())) {
         List<Float> cur = (List<Float>)JSON.parseObject(circuitBreakerModel.getTruncationData(), new TypeReference<List<Float>>() {}, new Feature[0]);
         circuitBreakerModel.setTruncationData(null);
         circuitBreakerModel.setCurrentData(cur);
      }

      return circuitBreakerModel;
   }

   @Override
   public Page<CircuitBreakerModel> getCircuitBreakerList(CircuitBreakerReq circuitBreakerReq) {
      Page<CircuitBreakerModel> ciPage = new Page(circuitBreakerReq.getPageNum().intValue(), circuitBreakerReq.getPageSize().intValue());
      LambdaQueryWrapper<CircuitBreakerModel> wrapper = Wrappers.lambdaQuery(CircuitBreakerModel.class);
      wrapper.select(CircuitBreakerModel.class, i -> !i.getColumn().equals("truncation_data"))
         .eq(CircuitBreakerModel::getDataType, circuitBreakerReq.getDataType())
         .eq(CircuitBreakerModel::getDeviceCode, circuitBreakerReq.getDeviceCode())
         .orderByDesc(CircuitBreakerModel::getCreateDate);
      return this.circuitBreakerMapper.selectPage(ciPage, wrapper);
   }

   @Override
   public CircuitBreakerInfoDto getCircuitBreakerInfo(String deviceCode) {
      List<String> keys = Lists.newArrayList(
         new String[]{
            DeviceCollectConstants.getCircuitBreakerCAOResdisKey(InitApplicationRunner.getAppCode(), deviceCode, "caoRatedNum"),
            DeviceCollectConstants.getCircuitBreakerCAOResdisKey(InitApplicationRunner.getAppCode(), deviceCode, "closeOperateNum"),
            DeviceCollectConstants.getCircuitBreakerCAOResdisKey(InitApplicationRunner.getAppCode(), deviceCode, "openOperateNum"),
            DeviceCollectConstants.getCircuitBreakerCAOResdisKey(InitApplicationRunner.getAppCode(), deviceCode, "closeOperateNumOfFir"),
            DeviceCollectConstants.getCircuitBreakerCAOResdisKey(InitApplicationRunner.getAppCode(), deviceCode, "openOperateNumOfFir")
         }
      );
      List<Object> list = this.redisTemplate.opsForValue().multiGet(keys);
      CircuitBreakerInfoDto circuitBreakerInfoDto = new CircuitBreakerInfoDto();
      circuitBreakerInfoDto.setDeviceStatus(0);
      if (list == null || list.isEmpty() || Objects.isNull(list.get(0))) {
         circuitBreakerInfoDto.setCloseOperateNum(0);
         circuitBreakerInfoDto.setOpenOperateNum(0);
         circuitBreakerInfoDto.setRemainingNum(0);
         circuitBreakerInfoDto.setRemainingLife(0.0);
         return circuitBreakerInfoDto;
      } else {
         int closeOperateNum = RemainingLifeUtil.asInt(list.get(1)) - RemainingLifeUtil.asInt(list.get(3));
         int openOperateNum = RemainingLifeUtil.asInt(list.get(2)) - RemainingLifeUtil.asInt(list.get(4));
         circuitBreakerInfoDto.setCloseOperateNum(Math.max(0, closeOperateNum));
         circuitBreakerInfoDto.setOpenOperateNum(Math.max(0, openOperateNum));
         circuitBreakerInfoDto.setRemainingNum(RemainingLifeUtil.remainingNum(list.get(0), closeOperateNum, openOperateNum));
         circuitBreakerInfoDto.setRemainingLife(RemainingLifeUtil.remainingLifePercent(list.get(0), closeOperateNum, openOperateNum));
         return circuitBreakerInfoDto;
      }
   }

   @Override
   public List<CircuitBreakerModel> getEnergyStorageList(String deviceCode, Integer listNum) {
      if (StringUtils.isBlank(deviceCode) || listNum == null || listNum <= 0) {
         return Collections.emptyList();
      }

      LambdaQueryWrapper<CircuitBreakerModel> wrapper = Wrappers.lambdaQuery(CircuitBreakerModel.class);
      wrapper.eq(CircuitBreakerModel::getDeviceCode, deviceCode).orderByDesc(CircuitBreakerModel::getCreateDate).last("limit " + listNum);
      List<CircuitBreakerModel> list = this.circuitBreakerMapper.selectList(wrapper);
      return list == null ? Collections.emptyList() : list;
   }
}
