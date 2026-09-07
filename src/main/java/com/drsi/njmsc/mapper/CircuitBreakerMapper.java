package com.drsi.njmsc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drsi.njmsc.dto.model.CircuitBreakerModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CircuitBreakerMapper extends BaseMapper<CircuitBreakerModel> {
}
