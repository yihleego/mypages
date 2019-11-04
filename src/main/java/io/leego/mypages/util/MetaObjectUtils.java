package io.leego.mypages.util;

import io.leego.mypages.exception.PaginationException;
import org.apache.ibatis.reflection.MetaObject;

import java.lang.reflect.Method;

/**
 * @author Yihleego
 */
public class MetaObjectUtils {
    private static Method method;
    private final static String[] classNames = {
            "org.apache.ibatis.reflection.SystemMetaObject",
            "org.apache.ibatis.reflection.MetaObject",
    };

    static {
        try {
            Class.forName("org.apache.ibatis.reflection.ReflectorFactory");
        } catch (ClassNotFoundException e) {
            throw new PaginationException(e);
        }
        for (String className : classNames) {
            try {
                Class<?> metaClass = Class.forName(className);
                method = metaClass.getDeclaredMethod("forObject", Object.class);
                if (method != null) {
                    break;
                }
            } catch (Exception ignored) {
            }
        }
        if (method == null) {
            throw new PaginationException();
        }
    }

    private MetaObjectUtils() {
    }

    public static MetaObject forObject(Object object) {
        try {
            return (MetaObject) method.invoke(null, object);
        } catch (Exception e) {
            throw new PaginationException(e);
        }
    }

}
