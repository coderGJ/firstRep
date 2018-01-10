package com.guojun.app;

import org.junit.Test;

import com.guojun.base.GeneratorManager;
import com.guojun.util.FreeMarkerUtil;
import com.guojun.util.JavaBeansUtil;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class UtilTest {

    @Test
    public void testgetCamelCaseString() {
        System.out.println(JavaBeansUtil.getCamelCaseString("BASE_cOnfig", true));
    }
    
    @Test
    public void testgetValidPropertyName() {
        System.out.println(JavaBeansUtil.getValidPropertyName("Baeconfig"));
    }
    
    @Test
    public void testManager() {
        GeneratorManager manager = new GeneratorManager();
        try {
            manager.getConditionalBeanList();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFeerMarkerUtil() {
        try {
            FreeMarkerUtil.startTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
