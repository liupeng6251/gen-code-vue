package org.qvit.lp.admin.generator.server;

import org.apache.commons.lang3.StringUtils;
import org.qvit.lp.admin.model.ClassInfo;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by peng.liu11 on 2019/5/26.
 */
@Component
public class MyBatisGenerator extends BaseGenerator {

    @Override
    protected String getTemplate() {
        return "mybatis.ftl";
    }

    @Override
    protected String getPath(ClassInfo classInfo) {
        String packatePath;
        if (StringUtils.isNotEmpty(classInfo.getBusinessModule())) {
            packatePath = "src/main/resources/mapper/" + File.separator + classInfo.getBusinessModule() + File.separator + classInfo.getName() + "Entity.xml";
        } else {
            packatePath = "src/main/resources/mapper/" + classInfo.getName() + "Mapper.xml";
        }
        return packatePath;
    }

    @Override
    protected boolean isGlobal() {
        return false;
    }
}
