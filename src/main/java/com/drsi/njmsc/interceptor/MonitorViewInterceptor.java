package com.drsi.njmsc.interceptor;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zhixin.api.client.UserClient;
import com.zhixin.common.core.domain.Result;
import com.zhixin.common.core.domain.SysUser;
import com.zhixin.common.core.util.StringUtils;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class MonitorViewInterceptor implements HandshakeInterceptor {
   private static final Logger log = LoggerFactory.getLogger(MonitorViewInterceptor.class);
   @Autowired
   private UserClient userClient;

   public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
      log.info("握手前处理");
      if (request instanceof ServletServerHttpRequest) {
         HttpServletRequest req = ((ServletServerHttpRequest)request).getServletRequest();
         String authorization = req.getHeader("Sec-WebSocket-Protocol");
         Result checkResult = this.userClient.wsAuth(authorization);
         if (!checkResult.isSuccess()) {
            log.info("MonitorView连接，用户登录失败 = {}", checkResult.getMsg());
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return false;
         }

         SysUser userInfo = (SysUser)JSONUtil.toBean(JSONUtil.toJsonStr(checkResult.getData()), SysUser.class);
         String user = userInfo.getAccount();
         Map<String, String> paramMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), StandardCharsets.UTF_8);
         String deviceCode = paramMap.get("deviceCode");
         String queryType = paramMap.get("queryType");
         if (StringUtils.isNotEmpty(user) && StringUtils.isNotEmpty(deviceCode) && StringUtils.isNotEmpty(queryType)) {
            attributes.put("user", user);
            attributes.put("deviceCode", deviceCode);
            attributes.put("queryType", queryType);
            return true;
         }
      }

      log.info("MonitorView用户登录失效");
      return false;
   }

   public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
      HttpServletRequest httpRequest = ((ServletServerHttpRequest)request).getServletRequest();
      HttpServletResponse httpResponse = ((ServletServerHttpResponse)response).getServletResponse();
      if (StringUtils.isNotEmpty(httpRequest.getHeader("Sec-WebSocket-Protocol"))) {
         httpResponse.addHeader("Sec-WebSocket-Protocol", httpRequest.getHeader("Sec-WebSocket-Protocol"));
      }
   }
}
