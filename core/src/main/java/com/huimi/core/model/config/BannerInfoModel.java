package com.huimi.core.model.config;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import java.util.List;

/**
 * 关于企业详情类
 * 2025/1/6 23:02
 *
 * @author Jiazngxiaobai
 */
@Data
@Serialization
public class BannerInfoModel {
    /**
     * 首页banner图
     */
    private String indexBannerImgUrl;
    /**
     * 关于banner图
     */
    private String abroadBannerImgUrl;
    /**
     * 产品banner图
     */
    private String productBannerImgUrl;
    /**
     * 应用领域banner
     */
    private String applicationBannerImgUrl;
    /**
     * 媒体资源banner
     */
    private String mediaBannerImgUrl;
    /**
     * 客户服务banner
     */
    private String clientBannerImgUrl;
    /**
     * 联系我们
     */
    private String boardBannerImgUrl;
}
