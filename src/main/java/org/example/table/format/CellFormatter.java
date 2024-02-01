package org.example.table.format;

import org.example.table.model.Cell;
import org.example.text.styles.TableStyles;

public class CellFormatter {
    static CellBuilder cellBuilder = new CellBuilder();

    public String format(Cell.CellType cellType, TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace){

        return switch (cellType) {
            // Top
            case TOP_SINGLE     -> TopCell.buildSingle      (style, content, cellWidth, cellHeight, whiteSpace);
            case TOP_LEFT       -> TopCell.buildLeft        (style, content, cellWidth, cellHeight, whiteSpace);
            case TOP_CENTER     -> TopCell.buildCenter      (style, content, cellWidth, cellHeight, whiteSpace);
            case TOP_RIGHT      -> TopCell.buildRight       (style, content, cellWidth, cellHeight, whiteSpace);

            // Middle
            case MIDDLE_SINGLE  -> MiddleCell.buildSingle   (style, content, cellWidth, cellHeight, whiteSpace);
            case MIDDLE_LEFT    -> MiddleCell.buildLeft     (style, content, cellWidth, cellHeight, whiteSpace);
            case MIDDLE_CENTER  -> MiddleCell.buildCenter   (style, content, cellWidth, cellHeight, whiteSpace);
            case MIDDLE_RIGHT   -> MiddleCell.buildRight    (style, content, cellWidth, cellHeight, whiteSpace);

            // Bottom
            case BOTTOM_SINGLE  -> BottomCell.buildSingle   (style, content, cellWidth, cellHeight, whiteSpace);
            case BOTTOM_LEFT    -> BottomCell.buildLeft     (style, content, cellWidth, cellHeight, whiteSpace);
            case BOTTOM_CENTER  -> BottomCell.buildCenter   (style, content, cellWidth, cellHeight, whiteSpace);
            case BOTTOM_RIGHT   -> BottomCell.buildRight    (style, content, cellWidth, cellHeight, whiteSpace);

        };
    }

    public static class TopCell {
        public static String buildSingle(TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.EnclosedCell(
                    content,
                    cellWidth, cellHeight,
                    style.getStyle()[0][0], style.getStyle()[1][0],
                    style.getStyle()[4][0], style.getStyle()[4][1],
                    style.getStyle()[4][2], style.getStyle()[4][3],
                    whiteSpace.charAt(0)
            );
        }

        public static String buildLeft(TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.EnclosedCell(
                    content,
                    cellWidth, cellHeight,
                    style.getStyle()[0][0], style.getStyle()[1][0],
                    style.getStyle()[5][0], style.getStyle()[5][1],
                    style.getStyle()[5][2], style.getStyle()[5][3],
                    whiteSpace.charAt(0)
            );
        }

        public static String buildRight(TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.EnclosedCell(
                    content,
                    cellWidth, cellHeight,
                    style.getStyle()[0][0], style.getStyle()[1][0],
                    style.getStyle()[6][0], style.getStyle()[6][1],
                    style.getStyle()[6][2], style.getStyle()[6][3],
                    whiteSpace.charAt(0)
            );
        }

        public static String buildCenter(TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.NoVerticalBordersCell(
                    content,
                    cellWidth, cellHeight,
                    style.getStyle()[0][0],
                    whiteSpace.charAt(0)
            );
        }

    }
    public static class MiddleCell {
        public static String buildSingle(TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.NoTopHorizontalWallCell(
                    content,
                    cellWidth, cellHeight,
                    style.getStyle()[2][0], style.getStyle()[3][0],
                    style.getStyle()[7][0], style.getStyle()[7][1],
                    whiteSpace.charAt(0)
            );
        }

        public static String buildLeft(TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.NoTopHorizontalWallCell(
                    content,
                    cellWidth, cellHeight,
                    style.getStyle()[2][0], style.getStyle()[3][0],
                    style.getStyle()[8][0], style.getStyle()[8][1],
                    whiteSpace.charAt(0)
            );
        }

        public static String buildRight(TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.NoTopHorizontalWallCell(
                    content,
                    cellWidth, cellHeight,
                    style.getStyle()[2][0], style.getStyle()[3][0],
                    style.getStyle()[9][0], style.getStyle()[9][1],
                    whiteSpace.charAt(0)
            );
        }

        public static String buildCenter(TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.NoVerticalBorders_and_TopHorizontalWall(
                    content,
                    cellWidth, cellHeight,
                    style.getStyle()[2][0],
                    whiteSpace.charAt(0)
            );
        }

    }

    public static class BottomCell {

        public static String buildSingle(TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.NoTopHorizontalWallCell(
                    content,
                    cellWidth, cellHeight,
                    style.getStyle()[2][0], style.getStyle()[3][0],
                    style.getStyle()[10][0], style.getStyle()[10][1],
                    whiteSpace.charAt(0)
            );
        }

        public static String buildLeft(TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.NoTopHorizontalWallCell(
                    content,
                    cellWidth, cellHeight,
                    style.getStyle()[2][0], style.getStyle()[3][0],
                    style.getStyle()[11][0], style.getStyle()[11][1],
                    whiteSpace.charAt(0)
            );
        }

        public static String buildRight(TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.NoTopHorizontalWallCell(
                    content,
                    cellWidth, cellHeight,
                    style.getStyle()[2][0], style.getStyle()[3][0],
                    style.getStyle()[12][0], style.getStyle()[12][1],
                    whiteSpace.charAt(0)
            );
        }

        public static String buildCenter(TableStyles style, String content, int cellWidth, int cellHeight, String whiteSpace) {
            return cellBuilder.NoVerticalBorders_and_TopHorizontalWall(
                    content,
                    cellWidth, cellHeight,
                    style.getStyle()[2][0],
                    whiteSpace.charAt(0)
            );
        }

    }

}
