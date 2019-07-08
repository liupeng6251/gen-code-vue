package org.qvit.lp.admin.generator.server;

import org.qvit.lp.admin.model.ClassInfo;
import org.springframework.stereotype.Component;

/**
 * Created by peng.liu11 on 2019/5/26.
 */
@Component
public class PomGenerator extends BaseGenerator {

    @Override
    protected String getTemplate() {
        return "pom.ftl";
    }

    @Override
    protected String getPath(ClassInfo classInfo) {
        return "pom.xml";
    }

    @Override
    protected boolean isGlobal() {
        return true;
    }
}
