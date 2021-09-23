package com.yansir.restful;

import org.springframework.http.HttpStatus;

/**
 * @author YANSIR
 * @Description: http接口返回的状态码标识
 * @date 2020/7/6 9:42
 */
public enum ResultCode {
    CODE_200(1001, HttpStatus.OK, "成功"),
    CODE_404(1002, HttpStatus.NOT_FOUND, "资源未找到"),
    CODE_405(1003, HttpStatus.METHOD_NOT_ALLOWED, "请求类型错误"),
    CODE_500(1004, HttpStatus.INTERNAL_SERVER_ERROR, "请求处理失败"),
    CODE_402(1005, HttpStatus.PAYMENT_REQUIRED, "token校验失败");

    private final int code;
    private final HttpStatus status;
    private final String msg;

    ResultCode(int code, HttpStatus status, String msg) {
        this.code = code;
        this.status = status;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
