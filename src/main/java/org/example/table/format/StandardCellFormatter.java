package org.example.table.format;

import org.example.constants.Unicode;
import org.example.table.model.Cell;

public class StandardCellFormatter {

    public String formatCell(Cell.CellType cellType, String cellText, int cellWidth, int cellHeight, String whiteSpace){
        switch (cellType){
            case TOP_SINGLE -> {
                TopCell topCell = new TopCell();
                return topCell.buildSingle(cellText,cellWidth,cellHeight,whiteSpace);
            }
            default -> {
                throw new IllegalArgumentException("Invalid CellType format");
            }
        }
    }

    public static class Spacer{
        public String singleHorizontalCellSpacer(int cellWidth){
            // U+2500	─
            return String.valueOf(Unicode.boxMatrix[0][0]).repeat(cellWidth);
        }
        // Output: ═════
        public String doubleHorizontalCellSpacer(int cellWidth){
            // U+2550	═
            return String.valueOf(Unicode.boxMatrix[5][0]).repeat(cellWidth);
        }
        // Output: |     |
        public String VerticalCellSpacer(int cellWidth, String whiteSpace){
            // U+2502	│
            return String.valueOf(Unicode.boxMatrix[0][2])+whiteSpace.repeat(cellWidth)+String.valueOf(Unicode.boxMatrix[0][2]);
        }
        private String repeatVerticalCellSpacer(int cellWidth, String whiteSpace, int n){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < n; i++) {
                stringBuilder.append(VerticalCellSpacer(cellWidth,whiteSpace));
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private static class CellRow{
        StringBuilder stringBuilder = new StringBuilder();
        Spacer spacer = new Spacer();

        public String build(String content, char startCorner, char endCorner) {
            stringBuilder.setLength(0);

            stringBuilder.append(startCorner);
            stringBuilder.append(content);
            stringBuilder.append(endCorner);
            stringBuilder.append("\n");

            return stringBuilder.toString();
        }
    }
    private static class CellBuilder{
        StringBuilder stringBuilder = new StringBuilder();
        Spacer spacer = new Spacer();
        CellRow rowBuilder = new CellRow();

        // * Title cell has doubleHorizontalCellSpacer
        public String joiningTitleCell(String content, char topLeftCorner, char topRightCorner,
                            char bottomLeftCorner, char bottomRightCorner,
                            int cellWidth, int cellHeight,
                           String whiteSpace)
        {
            stringBuilder.setLength(0);

            // First Row
            stringBuilder.append(
                    rowBuilder.build(
                            spacer.doubleHorizontalCellSpacer(cellWidth),
                            topLeftCorner,
                            topRightCorner
                            ));
            // Gives height to the table
            stringBuilder.append(spacer.repeatVerticalCellSpacer(cellWidth,whiteSpace,cellHeight-1));
            // Content Row
            stringBuilder.append(
                    rowBuilder.build(
                            content,
                            Unicode.boxMatrix[0][2],
                            Unicode.boxMatrix[0][2]
                    ));
            // Gives height to the table
            stringBuilder.append(spacer.repeatVerticalCellSpacer(cellWidth,whiteSpace,cellHeight-1));
            // Last Row
            stringBuilder.append(
                    rowBuilder.build(
                            spacer.doubleHorizontalCellSpacer(cellWidth),
                            bottomLeftCorner,
                            bottomRightCorner
                    ));
            return stringBuilder.toString();
        }
        public String joiningCell(String content, char topLeftCorner, char topRightCorner,
                                       char bottomLeftCorner, char bottomRightCorner,
                                       int cellWidth, int cellHeight,
                                       String whiteSpace)
        {
            stringBuilder.setLength(0);

            // First Row
            stringBuilder.append(
                    rowBuilder.build(
                            spacer.singleHorizontalCellSpacer(cellWidth),
                            topLeftCorner,
                            topRightCorner
                    ));
            // Gives height to the table
            stringBuilder.append(spacer.repeatVerticalCellSpacer(cellWidth,whiteSpace,cellHeight-1));
            // Content Row
            stringBuilder.append(
                    rowBuilder.build(
                            content,
                            topLeftCorner,
                            topRightCorner
                    ));
            // Gives height to the table
            stringBuilder.append(spacer.repeatVerticalCellSpacer(cellWidth,whiteSpace,cellHeight-1));
            // Last Row
            stringBuilder.append(
                    rowBuilder.build(
                            spacer.singleHorizontalCellSpacer(cellWidth),
                            bottomLeftCorner,
                            bottomRightCorner
                    ));
            return stringBuilder.toString();
        }
    }
    private static class TopCell{
        CellBuilder cellBuilder = new CellBuilder();

        public String buildSingle(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.joiningTitleCell(
                    text,
                    Unicode.boxMatrix[5][2], // ╒
                    Unicode.boxMatrix[5][5], // ╕
                    Unicode.boxMatrix[5][14],// ╞
                    Unicode.boxMatrix[6][1], // ╡
                    cellWidth,
                    cellHeight,
                    whiteSpace
            );
        }
        public String buildLeft(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.joiningTitleCell(
                    text,
                    Unicode.boxMatrix[5][2], // ╒
                    Unicode.boxMatrix[6][4], // ╤
                    Unicode.boxMatrix[5][14],// ╞
                    Unicode.boxMatrix[6][10], // ╤
                    cellWidth,
                    cellHeight,
                    whiteSpace
            );
        }
        public String buildRight(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.joiningTitleCell(
                    text,
                    Unicode.boxMatrix[6][4], // ╤
                    Unicode.boxMatrix[5][5], // ╕
                    Unicode.boxMatrix[6][10],// ╪
                    Unicode.boxMatrix[6][1], // ╡
                    cellWidth,
                    cellHeight,
                    whiteSpace
            );
        }

    }

}
