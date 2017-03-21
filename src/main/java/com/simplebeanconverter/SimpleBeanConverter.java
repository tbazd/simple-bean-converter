package com.simplebeanconverter;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Simple bean converter.
 * Created on 3/19/16.
 *
 * @author vknysh
 */
public final class SimpleBeanConverter {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private SimpleBeanConverter() {}

    public static <T> T convert(Object source, Class<T> targetClass) {
        return convert(source, targetClass, new HashMap<>(), new HashMap<>());
    }

    public static <T> T convert(Object source, Class<T> targetClass, Rules rules) {
        return convert(source, targetClass, rules.getRules(), rules.getEnclosed());
    }

    /**
     * Main converter method.
     * Converter may receive map with additional rules.
     *
     * @param source source object, which would be converted
     * @param targetClass target object class
     * @param rules rules map, that setup additional converter behaviour
     * @return instance of target class
     */
    public static <T> T convert(Object source, Class<T> targetClass, Map<String, String> rules, Map<String, Class<?>> enclosed) {
        T targetInstance;
        try {
            targetInstance = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Exception target class instantiating: " + targetClass.getName());
        }
        Class sourceClass = source.getClass();
        Method[] sourceClassMethods = sourceClass.getMethods();

        for (Method method : sourceClassMethods) {
            String methodName = method.getName();
            if (isGetter(methodName) && !"getClass".equals(methodName)) {
                String fieldName = lowercaseFirstLetter(methodName.substring(3, methodName.length()));
                Object sourceValue = getSourceValue(method, sourceClass, source);

                Object targetValue;
                Class<?> targetType;
                if (!rules.isEmpty() && rules.containsKey(fieldName)) {
                    targetValue = rulesProcessing(sourceValue, rules.get(fieldName));
                    targetType = targetValue.getClass();
                } else if (!enclosed.isEmpty() && enclosed.containsKey(fieldName) && sourceValue instanceof List) {
                    List<Object> targetValueList = new ArrayList<>();
                    for (Object sourceValueElem : (List) sourceValue) {
                        targetValueList.add(convert(sourceValueElem, enclosed.get(fieldName)));
                    }
                    targetValue = targetValueList;
                    targetType = List.class;
                } else if (!enclosed.isEmpty() && enclosed.containsKey(fieldName)) {
                    targetValue = convert(sourceValue, enclosed.get(fieldName));
                    targetType = targetValue.getClass();
                } else {
                    targetValue = sourceValue;
                    targetType = method.getReturnType();
                }

                try {
                    applyValue(targetClass, methodName, targetType, targetInstance, targetValue);
                } catch (Throwable e) {
                    throw new IllegalStateException("Exception during setting field from method " + methodName +
                            ".\n" + e.getMessage());
                }
            }
        }

        return targetInstance;
    }

    private static Object getSourceValue(Method method, Class sourceClass, Object source) {
        MethodType sourceMethodType = MethodType.methodType(method.getReturnType());
        Object sourceValue;
        try {
            MethodHandle sourceMethodHandle = LOOKUP.findVirtual(sourceClass, method.getName(), sourceMethodType);
            sourceValue = sourceMethodHandle.invoke(source);
        } catch (Throwable throwable) {
            throw new IllegalArgumentException("Exception during source reading : " + method.getName());
        }
        return sourceValue;
    }

    private static <T> void applyValue(Class<T> targetClass, String methodName, Class<?> targetType,
                                       T targetInstance, Object targetValue) throws Throwable {
        String targetSetterName = "set" + methodName.substring(3, methodName.length());
        try {
            MethodHandle methodHandleTarget = LOOKUP.findVirtual(targetClass,
                    targetSetterName, MethodType.methodType(void.class, targetType));
            methodHandleTarget.invoke(targetInstance, targetValue);
        } catch (NoSuchMethodException e) {
            // If correspond setter in target class isn't found. Just skipping it.
            // Log will be placed here.
        }

    }

    private static Object rulesProcessing(Object sourceObject, String rule) {
        String sourceClassName = sourceObject.getClass().getCanonicalName();
        switch (RuleEnum.valueOf(rule)) {
            case MONGODB:
                if (RuleEnum.MONGODB.getClassCanonicalName().equals(sourceClassName)) {
                    return sourceObject.toString();
                } //else {
                    //TODO: produce ObjectId here
                //}
            case LOCAL_DATE_TIME:
                if (RuleEnum.LOCAL_DATE_TIME.getClassCanonicalName().equals(sourceClassName)) {
                    return Date.from(((LocalDateTime) sourceObject).toInstant(ZoneOffset.UTC));
                } else {
                    return LocalDateTime.ofInstant(((Date) sourceObject).toInstant(), ZoneOffset.UTC);
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

    private enum RuleEnum {

        MONGODB("org.bson.types.ObjectId"),

        LOCAL_DATE_TIME("java.time.LocalDateTime");
        
        private String classCanonicalName;

        RuleEnum(String value) {
            this.classCanonicalName = value;
        }

        public String getClassCanonicalName() {
            return classCanonicalName;
        }

        public static Optional<RuleEnum> getRule(String classCanonicalName) {
            return Arrays.stream(values())
                    .filter(v -> v.classCanonicalName.equals(classCanonicalName))
                    .findFirst();
        }
    }

}
