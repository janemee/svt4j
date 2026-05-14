package com.huimi.core.task;

import lombok.Data;

/**
 * 任务返回信息类
 */

@Data
public class TaskModel {

    /**
     * 任务id
     */
    String task_id;
    /**
     * 任务类型
     */
    String task_type;

    /**
     * 话术内容
     */
    TaskDataModel task_data;

}
