package com.huimi.core.service.common;

import java.util.Locale;

import lombok.NonNull;

/**
 * @author dyroneteng on 17/5/11.
 */
public interface I18nService {
    /**
     * 根据国际化定义的key获取对应的message
     *
     * @param code
     * @return
     */
    String getMessage(String code);

    /**
     * 根据国际化定义的key和对象组成对应的message
     *
     * @param code
     * @param objects
     * @return
     */
    String getMessage(@NonNull String code, Object[] objects);

    /**
     * 根据国际化定义的key和对象组成对应的message，如果没有内容，则返回默认消息
     *
     * @param code
     * @param objects
     * @param defaultMessage
     * @return
     */
    String getMessage(@NonNull String code, Object[] objects, String defaultMessage);

    /**
     * Get runtime system locale
     *
     * @return
     */
    Locale getLocale();

}