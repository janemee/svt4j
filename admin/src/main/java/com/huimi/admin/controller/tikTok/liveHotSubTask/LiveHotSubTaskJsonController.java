package com.huimi.admin.controller.tikTok.liveHotSubTask;

import com.alibaba.fastjson.JSON;
import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.baseMapper.GenericPo;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.DateUtils;
import com.huimi.common.utils.StringUtils;
import com.huimi.common.utils.ToolDateTime;
import com.huimi.core.constant.Constants;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.entity.LiveHotSubTask;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.model.LiveHotSubTask.LiveHotSubTaskModel;
import com.huimi.core.po.comment.CommentTemplate;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.po.equipment.EquipmentModel;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.comment.CommentTemplateService;
import com.huimi.core.service.equipment.EquipmentGroupService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.liveHotSubTask.LiveHotSubTaskService;
import com.huimi.core.service.system.AdminService;
import com.huimi.core.service.task.TaskDetailsService;
import com.huimi.core.service.task.TaskService;
import com.huimi.core.task.*;
import com.huimi.core.util.TaskUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

import static com.huimi.common.entity.ResultEntity.fail;
import static com.huimi.common.entity.ResultEntity.success;

/**
 * 超级热度子任务控制类
 */
@SuppressWarnings("all")
@RestController
@RequestMapping(BaseController.BASE_URI)
public class LiveHotSubTaskJsonController extends BaseController {

