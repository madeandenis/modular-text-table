package org.example.table.format;

import org.example.constants.Unicode;
import org.example.table.model.Cell;
import org.example.text.styles.TableStyles;
import org.example.text.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TableFormatter {
    private char getStyleChar(TableStyles tableStyles, int row, int column){
        return tableStyles.getStyle()[row][column];
    }
    public String verticalCellSpacer(Cell.CellType cellType , TableStyles tableStyle, int rowHeight){
        StringBuilder builder = new StringBuilder();

        char verticalConnector;
        char upperJunction;
        char bottomJunction;

        switch (cellType){


            /*
            ╤
            │
            │ Header Cell Spacer - example
            ╪
             */
            case TOP_SINGLE,TOP_LEFT,TOP_CENTER,TOP_RIGHT -> {
                verticalConnector = getStyleChar(tableStyle,1,0);
                upperJunction     = getStyleChar(tableStyle,13,0);
                bottomJunction    = getStyleChar(tableStyle,13,1);

                builder.append(upperJunction).append(System.lineSeparator());
                for (int i = 0; i < rowHeight-2; i++) {
                    builder.append(verticalConnector).append(System.lineSeparator());
                }
                builder.append(bottomJunction).append(System.lineSeparator());

                return builder.toString();

            }
            /*
            │
            │ Bottom Cell Spacer - example
            ┴
             */
            case BOTTOM_SINGLE,BOTTOM_LEFT,BOTTOM_CENTER,BOTTOM_RIGHT -> {
                verticalConnector = getStyleChar(tableStyle,3,0);
                bottomJunction    = getStyleChar(tableStyle,15,0);
            }
            /*
            │
            │ Middle Cell Spacer - example
            ┼
            */
            // Middle cells
            default -> {
                verticalConnector = getStyleChar(tableStyle,3,0);
                bottomJunction    = getStyleChar(tableStyle,14,0);
            }
        }
        for (int i = 0; i < rowHeight-1; i++) {
            builder.append(verticalConnector).append(System.lineSeparator());
        }
        builder.append(bottomJunction).append(System.lineSeparator());

        return builder.toString();

    }

    public List<Cell<?>> convertHeaderDataToCellList(List<String> headers, Integer padding){

        return headers.stream()
                .map(header -> new Cell<>(header,header.length()+padding, Cell.CellType.TOP_SINGLE))
                .collect(Collectors.toList());

    }

    public List<Cell<?>> convertDataToCellList(Cell.CellType cellType, List<?> data, List<Integer> paddings) {
        return IntStream.range(0, data.size())
                .mapToObj(i -> new Cell<>(
                        data.get(i),
                        data.get(i).toString().length() + paddings.get(i),
                        cellType))
                .collect(Collectors.toList());
    }

    public List<Cell<?>> setOrderedCellTypes(List<Cell<?>> cellList, Cell.CellType cellType){

        // Single Cell
        if(cellList.size() == 1){
            cellList.get(0).setCellType(Cell.CellType.getSingleCellType(cellType));
            return cellList;
        }
        else{
            for (int i = 0; i < cellList.size(); i++) {
                // Left Cells
                if(i == 0){
                    cellList.get(i).setCellType(Cell.CellType.getLeftCellType(cellType));
                }
                // Middle Cells
                else if(i < cellList.size()-1){
                    cellList.get(i).setCellType(Cell.CellType.getMiddleCellType(cellType));
                }
                // Right Cells
                else{
                    cellList.get(i).setCellType(Cell.CellType.getRightCellType(cellType));
                }
            }
        }

        return cellList;
    }

    public String formatTableRow(List<Cell<?>> cells){
        StringBuilder builder = new StringBuilder();

        int rowSize   = cells.size();
        int rowHeight = TextUtils.countRows(
                cells.get(0).toString()
        );
        Cell.CellType rowType = cells.get(0).getCellType();

        cells = setOrderedCellTypes(cells,rowType);

        String cellSpacer = verticalCellSpacer(rowType,cells.get(0).getTableStyle(),rowHeight);

        for (int i = 0; i < rowHeight; i++) {
            for (int j = 0; j < rowSize; j++) {

                String cellLine = TextUtils.getRowFromString(cells.get(j).toString(),i);
                assert cellLine != null;

                // Each cell lines has a carriage return, I guess from using lineSeparator()
                // ! needs to be chopped down because the String before it s being ignored when printed
                cellLine = TextUtils.chop(cellLine);
                builder.append(cellLine);

                // Append line separator for middle joining cells
                if (j > 0 && j < rowSize - 2 ){
                    // ! needs to be chopped down because the String before it s being ignored when printed
                    builder.append(TextUtils.chop(TextUtils.getRowFromString(cellSpacer,i)));
                }
            }
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

}
