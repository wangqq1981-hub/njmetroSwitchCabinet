package com.drsi.njmsc.server;

import cn.hutool.json.JSONUtil;
import com.drsi.njmsc.constant.DeviceCollectConstants;
import com.drsi.njmsc.dto.vo.MonitorViewVo;
import com.drsi.njmsc.runner.InitApplicationRunner;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MonitorViewHandler extends TextWebSocketHandler {
   private static final Logger log = LoggerFactory.getLogger(MonitorViewHandler.class);
   private static RedisTemplate redisTemplate;

   @Autowired
   public void setRedisTemplate(RedisTemplate redisTemplate) {
      MonitorViewHandler.redisTemplate = redisTemplate;
   }

   public void afterConnectionEstablished(WebSocketSession session) throws Exception {
      String user = (String)session.getAttributes().get("user");
      String deviceCode = (String)session.getAttributes().get("deviceCode");
      String queryType = (String)session.getAttributes().get("queryType");
      String sessionKey = user + "_" + deviceCode + "_" + queryType;
      WebSocketSession old = MonitorViewSessionManager.getSession(sessionKey);
      if (old != null) {
         MonitorViewSessionManager.removeAndClose(sessionKey);
         log.info("[MonitorViewHandler]关闭失效连接:{}", sessionKey);
      }

      MonitorViewSessionManager.add(sessionKey, session);
   }

   public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
      session.sendMessage(new TextMessage("hello!"));
   }

   public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
      log.info("[MonitorViewHandler]连接关闭:{}", this.getSessionKey(session));
      MonitorViewSessionManager.remove(this.getSessionKey(session));
   }

   public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
      log.error("[MonitorViewHandler]异常连接处理:{}", this.getSessionKey(session));
      MonitorViewSessionManager.removeAndClose(this.getSessionKey(session));
   }

   private String getSessionKey(WebSocketSession session) {
      String user = (String)session.getAttributes().get("user");
      String deviceCode = (String)session.getAttributes().get("deviceCode");
      String queryType = (String)session.getAttributes().get("queryType");
      return user + "_" + deviceCode + "_" + queryType;
   }

   public static void sendRawData(Object result, String deviceCode, String queryType, String dataType) {
      for (Entry sessionEntry : MonitorViewSessionManager.SESSION_POOL.entrySet()) {
         String sessionKey = (String)sessionEntry.getKey();
         String[] keys = sessionKey.split("_");
         if (sessionKey.equals(keys[0] + "_" + deviceCode + "_" + queryType)) {
            WebSocketSession session = (WebSocketSession)sessionEntry.getValue();
            synchronized (session) {
               if (session.isOpen()) {
                  MonitorViewVo mv = new MonitorViewVo<Object>().setData(result).setQueryType(queryType).setDataType(dataType);

                  try {
                     session.sendMessage(new TextMessage(JSONUtil.toJsonStr(mv)));
                     log.debug("[monitor]：推送成功 queryType:{}", queryType);
                  } catch (IOException e) {
                     log.error("[MonitorViewHandler]数据推送异常");
                     throw new RuntimeException(e);
                  }
               }
            }
         }
      }
   }

   public static void sendAndCacheRawData(Object result, String deviceCode, String queryType, String dataType) {
      sendRawData(result, deviceCode, queryType, dataType);
      redisTemplate.opsForValue()
         .set(
            DeviceCollectConstants.getDeviceWebsocketResdisKey(InitApplicationRunner.getAppCode(), deviceCode, queryType, dataType),
            JSONUtil.toJsonStr(result),
            360L,
            TimeUnit.MINUTES
         );
   }
}
