package com.guojun.util;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.guojun.config.BaseConfiguration;
import com.guojun.config.Context;
import com.guojun.dom.ConditionalBean;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * freemarker模板引擎生成模板
 * 
 * @author      GuoJun
 * @since       1.0
 */
public class FreeMarkerUtil {

    private static Configuration configuration;
    private static Template template;
    private static Writer writer;

    // date formate
    public static final String DATE_FROMATE = "yyyy/MM/dd";

    // date formate
    public static final String TEMPLATE_BASE = "/templates";
    public static final String TEMPLATE_JAVABEAN = "javaBean.ftl";
    
    /**
     * 利用模板在控制台打印helloworld信息
     * 
     * @param path
     *            模板存放的路径
     * @param ftlFile
     *            模板文件
     * @throws Exception
     */
    public static void startTest() throws Exception {
        // 创建Freemarker配置实例
        configuration = getConfiguration();

        // 创建数据模型
        Map<String, String> root = new HashMap<String, String>();
        root.put("message", "Hello,World");

        // 加载模板文件
        template = configuration.getTemplate("test.ftl");

        // 显示生成的数据，这里打印在控制台
        writer = new OutputStreamWriter(System.out);
        template.process(root, writer);
        writer.flush();
        writer.close();
    }

    /**
     * 
     * @param path
     * @param packageUrl
     * @param ftlFile
     * @throws Exception
     */
    public static void generatorJavaBeanFile(String path, String templateFtl, ConditionalBean javaBean) throws Exception {

        Context context = Context.getContext();
        BaseConfiguration baseConfiguration = context.getBaseConfiguration();
        // 创建数据模型
        Map<String, Object> data = new HashMap<String, Object>();
        
        data.put("baseConfig", baseConfiguration);
        data.put("javaBean", javaBean);
        // 加载模板文件
        template = configuration.getTemplate(templateFtl);

        String beanPath = System.getProperty("user.dir");
        File filePath = new File(beanPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        String javaBeanFilePath = baseConfiguration.getBasePackage().concat(baseConfiguration.getBeanPackage());
        File file = new File(javaBeanFilePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        // 显示生成的数据
        writer = new OutputStreamWriter(System.out);
        template.process(data, writer);
        writer.flush();
        writer.close();
    }
    
    
    /**
     * 
     * @param path
     * @param packageUrl
     * @param ftlFile
     * @throws Exception
     */
    public static void generatorMapperXmlFile(String path, String packageUrl, String ftlFile) throws Exception {

        // 创建数据模型
        Map<String, Object> root = new HashMap<String, Object>();


        // 加载模板文件
        template = configuration.getTemplate(ftlFile);

        String beanPath = System.getProperty("user.dir") + "/src/" + packageUrl.replace(".", "/") + "/";
        File filePath = new File(beanPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        String filePathOfBean = beanPath + "/User.java";
        File file = new File(filePathOfBean);
        if (!file.exists()) {
            file.createNewFile();
        }

        // 显示生成的数据
        writer = new FileWriter(file);
        template.process(root, writer);
        writer.flush();
        writer.close();
    }
    
    /**
     * 
     * @param path
     * @param packageUrl
     * @param ftlFile
     * @throws Exception
     */
    public static void generatorFile(String path, String packageUrl, String ftlFile) throws Exception {
        // 创建Freemarker配置实例
        configuration = getConfiguration();
        configuration.setDirectoryForTemplateLoading(new File(path));

        // 创建数据模型
        Map<String, Object> root = new HashMap<String, Object>();

        // 加载模板文件
        template = configuration.getTemplate(ftlFile);

        String beanPath = System.getProperty("user.dir") + "/src/" + packageUrl.replace(".", "/") + "/";
        File filePath = new File(beanPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        String filePathOfBean = beanPath + "/User.java";
        File file = new File(filePathOfBean);
        if (!file.exists()) {
            file.createNewFile();
        }

        // 显示生成的数据
        writer = new FileWriter(file);
        template.process(root, writer);
        writer.flush();
        writer.close();
    }
    
    /** 
     * 获取freemarker的配置 freemarker本身支持classpath
     * @return 返回Configuration对象 
     */  
    private static Configuration getConfiguration() {
        if (null == configuration) {  
            configuration = new Configuration();
            configuration.setClassForTemplateLoading(FreeMarkerUtil.class, TEMPLATE_BASE);
            // 设置国家及其编码
            configuration.setEncoding(Locale.getDefault(), "UTF-8");
              
            // 设置对象的包装器  
            configuration.setObjectWrapper(new DefaultObjectWrapper());
            // 设置异常处理器//这样的话就可以${a.b.c.d}即使没有属性也不会出错 
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        }
        return configuration;
    }
    
    private static String getFilePath(BaseConfiguration baseConfig) {
        StringBuilder sb = new StringBuilder(baseConfig.getBasePackage());
        if (!sb.toString().endsWith(".")) {
            sb.append(".");
        }
        sb.append(baseConfig.getBeanPackage());
        return "";
    }
}