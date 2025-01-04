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
@ApiModel(description = "媒体表")
@Table(name = "bs_media_center")
public class BsMediaCenterPo extends GenericPo<Integer> {
    public static final String TABLE_NAME = "bs_media_center";

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 内容
     */
    @Column(name = "content")
    @ApiModelProperty(name = "content")
    private String content;

    /**
     * 资源地址
     */
    @Column(name = "media_url")
    private String mediaUrl;


    /**
     * 状态 1：发布 0：未发布
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 状态 1：发布 0：未发布
     */
    @Column(name = "type")
    private Integer type;

    @Transient
    private String statusName;

    @Transient
    private String typeName;

    public String getStatusName() {
        return EnumConstants.ApplicationStatusEunm.getHtmlStr(this.status);
    }


    public String getTypeName() {
        return EnumConstants.MediaCenterTypeEunm.getHtmlStr(this.type);
    }

}
