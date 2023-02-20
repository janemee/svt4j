//package com.huimi.admin.quartz;
//
//import com.huimi.common.tools.DateUtil;
//import com.huimi.common.tools.StringUtil;
//import com.huimi.core.constant.ConfigNID;
//import com.huimi.core.constant.EnumConstants;
//import com.huimi.core.service.cache.RedisService;
//import com.huimi.core.service.liveHotSubTask.LiveHotSubTaskService;
//import com.huimi.core.service.task.TaskDetailsService;
//import com.huimi.core.task.TaskDetails;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * 超级热度任务-过期的自动已完成
// */
//
//@Slf4j
//@Component
//public class TaskDetailEnableJob {
//
//    @Resource
//    private TaskDetailsService taskDetailsService;
//    @Resource
//    private RedisService redisService;
//    @Resource
//    private LiveHotSubTaskService liveHotSubTaskService;
//
//
//    public static String remarks = "定时器扫描，任务时间已过，自动关闭，并关闭相关的子任务";
//
//    /**
//     * 每秒钟去判断是否有定时业务需要开启
//     */
//    @Scheduled(cron = "0/3 * * * * ?")
//    public void executeInternal() throws Exception {
//        try {
//            String flag = redisService.get(ConfigNID.AUTO_OUT_TASK_FLAG);
//            if (StringUtil.isBlank(flag) || !"1".equals(flag)) {
//                return;
//            }
//            List<TaskDetails> taskDetails = taskDetailsService.findByOutTimeTaskList();
//            for (TaskDetails taskDetail : taskDetails) {
//                //结束主任务
//                taskDetail.setState(Integer.parseInt(EnumConstants.taskStatus.DONE.value));
//                taskDetail.setRemarks(remarks);
//                taskDetail.setUpdateTime(DateUtil.getNow());
//                taskDetailsService.updateByPrimaryKeySelective(taskDetail);
//                // TODO: 2020/9/11  动添加结束任务
////                TaskDetails overTaskDetails = new TaskDetails();
////                overTaskDetails.setUuid(System.currentTimeMillis()+"");
//
//                //结束相关的子任务
//                liveHotSubTaskService.updateByTaskDetailsId(taskDetail.getId(), EnumConstants.taskStatus.DONE.value, remarks, EnumConstants.TaskType.OVER.value);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//    }
//}
