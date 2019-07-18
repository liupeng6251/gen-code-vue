package org.qvit.lp.admin.generator.web;

import org.qvit.lp.admin.generator.IGenerator;
import org.qvit.lp.admin.model.ClassInfo;
import org.qvit.lp.admin.utils.FreemarkerUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by peng.liu11 on 2019/6/5.
 */
@Component
public class VueMockGenerator implements IGenerator {

    @Override
    public void execute(List<ClassInfo> classInfos, Map<String, Object> extParam, ZipOutputStream zipOutputStream) throws Exception {
        String projectName = String.valueOf(extParam.get("projectName"));
        ZipEntry entry = new ZipEntry(projectName + File.separator + projectName + "-vue/" + getPath());
        zipOutputStream.putNextEntry(entry);
        Map<String, Object> param = new HashMap<>();
        param.put("classInfos", classInfos);
        param.putAll(extParam);
        String value = FreemarkerUtil.processString(getTemplate(), param);
        zipOutputStream.write(value.getBytes(Charset.forName("utf-8")));
        zipOutputStream.closeEntry();
    }

    private String getTemplate() {
        return "vue/mock.ftl";
    }

    private String getPath() {
        return "src/mock/mock.js";
    }

}
