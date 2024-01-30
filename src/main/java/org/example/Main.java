package org.example;

import org.example.constants.Unicode;
import org.example.table.format.StandardCellFormatter;
import org.example.table.format.StandardTableFormatter;
import org.example.table.model.Cell;
import org.example.table.model.Table;
import org.example.text.format.TextFormatter;
import org.example.text.styles.TextAlignment;
import org.example.text.styles.TextCasing;
import org.example.text.utils.TextUtils;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //Tests();

        Table table = new Table();
        table.addColumn("Denis",9,"1","2","3","4","5");
        table.addColumn("Benisima",3,"1","2","3","4","5");
        table.addColumn("alex",20,"1","2","3","4","5");
        table.addColumn("alex",0,"1","2","3","4","5");

        System.out.println(table.getTableHeaders());

        //table.setHeaders(2,"Andrei","Mariaana","Bogdan","Alex","Mariaana","Bogdan","Alex");

        System.out.println(Arrays.toString(table.getTableDimensions()));


        table.addRow("1","2","3","4");
//        table.addRow("11111199","2","3","4","5","6","7");



        // TODO: Fix cell formatter stuck on bottom cells
        //System.out.println(table.getCell(2,5).getCellType());


        //table.displayData();


        System.out.println(table);;


    }
    public static void Tests(){

    }
}