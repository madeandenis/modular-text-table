package org.example.table.manipulation;

import org.example.table.format.TableFormatter;
import org.example.table.model.Cell;
import org.example.table.model.Table;
import org.example.text.utils.TextUtils;
import org.w3c.dom.ls.LSInput;

import java.util.*;

public class TableEditor {
    private final TableFormatter formatter;
    private final Table          table;

    public TableEditor(Table table) {
        this.table     = table;
        this.formatter = table.getTableFormatter();
    }

    /*
        Header Transformation
     */
    public List<Cell<String>> headersToCellList(int padding,List<String> headers){

        List<Cell<String>> cellList = new ArrayList<>();
        for (int headerIndex = 0; headerIndex < headers.size(); headerIndex++) {

            Cell.CellType headerCellType = determineHeaderCellType(headerIndex,headers.size());
            Cell<String> cellHeader = new Cell<>(
                    headers.get(headerIndex),
                    headers.get(headerIndex).length() + padding,
                    headerCellType
            );

            cellList.add(cellHeader);
        }
        return cellList;
    }
    private Cell.CellType determineHeaderCellType(int headerIndex, int headersSize){
        if(headerIndex == 0){
            return Cell.CellType.TOP_LEFT;
        }
        else if(headerIndex == headersSize - 1)
        {
            return Cell.CellType.TOP_RIGHT;
        }
        else {
            return Cell.CellType.TOP_CENTER;
        }
    }
    // Changes header value at a specified index
    public void setHeaderAt(int headerIndex, int padding, String header){
        var tableData = table.getTableData();
        if(tableData.isEmpty()){
            System.out.println("Cannot modify header : Table is empty.");
            return;
        }
        if (headerIndex < 0 || headerIndex > table.getTableWidth()-1){
            System.out.println("Cannot modify header : Header index is invalid");
        }
        List<String>  updatedHeadersList   = new ArrayList<>();
        List<Integer> currHeadersWidths    = new ArrayList<>();

        // Method creates a copy of a string list for the current headers and replaces with new header at requested index
        // Creates integer list for keeping headers widths
        for (var currHeader : table.getHeaders()){
            if(table.getHeaders().indexOf(currHeader) == headerIndex){
                updatedHeadersList.add(header);
            }
            else {
                updatedHeadersList.add(currHeader.getData().toString());
            }
            currHeadersWidths.add(currHeader.getCellWidth());
        }

        // Once the copy is over, the headers are cleared and the updated list is added using padding parameter
        table.getHeaders().clear();
        table.getHeaders().addAll(headersToCellList(padding,updatedHeadersList));

        // The headers need to be restored with the previous width
        for (int column = 0; column < table.getHeaders().size(); column++) {
            var currHeader = table.getHeaders().get(column);

            // The modified header keeps its cell width and cell text container width
            if (column == headerIndex){
                continue;
            }
            else {
                currHeader.setCellWidth(
                        currHeadersWidths.get(column)
                );
                currHeader.getTextFormatter().setContainerWidth(
                        currHeadersWidths.get(column)
                );
            }
        }
    }
    /*
        Row Transformation
     */
    public List<Cell<?>> rowToCellList(List<?> rowData, List<Integer> paddings, Cell.CellType cellType){
        var cellList = formatter.convertDataToCellList(
                cellType,
                rowData,
                paddings
        );
        cellList = formatter.setOrderedCellTypes(
                cellList,
                cellList.get(0).getCellType()
        );
        return cellList;
    }

    /*
        Row Manipulation
        -> Row appending addRow()
            -> validateRowSize()
            -> calculateCellPaddings()
                -> calculatePadding()
     */
    public void addRow(List<?> rowData , Cell.CellType cellType){
        if(table.getHeaders().isEmpty()){
            System.out.println("Cannot add rows without table headers");
            return;
        }
        if (!validateRowSize(rowData)){
            return;
        }

        // Calculate individual cell padding based on its corresponding header cell
        List<Integer> cellPaddings = calculateCellPaddings(rowData);

        // Transform row data into a bottom table row
        var cellRow = rowToCellList(rowData,cellPaddings,cellType);

        // Add cellRow to the table
        table.getTableData().add(cellRow);
    }
    private boolean validateRowSize(List<?> rowData){
        if(rowData.size() > table.getHeaders().size() || rowData.size() < table.getHeaders().size()){
            System.out.println(
                    "Row has more cells than expected. Expected: " + table.getHeaders().size() + " cells.");
            return false;
        }
        return true;
    }
    private List<Integer> calculateCellPaddings(List<?> rowData){
        List<Integer> paddings = new ArrayList<>();

        for (int column = 0; column < rowData.size(); column++) {
            int headerWidth   = table.getHeaders().get(column).getCellWidth();
            Object rowElement = rowData.get(column);

            if(rowElement == null){
                paddings.add(
                        calculatePadding("null",headerWidth)
                );
            }
            else {
                paddings.add(
                        calculatePadding(rowData.get(column).toString(),headerWidth)
                );
            }
        }
        return paddings;
    }
    private static int calculatePadding(String cellContent, int headerWidth){
        return headerWidth - cellContent.length();
    }
    /*
        Row Deletion
     */
    public void deleteRow(int rowIndex){
        if( table.getTableData().isEmpty() || (rowIndex < 0 || rowIndex >= table.getTableHeight())){
            System.out.println("Row at index : " + rowIndex + " does not exist");
        }
        else{
            table.getTableData().remove(rowIndex);
        }
    }
    
