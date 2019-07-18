package org.qvit.lp.admin.generator.web;

import org.apache.commons.lang3.StringUtils;
import org.qvit.lp.admin.model.ClassInfo;
import org.springframework.stereotype.Component;

/**
 * Created by peng.liu11 on 2019/5/26.
 */
@Component
public class VuePageGenerator extends BaseWbGenerator {

    @Override
    protected String getTemplate() {
        return "vue/Table.ftl";
    }

    @Override
    protected String getPath(ClassInfo classInfo) {
        return "src/views/" + (StringUtils.isEmpty(classInfo.getBusinessModule()) ? "" : classInfo.getBusinessModule() + "/")+ org.qvit.lp.admin.utils.StringUtils.lowerCaseFirst(classInfo.getName())+".vue";
    }

    @Override
    protected boolean isGlobal() {
        return false;
    }
}
