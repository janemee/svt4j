package com.huimi.apis.controller.test;

import com.huimi.apis.controller.WebGenericController;
import com.huimi.common.entity.ResultEntity;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel2;
import com.huimi.core.po.equipment.EquipmentGroup;
import com.huimi.core.po.equipment.EquipmentModel;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.task.TaskService;
import com.huimi.core.task.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController

@RequestMapping("/api/v1/test")
@Api(tags = "任务需求")
public class TestController extends WebGenericController<Integer, Task> {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private TaskService taskService;
    @Resource
    private RedisService redisService;

    /**
     * 设备主动结束任务（客户端主动调用）
     *
     * @param deviceUid 设备uid
     */
    @ApiOperation(value = "设备主动结束任务")
    @RequestMapping("/activeOverTask")
    public ResultEntity<Object> activeOverTask(@RequestParam(value = "device_id", required = false) String deviceUid) {
        try {
            List<BizApkHistoryModel> bizApkHistoryModels = new ArrayList<>();
            BizApkHistoryModel bizApkHistoryModel = new BizApkHistoryModel();
            bizApkHistoryModel.setUserName("123123");
            bizApkHistoryModel.setRemake("1231231");
            bizApkHistoryModel.setDateTime(new Date());
            BizApkHistoryModel2 bizApkHistoryModel2 = new BizApkHistoryModel2();
            bizApkHistoryModel2.setName("123123");
            bizApkHistoryModel2.setEmail("1231231@123.com");

            bizApkHistoryModel.setBizApkHistoryModel2(bizApkHistoryModel2);
            bizApkHistoryModel.setBizApkHistoryModel3(bizApkHistoryModel2);
            bizApkHistoryModel.setBizApkHistoryModel4(bizApkHistoryModel2);
            bizApkHistoryModels.add(bizApkHistoryModel);
            return ok(bizApkHistoryModels, "success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }

}
