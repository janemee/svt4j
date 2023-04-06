package com.huimi.common.mask3;

import org.springframework.util.StringUtils;

/**
 * @author Jiazngxiaobai
 */

public enum  DataMaskEnum {

    // 不脱敏
    NO_MASK((str, maskChar) -> str)
    ,
    // 全脱敏
    ALL_MASK((str, maskChar) ->{
        if (StringUtils.hasLength(str)) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                builder.append(StringUtils.hasLength(maskChar) ? maskChar : "*");
            }
            return builder.toString();
        }
        return str;
    })
    ;

    // 成员变量  是一个接口类型
    private DataMaskOperation operation;

    DataMaskEnum(DataMaskOperation operation) {
        this.operation = operation;
    }

    public DataMaskOperation operation() {
        return this.operation;
    }
}
