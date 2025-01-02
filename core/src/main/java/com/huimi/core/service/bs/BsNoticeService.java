package com.huimi.core.service.bs;

import com.huimi.core.po.bs.BsNoticePo;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * 产品服务接口类
 */
public interface BsNoticeService extends GenericService<Integer, BsNoticePo> {

    /**
     * 根据id更新删除标识
     *
     * @param id
     */
    void updateDelFlagByIds(Long id);

    /**
     * 查询产品名称是否存在
     *
     * @param title 标题
     * @return
     */
    List<BsNoticePo> findByTitleList(String title);

    /**
     * 更新产品的发布状态
     *
     * @param parseInt
     * @param value
     */
    void updateStatus(int parseInt, int value);
}
