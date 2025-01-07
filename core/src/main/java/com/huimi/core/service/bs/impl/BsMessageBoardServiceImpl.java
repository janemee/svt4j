package com.huimi.core.service.bs.impl;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.mapper.bs.BsMessageBoardMapper;
import com.huimi.core.model.bs.UserMessageModel;
import com.huimi.core.po.bs.BsMessageBoardPo;
import com.huimi.core.service.bs.BsMessageBoardService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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

    @Override
    public boolean sava(UserMessageModel userMessageModel) {
        BsMessageBoardPo messageBoard = new BsMessageBoardPo();
        messageBoard.setName(userMessageModel.getUsername());
        messageBoard.setEmail(userMessageModel.getEmail());
        messageBoard.setPhone(userMessageModel.getPhone());
        messageBoard.setAddress(userMessageModel.getAddress());
        messageBoard.setContent(userMessageModel.getContent());
        messageBoard.setStatus(EnumConstants.MessageBoardStatusEunm.PROCESSED.value);
        messageBoard.setCreateTime(new Date());
        messageBoard.setCreator("user");
        return insert(messageBoard) > 0;

    }

}
