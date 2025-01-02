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
@ApiModel(description = "应用领域表")
@Table(name = "bs_application_area")
public class BsApplicationAreaPo extends GenericPo<Integer> {
    public static final String TABLE_NAME = "bs_application_area";

    /**
     * 文件版本号
     */
    @Column(name = "application_title")
    private String applicationTitle;

    /**
     * 内容
     */
    @Column(name = "application_content")
    @ApiModelProperty(name = "application_content",value = "姓名")
    private String applicationContent;

    /**
     * 描述
     */
    @Column(name = "application_desc")
    private String applicationDesc;
    /**
     * 示意图地址
     */
    @Column(name = "application_img_url")
    private String applicationImgUrl;

    /**
     * 示意图地址
     */
    @Column(name = "application_detail_img_url")
    private String applicationDetailImgUrl;

    /**
     * 状态 0：发布 1：未发布
     */
    @Column(name = "status")
    private Integer status;

    @Transient
    private String statusName;

    public String getStatusName() {
        return EnumConstants.ApplicationStatusEunm.getHtmlStr(this.status);
    }

}
