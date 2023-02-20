package com.huimi.core.po.equipment;

import lombok.Data;

/**
 * 设备升级返回数据结构实体
 */
@Data
public class EquipmentModel {

    /**
     * 设备唯一标识
     */
    private String device_code;
    /**
     * 任务id标识
     */
    private String task_id;
    /**
     * 任务类别
     */
    private String task_type;
    /**
     * 安装包的下载地址
     */
    private String down_load_apk_url;

    /**
     * 安装包下载二维码图片
     */
    private String down_load_qrCode_url;

    /**
     * 更新方式
     */
    private String apk_upgrade_type;

    /**
     * 主任务id
     */
    private String main_task_id;

    public EquipmentModel() {
    }

    public EquipmentModel(String device_code, String task_id, String task_type) {
        this.device_code = device_code;
        this.task_id = task_id;
        this.task_type = task_type;
    }

    public EquipmentModel(String device_code, String task_id, String task_type, String down_load_apk_url) {
        this.device_code = device_code;
        this.task_id = task_id;
        this.task_type = task_type;
        this.down_load_apk_url = down_load_apk_url;
    }
}
