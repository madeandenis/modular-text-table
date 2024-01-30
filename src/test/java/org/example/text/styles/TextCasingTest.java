package org.example.text.styles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextCasingTest {

    @Test
    void toAllUpperCase() {
        String test = "text";
        assertEquals("TEXT",TextCasing.toAllUpperCase(test));
    }

    @Test
    void toAllLowerCase() {
        String test = "TEXT";
        assertEquals("text",TextCasing.toAllLowerCase(test));
    }

    @Test
    void toCapitalizedText() {
        String test = "text";
        assertEquals("Text",TextCasing.toCapitalizedText(test));
    }
}