package com.drsi.njmsc.exception;

public class BussException extends BaseException {
   public BussException() {
   }

   public BussException(String message) {
      super(message);
   }

   public BussException(String message, String code) {
      super(code, message);
   }
}
