package com.huimi.common.mask.jackJson;


import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.huimi.common.tools.StringUtil.isBlank;


/**
 * String 工具类
 */
public class MaskUtils extends StringUtils {

    /**
     * 除邮箱格式后缀加密长度
     */
    public static final int EMAIL_LENGTH = 11;
    /**
     * 除邮箱格式后缀加密长度
     */
    public static final int EMAIL_MASK_HANDE = 3;
    /**
     * 除邮箱格式后缀加密长度
     */
    public static final int NAME_MASK_LENGTH = 3;


    /**
     * 返回用户名脱敏串
     *
     * @param name
     * @return
     */
    public static String getMaskToName(String name) {
        if (isBlank(name) || name.length() == 1) {
            return name;
        }
        int index = name.length() > 2 ? 1 : 0;
        return getMaskStr(name, 1, index);
    }

    /**
     * 返回用户名脱敏串
     *
     * @param phone
     * @return
     */
    public static String getMaskToPhone(String phone) {
        if (isBlank(phone) || phone.length() == 1) {
            return phone;
        }
        int index = phone.length() >= 11 ? 4 : 0;
        return getMaskStr(phone, 3, index);
    }

    /**
     * 返回邮箱脱敏串
     *
     * @param object
     * @return
     */
    public static String getMaskToEmail(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        String email = object.toString();
        int emailFlagIndex = email.lastIndexOf("@");
        if (isBlank(email) || emailFlagIndex < 0) {
            return email;
        }
        //兼容非正常邮箱脱敏 例如1@163.com 或 @163.com等
        int handIndex = Math.min(emailFlagIndex, EMAIL_MASK_HANDE);
        int maskIndex = EMAIL_LENGTH - handIndex;
        String maskSb = String.format("%s%s%s", email.substring(0, handIndex), getMaskStrByLength(maskIndex), email.substring(emailFlagIndex));
        return maskSb;
    }

    /**
     * 通用脱敏方案
     *
     * @param str   脱敏内容
     * @param first 保留原本内容前?位
     * @param last  保留原本内容最后?位
     * @return
     */
    public static String getMaskStr(String str, Integer first, Integer last) {

        if (isBlank(str)) {
            return str;
        }
        StringBuffer sb = new StringBuffer();
        int index = str.length() - (first + last);
        sb.append(str.substring(0, first));
        sb.append(getMaskStrByLength(index));
        sb.append(str.substring(str.length() - last));
//        sb.append(getMaskToEmail(str));
        return sb.toString();
    }

    /**
     * 根据传入的个数返回*串字符
     *
     * @param index
     * @return
     */
    public static String getMaskStrByLength(Integer index) {
        if (index <= 0) {
            return "";
        }
        StringBuffer maskStr = new StringBuffer();
        for (int i = 0; i < index; i++) {
            maskStr.append("*");
        }
        return maskStr.toString();
    }


}
