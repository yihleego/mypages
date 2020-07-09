package io.leego.mypages.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yihleego
 */
public final class ReflectUtils {
    private static final Field[] EMPTY_FIELDS = new Field[0];
    private static final Method[] EMPTY_METHODS = new Method[0];

    private ReflectUtils() {
    }

    public static Field getField(Object o, String fieldName) throws NoSuchFieldException {
        return o.getClass().getField(fieldName);
    }

    public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        return clazz.getField(fieldName);
    }

    public static Field[] getFields(Object o) {
        return o.getClass().getFields();
    }

    public static Field[] getFields(Class<?> clazz) {
        return clazz.getFields();
    }

    public static Field[] getFields(Object o, boolean excludeStatic) {
        return getFields(o.getClass(), excludeStatic);
    }

    public static Field[] getFields(Class<?> clazz, boolean excludeStatic) {
        if (!excludeStatic) {
            return clazz.getFields();
        }
        Field[] fields = clazz.getFields();
        if (fields.length == 0) {
            return EMPTY_FIELDS;
        }
        List<Field> list = new ArrayList<>(fields.length);
        for (Field field : fields) {
            if (!isStatic(field)) {
                list.add(field);
            }
        }
        return list.toArray(EMPTY_FIELDS);
    }

    public static Field getDeclaredField(Object o, String fieldName) throws NoSuchFieldException {
        return o.getClass().getDeclaredField(fieldName);
    }

    public static Field getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        return clazz.getDeclaredField(fieldName);
    }

    public static Field[] getDeclaredFields(Object o) {
        return o.getClass().getDeclaredFields();
    }

    public static Field[] getDeclaredFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    public static Field[] getDeclaredFields(Object o, boolean excludeStatic) {
        return getDeclaredFields(o.getClass(), excludeStatic);
    }

    public static Field[] getDeclaredFields(Class<?> clazz, boolean excludeStatic) {
        if (!excludeStatic) {
            return clazz.getDeclaredFields();
        }
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            return EMPTY_FIELDS;
        }
        List<Field> list = new ArrayList<>(fields.length);
        for (Field field : fields) {
            if (!isStatic(field)) {
                list.add(field);
            }
        }
        return list.toArray(EMPTY_FIELDS);
    }

    public static Field getDeepField(Object o, String fieldName) throws NoSuchFieldException {
        return getDeepField(o.getClass(), fieldName);
    }

    public static Field getDeepField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        if (fieldName == null) {
            throw new NullPointerException();
        }
        Class<?> targetClass = clazz;
        while (targetClass != null && targetClass != Object.class) {
            Field[] fields = targetClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    return field;
                }
            }
            targetClass = targetClass.getSuperclass();
        }
        throw new NoSuchFieldException(fieldName);
    }

    public static Field[] getDeepFields(Object o) {
        return getDeepFields(o.getClass(), false);
    }

    public static Field[] getDeepFields(Class<?> clazz) {
        return getDeepFields(clazz, false);
    }

    public static Field[] getDeepFields(Object o, boolean excludeStatic) {
        return getDeepFields(o.getClass(), excludeStatic);
    }

    public static Field[] getDeepFields(Class<?> clazz, boolean excludeStatic) {
        List<Field> list = new ArrayList<>();
        Class<?> targetClass = clazz;
        while (targetClass != null && targetClass != Object.class) {
            Field[] fields = targetClass.getDeclaredFields();
            if (excludeStatic) {
                for (Field field : fields) {
                    if (!isStatic(field)) {
                        list.add(field);
                    }
                }
            } else {
                Collections.addAll(list, fields);
            }
            targetClass = targetClass.getSuperclass();
        }
        return list.toArray(EMPTY_FIELDS);
    }

    public static Object getFieldValue(Object o, Field field) throws IllegalAccessException {
        return field.get(o);
    }

    public static Object getFieldValue(Object o, Field field, boolean accessible) throws IllegalAccessException {
        setAccessible(field, accessible);
        return field.get(o);
    }

    public static Object getFieldValue(Object o, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getDeepField(o, fieldName).get(o);
    }

    public static Object getFieldValue(Object o, String fieldName, boolean accessible) throws NoSuchFieldException, IllegalAccessException {
        Field field = getDeepField(o, fieldName);
        setAccessible(field, accessible);
        return field.get(o);
    }

    public static void setFieldValue(Object o, Field field, Object value) throws IllegalAccessException {
        field.set(o, value);
    }

    public static void setFieldValue(Object o, Field field, Object value, boolean accessible) throws IllegalAccessException {
        setAccessible(field, accessible);
        field.set(o, value);
    }

    public static void setFieldValue(Object o, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        getDeepField(o, fieldName).set(o, value);
    }

    public static void setFieldValue(Object o, String fieldName, Object value, boolean accessible) throws NoSuchFieldException, IllegalAccessException {
        Field field = getDeepField(o, fieldName);
        setAccessible(field, accessible);
        field.set(o, value);
    }


    public static Method getMethod(Object o, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        return o.getClass().getMethod(methodName, parameterTypes);
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        return clazz.getMethod(methodName, parameterTypes);
    }

    public static Method[] getMethods(Object o) {
        return o.getClass().getMethods();
    }

    public static Method[] getMethods(Class<?> clazz) {
        return clazz.getMethods();
    }

    public static Method[] getMethods(Object o, boolean excludeStatic) {
        return getMethods(o.getClass(), excludeStatic);
    }

    public static Method[] getMethods(Class<?> clazz, boolean excludeStatic) {
        if (!excludeStatic) {
            return clazz.getMethods();
        }
        Method[] methods = clazz.getMethods();
        if (methods.length == 0) {
            return EMPTY_METHODS;
        }
        List<Method> list = new ArrayList<>(methods.length);
        for (Method method : methods) {
            if (!isStatic(method)) {
                list.add(method);
            }
        }
        return list.toArray(EMPTY_METHODS);
    }

    public static Method getDeclaredMethod(Object o, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        return o.getClass().getDeclaredMethod(methodName, parameterTypes);
    }

    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        return clazz.getDeclaredMethod(methodName, parameterTypes);
    }

    public static Method[] getDeclaredMethods(Object o) {
        return o.getClass().getDeclaredMethods();
    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        return clazz.getDeclaredMethods();
    }

    public static Method[] getDeclaredMethods(Object o, boolean excludeStatic) {
        return getDeclaredMethods(o.getClass(), excludeStatic);
    }

    public static Method[] getDeclaredMethods(Class<?> clazz, boolean excludeStatic) {
        if (!excludeStatic) {
            return clazz.getDeclaredMethods();
        }
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length == 0) {
            return EMPTY_METHODS;
        }
        List<Method> list = new ArrayList<>(methods.length);
        for (Method method : methods) {
            if (!isStatic(method)) {
                list.add(method);
            }
        }
        return list.toArray(EMPTY_METHODS);
    }

    public static Method getDeepMethod(Object o, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        return getDeepMethod(o.getClass(), methodName, parameterTypes);
    }

    public static Method getDeepMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        if (methodName == null) {
            throw new NullPointerException();
        }
        NoSuchMethodException exception = null;
        Class<?> targetClass = clazz;
        while (targetClass != null && targetClass != Object.class) {
            try {
                return targetClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                if (exception == null) {
                    exception = e;
                }
            }
            targetClass = targetClass.getSuperclass();
        }
        if (exception == null) {
            throw new NoSuchMethodException(methodName);
        } else {
            throw exception;
        }
    }

    public static Method[] getDeepMethods(Object o) {
        return getDeepMethods(o.getClass(), false);
    }

    public static Method[] getDeepMethods(Class<?> clazz) {
        return getDeepMethods(clazz, false);
    }

    public static Method[] getDeepMethods(Object o, boolean excludeStatic) {
        return getDeepMethods(o.getClass(), excludeStatic);
    }

    public static Method[] getDeepMethods(Class<?> clazz, boolean excludeStatic) {
        List<Method> list = new ArrayList<>();
        Class<?> targetClass = clazz;
        while (targetClass != null && targetClass != Object.class) {
            Method[] methods = targetClass.getDeclaredMethods();
            if (excludeStatic) {
                for (Method method : methods) {
                    if (!isStatic(method)) {
                        list.add(method);
                    }
                }
            } else {
                Collections.addAll(list, methods);
            }
            targetClass = targetClass.getSuperclass();
        }
        return list.toArray(EMPTY_METHODS);
    }

    public static Object invoke(Object o, Method method) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(o);
    }

    public static Object invoke(Object o, Method method, Object... args) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(o, args);
    }


    public static <A extends Annotation> A getDeclaredAnnotation(Object o, Class<A> annotationClass) {
        return o.getClass().getDeclaredAnnotation(annotationClass);
    }

    public static <A extends Annotation> A getDeclaredAnnotation(Class<?> clazz, Class<A> annotationClass) {
        return clazz.getDeclaredAnnotation(annotationClass);
    }

    public static Annotation[] getDeclaredAnnotations(Object o) {
        return o.getClass().getDeclaredAnnotations();
    }

    public static Annotation[] getDeclaredAnnotations(Class<?> clazz) {
        return clazz.getDeclaredAnnotations();
    }

    public static <A extends Annotation> A getAnnotation(Object o, Class<A> annotationClass) {
        return o.getClass().getAnnotation(annotationClass);
    }

    public static <A extends Annotation> A getAnnotation(Class<?> clazz, Class<A> annotationClass) {
        return clazz.getAnnotation(annotationClass);
    }

    public static Annotation[] getAnnotations(Object o) {
        return o.getClass().getAnnotations();
    }

    public static Annotation[] getAnnotations(Class<?> clazz) {
        return clazz.getAnnotations();
    }

    public static <A extends Annotation> A getDeclaredAnnotation(Field field, Class<A> annotationClass) {
        return field.getDeclaredAnnotation(annotationClass);
    }

    public static Annotation[] getDeclaredAnnotations(Field field) {
        return field.getDeclaredAnnotations();
    }

    public static <A extends Annotation> A getAnnotation(Field field, Class<A> annotationClass) {
        return field.getAnnotation(annotationClass);
    }

    public static Annotation[] getAnnotations(Field field) {
        return field.getAnnotations();
    }

    public static <A extends Annotation> A getDeclaredAnnotation(Method method, Class<A> annotationClass) {
        return method.getDeclaredAnnotation(annotationClass);
    }

    public static Annotation[] getDeclaredAnnotations(Method method) {
        return method.getDeclaredAnnotations();
    }

    public static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationClass) {
        return method.getAnnotation(annotationClass);
    }

    public static Annotation[] getAnnotations(Method method) {
        return method.getAnnotations();
    }


    public static boolean isInterface(Class<?> clazz) {
        return Modifier.isInterface(clazz.getModifiers());
    }

    public static boolean isAbstract(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    public static boolean isPublic(Class<?> clazz) {
        return Modifier.isPublic(clazz.getModifiers());
    }

    public static boolean isPublic(Field field) {
        return Modifier.isPublic(field.getModifiers());
    }

    public static boolean isPublic(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    public static boolean isPrivate(Class<?> clazz) {
        return Modifier.isPrivate(clazz.getModifiers());
    }

    public static boolean isPrivate(Field field) {
        return Modifier.isPrivate(field.getModifiers());
    }

    public static boolean isPrivate(Method method) {
        return Modifier.isPrivate(method.getModifiers());
    }

    public static boolean isProtected(Class<?> clazz) {
        return Modifier.isProtected(clazz.getModifiers());
    }

    public static boolean isProtected(Field field) {
        return Modifier.isProtected(field.getModifiers());
    }

    public static boolean isProtected(Method method) {
        return Modifier.isProtected(method.getModifiers());
    }

    public static boolean isStatic(Class<?> clazz) {
        return Modifier.isStatic(clazz.getModifiers());
    }

    public static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    public static boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    public static boolean isFinal(Class<?> clazz) {
        return Modifier.isFinal(clazz.getModifiers());
    }

    public static boolean isFinal(Field field) {
        return Modifier.isFinal(field.getModifiers());
    }

    public static boolean isFinal(Method method) {
        return Modifier.isFinal(method.getModifiers());
    }

    public static boolean isVolatile(Field field) {
        return Modifier.isVolatile(field.getModifiers());
    }

    public static boolean isTransient(Field field) {
        return Modifier.isTransient(field.getModifiers());
    }

    public static boolean isSynchronized(Method method) {
        return Modifier.isSynchronized(method.getModifiers());
    }

    public static boolean isNative(Method method) {
        return Modifier.isNative(method.getModifiers());
    }

    public static void setAccessible(Method method, boolean flag) {
        if (!isPublic(method)) {
            method.setAccessible(flag);
        }
    }

    public static void setAccessible(Field field, boolean flag) {
        if (!isPublic(field)) {
            field.setAccessible(flag);
        }
    }

}