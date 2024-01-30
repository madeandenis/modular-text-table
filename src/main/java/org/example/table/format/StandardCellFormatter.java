package org.example.table.format;

import org.example.constants.Unicode;
import org.example.table.model.Cell;
import org.example.text.utils.TextUtils;

public class StandardCellFormatter {

    public String formatCell(Cell.CellType cellType, String cellText, int cellWidth, int cellHeight, String whiteSpace){
        switch (cellType){
            // Top
            case TOP_SINGLE -> {
                TopCell topCell = new TopCell();
                return topCell.buildSingle(cellText,cellWidth,cellHeight,whiteSpace);
            }
            case TOP_LEFT -> {
                TopCell topCell = new TopCell();
                return topCell.buildLeft(cellText,cellWidth,cellHeight,whiteSpace);
            }
            case TOP_CENTER -> {
                TopCell topCell = new TopCell();
                return topCell.buildCenter(cellText,cellWidth,cellHeight,whiteSpace);
            }
            case TOP_RIGHT -> {
                TopCell topCell = new TopCell();
                return topCell.buildRight(cellText,cellWidth,cellHeight,whiteSpace);
            }

            //Middle
            case MIDDLE_SINGLE -> {
                MiddleCell middleCell = new MiddleCell();
                return middleCell.buildSingle(cellText,cellWidth,cellHeight,whiteSpace);
            }
            case MIDDLE_LEFT -> {
                MiddleCell middleCell = new MiddleCell();
                return middleCell.buildLeft(cellText,cellWidth,cellHeight,whiteSpace);
            }
            case MIDDLE_CENTER -> {
                MiddleCell middleCell = new MiddleCell();
                return middleCell.buildCenter(cellText,cellWidth,cellHeight,whiteSpace);
            }
            case MIDDLE_RIGHT -> {
                MiddleCell middleCell = new MiddleCell();
                return middleCell.buildRight(cellText,cellWidth,cellHeight,whiteSpace);
            }

            // Bottom
            case BOTTOM_SINGLE -> {
                BottomCell bottomCell = new BottomCell();
                return bottomCell.buildSingle(cellText,cellWidth,cellHeight,whiteSpace);
            }
            case BOTTOM_LEFT -> {
                BottomCell bottomCell = new BottomCell();
                return bottomCell.buildLeft(cellText,cellWidth,cellHeight,whiteSpace);
            }
            case BOTTOM_CENTER -> {
                BottomCell bottomCell = new BottomCell();
                return bottomCell.buildCenter(cellText,cellWidth,cellHeight,whiteSpace);
            }
            case BOTTOM_RIGHT -> {
                BottomCell bottomCell = new BottomCell();
                return bottomCell.buildRight(cellText,cellWidth,cellHeight,whiteSpace);
            }
            default -> {
                throw new IllegalArgumentException("Invalid CellType format");
            }
        }
    }

    public static class Spacer{

        public enum SpacerType{
            SINGLE_SPACER,DOUBLE_SPACER;
        }
        public enum BoundType{
            BOUNDED,UNBOUNDED;
        }
        private String repeatCharacter(char character, int count){
            return String.valueOf(character).repeat(count);
        }
        public String horizontalSpacer(int cellWidth, SpacerType spacerType){
            if(spacerType == SpacerType.SINGLE_SPACER){
                // Returns : ─────────
                return repeatCharacter(Unicode.boxMatrix[0][0],cellWidth);
            }
            else if(spacerType == SpacerType.DOUBLE_SPACER){
                // Returns : ═════════
                return repeatCharacter(Unicode.boxMatrix[5][0],cellWidth);
            }
            else{
                throw new IllegalArgumentException("Invalid spacerType format");
            }
        }

