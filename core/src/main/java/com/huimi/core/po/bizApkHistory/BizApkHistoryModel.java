package com.huimi.core.po.bizApkHistory;

import com.alibaba.fastjson.annotation.JSONField;
import com.huimi.common.mask.jackJson.DataMask;
import com.huimi.common.mask.jackJson.DataMaskEnum;
import lombok.Builder;
import lombok.Data;

/**
 * create by lja on 2020/7/28 17:05
 * @author Jiazngxiaobai
 */
@Data
@Builder
public class BizApkHistoryModel {

    /**
     * 文件名称
     */
    @DataMask(function = DataMaskEnum.USERNAME)
    @JSONField(name = "name")
    private String name;


    /**
     * 文件地址
     */
    private String dataUrl;

    /**
     * 备注
     */
    @DataMask(function = DataMaskEnum.EMAIL)
    @JSONField
    private String remake;

    public BizApkHistoryModel() {
    }

    public BizApkHistoryModel(String name, String dataUrl, String remake) {
        this.name = name;
        this.dataUrl = dataUrl;
        this.remake = remake;
    }
}
