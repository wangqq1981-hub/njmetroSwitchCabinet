package com.drsi.njmsc.aop;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.BetweenFormatter.Level;
import com.alibaba.fastjson2.JSON;
import java.util.Date;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CountTimeAspect {
   private static final Logger log = LoggerFactory.getLogger(CountTimeAspect.class);

   @Pointcut("@annotation(com.drsi.njmsc.annotation.CountTime)")
   public void methodPointCut() {
   }

   @Around("methodPointCut()")
   public Object runTimeStatistics(ProceedingJoinPoint pjp) throws Throwable {
      Signature signature = pjp.getSignature();
      String className = pjp.getTarget().getClass().getName();
      String methodName = signature.getName();
      Object[] requestParams = pjp.getArgs();
      StringBuffer sb = new StringBuffer();

      for (Object requestParam : requestParams) {
         if (requestParam != null) {
            sb.append(JSON.toJSONString(requestParam));
            sb.append(",");
         }
      }

      String requestParamsString = sb.toString();
      if (requestParamsString.length() > 0) {
         requestParamsString = requestParamsString.substring(0, requestParamsString.length() - 1);
      }

      log.info(String.format("【%s】类的【%s】方法，请求参数：%s", className, methodName, requestParamsString));
      Date startDate = DateUtil.date();
      Object response = pjp.proceed();
      Date endDate = DateUtil.date();
      long betweenDate = DateUtil.between(startDate, endDate, DateUnit.MS);
      String formatBetween = DateUtil.formatBetween(betweenDate, Level.MILLISECOND);
      log.info(String.format("【%s】类的【%s】方法，应答参数：%s", className, methodName, JSON.toJSONString(response)));
      log.info(String.format("方法【%s】总耗时(毫秒)：%s", methodName, formatBetween));
      return response;
   }
}
