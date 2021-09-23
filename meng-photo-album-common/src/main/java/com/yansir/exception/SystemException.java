package com.yansir.exception;

import com.yansir.restful.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SystemException extends RuntimeException{

    private ResultCode resultCode;

    public SystemException(ResultCode resultCode,Throwable cause) {
        super(resultCode.getMsg());
        this.resultCode=resultCode;
    }

    public SystemException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode=resultCode;
    }
}
