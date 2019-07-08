package org.qvit.lp.admin.generator.server;

import org.qvit.lp.admin.generator.IGenerator;
import org.qvit.lp.admin.model.ClassFieldInfo;
import org.qvit.lp.admin.model.ClassInfo;
import org.qvit.lp.admin.utils.FreemarkerUtil;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by peng.liu11 on 2019/5/26.
 */
public abstract class BaseGenerator implements IGenerator {

    public void execute(List<ClassInfo> classInfos, Map<String, Object> extParam, ZipOutputStream zipOutputStream) throws Exception {
        String projectName = String.valueOf(extParam.get("projectName"));
        for (ClassInfo classInfo : classInfos) {
            ZipEntry entry = new ZipEntry(projectName + File.separator + projectName + "-server/" + getPath(classInfo));
            zipOutputStream.putNextEntry(entry);
            ClassFieldInfo primaryKey = classInfo.getPrimaryKey();
            classInfo.setPrimaryKey(primaryKey);
            classInfo.getColumnList().forEach(e -> {
                        if (e.getColumnName().equals(primaryKey.getColumnName()))
                            BeanUtils.copyProperties(e, primaryKey);
                    }
            );
            Map<String, Object> param = new HashMap<>();
            param.put("classInfo", classInfo);
            param.putAll(extParam);
            String value = FreemarkerUtil.processString(getTemplate(), param);
            zipOutputStream.write(value.getBytes(Charset.forName("utf-8")));
            zipOutputStream.closeEntry();
            if (isGlobal()) {
                break;
            }
        }
    }

    protected abstract boolean isGlobal();

    protected abstract String getTemplate();

    protected abstract String getPath(ClassInfo classInfo);

}
