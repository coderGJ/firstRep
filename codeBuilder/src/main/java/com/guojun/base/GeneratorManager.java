package com.guojun.base;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guojun.config.Context;
import com.guojun.config.TableConfiguration;
import com.guojun.db.ConnectionFactory;
import com.guojun.dom.Column;
import com.guojun.dom.ConditionalBean;
import com.guojun.dom.Table;
import com.guojun.util.JavaBeansUtil;
import com.sun.org.apache.regexp.internal.recompile;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class GeneratorManager {

    private static Logger logger = LoggerFactory.getLogger(GeneratorManager.class);
    
    public static final String COMMENT = "COMMENT='";
    
    private static Pattern PATTERN_INCLUDE = null;
    
    private static Pattern PATTERN_EXCLUDE = null;
    
    static {
        Context context = Context.getContext();
        TableConfiguration tableConfig = context.getTableConfiguration();
        String include = tableConfig.getInclude();
        String exclude = tableConfig.getExclude();
        PATTERN_INCLUDE = Pattern.compile(include.replace(",", "|"));
        PATTERN_EXCLUDE = Pattern.compile(exclude.replace(",", "|"));
    }
    
    public List<ConditionalBean> getConditionalBeanList() throws Exception {
        List<ConditionalBean> beanList = new ArrayList<ConditionalBean>();
        
        ConnectionFactory cf = ConnectionFactory.getInstance();
        Connection connection = cf.getConnection();
        DatabaseMetaData dbMetaData = connection.getMetaData();
        databaseInfo(dbMetaData);
        
        String[] typeList = new String[] { "TABLE" };
        //库名(database)
        String catalog = connection.getCatalog();
        //查询所有表
        ResultSet rs = dbMetaData.getTables(catalog, "%", "%", typeList);
        String name = "";
        String type = "";
        String tableRemarks = "";
        
        String columnName = "";
        String columnType = "";
        String remarks = "";
        ResultSet colRet = null;
        
        while (rs.next()) {
            /*ResultSetMetaData metaData = rs.getMetaData();
            int columns = metaData.getColumnCount();
            for(int i = 1; i <= columns; i++) {
              System.out.print("name:" + metaData.getColumnName(i) + "|" + rs.getString(i) + "\t");
              
            }
            System.out.println();*/
            
            name = rs.getString("TABLE_NAME");
            type = rs.getString("TABLE_TYPE");
            tableRemarks = rs.getString("REMARKS");
            
            if (type.equalsIgnoreCase("TABLE") && name.indexOf("$") == -1) {
                if (filterInclude(name) && !filterExclude(name)) {
                    ConditionalBean bean = new ConditionalBean();
                    Table table = new Table();
                    table.setTableName(name);
                    table.setTableType(type);
                    if (StringUtils.isEmpty(tableRemarks)) {
                        tableRemarks = getTableComment(name);
                    }
                    table.setRemarks(tableRemarks);
                    logger.debug(table.toString());
                    
                    List<Column> columns = new ArrayList<Column>();
                    colRet = dbMetaData.getColumns(connection.getCatalog(), "%", name, "%");
                    while(colRet.next()) {
                        columnName = colRet.getString("COLUMN_NAME");
                        columnType = colRet.getString("TYPE_NAME");
                        int datasize = colRet.getInt("COLUMN_SIZE");
                        int digits = colRet.getInt("DECIMAL_DIGITS");
                        int nullable = colRet.getInt("NULLABLE");
                        remarks = colRet.getString("REMARKS");
                        logger.debug("columnName:" + columnName + "\tcolumnType:" + columnType +" "+datasize+" "+digits+" "+ nullable + "\tremarks:" + remarks); 
                    }
                    bean.setTable(table);
                    bean.setColumnList(columns);
                    beanList.add(bean);
                }
                
            }
        }
        rs.close();
        return beanList;
    }
    
    private String getTableComment(String tableName) throws SQLException {
        ConnectionFactory cf = ConnectionFactory.getInstance();
        Connection connection = cf.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
        String comment = "";
        if (rs != null && rs.next()) {
            String create = rs.getString("CREATE_TABLE");
            int index = create.indexOf(COMMENT);
            if (index > 0) {
                comment = create.substring(index + COMMENT.length());
                comment = comment.substring(0, comment.length() - 1);
                try {
                    comment = new String(comment.getBytes("utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return comment;
    }
    
    private List<Table> filterTable(List<Table> allTable) {
        if (allTable != null && !allTable.isEmpty()) {
            Context context = Context.getContext();
            TableConfiguration tableConfig = context.getTableConfiguration();
            String include = tableConfig.getInclude();
            String exclude = tableConfig.getExclude();
            Pattern p = null;
            boolean eligible = true;
            if (StringUtils.isNotEmpty(include)) {
                p = Pattern.compile(include);
            } else if (StringUtils.isNotEmpty(exclude)) {
                p = Pattern.compile(include);
                eligible = false;
            }
            if (p != null) {
                for (Table table : allTable) {
                    if (eligible) {
                        if (!p.matcher(table.getTableName()).matches()) {
                            allTable.remove(table);
                        }
                    } else {
                        if (p.matcher(table.getTableName()).matches()) {
                            allTable.remove(table);
                        }
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * 获得包含的表
     * @param include
     * @param allTable
     * @return
     */
    private boolean filterInclude(String tableName) {
        return PATTERN_INCLUDE.matcher(tableName).matches();
    }
    
    /**
     * 去除不包含的表
     * @param exclude
     * @param allTable
     * @return
     */
    private boolean filterExclude(String tableName) {
        return PATTERN_EXCLUDE.matcher(tableName).matches();
    }
    
    private List<Column> getColumnList(ResultSet columnResult) throws SQLException {
        List<Column> columnList = new ArrayList<Column>();
        String columnName = "";
        String columnType = "";
        String remarks = "";
        while(columnResult.next()) {
            Column column = new Column();
            columnName = columnResult.getString("COLUMN_NAME");
            columnType = columnResult.getString("TYPE_NAME");
            int datasize = columnResult.getInt("COLUMN_SIZE");
            int digits = columnResult.getInt("DECIMAL_DIGITS");
            int nullable = columnResult.getInt("NULLABLE");
            remarks = columnResult.getString("REMARKS");
            column.setColumnName(JavaBeansUtil.getCamelCaseString(columnName, false));
            column.setColumnType(columnType);
            logger.debug("columnName:" + columnName + "\tcolumnType:" + columnType +" "+datasize+" "+digits+" "+ nullable + "\tremarks:" + remarks); 
        }
        return columnList;
    }
    
    private void databaseInfo(DatabaseMetaData metaData) {
        try {
            logger.debug("数据库产品名：" + metaData.getDatabaseProductName());
            logger.debug("数据库版本号：" + metaData.getDatabaseProductVersion());
            logger.debug("数据库驱动名：" + metaData.getDriverName());
            logger.debug("数据库驱动版本号：" + metaData.getDriverVersion());
            logger.debug("当前连接的URL:" + metaData.getURL());
            logger.debug("当前连接的用户名:" + metaData.getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get database information throws error!");
        }
    }
}
