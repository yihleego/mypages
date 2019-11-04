package io.leego.mypages.util;

/**
 * @author Yihleego
 */
public final class NumberUtils {
    private NumberUtils() {
    }

    public static boolean isInteger(String text) {
        if (text == null || text.length() == 0) {
            return false;
        }
        int i = 0;
        int len = text.length();
        int limit = -Integer.MAX_VALUE;
        int radix = 10;
        char firstChar = text.charAt(0);
        // Possible leading "+" or "-"
        if (firstChar < '0') {
            if (firstChar == '-') {
                limit = Integer.MIN_VALUE;
            } else if (firstChar != '+') {
                return false;
            }
            if (len == 1) {
                // Cannot have lone "+" or "-"
                return false;
            }
            i++;
        }
        int multmin = limit / radix;
        int result = 0;
        while (i < len) {
            // Accumulating negatively avoids surprises near MAX_VALUE
            int digit = Character.digit(text.charAt(i++), radix);
            if (digit < 0 || result < multmin) {
                return false;
            }
            result *= radix;
            if (result < limit + digit) {
                return false;
            }
            result -= digit;
        }
        return true;
    }

    public static Integer parseInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String && isInteger((String) value)) {
            return Integer.parseInt((String) value);
        }
        return null;
    }

}
