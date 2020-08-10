package io.leego.mypages.util;

/**
 * @author Yihleego
 */
public final class StringUtils {
    public static final String EMPTY = "";
    public static final String NULL = null;
    public static final String LINE_BREAK = "[\n\r]";

    private StringUtils() {
    }

    public static boolean isNull(CharSequence text) {
        return text == null;
    }

    public static boolean isNotNull(CharSequence text) {
        return text != null;
    }

    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence text) {
        return !isEmpty(text);
    }

    public static boolean isBlank(CharSequence text) {
        if (isEmpty(text)) {
            return true;
        }
        for (int i = 0, len = text.length(); i < len; ++i) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence text) {
        return isNotEmpty(text) && !isBlank(text);
    }

    public static String trim(String text) {
        return text == null ? null : text.trim();
    }

}
