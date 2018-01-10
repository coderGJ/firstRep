package com.qmxx.listener;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户请求监听,request域属性变化监听
 * @author      GuoJun
 * @since       20170613
 * 
 */
public class RequestListener implements ServletRequestListener, ServletRequestAttributeListener {

    private static Logger logger = LoggerFactory.getLogger(RequestListener.class);
    /**
     * 当用户请求结束、被销毁时触发该方法
     */
    public void requestDestroyed(ServletRequestEvent sre) {
        // TODO Auto-generated method stub

    }

    /**
     * 当用户请求到达、被初始化时触发该方法
     */
    public void requestInitialized(ServletRequestEvent sre) {
        // TODO Auto-generated method stub
        HttpServletRequest request = (HttpServletRequest)sre.getServletRequest();
        getHeards(request);
        getParameters(request);
    }

    /**
     * 当程序向request范围添加属性时触发该方法
     */
    public void attributeAdded(ServletRequestAttributeEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    /**
     * 当程序从request范围删除属性时触发该方法
     */
    public void attributeRemoved(ServletRequestAttributeEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    /**
     * 当request范围的属性被替换时触发该方法
     */
    public void attributeReplaced(ServletRequestAttributeEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    /**
     * 获得请求信息并打印
     */
    private void getParameters(HttpServletRequest request) {
        logger.debug("request uri=" + request.getRequestURI());
        logger.debug("request ip=" + request.getRemoteAddr());
        logger.debug("request host=" + request.getRemoteHost());
        logger.debug("request port=" + request.getRemotePort());
        Map<String,String[]> map = request.getParameterMap();
        if(!map.isEmpty()) {
            logger.debug("-------------parameters---------------");
            for (String name : map.keySet()) {
                String[] values = map.get(name);
                for (String val : values) {
                    logger.debug(name + ":" + val);
                }
            }
            logger.debug("--------------------------------------");
        }
    }

    /**
     * 打印请求头信息
     */
    private void getHeards(HttpServletRequest request) {
        String basePath = request.getScheme() + "://" + request.getServerName() + 
            ":" + request.getServerPort() + request.getContextPath() + "/";//服务器路径
        Enumeration<String> e1 = request.getHeaderNames();
        while (e1.hasMoreElements()) {
            String headerName = (String) e1.nextElement();
            String headValue = request.getHeader(headerName);
            logger.debug(headerName + "=" + headValue);
        }
    }
}
