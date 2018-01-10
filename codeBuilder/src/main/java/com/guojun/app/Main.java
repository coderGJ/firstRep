package com.guojun.app;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guojun.db.ConnectionFactory;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = ConnectionFactory.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        if (connection != null) {
            DatabaseMetaData dbmd = null;
            try {
                dbmd = connection.getMetaData();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Connection getMetaData method throws error.");
            }
            List<String> tableList = new ArrayList<String>();
            String[] typeList = new String[] { "TABLE" };
            try {
                ResultSet rs = dbmd.getTables(connection.getCatalog(), "%", "%", typeList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String name = "";
            String type = "";
            String tableRemarks = "";
            
            String columnName = "";
            String columnType = "";
            String remarks = "";
            ResultSet colRet = null;
        }
    }
    
    private List getTables(DatabaseMetaData dbmd) {
        
        return null;
    }
}
