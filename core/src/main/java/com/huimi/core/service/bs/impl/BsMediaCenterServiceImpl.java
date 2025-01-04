package com.huimi.core.service.bs.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.bs.BsMediaCenterMapper;
import com.huimi.core.po.bs.BsMediaCenterPo;
import com.huimi.core.service.bs.BsMediaCenterService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户留言服务类
 *
 * @author Jiazngxiaobai
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class BsMediaCenterServiceImpl implements BsMediaCenterService {

    @Resource
    private BsMediaCenterMapper bsMediaCenterMapper;


    @Override
    public GenericMapper<BsMediaCenterPo, Integer> _getMapper() {
        return bsMediaCenterMapper;
    }

    @Override
    public void updateDelFlagByIds(Long id) {
        bsMediaCenterMapper.updateDelFlagById(id);
    }

    @Override
    public List<BsMediaCenterPo> findByNameList(String productTitle) {
        return bsMediaCenterMapper.findByNameList(productTitle);
    }

    @Override
    public void updateStatus(int id, int status) {
        bsMediaCenterMapper.updateStatusById(status, id);
    }

}
