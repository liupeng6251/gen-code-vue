package org.qvit.lp.admin.model;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by peng.liu11 on 2019/5/22.
 */
public class ClassInfo {

    private String packagePath;
    private String name;

    private String businessModule;

    private String businessModuleDesc;
    private String tableName;
    private String desc;
    private boolean disableEdit;
    private ClassFieldInfo primaryKey;
    private List<ClassFieldInfo> columnList;


    public boolean isDisableEdit() {
        return disableEdit;
    }

    public void setDisableEdit(boolean disableEdit) {
        this.disableEdit = disableEdit;
    }

    public String getBusinessModuleDesc() {
        return businessModuleDesc;
    }

    public void setBusinessModuleDesc(String businessModuleDesc) {
        this.businessModuleDesc = businessModuleDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getBusinessModule() {
        return businessModule;
    }

    public void setBusinessModule(String businessModule) {
        this.businessModule = businessModule;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ClassFieldInfo getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ClassFieldInfo primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<ClassFieldInfo> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ClassFieldInfo> columnList) {
        this.columnList = columnList;
    }

    public String controllerPackage() {
        return packagePath + (StringUtils.isEmpty(businessModule) ? "" : "." + businessModule) + ".controller";
    }
    public String servicePackage() {
        return packagePath + (StringUtils.isEmpty(businessModule) ? "" : "." + businessModule) + ".service";
    }
    public String serviceImplPackage() {
        return packagePath + (StringUtils.isEmpty(businessModule) ? "" : "." + businessModule) + ".service.impl";
    }
    public String mapperPackage() {
        return packagePath + (StringUtils.isEmpty(businessModule) ? "" : "." + businessModule) + ".mapper";
    }
    public String entityPackage() {
        return packagePath + (StringUtils.isEmpty(businessModule) ? "" : "." + businessModule) + ".entity";
    }
    public String dtoPackage() {
        return packagePath + (StringUtils.isEmpty(businessModule) ? "" : "." + businessModule) + ".dto";
    }
    public String paramPackage() {
        return packagePath + (StringUtils.isEmpty(businessModule) ? "" : "." + businessModule) + ".param";
    }
    public String adapterPackage() {
        return packagePath + (StringUtils.isEmpty(businessModule) ? "" : "." + businessModule) + ".adapter";
    }

}
