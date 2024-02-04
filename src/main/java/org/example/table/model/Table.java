package org.example.table.model;

import org.example.table.format.TableFormatter;
import org.example.table.manipulation.TableEditor;
import org.example.text.styles.TableStyles;

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
    }

    /*
        Table Constructors
        -> defaultConstructor
        -> with parameters for headers initialization
            -> padding with varargs
            -> varargs
            -> padding with list
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
    public Table(String... columnName){
        this(DEFAULT_PADDING,columnName);
    }
    public Table(int padding, List<String> columnName){
        this();
        setHeaders(padding,columnName);
    }
    public Table(List<String> columnName){
        this(DEFAULT_PADDING,columnName);
    }
    public Table(int padding, Object[][] dataMatrix){
        this();

    }
    public Table(Object[][] dataMatrix){
        this(DEFAULT_PADDING,dataMatrix);
    }

    /*
        Setters
     */
    public void setHeader(int columnIndex, int padding, String newHeader){
        editor.setHeaderAt(columnIndex,padding,newHeader);
        editor.applyCorrectColumnWidth();
    }
    public void setHeaders(String... columnName){
        setHeaders(DEFAULT_PADDING, Arrays.asList(columnName));
    }
    public void setHeaders(int padding, String... columnName){
        setHeaders(padding, Arrays.asList(columnName));
    }
    public void setHeaders(List<String>columnNames){
        setHeaders(DEFAULT_PADDING,columnNames);
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
    /*
        Table Formatting
     */
    public void setFormattedHeaders(){
        if(headers.isEmpty()){
            return;
        }
        formattedHeaders.setLength(0);
        formattedHeaders.append(
                formatter.formatTableRow(headers)
        );
    }
    public void setFormattedTable(){
        if(tableData.isEmpty()){
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

    /*
        Table Manipulation
        -> Row Manipulation
     */
    public void addRow(List<?> rowData){
        editor.addRow(rowData,bottomRowType);
        editor.updateAndFixTable();
    }
    public void addRow(Objects... rowData){
        addRow(Arrays.asList(rowData));
    }
    public void removeRow(int rowIndex){
        editor.deleteRow(rowIndex);
        editor.updateAndFixTable();
    }
    public void removeLastRow(){
        removeRow(getTableHeight()-1);
    }
    public void removeFirstRow(){
        removeRow(0);
    }
    /*
        -> Column Manipulation
     */
    public void addColumn(String header, int headerPadding, List<?> columnData){
        editor.addColumn(header,headerPadding,columnData);
    }
    public void addColumn(String header, List<?> columnData){
        addColumn(header,DEFAULT_PADDING,columnData);
    }
    public void addColumn(String header, int headerPadding, Object... columnData){
        addColumn(header,headerPadding,Arrays.asList(columnData));
    }public void addColumn(String header, Object... columnData){
        addColumn(header,DEFAULT_PADDING,columnData);
    }

    /*
        To String Method
     */
    @Override
    public String toString(){
        setFormattedHeaders();
        setFormattedTable();
        if (formattedTable.isEmpty() && formattedHeaders.isEmpty()){
            return "Table is empty";
        }
        // Joins the table headers with table content together for output
        return String.valueOf(formattedHeaders) + formattedTable;
    }

}
