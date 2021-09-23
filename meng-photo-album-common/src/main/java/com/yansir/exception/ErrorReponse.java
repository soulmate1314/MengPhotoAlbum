package com.yansir.exception;

import com.yansir.restful.ResultCode;
import com.yansir.utils.ServletUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

/**
 * @author YANSIR
 * @Description:全局异常返回客户端异常信息
 * @date 2020/7/9 14:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorReponse {
    private int code;
    private int status;
    private String message;
    private String path;
    private Instant timestamp;




    public ErrorReponse(String message) {
        HttpServletRequest request = ServletUtils.getRequest();
        this.code = ResultCode.CODE_500.getCode();
        this.status = ResultCode.CODE_500.getStatus().value();
        this.message = message;
        this.path = request.getRequestURI();
        this.timestamp = Instant.now();
    }


    public ErrorReponse(ResultCode resultCode) {
        HttpServletRequest request = ServletUtils.getRequest();
        this.code = resultCode.getCode();
        this.status = resultCode.getStatus().value();
        this.message = resultCode.getMsg();
        this.path = request.getRequestURI();
        this.timestamp = Instant.now();
    }


}
