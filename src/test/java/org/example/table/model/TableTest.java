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
            {"John", 35, "New York"},
            {"Alice", 28, "Los Angeles"},
            {"Michael", 40, "Chicago"},
            {"Emily", 32, "San Francisco"},
            {"David", 45, "Boston"},
            {"Sarah", 31, "Seattle"},
            {"Daniel", 38, "Austin"},
            {"Jessica", 27, "Miami"}
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

        testTable.removeFirstRow();
        testTable.removeFirstRow();
        testTable.removeRow(0);

        System.out.println(testTable);

    }


}