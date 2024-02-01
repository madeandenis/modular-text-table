package org.example.text.styles;

import org.example.constants.Unicode;

public enum TableStyles {

    BoxDrawing(new char[][] {
            // Header Walls
            {Unicode.boxMatrix[5][0]},  // (═) Horizontal wall
            {Unicode.boxMatrix[0][2]},  // (│) Vertical Wall
            // Cell Walls
            {Unicode.boxMatrix[0][0]},  // (─) Horizontal wall
            {Unicode.boxMatrix[0][2]},  // (│) Vertical Wall

            // --Top Cells-- (index 4-6)
            // <- Single Cell corners (upLeft,upRight,bottomLeft,bottomRight)
            {
                Unicode.boxMatrix[5][2],    // ╒
                Unicode.boxMatrix[5][5],    // ╕
                Unicode.boxMatrix[5][14],   // ╞
                Unicode.boxMatrix[6][1]     // ╡
            },
            // <- Left Cell corners
            {
                Unicode.boxMatrix[5][2],    // ╒
                Unicode.boxMatrix[6][4],    // ╤
                Unicode.boxMatrix[5][14],   // ╞
                Unicode.boxMatrix[6][10],   // ╤
            },
            // <- Right Cell corners
            {
                Unicode.boxMatrix[6][4],    // ╤
                Unicode.boxMatrix[5][5],    // ╕
                Unicode.boxMatrix[6][10],   // ╪
                Unicode.boxMatrix[6][1],    // ╡
            },

            // -- Middle Cells (index 7-9)
            // <- Single Cell corners (bottomLeft,bottomRight)
            {
                Unicode.boxMatrix[1][12],  // ├
                Unicode.boxMatrix[2][4],   // ┤
            },
            // <- Left Cell corners
            {
                Unicode.boxMatrix[1][12],   // ├
                Unicode.boxMatrix[3][12],   // ┼
            },
            // <- Right Cell corners
            {
                Unicode.boxMatrix[3][12],  // ┼
                Unicode.boxMatrix[2][4],   // ┤
            },

            // -- Bottom Cells (index 10-12)
            // <- Single Cell corners (bottomLeft,bottomRight)
            {
                    Unicode.boxMatrix[1][4],  // └
                    Unicode.boxMatrix[1][8],  // ┘
            },
            // <- Left Cell corners
            {
                    Unicode.boxMatrix[1][4],  // └
                    Unicode.boxMatrix[3][4],  // ┴
            },
            // <- Right Cell corners
            {
                    Unicode.boxMatrix[3][4],  // ┴
                    Unicode.boxMatrix[1][8],  // ┘
            },

            //  -- Cell Spacers -- (index 13-15)
            // <-  Top Cell Spacers
            {
                    Unicode.boxMatrix[6][4],  // ╤ (upperJunction)
                    Unicode.boxMatrix[6][10], // ╪ (bottomJunction)
            },
            // <-  Middle Cell Spacers
            {
                    Unicode.boxMatrix[3][12]  // ┼ (bottomJunction)
            },
            // <-  Bottom Cell Spacers
            {
                    Unicode.boxMatrix[3][4]  // ┴ (bottomJunction)
            }


    });

    private final char[][] design;

    TableStyles(char[][] design){
        this.design = design;
    }

    public char[][] getStyle() {
        return design;
    }

}
