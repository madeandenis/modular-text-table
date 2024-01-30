package org.example.text.format;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TextFormatterTest {

    private static final String testString = "Text";
    private static final Integer testInteger = 1234;
    private static final Double testDouble = 12.34;
    private static final Boolean testBoolean = true;
    private static final String defaultWhiteSpace = " ";
    private static final String testWhiteSpace = ".";
    private static final TextFormatter.Alignment defaultAlignment = TextFormatter.Alignment.CENTER;

    private TextFormatter<Object> formatter;
    @BeforeEach
    void setUp() {
        formatter = new TextFormatter<>();
    }


    @Test
    void testMethods(){

        // Integer data
        formatter.updateData(testInteger);
        assertEquals(testInteger.toString(),formatter.getStringData());

        // Double data
        formatter.updateData(testDouble);
        assertEquals(testDouble.toString(),formatter.getStringData());

        // Boolean data
        formatter.updateData(testBoolean);
        assertEquals(testBoolean.toString(),formatter.getStringData());

        // String data
        formatter.updateData(testString);
        assertEquals(testString,formatter.getStringData());

        // GetContainerWidth
        assertEquals(testString.length(),formatter.getContainerWidth());

        // GetFormattedData after ContainerWidth,Alignment,testWhitespace being applied
        formatter.setContainerWidth(10);

        formatter.setAlignmentState(TextFormatter.Alignment.RIGHT);
        formatter.setAlignmentState(TextFormatter.Alignment.CENTER);

        formatter.setWhiteSpace(testWhiteSpace);

        formatter.upperCase();
        assertEquals("...TEXT...",formatter.getFormattedData());
        formatter.lowerCase();
        assertEquals("...text...",formatter.getFormattedData());
        formatter.capitalize();
        assertEquals("...Text...",formatter.getFormattedData());

        // Expands the container width
        formatter.setContainerWidth(12);
        assertEquals("....Text....",formatter.getFormattedData());
        // Updates the formatter with new data/default width
        formatter.updateData(testInteger);
        assertEquals(testInteger.toString(),formatter.getFormattedData());

    }


    @Test
    void test_defaultConstructorWithDefaultValues() {
        assertEquals(defaultWhiteSpace,formatter.getWhiteSpace());
        assertNull(formatter.getStringData());
        assertEquals(0,formatter.getContainerWidth());
        assertEquals(defaultAlignment,formatter.getAlignmentState());

        testMethods();
    }
    @Test
    void test_parameterizedConstructor() {
        formatter = new TextFormatter<>(testDouble,testDouble.toString().length());
        testMethods();
    }


}