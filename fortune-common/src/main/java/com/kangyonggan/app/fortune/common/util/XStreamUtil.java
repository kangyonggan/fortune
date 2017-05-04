package com.kangyonggan.app.fortune.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * @author kangyonggan
 * @since 5/4/17
 */
public class XStreamUtil {

    /**
     * 忽略对匹配的字段，增强扩展性
     *
     * @return
     */
    public static XStream getXStream() {
        return new XStream() {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @Override
                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        if (definedIn == Object.class) {
                            return false;
                        }
                        return super.shouldSerializeMember(definedIn, fieldName);
                    }
                };
            }
        };
    }

}
