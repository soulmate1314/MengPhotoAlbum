package com.yansir.exception;

import com.yansir.restful.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author YANSIR
 * @Description:全局异常拦截
 * @date 2020/7/9 15:50
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        return processException(request, response, ex);
    }

    private ResponseEntity<?> processException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String requestMethod = request.getMethod();
        String requestUri = request.getRequestURI();
        boolean isAjax = isRequestAjax(request, response);

        if (log.isInfoEnabled()) {
            ex.printStackTrace();
            log.info("Start handle exception,Exception message:" + ex.getMessage());
            log.info("Current request requestMethod============{},requestUri============{},isAjax============{}", requestMethod, requestUri, isAjax);
        }
        return buildResponseEntity(ex);
    }

    private ResponseEntity<?> buildResponseEntity(Throwable ex) {
        if (ex instanceof ResourceNotFoundException) {//404异常
            return ResponseEntity.status(ResultCode.CODE_404.getStatus()).body(new ErrorReponse(ResultCode.CODE_404));
        } else if (ex instanceof TokenWrongException) {//402异常
            return ResponseEntity.status(ResultCode.CODE_402.getStatus()).body(new ErrorReponse(ResultCode.CODE_402));
        }else if (ex instanceof BusinessException) {//自定义异常信息
            return ResponseEntity.status(ResultCode.CODE_500.getStatus()).body(new ErrorReponse(ex.getMessage()));
        }  else if (ex instanceof SystemException) {//系统定义异常信息
            SystemException systemException = (SystemException) ex;
            return ResponseEntity.status(ResultCode.CODE_500.getStatus()).body(new ErrorReponse(systemException.getResultCode()));
        }else if (ex instanceof HttpRequestMethodNotSupportedException) {
            return ResponseEntity.status(ResultCode.CODE_405.getStatus()).body(new ErrorReponse(ResultCode.CODE_405.getMsg()));
        } else {
            return ResponseEntity.status(ResultCode.CODE_500.getStatus()).body(new ErrorReponse(ResultCode.CODE_500.getMsg()));
        }
    }


    /**
     * 用来判断http请求是否是Ajax请求
     */
    private boolean isRequestAjax(HttpServletRequest request, HttpServletResponse response) {
        String requestedWith = request.getHeader("X-Requested-With");
        return (StringUtils.isNotEmpty(requestedWith) && StringUtils.equalsIgnoreCase(requestedWith, "XMLHttpRequest"));
    }
}
