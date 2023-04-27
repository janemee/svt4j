package com.huimi.apis.config;

import org.springframework.core.Ordered;

/**
 * @author Jiazngxiaobai
 */
public class InterceptorOrder implements Ordered {


    public static final int MASK_VALUE = 1;

    @Override
    public int getOrder() {
        return InterceptorOrder.MASK_VALUE;
    }
}
