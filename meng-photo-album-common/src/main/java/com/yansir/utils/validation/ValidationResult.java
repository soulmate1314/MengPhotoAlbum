package com.yansir.utils.validation;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 校验结果
 *
 * @author yangping
 */
@Setter
@Getter
public class ValidationResult {

    /**
     * 校验结果是否有错
     */
    private boolean hasErrors;

    /**
     * 校验错误信息
     */
    private Map<String, String> errorMsg;
}
