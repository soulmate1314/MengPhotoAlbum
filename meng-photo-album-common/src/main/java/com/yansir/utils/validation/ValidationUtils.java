package com.yansir.utils.validation;

import com.yansir.exception.BusinessException;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <pre>
 * 校验工具类
 * </pre>
 * 
 * Created by yangping on 2018/11/5.
 *
 * @author yangping
 * @since 1.0
 */
public class ValidationUtils {

    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * @param obj 校验对象
     * @Description: 校验整个对象
     * @return:
     * @Author: yangping
     * @Date: 2018/11/5
     */
    public static <T> void checkEntity(T obj) throws BusinessException {
        checkEntity(obj, Default.class);
    }

    public static <T> void checkEntity(T obj, Class group) throws BusinessException {
        ValidationResult result = validateEntity(obj, group);
        if (result.isHasErrors()) {
            throw new BusinessException(result.getErrorMsg().toString());
        }
    }

    /**
     * <pre>
     * 校验对象指定参数
     * </pre>
     * 
     * Created by yangping on 2018/11/5.
     *
     * @author yangping
     * @since 1.0
     */
    public static <T> void checkProperty(T obj, String...propertyNames) throws BusinessException {
        checkProperty(obj, Default.class, propertyNames);
    }

    public static <T> void checkProperty(T obj, Class group, String...propertyNames) throws BusinessException {
        for (String propertyName : propertyNames) {
            ValidationResult result = validateProperty(obj, propertyName, group);
            if (result.isHasErrors()) {
                throw new BusinessException(result.getErrorMsg().toString());
            }
        }
    }

    /**
     * <pre>
     * 校验对象除了指定参数
     * </pre>
     * 
     * Created by yangping on 2018/11/5.
     *
     * @author yangping
     * @since 1.0
     */
    public static <T> void checkPropertyExcludes(T obj, Class group, String...propertyNames) throws BusinessException {
        Set set = Stream.of(propertyNames).collect(Collectors.toSet());
        String[] names = FieldUtils.getAllFieldsList(obj.getClass()).stream().map(field -> field.getName())
            .filter(field -> !set.contains(field)).toArray(String[]::new);
        checkProperty(obj, group, names);
    }

    public static <T> void checkPropertyExcludes(T obj, String...propertyNames) throws BusinessException {
        checkPropertyExcludes(obj, Default.class, propertyNames);
    }

    private static <T> ValidationResult validateEntity(T obj) {
        return validateEntity(obj, Default.class);
    }

    private static <T> ValidationResult validateEntity(T obj, Class group) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = VALIDATOR.validate(obj, group, Default.class);
        // if( CollectionUtils.isNotEmpty(set) ){
        if (set != null && set.size() != 0) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }

    private static <T> ValidationResult validateProperty(T obj, String propertyName) {
        return validateProperty(obj, propertyName, Default.class);
    }

    private static <T> ValidationResult validateProperty(T obj, String propertyName, Class group) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = VALIDATOR.validateProperty(obj, propertyName, group, Default.class);
        if (set != null && set.size() != 0) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(propertyName, cv.getMessage());
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }
}
