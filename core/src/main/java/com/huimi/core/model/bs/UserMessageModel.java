package com.huimi.core.model.bs;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户留言板
 * 2025/1/6 00:06
 *
 * @author Jiazngxiaobai
 */
@Data
public class UserMessageModel {
    @NotNull(message = "请填写姓名")
    private String username;
    @NotNull(message = "请填写手机号码")
    private String phone;
    @NotNull(message = "请填写邮箱")
    private String email;
    @NotNull(message = "请填写地址")
    private String address;
    @NotNull(message = "请填写留言内容")
    private String content;
}
