package org.example.text.utils;

import java.util.List;

public class TextUtils {

    public static int calculatePadding(String cellContent, int headerWidth){
        return headerWidth - cellContent.length();
    }
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
    /* No actual usage - to delete
    public static String removeLinesFromString(String originalString, int startIndex, int endIndex) {
        // Split the original string into an array of lines
        String[] lines = originalString.split("\n");

        // Check if the indices are within the valid range
        if (startIndex >= 0 && endIndex < lines.length && startIndex <= endIndex) {
            // Remove the specified lines by creating a new array without the specified elements
            String[] updatedLines = new String[lines.length - (endIndex - startIndex + 1)];
            System.arraycopy(lines, 0, updatedLines, 0, startIndex);
            System.arraycopy(lines, endIndex + 1, updatedLines, startIndex, lines.length - endIndex - 1);

            // Join the lines back into a single string with newline characters
            return String.join("\n", updatedLines);
        } else {
            // Return the original string if the indices are out of bounds or in the wrong order
            return originalString;
        }
    }

     */

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

    /* No actual usage - to delete
    public static String findLongestString(List<String> stringList) {
        if (stringList == null || stringList.isEmpty()) {
            return null;
        }

        String longestString = stringList.get(0);

        for (String str : stringList) {
            if (str.length() > longestString.length()) {
                longestString = str;
            }
        }

        return longestString;
    }
     */
}
