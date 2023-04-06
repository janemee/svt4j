package com.huimi.common.mask3;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;

public class DataMaskAnnotationIntrospector extends NopAnnotationIntrospector {
    @Override
    public Object findSerializer(Annotated am) {
        DataMask2 annotation = am.getAnnotation(DataMask2.class);
        if (annotation != null) {
            return new DataMaskingSerializer2(annotation.function());
        }
        return null;
    }
}

