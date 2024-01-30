package org.example.table.format;

import org.example.constants.Unicode;
import org.example.table.model.Cell;
import org.example.table.model.Table;
import org.example.text.utils.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StandardTableFormatter {
    private String verticalCellSpacer(int rowHeight, Cell.CellType cellType){
        StringBuilder stringBuilder = new StringBuilder();
        String endLine = "\n";

        char verticalConnector = Unicode.boxMatrix[0][2];

        switch (cellType){

            case TOP_SINGLE,TOP_LEFT,TOP_CENTER,TOP_RIGHT -> {
                char upperJunction = Unicode.boxMatrix[6][4];  // ╤
                char bottomJunction = Unicode.boxMatrix[6][10];// ╪

                stringBuilder.append(upperJunction).append(endLine);
                for (int i = 0; i < rowHeight-2; i++) {
                    stringBuilder.append(verticalConnector).append(endLine);
                }
                stringBuilder.append(bottomJunction).append(endLine);

            }
            case BOTTOM_SINGLE,BOTTOM_LEFT,BOTTOM_CENTER,BOTTOM_RIGHT -> {
                char bottomJunction = Unicode.boxMatrix[3][4]; // ┴

                for (int i = 0; i < rowHeight-1; i++) {
                    stringBuilder.append(verticalConnector).append(endLine);
                }
                stringBuilder.append(bottomJunction).append(endLine);
            }
            default -> {
                char bottomJunction = Unicode.boxMatrix[3][12]; // ┼

                for (int i = 0; i < rowHeight-1; i++) {
                    stringBuilder.append(verticalConnector).append(endLine);
                }
                stringBuilder.append(bottomJunction).append(endLine);
            }

        }

        return stringBuilder.toString();
    }

    public List<Cell<?>> convertHeadersToCellList(List<?> data, Cell.CellType cellType, int padding){
        List<Cell<?>> cellList = new ArrayList<>();

        for (Object cellData : data){
            Cell<?> newCell = new Cell<>(cellData,cellData.toString().length()+padding,cellType);
            cellList.add(newCell);
        }

        return cellList;
    }
    public List<Cell<?>> convertDataToCellList(List<?> data, Cell.CellType cellType, List<Integer> paddings){
        List<Cell<?>> cellList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            Cell<?> newCell = new Cell<>(
                    data.get(i),
                    data.get(i).toString().length()+paddings.get(i),
                    cellType);
            cellList.add(newCell);
        }

        return cellList;
    }

    /**
     * Orders the given list of cells in a row layout:
     * - If the list contains only one cell, it is considered a single cell.
     * - If the list contains multiple cells, they are arranged as follows:
     *   - Left cells: Cells that join with the middle cells on the left side.
     *   - Middle cells: Cells that form the central part of the row.
     *   - Right cell: A cell that joins with the middle cells on the right side.
     *
     * Example:
     * For a list of cells [cell1, cell2, cell3, cell4],
     * the order is [LeftCell1, MiddleCell2, MiddleCell3, RightCell4].
     *
     * @param cellList The list of cells to be ordered.
     * @return Ordered list of cells.
     */
    public List<Cell<?>> sortCellListByType(List<Cell<?>> cellList, Cell.CellType cellType){

        switch (cellType){
            case TOP_SINGLE,TOP_LEFT,TOP_CENTER,TOP_RIGHT -> {
                if(cellList.size() == 1){
                    cellList.get(0).setCellType(Cell.CellType.TOP_SINGLE);
                    return cellList;
                }
                for (int i = 0; i < cellList.size(); i++) {
                    if(i == 0)
                    {
                        cellList.get(i).setCellType(Cell.CellType.TOP_LEFT);
                    }
                    else if(i<cellList.size()-1){
                        cellList.get(i).setCellType(Cell.CellType.TOP_CENTER);
                    }
                    else{
                        cellList.get(i).setCellType(Cell.CellType.TOP_RIGHT);
                    }
                }
            }
            case MIDDLE_SINGLE,MIDDLE_LEFT,MIDDLE_CENTER,MIDDLE_RIGHT -> {
                if(cellList.size() == 1){
                    cellList.get(0).setCellType(Cell.CellType.MIDDLE_SINGLE);
                    return cellList;
                }
                for (int i = 0; i < cellList.size(); i++) {
                    if(i == 0)
                    {
                        cellList.get(i).setCellType(Cell.CellType.MIDDLE_LEFT);
                    }
                    else if(i<cellList.size()-1){
                        cellList.get(i).setCellType(Cell.CellType.MIDDLE_CENTER);
                    }
                    else{
                        cellList.get(i).setCellType(Cell.CellType.MIDDLE_RIGHT);
                    }
                }
            }
            case BOTTOM_SINGLE,BOTTOM_LEFT,BOTTOM_CENTER,BOTTOM_RIGHT -> {
                if(cellList.size() == 1){
                    cellList.get(0).setCellType(Cell.CellType.BOTTOM_SINGLE);
                    return cellList;
                }
                for (int i = 0; i < cellList.size(); i++) {
                    if(i == 0)
                    {
                        cellList.get(i).setCellType(Cell.CellType.BOTTOM_LEFT);
                    }
                    else if(i<cellList.size()-1){
                        cellList.get(i).setCellType(Cell.CellType.BOTTOM_CENTER);
                    }
                    else{
                        cellList.get(i).setCellType(Cell.CellType.BOTTOM_RIGHT);
                    }
                }
            }
            default -> {
                throw new IllegalArgumentException("Invalid cellType format");
            }
        }
        return cellList;
    }

    public String formatRow(List<Cell<?>> cellRow){
        StringBuilder tableRow = new StringBuilder();

        int rowSize = cellRow.size();
        int rowHeight = TextUtils.countRows(cellRow.get(0).toString());
        Cell.CellType rowType = cellRow.get(0).getCellType();

        // Orders the given list of cells in a row layout
        cellRow = sortCellListByType(cellRow,rowType);

        for (int i = 0; i < rowHeight; i++) {
            for (int j = 0; j < rowSize; j++) {
                String enclosureCellRow = TextUtils.getRowFromString(
                        cellRow.get(j).toString(),
                        i
                );
                tableRow.append(enclosureCellRow);
                // Inserting cell spacers between open middle cells
                String cellSpacer = verticalCellSpacer(rowHeight, rowType);
                if(j > 0 && j < rowSize-2){
                    tableRow.append(TextUtils.getRowFromString(cellSpacer,i));
                }
            }
            tableRow.append(System.lineSeparator());
        }
        return tableRow.toString();
    }

    public int calculatePadding(Object cellContent, int headerWidth){
        return headerWidth - cellContent.toString().length();
    }
}
