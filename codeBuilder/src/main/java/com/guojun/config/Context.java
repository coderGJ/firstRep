package com.guojun.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.guojun.base.AppProperties;

/**
 * 
 * @author    GuoJun
 * @since     1.0
 */
public class Context {
    
    private volatile static Context context;

    // date formate
    private static final String DEFAULT_DATE_FORMATE = "yyyy/MM/dd";
    
    private JDBCConfiguration jdbcConfiguration;

    private BaseConfiguration baseConfiguration;
    
    private TableConfiguration tableConfiguration;

    private Context() {}
    
    public static Context getContext() {
        if (context == null) {
            synchronized (Context.class) {
                if (context == null) {
                    init();
                }
            }
        }
        return context;
    }

    public JDBCConfiguration getJdbcConfiguration() {
        return jdbcConfiguration;
    }

    public void setJdbcConfiguration(JDBCConfiguration jdbcConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
    }

    public BaseConfiguration getBaseConfiguration() {
        return baseConfiguration;
    }

    public void setBaseConfiguration(BaseConfiguration baseConfiguration) {
        this.baseConfiguration = baseConfiguration;
    }

    public TableConfiguration getTableConfiguration() {
        return tableConfiguration;
    }

    public void setTableConfiguration(TableConfiguration tableConfiguration) {
        this.tableConfiguration = tableConfiguration;
    }
    
    private static void init() {
        context = new Context();
        JDBCConfiguration jdbcConfiguration = new JDBCConfiguration();
        jdbcConfiguration.setDriverClass(AppProperties.getProperty("jdbc.driverClassName"));
        jdbcConfiguration.setConnectionURL(AppProperties.getProperty("jdbc.url"));
        jdbcConfiguration.setUser(AppProperties.getProperty("jdbc.user"));
        jdbcConfiguration.setPassword(AppProperties.getProperty("jdbc.password"));
        context.setJdbcConfiguration(jdbcConfiguration);
        
        BaseConfiguration baseConfiguration = new BaseConfiguration();
        baseConfiguration.setAuthor(AppProperties.getProperty("base.author", "unascribed"));
        baseConfiguration.setBasePackage(AppProperties.getProperty("base.basePackage",
            System.getProperty("user.dir")));
        baseConfiguration.setVersion(AppProperties.getProperty("base.version", "1.0"));
        baseConfiguration.setBeanPackage(AppProperties.getProperty("base.beanPackage", "model"));
        baseConfiguration.setServicePackage(AppProperties.getProperty("base.servicePackage", "service"));
        baseConfiguration.setControllerPackage(AppProperties.getProperty("base.controllerPackage", "controller"));
        baseConfiguration.setMapperPackage(AppProperties.getProperty("base.mapperPackage", "mapper"));
        SimpleDateFormat sdf = new SimpleDateFormat(AppProperties.getProperty("base.dateFormat",
            DEFAULT_DATE_FORMATE));
        baseConfiguration.setDate(sdf.format(new Date()));
        context.setBaseConfiguration(baseConfiguration);
        
        TableConfiguration tableConfiguration = new TableConfiguration();
        tableConfiguration.setExclude(AppProperties.getProperty("table.exclude"));
        tableConfiguration.setInclude(AppProperties.getProperty("table.include"));
        context.setTableConfiguration(tableConfiguration);
    }
    

}
