package com.qmxx.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * 用户在线人数统计
 * @author     GuoJun
 * @since      2017-04-08
 * 
 */
public class OnlineUserCounterListener implements HttpSessionListener {

    /**
     * 记录在线用户数
     */
    private static int userCounter = 0;
    
    public int getUserCounter() {
        return userCounter;
    }

    public void sessionCreated(HttpSessionEvent arg0) {
        userCounter ++;
    }


    public void sessionDestroyed(HttpSessionEvent arg0) {
        userCounter --;
    }

}