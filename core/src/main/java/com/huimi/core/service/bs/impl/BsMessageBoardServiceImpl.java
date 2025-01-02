package com.huimi.core.service.bs.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.mapper.bs.BsMessageBoardMapper;
import com.huimi.core.po.bs.BsMessageBoardPo;
import com.huimi.core.po.bs.BsNoticePo;
import com.huimi.core.service.bs.BsMessageBoardService;
import com.huimi.core.service.bs.BsNoticeService;
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
public class BsMessageBoardServiceImpl implements BsMessageBoardService {

    @Resource
    private BsMessageBoardMapper bsMessageBoardMapper;


    @Override
    public GenericMapper<BsMessageBoardPo, Integer> _getMapper() {
        return bsMessageBoardMapper;
    }

    @Override
    public void updateDelFlagByIds(Long id) {
        bsMessageBoardMapper.updateDelFlagById(id);
    }

    @Override
    public List<BsMessageBoardPo> findByTitleList(String productTitle) {
        return bsMessageBoardMapper.findByTitleList(productTitle);
    }

    @Override
    public void updateStatus(int id, int status) {
        bsMessageBoardMapper.updateStatusById(id, status);
    }

}
