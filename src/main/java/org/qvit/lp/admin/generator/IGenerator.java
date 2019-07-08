package org.qvit.lp.admin.generator;

import org.qvit.lp.admin.model.ClassInfo;

import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * Created by peng.liu11 on 2019/6/5.
 */
public interface IGenerator {

    void execute(List<ClassInfo> classInfos, Map<String, Object> extParam, ZipOutputStream zipOutputStream) throws Exception;

}
