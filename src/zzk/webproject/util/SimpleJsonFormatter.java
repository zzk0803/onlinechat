package zzk.webproject.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class SimpleJsonFormatter {
    private static final Map<Class<?>, Function<String, ?>> ALLOWED_TYPE = new HashMap<>();

    static {
        ALLOWED_TYPE.put(byte.class, (Function<String, Byte>) Byte::parseByte);
        ALLOWED_TYPE.put(Byte.class, (Function<String, Byte>) Byte::parseByte);
        ALLOWED_TYPE.put(short.class, (Function<String, Short>) Short::parseShort);
        ALLOWED_TYPE.put(Short.class, (Function<String, Short>) Short::parseShort);
        ALLOWED_TYPE.put(int.class, (Function<String, Integer>) Integer::parseInt);
        ALLOWED_TYPE.put(Integer.class, (Function<String, Integer>) Integer::parseInt);
        ALLOWED_TYPE.put(long.class, (Function<String, Long>) Long::parseLong);
        ALLOWED_TYPE.put(Long.class, (Function<String, Long>) Long::parseLong);
        ALLOWED_TYPE.put(double.class, (Function<String, Double>) Double::parseDouble);
        ALLOWED_TYPE.put(Double.class, (Function<String, Double>) Double::parseDouble);
        ALLOWED_TYPE.put(char.class, (Function<String, Character>) str -> str.charAt(0));
        ALLOWED_TYPE.put(Character.class, (Function<String, Character>) str -> str.charAt(0));
        ALLOWED_TYPE.put(boolean.class, (Function<String, Boolean>) Boolean::valueOf);
        ALLOWED_TYPE.put(Boolean.class, (Function<String, Boolean>) Boolean::valueOf);
        ALLOWED_TYPE.put(String.class, (Function<String, String>) str -> {
            if ("null".equals(str) || "undefined".equals(str)) {
                return "";
            }
            return str;
        });
    }

    /**
     * 反序列化JSON字符串
     *
     * @param json
     * @return
     */
    public static <T> T fromJsonToObject(String json, Class<T> targetType) throws IllegalArgumentException {
        T instance = null;
        if (json.startsWith("{") && json.endsWith("}")) {
            //截取JSON字符串“”以内的内容
            String withoutBrace = json.substring(json.indexOf("{") + 1, json.lastIndexOf("}")).replace("\"", "");

            //分割数据项
            String[] keyValuePairs = withoutBrace.split(",");

            //分割键值对
            for (String keyValuePair : keyValuePairs) {
                String[] keyAndValue = keyValuePair.split(":");
                String key = keyAndValue[0];
                String value;
                try {
                    value = keyAndValue[1];
                } catch (IndexOutOfBoundsException e) {
                    value = "";
                }

                try {
                    //对应实体类的属性
                    Field targetTypeField = targetType.getDeclaredField(key);
                    if (Objects.isNull(instance)) {
                        instance = targetType.getDeclaredConstructor().newInstance();
                    }
                    Class<?> fieldType = targetTypeField.getType();
                    targetTypeField.setAccessible(true);

                    //为实体类设置属性
                    targetTypeField.set(instance, ALLOWED_TYPE.get(fieldType).apply(value));
                } catch (NoSuchFieldException e) {
                    throw new IllegalArgumentException("传入的类型无法和json匹配");
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    throw new IllegalArgumentException("传入的类型和JSON无法实例化成对象");
                }
            }
        }
        return instance;
    }

    public static String toJsonString(Object object) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        StringBuilder buildingJsonString = new StringBuilder();
        buildingJsonString.append("{");
        for (int index = 0; index < declaredFields.length; index++) {
            Field currentField = declaredFields[index];
            try {
                currentField.setAccessible(true);
                Class<?> fieldType = currentField.getType();
                if (ALLOWED_TYPE.containsKey(fieldType)) {
                    buildingJsonString.append("\"");
                    buildingJsonString.append(currentField.getName());
                    buildingJsonString.append("\"");
                    buildingJsonString.append(":");
                    buildingJsonString.append("\"");
                    Object value = currentField.get(object);
                    if (Objects.nonNull(value)) {
                        buildingJsonString.append(value);
                    } else {
                        buildingJsonString.append("null");
                    }
                    buildingJsonString.append("\"");
                }
                if (index + 1 != declaredFields.length) {
                    buildingJsonString.append(",");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        buildingJsonString.append("}");
        return buildingJsonString.toString();
    }
}
