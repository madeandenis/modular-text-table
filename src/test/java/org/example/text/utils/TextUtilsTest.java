package org.example.text.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextUtilsTest {

    @Test
    void chop_shouldRemoveTrailingNewline() {
        String test = "TextLib\n";
        assertEquals("TextLib",TextUtils.chop(test));
    }

    @Test
    void removeFirstLine_shouldRemoveFirstLine() {
        String test = "row1\nrow2\nrow3\nrow4\n";
        assertEquals("row2\nrow3\nrow4\n",TextUtils.removeFirstLine(test));
    }

    @Test
    void countRows() {
        String test = "row1\nrow2\nrow3\nrow4\n";
        assertEquals(4,TextUtils.countRows(test));

        String emptyString = "";
        assertEquals(0, TextUtils.countRows(emptyString));
    }

    @Test
    void getRowFromString_shouldReturnCorrectRow() {
        String test = "row1\nrow2\nrow3\nrow4\n";
        assertEquals("row3",TextUtils.getRowFromString(test,2));

        // Test with the first and last rows
        assertEquals("row1", TextUtils.getRowFromString(test, 0));
        assertEquals("row4", TextUtils.getRowFromString(test, 3));
    }

}