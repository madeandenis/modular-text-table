package org.example.table.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    String[] stringArr1 = {"One","Two","Three","Test1"};
    Object[] mixedArray = {null, 1234, 12.34, false};

    List<String> stringList1 = Arrays.asList(stringArr1);
    List<?> mixedList = Arrays.asList(mixedArray);


    void displayTableResult(Table table){
        System.out.println();
        System.out.println(table.getHeadersStrings());
        System.out.println(table);
    }

    @Test
    void testTableConstructors_usingHeaders(){
        Table table = new Table();

        table.setHeaders(stringList1);
        table.addRow(mixedList);
        table.addRow(mixedList);
        table.addRow(mixedList);
        table.addRow(mixedList);
        table.addRow(mixedList);

        table.setHeaders(10,stringList1);

        table.replaceHeader(-1,30,"test");


        displayTableResult(table);
    }

    @Test
    void testTableConstructors_usingMatrix(){
        Object[][] matrix = new Object[3][3];

        matrix[0][0] = 1;
        matrix[0][1] = "Hello";
        matrix[0][2] = null;

        matrix[1][0] = "WorldOf";
        matrix[1][1] = 3.14;
        matrix[1][2] = "Java";

        matrix[2][0] = null;
        matrix[2][1] = 42;
        matrix[2][2] = "Matrix";

        String[] headerArr = {"One","Two","Three"};
        List<String> headerList = new ArrayList<>(Arrays.asList(headerArr));

        Table table = new Table(10,headerList,matrix, Table.TableOrientation.ROWS_AS_ROWS);

        table.removeColumn(2);
//        table.removeColumn(1);

        displayTableResult(table);

    }

    @Test
    void test_CellOverflow(){
        Object[][] matrix = new Object[3][3];

        // Populate the matrix with various elements
        matrix[0][0] = 1;
        matrix[0][1] = "HelloHelloHello";
        matrix[0][2] = null;

        matrix[1][0] = "ejfijisjfWFQASFAS";
        matrix[1][1] = 3.14;
        matrix[1][2] = "Java";

        matrix[2][0] = null;
        matrix[2][1] = 42;
        matrix[2][2] = "Matrixaressdssd2";

        String[] headerArr = {"One","Two","Three"};
        List<String> headerList = new ArrayList<>(Arrays.asList(headerArr));

        Table table = new Table(10,headerList,matrix, Table.TableOrientation.ROWS_AS_ROWS);


        displayTableResult(table);
    }


}