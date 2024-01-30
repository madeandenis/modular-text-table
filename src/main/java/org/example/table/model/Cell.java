package org.example.table.model;

import org.example.table.format.StandardCellFormatter;
import org.example.text.format.TextFormatter;
import org.example.text.utils.TextUtils;
import org.w3c.dom.Text;

// Cell is a generic class with a type parameter
public class Cell<T> {

    public enum CellType{
        // SingleColumnTable
        TOP_SINGLE,MIDDLE_SINGLE,BOTTOM_SINGLE,

        // MultipleColumnTABLE
        // 3x3 cell variation
        TOP_LEFT,TOP_CENTER,TOP_RIGHT,
        MIDDLE_LEFT,MIDDLE_CENTER,MIDDLE_RIGHT,
        BOTTOM_LEFT,BOTTOM_CENTER,BOTTOM_RIGHT

    }

    private T data;
    private Class<?> dataType;
    private CellType cellType;
    private int cellWidth;
    private int cellHeight;
    private String whiteSpace;

    private final StandardCellFormatter standardCellFormatter;
    private final TextFormatter<T> textFormatter;
    private final Text text;

    // Constructors
    public Cell(T data, int cellWidth, CellType cellType){
        this(data,cellWidth,1,cellType);
    }
    public Cell(T data, int cellWidth, int cellHeight, CellType cellType){

        validateCellWidth(data,cellWidth);

        this.data = handleNullData(data);
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.cellType = cellType;
        this.whiteSpace = " ";

        standardCellFormatter = new StandardCellFormatter();
        textFormatter = new TextFormatter<>(data,cellWidth);
        text = new Text(this);
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

    // Overriding toString() Method
    @Override
    public String toString(){
        return formatCell();
    }


    // Getters
    public T getData() {
        return data;
    }

    public Class<?> getDataType() {
        dataType = data.getClass();
        return dataType;
    }

    public Text text(){
        return this.text;
    }
    public TextFormatter<?> getTextFormatter(){
        return textFormatter;
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

    // Data String Formatter
    private String dataToString(T data){
        if(data == null){
            return "null";
        }
        return data.toString();
    }

    // Setters
    public void setData(T data) {
        this.data = handleNullData(data);
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public void setCellWidth(int cellWidth) {
        validateCellWidth(data,cellWidth);
        this.cellWidth = cellWidth;
    }
    public void setCellHeight(int cellHeight) {
        validateCellHeight(cellHeight);
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

    private String formatCell(){
        // Prevents unaligned cells to be formatted
        textFormatter.defaultAlignment();

        String graphicalCell = standardCellFormatter.formatCell(cellType,textFormatter.getFormattedData(),cellWidth,cellHeight,whiteSpace);
        // Remove the extra line-separator from the graphical cell for cell binding
        graphicalCell = TextUtils.chop(graphicalCell);

        return graphicalCell;
    }

    public T handleNullData(T data){
        if(data == null){
            return (T) "null";
        }
        return data;
    }

    public class Text{
        private final Cell<?> cell;
        public Text(Cell<?> cell){
            this.cell = cell;
        }
        public void setDefault(){
            cell.textFormatter.setStringData(cell.data.toString());
            cell.textFormatter.setWhiteSpace(" ");
            cell.textFormatter.setContainerWidth(cellWidth);
            cell.setCellHeight(1);
            cell.textFormatter.align(TextFormatter.Alignment.CENTER);
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
