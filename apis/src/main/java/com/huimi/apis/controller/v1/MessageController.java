package com.huimi.apis.controller.v1;

import com.huimi.apis.controller.WebGenericController;
import com.huimi.common.entity.ResultEntity;
import com.huimi.core.model.bs.UserMessageModel;
import com.huimi.core.service.bs.BsMessageBoardService;
import com.huimi.core.task.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController

@RequestMapping("/v1/message")
@Api(tags = "客户留言模块")
public class MessageController extends WebGenericController<Integer, Task> {
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private BsMessageBoardService bsMessageBoardService;


    /**
     * 基础配置参数接口
     *
     * @return
     */
    @ApiOperation(value = "基础配置信息")
    @RequestMapping("/save")
    public ResultEntity<Object> save(@RequestBody UserMessageModel userMessageModel) {
        try {
            boolean flag = bsMessageBoardService.sava(userMessageModel);
            return flag ? ok() : fail();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }
}
