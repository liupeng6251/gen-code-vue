package org.qvit.lp.admin.core;

import org.apache.commons.lang3.StringUtils;
import org.qvit.lp.admin.model.ClassFieldInfo;
import org.qvit.lp.admin.model.ClassInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.*;
import java.util.*;

/**
 * Created by peng.liu11 on 2019/5/25.
 */
@Service
public class MySqlDbHepler implements DbHelper {

    private static Logger logger = LoggerFactory.getLogger(MySqlDbHepler.class);
    private static Map<String, String> javaTypeConverMapp = new HashMap<>();
    private static Map<String, String> jdbcTypeConverMapp = new HashMap<>();

    static {
        javaTypeConverMapp.put(java.sql.Timestamp.class.getName(), java.util.Date.class.getName());
        javaTypeConverMapp.put(java.sql.Date.class.getName(), java.util.Date.class.getName());
        javaTypeConverMapp.put(BigInteger.class.getName(), Long.class.getName());
        jdbcTypeConverMapp.put("INT", "INTEGER");
        jdbcTypeConverMapp.put("DATETIME", "DATE");
        jdbcTypeConverMapp.put("MEDIUMTEXT", "BLOB");


    }

    @Override
    public List<ClassInfo> tableList(String url, String userName, String password) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        List<ClassInfo> result = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, userName, password);
            DatabaseMetaData metaData =  conn.getMetaData();
            rs = metaData.getTables(null, null, null, new String[]{"TABLE"});
            List<String> tables = new ArrayList<>();
            String dbName = getDbName(url);
            while (rs.next()) {
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                int columnLen = resultSetMetaData.getColumnCount();
                String tablsName = rs.getString("TABLE_NAME");
                String tableCat = rs.getString("TABLE_CAT");
                if (!tableCat.equals(dbName)) {
                    continue;
                }
                tables.add(tablsName);
            }
            rs.close();
            return genClassInfoByTables(url, userName, password, dbName, tables);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private String getDbName(String url) {
        String split = StringUtils.split(url, "?")[0];
        return split.substring(split.lastIndexOf("/") + 1);
    }

    private List<ClassInfo> genClassInfoByTables(String url, String userName, String password, String dbName, List<String> tables) throws Exception {
        List<ClassInfo> classInfos = new ArrayList<>(tables.size());
        Connection conn = DriverManager.getConnection(url, userName, password);
        tables.forEach(t -> {
                    ClassInfo classInfo = genClassInfoByTable(conn, dbName, t);
                    fillComment(conn, classInfo);
                    classInfos.add(classInfo);
                }
        );
        conn.close();
        return classInfos;
    }

    private ClassInfo genClassInfoByTable(Connection conn, String dbName, String table) {
        String sql = "select * from " + table + " limit 1";
        Statement statement = null;
        ResultSet resultSet = null;
        DatabaseMetaData databaseMetaData = null;
        ResultSet primaryKeysResultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        try {
            databaseMetaData = conn.getMetaData();
            primaryKeysResultSet = databaseMetaData.getPrimaryKeys(conn.getCatalog(), null , table);
            ResultSetMetaData metaData = primaryKeysResultSet.getMetaData();
            ClassInfo classInfo = new ClassInfo();
            ClassFieldInfo primarKey = new ClassFieldInfo();
            while (primaryKeysResultSet.next()) {
                String columnName = primaryKeysResultSet.getString("COLUMN_NAME");
                int len = primaryKeysResultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= len; i++) {
                    System.err.println(primaryKeysResultSet.getObject(i));
                }
                primarKey.setColumnName(columnName);
            }
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            resultSetMetaData = resultSet.getMetaData();
            int columtLen = resultSetMetaData.getColumnCount();
            List<ClassFieldInfo> fieldInfos = new ArrayList<>();
            Set<String> classFieldNames = new HashSet<>();
            for (int i = 1; i <= columtLen; i++) {
                String columnName = resultSetMetaData.getColumnName(i);
                ClassFieldInfo fieldInfo;
                if (columnName.equals(primarKey.getColumnName())) {
                    fieldInfo = primarKey;
                    fieldInfo.setPrimarKey(true);
                } else {
                    fieldInfo = new ClassFieldInfo();
                }
                columnName = columnName.toLowerCase();
                fieldInfo.setColumnName(columnName);
                String className = resultSetMetaData.getColumnClassName(i);
                if (javaTypeConverMapp.containsKey(className)) {
                    className = javaTypeConverMapp.get(className);
                }
                fieldInfo.setJavaType(className);
                String[] split = StringUtils.split(className, ".");
                fieldInfo.setTypeClassName(split[split.length - 1]);
                String type = resultSetMetaData.getColumnTypeName(i);
                if (jdbcTypeConverMapp.containsKey(type)) {
                    fieldInfo.setType(jdbcTypeConverMapp.get(type));
                } else {
                    type = StringUtils.split(type)[0];
                    if (jdbcTypeConverMapp.containsKey(type)) {
                        fieldInfo.setType(jdbcTypeConverMapp.get(type));
                    } else {
                        fieldInfo.setType(type);
                    }
                }
                String columnClassName = org.qvit.lp.admin.utils.StringUtils.underlineToCamelCase(columnName);
                if (JAVA_KEYWORD.contains(columnClassName)) {
                    columnClassName = columnClassName + KEYWORD_APPEND_CHAR;
                } else if (PROJECT_FIELD_KEYWORD.contains(columnName)) {
                    columnClassName = columnClassName + KEYWORD_APPEND_CHAR;
                }
                if (classFieldNames.contains(columnClassName)) {
                    fieldInfo.setName(columnName);
                } else {
                    fieldInfo.setName(columnClassName);
                }
                classFieldNames.add(fieldInfo.getName());
                fieldInfo.setDesc(columnName);
                fieldInfo.setLenth(resultSetMetaData.getColumnDisplaySize(i));
                fieldInfos.add(fieldInfo);
            }
            classInfo.setColumnList(fieldInfos);
            classInfo.setTableName(table);
            classInfo.setDesc(table);
            classInfo.setPrimaryKey(primarKey);
            classInfo.setBusinessModule(dbName);
            classInfo.setBusinessModuleDesc("首页");
            String className = org.qvit.lp.admin.utils.StringUtils.upperCaseFirst(org.qvit.lp.admin.utils.StringUtils.underlineToCamelCase(table));
            classInfo.setName(className);
            return classInfo;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }

                if (primaryKeysResultSet != null) {
                    primaryKeysResultSet.close();
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void fillComment(Connection conn, ClassInfo classInfo) {
        ResultSet showTableResultSet = null;
        Statement showTableStatement = null;
        try {
            showTableStatement = conn.createStatement();
            showTableResultSet = showTableStatement.executeQuery("show create table " + classInfo.getTableName());
            showTableResultSet.next();
            String createTableSql = showTableResultSet.getString(2);
            fillTableComment(classInfo, createTableSql);
            fillFieleComment(classInfo, createTableSql);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                if (showTableResultSet != null) {
                    showTableResultSet.close();
                }
                if (showTableStatement != null) {
                    showTableStatement.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void fillTableComment(ClassInfo classInfo, String tableSql) {
        String classCommentTmp = tableSql.substring(tableSql.lastIndexOf("COMMENT=") + 8).trim();
        classCommentTmp = replace(classCommentTmp);
        classCommentTmp = StringUtils.trim(classCommentTmp);
        classInfo.setDesc(classCommentTmp);
    }

    private String replace(String classCommentTmp) {
        classCommentTmp = StringUtils.split(classCommentTmp, " ")[0];
        classCommentTmp = StringUtils.replace(classCommentTmp, "'", "");
        classCommentTmp = StringUtils.replace(classCommentTmp, ";", "");
        classCommentTmp = StringUtils.replace(classCommentTmp, ",", "");
        classCommentTmp = StringUtils.replace(classCommentTmp, "`", "");
        classCommentTmp = StringUtils.replace(classCommentTmp, "\n", "");
        classCommentTmp = StringUtils.replace(classCommentTmp, "\t", "");
        classCommentTmp = StringUtils.trim(classCommentTmp);
        return classCommentTmp;
    }

    private void fillFieleComment(ClassInfo classInfo, String tableSql) {
        String fieldSql = tableSql.substring(tableSql.indexOf("(") + 1, tableSql.lastIndexOf(")"));
        String[] fieldDescs = StringUtils.split(fieldSql, "\n");
        Map<String, ClassFieldInfo> commentMapp = new HashMap<>();
        for (ClassFieldInfo fieldInfo : classInfo.getColumnList()) {
            commentMapp.put(fieldInfo.getColumnName().toUpperCase(), fieldInfo);
        }
        for (String fieldDesc : fieldDescs) {
            String trim = StringUtils.trim(fieldDesc);
            String fieldName = StringUtils.split(trim, " ")[0].toUpperCase();
            fieldName = replace(fieldName);
            String upper = fieldDesc.toUpperCase();
            if (upper.contains("AUTO_INCREMENT")) {
                if (Arrays.asList(StringUtils.split(upper, " ")).contains("AUTO_INCREMENT")) {
                    commentMapp.get(fieldName).setAutoIncrement(true);
                }
            }
            if (!fieldDesc.contains("COMMENT")) {
                continue;
            }
            String[] splits = StringUtils.split(trim, "COMMENT");
            String comment = splits[splits.length - 1];
            comment = replace(comment);
            if (commentMapp.containsKey(fieldName)) {
                commentMapp.get(fieldName).setDesc(comment);
            } else {
                logger.info("table:{},fileName:{}", classInfo.getTableName(), fieldDesc);
            }
        }
        String classCommentTmp = tableSql.substring(tableSql.lastIndexOf("COMMENT=") + 8).trim();
        classCommentTmp = replace(classCommentTmp);
        classInfo.setDesc(classCommentTmp);
    }

    @Override
    public String dbName() {
        return "MySql";
    }

}
