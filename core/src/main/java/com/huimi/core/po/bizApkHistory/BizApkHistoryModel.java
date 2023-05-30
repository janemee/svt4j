package com.huimi.core.po.bizApkHistory;

import com.alibaba.fastjson.annotation.JSONField;
import com.huimi.common.enums.ExchangeEnum;
import com.huimi.common.enums.LoginEnum;
import com.huimi.common.mask.jackJson.DataMask;
import com.huimi.common.mask.jackJson.DataMaskEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * create by lja on 2020/7/28 17:05
 *
 * @author Jiazngxiaobai
 */
@Data
public class BizApkHistoryModel {

    /**
     * 文件名称
     */
    @DataMask(function = DataMaskEnum.USERNAME)
    @JSONField(name = "user_name")
    private String userName;


    @JSONField(name = "date_time")
    private Date dateTime;
    /**
     * 备注
     */
    @DataMask(function = DataMaskEnum.EMAIL)
    @JSONField(name = "remake")
    private String remake;

    private BizApkHistoryModel2 bizApkHistoryModel2;

    private List<BizApkHistoryModel2> list;

    private List<Integer> integerList;

    private Map<String, Integer> map;

    private int number;

    private LoginEnum loginEnum;

    private ExchangeEnum exchangeEnum;

    public BizApkHistoryModel() {
    }

    public BizApkHistoryModel(String name, String dataUrl, String remake) {
        this.userName = name;
        this.remake = remake;
    }
}
