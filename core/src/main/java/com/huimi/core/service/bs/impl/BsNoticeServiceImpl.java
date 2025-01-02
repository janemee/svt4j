package com.huimi.core.service.bs.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.bs.BsNoticeMapper;
import com.huimi.core.mapper.bs.BsProductMapper;
import com.huimi.core.po.bs.BsNoticePo;
import com.huimi.core.po.bs.BsProductPo;
import com.huimi.core.service.bs.BsNoticeService;
import com.huimi.core.service.bs.BsProductService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 产品实现类
 *
 * @author Jiazngxiaobai
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class BsNoticeServiceImpl implements BsNoticeService {

    @Resource
    private BsNoticeMapper bsNoticeMapper;


    @Override
    public GenericMapper<BsNoticePo, Integer> _getMapper() {
        return bsNoticeMapper;
    }

    @Override
    public void updateDelFlagByIds(Long id) {
        bsNoticeMapper.updateDelFlagById(id);
    }

    @Override
    public List<BsNoticePo> findByTitleList(String productTitle) {
        return bsNoticeMapper.findByTitleList(productTitle);
    }

    @Override
    public void updateStatus(int id, int status) {
        bsNoticeMapper.updateStatusById(id, status);
    }

}
