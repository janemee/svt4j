package com.huimi.core.service.bs;

import com.huimi.core.model.bs.UserMessageModel;
import com.huimi.core.po.bs.BsMessageBoardPo;
import com.huimi.core.po.bs.BsNoticePo;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * 产品服务接口类
 */
public interface BsMessageBoardService extends GenericService<Integer, BsMessageBoardPo> {

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
    List<BsMessageBoardPo> findByTitleList(String title);

    /**
     * 更新客户留言状态
     *
     * @param id id
     * @param status 状态
     */
    void updateStatus(int id, int status);

    /**
     * 添加留言信息
     * @param userMessageModel
     * @return
     */
    boolean sava(UserMessageModel userMessageModel);

}
