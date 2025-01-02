package com.huimi.core.service.bs.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.bs.BsApplicationAreaItemMapper;
import com.huimi.core.po.bs.BsApplicationAreaItemPo;
import com.huimi.core.po.bs.BsApplicationAreaPo;
import com.huimi.core.service.bs.BsApplicationAreaItemService;
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
public class BsApplicationAreaItemServiceImpl implements BsApplicationAreaItemService {

    @Resource
    private BsApplicationAreaItemMapper bsApplicationAreaItemMapper;


    @Override
    public GenericMapper<BsApplicationAreaItemPo, Integer> _getMapper() {
        return bsApplicationAreaItemMapper;
    }


    @Override
    public List<BsApplicationAreaItemPo> findApplicationItemsByApplicationAreaId(Integer applicationAreaId) {
        return bsApplicationAreaItemMapper.findApplicationItemsByApplicationAreaId(applicationAreaId);
    }

    @Override
    public List<BsApplicationAreaPo> findByStateList(Integer applicationAreaId, String applicationTitle) {
        return bsApplicationAreaItemMapper.findByStateList(applicationAreaId, applicationTitle);
    }

    @Override
    public void updateDelFlagByIds(Integer parseLong) {
        bsApplicationAreaItemMapper.updateDelFlagById(parseLong);
    }
}
