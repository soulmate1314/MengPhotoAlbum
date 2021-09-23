package com.yansir.exception;


import com.yansir.restful.ResultCode;

public class TokenWrongException extends BusinessException{

    public TokenWrongException() {
        super(ResultCode.CODE_402.getMsg());
    }
}
