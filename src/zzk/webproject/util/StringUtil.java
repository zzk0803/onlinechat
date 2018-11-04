package zzk.webproject.util;

public class StringUtil {
    public static boolean isBlank(String string) {
        return string == null || "".equals(string);
    }

    public static boolean nonBlank(String string) {
        return !isBlank(string);
    }
}
