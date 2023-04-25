package com.huimi.core.service.common.impl;


//import com.alibaba.aone.framework.context.AoneContext;

import com.huimi.core.service.common.I18nService;
import lombok.NonNull;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * @author van.yzt
 * @date 2017/08/02
 */
public class I18nServiceImpl extends ResourceBundleMessageSource implements I18nService {
    @Value("${i18n:zh_CN}")
    private String i18n;

    private Locale locale = null;

    @PostConstruct
    public void init() {
        locale = LocaleUtils.toLocale(i18n);
    }

    /**
     * 根据请求上下文获取locale
     *
     * @return
     */
    @Override
    public Locale getLocale() {
        final Locale userLocale = null;
        return userLocale == null ? locale : userLocale;
    }

    /**
     * 根据国际化定义的key或取对应的message
     *
     * @param code
     * @return
     */
    @Override
    public String getMessage(@NonNull String code) {
        return super.getMessage(code, null, getLocale());
    }

    @Override
    public String getMessage(@NonNull String code, Object[] objects) {
        return super.getMessage(code, objects, getLocale());
    }

    @Override
    public String getMessage(@NonNull String code, Object[] objects, @NonNull String defaultMessage) {
        return super.getMessage(code, objects, defaultMessage, getLocale());
    }
}


