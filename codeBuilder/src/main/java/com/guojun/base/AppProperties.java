package com.guojun.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class AppProperties {

    private static Properties properties = null;

    private static Logger logger = LoggerFactory.getLogger(AppProperties.class);

    private AppProperties() {
        super();
    }

    static {
        loadProperties();
    }

    /**
     * @param name The property name
     * @return specified property value
     */
    public static String getProperty(String name) {
        return properties.getProperty(name);
    }
    
    /**
     * @param name The property name
     * @param defultVal The property value is null return defultVal
     * @return specified property value
     */
    public static String getProperty(String name, String defultVal) {
        return getNotNullValue(properties.getProperty(name), defultVal);
    }

    /**
     * @param value
     * @param defultVal
     * @return not null string
     */
    private static String getNotNullValue(String value, String defultVal) {
        if (value == null) {
            if (defultVal != null) {
                return defultVal;
            }
            return "";
        }
        return value;
    }
    /**
     * Load properties.
     */
    private static void loadProperties() {

        InputStream is = null;
        try {
            File propsFile = new File("/config.properties");
            is = new FileInputStream(propsFile);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        if (is == null) {
            try {
                is = AppProperties.class.getResourceAsStream
                    ("/config.properties");
            } catch (Throwable t) {
                handleThrowable(t);
            }
        }
        if (is != null) {
            try {
                properties = new Properties();
                properties.load(is);
            } catch (Throwable t) {
                handleThrowable(t);
                logger.debug("出错啦");
            } finally {
                try {
                    is.close();
                } catch (IOException ioe) {
                    logger.debug("Could not close config.properties");
                }
            }
        } else {
            // Do something
            logger.error("Failed to load config.properties");
            // That's fine - we have reasonable defaults.
            properties = new Properties();
        }

        // Register the properties as system properties
        /*Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            String value = properties.getProperty(name);
            if (value != null) {
                System.setProperty(name, value);
            }
        }*/
    }
    
    // Copied from ExceptionUtils since that class is not visible during start
    private static void handleThrowable(Throwable t) {
        if (t instanceof ThreadDeath) {
            throw (ThreadDeath) t;
        }
        if (t instanceof VirtualMachineError) {
            throw (VirtualMachineError) t;
        }
        // All other instances of Throwable will be silently swallowed
    }
}
