# Modular Java Text-Table

## Description

This is one of my firsts Java projects. My coding skills and the project also are still developing, so the code will look poorly written. However, I've made an effort to create a package for making text tables with different designs.
It provides methods for adding, removing, and styling table elements such as rows and columns. Additionally, the class supports various table orientations and formatting styles to suit different use cases.


## Installation

.......

## Usage

The package offers a range of methods for creating and manipulating text tables. It requires headers in `string` format and supports generic data types including `null values, booleans, strings, and floats`.

### Creating a Table with headers using the Default Constructor

```java
Table table = new Table();
String[] headers = {"EmployeeID", "Name", "Age", "IsManager", "Salary"};
table.setHeaders(Table.DEFAULT_PADDING, headers);
```

### Headers included constructor
```java
Table table = new Table(Table.DEFAULT_PADDING, "EmployeeID", "Name", "Age", "IsManager", "Salary");
```

```
System.out.println(table);
╒════════════╤═══════╤═════╤═══════════╤═════════╕
│ EmployeeID │ Name  │ Age │ IsManager │ Salary  │
╞════════════╪═══════╪═════╪═══════════╪═════════╡
```

### Data included constructor
```java
String[] headers = {"EmployeeID", "FullName", "Age", "IsManager", "Salary"};
Object[][] dataRows = {
    {1863, "Denis", 31, true,  55000.50},
    {1952, "Alex",  22, false, 48000.75},
    {1132, "Alice", 46, true,  62000.25}
};
int headerPadding = 5;

Table table = new Table(headerPadding, headers, dataRows, Table.TableOrientation.ROWS_AS_ROWS);
System.out.println(table);
```
`TableOrientation.ROWS_AS_ROWS :`
```
╒═══════════════╤═════════════╤════════╤══════════════╤═══════════╕
│  EmployeeID   │  FullName   │  Age   │  IsManager   │  Salary   │
╞═══════════════╪═════════════╪════════╪══════════════╪═══════════╡
│ 1863          │ Denis       │ 31     │ true         │ 55000.5   │
├───────────────┼─────────────┼────────┼──────────────┼───────────┤
│ 1952          │ Alex        │ 22     │ false        │ 48000.75  │
├───────────────┼─────────────┼────────┼──────────────┼───────────┤
│ 1132          │ Alice       │ 46     │ true         │ 62000.25  │
└───────────────┴─────────────┴────────┴──────────────┴───────────┘
```

```java
Object[][] dataRows = {
    {1863, 1952, 1132},
    {"Denis", "Alex", "Alice"},
    {31,22,46},
    {null,null,null},
    {55000.50,55000.50,62000.25}
};
Table table = new Table(headerPadding, headers, dataRows, Table.TableOrientation.ROWS_AS_COLUMNS);
System.out.println(table);
```
`TableOrientation.ROWS_AS_COLUMNS : `

```
╒═══════════════╤═════════════╤════════╤══════════════╤═══════════╕
│  EmployeeID   │  FullName   │  Age   │  IsManager   │  Salary   │
╞═══════════════╪═════════════╪════════╪══════════════╪═══════════╡
│ 1863          │ Denis       │ 31     │ null         │ 55000.5   │
├───────────────┼─────────────┼────────┼──────────────┼───────────┤
│ 1952          │ Alex        │ 22     │ null         │ 55000.5   │
├───────────────┼─────────────┼────────┼──────────────┼───────────┤
│ 1132          │ Alice       │ 46     │ null         │ 62000.25  │
└───────────────┴─────────────┴────────┴──────────────┴───────────┘
```

### Table headers
Headers for the table can be established or replaced using the method `setHeaders(padding,headers)`. This function expects an integer value representing the desired padding, followed by a `list or an array of strings` containing the headers.

The padding parameter can also accept a default padding : `Table.DEFAULT_PADDING`, which automatically sets a padding size of 2 characters.

```java
String[] headers = {"EmployeeID", "Name", "Age", "IsManager", "Salary"};
List<String> headersList = Arrays.asList("EmployeeID", "Name", "Age", "IsManager", "Salary");

table.setHeaders(Table.DEFAULT_PADDING, headers);
table.setHeaders(10, headersList);
```

Individual headers can be replaced using `replaceHeader(int index, int padding, String newHeader)`
```java
table.replaceHeader(1, Table.DEFAULT_PADDING, "EmpName");
```
Headers can be displayed individually without the table content using `displayTableWithoutHeaders()`

The content of the headers can be retrieved trough `getHeadersStrings()`, which returns a string list.

### Row operations
#### Adding Rows

Rows can be added to the table using various methods:

```java
// Adds a row to the table using :

// a list of data items.
table.addRow(Arrays.asList(1863, "Denis", 31, true, 55000.50));
// string data items.
table.addRow("1952", "Alex", "22", "false", "48000.75");
// an array of data items.
table.addRow(new Object[]{1132, "Alice", 46, true, 62000.25});
```

#### Inserting Rows
```java
// Inserts a row at the specified index using :

// a list of data items.
table.insertRow(1, Arrays.asList(2000, "Bob", 25, false, 60000.00));
// string data items.
table.insertRow(2, "2050", "Eve", "30", "true", "70000.50");
//  an array of data items.
table.insertRow(3, new Object[]{2100, "Frank", 40, false, 75000.75});
```

