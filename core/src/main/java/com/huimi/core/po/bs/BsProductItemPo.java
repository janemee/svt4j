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
@ApiModel(description = "产品详情子项表")
@Table(name = "bs_product_item")
public class BsProductItemPo extends GenericPo<Integer> {
    public static final String TABLE_NAME = "bs_product_item";

    /**
     * 产品父id
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 产品标题
     */
    @Column(name = "product_title")
    @ApiModelProperty(name = "application_content")
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
     * 子项类型 1:精密结构 2:价值优势 3.应用领域
     */
    @Column(name = "table_type")
    private Integer tableType;


    /**
     * 状态 1：发布 0：未发布
     */
    @Column(name = "status")
    private Integer status;

    @Transient
    private String statusName;

    @Transient
    private String tableTypeName;

    @Transient
    private String tableTypeDesc;

    public String getStatusName() {
        return EnumConstants.ApplicationStatusEunm.getHtmlStr(this.status);
    }

    public String getTableTypeName() {
        return EnumConstants.ProductTypeEunm.getHtmlStr(this.tableType);
    }

    public String getTableTypeDesc() { return EnumConstants.ProductTypeEunm.getDesc(this.tableType); }
}
