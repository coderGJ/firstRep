package com.guojun.dom;

import java.util.List;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class Table {

    //TABLE_NAME
    private String tableName;

    //TABLE_TYPE
    private String tableType;

    //REMARKS
    private String remarks;
    
    //columnList
    private List<Column> columnList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    @Override
    public String toString() {
        return "Table [tableName=" + tableName + ", tableType=" + tableType + ", remarks=" + remarks + ", columnList="
                + columnList + "]";
    }
    
}
