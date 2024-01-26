package org.example.constants;

import javax.swing.*;

public class Unicode {
    // Box-Drawing unicode characters (U+250x -> U+257x)

    /**
     *
    Official Unicode Consortium code chart
                                                        (A) (B) (C) (D) (E) (()
                0	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15
    0   U+250x	─	━	│	┃	┄	┅	┆	┇	┈	┉	┊	┋	┌	┍	┎	┏
    1   U+251x	┐	┑	┒	┓	└	┕	┖	┗	┘	┙	┚	┛	├	┝	┞	┟
    2   U+252x	┠	┡	┢	━━	┤	┥	┦	┧	┨	┩	┪	┫	┬	┭	┮	┯
    3   U+253x	┰	┱	┲	┳	┴	┵	┶	┷	┸	┹	┺	┻	┼	┽	┾	┿
    4   U+254x	╀	╁	╂	╃	╄	╅	╆	╇	╈	╉	╊	╋	╌	╍	╎	╏
    5   U+255x	═	║	╒	╓	╔	╕	╖	╗	╘	╙	╚	╛	╜	╝	╞	╟
    6   U+256x	╠	╡	╢	╣	╤	╥	╦	╧	╨	╩	╪	╫	╬	╭	╮	╯
    7   U+257x	╰	╱	╲	╳	╴	╵	╶	╷	╸	╹	╺	╻	╼	╽	╾	╿
     *
     */

    private static char[][] generateUnicodeMatrix(int rows, int columns, int startUnicodeChar) {

        char[][] unicodeMatrix = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                unicodeMatrix[i][j] = (char) startUnicodeChar;
                startUnicodeChar++;
            }
        }
        return unicodeMatrix;
    }

    static private final int startCharacter_boxDrawing = 0x2500;
    static public char [][] boxMatrix = generateUnicodeMatrix(8,16,startCharacter_boxDrawing);

}
