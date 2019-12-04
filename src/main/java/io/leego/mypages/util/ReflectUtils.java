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
    private ReflectUtils() {
    }

    public static Field getDeclaredField(Object o, String fieldName) {
        try {
            return o.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException ignored) {
        }
        return null;
    }

    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ignored) {
        }
        return null;
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
        List<Field> fieldList = new ArrayList<>(fields.length);
        for (Field field : fields) {
            if (!isStatic(field)) {
                fieldList.add(field);
            }
        }
        return fieldList.toArray(new Field[0]);
    }

    public static Field getField(Object o, String fieldName) {
        return getField(o.getClass(), fieldName);
    }

    public static Field getField(Class<?> clazz, String fieldName) {
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
        return null;
    }

    public static Field[] getFields(Object o) {
        return getFields(o.getClass(), false);
    }

    public static Field[] getFields(Class<?> clazz) {
        return getFields(clazz, false);
    }

    public static Field[] getFields(Object o, boolean excludeStatic) {
        return getFields(o.getClass(), excludeStatic);
    }

    public static Field[] getFields(Class<?> clazz, boolean excludeStatic) {
        List<Field> fieldList = new ArrayList<>();
        Class<?> targetClass = clazz;
        while (targetClass != null && targetClass != Object.class) {
            Field[] fields = targetClass.getDeclaredFields();
            if (excludeStatic) {
                for (Field field : fields) {
                    if (!isStatic(field)) {
                        fieldList.add(field);
                    }
                }
            } else {
                Collections.addAll(fieldList, fields);
            }
            targetClass = targetClass.getSuperclass();
        }
        return fieldList.toArray(new Field[0]);
    }


    public static Method getDeclaredMethod(Object o, String methodName) {
        return getDeclaredMethod(o.getClass(), methodName);
    }

    public static Method getDeclaredMethod(Object o, String methodName, Class<?>... parameterTypes) {
        return getDeclaredMethod(o.getClass(), methodName, parameterTypes);
    }

    public static Method getDeclaredMethod(Class<?> clazz, String methodName) {
        try {
            return clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException ignored) {
        }
        return null;
    }

    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException ignored) {
        }
        return null;
    }

    public static Method[] getDeclaredMethods(Object o) {
        return o.getClass().getDeclaredMethods();
    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        return clazz.getDeclaredMethods();
    }

    public static Method getMethod(Object o, String methodName) {
        return getMethod(o.getClass(), methodName, (Class<?>[]) null);
    }

    public static Method getMethod(Object o, String methodName, Class<?>... parameterTypes) {
        return getMethod(o.getClass(), methodName, parameterTypes);
    }

    public static Method getMethod(Class<?> clazz, String methodName) {
        return getMethod(clazz, methodName, (Class<?>[]) null);
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        Class<?> targetClass = clazz;
        while (targetClass != null && targetClass != Object.class) {
            Method method;
            try {
                method = targetClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException ignored) {
                return null;
            }
            if (method != null) {
                return method;
            }
            targetClass = targetClass.getSuperclass();
        }
        return null;
    }

    public static Method[] getMethods(Object o) {
        return getMethods(o.getClass());
    }

    public static Method[] getMethods(Class<?> clazz) {
        List<Method> methodList = new ArrayList<>();
        Class<?> targetClass = clazz;
        while (targetClass != null && targetClass != Object.class) {
            Collections.addAll(methodList, targetClass.getDeclaredMethods());
            targetClass = targetClass.getSuperclass();
        }
        return methodList.toArray(new Method[0]);
    }


    public static Object invoke(Object o, Method method) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(o);
    }

    public static Object invoke(Object o, Method method, Object... args) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(o, args);
    }

    public static Object invoke(Object o, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = getMethod(o, methodName);
        if (method == null) {
            throw new NoSuchMethodException();
        }
        return method.invoke(o);
    }

    public static Object invoke(Object o, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = getMethod(o, methodName);
        if (method == null) {
            throw new NoSuchMethodException();
        }
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

}