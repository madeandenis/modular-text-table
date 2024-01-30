package org.example.text.styles;

public class TextCasing {
    public static String toAllUpperCase(String text) {
        return text.toUpperCase();
    }

    public static String toAllLowerCase(String text) {
        return text.toLowerCase();
    }

    public static String toCapitalizedText(String text) {
        return Character.toUpperCase(text.charAt(0)) + text.substring(1).toLowerCase();
    }
}
