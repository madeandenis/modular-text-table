package org.example.table.format;

import org.example.table.model.Cell;
import org.example.text.styles.TableStyles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellWallBuilderTest {

    private CellWallBuilder wallBuilder;
    private final int width = 10;
    private final char spacer = '─';
    private final char border = '║';

    @BeforeEach
    void createWallBuilder(){
        wallBuilder = new CellWallBuilder();
    }
    @Test
    void horizontalSpacer() {
        System.out.println("Horizontal Cell Walls");
        System.out.println(wallBuilder.horizontalSpacer(spacer,width));
        assertEquals(String.valueOf(spacer).repeat(width),wallBuilder.horizontalSpacer(spacer,width));
    }

    @Test
    void verticalSpacer() {
        System.out.println("Vertical Cell Walls");
        String verticalSpacer = wallBuilder.verticalSpacer(CellWallBuilder.BoundType.BOUNDED,border,spacer,width);
        System.out.println(verticalSpacer);
        assertEquals(
                border + String.valueOf(spacer).repeat(width) + border,
                wallBuilder.verticalSpacer(CellWallBuilder.BoundType.BOUNDED,border,spacer,width));
    }

    @Test
    void repeatVerticalSpacer() {
        System.out.println("Multiple Vertical Cell Walls (empty cell rows -> make up cell height)");
        String verticalSpacer = wallBuilder.verticalSpacer(CellWallBuilder.BoundType.UNBOUNDED,border,spacer,width);
        System.out.println(wallBuilder.repeatVerticalSpacer(verticalSpacer,5));
        verticalSpacer = wallBuilder.verticalSpacer(CellWallBuilder.BoundType.BOUNDED,border,spacer,width);
        System.out.println(wallBuilder.repeatVerticalSpacer(verticalSpacer,5));

    }
}