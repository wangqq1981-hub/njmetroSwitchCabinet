package com.drsi.njmsc;

import com.dsri.iec104.annotation.EnableIec104Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.zhixin", "com.drsi"})
@EnableScheduling
@EnableFeignClients(basePackages = {"com.zhixin", "com.bpg"})
@EnableEurekaClient
@EnableIec104Server
public class NjMetroSwitchCabinetApplication {
   public static void main(String[] args) {
      SpringApplication.run(NjMetroSwitchCabinetApplication.class, args);
   }
}
