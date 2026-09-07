package com.drsi.njmsc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drsi.njmsc.dto.model.CircuitBreakerModel;
import com.drsi.njmsc.dto.query.CircuitBreakerReq;
import com.drsi.njmsc.service.CircuitBreakerService;
import com.zhixin.common.core.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "断路器信息API")
@RequestMapping("/circuitBr")
@RestController
public class CircuitBrInfoController {
   private static final Logger log = LoggerFactory.getLogger(CircuitBrInfoController.class);
   @Resource
   private CircuitBreakerService circuitBreakerService;

   @ApiOperation("获取分合闸数据详情 0-合闸 1-分闸")
   @GetMapping("/getCaoDetail")
   public Result<CircuitBreakerModel> getCaoDetail(CircuitBreakerReq circuitBreakerReq) {
      return Result.successWithData(this.circuitBreakerService.getCircuitBreakerModel(circuitBreakerReq));
   }

   @ApiOperation("获取分合闸历史数据 0-合闸 1-分闸")
   @GetMapping("/getCaoList/page")
   public Result<Page<CircuitBreakerModel>> getCaoList(CircuitBreakerReq circuitBreakerReq) {
      return Result.successWithData(this.circuitBreakerService.getCircuitBreakerList(circuitBreakerReq));
   }
}
