package org.example.table.model;

import org.example.table.format.TableFormatter;
import org.example.table.manipulation.TableEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    private StringBuilder formattedTable;
    private List<String> columnHeaders;
    private List<Class<?>> columnTypes;
    private List<List<Cell<?>>> data;

    TableFormatter formatter = new TableFormatter();
    TableEditor editor;


    // Constructors
    public Table(){
        formattedTable = new StringBuilder();
        columnTypes = new ArrayList<>();
        data = new ArrayList<>();
        editor = new TableEditor(this);
    }
    // Manual Padding using varargs for columnNames
    public Table(int padding,String... columnNames){
        this();
        columnHeaders.addAll(Arrays.asList(columnNames));
        editor.setHeaders_ManualPadding(columnHeaders,padding);

    }
    // Automatic Padding using varargs for columnNames
    public Table(String... columnNames){
        this(2,columnNames);

    }

    @Override
    public String toString(){
        formattedTable.setLength(0);
        formatTable();
        return formattedTable.toString();
    }

    // Manual Padding using List for columnNames
    public Table(int padding,List<String> columnNames){
        // String[]::new => size -> new String[size]
        this(padding, columnNames.toArray(String[]::new));
    }

    // Automatic Padding using List for columnNames
    public Table(List<String> columnNames){
        this(2, columnNames);
    }

    // Overloading methods for setting headers
    public void setColumnHeaders(int padding,String... columnNames) {
        columnHeaders.clear();
        columnHeaders.addAll(Arrays.asList(columnNames));
        editor.setHeaders_ManualPadding(columnHeaders,padding);
    }
    public void setColumnHeaders(String... columnNames) {
        columnHeaders.clear();
        columnHeaders.addAll(Arrays.asList(columnNames));
        editor.setHeaders_AutomaticPadding(columnHeaders);
    }
    public void setColumnHeaders(int padding,List<String> columnNames){
        columnHeaders.clear();
        columnHeaders.addAll(columnNames);
        editor.setHeaders_ManualPadding(columnHeaders,padding);
    }
    public void setColumnHeaders(List<String> columnNames){
        columnHeaders.clear();
        columnHeaders = columnNames;
        editor.setHeaders_AutomaticPadding(columnHeaders);
    }

    // Add new row
    public void addRow(List<String> columnNames){
        editor.addRow(columnNames);
    }
    public void addRow(String... columnNames){
        editor.addRow(Arrays.asList(columnNames));
    }

    // Add new column
    // Using List<?>
    public void addColumn(String header, int headerPadding, List<?> columnData){
        editor.addColumn(header,headerPadding,columnData);
    }
    public void addColumn(String header, List<?> columnData){
        editor.addColumn(header,header.length()+2,columnData);
    }
    // Using varargs
    public void addColumn(String header, int headerPadding, String... columnData){
        editor.addColumn(header,headerPadding,Arrays.asList(columnData));
    }
    public void addColumn(String header, String... columnData){
        editor.addColumn(header,header.length()+2,Arrays.asList(columnData));
    }

    // Getters
    public TableFormatter getFormatter() {
        return formatter;
    }

    public StringBuilder getFormattedTable() {
        return formattedTable;
    }

    public List<String> getColumnHeaders() {
        return columnHeaders;
    }

    public List<Class<?>> getColumnTypes() {
        return columnTypes;
    }

    public List<List<Cell<?>>> getData() {
        return data;
    }
    public int getTableWidth(){
        if(!data.isEmpty()) {
            return data.get(0).size();
        }
        return 0;
    }
    public int getTableHeight(){
        if(!data.isEmpty()) {
            return data.size();
        }
        return 0;
    }
    public Cell<?> getCellAt(int rowIndex, int columnIndex){
        return data.get(rowIndex).get(columnIndex);
    }
    // Display methods
    public void displayTableSize(){
        System.out.println("Table size : [ " + getTableWidth() + " X " + getTableHeight() + " ] (W x H)" );
    }

    // Table graphical format
    public void formatTable(){
        for(var rowData : data){
            formattedTable.append(formatter.formatTableRow(rowData));
        }
    }
}
