package zzk.webproject.util;

import zzk.webproject.air.AirMessage;
import zzk.webproject.air.MessageType;
import zzk.webproject.pojo.Account;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;
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
     * {"id":"0","username":"username","password":"password"}
     *
     * @param json
     * @return
     */
    public static <T> T fromJsonToObject(String json, Class<T> targetType) throws IllegalArgumentException {
        T instance = null;
        if (json.startsWith("{") && json.endsWith("}")) {
            String withoutBrace = json.substring(json.indexOf("{") + 1, json.lastIndexOf("}")).replace("\"", "");
            String[] keyValuePairs = withoutBrace.split(",");
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
                    Field targetTypeField = targetType.getDeclaredField(key);
                    if (Objects.isNull(instance)) {
                        instance = targetType.getDeclaredConstructor().newInstance();
                    }
                    Class<?> fieldType = targetTypeField.getType();
                    targetTypeField.setAccessible(true);
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

//    public static void main(String[] args) {
//        String jsonString = toJsonString(new AirMessage());
//        AirMessage account = fromJsonToObject(jsonString, AirMessage.class);
//        System.out.println(account);
////        Field[] fields = AirMessage.class.getDeclaredFields();
////        for (Field field : fields) {
////            field.setAccessible(true);
////            System.out.print(field.getType());
////            System.out.print(",");
////            System.out.println(field.getName());
////        }
////
////        try {
////            Field uuid = AirMessage.class.getDeclaredField("uuid");
////            System.out.println(uuid.getType());
////        } catch (NoSuchFieldException e) {
////            e.printStackTrace();
////        }
//    }
}
