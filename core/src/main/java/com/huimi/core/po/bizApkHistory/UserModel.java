package com.huimi.core.po.bizApkHistory;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jiazngxiaobai
 */
@Data
public class UserModel {

    @JSONField(name = "user_name")
    @ApiModelProperty(name = "user_name",value = "姓名")
    private String userName;

    @JSONField(name = "user_age")
    @ApiModelProperty(name = "user_age",value = "姓名")
    private String userAge;

    @JSONField(name = "user_class")
    @ApiModelProperty(name = "user_class",value = "姓名")
    private String userClass;

    @JSONField(name = "user_sex")
    @ApiModelProperty(name = "user_sex",value = "姓名")
    private String userSex;
}
