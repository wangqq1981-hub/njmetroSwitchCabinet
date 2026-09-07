package com.drsi.njmsc.runner;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.JSONReader.Feature;
import com.drsi.njmsc.controller.ThreeStationsController;
import com.drsi.njmsc.dto.model.BaseTaskInfoModel;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitApplicationRunner implements ApplicationRunner {
   private static final Logger log = LoggerFactory.getLogger(InitApplicationRunner.class);
   private final List<BaseTaskInfoModel> taskInfoModels = Lists.newArrayList();
   private static String appCode;
   private static String dataPath;

   public InitApplicationRunner() throws IOException {
      String s = ThreeStationsController.readFile2Json("taskInfo.json");
      this.taskInfoModels.addAll((Collection<? extends BaseTaskInfoModel>)JSON.parseObject(s, new TypeReference<List<BaseTaskInfoModel>>() {}, new Feature[0]));
   }

   public void run(ApplicationArguments args) throws Exception {
      log.info("InitApplicationRunner init....");
   }

   public static String getAppCode() {
      return appCode;
   }

   @Value("${app.code:acfCircuitBreaker}")
   public void setAppCode(String appCode) {
      InitApplicationRunner.appCode = appCode;
   }

   public static String getDataPath() {
      return dataPath;
   }

   @Value("${njmsc.data.path}")
   public void setDataPath(String dataPath) {
      InitApplicationRunner.dataPath = dataPath;
   }

   public List<BaseTaskInfoModel> getTaskInfoModels() {
      return this.taskInfoModels;
   }
}
