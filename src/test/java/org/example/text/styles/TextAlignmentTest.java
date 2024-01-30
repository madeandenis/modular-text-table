package org.example.text.styles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextAlignmentTest {
    private static final String whiteSpace = ".";

    private static final int testContainerWidth1 = 10;
    private static final int testContainerWidth2 = 5;

    @Test
    void left_shouldAlignLeft() {
        String test = "text";

        String expectedString1 = ".text.....";
        String expectedString2 = "text.";
        String expectedString3 = "text";

        int testContainerWidth3 = test.length();

        assertEquals(expectedString1,TextAlignment.left(test,testContainerWidth1,whiteSpace));
        assertEquals(expectedString2,TextAlignment.left(test,testContainerWidth2,whiteSpace));
        assertEquals(expectedString3,TextAlignment.left(test,testContainerWidth3,whiteSpace));
    }

    @Test
    void center_shouldAlignCenter() {
        String test = "text";
        String whiteSpace = ".";

        int testContainerWidth3 = test.length();

        String expectedString1 = "...text...";
        String expectedString2 = "text.";
        String expectedString3 = "text";

        assertEquals(expectedString1,TextAlignment.center(test,testContainerWidth1,whiteSpace));
        assertEquals(expectedString2,TextAlignment.center(test,testContainerWidth2,whiteSpace));
        assertEquals(expectedString3,TextAlignment.center(test,testContainerWidth3,whiteSpace));
    }

    @Test
    void right_shouldAlignRight() {
        String test = "text";
        String whiteSpace = ".";

        int testContainerWidth3 = test.length();

        String expectedString1 = ".....text.";
        String expectedString2 = ".text";
        String expectedString3 = "text";

        assertEquals(expectedString1,TextAlignment.right(test,testContainerWidth1,whiteSpace));
        assertEquals(expectedString2,TextAlignment.right(test,testContainerWidth2,whiteSpace));
        assertEquals(expectedString3,TextAlignment.right(test,testContainerWidth3,whiteSpace));
    }
}