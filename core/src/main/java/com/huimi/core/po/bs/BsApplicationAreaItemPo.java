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

/**
 * create by lja on 2020/7/28 17:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "应用领域子项表")
@Table(name = "bs_application_area_item")
public class BsApplicationAreaItemPo extends GenericPo<Integer> {
    public static final String TABLE_NAME = "bs_application_area_item";
    /**
     * 产品id
     */
    @Column(name = "application_area_id")
    private Integer applicationAreaId;

    /**
     * 产品标题
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

    /**
     * 类型 1:精密结构 2:价值优势 3.应用领域
     */
    @Column(name = "table_type")
    private Integer tableType;



    @Transient
    private String tableName;
    @Transient
    private String statusName;

    public String getTableName() {
        return EnumConstants.ApplicationTypeEunm.getHtmlStr(this.tableType);
    }

    public String getStatusName() {
        return EnumConstants.ApplicationStatusEunm.getHtmlStr(this.status);
    }

}
