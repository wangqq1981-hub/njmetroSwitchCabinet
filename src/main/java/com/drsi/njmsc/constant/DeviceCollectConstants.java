package com.drsi.njmsc.constant;

public interface DeviceCollectConstants {
   String MONITOR_WEBSOCKET_REDIS_PREFIX = "monitorWebsocket";
   String CIRCUIT_BREAKER_REDIS_PREFIX = "circuitBreaker";
   String CIRCUIT_BREAKER_CLOSEOPERATE_NUM = "closeOperateNum";
   String CIRCUIT_BREAKER_OPENOPERATE_NUM = "openOperateNum";
   String CIRCUIT_BREAKER_ENERGYSTORAGE_DATE = "energyStorageDate";
   String CIRCUIT_BREAKER_CAO_RATED_NUM = "caoRatedNum";
   String CIRCUIT_BREAKER_CLOSEOPERATE_NUM_Fir = "closeOperateNumOfFir";
   String CIRCUIT_BREAKER_OPENOPERATE_NUM_Fir = "openOperateNumOfFir";
   long COLLECT_MONITOR_REDIS_EFFECTIVE_TIME = 360L;

   static String getDeviceWebsocketResdisKey(String appCode, String deviceCode, String queryType, String dataType) {
      return appCode + ":" + "monitorWebsocket" + ":" + deviceCode + ":" + queryType + ":" + dataType;
   }

   static String getCircuitBreakerCAOResdisKey(String appCode, String deviceCode, String dataType) {
      return appCode + ":" + "circuitBreaker" + ":" + deviceCode + ":" + dataType;
   }
}
