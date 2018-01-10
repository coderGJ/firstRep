package com.guojun.dom;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class Column {

    //COLUMN_NAME
    private String columnName;

    //FIELD_NAME
    private String fieldName;

    //TYPE_NAME
    private String columnType;

    //COLUMN_SIZE
    private String columnSize;

    //DECIMAL_DIGITS
    private int digits;

    //NULLABLE
    private boolean nullable;

    //REMARKS
    private String remarks;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(String columnSize) {
        this.columnSize = columnSize;
    }

    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Column [columnName=" + columnName + ", fieldName=" + fieldName + ", columnType=" + columnType
                + ", columnSize=" + columnSize + ", digits=" + digits + ", nullable=" + nullable + ", remarks="
                + remarks + "]";
    }

}