    @Resource
    private TaskService taskService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private EquipmentGroupService equipmentGroupService;
    @Resource
    private TaskDetailsService taskDetailsService;
    @Resource
    private LiveHotSubTaskService liveHotSubTaskService;
    @Resource
    private AdminService adminService;
    @Resource
    private CommentTemplateService commentTemplateService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveHotSubTaskJsonController.class);

    @ResponseBody
    @RequestMapping("task/json/subHotTaskList")
    public DtGrid listJson(HttpServletRequest request, int rows, int page, Integer id) throws Exception {
        DtGrid dtGrid = new DtGrid();
        Integer pageSize = rows == 0 ? 1 : rows;
        Integer pageNumber = page == 0 ? 1 : page;
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String nid = request.getParameter("search_val");

        whereSql.append(" and t.task_detail_id = ").append(id);
        if (StringUtils.isNotBlank(nid)) {
            whereSql.append(" and t.id = ").append(nid);
        }
        String name = request.getParameter("name");
        if (StringUtils.isNotBlank(name)) {
            whereSql.append(" and name like '%").append(name).append("%'");
        }
        String type = request.getParameter("type");
        if (StringUtils.isNotBlank(type) && !"99".equals(type)) {
            whereSql.append(" and type like '%").append(type).append("%'");
        }
        String state = request.getParameter("state");
        if (StringUtils.isNotBlank(state) && !"99".equals(state)) {
            whereSql.append(" and state like '%").append(state).append("%'");
        }

        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        dtGrid = liveHotSubTaskService.getDtGridList(dtGrid);

        return dtGrid;
    }


    /**
     * 添加超级热度
     */
    @RequestMapping(value = "/task/superHeat/json/add", method = RequestMethod.POST)
    public ResultEntity addSuperHeat(String deviceStyle, String[] equipments, String[] equipmentGroups, Task task, String taskStartTime) throws BussinessException {
        try {
            //检查直播链接是否正确
            if (StringUtil.isNotBlank(task.getLiveInContent()) && StringUtil.isBlank(TaskUtil.saveLiveInContent(task.getLiveInContent()))) {
                return fail("直播链接信息有误，请检查后重试");
            }
            long s1 = System.currentTimeMillis();
            super.checkDevice(deviceStyle, equipmentGroups, equipments);
            //获得用户id
            //定时开始时间不能小于当前时间
            if (StringUtil.isNotBlank(taskStartTime)) {
                Date date = DateUtils.getDateByFullDateStr(taskStartTime);
                assert date != null;
                if (date.before(new Date())) {
                    throw new BussinessException("定时日期开始不能小于当前时间");
                }
            }
            ArrayList<Equipment> allEquipments = new ArrayList<>();
            if (equipments != null) {
                for (String equipmentId : equipments) {
                    Equipment e = equipmentService.selectByPrimaryKey(Integer.parseInt(equipmentId));
                    allEquipments.add(e);
                }
            }
            //添加分组下所有在线的设备
            Integer adminId = AdminSessionHelper.getAdminId();
            Admin admin = adminService.selectByPrimaryKey(adminId);
            if (equipmentGroups != null) {
                ArrayList<Long> groupsId = new ArrayList<>();
                for (String equipmentGroup : equipmentGroups) {
                    groupsId.add(Long.parseLong(equipmentGroup));
                    if (StringUtils.isNotBlank(admin.getParentId())) {
                        allEquipments = equipmentService.findAllStateByGroup(groupsId, adminId);
                    } else {
                        List<String> roleAdmin = adminService.findRoleAdmin("1");
                        allEquipments = equipmentService.findAllStateByGroupAgent(groupsId, roleAdmin);
                    }
                }
            }
            //添加任务
            task.setTaskType(EnumConstants.TaskType.LIVE_HOT.value);
            task.setDelFlag(GenericPo.DELFLAG.NO.code);
            task.setIsLiveHot(1);
            task.setSysAdminId(adminId);
            String taskRunCode = task.getTaskRunCode();
            Integer taskExpectRunning = task.getTaskExpectRunning();
            task.setTaskExpectRunning(taskExpectRunning);

            boolean sendFlag = taskRunCode.equals(EnumConstants.TaskRunCode.RANDOM.code) || taskRunCode.equals(EnumConstants.TaskRunCode.NOW.code);
            if (sendFlag) {
                task.setTaskStartTime(new Date());
                Calendar nowTime = Calendar.getInstance();
                nowTime.add(Calendar.MINUTE, taskExpectRunning);
                task.setTaskEndTime(nowTime.getTime());
            } else {
                task.setTaskStartTime(ToolDateTime.parse(taskStartTime, "yyyy-MM-dd HH:mm:ss"));
                Calendar instance = Calendar.getInstance();
                instance.setTime(task.getTaskStartTime());
                instance.add(Calendar.MINUTE, taskExpectRunning);
                task.setTaskEndTime(instance.getTime());
            }
            System.out.println("check params took: " + (System.currentTimeMillis() - s1));
            if (StringUtils.isNotBlank(task.getLiveInContent())) {
                task.setAnalysisContent(TaskUtil.saveLiveInContent(task.getLiveInContent()));
            }
            long s2 = System.currentTimeMillis();
            String millis = System.currentTimeMillis() + "";
            taskService.addSuperHeatTask(task, allEquipments, millis);
            System.out.println("add task to database took: " + (System.currentTimeMillis() - s2));

            //通过redis发布/订阅模式发送任务
            if (sendFlag) {
                ArrayList<Equipment> finalAllEquipments = allEquipments;
                new Thread(() -> sendSuperHotTaskBySocket(finalAllEquipments, task, millis)).start();
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }

    }

    /**
     * 查找超级热度下所有的设备
     */
    @ResponseBody
    @RequestMapping(value = "task/json/getEquipmentsByTaskId")
    public DtGrid getByTaskId(HttpServletRequest request, String id, int rows, int page) {
        DtGrid dtGrid = new DtGrid();
        if (!id.equals("undefined")) {
            dtGrid = taskDetailsService.findByAllDevCode(Long.valueOf(id));
        }
        Integer pageSize = rows == 0 ? 1 : rows;
        Integer pageNumber = page == 0 ? 1 : page;
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String nid = request.getParameter("nid");
        if (StringUtils.isNotBlank(nid)) {
            whereSql.append(" and nid like '%").append(nid).append("%'");
        }
        String name = request.getParameter("name");
        if (StringUtils.isNotBlank(name)) {
            whereSql.append(" and name like '%").append(name).append("%'");
        }
        String type = request.getParameter("type");
        if (StringUtils.isNotBlank(type) && !"99".equals(type)) {
            whereSql.append(" and type like '%").append(type).append("%'");
        }
        String state = request.getParameter("state");
        if (StringUtils.isNotBlank(state) && !"99".equals(state)) {
            whereSql.append(" and state like '%").append(state).append("%'");
        }

        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        return dtGrid;

    }

    /**
     * 查找超级热度下所有的设备分组
     */
    @ResponseBody
    @RequestMapping(value = "task/json/getEquipmentGroupsByTaskId")
    public DtGrid getByGroupsTaskId(HttpServletRequest request, String id, int rows, int page) {
        DtGrid dtGrid = new DtGrid();
        if (!id.equals("undefined")) {
            dtGrid = equipmentGroupService.findtaskGroup(Long.valueOf(id));
            List<Object> exhibitDatas = dtGrid.getExhibitDatas();
            exhibitDatas.removeIf(Objects::isNull);
        }
        Integer pageSize = rows == 0 ? 1 : rows;
        Integer pageNumber = page == 0 ? 1 : page;
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String nid = request.getParameter("nid");
        if (StringUtils.isNotBlank(nid)) {
            whereSql.append(" and nid like '%").append(nid).append("%'");
        }
        String name = request.getParameter("name");
        if (StringUtils.isNotBlank(name)) {
            whereSql.append(" and name like '%").append(name).append("%'");
        }
        String type = request.getParameter("type");
        if (StringUtils.isNotBlank(type) && !"99".equals(type)) {
            whereSql.append(" and type like '%").append(type).append("%'");
        }
        String state = request.getParameter("state");
        if (StringUtils.isNotBlank(state) && !"99".equals(state)) {
            whereSql.append(" and state like '%").append(state).append("%'");
        }

        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        return dtGrid;

    }


    /**
     * 添加实时互动任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/interaction/json/add")
    public ResultEntity addImmediatelyTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            long s1 = System.currentTimeMillis();
            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //添加分组下所有在线的设备
            if (taskType == null) {
                return fail("任务类型有误");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            System.out.println("add sub task took:" + (System.currentTimeMillis() - s1));
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    /**
     * 添加加入粉丝团任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/fans/json/add")
    public ResultEntity addFans(TaskAdminPramsModel taskAdminPramsModel) {
        try {

            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);

            //添加分组下所有在线的设备

            if (taskType == null) {
                return fail("任务类型有误");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }


    /**
     * 添加关注打榜任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/makeList/json/add")
    public ResultEntity addMakeListTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {

            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);

            //添加分组下所有在线的设备

            if (taskType == null) {
                return fail("任务类型有误");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    /**
     * 添加红包任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/redEnvelope/json/add")
    public ResultEntity addRedEnvelopeTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {

            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //添加分组下所有在线的设备

            if (taskType == null) {
                return fail("任务类型有误");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    /**
     * 添加查看商店任务
     *
     * @param taskAdminPramsModel 任务参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "task/lookShopping/json/add")
    public ResultEntity addLookShoppingTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {

            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);

            //添加分组下所有在线的设备
            if (taskType == null) {
                return fail("任务类型有误");
            }
            if (taskAdminPramsModel.getTaskExpectRunning() == null || taskAdminPramsModel.getTaskExpectRunning() < 1) {
                return fail("请填写正确的任务时间，任务时间不能小于1分钟");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加赠送礼物任务
     *
     * @param taskAdminPramsModel 任务参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "task/giveGift/json/add")
    public ResultEntity addGiveGiftTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {

            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);

            //添加分组下所有在线的设备

            if (taskType == null) {
                return fail("任务类型有误");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加赠送礼物任务
     *
     * @param taskAdminPramsModel 任务参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "task/fansGroup/json/add")
    public ResultEntity addFansGroupTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {

            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);

            //添加分组下所有在线的设备

            if (taskType == null) {
                return fail("任务类型有误");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加关注PK对手任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/followPk/json/add")
    public ResultEntity addFollowPkTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {

            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);

            //添加分组下所有在线的设备

            if (taskType == null) {
                return fail("任务类型有误");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加疯狂点屏任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/insaneClick/json/add")
    public ResultEntity addInsaneClickTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            checkSuperHeatTime(taskAdminPramsModel.getHeart().intValue(), taskAdminPramsModel.getTaskExpectRunning());
            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //添加分组下所有在线的设备

            if (taskType == null) {
                return fail("任务类型有误");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    /**
     * 添加关注任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/follow/json/add")
    public ResultEntity addFollowTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {

            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            checkedPrams(taskAdminPramsModel);
            //找到全部设备
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);

            //添加分组下所有在线的设备

            if (taskType == null) {
                return fail("任务类型有误");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加抢福袋
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/grabFuBag/json/add")
    public ResultEntity addGrabFuBagTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {

            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            checkedPrams(taskAdminPramsModel);
            //找到全部设备
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);

            //添加分组下所有在线的设备
            if (taskType == null) {
                return fail("任务类型有误");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加停止任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/overSubTask/json/add")
    public ResultEntity addOverSubTaskTask(TaskAdminPramsModel taskAdminPramsModel) {
        try {

            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            this.checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);

            //添加分组下所有在线的设备

            if (taskType == null) {
                return fail("任务类型有误");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 添加赠送灯牌任务
     *
     * @param taskAdminPramsModel 任务参数
     */
    @ResponseBody
    @RequestMapping(value = "task/giveLightPlateTask/json/add")
    public ResultEntity addGiveLightPlate(TaskAdminPramsModel taskAdminPramsModel) {
        try {
            //根据任务类型获取对应的枚举
            EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
            this.checkedPrams(taskAdminPramsModel);
            //判断是否是超级热度下的任务,不是正常添加是处理添加
            ArrayList<Equipment> allEquipments = this.allEquipments(taskAdminPramsModel);
            //添加分组下所有在线的设备
            if (taskType == null) {
                return fail("任务类型有误");
            }
            List<LiveHotSubTask> liveHotSubTasks = taskService.addImmediatelyTask(allEquipments, taskType, taskAdminPramsModel);
            //通过redis发布/订阅模式发送任务
            new Thread(() -> sendSubTaskBySocket(liveHotSubTasks)).start();
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 添加任务参数校验
     */
    public void checkedPrams(TaskAdminPramsModel taskAdminPramsModel) {
        EnumConstants.TaskType taskType = EnumConstants.TaskType.getTaskType(taskAdminPramsModel.getTaskType());
        if (StringUtils.isBlank(taskAdminPramsModel.getDevicesOrGroupsId())) {
            throw new BussinessException("请选择设备或分组后重试");
        }
        //关注打榜
        if (taskType.equals(EnumConstants.TaskType.FOLLOW_MAKER_A_LIST)) {
            if (taskAdminPramsModel.getMakeListNum() == null || taskAdminPramsModel.getMakeListNum() < 0) {
                throw new BussinessException("请输入正确打榜关注数量");
            }
        }

        //抢红包
        if (taskType.equals(EnumConstants.TaskType.GRAB_A_RED_ENVELOPE)) {
            if (taskAdminPramsModel.getRedEnvelopeTime() == null || taskAdminPramsModel.getRedEnvelopeTime() < 0) {
                throw new BussinessException("请输入抢红包时间");
            }
        }

        //赠送礼物
        if (taskType.equals(EnumConstants.TaskType.GIVE_GIFT) || taskType.equals(EnumConstants.TaskType.FANS_GROUP)) {
            if (taskAdminPramsModel.getGiftNumber() == null || taskAdminPramsModel.getGiftNumber() < 0) {
                throw new BussinessException("请输入正确的礼物数量");
            }
            if (taskAdminPramsModel.getGiftPage() == null || taskAdminPramsModel.getGiftPage() < 0) {
                throw new BussinessException("请输入正确的礼物页数");
            }
            if (StringUtil.isBlank(taskAdminPramsModel.getGiftBox())) {
                throw new BussinessException("请选择正确的礼物格子");
            }
        }
        //疯狂点屏幕
        if (taskType.equals(EnumConstants.TaskType.INSANE_CLICK)) {
            if (taskAdminPramsModel.getClickNumber() == null || taskAdminPramsModel.getClickNumber() < 0) {
                throw new BussinessException("请输入正确点击次数");
            }
            if (taskAdminPramsModel.getCommentInterval() == null || taskAdminPramsModel.getCommentInterval() < 0) {
                throw new BussinessException("请输入正确的任务运行间隔时间");
            }
            if (taskAdminPramsModel.getTaskExpectRunning() == null || taskAdminPramsModel.getTaskExpectRunning() < 0) {
                throw new BussinessException("请输入正确的任务时间");
            }
        }

    }

    public ArrayList<Equipment> allEquipments(TaskAdminPramsModel taskAdminPramsModel) {
        ArrayList<Equipment> allEquipments = new ArrayList<>();
        String devicesOrGroupsId = taskAdminPramsModel.getDevicesOrGroupsId();
        String[] devicesOrGroupsIdSplit = devicesOrGroupsId.split(",");
        if (taskAdminPramsModel.getDeviceStyle().equals(EnumConstants.DevicesOrGroupsId.EQUIPEMENT.code)) {
            for (String s : devicesOrGroupsIdSplit) {
                Equipment equipment = equipmentService.selectByPrimaryKey(Integer.parseInt(s));
                allEquipments.add(equipment);
            }
        } else {
            ArrayList<Long> groupsId = new ArrayList<>();
            for (String s : devicesOrGroupsIdSplit) {
                groupsId.add(Long.parseLong(s));
                allEquipments = equipmentService.selectLiveHotGroup(taskAdminPramsModel.getHeart(), groupsId);
            }
        }
        return allEquipments;
    }

    void sendSubTaskBySocket(List<LiveHotSubTask> liveHotSubTasks) {
        for (LiveHotSubTask subTask : liveHotSubTasks) {
            long s1 = System.currentTimeMillis();
            LiveHotSubTaskModel subTaskModel = new LiveHotSubTaskModel();
            //返回的集合内容
            List list = new ArrayList();
            //任务内容
            Map<String, Object> objectMap = new HashMap<>();
            String taskType = subTask.getTaskType();
            if (taskType.equals(EnumConstants.TaskType.OVER.code)) {
                objectMap.put("device_code", subTask.getDeviceUid());
                objectMap.put("task_id", subTask.getUuid());
                objectMap.put("task_type", subTask.getTaskType());
                objectMap.put("main_task_id", "");
                objectMap.put("sub_task_id", "");
                //判断是否关闭主任务
                if (StringUtil.isBlank(subTask.getLiveInContent())) {
                    objectMap.put("main_task_id", subTask.getTaskDetailId());
                } else {
                    objectMap.put("sub_task_id", subTask.getLiveInContent());
                }
                list.add(objectMap);
            }
            if (taskType.equals(EnumConstants.TaskType.LIVE_CHAT.code)) {
                RightNowModel data = new RightNowModel();
                data.setTask_type(taskType);
                data.setTask_id(subTask.getUuid());
                RightNowTaskDataModel taskDataModel = new RightNowTaskDataModel();
                taskDataModel.setTask_type(taskType);
                taskDataModel.setDevice_code(subTask.getDeviceUid());
                RightNowTaskModel rightNowTaskModel = new RightNowTaskModel();
                rightNowTaskModel.setLive(subTask.getLiveInContent());
                taskDataModel.setComment_template(rightNowTaskModel);
                data.setTask_data(taskDataModel);
                list.add(data);
            }
            if (taskType.equals(EnumConstants.TaskType.LOOK_SHOPPING.code)) {
                //查看商店
                //浏览间隔时间(秒)
                objectMap.put("seconds_interval_view", subTask.getCommentInterval());
                //每次浏览时长(秒)
                objectMap.put("seconds_per_view", subTask.getClickNumber());
                //关键字
                objectMap.put("keywords", subTask.getLiveInContent());
                //任务执行时长(分钟)
                objectMap.put("task_expect_running", subTask.getTaskExpectRunning());
                list.add(new TaskDataTowModel(subTask.getUuid(), subTask.getTaskType(), objectMap));

            }
            if (EnumConstants.TaskType.FOLLOW_PK_OPPONENT.code.equals(taskType)) {
                //关注对手
                list.add(new EquipmentModel(subTask.getDeviceUid(), subTask.getUuid(), subTask.getTaskType()));

            }
            //关注打榜
            if (EnumConstants.TaskType.FOLLOW_MAKER_A_LIST.code.equals(taskType)) {
                //打榜数量
                objectMap.put("pay_attention_to_the_number", subTask.getMakeListNumber());
                list.add(new TaskDataTowModel(subTask.getUuid(), subTask.getTaskType(), objectMap));

            }
            //抢红包
            if (EnumConstants.TaskType.GRAB_A_RED_ENVELOPE.code.equals(taskType)) {
                objectMap.put("red_envelope_time", subTask.getRedEnvelopeTime());
                list.add(new TaskDataTowModel(subTask.getUuid(), subTask.getTaskType(), objectMap));

            }
            //赠送礼物
            if (EnumConstants.TaskType.GIVE_GIFT.code.equals(taskType)) {
                objectMap.put("gift_number", subTask.getGiftNumber());
                objectMap.put("gift_box", subTask.getGiftBox());
                objectMap.put("gift_page", subTask.getGiftPage());
                list.add(new TaskDataTowModel(subTask.getUuid(), subTask.getTaskType(), objectMap));
            }
            //粉丝团
            if (EnumConstants.TaskType.FANS_GROUP.code.equals(taskType)) {
                objectMap.put("gift_number", subTask.getGiftNumber());
                objectMap.put("gift_box", subTask.getGiftBox());
                objectMap.put("gift_page", subTask.getGiftPage());
                list.add(new TaskDataTowModel(subTask.getUuid(), subTask.getTaskType(), objectMap));
            }
            //加入粉丝团
            if (EnumConstants.TaskType.JOIN_FAN_GROUP.code.equals(taskType)) {
                //是否赠送灯牌
                objectMap.put("give_light_plate_flag", subTask.getGiveLight());
                list.add(new TaskDataTowModel(subTask.getUuid(), subTask.getTaskType(), objectMap));
            }
            //疯狂点屏
            if (EnumConstants.TaskType.INSANE_CLICK.code.equals(taskType)) {
                //点击次数
                objectMap.put("click_number", subTask.getClickNumber());
                //运行时间
                objectMap.put("task_expect_running", subTask.getTaskExpectRunning());
                //运行时间间隔
                objectMap.put("comment_interval", subTask.getCommentInterval());
                list.add(new TaskDataTowModel(subTask.getUuid(), subTask.getTaskType(), objectMap));
            }

            list.add(new EquipmentModel(subTask.getDeviceUid(), subTask.getUuid(), subTask.getTaskType()));

            ResultEntity<List<TaskModel>> resultEntity = new ResultEntity<>();
            resultEntity.setCode(ResultEntity.SUCCESS);
            resultEntity.setData(list);
            resultEntity.setMsg("success");
            resultEntity.setTotal(list.size());
            String channelId = redisService.get(Constants.DEVICE_CHANNEL + subTask.getDeviceUid());
            if (StringUtil.isBlank(channelId)) {
                Equipment equipment = equipmentService.selectByUid(subTask.getDeviceUid());
                channelId = equipment.getChannelId();
            }
            System.out.println("deviceId:" + subTask.getDeviceUid() + ",channelId:" + channelId);
            resultEntity.setUuid(channelId);
            resultEntity.setUrl(subTask.getDeviceUid());
            //执行发送
            redisService.publish(Constants.TIKTOK_LIVE_CHAT, JSON.toJSONString(resultEntity));
            System.out.println("send single sub task took:" + (System.currentTimeMillis() - s1));
            //休眠100ms
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }

    }

    /**
     * 拼装数据
     *
     * @param allEquipments 设备信息
     * @param task          任务信息
     */
    void sendSuperHotTaskBySocket(ArrayList<Equipment> allEquipments, Task task, String millis) {
        long s1 = System.currentTimeMillis();
        for (Equipment equipment : allEquipments) {
            long s2 = System.currentTimeMillis();
            int state = Integer.parseInt(EnumConstants.taskStatus.RUN.value);
            if (!task.getTaskRunCode().equals(EnumConstants.TaskRunCode.NOW.code)) {
                state = (Integer.parseInt(EnumConstants.taskStatus.WAIT.value));
            }
            String taskId = "task_" + task.getTaskType() + "_" + equipment.getDeviceUid() + "_" + millis;

            //添加任务详情
            TaskDetails taskDetails = new TaskDetails();
            taskDetails.setCity("");
            taskDetails.setApkUpgrade(1);
            taskDetails.setTaskId(task.getId());
            taskDetails.setDeviceUid(equipment.getDeviceUid());
            taskDetails.setState(state);
            taskDetails.setNumber(0);
            taskDetails.setLetterType("letterAdmin");
            taskDetails.setTaskType(task.getTaskType());
            taskDetails.setTaskDetailUuid(taskId);
            TaskAllModel taskAllModel = new TaskAllModel();
            taskAllModel.setTaskDetailUuid(taskId);
            taskAllModel.setTaskType(task.getTaskType());
            taskAllModel.setCommentInterval(task.getCommentInterval());
            taskAllModel.setTaskContent(task.getTaskContent());
            taskAllModel.setTaskExpectRunning(task.getTaskExpectRunning());
            taskAllModel.setTaskRunCode(task.getTaskRunCode());
            taskAllModel.setNumber(taskDetails.getNumber());
            taskAllModel.setLetterType(taskDetails.getLetterType());
            taskAllModel.setTaskStartTime(taskDetails.getTaskStartTime());
            taskAllModel.setTaskEndTime(task.getTaskEndTime());
            taskAllModel.setAnalysisContent(task.getAnalysisContent());
            taskAllModel.setLiveInContent(task.getAnalysisContent());
            taskAllModel.setLiveInType(task.getLiveInType());

            Long commentTemplateId = task.getCommentTemplateId();
            CommentTemplate commentTemplate = commentTemplateService.findById(commentTemplateId);
            taskAllModel.setName(commentTemplate.getName());
            taskAllModel.setComment((commentTemplate.getComment()));
            taskAllModel.setLive(commentTemplate.getLive());
            taskAllModel.setTurns(commentTemplate.getTurns());
            taskAllModel.setLetter(commentTemplate.getLetter());
            TaskModel taskModel = taskService.getTaskModel(taskAllModel);
            List<TaskModel> list = new ArrayList<>();
            list.add(taskModel);
            ResultEntity<List<TaskModel>> resultEntity = new ResultEntity<>();
            resultEntity.setCode(ResultEntity.SUCCESS);
            resultEntity.setData(list);
            resultEntity.setMsg("success");
            resultEntity.setTotal(list.size());
            String channelId = redisService.get(Constants.DEVICE_CHANNEL + taskDetails.getDeviceUid());
            if (StringUtil.isBlank(channelId)) {
                channelId = equipment.getChannelId();
            }
            System.out.println("deviceId:" + taskDetails.getDeviceUid() + ",channelId:" + channelId);
            resultEntity.setUuid(channelId);
            resultEntity.setUrl(equipment.getDeviceUid());
            //执行发送
            String message = JSON.toJSONString(resultEntity);
            redisService.publish(Constants.TIKTOK_LIVE_HOT, message);
            System.out.println("single send task to client took: " + (System.currentTimeMillis() - s2));
            //休眠100ms
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
        System.out.println("Total send task to client took: " + (System.currentTimeMillis() - s1));
    }

    Task createTask(EnumConstants.TaskType taskType, TaskAdminPramsModel taskAdminPramsModel) {
        //添加任务
        Integer adminId = AdminSessionHelper.getAdminId();
        Task task = new Task();
        task.setTaskType(taskType.code);
        task.setDelFlag(GenericPo.DELFLAG.NO.code);
        String taskRunCode = StringUtil.isBlank(taskAdminPramsModel.getTaskRunCode()) ? EnumConstants.TaskRunCode.NOW.code : taskAdminPramsModel.getTaskRunCode();
        //如果任务内容没有输入  则取任务类型的描述
        String taskContent = StringUtil.isBlank(taskAdminPramsModel.getTaskContent()) ? taskType.desc : taskAdminPramsModel.getTaskContent();

        int taskExpectRunning = taskAdminPramsModel.getTaskExpectRunning() == null || taskAdminPramsModel.getTaskExpectRunning() == 0 ? EnumConstants.ExpireTime.zero.value : taskAdminPramsModel.getTaskExpectRunning();
        task.setCommentInterval(taskAdminPramsModel.getCommentInterval());
        task.setTaskExpectRunning(taskExpectRunning);
        task.setCommentTemplateId(taskAdminPramsModel.getCommentTemplateId());
        task.setTaskContent(taskContent);
        task.setTaskRunCode(taskRunCode);
        task.setTaskName(taskAdminPramsModel.getTaskName());
        task.setSysAdminId(adminId);
        task.setLiveInContent(taskAdminPramsModel.getLiveInContent());
        task.setCreateTime(new Timestamp(System.currentTimeMillis()));
        task.setIsLiveHot(taskAdminPramsModel.getIsLiveHot());

        //区分是否是直播任务
        if (1 == taskType.taskType) {
            task.setIsLiveHot(1);
        }
        //判断定时执行还是立即执行
        if (taskRunCode.equals(EnumConstants.TaskRunCode.NOW.code)) {
            task.setTaskStartTime(new Timestamp(System.currentTimeMillis()));
            task.setTaskStartTime(new Date());
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.MINUTE, taskExpectRunning);
            task.setTaskEndTime(nowTime.getTime());
        } else {
            task.setTaskStartTime(taskAdminPramsModel.getTaskStartDateTime());
            Calendar instance = Calendar.getInstance();
            instance.setTime(taskAdminPramsModel.getTaskStartDateTime());
            instance.add(Calendar.MINUTE, taskExpectRunning);
            task.setTaskEndTime(instance.getTime());
        }
        return task;
    }


    /**
     * 校验子任务运行时间不能超过主任务时间
     *
     * @param superHeartId      主任务id
     * @param taskExpectRunning 子任务时间
     * @return
     */
    public ResultEntity checkSuperHeatTime(Integer superHeartId, Integer taskExpectRunning) {
        try {

            Task task = taskService.selectByPrimaryKey(superHeartId);
            if (task == null) {
                throw new BussinessException("超级热度任务不存在，请刷新后重试");
            }
            //疯狂点屏任务时间
            if (null == taskExpectRunning) {
                throw new BussinessException("请输入任务时间");
            }

            Date date = EnumConstants.TaskRunCode.DELAY.code.equals(task.getTaskRunCode()) ? task.getCreateTime() : task.getTaskStartTime();
            //子任务时间不能大于主任务运行时间
            Date superHartTime = DateUtils.rollMinute(date, task.getTaskExpectRunning());
            //子任务时间
            Date giveLightPlateTime = DateUtils.rollMinute(new Date(), taskExpectRunning);
            System.out.println(DateUtils.dateStr(superHartTime, "yyyy-MM-dd HH:mm:ss") + "--" + DateUtils.dateStr(giveLightPlateTime, "yyyy-MM-dd HH:mm:ss"));
            if (superHartTime.before(giveLightPlateTime)) {
                long surplusTime = DateUtils.minutesBetween(new Date(), superHartTime);
                if (surplusTime <= 0) {
                    throw new BussinessException("超级热度任务是否已结束，请刷新后重试");
                }
                String msg = "超级热度任务时间为" + DateUtils.dateStr(superHartTime, "yyyy-MM-dd HH:mm:ss");
                msg += ",任务时间不能大于" + surplusTime + "分钟";
                throw new BussinessException("任务时间不能超过超级热度任务时间," + msg);
            }
            return success("校验通过");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussinessException(e.getMessage());
        }

    }
}

