package org.example.table.manipulation;

import org.example.table.format.CellBuilder;
import org.example.table.format.TableFormatter;
import org.example.table.model.Cell;
import org.example.table.model.Table;
import org.example.text.utils.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TableEditor {
    private final Table table;
    private final TableFormatter formatter;

    public TableEditor(Table table){
        this.table = table;
        this.formatter = this.table.getFormatter();
    }


    // Table headers logic
    private List<Cell<?>> mapHeadersToTableCells(List<String> headers, int padding){
        // First create the cell list
        List<Cell<?>> headerCells = formatter.convertHeaderDataToCellList(headers,padding);
        // Set accordingly cell types from LEFT->RIGHT or SINGLE
        headerCells = formatter.setOrderedCellTypes(headerCells,headerCells.get(0).getCellType());

        return headerCells;
    }

    private void addHeadersListToTable(List<Cell<?>> headerCells){
        // Add them to the first table row
        var data = table.getData();
        if(data.isEmpty()){
            data.add(headerCells);
        }
        else {
            deleteRow(0);
            data.add(0,headerCells);
        }
    }

    public void setHeaders_ManualPadding(List<String> headers, int padding){
        List<Cell<?>> headersCellList = mapHeadersToTableCells(headers,padding);
        addHeadersListToTable(headersCellList);
    }
    public void setHeaders_AutomaticPadding(List<String> headers){
        setHeaders_ManualPadding(headers,2);
    }
    private void appendHeader(String header, int padding){
        var data = table.getData();
        Cell<String> headerCell = new Cell<>(header,header.length()+padding, Cell.CellType.TOP_SINGLE);
        data.get(0).add(headerCell);
    }

    // Table rows logic
    private List<Cell<?>> mapRowDataToTableCells(List<?> rowData, List<Integer> paddings){
        // First create the cell list
        List<Cell<?>> rowCells = formatter.convertDataToCellList(Cell.CellType.BOTTOM_LEFT,rowData,paddings);
        // Set accordingly cell types from LEFT->RIGHT or SINGLE
        rowCells = formatter.setOrderedCellTypes(rowCells,rowCells.get(0).getCellType());

        return rowCells;
    }
    private void addCellRowToTable(List<Cell<?>> rowCells){
        // Add them to the first table row
        var data = table.getData();
        data.add(rowCells);
    }



    // Insertion / Deletions Rows
    public void addRow(List<?> rowData){
        var data = table.getData();
        if(data.isEmpty()){
            throw new UnsupportedOperationException("Cannot add rows without table headers");
        }
        if(rowData.size() > table.getTableWidth()){
            throw new IllegalArgumentException("Row has more cells than expected. Expected: " + table.getTableWidth() + " cells.");
        }
        else if (rowData.size() < table.getTableWidth()){
            throw new IllegalArgumentException("Insufficient number of cells in the row. Expected at least " + table.getTableWidth() + " cells.");
        }

        // Every new row is displayed as a bottom row
        Cell.CellType cellType = Cell.CellType.BOTTOM_LEFT;

        // Calculate individual padding based on the header cells
        List<Integer> cellPaddings = new ArrayList<>();

        for (int column = 0; column < table.getTableWidth(); column++) {
            Cell<?> header = table.getCellAt(0,column);
            int headerWidth = header.getCellWidth();

            cellPaddings.add(TextUtils.calculatePadding(
                    rowData.get(column).toString(),
                    headerWidth));
        }

        List<Cell<?>> cellRow = mapRowDataToTableCells(rowData,cellPaddings);
        addCellRowToTable(cellRow);

        // Update bottom cells to middle cells after row insertion
        adjustBottomCellsToMiddleCells();

    }

    public void addColumn(String header, int headerPadding, List<?> columnData){
        var tableData = table.getData();

        // Check if the table is empty
        // If it is then add a single column with Cell Type Single
        if(tableData.isEmpty()){
            setHeaders_ManualPadding(Collections.singletonList(header),headerPadding);
            for(var data : columnData){
                addRow(Collections.singletonList(data));
            }
        }
        // If not, then append to the table end
        else {

            // Check if column doesn't overflow the table height
            if ( columnData.size() + 1 > tableData.size()){
                System.out.println("Column input exceeds table height\nRequired column height : " + tableData.size());
                return;
            }

            appendHeader(header,headerPadding);

            int headerWidth = header.length() + headerPadding;

            for (int i = 0; i < columnData.size(); i++) {
                var columnElement = columnData.get(i);
                Cell<?> newCell = new Cell<>(
                        columnElement,
                        columnElement.toString().length() + TextUtils.calculatePadding(columnElement.toString(),headerWidth),
                        Cell.CellType.MIDDLE_CENTER // Trash Value
                );

                /// ????
                tableData.get(i+1).add(newCell);
            }

            adjustBottomCellsToMiddleCells();
        }
    }

    public void deleteRow(int indexRow){
        var data = table.getData();
        data.remove(indexRow);
    }

    // Table Update
    public void adjustBottomCellsToMiddleCells(){
        var data = table.getData();
        for (var row : data){
            for (var cell : row){
                if(data.indexOf(row) > 0 && data.indexOf(row) < data.size()-1){
                    cell.setCellType(Cell.CellType.MIDDLE_CENTER);
                }
            }
        }
    }

}
