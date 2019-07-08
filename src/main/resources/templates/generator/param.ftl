package ${classInfo.paramPackage()};

<#if classInfo.columnList?exists && classInfo.columnList?size gt 0>
    <#list classInfo.columnList as fieldItem >
        <#if fieldItem.javaType == "java.util.Date">
            <#assign importDdate = true />
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

<#if importDdate?? && importDdate>
import java.util.Date;
</#if>
import ${classInfo.packagePath}.core.page.PageRequest;
/**
*  ${classInfo.desc}
*
*  Created by liupeng6251@163.com on '${.now?string('yyyy-MM-dd HH:mm:ss')}'.
*/
public class ${classInfo.name}Param extends  PageRequest{

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

    public ${classInfo.name}Param() {
    }

    private ${classInfo.name}Param(Builder builder) {
    <#if classInfo.columnList?exists && classInfo.columnList?size gt 0>
        <#list classInfo.columnList as fieldItem>

        set${fieldItem.name?cap_first}(builder.${fieldItem.name});
        </#list>
    </#if>

    }

    public static final class Builder {
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
        public Builder ${fieldItem.name}(${fieldItem.typeClassName} ${fieldItem.name}) {
            this.${fieldItem.name} = ${fieldItem.name};
            return this;
        }

        </#list>
    </#if>
        public ${classInfo.name}Param build() {
              return new ${classInfo.name}Param(this);
        }

    }
}