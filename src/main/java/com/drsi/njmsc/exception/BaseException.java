package com.drsi.njmsc.exception;

public class BaseException extends RuntimeException {
   private static final long serialVersionUID = -8258378592398298449L;
   protected String code;

   BaseException() {
   }

   BaseException(String message) {
      super(message);
   }

   public BaseException(String message, String code) {
      super(message);
      this.code = code;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String code) {
      this.code = code;
   }
}
