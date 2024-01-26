package org.example;

import org.example.table.format.StandardCellFormatter;
import org.example.table.model.Cell;
import org.example.text.format.TextFormatter;
import org.example.text.styles.TextAlignment;
import org.example.text.styles.TextCasing;
import org.w3c.dom.Text;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //Tests();
        Cell<String> topCell = new Cell<>("Heelloo",15, Cell.CellType.TOP_SINGLE);
        topCell.setCellHeight(2);
        topCell.setWhiteSpace(".");
        System.out.println(topCell.getCellHeight());
        System.out.println(topCell);

    }
    public static void Tests(){

    }
}