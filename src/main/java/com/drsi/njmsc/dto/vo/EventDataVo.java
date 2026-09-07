package com.drsi.njmsc.dto.vo;

public class EventDataVo {
   private String dataType;
   private Object data;

   public String getDataType() {
      return this.dataType;
   }

   public Object getData() {
      return this.data;
   }

   public void setDataType(final String dataType) {
      this.dataType = dataType;
   }

   public void setData(final Object data) {
      this.data = data;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EventDataVo)) {
         return false;
      } else {
         EventDataVo other = (EventDataVo)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$dataType = this.getDataType();
            Object other$dataType = other.getDataType();
            if (this$dataType == null ? other$dataType == null : this$dataType.equals(other$dataType)) {
               Object this$data = this.getData();
               Object other$data = other.getData();
               return this$data == null ? other$data == null : this$data.equals(other$data);
            } else {
               return false;
            }
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof EventDataVo;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $dataType = this.getDataType();
      result = result * 59 + ($dataType == null ? 43 : $dataType.hashCode());
      Object $data = this.getData();
      return result * 59 + ($data == null ? 43 : $data.hashCode());
   }

   @Override
   public String toString() {
      return "EventDataVo(dataType=" + this.getDataType() + ", data=" + this.getData() + ")";
   }
}
