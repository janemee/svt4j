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
@ApiModel(description = "产品表")
@Table(name = "bs_notice")
public class BsNoticePo extends GenericPo<Integer> {
    public static final String TABLE_NAME = "bs_notice";

    /**
     * 公告标题
     */
    @Column(name = "title")
    @ApiModelProperty(name = "application_content")
    private String title;

    /**
     * 产品描述
     */
    @Column(name = "content")
    private String content;

    /**
     * 示意图地址
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 状态 1：发布 0：未发布
     */
    @Column(name = "status")
    private Integer status;

    @Transient
    private String statusName;

    public String getStatusName() {
        return EnumConstants.ApplicationStatusEunm.getHtmlStr(this.status);
    }

}