    // TODO: create row/column insertion beside appending
    /*
        Column Manipulation
        -> Column appending addColumn()
            -> addSingleColumn()
            -> appendColumn
         
     */
    public void addColumn(String header, int headerPadding, List<?> columnData){
        // If table is empty, add a single styled colum line
        if (table.getTableData().isEmpty()){
            addSingleColumn(header,headerPadding,columnData);
        }
        // Else, append the column to the table end
        else {
            appendColumn(header,headerPadding,columnData);
        }
    }
    private void addSingleColumn(String header, int headerPadding, List<?> columnData){

        table.setHeaders(headerPadding, Collections.singletonList(header));

        for (int column = 0; column < columnData.size(); column++) {
            var columnElement = columnData.get(column);
            Cell.CellType rowType;
            if(column == columnData.size()-1){
                rowType = Cell.CellType.BOTTOM_SINGLE;
            }
            else {
                rowType = Cell.CellType.MIDDLE_SINGLE;
            }

            addRow(Collections.singletonList(columnElement),rowType);
        }
        
    }
    private void appendColumn(String header, int headerPadding, List<?> columnData){
        if(!validateColumn(columnData)){
            System.out.println(
                    "Column input exceeds table height. Required column height : " + table.getTableHeight()
            );
        }
        else{
            Cell<String> cellHeader = new Cell<>(header,header.length()+headerPadding, Cell.CellType.TOP_SINGLE);

            int headerCellWidth = header.length() + headerPadding;

            // Append new header
            table.getHeaders().add(cellHeader);

            for (var columnElement : columnData){
                Cell<?> columnCell;
                if(Objects.isNull(columnElement)){
                    columnCell = addNulLCell(headerCellWidth);
                }
                else {
                    columnCell = new Cell<>(
                            columnElement,
                            columnElement.toString().length() + calculatePadding(columnElement.toString(),headerCellWidth),
                            Cell.CellType.TOP_SINGLE
                    );
                }

                // Add new columnCell to the table
                table.getRow(columnData.indexOf(columnElement)).add(columnCell);
            }
            // If the newly added column is shorter than the other columns in the table, append null cells to ensure equal column heights.
            if(columnData.size() < table.getTableHeight()){
                int heightDifference = table.getTableHeight() - columnData.size();
                for (int i = 0; i < heightDifference; i++) {
                    table.getRow(columnData.size() + i).add(addNulLCell(headerCellWidth));
                }
            }
        }
    }
    private Cell<?> addNulLCell(int headerWidth){
        return new Cell<>(
                "null",
                "null".length() + calculatePadding("null",headerWidth),
                Cell.CellType.MIDDLE_SINGLE
        );
    }
    private boolean validateColumn(List<?> columnData){
        return columnData.size() <= table.getTableHeight();
    }

    /*
        Update Table
        -> updateAndFixTable()
            -> adjustBottomCells()
        -> applyCorrectColumnWidth(){
     */
    public void updateAndFixTable(){
        adjustBottomCells();
    }
    /*
    First if ->
         After a row addition, the bottom cells, now middle cells, require a type change.
         They are set to the middle cell type and subsequently formatted from left to right.
    Second if ->
         In contrary after a row deletion the middle cells now become the bottom cells and require a type change.
     */
    public void adjustBottomCells(){
        var tableData = table.getTableData();
        for (var row : tableData){
            if(tableData.indexOf(row) < tableData.size()-1) {
                for (var cell : row) {
                    cell.setCellType(Cell.CellType.MIDDLE_SINGLE);
                }
            }
            if(tableData.indexOf(row) == tableData.size()-1){
                for (var cell : row) {
                    cell.setCellType(Cell.CellType.BOTTOM_SINGLE);
                }
            }
        }
    }
    // Check if the column cells has the same width as the headers;
    // if not adjust the cell width with their header
    public void applyCorrectColumnWidth(){
        var tableData = table.getTableData();

        if (tableData.isEmpty()){
            return;
        }
        for (int row = 0; row < tableData.size(); row++) {
            for (int column = 0; column < table.getRow(row).size(); column++) {
                if(!checkCorrectColumnWidth(row,column)){
                    int headerWidth = table.getHeaders().get(column).getCellWidth();

                    table.getCell(row,column).setCellWidth(headerWidth);
                    table.getCell(row,column).getTextFormatter().setContainerWidth(headerWidth);

                }
            }
        }
    }
    private boolean checkCorrectColumnWidth(int rowIndex, int columnIndex){

        int columnCellWidth = table.getCell(rowIndex,columnIndex).getCellWidth();
        int headerCellWidth = table.getHeaders().get(columnIndex).getCellWidth();

        return columnCellWidth == headerCellWidth;

    }
}
