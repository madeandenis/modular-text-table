package org.example;

import org.example.constants.Unicode;
import org.example.table.model.Cell;

public class OldStandardCellFormatter {
    private final StringBuilder stringBuilderCell = new StringBuilder();
    private String whiteSpace = " ";

    public String build(String text, int cellWidth, int cellHeight, Cell.CellType cellType){
        validateFormattedText(text,cellWidth);
        // The StringBuilder is recycled for each cell construction
        stringBuilderCell.setLength(0);
        switch (cellType){
            case TOP_SINGLE -> {
                return graphicalTopCell(text,cellWidth,cellHeight);
            }
            case MIDDLE_SINGLE -> {
                return graphicalMiddleCell(text,cellWidth,cellHeight);
            }
            case BOTTOM_SINGLE -> {
                return graphicalBottomCell(text,cellWidth,cellHeight);
            }
            default -> {
                throw new IllegalArgumentException("CellType must be a valid type");
            }
        }
    }

    // Validators
    public void validateFormattedText(String text, int cellWidth){
        if (text.length() != cellWidth){
            throw new IllegalArgumentException("Text is not correctly formatted for the graphical cell");
        }
    }

    // Setters
    public void setWhiteSpace(String whiteSpace) {
        this.whiteSpace = whiteSpace;
    }

    // Getters
    public String getWhiteSpace() {
        return whiteSpace;
    }

    // Output: ─────
    private String singleHorizontalCellSpacer(int cellWidth){
        // U+2500	─
        return String.valueOf(Unicode.boxMatrix[0][0]).repeat(cellWidth);
    }
    // Output: ═════
    private String doubleHorizontalCellSpacer(int cellWidth){
        // U+2550	═
        return String.valueOf(Unicode.boxMatrix[5][0]).repeat(cellWidth);
    }

    // Output: |     |
    private String VerticalCellSpacer(int cellWidth){
        // U+2502	│
        return String.valueOf(Unicode.boxMatrix[0][2])+whiteSpace.repeat(cellWidth)+String.valueOf(Unicode.boxMatrix[0][2]);
    }
    private void repeatVerticalCellSpacer(int cellWidth, int n){
        for (int i = 0; i < n; i++) {
            stringBuilderCell.append(VerticalCellSpacer(cellWidth));
            stringBuilderCell.append("\n");
        }
    }

    /**
     *
     * One Column Table Formatting
     *
     */

    private void topCell_firstRow(int cellWidth){
        // First row : ╒═════════╕
        stringBuilderCell.append(Unicode.boxMatrix[5][2]);
        stringBuilderCell.append(doubleHorizontalCellSpacer(cellWidth));
        stringBuilderCell.append(Unicode.boxMatrix[5][5]);
        stringBuilderCell.append("\n");

    }
    private void topCell_lastRow(int cellWidth){
        // Last row : ╞═════════╡
        stringBuilderCell.append(Unicode.boxMatrix[5][14]);
        stringBuilderCell.append(doubleHorizontalCellSpacer(cellWidth));
        stringBuilderCell.append(Unicode.boxMatrix[6][1]);
        stringBuilderCell.append("\n");

    }

    private void bottomCell_firstRow(int cellWidth){
        // First row : ├─────────┤
        stringBuilderCell.append(Unicode.boxMatrix[1][12]);
        stringBuilderCell.append(singleHorizontalCellSpacer(cellWidth));
        stringBuilderCell.append(Unicode.boxMatrix[2][4]);
        stringBuilderCell.append("\n");

    }
    private void bottomCell_lastRow(int cellWidth){
        // Last row : └─────────┘
        stringBuilderCell.append(Unicode.boxMatrix[1][4]);
        stringBuilderCell.append(singleHorizontalCellSpacer(cellWidth));
        stringBuilderCell.append(Unicode.boxMatrix[1][8]);
        stringBuilderCell.append("\n");

    }

    private void textRow(String text){
        // Text row : │ text │
        stringBuilderCell.append(Unicode.boxMatrix[0][2]);
        stringBuilderCell.append(text);
        stringBuilderCell.append(Unicode.boxMatrix[0][2]);
        stringBuilderCell.append("\n");

    }
    // ╒═════════╕
    // │ example │ -> TOP cell (height : 1)
    // ╞═════════╡

    // ╒═════════╕
    // │         │
    // │ example │ -> TOP cell (height : 2)
    // │         │
    // ╞═════════╡
    private String graphicalTopCell(String text, int cellWidth, int cellHeight){
        stringBuilderCell.setLength(0);

        topCell_firstRow(cellWidth);

        repeatVerticalCellSpacer(cellWidth,cellHeight-1);

        textRow(text);

        repeatVerticalCellSpacer(cellWidth,cellHeight-1);

        topCell_lastRow(cellWidth);

        return stringBuilderCell.toString();
    }
    //
    // │ example │ -> MIDDLE cell (height : 1)
    //

    // │         │
    // │ example │ -> MIDDLE cell (height : 2)
    // │         │

