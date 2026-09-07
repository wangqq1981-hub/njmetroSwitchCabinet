package com.drsi.njmsc.exception;

import com.drsi.njmsc.constant.SwitchStatusEnums;
import com.zhixin.common.core.domain.Result;
import javax.validation.ValidationException;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionConfig {
   private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionConfig.class);

   @ExceptionHandler(NoHandlerFoundException.class)
   public Result<String> handleNotFoundException(NoHandlerFoundException e) {
      Result<String> result = Result.failure(SwitchStatusEnums.NOT_FOUND.code(), SwitchStatusEnums.NOT_FOUND.desc());
      logger.info(e.getMessage());
      return result;
   }

   @ExceptionHandler(BaseException.class)
   public Result<String> handleBaseException(BaseException e) {
      Result<String> result = Result.failure(SwitchStatusEnums.INTERNAL_ERROR.code(), e.getMessage());
      logger.error("BaseException[code: {}, message: {}]", new Object[]{e.getCode(), e.getMessage(), e});
      return result;
   }

   @ExceptionHandler(ParamException.class)
   public Result<String> handleParamException(ParamException e) {
      Result<String> result = Result.failure(SwitchStatusEnums.BAD_REQUEST.code(), e.getMessage());
      logger.error("ParamException[code: {}, message: {}]", new Object[]{e.getCode(), e.getMessage(), e});
      return result;
   }

   @ExceptionHandler(Exception.class)
   public Result<String> handleException(Exception e) {
      Result<String> result = Result.failure(SwitchStatusEnums.ERROR.code(), SwitchStatusEnums.ERROR.desc());
      logger.error(e.getMessage(), e);
      return result;
   }

   @ExceptionHandler(IllegalArgumentException.class)
   public Result<String> handleIllegalArgumentException(IllegalArgumentException e) {
      Result<String> result = Result.failure(SwitchStatusEnums.INTERNAL_ERROR.code(), e.getMessage());
      logger.error(e.getClass().getSimpleName(), e);
      return result;
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public Result<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
      ObjectError objectError = (ObjectError)e.getBindingResult().getAllErrors().get(0);
      logger.info("异常对象{}的错误信息：{}", objectError, e.getMessage());
      return Result.failure(
         SwitchStatusEnums.BAD_REQUEST.code(), Strings.isNotBlank(e.getMessage()) ? e.getLocalizedMessage() : SwitchStatusEnums.BAD_REQUEST.desc()
      );
   }

   @ExceptionHandler(ValidationException.class)
   public Result<String> handleValidationException(ValidationException e) {
      Result<String> result = Result.failure(SwitchStatusEnums.INTERNAL_ERROR.code(), e.getMessage());
      logger.error(e.getMessage());
      return result;
   }

   @ExceptionHandler(BussException.class)
   public Result<String> handleBussException(BussException e) {
      Result<String> result = Result.failure(SwitchStatusEnums.INTERNAL_ERROR.code(), e.getMessage());
      logger.error(e.getMessage());
      return result;
   }
}
