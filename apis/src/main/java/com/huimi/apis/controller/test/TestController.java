package com.huimi.apis.controller.test;

import com.huimi.apis.controller.WebGenericController;
import com.huimi.common.entity.PageResult;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.mask.jackJson.DataMask;
import com.huimi.core.po.bizApkHistory.*;
import com.huimi.core.task.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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
    @DataMask
    public ResultEntity<Object> activeOverTask(@RequestParam(value = "device_id", required = false) String deviceUid) {
        try {
            Map<String,Integer> map = new HashMap<>();
            map.put("1",1);
            map.put("2",1);
            map.put("3",1);
            List<BizApkHistoryModel> bizApkHistoryModels = new ArrayList<>();
            BizApkHistoryModel bizApkHistoryModel = new BizApkHistoryModel();
            bizApkHistoryModel.setUserName("123123");
            bizApkHistoryModel.setRemake("1231231");
            bizApkHistoryModel.setDateTime(new Date());
            bizApkHistoryModel.setIntegerList(Arrays.asList(1,1));
            bizApkHistoryModel.setNumber(1);
            bizApkHistoryModel.setMap(map);

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
            for (int i = 0; i < 2; i++) {
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
    @ApiOperation(value = "测试返回单个对象")
    @RequestMapping("/test_result")
    public ResultEntity<ResultEntity<BizApkHistoryModel>> testResult() {
        try {
            List list = new ArrayList();
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

            list.add(bizApkHistoryModel);
            PageResult pageResult = new PageResult();
            pageResult.setList(list);
            pageResult.setAmount(10L);
            return new ResultEntity(pageResult,"");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }


    @ApiOperation(value = "测试返回单个对象")
    @RequestMapping("/dataMaskDome")
    public ResultEntity<List<BizApkHistoryModel>> dataMaskDome() {
        try {
            List list = new ArrayList();
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

            list.add(bizApkHistoryModel);
            PageResult pageResult = new PageResult();
            pageResult.setList(list);
            pageResult.setAmount(10L);
            return new ResultEntity(pageResult,"");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }


    @ApiOperation(value = "测试返回单个对象")
    @RequestMapping("/testSaveUser")
    public ResultEntity<UserModel> testSaveUser(UserModel userModel) {
        try {
            return ok(userModel,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }

}
