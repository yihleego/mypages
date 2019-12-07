package io.leego.mypages.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
     * @param fieldName The name of the property
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
     * @param fieldName        The name of the property
     * @param primitiveBoolean Whether the field type is primitive boolean
     * @return read method name
     */
    public static String getReadMethodName(String fieldName, boolean primitiveBoolean) {
        return (primitiveBoolean ? IS_PREFIX : GET_PREFIX) + upperCaseFirst(fieldName);
    }

    /**
     * Returns read method name.
     * <p>Example 1:<pre>
     * Field field = Foo.class.getField("name"); // field.getType() != boolean.class
     * String s = getReadMethodName(field); // getName
     * </pre>
     * <p>Example 2:<pre>
     * Field field = Foo.class.getField("success"); // field.getType() == boolean.class
     * String s = getReadMethodName(field); // isSuccess
     * </pre>
     * @param field {@link Field}
     * @return read method name
     */
    public static String getReadMethodName(Field field) {
        return getReadMethodName(field.getName(), field.getType() == boolean.class);
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
     * @param fieldName The name of the property
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
     * @param type      The Class object for the target bean
     * @param fieldName The name of the property
     * @return read method
     */
    public static Method getReadMethod(Class<?> type, String fieldName) throws IntrospectionException {
        return getPropertyDescriptor(fieldName, type).getReadMethod();
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
    public static Method getReadMethod(Field field) throws IntrospectionException {
        return getReadMethod(field.getDeclaringClass(), field.getName());
    }

    /**
     * Returns write method.
     * <p>Example: <pre>
     * String s = getWriteMethod(Foo.class, "name");
     * </pre>
     * @param type      The Class object for the target bean
     * @param fieldName The name of the property
     * @return write method
     */
    public static Method getWriteMethod(Class<?> type, String fieldName) throws IntrospectionException {
        return getPropertyDescriptor(fieldName, type).getWriteMethod();
    }

    /**
     * Returns write method.
     * <p>Example: <pre>
     * Field field = Foo.class.getField("name");
     * Method m = getWriteMethod(field);
     * </pre>
     * @param field {@link Field}
     * @return read method
     */
    public static Method getWriteMethod(Field field) throws IntrospectionException {
        return getWriteMethod(field.getDeclaringClass(), field.getName());
    }


    /**
     * Returns value of the object field.
     * @param o         Target bean
     * @param fieldName The name of the property
     * @return value
     * @throws NoSuchMethodException     if method is not found.
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static Object read(Object o, String fieldName) throws IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = getReadMethod(o.getClass(), fieldName);
        if (method == null) {
            throw new NoSuchMethodException();
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
        }
        return method.invoke(o);
    }

    /**
     * Returns value of the object field.
     * @param o     Target bean
     * @param field {@link Field}
     * @return value
     * @throws NoSuchMethodException     if method is not found.
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static Object read(Object o, Field field) throws IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = getReadMethod(o.getClass(), field.getName());
        if (method == null) {
            throw new NoSuchMethodException();
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
        }
        return method.invoke(o);
    }

    /**
     * Invokes method.
     * @param o      Target bean
     * @param method {@link Method}
     * @return value
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static Object read(Object o, Method method) throws IllegalAccessException, InvocationTargetException {
        if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
        }
        return method.invoke(o);
    }

    /**
     * Returns value of the object field.
     * @param o          Target bean
     * @param fieldName  The name of the property
     * @param returnType The return type
     * @return value
     * @throws NoSuchMethodException     if method is not found.
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static <T> T read(Object o, String fieldName, Class<T> returnType) throws IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object value = read(o, fieldName);
        if (value != null) {
            return returnType.cast(value);
        }
        return null;
    }

    /**
     * Returns value of the object field.
     * @param o          Target bean
     * @param field      {@link Field}
     * @param returnType The return type
     * @return value
     * @throws NoSuchMethodException     if method is not found.
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static <T> T read(Object o, Field field, Class<T> returnType) throws IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object value = read(o, field);
        if (value != null) {
            return returnType.cast(value);
        }
        return null;
    }

    /**
     * Invokes method.
     * @param o          Target bean
     * @param method     {@link Method}
     * @param returnType The return type
     * @return value
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static <T> T read(Object o, Method method, Class<T> returnType) throws IllegalAccessException, InvocationTargetException {
        Object value = read(o, method);
        if (value != null) {
            return returnType.cast(value);
        }
        return null;
    }

    /**
     * Invokes the underlying method represented by field name
     * @param o         Target bean
     * @param fieldName The name of the property
     * @param arg       The argument used for the method call
     * @return result
     * @throws NoSuchMethodException     if method is not found.
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static Object write(Object o, String fieldName, Object arg) throws IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = getWriteMethod(o.getClass(), fieldName);
        if (method == null) {
            throw new NoSuchMethodException();
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
        }
        return method.invoke(o, arg);
    }

    /**
     * Invokes the underlying method represented by field
     * @param o     Target bean
     * @param field {@link Field}
     * @param arg   The argument used for the method call
     * @return result
     * @throws NoSuchMethodException     if method is not found.
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static Object write(Object o, Field field, Object arg) throws IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = getWriteMethod(o.getClass(), field.getName());
        if (method == null) {
            throw new NoSuchMethodException();
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
        }
        return method.invoke(o, arg);
    }

    /**
     * Invokes method.
     * @param o      Target bean
     * @param method {@link Method}
     * @param arg    The argument used for the method call
     * @return result
     * @throws IllegalAccessException    if the method is not accessible.
     * @throws InvocationTargetException if the underlying method  throws an exception.
     */
    public static Object write(Object o, Method method, Object arg) throws IllegalAccessException, InvocationTargetException {
        if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
        }
        return method.invoke(o, arg);
    }


    /**
     * Returns a {@link PropertyDescriptor} for a property that follows
     * the standard Java convention.
     * @param propertyName The programmatic name of the property
     * @param beanClass    The Class object for the target bean
     * @throws IntrospectionException if an exception occurs during introspection.
     */
    public static PropertyDescriptor getPropertyDescriptor(String propertyName, Class<?> beanClass) throws IntrospectionException {
        return new PropertyDescriptor(propertyName, beanClass);
    }

    /**
     * Returns a {@link PropertyDescriptor} of a simple property, and method
     * names for reading and writing the property.
     * @param propertyName    The programmatic name of the property.
     * @param beanClass       The Class object for the target bean.
     * @param readMethodName  The name of the method used for reading the property value.
     * @param writeMethodName The name of the method used for writing the property value.
     * @throws IntrospectionException if an exception occurs during introspection.
     */
    public static PropertyDescriptor getPropertyDescriptor(String propertyName, Class<?> beanClass, String readMethodName, String writeMethodName) throws IntrospectionException {
        return new PropertyDescriptor(propertyName, beanClass, readMethodName, writeMethodName);
    }

    /**
     * Returns a {@link PropertyDescriptor} of a simple property, and Method
     * objects for reading and writing the property.
     * @param propertyName The programmatic name of the property.
     * @param readMethod   The method used for reading the property value.
     * @param writeMethod  The method used for writing the property value.
     * @throws IntrospectionException if an exception occurs during introspection.
     */
    public static PropertyDescriptor getPropertyDescriptor(String propertyName, Method readMethod, Method writeMethod) throws IntrospectionException {
        return new PropertyDescriptor(propertyName, readMethod, writeMethod);
    }

    /**
     * Returns descriptors for all properties of the bean.
     * @param beanClass The bean class to be analyzed
     * @return an array of {@code PropertyDescriptor} objects
     * @throws IntrospectionException if an exception occurs during introspection.
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> beanClass) throws IntrospectionException {
        return Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
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
