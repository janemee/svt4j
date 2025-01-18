package com.huimi.apis.controller.v1;

import com.huimi.apis.controller.WebGenericController;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.tools.StringUtil;
import com.huimi.core.model.bs.UserMessageModel;
import com.huimi.core.po.bs.BsMessageBoardPo;
import com.huimi.core.service.bs.BsMessageBoardService;
import com.huimi.core.task.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController

@RequestMapping("/v1/message")
@Api(tags = "客户留言模块")
public class MessageController extends WebGenericController<Integer, BsMessageBoardPo> {
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private BsMessageBoardService bsMessageBoardService;


    /**
     * 基础配置参数接口
     *
     * @return
     */
    @ApiOperation(value = "用户留言")
    @PostMapping("/save")
    public ResultEntity save(@RequestBody UserMessageModel userMessageModel) {
        try {
            ResultEntity checkParameter = checkParameter(userMessageModel);
            if (checkParameter.getCode() != ResultEntity.SUCCESS) {
                return checkParameter;
            }
            boolean flag = bsMessageBoardService.sava(userMessageModel);
            return flag ? ok() : fail("未留言成功，请稍后重试");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }

    /**
     * 参数校验
     *
     * @param userMessageModel 参数
     * @return 校验结果
     */
    private ResultEntity checkParameter(UserMessageModel userMessageModel) {
        if (Objects.isNull(userMessageModel)) {
            return fail("请填写正确的信息");
        }
        if (StringUtil.isBlank(userMessageModel.getUsername())) {
            return fail("请填写姓名");
        }
        if (StringUtil.isBlank(userMessageModel.getPhone())) {
            return fail("请填写手机号码");
        }
        if (StringUtil.isBlank(userMessageModel.getAddress())) {
            return fail("请填写地址");
        }
        if (StringUtil.isBlank(userMessageModel.getContent())) {
            return fail("请填写留言信息");
        }
        return ok();
    }
}
