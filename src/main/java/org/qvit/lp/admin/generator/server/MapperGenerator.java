package org.qvit.lp.admin.generator.server;

import org.qvit.lp.admin.model.ClassInfo;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by peng.liu11 on 2019/5/26.
 */
@Component
public class MapperGenerator extends BaseGenerator {

    @Override
    protected String getTemplate() {
        return "mapper.ftl";
    }

    @Override
    protected String getPath(ClassInfo classInfo) {
        String packatePath = classInfo.mapperPackage().replace(".","/")+ File.separator  + classInfo.getName() + "Mapper.java";
        return "src/main/java/"+packatePath;
    }

    @Override
    protected boolean isGlobal() {
        return false;
    }
}
