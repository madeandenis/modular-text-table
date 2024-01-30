package org.example.table.model;

import org.example.table.format.StandardTableFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    private StringBuilder formattedTable = new StringBuilder();
    private List<?> columnHeaders = new ArrayList<>();
    private List<Class<?>> columnHeadersType = new ArrayList<>();
    private List<List<Cell<?>>> data = new ArrayList<>();
    private int tableWidth = 0;
    private int tableHeight = 0;

    StandardTableFormatter tableFormatter = new StandardTableFormatter();
    public Table(){
    }
    public Table(int padding, String... columnNames){
        columnHeaders = Arrays.asList(columnNames);


        addHeaders(padding);
        updateTableDimensions();
    }

    @Override
    public String toString(){
        clearFormattedTable();
        formatTable();
        return formattedTable.toString();
    }
    public void displayData() {
        for (int i = 0; i < data.size(); i++) {
            List<Cell<?>> row = data.get(i);

            if (row != null && !row.isEmpty()) {
                for (int j = 0; j < row.size(); j++) {
                    System.out.println("[" + i + "]" +
                            "[" + j + "]" +
                            row.get(j).getData());
                }
                System.out.println("-".repeat(20));
            } else {
                System.out.println("Empty row at index " + i);
            }
        }
    }

    // Update
    public void updateTableDimensions(){
        this.tableWidth = data.isEmpty() ? 0 : data.get(0).size();
        this.tableHeight = data.size();
    }
    public void updateTableRows(){
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                if(i > 0 && i < data.size()-1){
                    data.get(i).get(j).setCellType(Cell.CellType.MIDDLE_CENTER);
                }
            }
        }
    }
    // Clear
    public void clearFormattedTable(){
        this.formattedTable.setLength(0);
    }


    // Setters
    public void setHeaders(int padding, String... columnNames) {
        // Clear data, formattedTable and reset columnHeaders
        data.clear();
        formattedTable.setLength(0);
        columnHeaders = Arrays.asList(columnNames);

        // Add new header row to the table
        addHeaders(padding);
    }

    // Getters
    public List<List<Cell<?>>> getData(){
        return data;
    }
    public Cell<?> getCell(int row, int column){
        return data.get(row).get(column);
    }
    public List<?> getTableHeaders(){
        return this.columnHeaders;
    }
    public int[] getTableDimensions(){
        int[] tableDimensions = new int[2];
        tableDimensions[0] = tableWidth;
        tableDimensions[1] = tableHeight;
        return tableDimensions;
    }
    public List<Cell<?>> getFirstRow(){
        return this.data.get(0);
    }
    public List<Cell<?>> getLastRow(){
        return this.data.get(data.size()-1);
    }

    // Table Modifier
    // Add new

    // AddHeaders methods overwrites the already declared headers and replaces them with new ones
    public void addHeaders(int padding) {
        // Convert column headers to a list of cells and add it to the 'data' list
        List<Cell<?>> headerCells = tableFormatter.convertHeadersToCellList(columnHeaders, Cell.CellType.TOP_LEFT, padding);
        data.add(headerCells);

        // Format the table headers using the converted header cells and append to the 'formattedTable'
        formattedTable.append(tableFormatter.formatRow(headerCells));

        updateTableDimensions(); // to delete
    }
    // Adds to already existing headers list
    public void addHeader(Object header,int padding){
        Cell<?> headerCell = new Cell<>(header,header.toString().length()+padding, Cell.CellType.TOP_LEFT);
        if (data.isEmpty()){
            data.add(List.of(headerCell));
        }
        else {
            List<Object> updatedHeaders = new ArrayList<>(columnHeaders);
            updatedHeaders.add(header);
            columnHeaders = updatedHeaders;
            data.get(0).add(headerCell);
        }


    }
    public void addRow(Object... rowElements){
        if(data.isEmpty()){
            throw new UnsupportedOperationException("Cannot add rows without table headers");
        }
        if(Arrays.asList(rowElements).size() > columnHeaders.size()){
            throw new IllegalArgumentException("Row has more cells than expected. Expected: " + columnHeaders.size() + " cells.");
        }
        else if (Arrays.asList(rowElements).size() < columnHeaders.size()){
            throw new IllegalArgumentException("Insufficient number of cells in the row. Expected at least " + columnHeaders.size() + " cells.");
        }

        List<?> row = new ArrayList<>(Arrays.asList(rowElements));
        Cell.CellType rowType = Cell.CellType.BOTTOM_LEFT;

        // Calculate padding for the cells based on the header cells
        List<Integer> cellPaddings = new ArrayList<>();
        for (int i = 0; i < row.size(); i++) {
            int headerWidth = data.get(0).get(i).getCellWidth();
            cellPaddings.add(tableFormatter.calculatePadding(row.get(i),headerWidth));
        }


        List<Cell<?>> rowCellList = tableFormatter.convertDataToCellList(row, rowType, cellPaddings);
        data.add(rowCellList);

        // Format the table headers using the converted header cells and append to the 'formattedTable'
        formattedTable.append(tableFormatter.formatRow(rowCellList));

        updateTableRows();
        updateTableDimensions(); // to delete
    }
    public void addColumn(String header,int padding,Object... columnElements){
        // If table is empty add a SINGLE cell
        if(data.isEmpty()){
            setHeaders(padding,header);
            for(Object data : columnElements){
                addRow(data);
            }
        }
        else{
            addHeader(header,padding);
            int headerWidth = header.length() + padding + 1; // ??? IDK why +1

            List<?> columnElementsList = Arrays.asList(columnElements);
            for (int i = 0; i < columnElementsList.size(); i++) {
                Object columnElement = columnElementsList.get(i);
                Cell.CellType cellType = Cell.CellType.MIDDLE_CENTER;
                Cell<?> newCell = new Cell<>(columnElement,
                                            tableFormatter.calculatePadding(columnElement.toString().length(),headerWidth),
                                            cellType);
                // Columns data are being added from the header to bottom
                data.get(i+1).add(newCell);
            }
        }

        updateTableRows();
        updateTableDimensions(); // to delete
    }


    // Remove at
    public void removeRow(int index){
        // Remove the row from the data
        this.data.remove(index);

    }
    // Formatting the table
    public void formatTable(){
        for (List<Cell<?>> rowData : data) {
            formattedTable.append(tableFormatter.formatRow(rowData));
        }
    }

}
