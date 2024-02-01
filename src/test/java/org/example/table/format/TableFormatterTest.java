package org.example.table.format;

import org.example.table.model.Cell;
import org.example.text.styles.TableStyles;
import org.example.text.utils.TextUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TableFormatterTest {

    TableFormatter formatter;
    TableStyles tableStyle = TableStyles.BoxDrawing;
    int cellHeight = 5;

    List<String> headers = new ArrayList<>(Arrays.asList("Denis","1","Tester"));
    List<String> rowData = new ArrayList<>(Arrays.asList("1","2","3"));
    List<Integer> rowPadding = new ArrayList<>();


    @BeforeEach
    void init(){
        formatter = new TableFormatter();
    }

    @Test
    void test_VerticalCellSpacer(){
        String headerSpacer = formatter.verticalCellSpacer(Cell.CellType.MIDDLE_CENTER,tableStyle,cellHeight);
        System.out.println(headerSpacer);
    }

    @Test
    void test_formatTable_HeadersAndRows(){
        List<Cell<?>> tableHeaders = formatter.convertHeaderDataToCellList(headers,5);

        for (Cell<?> header : tableHeaders) {
            rowPadding.add(TextUtils.calculatePadding("1",header.getCellWidth()));
        }

        List<Cell<?>> tableRow = formatter.convertDataToCellList(
                Cell.CellType.MIDDLE_SINGLE,rowData,rowPadding);

        String formattedTableHeaders = formatter.formatTableRow(tableHeaders);
        String formattedTableRow = formatter.formatTableRow(tableRow);

        System.out.println(formattedTableHeaders);
        System.out.println(formattedTableRow);
    }



}