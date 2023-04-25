package com.huimi.core.po.bizApkHistory;

import com.alibaba.fastjson.annotation.JSONField;
import com.huimi.common.mask.jackJson.DataMask;
import com.huimi.common.mask.jackJson.DataMaskEnum;
import lombok.Builder;
import lombok.Data;

/**
 * create by lja on 2020/7/28 17:05
 *
 * @author Jiazngxiaobai
 */
@Data
@Builder
public class BizApkHistoryModel2 {

    /**
     * 文件名称
     */
    @DataMask(function = DataMaskEnum.USERNAME)
    @JSONField(name = "name")
    private String name;

    /**
     * 备注
     */
    @DataMask(function = DataMaskEnum.EMAIL)
    @JSONField(name = "email")
    private String email;


    public BizApkHistoryModel2() {
    }

    public BizApkHistoryModel2(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
