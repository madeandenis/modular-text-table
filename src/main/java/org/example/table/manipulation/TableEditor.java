package org.example.table.manipulation;

import org.example.table.format.TableFormatter;
import org.example.table.model.Cell;
import org.example.table.model.Table;
import org.example.text.format.TextFormatter;
import org.example.text.utils.TextUtils;

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
    public void replaceHeaderAt(int headerIndex, int padding, String header){
        try {
            if (headerIndex < 0 || headerIndex > table.getHeaders().size() - 1) {
                System.out.println("Cannot modify header : Header index is invalid");
                return;
            }
            List<String> updatedHeadersList = new ArrayList<>();
            List<Integer> currHeadersWidths = new ArrayList<>();

            // Method creates a copy of a string list for the current headers and replaces with new header at requested index
            // Creates integer list for keeping headers widths
            for (var currHeader : table.getHeaders()) {
                if (table.getHeaders().indexOf(currHeader) == headerIndex) {
                    updatedHeadersList.add(header);
                } else {
                    updatedHeadersList.add(currHeader.getData().toString());
                }
                currHeadersWidths.add(currHeader.getCellWidth());
            }

            // Once the copy is over, the headers are cleared and the updated list is added using padding parameter
            table.getHeaders().clear();
            table.getHeaders().addAll(headersToCellList(padding, updatedHeadersList));

            // The headers need to be restored with the previous width
            for (int column = 0; column < table.getHeaders().size(); column++) {
                var currHeader = table.getHeaders().get(column);

                // The modified header keeps its cell width and cell text container width
                if (column == headerIndex) {
                    continue;
                } else {
                    currHeader.setCellWidth(
                            currHeadersWidths.get(column)
                    );
                    currHeader.getTextFormatter().setContainerWidth(
                            currHeadersWidths.get(column)
                    );
                }
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Caught IllegalArgumentException: " + e.getMessage());
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
        -> insertRow()
        -> addRow()
            -> handleAddRow()
                -> validateRowSize()
                -> calculateCellPaddings()
                -> calculatePadding()
     */
    public void insertRow(int rowIndex, List<?> rowData , Cell.CellType cellType){
        handleAddRow(rowIndex,rowData,cellType);
    }
    public void addRow(List<?> rowData , Cell.CellType cellType){
        handleAddRow(table.getTableHeight(),rowData,cellType);
    }
    private void handleAddRow(Integer rowIndex, List<?> rowData, Cell.CellType cellType) {
        try {
            // If a cell is being appended and not inserted inside doesn't need to check this if
            if ((rowIndex < 0 || rowIndex > table.getTableHeight() - 1) && rowIndex != table.getTableHeight()) {
                System.out.println("Row at index : " + rowIndex + " does not exist");
                return;
            }
            if (table.getHeaders().isEmpty()) {
                System.out.println("Cannot add rows without table headers");
                return;
            }
            if (validateRowSize(rowData)) {
                // Calculate individual cell padding based on its corresponding header cell
                List<Integer> cellPaddings = calculateCellPaddings(rowData);

                // Transform row data into a bottom table row
                var cellRow = rowToCellList(rowData, cellPaddings, cellType);

                // Add cellRow to the table
                if (rowIndex == table.getTableHeight()){
                    table.getTableData().add(cellRow);
                }
                else {
                    table.getTableData().add(rowIndex, cellRow);
                }
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Caught IllegalArgumentException: " + e.getMessage());
        }
    }
    private boolean validateRowSize(List<?> rowData){
        if(rowData.size() != table.getHeaders().size()){
            String message = (rowData.size() > table.getHeaders().size()) ?
                    "Row has more cells than expected." :
                    "Row has fewer cells than expected.";

            System.out.println(message + " Expected: " + table.getHeaders().size() + " cells.");
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
        -> Column addition addColumn()
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
    public void insertColumn(String header, int headerPadding, List<?> columnData){

    }
    private void addSingleColumn(String header, int headerPadding, List<?> columnData){

        try {

            table.setHeaders(headerPadding, Collections.singletonList(header));

            for (int column = 0; column < columnData.size(); column++) {
                var columnElement = columnData.get(column);
                Cell.CellType rowType;
                if (column == columnData.size() - 1) {
                    rowType = Cell.CellType.BOTTOM_SINGLE;
                } else {
                    rowType = Cell.CellType.MIDDLE_SINGLE;
                }

                addRow(Collections.singletonList(columnElement), rowType);
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Caught IllegalArgumentException: " + e.getMessage());
        }
        
    }
    private void appendColumn(String header, int headerPadding, List<?> columnData){
        try {
            if (validateColumn(columnData)) {

                Cell<String> cellHeader = new Cell<>(header, header.length() + headerPadding, Cell.CellType.TOP_SINGLE);

                int headerCellWidth = header.length() + headerPadding;

                // Append new header
                table.getHeaders().add(cellHeader);

                for (var columnElement : columnData) {
                    Cell<?> columnCell;
                    if (Objects.isNull(columnElement)) {
                        columnCell = addNulLCell(headerCellWidth);
                    } else {
                        columnCell = new Cell<>(
                                columnElement,
                                columnElement.toString().length() + calculatePadding(columnElement.toString(), headerCellWidth),
                                Cell.CellType.TOP_SINGLE
                        );
                    }

                    // Add new columnCell to the table
                    table.getRow(columnData.indexOf(columnElement)).add(columnCell);
                }
                // If the newly added column is shorter than the other columns in the table, append null cells to ensure equal column heights.
                if (columnData.size() < table.getTableHeight()) {
                    int heightDifference = table.getTableHeight() - columnData.size();
                    for (int i = 0; i < heightDifference; i++) {
                        table.getRow(columnData.size() + i).add(addNulLCell(headerCellWidth));
                    }
                }
            }
            else {
                System.out.println(
                        "Column input exceeds table height. Required column height : " + table.getTableHeight()
                );
            }

        }
        catch (IllegalArgumentException e){
            System.out.println("Caught IllegalArgumentException: " + e.getMessage());
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
        Column Deletion
     */
    public void deleteColumn(int columnIndex){
        if( table.getTableData().isEmpty() || (columnIndex < 0 || columnIndex >= table.getTableWidth())){
            System.out.println("Column at index : " + columnIndex + " does not exist");
        }
        else{
            table.getHeaders().remove(columnIndex);
            for (int i = 0; i < table.getTableHeight(); i++) {
                table.getTableData().get(i).remove(columnIndex);
            }
        }
    }
    /*
        Table styling
        -> alignment
        -> text casing
        -> column width
        -> headers height
     */
    public void alignColumn(int columnIndex, Table.ColumnAlign alignment){
        
        TextFormatter.Alignment formatterAlignment = null;

        switch (alignment){
            case LEFT -> formatterAlignment = TextFormatter.Alignment.LEFT;
            case CENTER -> formatterAlignment = TextFormatter.Alignment.CENTER;
            case RIGHT -> formatterAlignment = TextFormatter.Alignment.RIGHT;
        }

        var columnHeader = table.getHeaders().get(columnIndex).getTextFormatter();

        // Align header
        if (alignment == Table.ColumnAlign.DEFAULT) {
            columnHeader.align(TextFormatter.Alignment.CENTER);
        }
        else {
            columnHeader.align(formatterAlignment);
        }

        for (int row = 0; row < table.getTableHeight(); row++) {
            var columnCell = table.getTableData().get(row).get(columnIndex).getTextFormatter();
            if (alignment == Table.ColumnAlign.DEFAULT) {
                columnCell.align(TextFormatter.Alignment.LEFT);
            }
            else {
                columnCell.align(formatterAlignment);
            }
        }
    }
    public void alignTable(Table.ColumnAlign alignment){
        for (int column = 0; column < table.getTableWidth(); column++) {
            alignColumn(column,alignment);
        }

    }
    // Text Casing
    public void setHeadersStyle(Table.CasingStyle casing) {
        for (var header : table.getHeaders()){
            switch (casing){
                case LOWER_CASE -> header.text().lowerCase();
                case UPPER_CASE -> header.text().upperCase();
                case CAPITALIZE -> header.text().capitalize();
            }

        }
    }
    public void setColumnStyle(int columnIndex, Table.CasingStyle casing){
        for (int row = 0; row < table.getTableHeight(); row++) {
            var columnCell = table.getCell(row,columnIndex).text();
            switch (casing){
                case LOWER_CASE -> columnCell.lowerCase();
                case UPPER_CASE -> columnCell.upperCase();
                case CAPITALIZE -> columnCell.capitalize();
            }
        }
    }
    public void setColumnWidth(int columnIndex, int width){
        var header =  table.getHeaders().get(columnIndex);
        header.setCellWidth(width);
        header.getTextFormatter().setContainerWidth(width);

        for (int row = 0; row < table.getTableHeight(); row++) {
            var columnCell = table.getCell(row,columnIndex);
            columnCell.setCellWidth(width);
            columnCell.getTextFormatter().setContainerWidth(width);
        }
    }
    public void setHeadersHeight(int height){
        for (var header : table.getHeaders()){
            header.setCellHeight(height);
        }
    }
    public void setHeadersWidth(int width){
        for (int column = 0; column < table.getTableWidth(); column++) {
            setColumnWidth(column,width);
        }
    }
    public void equalizeColumnWidths() {
        List<Integer> headerWidths = new ArrayList<>();
        for(var header : table.getHeaders()){
            headerWidths.add(header.getCellWidth());
        }
        setHeadersWidth(Collections.max(headerWidths));
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
    public String adjustTableOutputForTwoColumns(String tableOutput){
        int tableHeightInChars = TextUtils.countRows(tableOutput);
        String[] tableOutputRows = tableOutput.split("\n");

        for (int i = 0; i < tableHeightInChars; i++) {
            tableOutputRows[i] = TextUtils.removeCharAt(
                    tableOutputRows[i],
                    table.getHeaders().get(0).getCellWidth() + 1);
        }
        return TextUtils.assembleRows(tableOutputRows);
    }

    //TODO: handle cell overflow ...

}
