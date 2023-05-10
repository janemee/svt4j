package com.huimi.common.entity;

import lombok.Data;

import java.util.Collection;

/**
 * @author Jiazngxiaobai
 */
@Data
public class PageResult<T> {

    private Collection<T> list;

    private Long amount;

}
