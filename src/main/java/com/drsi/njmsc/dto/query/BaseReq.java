package com.drsi.njmsc.dto.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "BaseReq", description = "基础查询请求参数对象")
public class BaseReq {
   @ApiModelProperty(value = "页数", dataType = "int", example = "1")
   private Integer pageNum = 1;
   @ApiModelProperty(value = "每页条数", dataType = "int", example = "15")
   private Integer pageSize = 15;

   public Integer getPageNum() {
      return this.pageNum;
   }

   public Integer getPageSize() {
      return this.pageSize;
   }

   public void setPageNum(final Integer pageNum) {
      this.pageNum = pageNum;
   }

   public void setPageSize(final Integer pageSize) {
      this.pageSize = pageSize;
   }

   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof BaseReq)) {
         return false;
      } else {
         BaseReq other = (BaseReq)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$pageNum = this.getPageNum();
            Object other$pageNum = other.getPageNum();
            if (this$pageNum == null ? other$pageNum == null : this$pageNum.equals(other$pageNum)) {
               Object this$pageSize = this.getPageSize();
               Object other$pageSize = other.getPageSize();
               return this$pageSize == null ? other$pageSize == null : this$pageSize.equals(other$pageSize);
            } else {
               return false;
            }
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof BaseReq;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $pageNum = this.getPageNum();
      result = result * 59 + ($pageNum == null ? 43 : $pageNum.hashCode());
      Object $pageSize = this.getPageSize();
      return result * 59 + ($pageSize == null ? 43 : $pageSize.hashCode());
   }

   @Override
   public String toString() {
      return "BaseReq(pageNum=" + this.getPageNum() + ", pageSize=" + this.getPageSize() + ")";
   }
}
