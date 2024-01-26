package org.example.text.styles;

import org.example.text.utils.TextUtils;

public class TextCasing {
    public static String setAllUpperCase(String text) {
        return text.toUpperCase();
    }

    public static String setAllLowerCase(String text) {
        return text.toLowerCase();
    }

    public static String setCapitalizedText(String text) {
        String lowerCaseText = setAllLowerCase(text);
        return Character.toUpperCase(lowerCaseText.charAt(0)) + lowerCaseText.substring(1).toLowerCase();
    }
}
