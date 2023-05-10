package com.huimi.core.po.bizApkHistory;

import com.alibaba.fastjson.annotation.JSONField;
import com.huimi.common.mask.jackJson.DataMask;
import com.huimi.common.mask.jackJson.DataMaskEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * create by lja on 2020/7/28 17:05
 *
 * @author Jiazngxiaobai
 */
@Data
public class BizApkHistoryModel3 {

    /**
     * 文件名称
     */
    @DataMask(function = DataMaskEnum.USERNAME)
    @JSONField(name = "user_name")
    private String name;

    /**
     * 备注
     */
    @DataMask(function = DataMaskEnum.EMAIL)
    @JSONField(name = "user_email")
    private String email;

    private BizApkHistoryModel4 bizApkHistoryModel4;

    private List<BizApkHistoryModel4> list;

    public BizApkHistoryModel3() {
    }

    public BizApkHistoryModel3(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
