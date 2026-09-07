package com.drsi.njmsc.scheduled;

import com.drsi.njmsc.dto.model.BaseTaskInfoModel;
import com.drsi.njmsc.runner.InitApplicationRunner;
import java.io.File;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractScheduled implements InitializingBean {
   private static final Logger log = LoggerFactory.getLogger(AbstractScheduled.class);
   protected String collectCode;
   protected String type;
   protected List<BaseTaskInfoModel> taskInfoModels;
   @Resource
   protected InitApplicationRunner initApplicationRunner;
   @Resource
   protected RestTemplate restTemplate;
   @Resource
   protected RedisTemplate<String, Object> redisTemplate;

   @PostConstruct
   public abstract void init();

   public void afterPropertiesSet() throws Exception {
   }

   protected void setup() {
   }

   protected void cleanup() {
   }

   public String getDataFilePath() {
      return InitApplicationRunner.getDataPath() + File.separator + this.getCollectCode();
   }

   public String getCollectCode() {
      return this.collectCode;
   }

   public String getType() {
      return this.type;
   }

   public List<BaseTaskInfoModel> getTaskInfoModels() {
      return this.taskInfoModels;
   }

   public InitApplicationRunner getInitApplicationRunner() {
      return this.initApplicationRunner;
   }

   public RestTemplate getRestTemplate() {
      return this.restTemplate;
   }

   public RedisTemplate<String, Object> getRedisTemplate() {
      return this.redisTemplate;
   }

   public void setCollectCode(final String collectCode) {
      this.collectCode = collectCode;
   }

   public void setType(final String type) {
      this.type = type;
   }

   public void setTaskInfoModels(final List<BaseTaskInfoModel> taskInfoModels) {
      this.taskInfoModels = taskInfoModels;
   }

   public void setInitApplicationRunner(final InitApplicationRunner initApplicationRunner) {
      this.initApplicationRunner = initApplicationRunner;
   }

   public void setRestTemplate(final RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
   }

   public void setRedisTemplate(final RedisTemplate<String, Object> redisTemplate) {
      this.redisTemplate = redisTemplate;
   }
}
