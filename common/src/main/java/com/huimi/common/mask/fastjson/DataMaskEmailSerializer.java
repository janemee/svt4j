package com.huimi.common.mask.fastjson;


import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.huimi.common.mask.jackJson.MaskUtils;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author Jiazngxiaobai
 */
public class DataMaskEmailSerializer implements ObjectSerializer {
    public final static DataMaskEmailSerializer DATA_MASK_EMAIL_SERIALIZER = new DataMaskEmailSerializer();


    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
//        jsonSerializer.write(MaskUtils.getMaskToEmail(o));
    }
}