#### Removing Rows
```java
table.removeRow(1);     // Removes the row at the specified index.
table.removeLastRow();
table.removeFirstRow();
```

### Column Operations

#### Adding Columns
```java
// Adds a column to the end of the table with the specified header and padding using :

// a list of data items.
table.addColumn("City", 5, Arrays.asList("London", "Paris", "Berlin"));
// string data items.
table.addColumn("Population", 5, "8,982,000", "2,161,000", "3,769,000");
// using an array of data items representing area in square kilometers.
table.addColumn("Area (sq km)", 3, new Object[]{"1,572", "105", "891"});
```

```
╒═════════╤═══════════════╤═══════════════╕
│  City   │  Population   │ Area (sq km)  │
╞═════════╪═══════════════╪═══════════════╡
│ London  │ 8,982,000     │ 1,572         │
├─────────┼───────────────┼───────────────┤
│ Paris   │ 2,161,000     │ 105           │
├─────────┼───────────────┼───────────────┤
│ Berlin  │ 3,769,000     │ 891           │
└─────────┴───────────────┴───────────────┘
```
### Inserting columns
```java
table.insertColumn( int columnIndex, (List<?>, String..., Object[]) columnData )
```
### Removing columns
```java
table.removeColumn(2);
table.removeLastColumn();
table.removeFirstColumn();
```


## Table Styling

In addition to creating and manipulating tables, it also offers various styling options to customize the appearance of the table. 

### Alignment

You can align individual columns or the entire table using the `alignColumn` and `alignTable` methods, respectively.

- `LEFT`
- `CENTER`
- `RIGHT`
- `DEFAULT`: Headers are aligned to the center and table data to the left

```java
table.alignTable(Table.ColumnAlign.LEFT);

table.alignColumn(2,Table.ColumnAlign.CENTER);
table.alignColumn(3,Table.ColumnAlign.RIGHT);
```
```
╒═══════════════╤═════════════╤════════╤══════════════╤═══════════╕
│ EmployeeID    │ FullName    │  Age   │    IsManager │ Salary    │
╞═══════════════╪═════════════╪════════╪══════════════╪═══════════╡
│ 1863          │ Denis       │   31   │         true │ 55000.5   │
├───────────────┼─────────────┼────────┼──────────────┼───────────┤
│ 1952          │ Alex        │   22   │        false │ 48000.75  │
├───────────────┼─────────────┼────────┼──────────────┼───────────┤
│ 1132          │ Alice       │   46   │         true │ 62000.25  │
└───────────────┴─────────────┴────────┴──────────────┴───────────┘
```

### Text Casing
You can set the casing style for table headers and individual columns using the `setHeadersStyle()` and `setColumnStyle` methods.

- `LOWER_CASE` 
- `UPPER_CASE`
- `CAPITALIZE`


```java
table.setHeadersStyle(Table.CasingStyle.UPPER_CASE);

table.setColumnStyle(1, Table.CasingStyle.LOWER_CASE);
table.setColumnStyle(3, Table.CasingStyle.CAPITALIZE);
```

```
╒═══════════════╤═════════════╤════════╤══════════════╤═══════════╕
│  EMPLOYEEID   │  FULLNAME   │  AGE   │  ISMANAGER   │  SALARY   │
╞═══════════════╪═════════════╪════════╪══════════════╪═══════════╡
│ 1863          │ denis       │ 31     │ True         │ 55000.5   │
├───────────────┼─────────────┼────────┼──────────────┼───────────┤
│ 1952          │ alex        │ 22     │ False        │ 48000.75  │
├───────────────┼─────────────┼────────┼──────────────┼───────────┤
│ 1132          │ alice       │ 46     │ True         │ 62000.25  │
└───────────────┴─────────────┴────────┴──────────────┴───────────┘
```

### Width and Height

```java
// Equalizes the widths of all columns for uniform appearance
table.equalizeColumnWidths();
```

```
╒═══════════════╤═══════════════╤═══════════════╤═══════════════╤═══════════════╕
│  EmployeeID   │   FullName    │      Age      │   IsManager   │    Salary     │
╞═══════════════╪═══════════════╪═══════════════╪═══════════════╪═══════════════╡
│ 1863          │ Denis         │ 31            │ true          │ 55000.5       │
├───────────────┼───────────────┼───────────────┼───────────────┼───────────────┤
│ 1952          │ Alex          │ 22            │ false         │ 48000.75      │
├───────────────┼───────────────┼───────────────┼───────────────┼───────────────┤
│ 1132          │ Alice         │ 46            │ true          │ 62000.25      │
└───────────────┴───────────────┴───────────────┴───────────────┴───────────────┘
```

```java
table.setHeadersWidth(10);
table.setHeadersHeight(2);
```

```
╒══════════╤══════════╤══════════╤══════════╤══════════╕
│          │          │          │          │          │
│EmployeeID│ FullName │   Age    │IsManager │  Salary  │
│          │          │          │          │          │
╞══════════╪══════════╪══════════╪══════════╪══════════╡
│ 1863     │ Denis    │ 31       │ true     │ 55000.5  │
├──────────┼──────────┼──────────┼──────────┼──────────┤
│ 1952     │ Alex     │ 22       │ false    │ 48000.75 │
├──────────┼──────────┼──────────┼──────────┼──────────┤
│ 1132     │ Alice    │ 46       │ true     │ 62000.25 │
└──────────┴──────────┴──────────┴──────────┴──────────┘
```
