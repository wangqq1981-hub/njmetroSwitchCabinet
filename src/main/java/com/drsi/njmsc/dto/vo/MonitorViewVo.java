package com.drsi.njmsc.dto.vo;

public class MonitorViewVo<T> {
   private String dataType;
   private String queryType;
   private T data;

   public MonitorViewVo<T> setDataType(final String dataType) {
      this.dataType = dataType;
      return this;
   }

   public MonitorViewVo<T> setQueryType(final String queryType) {
      this.queryType = queryType;
      return this;
   }

   public MonitorViewVo<T> setData(final T data) {
      this.data = data;
      return this;
   }

   public String getDataType() {
      return this.dataType;
   }

   public String getQueryType() {
      return this.queryType;
   }

   public T getData() {
      return this.data;
   }
}
