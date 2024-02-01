package org.example.table.format;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CellBuilderTest {

    CellBuilder cellBuilder;
    @BeforeEach
    void createBuilder(){
        cellBuilder = new CellBuilder();
    }
    @Test
    void test_EnclosedCell() {
        String EnclosedCell = cellBuilder.EnclosedCell(
                "   test   ",
                10,2,
                '─','║',
                '*','*',
                '*','*',
                ' '
                );
        System.out.println(EnclosedCell);
    }
    @Test
    void test_NoVerticalBorderCell(){
        String NoVerticalBordersCell = cellBuilder.NoVerticalBordersCell(
                "   test   ",
                10,2,
                '─',
                ' '
        );
        System.out.println(NoVerticalBordersCell);
    }

    @Test
    void test_NoTopHorizontalWallCell(){
        String NoTopHorizontalWallCell = cellBuilder.NoTopHorizontalWallCell(
                "   test   ",
                10,2,
                '─','║',
                '*','*',
                ' '
        );
        System.out.println(NoTopHorizontalWallCell);
    }
}