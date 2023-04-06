package com.huimi.common.mask3;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.util.Objects;

public final class DataMaskingSerializer2 extends JsonSerializer<String> implements ContextualSerializer {

    private DataMaskEnum2 dataMaskEnum2;

    public DataMaskingSerializer2(DataMaskEnum2 function) {
        dataMaskEnum2 =function;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(dataMaskEnum2.function().apply(value));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        DataMask2 annotation = property.getAnnotation(DataMask2.class);
        if (Objects.nonNull(annotation)&&Objects.equals(String.class, property.getType().getRawClass())) {
            this.dataMaskEnum2 = annotation.function();
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }

}

