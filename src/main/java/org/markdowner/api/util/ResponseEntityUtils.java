package org.markdowner.api.util;

import org.springframework.http.converter.json.MappingJacksonValue;

public class ResponseEntityUtils {

    public static <T> MappingJacksonValue JsonViewer(final Class<?> view, final T target) {
        final MappingJacksonValue wrapper = new MappingJacksonValue(target);
        wrapper.setSerializationView(view);
        return wrapper;
    }

}
