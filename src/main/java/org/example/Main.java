package org.example;

import org.example.table.model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        List<String> testList = new ArrayList<>(Arrays.asList("marian","bir","adin","marian","bir","adin"));

        Table testTable = new Table();

//        testTable.setColumnHeaders(5,"marian","bir","adin","marian","bir","adin");
//        testTable.addRow(testList);
//        testTable.addRow(testList);

        testTable.addColumn("222",testList);
        testTable.addColumn("222",testList);

        System.out.println(testTable);


    }
    public static void Tests(){

    }
}