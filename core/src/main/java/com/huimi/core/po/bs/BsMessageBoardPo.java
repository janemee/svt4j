package com.huimi.core.po.bs;

import com.huimi.common.baseMapper.GenericPo;
import com.huimi.core.constant.EnumConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;


@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户留言表")
@Table(name = "bs_message_board")
public class BsMessageBoardPo extends GenericPo<Integer> {
    public static final String TABLE_NAME = "bs_message_board";

    /**
     * 用户名称
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name")
    private String name;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 手机号
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 手机号
     */
    @Column(name = "address")
    private String address;

    /**
     * 状态 0：待处理 1：跟进中，2.暂不处理，3.无效
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 留言内容
     */
    @Column(name = "content")
    private String content;


    @Transient
    private String statusName;

    public String getStatusName() {
        return EnumConstants.MessageBoardStatusEunm.getHtmlStr(this.status);
    }

}
