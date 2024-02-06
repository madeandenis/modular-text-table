package org.example.table.model;

import org.example.table.format.TableFormatter;
import org.example.table.manipulation.TableEditor;
import org.example.text.styles.TableStyles;
import org.example.text.utils.TextUtils;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Table {
    private StringBuilder       formattedTable;
    private StringBuilder       formattedHeaders;
    private List<Cell<?>>       headers;
    private List<List<Cell<?>>> tableData;
    private TableStyles         tableStyle;
    TableFormatter              formatter;
    TableEditor                 editor;

    ColumnAlign tableAlignment;
    Stack<Pair<ColumnAlign,Integer>> alignments;
    Stack<CasingStyle> headersStyles;
    Stack<Pair<CasingStyle,Integer>> columnStyles;

    // Default constants
    public static final TableStyles DEFAULT_STYLE = TableStyles.BoxDrawing;
    public static final Cell.CellType bottomRowType = Cell.CellType.BOTTOM_SINGLE;
    public static final int DEFAULT_PADDING = 2;


    public void initTableVars(){
        formattedTable   = new StringBuilder();
        formattedHeaders = new StringBuilder();
        headers          = new ArrayList<>();
        tableData        = new ArrayList<>();
        tableStyle       = DEFAULT_STYLE;
        formatter        = new TableFormatter();
        editor           = new TableEditor(this);
        tableAlignment   = ColumnAlign.DEFAULT;
        alignments       = new Stack<>();
        headersStyles    = new Stack<>();
        columnStyles     = new Stack<>();
    }

    /*
        Table Constructors
        -> defaultConstructor
        -> with parameters for headers initialization
            -> varargs
            -> list
            -> matrix
     */
    public Table(){
        initTableVars();
    }
    public Table(int padding, String... columnName){
        this();
        setHeaders(padding,columnName);
    }
    public Table(int padding, List<String> columnName){
        this();
        setHeaders(padding,columnName);
    }
    // Table constructor using a Matrix
    public enum TableOrientation{
        ROWS_AS_ROWS,
        ROWS_AS_COLUMNS
    }
    // Object[][] matrix
    public Table(int padding,List<String> headers, Object[][] dataMatrix, TableOrientation tableOrientation){
        this();
        if(headers.isEmpty() || (dataMatrix == null || dataMatrix.length == 0 || dataMatrix[0].length == 0)){
            System.out.println("Headers or Data Matrix cannot be empty");
            return;
        }
        if (!validateInputDataMatrix(headers,dataMatrix) && tableOrientation == TableOrientation.ROWS_AS_ROWS){
            System.out.println("Data Matrix width must be equal to the headers size");
            return;
        }
        // If matrix has missing elements then they would be treated as null cells
        if (tableOrientation == TableOrientation.ROWS_AS_ROWS) {
            setHeaders(padding,headers);
            for (Object[] matrix : dataMatrix) {
                addRow(matrix);
            }
        }
        if (tableOrientation == TableOrientation.ROWS_AS_COLUMNS){
            // We add columns for every header in the string list
            for (int i = 0; i < headers.size(); i++) {
                addColumn(headers.get(i),padding,dataMatrix[i]);
            }
        }
    }
    // Matrix needs to have same width as the headers
    private boolean validateInputDataMatrix(List<String> headers, Object[][] dataMatrix){
        return dataMatrix[0].length == headers.size();
    }
    // List<List<Object>> matrix
    public Table(int padding,List<String> headers, List<List<Object>> dataMatrix, TableOrientation tableOrientation) {
        this(padding,headers,convertToArrayMatrix(dataMatrix),tableOrientation);
    }
    private static Object[][] convertToArrayMatrix(List<List<Object>> list) {
        Object[][] matrix = new Object[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            List<Object> row = list.get(i);
            matrix[i] = row.toArray(new Object[0]);
        }
        return matrix;
    }

    /*
        Setters
     */
    public void replaceHeader(int columnIndex, int padding, String newHeader){
        editor.replaceHeaderAt(columnIndex,padding,newHeader);
        editor.applyCorrectColumnWidth();
    }
    public void setHeaders(int padding, List<String>columnNames){
        if(getHeaders().isEmpty()) {
            headers.clear();
            headers.addAll(editor.headersToCellList(padding, columnNames));
        }
        else{
            if (getHeaders().size() != columnNames.size()){
                System.out.println("The number of headers should be equal to: " + getHeaders().size());
                return;
            }
            headers.clear();
            headers.addAll(editor.headersToCellList(padding, columnNames));
            editor.applyCorrectColumnWidth();
        }
    }
    public void setHeaders(int padding, String... columnName){
        setHeaders(padding, Arrays.asList(columnName));
    }
    /*
        Table Formatting
     */
    private void setFormattedHeaders(){
        if(headers.isEmpty()){
            formattedHeaders.setLength(0);
            return;
        }
        formattedHeaders.setLength(0);
        formattedHeaders.append(
                formatter.formatTableRow(headers)
        );
    }
    private void setFormattedTable(){
        if(tableData.isEmpty()){
            formattedTable.setLength(0);
            return;
        }

        formattedTable.setLength(0);
        for(var tableRow : tableData){
            formattedTable.append(
                    formatter.formatTableRow(tableRow)
            );
        }

    }


    /*
        Getters
     */

    public List<List<Cell<?>>> getTableData() {
        return tableData;
    }

    public List<Cell<?>> getHeaders(){
        return headers;
    }
    public List<?> getHeadersStrings(){
        return getHeaders()
                .stream()
                .map(Cell::getData)
                .collect(Collectors.toList());
    }
    public List<Cell<?>> getRow(int rowIndex){
        return tableData.get(rowIndex);
    }
    public Cell<?> getCell(int rowIndex, int columnIndex){
        return getRow(rowIndex).get(columnIndex);
    }
    public TableFormatter getTableFormatter(){
        return formatter;
    }
    public int getTableWidth(){
        if(tableData.isEmpty()){
            return 0;
        }
        return tableData.get(0).size();
    }
    public int getTableHeight(){
        if(tableData.isEmpty()){
            return 0;
        }
        return tableData.size();
    }

    public StringBuilder getFormattedTable() {
        return formattedTable;
    }

    /*
        Display Methods
     */
    public void displayTableWithoutHeaders(){
        setFormattedTable();
        System.out.println(".".repeat(
                TextUtils.getRowFromString(getFormattedTable().toString(), 0).length()
        ));
        System.out.println(getFormattedTable());
    }
    public void displayTableData(){
        if (tableData.isEmpty()){
            System.out.println("Table is empty!");
            return;
        }
        for (var row : tableData){
            for (var column : row){
                System.out.print("[" + column.getData() + "], \t");
            }
            System.out.println();
        }
    }
    public void displayTableDimensions(){
        System.out.println("Table Dimensions: " + getTableWidth() + " x " + getTableHeight());
    }


    /*
        Table Manipulation
        -> Row Manipulation
     */
    // Rows addition/insertion for using different data types are made using wildcard list
    // Manual addition through String varargs results in only string data types
    public void addRow(List<?> rowData){
        editor.addRow(rowData,bottomRowType);
        editor.updateAndFixTable();
    }
    public void addRow(String... rowData){
        addRow(Arrays.asList(rowData));
    }
    public void addRow(Object[] rowData){
        addRow(Arrays.asList(rowData));
    }
    public void insertRow(int rowIndex, List<?> rowData){
        editor.insertRow(rowIndex, rowData, bottomRowType);
        editor.updateAndFixTable();
    }
    public void insertRow(int rowIndex, String... rowData){
        insertRow(rowIndex,Arrays.asList(rowData));
    }
    public void insertRow(int rowIndex, Object[] rowData){
        insertRow(rowIndex,Arrays.asList(rowData));
    }
    public void removeRow(int rowIndex){
        if (!tableData.isEmpty()) {
            editor.deleteRow(rowIndex);
            editor.updateAndFixTable();
        }
    }
    public void removeLastRow(){
        if (tableData.isEmpty()){
            return;
        }
        else {
            removeRow(getTableHeight() - 1);
        }
    }
    public void removeFirstRow(){
        if (tableData.isEmpty()){
            return;
        }
        removeRow(0);
    }
    /*
        -> Column Manipulation
     */
    public void addColumn(String header, int headerPadding, List<?> columnData){
        editor.addColumn(header,headerPadding,columnData);
    }
    public void addColumn(String header, int headerPadding, String... columnData){
        addColumn(header,headerPadding,Arrays.asList(columnData));
    }
    public void addColumn(String header, int headerPadding, Object[] columnData){
        addColumn(header,headerPadding,Arrays.asList(columnData));
    }

    //TODO public void insertColumn(String header,)
    public void removeColumn(int columnIndex){
        editor.deleteColumn(columnIndex);
    }
    public void removeLastColumn(){
        removeColumn(getTableWidth()-1);
    }
    public void removeFirstColumn(){
        removeColumn(0);
    }
    /*
        Table styling
        -> alignment
        -> text casing
        -> set column width/height
     */

    public enum ColumnAlign{
        LEFT,CENTER,RIGHT,DEFAULT;
    }
    public void alignColumn(int columnIndex, ColumnAlign alignment){
        alignments.push(new Pair<>(alignment,columnIndex));
    }
    public void alignTable(ColumnAlign alignment){
        editor.alignTable(alignment);
        this.tableAlignment = alignment;
        clearAlignments();
    }
    private void applyAlignments(){
        for (var alignment : alignments){
            editor.alignColumn(alignment.getValue1(),alignment.getValue0());
        }
    }
    public void clearAlignments(){
        while (!alignments.isEmpty()){
            alignments.pop();
        }
    }
    // Text Casing
    public enum CasingStyle{
        LOWER_CASE,UPPER_CASE,CAPITALIZE
    }
    public void setHeadersStyle(CasingStyle casing){
        headersStyles.push(casing);
    }
    public void setColumnStyle(int columnIndex, CasingStyle casing){
        columnStyles.push(new Pair<>(casing,columnIndex));
    }
    private void applyStyling(){
        for (var headerStyle : headersStyles){
            editor.setHeadersStyle(headerStyle);
        }
        for (var columnStyle : columnStyles){
            editor.setColumnStyle(columnStyle.getValue1(),columnStyle.getValue0());
        }

    }
    public void clearStyling(){
        while (!headersStyles.isEmpty()){
            headersStyles.pop();
        }
        while (!columnStyles.isEmpty()){
            columnStyles.pop();
        }
    }
    // Change column width
    public void setColumnWidth(int columnIndex, int width){
        editor.setColumnWidth(columnIndex,width);
    }
    // Change headers width/height
    public void setHeadersHeight(int height){
        editor.setHeadersHeight(height);
    }
    public void setHeadersWidth(int width){
        editor.setHeadersWidth(width);
    }
    public void equalizeColumnWidths(){
        editor.equalizeColumnWidths();
    }

    /*
        To String Method
     */
    @Override
    public String toString(){
        // Apply default alignment
        editor.alignTable(tableAlignment);
        applyAlignments();
        applyStyling();
        setFormattedHeaders();
        setFormattedTable();
        if (formattedTable.isEmpty() && formattedHeaders.isEmpty()){
            return "Table is empty";
        }
        // Joins the table headers with table content together for output
        // Checks for a two-column table, removes characters (chars for joining middle cells) from the first column to account for the absence of middle cells.
        if (getTableWidth() == 2){
            return editor.adjustTableOutputForTwoColumns(String.valueOf(formattedHeaders) + formattedTable);
        }
        return String.valueOf(formattedHeaders) + formattedTable;
    }

}
