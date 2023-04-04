package utils;

public class HashUtils {
    public static int createHash(Object key) {
        return key == null ? 0 : key.hashCode();
    }
}
