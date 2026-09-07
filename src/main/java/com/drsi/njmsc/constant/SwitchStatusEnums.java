package com.drsi.njmsc.constant;

import com.google.common.collect.Maps;
import com.zhixin.common.core.enums.BaseEnum;
import java.util.Map;
import org.jetbrains.annotations.Contract;

public enum SwitchStatusEnums implements BaseEnum<Integer, String> {
   ERROR(-1, "System Exception"),
   OPERATION_FAILURE(0, "operation.failure"),
   OPERATION_SUCCESS(1, "operation.success"),
   PARAMETER_NOT_NULL(400103, "Parameter cannot be empty"),
   SUCCESS(200, "operation.success"),
   CREATED(201, "Created"),
   ACCEPTED(202, "Accepted"),
   NOT_AUTHORITATIVE(203, "Non-Authoritative Information"),
   NO_CONTENT(204, "No Content"),
   RESET(205, "Reset Content"),
   BAD_REQUEST(400, "Bad Request"),
   UNAUTHORIZED(401, "Unauthorized"),
   FORBIDDEN(403, "Forbidden"),
   NOT_FOUND(404, "Not Found"),
   BAD_METHOD(405, "Method Not Allowed"),
   NOT_ACCEPTABLE(406, "Not Acceptable"),
   REQUEST_TIME_OUT(408, "Request Timeout"),
   CONFLICT(409, "Conflict"),
   PRECON_FAILED(412, "Precondition Failed"),
   ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
   URI_TOO_LARGE(414, "Request-URI Too Long"),
   UNSUPPORTED_TYPE(415, "Unsupported Media Type"),
   INTERNAL_ERROR(500, "Internal Server Error"),
   BAD_GATEWAY(502, "Bad Gateway"),
   SERVICE_UNAVAILABLE(503, "Service Unavailable");

   private final Integer code;
   private final String desc;
   private static final Map<Integer, String> ALL_MAP = Maps.newHashMap();

   SwitchStatusEnums(Integer code, String desc) {
      this.code = code;
      this.desc = desc;
   }

   @Contract(pure = true)
   public Integer code() {
      return this.code;
   }

   @Contract(pure = true)
   public String desc() {
      return this.desc;
   }

   public static String desc(Integer code) {
      return ALL_MAP.get(code);
   }

   static {
      for (SwitchStatusEnums enums : values()) {
         ALL_MAP.put(enums.code, enums.desc);
      }
   }
}
