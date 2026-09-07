package com.drsi.njmsc.config;

import com.drsi.njmsc.mapper.DeviceHcsInfoMapper;
import com.drsi.njmsc.scheduled.Iec104Scheduled;
import com.dsri.iec104.ies.SunStation;
import com.dsri.iec104.ies.SunTerminal;
import com.dsri.iec104.ies.SunTerminal.SunTerminalBuilder;
import javax.annotation.Resource;
import org.openmuc.j60870.ASduType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("'pro'.equalsIgnoreCase('${spring.profiles.active}')")
public class UpwardDeliveryConfig {
   private static final Logger log = LoggerFactory.getLogger(UpwardDeliveryConfig.class);
   @Resource
   private DeviceHcsInfoMapper deviceHcsInfoMapper;

   @Bean
   public SunTerminal sunTerminal(SunTerminalBuilder sunTerminalBuilder) {
      SunStation[] oss = new SunStation[1];
      SunStation sunStation = new SunStation(Iec104Scheduled.commonAddress, ASduType.M_ME_NC_1, null, null);
      oss[0] = sunStation;
      return sunTerminalBuilder.build(oss);
   }
}
