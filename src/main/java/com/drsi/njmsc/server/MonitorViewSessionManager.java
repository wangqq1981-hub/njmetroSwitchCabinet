package com.drsi.njmsc.server;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

public class MonitorViewSessionManager {
   private static final Logger log = LoggerFactory.getLogger(MonitorViewSessionManager.class);
   public static final ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

   public static void add(String key, WebSocketSession session) {
      SESSION_POOL.put(key, session);
      log.info("[SESSION_POOL]加入连接池:{}", key);
   }

   public static WebSocketSession remove(String key) {
      log.info("[SESSION_POOL]连接池移除:{}", key);
      return SESSION_POOL.remove(key);
   }

   public static void removeAndClose(String key) {
      WebSocketSession session = remove(key);
      if (session != null) {
         try {
            log.info("[SESSION_POOL]关闭连接:{}", key);
            session.close();
         } catch (IOException e) {
            log.error("[SESSION_POOL]关闭连接失败:{}", key, e);
         }
      }
   }

   public static WebSocketSession getSession(String key) {
      return SESSION_POOL.get(key);
   }
}
