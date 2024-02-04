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

        table.setHeader(-1,30,"test");


        displayTableResult(table);
    }


}