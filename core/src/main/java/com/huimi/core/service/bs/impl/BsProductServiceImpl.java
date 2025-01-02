package com.huimi.core.service.bs.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.bs.BsProductMapper;
import com.huimi.core.po.bs.BsProductPo;
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
public class BsProductServiceImpl implements BsProductService {

    @Resource
    private BsProductMapper bsProductMapper;


    @Override
    public GenericMapper<BsProductPo, Integer> _getMapper() {
        return bsProductMapper;
    }

    @Override
    public void updateDelFlagByIds(Long id) {
        bsProductMapper.updateDelFlagById(id);
    }

    @Override
    public List<BsProductPo> findByNameList(String productTitle) {
        return bsProductMapper.findByNameList(productTitle);
    }

    @Override
    public void updateStatus(int status, int id) {
        bsProductMapper.updateStatusById(status, id);
    }

}
