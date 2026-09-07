package com.zhixin.common.core.domain;

public class Result<T> {
   private int statusCode;
   private String error;
   private String msg;
   private T data;
   private boolean success = true;

   public static <T> Result<T> success() {
      return new Result<T>();
   }

   public static <T> Result<T> successWithData(T data) {
      Result<T> result = new Result<T>();
      result.data = data;
      return result;
   }

   public static <T> Result<T> failure(int code, String msg) {
      Result<T> result = new Result<T>();
      result.success = false;
      result.statusCode = code;
      result.msg = msg;
      result.error = msg;
      return result;
   }

   public static <T> Result<T> failure(int code, String error, String msg) {
      Result<T> result = failure(code, msg);
      result.error = error;
      return result;
   }

   public boolean isSuccess() {
      return this.success;
   }

   public int getStatusCode() {
      return this.statusCode;
   }

   public String getError() {
      return this.error;
   }

   public String getMsg() {
      return this.msg;
   }

   public T getData() {
      return this.data;
   }
}
