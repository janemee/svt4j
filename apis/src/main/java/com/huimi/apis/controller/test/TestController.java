package com.huimi.apis.controller.test;

import com.huimi.apis.controller.WebGenericController;
import com.huimi.common.entity.ResultEntity;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel2;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel3;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel4;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@RestController

@RequestMapping("/api/v1/test")
@Api(tags = "任务需求")
public class TestController extends WebGenericController<Integer, Task> {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);


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

            BizApkHistoryModel3 bizApkHistoryModel3 = new BizApkHistoryModel3();
            bizApkHistoryModel3.setName("123123");
            bizApkHistoryModel3.setEmail("1231231@123.com");

            BizApkHistoryModel4 bizApkHistoryModel4 = new BizApkHistoryModel4();
            bizApkHistoryModel4.setName("123123");
            bizApkHistoryModel4.setEmail("1231231@123.com");

            bizApkHistoryModel2.setList(Arrays.asList(bizApkHistoryModel3));

            bizApkHistoryModel3.setBizApkHistoryModel4(bizApkHistoryModel4);
            bizApkHistoryModel3.setList(Arrays.asList(bizApkHistoryModel4));

            bizApkHistoryModel2.setBizApkHistoryModel3(bizApkHistoryModel3);
            bizApkHistoryModel2.setList(Arrays.asList(bizApkHistoryModel3));

            bizApkHistoryModel.setBizApkHistoryModel2(bizApkHistoryModel2);
            bizApkHistoryModel.setList(Arrays.asList(bizApkHistoryModel2));
            for (int i = 0; i < 1000; i++) {
                bizApkHistoryModels.add(bizApkHistoryModel);
            }
            return ok(bizApkHistoryModels, "success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }


    @ApiOperation(value = "测试返回单个对象")
    @RequestMapping("/test_result_one")
    public ResultEntity<Object> testResultToOneObject() {
        try {
            BizApkHistoryModel bizApkHistoryModel = new BizApkHistoryModel();
            bizApkHistoryModel.setUserName("123123");
            bizApkHistoryModel.setRemake("1231231");
            bizApkHistoryModel.setDateTime(new Date());

            BizApkHistoryModel2 bizApkHistoryModel2 = new BizApkHistoryModel2();
            bizApkHistoryModel2.setName("123123");
            bizApkHistoryModel2.setEmail("1231231@123.com");

            BizApkHistoryModel3 bizApkHistoryModel3 = new BizApkHistoryModel3();
            bizApkHistoryModel3.setName("123123");
            bizApkHistoryModel3.setEmail("1231231@123.com");

            BizApkHistoryModel4 bizApkHistoryModel4 = new BizApkHistoryModel4();
            bizApkHistoryModel4.setName("123123");
            bizApkHistoryModel4.setEmail("1231231@123.com");

            bizApkHistoryModel2.setList(Arrays.asList(bizApkHistoryModel3));

            bizApkHistoryModel3.setBizApkHistoryModel4(bizApkHistoryModel4);
            bizApkHistoryModel3.setList(Arrays.asList(bizApkHistoryModel4));

            bizApkHistoryModel2.setBizApkHistoryModel3(bizApkHistoryModel3);
            bizApkHistoryModel2.setList(Arrays.asList(bizApkHistoryModel3));

            bizApkHistoryModel.setBizApkHistoryModel2(bizApkHistoryModel2);
            bizApkHistoryModel.setList(Arrays.asList(bizApkHistoryModel2));

            return ok(bizApkHistoryModel, "success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }

}
