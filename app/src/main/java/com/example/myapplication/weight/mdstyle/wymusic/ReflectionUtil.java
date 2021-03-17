package com.example.myapplication.weight.mdstyle.wymusic;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {

    /**
     * 反射属性
     *
     * @param className
     * @param fieldName
     * @return
     */
    public static Field reflectionField(Class className, String fieldName) {
        Field field = null;
        try {
            field = className.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return field;
    }

    /**
     * 反射方法
     *
     * @param className
     * @param methodName
     * @param values
     * @return
     */
    public static Method reflectionMethod(Class className, String methodName, Class<?>... values) {
        Method method = null;

        try {
            method = className.getMethod(methodName, values);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    /**
     * 属性赋值
     *
     * @param o
     * @param field
     * @param value
     */
    public static void setField(Object o, Field field, Object value) {
        try {
            field.set(o, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 调用方法
     *
     * @param o
     * @param method
     * @param value
     */
    public static Object setMethod(Object o, Method method, Object... value) {
        Object invoke = null;
        try {
            invoke = method.invoke(o, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return invoke;
    }

    /**
     * 获取属性值
     *
     * @param o
     * @param field
     * @return
     */
    public static Object getField(Object o, Field field) {
        Object value = null;
        try {
            value = field.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }
}
