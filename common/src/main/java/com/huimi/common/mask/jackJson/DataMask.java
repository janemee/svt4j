package com.huimi.common.mask.jackJson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jiazngxiaobai
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataMask {
    DataMaskEnum function() default DataMaskEnum.EMAIL;
}
