<#-----author : GuoJun------->
<#-----date : 2018-01-10----->
package ${baseConfig.basePackage}.${baseConfig.beanPackage};

<#if javaBean.importList?? && (javaBean.importList?size > 0) >
<#list javaBean.importList as import>
import ${import};
</#list>
</#if>

/**
 *
 * @author  ${baseConfig.author}
 * @since   ${baseConfig.version}
 * @date    ${baseConfig.date}
 */
public class ${javaBean.className} {

    <#if javaBean.columnList?? && javaBean.columnList?size > 0>
    <#list javaBean.columnList as column>
    /**
     * This remarks: ${column.remarks}
     * This field corresponds to the database column ${(javaBean.table.tableName)!}.${column.columnName}
     */
    private ${column.columnType}
    </#list>
    </#if>
}