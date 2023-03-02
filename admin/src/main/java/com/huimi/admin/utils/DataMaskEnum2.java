package com.huimi.admin.utils;

import com.huimi.common.tools.StringUtil;

import java.util.function.Function;

public enum DataMaskEnum2 {

    /**
     * 名称脱敏
     */
    USERNAME(s -> StringUtil.getMaskToName(s))
    ,
    /**
     * Phone sensitive type.
     */
    PHONE(s -> StringUtil.getMaskStr(s,3,4))
    ,
    /**
     * Address sensitive type.
     */
    ADDRESS(s -> StringUtil.getMaskStr(s,3,6))

    ,
    /**
     * Address sensitive type.
     */
    EMAIL(s ->StringUtil.getMaskStr(s,3,6))
    ;

    /**
     * 成员变量  是一个接口类型
     */
    private Function<String, String> function;

    DataMaskEnum2(Function<String, String> function) {
        this.function = function;
    }

    public Function<String, String> function() {
        return this.function;
    }

}


