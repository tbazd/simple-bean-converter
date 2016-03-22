package com.tbazd.utils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * Simple bean converter.
 * Created on 3/19/16.
 *
 * @author vknysh
 */
public final class SimpleBeanConverter {

    private SimpleBeanConverter() {}

    public static <T> T convert(Object source, Class<?> sourceClass, Class<T> targetClass) throws Throwable {
        T targetInstance = targetClass.newInstance();
        Method[] sourceClassMethods = sourceClass.getMethods();
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        for (Method method : sourceClassMethods) {
            String methodName = method.getName();
            if (isGetter(methodName) && !"getClass".equals(methodName)) {
                MethodType sourceMethodType = MethodType.methodType(method.getReturnType());
                MethodHandle sourceMethodHandle = lookup.findVirtual(sourceClass, methodName, sourceMethodType);
                Object sourceValue = sourceMethodHandle.invoke(source);

                String targetSetterName = "set" + methodName.substring(3, methodName.length());
                MethodHandle methodHandleTarget = lookup.findVirtual(targetClass,
                        targetSetterName, MethodType.methodType(void.class, method.getReturnType()));
                methodHandleTarget.invoke(targetInstance, sourceValue);
            }
        }

        return targetInstance;
    }

    private static boolean isGetter(String name) {
        return "get".equals(name.substring(0,3));
    }

//    private static boolean isBean(Class<?> klass) {
//
//        sourceValue.getClass().isPrimitive()
//        Method[] sourceClassMethods = sourceValue.getClass().getMethods();
//        for
//        return true;
//    }
}
