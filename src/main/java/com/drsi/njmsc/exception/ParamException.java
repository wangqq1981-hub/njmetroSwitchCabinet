package com.drsi.njmsc.exception;

public class ParamException extends RuntimeException {
   private static final long serialVersionUID = -82518592398298449L;
   protected String code;

   ParamException() {
   }

   public ParamException(String message) {
      super(message);
   }

   public ParamException(String message, String code) {
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
