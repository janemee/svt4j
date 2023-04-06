package com.huimi.common.mask2;

import com.huimi.common.mask.jackJson.MaskUtils;

/**
 * 电子邮箱脱敏
 * @author yhq
 * @date 2021年9月6日 16点13分
 **/
public class SensitiveEmailClass implements IStrategy {

    @Override
    public String desensitization(String email,int begin,int end) {
        return MaskUtils.getMaskToEmail(email);
    }

}
