package com.huimi.core.service.bs.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.bizApkHistory.BizApkHistoryMapper;
import com.huimi.core.mapper.bs.BsApplicationAreaMapper;
import com.huimi.core.po.bizApkHistory.BizApkHistory;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel;
import com.huimi.core.po.bs.BsApplicationAreaPo;
import com.huimi.core.service.apkHistory.BizApkHistoryService;
import com.huimi.core.service.bs.BsApplicationAreaService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 应用领域业务实现类
 *
 * @author Jiazngxiaobai
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class BsApplicationAreaServiceImpl implements BsApplicationAreaService {

    @Resource
    private BsApplicationAreaMapper bsApplicationAreaMapper;


    @Override
    public GenericMapper<BsApplicationAreaPo, Integer> _getMapper() {
        return bsApplicationAreaMapper;
    }


    @Override
    public List<BsApplicationAreaPo> findByStateList(int state) {
        return bsApplicationAreaMapper.findByStateList(state);
    }

    @Override
    public List<BsApplicationAreaPo> findByNameList(String name) {
        return bsApplicationAreaMapper.findByNameList(name);
    }

    @Override
    public BsApplicationAreaPo findByStateOne(int value) {
        return bsApplicationAreaMapper.findByStateOne(value);
    }

    @Override
    public void updateDelFlagByIds(Long id) {
        bsApplicationAreaMapper.updateDelFlagById(id);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        bsApplicationAreaMapper.updateStatusById(id, status);
    }

}
