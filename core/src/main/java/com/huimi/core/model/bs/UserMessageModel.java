package com.huimi.core.model.bs;

import com.sun.xml.internal.ws.developer.Serialization;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户留言板
 * 2025/1/6 00:06
 *
 * @author Jiazngxiaobai
 */
@Data
@Serialization
public class UserMessageModel {
    @ApiModelProperty(name = "user_name",value = "用户姓名")
    private String username;
    @ApiModelProperty(name = "phone",value = "手机号")
    private String phone;
    @ApiModelProperty(name = "email",value = "邮箱")
    private String email;
    @ApiModelProperty(name = "address",value = "地址")
    private String address;
    @ApiModelProperty(name = "content",value = "留言内容")
    private String content;
}
