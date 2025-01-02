package com.huimi.apis.config.aop;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.huimi.apis.config.InterceptorOrder;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.mask.jackJson.DataMask;
import com.huimi.common.mask.jackJson.DataMaskEnum;
import com.huimi.common.mask.jackJson.MaskUtils;
import com.huimi.common.tools.StringUtil;
import com.huimi.core.service.system.ConfService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Jiazngxiaobai
 */
@Aspect
@Component
@Slf4j
public class DataSourceMaskValueAspect extends InterceptorOrder {
    private static int num = 20;
    @Resource
    private ConfService confService;

    public static final List<String> apiList = Arrays.asList(
            "TestController.activeOverTask", "TestController.testResultToOneObject", "TestController.testResult"
    );


    @Pointcut("execution(* com.huimi.apis.controller..*Controller.*(..)) && @annotation(com.huimi.common.mask.jackJson.DataMask)")
    public void dataSourceMaskValuePointCutRest() {
    }

    /**
     * 后置处理返回参数
     */
    @AfterReturning(value = "dataSourceMaskValuePointCutRest()", returning = "result")
    public Object after(JoinPoint joinPoint, Object result) throws Throwable {
        log.info("result :{}", JSON.toJSONString(result));
        joinPoint.getSignature().getDeclaringTypeName();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DataMask dataMaskFlag = methodSignature.getMethod().getAnnotation(DataMask.class);
        //脱敏开关
        String maskFlag = confService.getConfigByKey("data_mask_flag");
        if (StringUtil.isBlank(maskFlag) || "false".equals(maskFlag) || dataMaskFlag == null) {
            return joinPoint.getThis();
        }
        try {
            if (result instanceof ResultEntity) {
                ResultEntity resultEntity = (ResultEntity) result;
                Object resultDate = resultEntity.getData();
                handleObject(1, resultDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("spring  aop mask value error msg :{}", e.getMessage());
        } finally {
            return joinPoint.getThis();
        }
    }

    public void handleResultEntity(Object result) throws Exception {

        if (result instanceof ResultEntity) {
            ResultEntity resultEntity = (ResultEntity) result;
            Object resultDate = resultEntity.getData();
            handleObject(num, resultDate);
        } else {
            handleObject(num, result);
        }
    }

    public void handleObject(int thisNum, Object resultDate) {
        try {
            if (thisNum >= num) {
                return;
            }
            if (resultDate instanceof List) {
                List<Object> resultList = (List) resultDate;
                if (CollectionUtil.isEmpty(resultList)) {
                    return;
                }
                for (Object obj : resultList) {
                    handleObject(sumAdd(thisNum), obj);
                }
            } else if (resultDate instanceof Map) {
                Map<Object, Object> objectMap = (Map) resultDate;
                for (Object key : objectMap.keySet()) {
                    Object value = objectMap.get(key);
                    if (value instanceof String) {
                        String keyStr = key.toString();
                        objectMap.put(key, getMaskValue(value.toString(), getDataMaskEnum(keyStr)));
                    } else if (value instanceof Map) {

                        handleObject(sumAdd(thisNum), value);
                    }
                }
            } else {
                //处理单个对象情况
                handleMaskValue(sumAdd(thisNum), resultDate);
            }
            sumSub(thisNum);
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
    public void handleMaskValue(int thisNum, Object obj) {
        try {
            DataMask dataMask;
            //null 或者 枚举时跳过 否则递归时 内存溢出
            if (Objects.isNull(obj) || isClass(obj)) {
                return;
            }
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (!isClass(field.getType())) {
                    Object object = field.get(obj);
//                    log.info("field by class ,name  {} ,type :{} ", field.getName(), field.getType());
                    handleObject(sumSub(thisNum), object);
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

    public static List<Object> ObjectList = Arrays.asList(
            int.class, float.class, double.class, long.class, short.class,
            byte.class, boolean.class, char.class, Date.class, java.sql.Date.class);

    public boolean isClass(Object clazz) {
        if (Integer.class == clazz
                || Float.class == clazz || Double.class == clazz
                || Long.class == clazz || Short.class == clazz
                || Byte.class == clazz || Boolean.class == clazz
                || Character.class == clazz || clazz instanceof Enum) {
            return true;
        }
        return ObjectList.contains(clazz);
    }

    /**
     * 判断当前接口是否需要脱敏处理
     *
     * @param joinPoint
     * @return
     */
    public boolean handleCheckedApi(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();

        className = className.substring(className.lastIndexOf(".") + 1);

        String api = className + "." + joinPoint.getSignature().getName();
        if (apiList.contains(api)) {
            return true;
        }
        return false;
    }

    public String getMaskValue(String value, DataMaskEnum dataMaskEnum) {
        if (Objects.nonNull(dataMaskEnum) && StringUtil.isNotBlank(value)) {
            switch (dataMaskEnum) {
                case EMAIL:
                    return MaskUtils.getMaskToEmail(value);
            }
        }
        return value;
    }

    public static int sumAdd(int n) {
        System.out.println(n);
        return n == num ? num : ++n;
    }

    public static int sumSub(int n) {
        System.out.println(n);
        return n == 1 ? 1 : --n;
    }

    public DataMaskEnum getDataMaskEnum(String fieldName) {
        if (StringUtil.isNotBlank(fieldName)) {
            if (fieldName.toLowerCase().contains(DataMaskEnum.EMAIL.name().toLowerCase())) {
                return DataMaskEnum.EMAIL;
            }
        }
        return null;
    }
}
