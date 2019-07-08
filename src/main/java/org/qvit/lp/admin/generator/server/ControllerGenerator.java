package org.qvit.lp.admin.generator.server;

import org.qvit.lp.admin.model.ClassInfo;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by peng.liu11 on 2019/5/26.
 */
@Component
public class ControllerGenerator extends BaseGenerator {

    @Override
    protected String getTemplate() {
        return "controller.ftl";
    }

    @Override
    protected String getPath(ClassInfo classInfo) {
        String packatePath = classInfo.controllerPackage().replace(".","/")+ File.separator  + classInfo.getName() + "Controller.java";
        return "src/main/java/"+packatePath;
    }
    @Override
    protected boolean isGlobal() {
        return false;
    }
}