        public String verticalSpacer(int cellWidth, String whiteSpace, BoundType boundType){
            if(boundType == BoundType.BOUNDED){
                // Returns : |.......|
                return String.valueOf(Unicode.boxMatrix[0][2])+
                        repeatCharacter(whiteSpace.charAt(0),cellWidth)+
                        String.valueOf(Unicode.boxMatrix[0][2])+
                        "\n";
            }
            else if(boundType == BoundType.UNBOUNDED){
                // Returns :  .......
                return repeatCharacter(whiteSpace.charAt(0),cellWidth) + "\n";
            }
            else{
                throw new IllegalArgumentException("Invalid BoundType format");
            }

        }
        private String repeatVerticalSpacer(int cellWidth, String whiteSpace, BoundType boundType, int n){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < n; i++) {
                stringBuilder.append(verticalSpacer(cellWidth,whiteSpace,boundType));
            }
            return stringBuilder.toString();
        }

    }
    private static class CellBuilder{
        StringBuilder stringBuilder = new StringBuilder();
        Spacer spacer = new Spacer();

        private String buildCellRow(String content, char startCorner, char endCorner) {
            if (content == null) {
                throw new IllegalArgumentException("Content cannot be null");
            }

            StringBuilder stringBuilder = new StringBuilder();
            Spacer spacer = new Spacer();

            stringBuilder.setLength(0);

            stringBuilder.append(startCorner);
            stringBuilder.append(content);
            stringBuilder.append(endCorner);
            stringBuilder.append("\n");

            return stringBuilder.toString();
        }

        // * Title cell has doubleHorizontalCellSpacer
        public String enclosedCell(String content,
                                   char topLeftCorner, char topRightCorner,
                                   char bottomLeftCorner, char bottomRightCorner,
                                   int cellWidth, int cellHeight,
                                   org.example.table.model.Cell.CellType cellType,
                                   String whiteSpace)
        {
            String cellSurface = switch (cellType) {
                case TOP_SINGLE, TOP_LEFT, TOP_CENTER, TOP_RIGHT -> spacer.horizontalSpacer(cellWidth, Spacer.SpacerType.DOUBLE_SPACER);
                default -> spacer.horizontalSpacer(cellWidth, Spacer.SpacerType.SINGLE_SPACER);
            };

            stringBuilder.setLength(0);

            // Top Surface
            stringBuilder.append(
                    buildCellRow(
                            cellSurface,
                            topLeftCorner,
                            topRightCorner
                    ));
            // Gives height to the table
            stringBuilder.append(spacer.repeatVerticalSpacer(cellWidth,whiteSpace, Spacer.BoundType.BOUNDED,cellHeight-1));
            // Content Row
            stringBuilder.append(
                    buildCellRow(
                            content,
                            Unicode.boxMatrix[0][2],
                            Unicode.boxMatrix[0][2]
                    ));
            // Gives height to the table
            stringBuilder.append(spacer.repeatVerticalSpacer(cellWidth,whiteSpace, Spacer.BoundType.BOUNDED,cellHeight-1));
            // Bottom Surface
            stringBuilder.append(
                    buildCellRow(
                            cellSurface,
                            bottomLeftCorner,
                            bottomRightCorner
                    ));

            return stringBuilder.toString();
        }
        // Has no Lef-Right Bounds
        public String openCell(String content,
                               int cellWidth, int cellHeight,
                               Cell.CellType cellType,
                               String whiteSpace)
        {
            String cellSurface = switch (cellType) {
                case TOP_SINGLE, TOP_LEFT, TOP_CENTER, TOP_RIGHT -> spacer.horizontalSpacer(cellWidth, Spacer.SpacerType.DOUBLE_SPACER);
                default -> spacer.horizontalSpacer(cellWidth, Spacer.SpacerType.SINGLE_SPACER);
            };

            stringBuilder.setLength(0);

            stringBuilder.append(cellSurface).append("\n");
            stringBuilder.append(spacer.repeatVerticalSpacer(cellWidth,whiteSpace, Spacer.BoundType.UNBOUNDED,cellHeight-1));
            stringBuilder.append(content).append("\n");
            stringBuilder.append(spacer.repeatVerticalSpacer(cellWidth,whiteSpace, Spacer.BoundType.UNBOUNDED,cellHeight-1));
            stringBuilder.append(cellSurface).append("\n");

            return stringBuilder.toString();
        }
        // Has no Top Surface
        public String noTopSurfaceCell(String content,
                                   char bottomLeftCorner, char bottomRightCorner,
                                   int cellWidth, int cellHeight,
                                   Cell.CellType cellType,
                                   String whiteSpace)
        {
            String cellSurface = switch (cellType) {
                case TOP_SINGLE, TOP_LEFT, TOP_CENTER, TOP_RIGHT -> spacer.horizontalSpacer(cellWidth, Spacer.SpacerType.DOUBLE_SPACER);
                default -> spacer.horizontalSpacer(cellWidth, Spacer.SpacerType.SINGLE_SPACER);
            };

            stringBuilder.setLength(0);

            // Gives height to the table
            stringBuilder.append(spacer.repeatVerticalSpacer(cellWidth,whiteSpace, Spacer.BoundType.BOUNDED,cellHeight-1));
            // Content Row
            stringBuilder.append(
                    buildCellRow(
                            content,
                            Unicode.boxMatrix[0][2],
                            Unicode.boxMatrix[0][2]
                    ));
            // Gives height to the table
            stringBuilder.append(spacer.repeatVerticalSpacer(cellWidth,whiteSpace, Spacer.BoundType.BOUNDED,cellHeight-1));
            // Bottom Surface
            stringBuilder.append(
                    buildCellRow(
                            cellSurface,
                            bottomLeftCorner,
                            bottomRightCorner
                    ));

            return stringBuilder.toString();
        }
    }
    private static class TopCell{
        CellBuilder cellBuilder = new CellBuilder();

        public String buildSingle(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.enclosedCell(
                    text,
                    Unicode.boxMatrix[5][2], // ╒
                    Unicode.boxMatrix[5][5], // ╕
                    Unicode.boxMatrix[5][14],// ╞
                    Unicode.boxMatrix[6][1], // ╡
                    cellWidth,
                    cellHeight,
                    Cell.CellType.TOP_SINGLE,
                    whiteSpace
            );
        }
        public String buildLeft(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.enclosedCell(
                    text,
                    Unicode.boxMatrix[5][2], // ╒
                    Unicode.boxMatrix[6][4], // ╤
                    Unicode.boxMatrix[5][14],// ╞
                    Unicode.boxMatrix[6][10], // ╤
                    cellWidth,
                    cellHeight,
                    Cell.CellType.TOP_SINGLE,
                    whiteSpace
            );
        }
        public String buildCenter(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.openCell(
                    text,
                    cellWidth,
                    cellHeight,
                    Cell.CellType.TOP_SINGLE,
                    whiteSpace
            );
        }
        public String buildRight(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.enclosedCell(
                    text,
                    Unicode.boxMatrix[6][4], // ╤
                    Unicode.boxMatrix[5][5], // ╕
                    Unicode.boxMatrix[6][10],// ╪
                    Unicode.boxMatrix[6][1], // ╡
                    cellWidth,
                    cellHeight,
                    org.example.table.model.Cell.CellType.TOP_SINGLE,
                    whiteSpace
            );
        }

    }
    private static class MiddleCell{
        CellBuilder cellBuilder = new CellBuilder();
        Spacer spacer = new Spacer();

        StringBuilder stringBuilder = new StringBuilder();

        public String buildSingle(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.noTopSurfaceCell(
                    text,
                    Unicode.boxMatrix[1][12],  // ├
                    Unicode.boxMatrix[2][4],   // ┤
                    cellWidth,
                    cellHeight,
                    Cell.CellType.BOTTOM_SINGLE,
                    whiteSpace
            );
        }
        public String buildLeft(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.noTopSurfaceCell(
                    text,
                    Unicode.boxMatrix[1][12],  // ├
                    Unicode.boxMatrix[3][12],   // ┼
                    cellWidth,
                    cellHeight,
                    Cell.CellType.BOTTOM_SINGLE,
                    whiteSpace
            );
        }
        public String buildRight(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.noTopSurfaceCell(
                    text,
                    Unicode.boxMatrix[3][12],  // ┼
                    Unicode.boxMatrix[2][4],   // ┤
                    cellWidth,
                    cellHeight,
                    Cell.CellType.BOTTOM_SINGLE,
                    whiteSpace
            );
        }

        public String buildCenter(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return TextUtils.removeFirstLine(cellBuilder.openCell(text,cellWidth,cellHeight, Cell.CellType.MIDDLE_CENTER,whiteSpace));
        }

    }
    private static class BottomCell{
        CellBuilder cellBuilder = new CellBuilder();

        public String buildSingle(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.noTopSurfaceCell(
                    text,
                    Unicode.boxMatrix[1][4],  // └
                    Unicode.boxMatrix[1][8],  // ┘
                    cellWidth,
                    cellHeight,
                    Cell.CellType.BOTTOM_SINGLE,
                    whiteSpace
            );
        }
        public String buildLeft(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.noTopSurfaceCell(
                    text,
                    Unicode.boxMatrix[1][4],  // └
                    Unicode.boxMatrix[3][4],  // ┴
                    cellWidth,
                    cellHeight,
                    Cell.CellType.BOTTOM_SINGLE,
                    whiteSpace
            );
        }
        public String buildCenter(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return TextUtils.removeFirstLine(cellBuilder.openCell(
                    text,
                    cellWidth,
                    cellHeight,
                    Cell.CellType.BOTTOM_SINGLE,
                    whiteSpace
            ));
        }
        public String buildRight(String text, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.noTopSurfaceCell(
                    text,
                    Unicode.boxMatrix[3][4],  // ┴
                    Unicode.boxMatrix[1][8],  // ┘
                    cellWidth,
                    cellHeight,
                    Cell.CellType.BOTTOM_SINGLE,
                    whiteSpace
            );
        }

    }

}
