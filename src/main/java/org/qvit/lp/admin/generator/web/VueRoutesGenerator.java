package org.qvit.lp.admin.generator.web;

import org.qvit.lp.admin.generator.IGenerator;
import org.qvit.lp.admin.model.ClassInfo;
import org.qvit.lp.admin.utils.FreemarkerUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by peng.liu11 on 2019/6/5.
 */
@Component
public class VueRoutesGenerator implements IGenerator {

    @Override
    public void execute(List<ClassInfo> classInfos, Map<String, Object> extParam, ZipOutputStream zipOutputStream) throws Exception {
        String projectName = String.valueOf(extParam.get("projectName"));
        ZipEntry entry = new ZipEntry(projectName + File.separator + projectName + "-vue/" + getPath());
        zipOutputStream.putNextEntry(entry);
        Map<String, Object> param = new HashMap<>();
        param.put("routes", menuTree(classInfos));
        param.putAll(extParam);
        String value = FreemarkerUtil.processString(getTemplate(), param);
        zipOutputStream.write(value.getBytes(Charset.forName("utf-8")));
        zipOutputStream.closeEntry();
    }

    private String getTemplate() {
        return "vue/routes.ftl";
    }

    private String getPath() {
        return "src/routes.js";
    }

    private List<Map<String, Object>> menuTree(List<ClassInfo> classInfos) {
        Map<String, List<ClassInfo>> maps = classInfos.stream().collect((Collectors.groupingBy(o -> o.getBusinessModule())));
        List<Map<String, Object>> result = new ArrayList<>(maps.size());
        for (String key : maps.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("module", key);
            map.put("moduleName", maps.get(key).get(0).getBusinessModuleDesc());
            map.put("children", maps.get(key));
            result.add(map);
        }
        return result;
    }
}
