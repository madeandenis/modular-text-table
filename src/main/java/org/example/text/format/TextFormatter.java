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
        setWhiteSpace(" ");
        setAlignmentState(Alignment.CENTER);
    }
    public TextFormatter(T rawData,int containerWidth){
        setWhiteSpace(" ");
        updateData(rawData);
        setContainerWidth(containerWidth);
        setAlignmentState(Alignment.CENTER);
    }


    // Setters
    public void updateData(T data) {
        this.stringData = data.toString();
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }

    public void setFormattedData(String formattedData) {
        this.formattedData = formattedData;
    }
    public void setContainerWidth(int containerWidth) {
        this.containerWidth = containerWidth;
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
        return formattedData != null ? formattedData : getStringData();
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
        setStringData(TextCasing.setAllLowerCase(getStringData()));
        defaultAlignment();
    }
    public void upperCase(){
        setStringData(TextCasing.setAllUpperCase(getStringData()));
        defaultAlignment();
    }
    public void capitalize(){
        setStringData(TextCasing.setCapitalizedText(getStringData()));
        defaultAlignment();
    }
}
