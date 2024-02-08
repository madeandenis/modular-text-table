package org.example.text.format;

import org.example.text.styles.TextAlignment;
import org.example.text.styles.TextCasing;

public class TextFormatter<T> {

    public enum Alignment{
        LEFT,CENTER,RIGHT;
    }

    private String stringData;
    private String formattedData;
    private int containerWidth;
    private Alignment alignmentState;
    private String whiteSpace;

    public TextFormatter(){
        setDefaultAttributes();
    }
    public TextFormatter(T rawData,int containerWidth){
        setDefaultAttributes();
        updateData(rawData);
        setContainerWidth(containerWidth);
    }
    private void setDefaultAttributes(){
        setWhiteSpace(" ");
        setAlignmentState(Alignment.CENTER);
    }

    // Setters
    public void updateData(T data) {
        if (data == null) {
            this.stringData = "null";
        } else {
            this.stringData = String.valueOf(data);
        }
        // Updating the container width every time the content is being replaced ensures
        // that cells does not remain the previous width
        setContainerWidth(this.stringData.length());
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
        setContainerWidth(this.stringData.length());
    }

    public void setContainerWidth(int containerWidth) {
        if(containerWidth >= 0 && containerWidth >= this.stringData.length()) {
            this.containerWidth = containerWidth;
        }
        else {
            System.out.println("Container width must non-negative and greater or equal to string data length.");
        }
    }

    public void setAlignmentState(Alignment alignmentState) {
        this.alignmentState = alignmentState;
    }

    public void setWhiteSpace(String whiteSpace) {
        this.whiteSpace = whiteSpace;
    }

    // Getters
    public String getStringData() {
        return stringData;
    }

    public int getContainerWidth() {
        return containerWidth;
    }

    public Alignment getAlignmentState() {
        return alignmentState;
    }

    public String getWhiteSpace() {
        return whiteSpace;
    }

    public String getFormattedData() {
        defaultAlignment();
        return (formattedData != null) ? formattedData : getStringData();
    }

    // Text Styles
    public void defaultAlignment(){
        align(alignmentState);
    }
    public void align(Alignment alignment){
        setAlignmentState(alignment);
        switch (alignment){
            case LEFT -> {
                formattedData = TextAlignment.left(getStringData(),getContainerWidth(),getWhiteSpace());
            }
            case CENTER -> {
                formattedData = TextAlignment.center(getStringData(),getContainerWidth(),getWhiteSpace());
            }
            case RIGHT -> {
                formattedData = TextAlignment.right(getStringData(),getContainerWidth(),getWhiteSpace());
            }
        }
    }

    public void lowerCase(){
        updateAndRealign(TextCasing.toAllLowerCase(getStringData()));
    }
    public void upperCase(){
        updateAndRealign(TextCasing.toAllUpperCase(getStringData()));
    }
    public void capitalize(){
        updateAndRealign(TextCasing.toCapitalizedText(getStringData()));
    }

    // When changing the casing of a word using the setStringData function,
    // the initial width is reset with the new one.
    // However, if the cell was resized with a new width, we want to retain that current width.
    // To achieve this, we create a backup container width.
    private void updateAndRealign(String stringData){
        int backupContainerWidth = getContainerWidth();
        setStringData(stringData);
        setContainerWidth(backupContainerWidth);
        defaultAlignment();
    }
}
