package com.huimi.core.po.equipment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huimi.common.baseMapper.GenericPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * create by lja on 2020/7/29 14:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "设备表")
@Table(name = "equipment_group")
public class EquipmentGroup  extends GenericPo<Integer> {
    public static final String TABLE_NAME = "equipment_group";

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    private Integer state;

    @Column(name = "sys_admin_id")
    private Integer sysAdminId;

}
