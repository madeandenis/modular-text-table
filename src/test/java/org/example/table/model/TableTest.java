package org.example.table.model;

import org.example.text.format.TextFormatter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class TableTest {

    String[] headersArr = {"Name", "Age", "City"};
    List<String> headersList = Arrays.asList(headersArr);

    void displayTableResult(Table table){
        System.out.println(table);
        System.out.println();
    }
    void displayTableInfo(Table table){
        System.out.println("Table Headers :" +table.getHeadersStrings());
        table.displayTableDimensions();
    }

    @Test
    void test_NonMatrixConstructors(){
        int headerPadding = 15;

        System.out.println("Default constructor");
        Table testTable = new Table();
        displayTableResult(testTable);

        System.out.println("Varargs using custom header padding");
        testTable = new Table(headerPadding,"Name", "Age", "City");
        displayTableResult(testTable);

        System.out.println("Varargs using default header padding");
        testTable = new Table(Table.DEFAULT_PADDING,"Name", "Age", "City");
        displayTableResult(testTable);

        System.out.println("String list using custom header padding");
        testTable = new Table(headerPadding, headersArr);
        displayTableResult(testTable);

        System.out.println("String list using default header padding");
        testTable = new Table(Table.DEFAULT_PADDING,headersArr);
        displayTableResult(testTable);

        displayTableInfo(testTable);

    }

    Object[][] dataMatrixArr = {
            {"John", 35, "NY"},
            {"Alice", 28, "LA"},
            {"Michael", 40, "CHI"},
            {null, 32, "SF"},
            {"David", 45, "BOS"},
            {"Sarah", 31, "SEA"},
            {"Daniel", null, "AUS"},
            {null, null, null}
    };

    public static List<List<Object>> convertToMatrixList(Object[][] array) {
        List<List<Object>> matrixList = new ArrayList<>();
        for (Object[] row : array) {
            List<Object> rowList = new ArrayList<>();
            for (Object element : row) {
                rowList.add(element);
            }
            matrixList.add(rowList);
        }
        return matrixList;
    }

    @Test
    void test_MatrixConstructors(){
        Table testTable;
        int headerPadding = 15;

        // Table Orientation Testing using Object[][] matrix
        testTable = new Table(headerPadding,headersList,dataMatrixArr, Table.TableOrientation.ROWS_AS_ROWS);
        displayTableResult(testTable);

        testTable = new Table(headerPadding,
                new ArrayList<>(Arrays.asList("1","2","3","4","5","6","7","8")),
                dataMatrixArr,
                Table.TableOrientation.ROWS_AS_COLUMNS);
        displayTableResult(testTable);

        List<List<Object>> matrixList = convertToMatrixList(dataMatrixArr);


        // List<List<Object>> matrix
        testTable = new Table(headerPadding,
                headersList,
                convertToMatrixList(dataMatrixArr),
                Table.TableOrientation.ROWS_AS_ROWS);
        displayTableResult(testTable);

    }

    @Test
    void test_TableHeaderSetters(){
        Table testTable = new Table();
        testTable.setHeaders(Table.DEFAULT_PADDING,headersArr);
        displayTableResult(testTable);

        testTable.setHeaders(10,headersList);
        displayTableResult(testTable);

        testTable.replaceHeader(1,Table.DEFAULT_PADDING,"ReplacedHeader");
        displayTableResult(testTable);


        displayTableInfo(testTable);
    }

    @Test
    void test_DisplayMethods(){
        Table testTable = new Table(15,headersList,dataMatrixArr, Table.TableOrientation.ROWS_AS_ROWS);
        testTable.displayTableWithoutHeaders();
        testTable.displayTableData();
        testTable.displayTableDimensions();
    }

    @Test
    void test_rowManipulation(){
        Table testTable = new Table();
        testTable.setHeaders(10,headersList);
        // Row Addition
        testTable.addRow(Arrays.asList(dataMatrixArr[0]));
        testTable.addRow(dataMatrixArr[1]);
        testTable.addRow("Michael","40","Chicago");

        displayTableResult(testTable);

        // Row Insertion
        testTable.insertRow(0,Arrays.asList(dataMatrixArr[0]));
        testTable.insertRow(1,dataMatrixArr[1]);
        testTable.insertRow(2,"Michael","40","Chicago");

        displayTableResult(testTable);

        testTable.removeRow(0);
        testTable.removeRow(1);
        testTable.removeRow(2);

        displayTableResult(testTable);

        testTable.removeLastRow();
        testTable.removeFirstRow();
        testTable.removeLastRow();

        System.out.println(testTable);

    }

    @Test
    void test_ColumnManipulation(){
        Table testTable = new Table();

        testTable.addColumn(headersArr[0],10,Arrays.asList(dataMatrixArr[0]));
        testTable.addColumn(headersArr[1],10,"Alice","28","Los Angeles");
        testTable.addColumn(headersArr[2],10, dataMatrixArr[2]);

        testTable.replaceHeader(1,10,"ReplacedHeader");
        displayTableResult(testTable);

        testTable.removeColumn(1);
        displayTableResult(testTable);

        // Removes the table completely
        testTable.removeFirstColumn();

        // Insertion
        System.out.println("Insertion table");
        // Single column insertion
        testTable.insertColumn(0,headersArr[0],10,Arrays.asList(dataMatrixArr[0]));
        // Append cell
        testTable.insertColumn(testTable.getTableWidth(),headersArr[1],10,Arrays.asList(dataMatrixArr[1]));
        testTable.insertColumn(testTable.getTableWidth(),headersArr[2],10,Arrays.asList(dataMatrixArr[2]));
        testTable.insertColumn(testTable.getTableWidth(),headersArr[0],10,Arrays.asList(dataMatrixArr[0]));
        // Middle cell insertion
        testTable.insertColumn(1,"InsertionAt1",10,Arrays.asList(dataMatrixArr[1]));
        testTable.insertColumn(3,"InsertionAt3",10,"VarArg1","VarArg2","VarArg3");
        testTable.insertColumn(0,"InsertionAt0",10,dataMatrixArr[1]);

        displayTableResult(testTable);

        // Should remove the insertions
        testTable.removeColumn(4);
        testTable.removeColumn(2);
        testTable.removeColumn(0);

        displayTableResult(testTable);

        // Should remove "Name" start/end columns
        testTable.removeFirstColumn();
        testTable.removeLastColumn();


        displayTableResult(testTable);
        displayTableInfo(testTable);

    }

    @Test
    void test_TableStyling_Casing(){
        Table testTable = new Table(5,headersList,dataMatrixArr, Table.TableOrientation.ROWS_AS_ROWS);

        testTable.setHeadersStyle(Table.CasingStyle.LOWER_CASE);
        testTable.displayTableOnlyHeaders();
        testTable.setHeadersStyle(Table.CasingStyle.UPPER_CASE);
        testTable.displayTableOnlyHeaders();
        testTable.setHeadersStyle(Table.CasingStyle.CAPITALIZE);
        testTable.displayTableOnlyHeaders();

        testTable.setColumnStyle(0, Table.CasingStyle.UPPER_CASE);
        testTable.setColumnStyle(1, Table.CasingStyle.CAPITALIZE);
        testTable.setColumnStyle(2, Table.CasingStyle.LOWER_CASE);
        displayTableResult(testTable);

        // TODO : fix style clearing bug not working
//        testTable.clearStyling();
//        displayTableResult(testTable);

        testTable.equalizeColumnWidths();
        displayTableResult(testTable);

        testTable.setColumnWidth(1,20);
        displayTableResult(testTable);

        testTable.setHeadersHeight(2);
        displayTableResult(testTable);

        testTable.setHeadersWidth(30);
        displayTableResult(testTable);

    }
    @Test
    void test_TableStyling_Alignment(){
        Table testTable = new Table(9,headersList,dataMatrixArr, Table.TableOrientation.ROWS_AS_ROWS);
        testTable.alignColumn(0, Table.ColumnAlign.LEFT);
        testTable.alignColumn(1, Table.ColumnAlign.CENTER);
        testTable.alignColumn(2, Table.ColumnAlign.RIGHT);
        displayTableResult(testTable);

        testTable.alignTable(Table.ColumnAlign.CENTER);
        displayTableResult(testTable);

        testTable.alignTable(Table.ColumnAlign.DEFAULT);
        displayTableResult(testTable);

        testTable.alignTable(Table.ColumnAlign.RIGHT);
        testTable.clearAlignments();
        displayTableResult(testTable);

    }

    @Test
    void readMe_examples(){
        String[] headers = {"EmployeeID", "FullName", "Age", "IsManager", "Salary"};
        Object[][] dataRows = {
                {1863, "Denis", 31, true,  55000.50},
                {1952, "Alex",  22, false, 48000.75},
                {1132, "Alice", 46, true,  62000.25}
        };
        int headerPadding = 5;

        Table table = new Table(headerPadding, headers, dataRows, Table.TableOrientation.ROWS_AS_ROWS);

        table.setHeadersWidth(10);
        table.setHeadersHeight(2);

        System.out.println(table);
    }


}