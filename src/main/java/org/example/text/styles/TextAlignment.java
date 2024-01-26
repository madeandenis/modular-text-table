package org.example.text.styles;

public class TextAlignment {

    private static void validateTextFitInContainer(String text, int textContainerWidth) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        if (textContainerWidth <= 0) {
            throw new IllegalArgumentException("Invalid container width: " + textContainerWidth);
        }
        if (text.length() > textContainerWidth) {
            throw new IllegalArgumentException("Text length: " + text.length() +
                    " is larger than the container width: " + textContainerWidth);
        }
    }

    private static void validateWhiteSpace(String whiteSpace){
        if(whiteSpace.length() != 1){
            throw new IllegalArgumentException("White space must contain exactly one character");
        }
    }

    public static String left(String text, int textContainerWidth, String whiteSpace){

        validateWhiteSpace(whiteSpace);
        validateTextFitInContainer(text,textContainerWidth);

        int leftPadding;
        int rightPadding;

        if(text.length() == textContainerWidth)
            return text;
        else if(text.length() == textContainerWidth-1)
            leftPadding = 0;
        else{
            leftPadding = 1;
        }

        rightPadding = textContainerWidth - text.length() - leftPadding;

        return whiteSpace.repeat(leftPadding) + text + whiteSpace.repeat(rightPadding);
    }

    public static String center(String text, int textContainerWidth, String whiteSpace){

        validateWhiteSpace(whiteSpace);
        validateTextFitInContainer(text,textContainerWidth);

        if(text.length() == textContainerWidth)
            return text;

        int totalPadding = textContainerWidth - text.length();
        int leftPadding = 0;
        int rightPadding = 0;

        if(totalPadding % 2 == 0) {
            leftPadding = totalPadding / 2;
            rightPadding = totalPadding / 2;
        }
        else{
            // If the padding cannot be split equally, the right padding gains one extra space.
            leftPadding = totalPadding / 2;
            rightPadding = leftPadding + 1;
        }

        return  whiteSpace.repeat(leftPadding) + text + whiteSpace.repeat(rightPadding);
    }

    public static String right(String text, int textContainerWidth, String whiteSpace){

        validateWhiteSpace(whiteSpace);
        validateTextFitInContainer(text,textContainerWidth);

        int rightPadding;
        int leftPadding;

        if(text.length() == textContainerWidth)
            return text;
        else if(text.length() == textContainerWidth-1)
            rightPadding = 0;
        else{
            rightPadding = 1;
        }

        leftPadding = textContainerWidth - text.length() - rightPadding;

        return whiteSpace.repeat(leftPadding) + text + whiteSpace.repeat(rightPadding);
    }

}
