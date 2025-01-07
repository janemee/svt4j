package com.huimi.core.model.config;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

/**
 * 基础配置模型类
 * 2025/1/6 23:02
 * @author Jiazngxiaobai
 */
@Data
@Serialization
public class ConfigInfoModel {
    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 销售电话（国内）
     */
    private String cnMobile;

    /**
     * 销售电话（海外）
     */
    private String abroadMobile;

    /**
     * 传真
     */
    private String fax;

    /**
     * 备案号
     */
    private String webIcp;
    /**
     * 版权所有
     */
    private String copyRight;

}
