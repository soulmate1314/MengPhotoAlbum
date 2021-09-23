package com.yansir.exception;


import com.yansir.restful.ResultCode;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException() {
        super(ResultCode.CODE_404.getMsg());
    }
}
