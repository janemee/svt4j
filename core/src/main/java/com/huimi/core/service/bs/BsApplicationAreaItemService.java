package com.huimi.core.service.bs;

import com.huimi.core.po.bs.BsApplicationAreaItemPo;
import com.huimi.core.po.bs.BsApplicationAreaPo;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * 应用领域子项服务接口类
 *
 * @author Jiazngxiaobai
 */
public interface BsApplicationAreaItemService extends GenericService<Integer, BsApplicationAreaItemPo> {

    /**
     * 根据领域id获取领域子项列表
     */
    List<BsApplicationAreaItemPo> findApplicationItemsByApplicationAreaId(Integer applicationAreaId);

    /**
     * 根据父id和名称查询是否存在
     * @param applicationAreaId
     * @param applicationTitle
     * @return
     */
    List<BsApplicationAreaPo> findByStateList(Integer applicationAreaId, String applicationTitle);

    /**
     * 逻辑删除应用领域详情子项
     * @param parseLong
     */
    void updateDelFlagByIds(Integer parseLong);
}
