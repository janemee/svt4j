package com.huimi.core.service.bs.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.bs.BsProductItemMapper;
import com.huimi.core.mapper.bs.BsProductMapper;
import com.huimi.core.po.bs.BsProductItemPo;
import com.huimi.core.po.bs.BsProductPo;
import com.huimi.core.service.bs.BsProductItemService;
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
public class BsProductItemServiceImpl implements BsProductItemService {

    @Resource
    private BsProductItemMapper bsProductItemMapper;


    @Override
    public GenericMapper<BsProductItemPo, Integer> _getMapper() {
        return bsProductItemMapper;
    }

    @Override
    public void updateDelFlagByIds(Integer id) {
        bsProductItemMapper.updateDelFlagById(id);
    }

    @Override
    public List<BsProductPo> findByNameList(Integer productId, String productTitle) {
        return bsProductItemMapper.findByNameList(productId, productTitle);
    }

    @Override
    public void updateStatus(int status, int id) {
        bsProductItemMapper.updateStatusById(status,id);
    }

    @Override
    public List<BsProductItemPo> findListByProductId(Integer productId,Integer status) {
        return bsProductItemMapper.findListByProductId(productId,status);
    }

}
