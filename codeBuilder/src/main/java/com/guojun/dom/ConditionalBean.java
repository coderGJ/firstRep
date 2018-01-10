package com.guojun.dom;

import java.util.List;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class ConditionalBean {

    private List<String> importList;

    private Table table;

    private List<Column> columnList;

    public List<String> getImportList() {
        return importList;
    }

    public void setImportList(List<String> importList) {
        this.importList = importList;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }
}
