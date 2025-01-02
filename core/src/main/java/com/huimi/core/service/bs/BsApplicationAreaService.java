package com.huimi.core.service.bs;

import com.huimi.core.po.bs.BsApplicationAreaPo;
import com.huimi.core.service.base.GenericService;

import java.util.List;

/**
 * 应用领域服务接口类
 */
public interface BsApplicationAreaService extends GenericService<Integer, BsApplicationAreaPo> {

    /**
     * 根据状态查询启用的文件列表
     *
     * @param state
     * @return
     */
    List<BsApplicationAreaPo> findByStateList(int state);

    /**
     * 根据文件名称查询文件列表
     *
     * @param name
     * @return
     */
    List<BsApplicationAreaPo> findByNameList(String name);

    BsApplicationAreaPo findByStateOne(int value);


    /**
     * 根据id更新删除标识
     * @param id
     */
    void updateDelFlagByIds(Long id);

    /**
     * 更新发布状态
     * @param parseLong
     */
    void updateStatus(Integer parseLong,Integer status);
}