    private String graphicalMiddleCell(String text, int cellWidth, int cellHeight){
        stringBuilderCell.setLength(0);

        repeatVerticalCellSpacer(cellWidth,cellHeight-1);

        textRow(text);

        repeatVerticalCellSpacer(cellWidth,cellHeight-1);

        return stringBuilderCell.toString();
    }

    // ├─────────┤
    // │         │ -> BOTTOM cell (height : 1)
    // └─────────┘

    // ├─────────┤
    // │         │
    // │         │ -> BOTTOM cell (height : 2)
    // │         │
    // └─────────┘

    private String graphicalBottomCell(String text, int cellWidth, int cellHeight){
        stringBuilderCell.setLength(0);

        bottomCell_firstRow(cellWidth);

        repeatVerticalCellSpacer(cellWidth,cellHeight-1);

        textRow(text);

        repeatVerticalCellSpacer(cellWidth,cellHeight-1);

        bottomCell_lastRow(cellWidth);

        return stringBuilderCell.toString();
    }

    /**
     *
     *  Multiple Column Table Formatting
     *
     *  TOP_LEFT,TOP_CENTER,TOP_RIGHT,
     *  MIDDLE_LEFT,MIDDLE_CENTER,MIDDLE_RIGHT,
     *  BOTTOM_LEFT,BOTTOM_CENTER,BOTTOM_RIGHT;
     *
     */

    // TOP Cells (1x3)

    // TOP_LEFT
    // ╒═════════╤
    // │ example │
    // ╞═════════╤╤

    private void topLeftCell_firstRow(int cellWidth){
        // First row : ╒═════════╤
        stringBuilderCell.append(Unicode.boxMatrix[5][2]);
        stringBuilderCell.append(doubleHorizontalCellSpacer(cellWidth));
        stringBuilderCell.append(Unicode.boxMatrix[6][4]);
        stringBuilderCell.append("\n");

    }
    private void topLeftCell_lastRow(int cellWidth){
        // Last row : ╞═════════╪
        stringBuilderCell.append(Unicode.boxMatrix[5][14]);
        stringBuilderCell.append(doubleHorizontalCellSpacer(cellWidth));
        stringBuilderCell.append(Unicode.boxMatrix[6][10]);
        stringBuilderCell.append("\n");

    }
    private String graphicalTopLeftCell(String text, int cellWidth, int cellHeight){
        stringBuilderCell.setLength(0);

        topLeftCell_firstRow(cellWidth);

        repeatVerticalCellSpacer(cellWidth,cellHeight-1);

        textRow(text);

        repeatVerticalCellSpacer(cellWidth,cellHeight-1);

        topLeftCell_lastRow(cellWidth);

        return stringBuilderCell.toString();
    }

    // TOP_CENTER
    // ╤═════════╤
    // │ example │
    // ╪═════════╪

    private void topCenterCell_firstRow(int cellWidth){
        // First row : ╤═════════╤
        stringBuilderCell.append(Unicode.boxMatrix[6][4]);
        stringBuilderCell.append(doubleHorizontalCellSpacer(cellWidth));
        stringBuilderCell.append(Unicode.boxMatrix[6][4]);
        stringBuilderCell.append("\n");

    }
    private void topCenterCell_lastRow(int cellWidth){
        // Last row : ╪═════════╪
        stringBuilderCell.append(Unicode.boxMatrix[6][10]);
        stringBuilderCell.append(doubleHorizontalCellSpacer(cellWidth));
        stringBuilderCell.append(Unicode.boxMatrix[6][10]);
        stringBuilderCell.append("\n");

    }
    private String graphicalTopCenterCell(String text, int cellWidth, int cellHeight){
        stringBuilderCell.setLength(0);

        topCenterCell_firstRow(cellWidth);

        repeatVerticalCellSpacer(cellWidth,cellHeight-1);

        textRow(text);

        repeatVerticalCellSpacer(cellWidth,cellHeight-1);

        topCenterCell_lastRow(cellWidth);

        return stringBuilderCell.toString();
    }

    // TOP_RIGHT
    // ╤═════════╕
    // │ example │
    // ╪═════════╡

    private void topRightCell_firstRow(int cellWidth){
        // First row : ╤═════════╕
        stringBuilderCell.append(Unicode.boxMatrix[6][4]);
        stringBuilderCell.append(doubleHorizontalCellSpacer(cellWidth));
        stringBuilderCell.append(Unicode.boxMatrix[5][5]);
        stringBuilderCell.append("\n");

    }
    private void topRightCell_lastRow(int cellWidth){
        // Last row : ╪═════════╡
        stringBuilderCell.append(Unicode.boxMatrix[6][10]);
        stringBuilderCell.append(doubleHorizontalCellSpacer(cellWidth));
        stringBuilderCell.append(Unicode.boxMatrix[6][1]);
        stringBuilderCell.append("\n");

    }
    private String graphicalTopRightCell(String text, int cellWidth, int cellHeight){
        stringBuilderCell.setLength(0);

        topRightCell_firstRow(cellWidth);

        repeatVerticalCellSpacer(cellWidth,cellHeight-1);

        textRow(text);

        repeatVerticalCellSpacer(cellWidth,cellHeight-1);

        topRightCell_lastRow(cellWidth);

        return stringBuilderCell.toString();
    }

}
