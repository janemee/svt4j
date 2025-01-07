package com.huimi.common.entity;

import lombok.Data;

import java.util.Collection;

/**
 * @author Jiazngxiaobai
 */
@Data
public class PageResult<T> {

    /**
     * 数据列表
     */
    private Collection<T> list;

    /**
     * 总条数
     */
    private Integer totalCount;


}
