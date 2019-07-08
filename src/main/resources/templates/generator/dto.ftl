package ${classInfo.dtoPackage()};

<#if classInfo.columnList?exists && classInfo.columnList?size gt 0>
    <#list classInfo.columnList as fieldItem >
        <#if fieldItem.javaType == "java.util.Date">
            <#assign importDate = true />
        </#if>
    </#list>
</#if>
<#if classInfo.columnList?exists && classInfo.columnList?size gt 0>
    <#list classInfo.columnList as fieldItem >
        <#if fieldItem.javaType == "java.math.BigDecimal">
            <#assign importBigDecimal = true />
        </#if>
    </#list>
</#if>

<#if importBigDecimal?? && importBigDecimal>
import java.math.BigDecimal;
</#if>

<#if importDate?? && importDate>
import java.util.Date;
</#if>
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import java.io.Serializable;
/**
*  ${classInfo.desc}
*
*  Created by liupeng6251@163.com on '${.now?string('yyyy-MM-dd HH:mm:ss')}'.
*/
public class ${classInfo.name}Dto implements Serializable{

<#if classInfo.columnList?exists && classInfo.columnList?size gt 0>
<#list classInfo.columnList as fieldItem >
    /**
    * ${fieldItem.desc}
    */
    private ${fieldItem.typeClassName} ${fieldItem.name};

</#list>
</#if>

<#if classInfo.columnList?exists && classInfo.columnList?size gt 0>
<#list classInfo.columnList as fieldItem>
    public ${fieldItem.typeClassName} get${fieldItem.name?cap_first}() {
        return ${fieldItem.name};
    }

    public void set${fieldItem.name?cap_first}(${fieldItem.typeClassName} ${fieldItem.name}) {
        this.${fieldItem.name} = ${fieldItem.name};
    }

</#list>
</#if>
}