package com.tbazd.utils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * Simple bean converter.
 * Created on 3/19/16.
 *
 * @author vknysh
 */
public final class SimpleBeanConverter {

    private SimpleBeanConverter() {}

    /**
     * Main converter method.
     * Converter may receive map with additional rules.
     *
     *
     * @param source source object, which would be converted
     * @param sourceClass source object class
     * @param targetClass target object class
     * @param rules rules map, that setup additional converter behaviour
     * @return instance of target class
     * @throws Throwable
     */
    public static <T> T convert(Object source, Class sourceClass, Class<T> targetClass, Map<String, String> rules)
            throws Throwable {
        T targetInstance = targetClass.newInstance();
        Method[] sourceClassMethods = sourceClass.getMethods();
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        for (Method method : sourceClassMethods) {
            String methodName = method.getName();
            if (isGetter(methodName) && !"getClass".equals(methodName)) {
                String fieldName = lowercaseFirstLetter(methodName.substring(3, methodName.length()));
                MethodType sourceMethodType = MethodType.methodType(method.getReturnType());
                MethodHandle sourceMethodHandle = lookup.findVirtual(sourceClass, methodName, sourceMethodType);
                Object sourceValue = sourceMethodHandle.invoke(source);

                Object targetValue;
                Class<?> targetType;
                if (!rules.isEmpty() && rules.containsKey(fieldName)) {
                    targetValue = rulesProcessing(sourceValue, rules.get(fieldName));
                    targetType = targetValue.getClass();
                } else {
                    targetValue = sourceValue;
                    targetType = method.getReturnType();
                }

                String targetSetterName = "set" + methodName.substring(3, methodName.length());

                try {
                    MethodHandle methodHandleTarget = lookup.findVirtual(targetClass,
                            targetSetterName, MethodType.methodType(void.class, targetType));
                    methodHandleTarget.invoke(targetInstance, targetValue);
                } catch (NoSuchMethodException e) {
                    // Log will be placed here.
                }
            }
        }

        return targetInstance;
    }

    private static Object rulesProcessing(Object sourceObject, String rule) {
        switch (Rule.valueOf(rule)) {
            case MONGODB:
                if (Rule.MONGODB.getClassCanonicalName().equals(sourceObject.getClass().getCanonicalName())) {
                    return sourceObject.toString();
                }
            default:
                return sourceObject;
        }
    }

    private static boolean isGetter(String name) {
        return "get".equals(name.substring(0,3));
    }

    private static String lowercaseFirstLetter(String input) {
        return Character.toLowerCase(input.charAt(0)) + input.substring(1);
    }

    private enum Rule {

        MONGODB("org.bson.types.ObjectId");
        
        private String classCanonicalName;

        Rule(String value) {
            this.classCanonicalName = value;
        }

        public String getClassCanonicalName() {
            return classCanonicalName;
        }

        public static Optional<Rule> getRule(String classCanonicalName) {
            return Arrays.asList(values())
                    .stream()
                    .filter(v -> v.classCanonicalName.equals(classCanonicalName))
                    .findFirst();
        }
    }

}
