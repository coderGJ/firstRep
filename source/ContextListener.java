package com.qmxx.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author      GuoJun
 * @since       2017年7月27日
 * 
 */
public class ContextListener implements ServletContextListener{

    Logger logger = LoggerFactory.getLogger(ContextListener.class);
    
    private final static String BASE_PATH = "basePath";
    
    public void contextDestroyed(ServletContextEvent event) {
        
    }

    public void contextInitialized(ServletContextEvent event) {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            String hostAddress = address.getHostAddress();//192.168.1.121
            logger.debug(hostAddress);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ServletContext context = event.getServletContext();
        String path = context.getRealPath("");

        context.setAttribute(BASE_PATH, path);
        
    }

}
