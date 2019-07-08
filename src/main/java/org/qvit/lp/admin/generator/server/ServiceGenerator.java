package org.qvit.lp.admin.generator.server;

import org.qvit.lp.admin.model.ClassInfo;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by peng.liu11 on 2019/5/26.
 */
@Component
public class ServiceGenerator extends BaseGenerator {

    @Override
    protected String getTemplate() {
        return "service.ftl";
    }

    @Override
    protected String getPath(ClassInfo classInfo) {
        String packatePath = classInfo.servicePackage().replace(".","/")+ File.separator + "I" + classInfo.getName() + "Service.java";
        return "src/main/java/"+packatePath;
    }
    @Override
    protected boolean isGlobal() {
        return false;
    }
}
