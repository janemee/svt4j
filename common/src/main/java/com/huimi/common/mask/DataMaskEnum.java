package com.huimi.common.mask;

import com.huimi.common.tools.StringUtil;

import java.util.function.Function;

public enum DataMaskEnum {

    /**
     * 名称脱敏
     */
    USERNAME(s -> MaskUtils.getMaskToName(s))
    ,
    /**
     * Phone sensitive type.
     */
    PHONE(s -> MaskUtils.getMaskStr(s,3,4))
    ,
    /**
     * Address sensitive type.
     */
    ADDRESS(s -> MaskUtils.getMaskStr(s,3,6))

    ,
    /**
     * Address sensitive type.
     */
    EMAIL(s ->MaskUtils.getMaskToEmail(s))
    ;

    /**
     * 成员变量  是一个接口类型
     */
    private Function<String, String> function;

    DataMaskEnum(Function<String, String> function) {
        this.function = function;
    }

    public Function<String, String> function() {
        return this.function;
    }

}


