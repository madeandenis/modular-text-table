package org.example.text.utils;

import java.util.List;

public class TextUtils {

    public static String repeatCharacter(char character, int count){
        return String.valueOf(character).repeat(count);
    }
    // Removes last character from a string
    public static String chop(String string){
        if (string != null) {
            return string.substring(0, string.length() - 1);
        }
        else {
            return null;
        }
    }
    public static String removeFirstLine(String string) {

        int indexOfNewline = string.indexOf('\n');

        // If there is a newline character, create a substring starting from the position after that index
        if (indexOfNewline != -1) {
            return string.substring(indexOfNewline + 1);
        }

        // If there is no newline character, return the input string as it is (no change)
        return string;
    }

    public static int countRows(String string){
        if(string.isEmpty()){
            return 0;
        }
        else {
            return string.split("\n").length;
        }
    }

    // Obtains row without the end-line char
    public static String getRowFromString(String string, int rowIndex) {
        String[] rows = string.split("\n");

        if (rowIndex >= 0 && rowIndex < rows.length) {
            return rows[rowIndex];
        } else {
            return null; // Invalid row index
        }
    }
    public static String removeCharAt(String originalString, int index){
        if (index < 0 || index >= originalString.length()){
            throw new IndexOutOfBoundsException("Cannot delete char from : " + originalString );
        }
        StringBuilder stringBuilder = new StringBuilder(originalString);
        stringBuilder.deleteCharAt(index);

        return stringBuilder.toString();
    }
    public static String assembleRows(String[] rows) {
        StringBuilder result = new StringBuilder();
        for (String row : rows) {
            result.append(row).append("\n");
        }
        return result.toString();
    }


}
