package com.huimi.core.constant;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

/**
 * 枚举配置类
 *
 * @author Jiazngxiaobai
 */
@Data
@Serialization
public class EnumDTO {
    private String code;
    private Integer value;

    public EnumDTO(String code, Integer value) {
        this.code = code;
        this.value = value;
    }
    public EnumDTO(){

    }
}
