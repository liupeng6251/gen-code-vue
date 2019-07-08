package com.example.demo;

import org.qvit.lp.admin.model.ClassFieldInfo;
import org.qvit.lp.admin.model.ClassInfo;
import org.qvit.lp.admin.utils.FreemarkerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoApplicationTests {


	public static void main(String[] args) throws Exception {
		ClassFieldInfo fieldInfo = new ClassFieldInfo();
		fieldInfo.setColumnName("id");
		fieldInfo.setName("id");
		fieldInfo.setType("BIGINT");
		fieldInfo.setJavaType("java.lang.Long");
		fieldInfo.setLenth(20);
		fieldInfo.setDesc("主键id");

		ClassFieldInfo fieldInfo2 = new ClassFieldInfo();
		fieldInfo2.setColumnName("name");
		fieldInfo2.setName("name");
		fieldInfo2.setType("BIGINT");
		fieldInfo2.setJavaType("java.lang.Long");
		fieldInfo2.setLenth(20);
		fieldInfo2.setDesc("主键id");
		List<ClassFieldInfo> columns = new ArrayList<>();
		columns.add(fieldInfo);
		columns.add(fieldInfo2);
		ClassInfo classInfo = new ClassInfo();
		classInfo.setPrimaryKey(fieldInfo);
		classInfo.setDesc("测试表");
		classInfo.setName("Test");
		classInfo.setBusinessModule("test");
		classInfo.setPackagePath("org.qvit");
		classInfo.setTableName("sys_test");
		classInfo.setColumnList(columns);

		Map<String,Object> param  = new HashMap<>();
		param.put("classInfo",classInfo);

		String value=FreemarkerUtil.processString("mapper.ftl",param);
		System.err.println(value);
	}
}
