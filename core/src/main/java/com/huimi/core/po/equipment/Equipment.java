package com.huimi.core.po.equipment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huimi.common.baseMapper.GenericPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * create by lja on 2020/7/28 17:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "设备表")
@Table(name = "equipment")
public class Equipment extends GenericPo<Integer> {
    public static final String TABLE_NAME = "equipment";

    @Column(name = "device_uid")
    private String deviceUid;

    @Column(name = "device_code")
    private String deviceCode;

    @Column(name = "channel_id")
    private String channelId;

    @Column(name = "sys_admin_id")
    private Integer sysAdminId;

    /**
     * 代理商邀请码
     */
    @Column(name = "sys_admin_code")
    private String sysAdminCode;
    /**
     * 代理商名称
     */
    @Transient
    private String sysAdminName;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "state")
    private Integer state;

    @Column(name = "type")
    private Integer type;

    @Transient
    private String status;

    @Transient
    private String username;

    @Transient
    private String name;

    @Transient
    private String groupName;
    /**
     * 任务数量
     */
    @Transient
    private Integer taskNumber;

    /**
     * 备注
     */
    @Transient
    private String remakes;

    @Column(name = "`last_time`")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
}
