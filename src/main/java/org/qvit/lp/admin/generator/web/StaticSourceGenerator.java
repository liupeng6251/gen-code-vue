package org.qvit.lp.admin.generator.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.qvit.lp.admin.generator.IGenerator;
import org.qvit.lp.admin.model.ClassInfo;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by peng.liu11 on 2019/6/5.
 */
@Component
public class StaticSourceGenerator implements IGenerator {

    @Override
    public void execute(List<ClassInfo> classInfos, Map<String, Object> extParam, ZipOutputStream zipOutputStream) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();//ClassPathResource("statics");
        Resource[] resources = resolver.getResources("classpath*:/vue/**");
        String projectName = String.valueOf(extParam.get("projectName"));
        for (Resource resource : resources) {
            try {
                String path = resource.getURL().getPath();
                String classPath = StringUtils.substring(path, StringUtils.indexOf(path, "vue") + 4, path.length());
                ZipEntry entry = new ZipEntry(projectName + File.separator + projectName + "-vue/" + File.separator + classPath);
                zipOutputStream.putNextEntry(entry);
                IOUtils.copy(resource.getInputStream(), zipOutputStream);
                zipOutputStream.closeEntry();
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
    }
}
