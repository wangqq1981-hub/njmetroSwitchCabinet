package com.drsi.njmsc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SpringScheduleConfig {
   @Bean
   public TaskScheduler taskScheduler() {
      ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
      taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
      return taskScheduler;
   }
}
