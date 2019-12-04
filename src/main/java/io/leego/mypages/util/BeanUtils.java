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

    /**
     * Returns read method name.
     * <p>Example: <pre>
     * String s = getReadMethodName("name"); // getName
     * </pre>
     * @param fieldName field name
     * @return read method name
     */
    public static String getReadMethodName(String fieldName) {
        return GET_PREFIX + upperCaseFirst(fieldName);
    }

    /**
     * Returns read method name.
     * <p>Example 1:<pre>
     * String s = getReadMethodName("name", false); // getName
     * </pre>
     * <p>Example 2:<pre>
     * String s = getReadMethodName("success", true); // isSuccess
     * </pre>
     * @param fieldName        field name
     * @param primitiveBoolean Whether the field type is primitive boolean
     * @return read method name
     */
    public static String getReadMethodName(String fieldName, boolean primitiveBoolean) {
        return (primitiveBoolean ? IS_PREFIX : GET_PREFIX) + upperCaseFirst(fieldName);
    }

    /**
     * Returns read method name.
     * <p>Example 1:<pre>
     * Field field = Foo.class.getField("name"); // field.getGenericType() != boolean.class
     * String s = getReadMethodName(field); // getName
     * </pre>
     * <p>Example 2:<pre>
     * Field field = Foo.class.getField("success"); // field.getGenericType() == boolean.class
     * String s = getReadMethodName(field); // isSuccess
     * </pre>
     * @param field {@link Field}
     * @return read method name
     */
    public static String getReadMethodName(Field field) {
        return getReadMethodName(field.getName(), field.getGenericType() == boolean.class);
    }

    /**
     * Returns read method name.
     * <p>Example 1:<pre>
     * Field field = Foo.class.getField("name");
     * String s = getReadMethodName(field, false); // getName
     * </pre>
     * <p>Example 2:<pre>
     * Field field = Foo.class.getField("success");
     * String s = getReadMethodName(field, true); // isSuccess
     * </pre>
     * @param field            {@link Field}
     * @param primitiveBoolean Whether the field type is primitive boolean
     * @return read method name
     */
    public static String getReadMethodName(Field field, boolean primitiveBoolean) {
        return getReadMethodName(field.getName(), primitiveBoolean);
    }

    /**
     * Returns write method name.
     * <p>Example: <pre>
     * String s = getWriteMethodName("name"); // setName
     * </pre>
     * @param fieldName field name
     * @return write method name
     */
    public static String getWriteMethodName(String fieldName) {
        return SET_PREFIX + upperCaseFirst(fieldName);
    }

    /**
     * Returns write method name.
     * <p>Example: <pre>
     * Field field = Foo.class.getField("name");
     * String s = getWriteMethodName(field); // setName
     * </pre>
     * @param field {@link Field}
     * @return write method name
     */
    public static String getWriteMethodName(Field field) {
        return SET_PREFIX + upperCaseFirst(field.getName());
    }


    /**
     * Returns read method.
     * <p>Example: <pre>
     * Method m = getReadMethod(Foo.class, "name");
     * </pre>
     * @param type      Bean's class
     * @param fieldName field name
     * @return read method
     */
    public static Method getReadMethod(Class<?> type, String fieldName) {
        Field field = ReflectUtils.getField(type, fieldName);
        if (field == null) {
            return null;
        }
        return ReflectUtils.getMethod(type, getReadMethodName(field));
    }

    /**
     * Returns read method.
     * <p>Example: <pre>
     * Field field = Foo.class.getField("name");
     * Method m = getReadMethod(field);
     * </pre>
     * @param field {@link Field}
     * @return read method
     */
    public static Method getReadMethod(Field field) {
        return ReflectUtils.getMethod(field.getDeclaringClass(), getReadMethodName(field));
    }

    /**
     * Returns write method.
     * <p>Example: <pre>
     * String s = getWriteMethod(Foo.class, "name");
     * </pre>
     * @param type           Bean's class
     * @param fieldName      field name
     * @param parameterTypes the parameter type array
     * @return write method
     */
    public static Method getWriteMethod(Class<?> type, String fieldName, Class<?>... parameterTypes) {
        return ReflectUtils.getMethod(type, getWriteMethodName(fieldName), parameterTypes);
    }

    /**
     * Returns write method.
     * <p>Example: <pre>
     * Field field = Foo.class.getField("name");
     * Method m = getWriteMethod(field);
     * </pre>
     * @param field          {@link Field}
     * @param parameterTypes the parameter type array
     * @return read method
     */
    public static Method getWriteMethod(Field field, Class<?>... parameterTypes) {
        return ReflectUtils.getMethod(field.getDeclaringClass(), getWriteMethodName(field), parameterTypes);
    }


    /**
     * Returns value of the object field.
     * @param o         target
     * @param fieldName field name
     * @return value
     * @throws NoSuchFieldException      if field is not found.
     * @throws NoSuchMethodException     if method is not found.
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static Object read(Object o, String fieldName) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Field field = ReflectUtils.getField(o, fieldName);
        if (field == null) {
            throw new NoSuchFieldException();
        }
        return read(o, field);
    }

    /**
     * Returns value of the object field.
     * @param o     target
     * @param field {@link Field}
     * @return value
     * @throws NoSuchMethodException     if method is not found.
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static Object read(Object o, Field field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectUtils.invoke(o, getReadMethodName(field));
    }

    /**
     * Invokes the underlying method represented by field name
     * @param o         target
     * @param fieldName field name
     * @param args      the arguments used for the method call
     * @return result
     * @throws NoSuchMethodException     if method is not found.
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static Object write(Object o, String fieldName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectUtils.invoke(o, getWriteMethodName(fieldName), args);
    }

    /**
     * Invokes the underlying method represented by field
     * @param o     target
     * @param field {@link Field}
     * @param args  the argument used for the method call
     * @return result
     * @throws NoSuchMethodException     if method is not found.
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static Object write(Object o, Field field, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ReflectUtils.invoke(o, getWriteMethodName(field), args);
    }


    /**
     * Returns a {@link String} which capitalizes the first letter of the string.
     */
    private static String upperCaseFirst(String s) {
        if (s != null && s.length() > 0) {
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
        return s;
    }

}
