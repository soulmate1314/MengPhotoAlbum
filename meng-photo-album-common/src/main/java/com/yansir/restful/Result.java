package com.yansir.restful;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
* @author YANSIR
* @Description: Http接口结果返回的对象
* @date 2020/11/20 15:22
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable{

    private static final long serialVersionUID = 1L;
    private String msg;
    private Object data;
    private int code;
}
