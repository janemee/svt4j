package com.huimi.apis.config;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.huimi.common.mask.jackJson.DataMask;
import com.huimi.common.mask.jackJson.DataMaskEnum;
import com.huimi.common.mask.jackJson.MaskUtils;
import com.huimi.common.tools.StringUtil;
import com.huimi.core.service.cache.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;

/**
 * @author Jiazngxiaobai
 */
@Component
@Slf4j
public class ValueMaskFilter implements ValueFilter {
    @Resource
    private RedisService redisService;


    public ValueMaskFilter() {
    }

    ValueMaskFilter(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public Object process(Object o, String name, Object value) {
        try {
            if (Objects.isNull(value) || !(value instanceof String)) {
                return value;
            }
            try {
                Field field = o.getClass().getDeclaredField(StringUtil.underlineToCamel(name));
                log.info("name :{}, value :{}", name, value);
                DataMask dataMask;
                if (String.class != field.getType() || (dataMask = field.getAnnotation(DataMask.class)) == null) {
                    return value;
                }
                String valueStr = value.toString();
                DataMaskEnum dataMaskEnum = dataMask.function();
                switch (dataMaskEnum) {
                    case EMAIL:
                        return MaskUtils.getMaskToEmail(valueStr);
                    case USERNAME:
                        return MaskUtils.getMaskToName(valueStr);
                    default:
                }
            } catch (NoSuchFieldException e) {
                return value;
            }
            return value;
        } catch (Exception e) {
            return value;
        }
    }

}
