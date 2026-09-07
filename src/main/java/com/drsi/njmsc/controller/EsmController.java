package com.drsi.njmsc.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.JSONReader.Feature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drsi.njmsc.dto.model.EnergyStorageMotorModel;
import com.drsi.njmsc.dto.query.EsmReq;
import com.drsi.njmsc.mapper.EnergyStorageMotorMapper;
import com.zhixin.common.core.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "储能电机信息API")
@RequestMapping("/esm")
@RestController
public class EsmController {
   private static final Logger log = LoggerFactory.getLogger(EsmController.class);
   @Resource
   private EnergyStorageMotorMapper energyStorageMotorMapper;

   @ApiOperation("获取储能电机数据详情")
   @GetMapping("/getDetail")
   public Result<EnergyStorageMotorModel> getDetail(EsmReq esmReq) {
      LambdaQueryWrapper<EnergyStorageMotorModel> wrapper = Wrappers.lambdaQuery(EnergyStorageMotorModel.class);
      wrapper.eq(EnergyStorageMotorModel::getDeviceCode, esmReq.getDeviceCode());
      if (Objects.nonNull(esmReq.getId())) {
         wrapper.eq(EnergyStorageMotorModel::getId, esmReq.getId());
      } else {
         ((LambdaQueryWrapper)wrapper.orderByDesc(EnergyStorageMotorModel::getCreateDate)).last("limit 1");
      }

      EnergyStorageMotorModel energyStorageMotorModel = (EnergyStorageMotorModel)this.energyStorageMotorMapper.selectOne(wrapper);
      if (Objects.isNull(energyStorageMotorModel)) {
         return Result.successWithData(new EnergyStorageMotorModel());
      }

      if (StringUtils.isNotBlank(energyStorageMotorModel.getTruncationData())) {
         List<Float> cur = (List<Float>)JSON.parseObject(energyStorageMotorModel.getTruncationData(), new TypeReference<List<Float>>() {}, new Feature[0]);
         energyStorageMotorModel.setTruncationData(null);
         energyStorageMotorModel.setCurrentData(cur);
      }

      return Result.successWithData(energyStorageMotorModel);
   }

   @ApiOperation("获取储能电机历史数据 0-合闸 1-分闸")
   @GetMapping("/getList/page")
   public Result<Page<EnergyStorageMotorModel>> getList(EsmReq esmReq) {
      Page<EnergyStorageMotorModel> ciPage = new Page(esmReq.getPageNum().intValue(), esmReq.getPageSize().intValue());
      LambdaQueryWrapper<EnergyStorageMotorModel> wrapper = Wrappers.lambdaQuery(EnergyStorageMotorModel.class);
      wrapper.select(EnergyStorageMotorModel.class, i -> !i.getColumn().equals("truncation_data"))
         .eq(EnergyStorageMotorModel::getDeviceCode, esmReq.getDeviceCode());
      wrapper.orderByDesc(EnergyStorageMotorModel::getCreateDate);
      return Result.successWithData(this.energyStorageMotorMapper.selectPage(ciPage, wrapper));
   }
}
