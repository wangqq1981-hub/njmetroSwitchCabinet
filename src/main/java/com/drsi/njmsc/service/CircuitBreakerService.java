package com.drsi.njmsc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drsi.njmsc.dto.dto.CircuitBreakerInfoDto;
import com.drsi.njmsc.dto.model.CircuitBreakerModel;
import com.drsi.njmsc.dto.query.CircuitBreakerReq;
import java.util.List;

public interface CircuitBreakerService extends IService<CircuitBreakerModel> {
   CircuitBreakerModel getCircuitBreakerModel(CircuitBreakerReq circuitBreakerReq);

   Page<CircuitBreakerModel> getCircuitBreakerList(CircuitBreakerReq circuitBreakerReq);

   CircuitBreakerInfoDto getCircuitBreakerInfo(String deviceCode);

   List<CircuitBreakerModel> getEnergyStorageList(String deviceCode, Integer listNum);
}
