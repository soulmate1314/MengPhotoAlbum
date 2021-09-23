package com.yansir.exception;

/**
* @author YANSIR
* @Description:自定义异常信息
* @date 2020/7/10 14:42
*/
public class BusinessException extends RuntimeException {

    private String msg;

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }

}
