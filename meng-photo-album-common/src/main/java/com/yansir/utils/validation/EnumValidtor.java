package com.yansir.utils.validation;

import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidtor implements ConstraintValidator<EnumValidAnnotation, String> {

    private Class<?>[] cls; //枚举类

    public EnumValidtor() {
        System.out.println();
    }

    @Override
    public void initialize(EnumValidAnnotation constraintAnnotation) {
        cls = constraintAnnotation.target();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value != null && cls.length > 0) {
            for (Class<?> cl : cls) {
                if (cl.isEnum()) {
                    //枚举类验证
                    Object[] objs = cl.getEnumConstants();
                    for (Object obj : objs) {
                        Object code = ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(cl, "name"), obj);
                        if (value.equalsIgnoreCase(code.toString())) {
                            return true;
                        }
                    }
                }

            }
        } else {
            return true;
        }
        return false;
    }
}
