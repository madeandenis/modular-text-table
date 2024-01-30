package org.example.table.model;

import org.example.text.format.TextFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    private static final String testNull = null;
    private static final String testString = "text";
    private static final Integer testInteger = 1234;
    private static final Double testDouble = 12.34;
    private static final Boolean testBoolean = true;
    private static final String defaultWhiteSpace = " ";
    private static final String testWhiteSpace = ".";
    private static final int cellWidth = 10;
    private List<Object> testCases;

    @BeforeEach
    void setUpTestCases(){
        testCases = new ArrayList<>();
        testCases.add(testNull);
        testCases.add(testString);
        testCases.add(testInteger);
        testCases.add(testDouble);
        testCases.add(testBoolean);
    }

    @Test
    void testingCellCreations_PrintingAllPossibleCellTypes() {
        System.out.println("testing_Cell_Creations_Printing_All_Possible_CellTypes");
        // Testing all cellTypes from enum CellType
        for (Cell.CellType cellType : Cell.CellType.values()) {
            for (Object testCase : testCases) {

                if(testCase != null) {
                    System.out.println(cellType + " for " + testCase.getClass());
                }
                else{
                    System.out.println(cellType);
                }

                Cell<Object> testCell = new Cell<>(testCase,
                        cellWidth,
                        cellType);

                // Display the cell
                System.out.println(testCell+System.lineSeparator());
            }
        }
    }

    @Test
    void testingCellTextFormatterInSingleColumn() {

        System.out.println("testing_Cell_TextFormatter_In_Single_Column");

        Cell.CellType cellTypeTOP = Cell.CellType.TOP_SINGLE;
        Cell.CellType cellTypeMIDDLE = Cell.CellType.MIDDLE_SINGLE;
        Cell.CellType cellTypeBOTTOM = Cell.CellType.BOTTOM_SINGLE;

        List<Cell.CellType> singleCellType = new ArrayList<>();
        singleCellType.add(cellTypeTOP);
        singleCellType.add(cellTypeMIDDLE);
        singleCellType.add(cellTypeBOTTOM);


        for (Object testCase : testCases) {
            for (Cell.CellType cellType : singleCellType) {
                Cell<Object> testCell = new Cell<>(testCase,
                        cellWidth,
                        cellType);
                // Reset the data for double-checking
                testCell.setData(testCase);

                // Set of cell modifications
                List<Consumer<TextFormatter<?>>> alignmentMethods = List.of(
                        textFormatter -> testCell.setCellHeight(2),
                        textFormatter -> testCell.text().alignLeft(),
                        textFormatter -> testCell.text().alignCenter(),
                        textFormatter -> testCell.text().alignRight(),
                        textFormatter -> testCell.text().capitalize(),
                        textFormatter -> testCell.text().lowerCase(),
                        textFormatter -> testCell.text().upperCase(),
                        textFormatter -> testCell.text().setWhiteSpace("."),
                        textFormatter -> testCell.text().setDefault(),
                        textFormatter -> testCell.setCellHeight(3)
                );

                // Applying modification list one by one
                for (Consumer<TextFormatter<?>> alignmentMethod : alignmentMethods) {
                    alignmentMethod.accept(testCell.getTextFormatter());
                    System.out.println(testCell);
                }
                System.out.println();

            }
        }
    }

}