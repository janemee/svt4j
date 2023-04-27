package com.huimi.apis.config;

import cn.hutool.core.collection.CollectionUtil;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.mask.jackJson.DataMask;
import com.huimi.common.mask.jackJson.DataMaskEnum;
import com.huimi.common.mask.jackJson.MaskUtils;
import com.huimi.common.tools.StringUtil;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.system.ConfService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Jiazngxiaobai
 */
@Aspect
@Component
@Slf4j
public class DataSourceMaskValueAspect extends InterceptorOrder {
    @Autowired
    private RedisService redisService;
    @Resource
    private ConfService confService;


    @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void dataSourceMaskValuePointCut() {

    }

    /**
     * 后置处理返回参数
     */
    @AfterReturning(value = "dataSourceMaskValuePointCut()", returning = "result")
    public void after(Object result) {
        //脱敏开关
        String maskFlag = confService.getConfigByKey("data_mask_flag");
        if (StringUtil.isBlank(maskFlag) || "false".equals(maskFlag)) {
            return;
        }
        try {
            if (result instanceof ResultEntity) {
                ResultEntity resultEntity = (ResultEntity) result;
                Object resultDate = resultEntity.getData();
                handleObject(resultDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("spring  aop mask value error msg :{}", e.getMessage());
        } finally {
            log.info("spring  aop mask value");
        }
    }

    public void handleObject(Object resultDate) {
        try {
            if (resultDate instanceof List) {
                List<Object> resultList = (List) resultDate;
                if (CollectionUtil.isEmpty(resultList)) {
                    return;
                }
                for (Object obj : resultList) {
                    handleMaskValue(obj);
                }
            } else {
                //处理单个对象情况
                handleMaskValue(resultDate);
            }
        } catch (Exception e) {
            log.error("data mask value error msg : {}", e.getMessage());
        }
    }


    /**
     * 递归对象属性 处理属性复杂的对象
     *
     * @param obj
     * @param
     */
    public void handleMaskValue(Object obj) {
        try {
            DataMask dataMask;
            if(Objects.isNull(obj)){
                return;
            }
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (isClass(field.getType())) {
                    Object object = field.get(obj);
                    log.info("field by class ,name  {} ,type :{} ", field.getName(), field.getType());
                    handleObject(object);
                }
                log.info("field : {}", field.getName());
                if (String.class != field.getType() || (dataMask = field.getAnnotation(DataMask.class)) == null) {
                    continue;
                }
                //如果属性类型是时间类型，取出属性的值
                String valueStr = (String) field.get(obj);
                if (StringUtil.isBlank(valueStr)) {
                    continue;
                }
                DataMaskEnum dataMaskEnum = dataMask.function();
                if (DataMaskEnum.EMAIL == dataMaskEnum) {
                    field.set(obj, MaskUtils.getMaskToEmail(valueStr));
                }
                if (DataMaskEnum.USERNAME == dataMaskEnum) {
                    field.set(obj, MaskUtils.getMaskToName(valueStr));
                }
                if (DataMaskEnum.PHONE == dataMaskEnum) {
                    field.set(obj, MaskUtils.getMaskToPhone(valueStr));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isClass(Object clazz) {
        if (String.class == clazz || Integer.class == clazz
                || Float.class == clazz || Double.class == clazz
                || Long.class == clazz || Short.class == clazz
                || Byte.class == clazz || Boolean.class == clazz
                || Character.class == clazz) {
            return false;
        }
        if (Date.class == clazz || java.sql.Date.class == clazz) {
            return false;
        }
        return true;
    }
}
