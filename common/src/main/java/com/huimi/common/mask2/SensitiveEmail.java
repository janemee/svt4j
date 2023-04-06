package com.huimi.common.mask2;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.huimi.common.mask.jackJson.DataMaskEnum;

import java.lang.annotation.*;

/**
 * 邮箱脱敏
 * @author yhq
 * @date 2021年9月7日 08点51分
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = com.huimi.common.mask2.SensitiveEmailClass.class,pattern = DataMaskEnum.EMAIL)
@JacksonAnnotationsInside
public @interface SensitiveEmail {

}
