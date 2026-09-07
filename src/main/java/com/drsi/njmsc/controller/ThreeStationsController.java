package com.drsi.njmsc.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.JSONReader.Feature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drsi.njmsc.dto.dto.CircuitBrCAODto;
import com.drsi.njmsc.dto.model.ThreeStationsModel;
import com.drsi.njmsc.dto.query.ThreeStationsReq;
import com.drsi.njmsc.mapper.ThreeStationsMapper;
import com.zhixin.common.core.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "三工位信息API")
@RequestMapping("/threeSt")
@RestController
public class ThreeStationsController {
   private static final Logger log = LoggerFactory.getLogger(ThreeStationsController.class);
   private static List<CircuitBrCAODto> close;
   private static List<CircuitBrCAODto> open;
   @Resource
   private ThreeStationsMapper threeStationsMapper;

   @PostConstruct
   public void init() {
      try {
         String c = readFile2Json("5_closing_threegong.json");
         String a = readFile2Json("5_apart_threegong.json");
         close = (List<CircuitBrCAODto>)JSON.parseObject(c, new TypeReference<List<CircuitBrCAODto>>() {}, new Feature[0]);
         open = (List<CircuitBrCAODto>)JSON.parseObject(a, new TypeReference<List<CircuitBrCAODto>>() {}, new Feature[0]);
         log.info("circuitBrCAOData init success close:{} open:{}", c.substring(0, 9), a.substring(0, 9));
      } catch (Exception e) {
         log.error("", e);
      }
   }

   @ApiOperation("获取分合闸数据详情 0-合闸 1-分闸")
   @GetMapping("/getCaoDetail")
   public Result<ThreeStationsModel> getCaoDetail(ThreeStationsReq threeStationsReq) {
      LambdaQueryWrapper<ThreeStationsModel> wrapper = Wrappers.lambdaQuery(ThreeStationsModel.class);
      wrapper.eq(ThreeStationsModel::getDeviceCode, threeStationsReq.getDeviceCode());
      if (Objects.nonNull(threeStationsReq.getId())) {
         wrapper.eq(ThreeStationsModel::getId, threeStationsReq.getId());
      } else {
         if (Objects.isNull(threeStationsReq.getDataType())) {
            threeStationsReq.setDataType(0);
         }

         if (threeStationsReq.getDataType() == 0) {
            wrapper.eq(ThreeStationsModel::getLsolatedPosition, 0);
         } else {
            wrapper.eq(ThreeStationsModel::getLsolatedPosition, 1);
         }

         if (Objects.isNull(threeStationsReq.getDeviceNum())) {
            threeStationsReq.setDeviceNum(3);
         }

         wrapper.eq(ThreeStationsModel::getDataType, threeStationsReq.getDeviceNum());
         wrapper.orderByDesc(ThreeStationsModel::getCreateDate).last("limit 1");
      }

      ThreeStationsModel threeStationsModel = (ThreeStationsModel)this.threeStationsMapper.selectOne(wrapper);
      if (Objects.isNull(threeStationsModel)) {
         return Result.successWithData(new ThreeStationsModel());
      }

      if (StringUtils.isNotBlank(threeStationsModel.getTruncationData())) {
         List<Float> cur = (List<Float>)JSON.parseObject(threeStationsModel.getTruncationData(), new TypeReference<List<Float>>() {}, new Feature[0]);
         threeStationsModel.setTruncationData(null);
         threeStationsModel.setCurrentData(cur);
      }

      return Result.successWithData(threeStationsModel);
   }

   @ApiOperation("获取分合闸历史数据 0-合闸 1-分闸")
   @GetMapping("/getCaoList/page")
   public Result<Page<ThreeStationsModel>> getCaoList(ThreeStationsReq threeStationsReq) {
      Page<ThreeStationsModel> ciPage = new Page(threeStationsReq.getPageNum().intValue(), threeStationsReq.getPageSize().intValue());
      LambdaQueryWrapper<ThreeStationsModel> wrapper = Wrappers.lambdaQuery(ThreeStationsModel.class);
      wrapper.select(ThreeStationsModel.class, i -> !i.getColumn().equals("truncation_data"))
         .eq(ThreeStationsModel::getDeviceCode, threeStationsReq.getDeviceCode());
      if (Objects.isNull(threeStationsReq.getDataType())) {
         threeStationsReq.setDataType(0);
      }

      if (Objects.isNull(threeStationsReq.getDeviceNum())) {
         threeStationsReq.setDeviceNum(3);
      }

      wrapper.eq(ThreeStationsModel::getDataType, threeStationsReq.getDeviceNum());

      if (threeStationsReq.getDataType() == 0) {
         wrapper.and(
            w -> w.or(we -> we.eq(ThreeStationsModel::getPosition, 0).eq(ThreeStationsModel::getOriginalPosition, 1))
               .or(we -> we.eq(ThreeStationsModel::getPosition, 2).eq(ThreeStationsModel::getOriginalPosition, 1))
         );
      } else {
         wrapper.and(
            w -> w.or(we -> we.eq(ThreeStationsModel::getPosition, 1).eq(ThreeStationsModel::getOriginalPosition, 0))
               .or(we -> we.eq(ThreeStationsModel::getPosition, 1).eq(ThreeStationsModel::getOriginalPosition, 2))
         );
      }

      wrapper.orderByDesc(ThreeStationsModel::getCreateDate);
      return Result.successWithData(this.threeStationsMapper.selectPage(ciPage, wrapper));
   }

   public static String readFile2Json(String FileName) throws IOException {
      InputStream ipt = ClassUtils.getDefaultClassLoader().getResourceAsStream(FileName);
      if (ipt == null) {
         throw new IOException("classpath resource not found: " + FileName);
      }

      StringBuilder jsonStr = new StringBuilder();
      try (InputStream in = ipt; InputStreamReader isr = new InputStreamReader(in, "utf-8"); BufferedReader br = new BufferedReader(isr)) {
         String line;
         while ((line = br.readLine()) != null) {
            jsonStr.append(line);
         }
      }

      log.info("readFile2Json:{}", jsonStr);
      return jsonStr.toString();
   }
}
