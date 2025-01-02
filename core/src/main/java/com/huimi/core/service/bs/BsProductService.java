package com.huimi.core.service.bs;

import com.huimi.core.po.bs.BsProductPo;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * 产品服务接口类
 */
public interface BsProductService extends GenericService<Integer, BsProductPo> {

    /**
     * 根据id更新删除标识
     * @param id
     */
    void updateDelFlagByIds(Long id);

    /**
     * 查询产品名称是否存在
     * @param productTitle
     * @return
     */
    List<BsProductPo> findByNameList(String productTitle);

    /**
     * 更新产品的发布状态
     * @param parseInt
     * @param value
     */
    void updateStatus(int parseInt, int value);
}
