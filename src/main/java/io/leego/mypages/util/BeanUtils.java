package io.leego.mypages.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Yihleego
 */
public final class BeanUtils {
    private static final String GET_PREFIX = "get";
    private static final String SET_PREFIX = "set";
    private static final String IS_PREFIX = "is";

    private BeanUtils() {
    }

    public static String getReadMethodName(String fieldName) {
        return GET_PREFIX + upperCaseFirst(fieldName);
    }

    public static String getReadMethodName(String fieldName, boolean booleanType) {
        return (booleanType ? IS_PREFIX : GET_PREFIX) + upperCaseFirst(fieldName);
    }

    public static String getReadMethodName(Field field) {
        return getReadMethodName(field.getName(), field.getGenericType() == boolean.class);
    }

    public static String getWriteMethodName(String fieldName) {
        return SET_PREFIX + upperCaseFirst(fieldName);
    }

    public static String getWriteMethodName(Field field) {
        return SET_PREFIX + upperCaseFirst(field.getName());
    }


    public static Method getReadMethod(Class<?> type, String fieldName) {
        return ReflectUtils.getMethod(type, getReadMethodName(fieldName));
    }

    public static Method getReadMethod(Class<?> type, String fieldName, boolean booleanType) {
        return ReflectUtils.getMethod(type, getReadMethodName(fieldName, booleanType));
    }

    public static Method getReadMethod(Field field) {
        return ReflectUtils.getMethod(field.getDeclaringClass(), getReadMethodName(field));
    }

    public static Method getWriteMethod(Class<?> type, String fieldName) {
        return ReflectUtils.getMethod(type, getWriteMethodName(fieldName));
    }

    public static Method getWriteMethod(Field field) {
        return ReflectUtils.getMethod(field.getDeclaringClass(), getWriteMethodName(field));
    }


    public static Object read(Object o, String fieldName) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Field field = ReflectUtils.getField(o, fieldName);
        if (field == null) {
            throw new NoSuchFieldException();
        }
        return read(o, field);
    }

    public static Object read(Object o, Field field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectUtils.invoke(o, getReadMethodName(field));
    }

    public static Object write(Object o, String fieldName, Object arg) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectUtils.invoke(o, getWriteMethodName(fieldName), arg);
    }

    public static Object write(Object o, Field field, Object arg) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectUtils.invoke(o, getWriteMethodName(field), arg);
    }

    public static Object write(Object o, String fieldName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectUtils.invoke(o, getWriteMethodName(fieldName), args);
    }

    public static Object write(Object o, Field field, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectUtils.invoke(o, getWriteMethodName(field), args);
    }


    private static String upperCaseFirst(String s) {
        if (s != null && s.length() > 0) {
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
        return s;
    }

}
