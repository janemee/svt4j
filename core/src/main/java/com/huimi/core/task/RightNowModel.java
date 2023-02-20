package com.huimi.core.task;

import lombok.Data;

/**
 * 任务返回信息类
 */

@Data
public class RightNowModel {

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
    RightNowTaskDataModel task_data;

}
