package org.example.table.format;

import org.example.constants.Unicode;
import org.example.text.utils.TextUtils;

public class CellWallBuilder {
    public enum BoundType{
        BOUNDED,UNBOUNDED;
    }

    // Builds horizontal wall of a cell
    public String horizontalSpacer(char spacerChar, int width){
        return TextUtils.repeatCharacter(
                spacerChar,
                width
        );
    }
    // Builds vertical walls of a cell using whiteSpace between them
    public String verticalSpacer(BoundType boundType,char borderChar, char whiteSpaceChar, int width){
        StringBuilder verticalSpacer = new StringBuilder();

        if(boundType == BoundType.BOUNDED){
                verticalSpacer.append(borderChar);
                verticalSpacer.append(horizontalSpacer(whiteSpaceChar,width));
                verticalSpacer.append(borderChar);
                return verticalSpacer.toString();
        }
        else if(boundType == BoundType.UNBOUNDED){
            return horizontalSpacer(whiteSpaceChar,width);
        }
        else{
            throw new IllegalArgumentException("Invalid BoundType format");
        }
    }
    public String repeatVerticalSpacer(String verticalSpacer, int nTimes){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nTimes-1; i++) {
            stringBuilder.append(verticalSpacer);
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }


}
