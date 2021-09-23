package com.yansir.restful;

/**
 * @author YANSIR
 * @Description: http接口返回结果构造器
 * @date 2020/7/6 9:43
 */
public class ResultGenerator {

    /**
     * 无参数返回成功状态
     *
     * @return
     */
    public static Result genSuccessResult() {
        return Result.builder()
                .code(ResultCode.CODE_200.getCode())
                .msg(ResultCode.CODE_200.getMsg())
                .build();
    }

    /**
     * 有参数返回成功状态
     *
     * @param data 计算结果对象
     * @return
     */
    public static Result genSuccessResult(Object data) {
        return Result.builder()
                .code(ResultCode.CODE_200.getCode())
                .msg(ResultCode.CODE_200.getMsg())
                .data(data)
                .build();

    }

    /**
     * 有参数返回失败状态
     *
     * @param message 失败原因
     * @param code    状态码
     * @return
     */
    public static Result genFailResult(String message, int code) {
        return Result.builder()
                .code(code)
                .msg(message)
                .build();
    }
}
