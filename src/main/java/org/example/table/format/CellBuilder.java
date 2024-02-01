package org.example.table.format;

import org.example.text.utils.TextUtils;

public class CellBuilder {
    private final StringBuilder builder;
    private final CellWallBuilder cellWallBuilder;

    public CellBuilder(){
        builder = new StringBuilder();
        cellWallBuilder = new CellWallBuilder();
    }

    private String buildCellRow(String content, char leftBorder, char rightBorder){
        if (content == null || content.isEmpty()){
            throw new IllegalArgumentException("Content cannot be empty or null");
        }

        return leftBorder +
                content +
                rightBorder +
                System.lineSeparator();
    }

    //They made the table corners
    public String EnclosedCell(String content,
                               int cellWidth, int cellHeight,
                               char horizontalWall, char verticalWall,
                               char topLeftCorner, char topRightCorner,
                               char bottomLeftCorner, char bottomRightCorner,
                               char whiteSpace
                               )
    {
        String horizontalWalls = cellWallBuilder.horizontalSpacer(horizontalWall,cellWidth);
        String verticalWalls = cellWallBuilder.verticalSpacer(CellWallBuilder.BoundType.BOUNDED,
                                                                verticalWall,
                                                                whiteSpace,
                                                                cellWidth);
        String cellHeightRows = cellWallBuilder.repeatVerticalSpacer(verticalWalls,cellHeight);

        builder.setLength(0);

        // Top-Horizontal Wall
        builder.append(
                buildCellRow(horizontalWalls,topLeftCorner,topRightCorner)
        );
        // It gives height to the cell
        builder.append(cellHeightRows);
        // Content row
        builder.append(
                buildCellRow(content,verticalWall,verticalWall)
        );
        // It gives height to the cell
        builder.append(cellHeightRows);
        // Bottom-Horizontal Wall
        builder.append(
                buildCellRow(horizontalWalls,bottomLeftCorner,bottomRightCorner)
        );

        return builder.toString();
    }
    // They made up the middle(left-right) table part
    public String NoVerticalBordersCell(String content,
                               int cellWidth, int cellHeight,
                               char horizontalWall,
                               char whiteSpace
                               )
    {
        // We subtract 2 for the leftCorner and rightCorner
        String horizontalWalls = cellWallBuilder.horizontalSpacer(horizontalWall,cellWidth-2);
        String verticalWalls = cellWallBuilder.verticalSpacer(CellWallBuilder.BoundType.BOUNDED,
                                                                whiteSpace,
                                                                whiteSpace,
                                                                cellWidth-2);
        String cellHeightRows = cellWallBuilder.repeatVerticalSpacer(verticalWalls,cellHeight);

        builder.setLength(0);

        // Top-Horizontal Wall
        builder.append(
                buildCellRow(horizontalWalls,horizontalWall,horizontalWall)
        );

        builder.append(cellHeightRows);
        // Content row
        builder.append(content).append(System.lineSeparator());

        builder.append(cellHeightRows);

        // Bottom-Horizontal Wall
        builder.append(
                buildCellRow(horizontalWalls,horizontalWall,horizontalWall)
        );

        return builder.toString();
    }
    // They made the rest of the table under the headers
    public String NoTopHorizontalWallCell(String content,
                               int cellWidth, int cellHeight,
                               char horizontalWall, char verticalWall,
                               char bottomLeftCorner, char bottomRightCorner,
                               char whiteSpace
    )
    {
        String horizontalWalls = cellWallBuilder.horizontalSpacer(horizontalWall,cellWidth);
        String verticalWalls = cellWallBuilder.verticalSpacer(CellWallBuilder.BoundType.BOUNDED,
                verticalWall,
                whiteSpace,
                cellWidth);
        String cellHeightRows = cellWallBuilder.repeatVerticalSpacer(verticalWalls,cellHeight);

        builder.setLength(0);



        builder.append(cellHeightRows);
        // Content row
        builder.append(
                buildCellRow(content,verticalWall,verticalWall)
        );
        // It gives height to the cell
        builder.append(cellHeightRows);
        // Bottom-Horizontal Wall
        builder.append(
                buildCellRow(horizontalWalls,bottomLeftCorner,bottomRightCorner)
        );

        return builder.toString();
    }
    // Middle Cells
    public String NoVerticalBorders_and_TopHorizontalWall(String content,
                                                          int cellWidth, int cellHeight,
                                                          char horizontalWall,
                                                          char whiteSpace)
    {
        return TextUtils.removeFirstLine(NoVerticalBordersCell(content, cellWidth, cellHeight, horizontalWall, whiteSpace));
    }


}
