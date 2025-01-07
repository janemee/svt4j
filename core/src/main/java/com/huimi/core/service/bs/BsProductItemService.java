package com.huimi.core.service.bs;

import com.huimi.core.po.bs.BsProductItemPo;
import com.huimi.core.po.bs.BsProductPo;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * 产品子项详情服务接口类
 */
public interface BsProductItemService extends GenericService<Integer, BsProductItemPo> {

    /**
     * 根据id更新删除标识
     * @param id
     */
    void updateDelFlagByIds(Integer id);

    List<BsProductPo> findByNameList(Integer productId, String productTitle);

    /**
     * 根据id更新产品子项发布状态
     * @param status
     * @param id
     */
    void updateStatus(int status, int id);

    /**
     * 根据产品id获取产品子项列表
     * @param productId
     * @return
     */
    List<BsProductItemPo> findListByProductId(Integer productId,Integer status);

}
