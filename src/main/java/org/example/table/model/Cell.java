package org.example.table.model;

import org.example.table.format.StandardCellFormatter;
import org.example.text.format.TextFormatter;

// Cell is a generic class with a type parameter
public class Cell<T> {

    public enum CellType{
        // SingleColumnTable
        TOP_SINGLE,MIDDLE_SINGLE,BOTTOM_SINGLE,

        // MultipleColumnTABLE
        // 3x3 cell variation
        TOP_LEFT,TOP_CENTER,TOP_RIGHT,
        MIDDLE_LEFT,MIDDLE_CENTER,MIDDLE_RIGHT,
        BOTTOM_LEFT,BOTTOM_CENTER,BOTTOM_RIGHT;

    };

    private T data;
    private Class<?> dataType;
    private CellType cellType;
    private int cellWidth;
    private int cellHeight = 1;
    private String whiteSpace = " ";

    private StandardCellFormatter standardGraphicCell;
    private TextFormatter<T> textFormatter;
    private Text text;

    // Constructors
    public Cell(T data, int cellWidth, CellType cellType){

        validateCellWidth(data,cellWidth);
        validateCellHeight(cellHeight);

        this.data = data;
        this.dataType = data.getClass();
        this.cellWidth = cellWidth;
        this.cellType = cellType;

        standardGraphicCell = new StandardCellFormatter();
        textFormatter = new TextFormatter<>(data,cellWidth);
        text = new Text(this);
    }

    // Overriding toString() Method
    @Override
    public String toString(){
        textFormatter.defaultAlignment();
        return standardGraphicCell.formatCell(cellType,textFormatter.getFormattedData(),cellWidth,cellHeight,whiteSpace);
    }


    // Data String Formatter
    private String dataToString(T data){
        return data.toString();
    }

    // Validators
    private void validateCellWidth(T data, int cellWidth){
        if (cellWidth < dataToString(data).length()) {
            throw new IllegalArgumentException("Cell width smaller than the content : " + dataToString(this.data) + ")");
        }
    }
    private void validateCellHeight(int cellHeight){
        if(cellHeight < 1){
            throw new IllegalArgumentException("Cell height must be greater or equal to 1 (current cell height : " + cellHeight + ")");
        }
    }

    // Getters
    public T getData() {
        return data;
    }

    public Class<?> getDataType() {
        return dataType;
    }

    public Text text(){
        return this.text;
    }

    public CellType getCellType() {
        return cellType;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }
    public String getWhiteSpace(){
        return this.whiteSpace;
    }

    // Setters
    public void setData(T data) {
        this.data = data;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }
    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }

    public void setWhiteSpace(String whiteSpace) {
        this.whiteSpace = whiteSpace;
        text.setWhiteSpace(whiteSpace);
    }
    public void setDefaultWhiteSpace() {
        this.whiteSpace = " ";
        text.setWhiteSpace(whiteSpace);
    }

    public class Text{
        private final Cell<?> cell;
        public Text(Cell<?> cell){
            this.cell = cell;
        }
        public void setWhiteSpace(String whiteSpace){
            cell.textFormatter.setWhiteSpace(whiteSpace);
        }
        public void alignLeft(){
            cell.textFormatter.align(TextFormatter.Alignment.LEFT);
        }
        public void alignCenter(){
            cell.textFormatter.align(TextFormatter.Alignment.CENTER);
        }
        public void alignRight(){
            cell.textFormatter.align(TextFormatter.Alignment.RIGHT);
        }
        public void lowerCase(){
            cell.textFormatter.lowerCase();
        }
        public void upperCase(){
            cell.textFormatter.upperCase();
        }
        public void capitalize(){
            cell.textFormatter.capitalize();
        }
    }

}
