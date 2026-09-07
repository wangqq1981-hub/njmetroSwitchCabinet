package com.drsi.njmsc.config;

import com.drsi.njmsc.interceptor.MonitorViewInterceptor;
import com.drsi.njmsc.server.MonitorViewHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class MonitorViewConfig implements WebSocketConfigurer {
   @Autowired
   private MonitorViewHandler monitorViewHandler;
   @Autowired
   private MonitorViewInterceptor monitorViewInterceptor;

   public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
      registry.addHandler(this.monitorViewHandler, new String[]{"/ws/monitorView"})
         .addInterceptors(new HandshakeInterceptor[]{this.monitorViewInterceptor})
         .setAllowedOrigins(new String[]{"*"});
   }
}
