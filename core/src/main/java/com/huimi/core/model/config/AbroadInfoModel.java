package com.huimi.core.model.config;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import java.util.List;

/**
 * 关于企业详情类
 * 2025/1/6 23:02
 * @author Jiazngxiaobai
 */
@Data
@Serialization
public class AbroadInfoModel {
    /**
     * 公司起源
     */
    private String companySource;

    /**
     * 公司背景
     */
    private String companyBackdrop;

    /**
     * 宣传视频url
     */
    private String companyVideoUrl;

    /**
     * 公司宣传文案
     */
    private String companyCopyContent;

    /**
     * 公司宣传图片组
     */
    private String companyPubPicUrl;

    /**
     * 公司宣传图片组
     */
    private List<String> companyPubPicUrlList;
    /**
     * 公司发展史
     */
    private String companyHistoryOfDevUrl;

    /**
     * 公司发展史
     */
    private List<String> companyHistoryOfDevUrlList;
    /**
     * 企业文化
     */
    private String corporateCulture;

    /**
     * 企业文化图片组
     */
    private String corporateCulturePicUrl;

    /**
     * 企业文化图片组
     */
    private List<String> corporateCulturePicUrlList;

    /**
     * 企业荣誉证书 图片
     */
    private String enterpriseHonorCertPicUrl;
    /**
     * @企业荣誉证书 图片列表
     */
    private List<String> enterpriseHonorCertPicUrlList;

}
