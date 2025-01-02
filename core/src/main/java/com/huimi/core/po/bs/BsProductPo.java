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
@Table(name = "bs_product")
public class BsProductPo extends GenericPo<Integer> {
    public static final String TABLE_NAME = "bs_product";

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 产品标题
     */
    @Column(name = "product_title")
    @ApiModelProperty(name = "application_content",value = "姓名")
    private String productTitle;

    /**
     * 产品描述
     */
    @Column(name = "product_desc")
    private String productDesc;
    /**
     * 示意图地址
     */
    @Column(name = "product_img_url")
    private String productImgUrl;

    /**
     * 示意图地址
     */
    @Column(name = "product_detail_img_url")
    private String productDetailImgUrl;

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
