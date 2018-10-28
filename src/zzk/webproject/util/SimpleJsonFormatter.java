package zzk.webproject.util;

import zzk.webproject.air.AirAccountEventMessage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SimpleJsonFormatter {
    private static final List<Class<?>> allowedType = new ArrayList<>();

    static {
        allowedType.add(Byte.class);
        allowedType.add(Short.class);
        allowedType.add(Integer.class);
        allowedType.add(Long.class);

        allowedType.add(Integer.class);
        allowedType.add(Double.class);

        allowedType.add(Character.class);
        allowedType.add(Boolean.class);

        allowedType.add(String.class);
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
                if (allowedType.stream().anyMatch(fieldType::equals)) {
                    buildingJsonString.append("\"");
                    buildingJsonString.append(currentField.getName());
                    buildingJsonString.append("\"");
                    buildingJsonString.append(":");
                    buildingJsonString.append("\"");
                    buildingJsonString.append(currentField.get(object));
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
