
package org.qvit.lp.admin.generator.web;

import org.apache.commons.lang3.StringUtils;
import org.qvit.lp.admin.model.ClassInfo;
import org.springframework.stereotype.Component;

/**
 * Created by peng.liu11 on 2019/6/5.
 */
@Component
public class VueApiGenerator extends BaseWbGenerator {

    @Override
    protected boolean isGlobal() {
        return false;
    }


    @Override
    protected String getTemplate() {
        return "vue/api.ftl";
    }

    @Override
    protected String getPath(ClassInfo classInfo) {
        String path = "src/api/" + (StringUtils.isEmpty(classInfo.getBusinessModule()) ? "" : classInfo.getBusinessModule() + "/");
        String packatePath = path + org.qvit.lp.admin.utils.StringUtils.lowerCaseFirst(classInfo.getName()) + ".js";
        return packatePath;
    }

}
