package com.drsi.njmsc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.drsi.njmsc.dto.model.AlarmRecordModel;
import com.drsi.njmsc.mapper.AlarmRecordMapper;
import com.zhixin.common.core.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "告警记录API")
@RequestMapping("/alarmRecord")
@RestController
public class AlarmRecordController {
   private static final Logger log = LoggerFactory.getLogger(AlarmRecordController.class);
   @Resource
   private AlarmRecordMapper alarmRecordMapper;

   @ApiOperation("获取未读告警记录")
   @GetMapping("/getNotReadAlarmRecord")
   public Result<List<AlarmRecordModel>> getNotReadAlarmRecord(Integer readFinal) {
      LambdaQueryWrapper<AlarmRecordModel> alarmRecordModelLambdaQueryWrapper = Wrappers.lambdaQuery(AlarmRecordModel.class);
      alarmRecordModelLambdaQueryWrapper.eq(AlarmRecordModel::getReadFinal, readFinal).orderByDesc(AlarmRecordModel::getCreateDate);
      List<AlarmRecordModel> alarmRecordModels = this.alarmRecordMapper.selectList(alarmRecordModelLambdaQueryWrapper);
      return Result.successWithData(alarmRecordModels);
   }

   @ApiOperation("未读告警记录 置已读")
   @GetMapping("/setAlarmRecordReadFinal")
   public Result setAlarmRecordReadFinal(Long id) {
      if (id == null) {
         return Result.failure(400, "告警记录id不能为空");
      }

      AlarmRecordModel alarmRecordModel = (AlarmRecordModel)this.alarmRecordMapper.selectById(id);
      if (alarmRecordModel == null) {
         return Result.failure(404, "告警记录不存在");
      }

      alarmRecordModel.setReadFinal(1);
      this.alarmRecordMapper.updateById(alarmRecordModel);
      return Result.success();
   }
}
