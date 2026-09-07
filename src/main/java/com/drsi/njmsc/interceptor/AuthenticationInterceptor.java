package com.drsi.njmsc.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter.Feature;
import com.drsi.njmsc.annotation.CheckToken;
import com.drsi.njmsc.runner.InitApplicationRunner;
import com.zhixin.api.client.UserClient;
import com.zhixin.api.req.TokenReq;
import com.zhixin.common.core.context.UserContextHolder;
import com.zhixin.common.core.domain.Result;
import com.zhixin.common.core.domain.SysUser;
import com.zhixin.common.core.util.ip.IpUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthenticationInterceptor extends UserContextHolder implements HandlerInterceptor {
   private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);
   @Autowired
   private UserClient userClient;

   public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
      log.debug("---------------------开始进入请求地址拦截----------------------------");
      String token = httpServletRequest.getHeader("Authorization");
      String ip = IpUtils.getIpAddr(httpServletRequest);
      log.info("当前pc ip为：{}", ip);
      log.info("当前token为：{}", token);
      if (!(o instanceof HandlerMethod)) {
         return true;
      }

      HandlerMethod handlerMethod = (HandlerMethod)o;
      Method method = handlerMethod.getMethod();
      if (method.isAnnotationPresent(CheckToken.class)) {
         if (StringUtils.isEmpty(token)) {
            Result<Boolean> result = Result.failure(HttpStatus.PRECONDITION_FAILED.value(), HttpStatus.PRECONDITION_FAILED.getReasonPhrase(), "无token，请重新登录");
            this.responseOutWithJson(httpServletResponse, result);
            return false;
         }

         Result<SysUser> tResult = this.userClient.authToken(new TokenReq(InitApplicationRunner.getAppCode(), Boolean.TRUE, ip));
         if (!tResult.isSuccess()) {
            Result<Boolean> result = Result.failure(tResult.getStatusCode(), tResult.getError(), tResult.getMsg());
            this.responseOutWithJson(httpServletResponse, result);
            return false;
         }

         this.setLoginUser((SysUser)tResult.getData());
      }

      return true;
   }

   public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
   }

   public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
   }

   protected void responseOutWithJson(HttpServletResponse response, Object responseObject) {
      String strjson = JSONObject.toJSONString(responseObject, new Feature[0]);
      JSONObject jsonObject = JSONObject.parseObject(strjson);
      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/json; charset=utf-8");

      try (PrintWriter out = response.getWriter()) {
         out.append(jsonObject.toString());
         log.debug("返回的结果是：{}", jsonObject);
      } catch (IOException e) {
         log.error("写回response失败", e);
      }
   }
}
