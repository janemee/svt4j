package com.huimi.common.mask3;

/**
 * @author Jiazngxiaobai
 */
public interface DataMaskOperation {
    /**
     * 脱敏方法
     *
     * @param str
     * @param maskChar
     * @return
     */
    String mask(String str, String maskChar);
}
