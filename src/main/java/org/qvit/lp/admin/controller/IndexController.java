package org.qvit.lp.admin.controller;

import com.alibaba.fastjson.JSONArray;
import org.qvit.lp.admin.core.MySqlDbHepler;
import org.qvit.lp.admin.generator.IGenerator;
import org.qvit.lp.admin.model.ClassInfo;
import org.qvit.lp.admin.model.ReturnT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * sso server (for web)
 *
 * @author xuxueli 2017-08-01 21:39:47
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private MySqlDbHepler mySqlDbHepler;

    @Autowired
    private List<IGenerator> generators;


    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping("/tableList")
    @ResponseBody
    public ReturnT<List<ClassInfo>> codeGenerate(String url, String userName, String password) throws Exception {
        return new ReturnT<List<ClassInfo>>(mySqlDbHepler.tableList(url, userName, password));
    }


    @RequestMapping("/codeGenerate")
    @ResponseBody
    public void codeGenerate(String data,
                             String jdbcUrl,
                             String jdbcUsername,
                             String jdbcPassword,
                             String packageName,
                             String projectName,
                             String projectDesc,
                             HttpServletResponse resp) {
        try {
            logger.info(data);
            List<ClassInfo> tables = JSONArray.parseArray(data, ClassInfo.class);
            resp.setHeader("Content-Disposition", "attachment; filename=\""+projectName+".zip\"");
            resp.setContentType("application/octet-stream;charset=UTF-8");
            ZipOutputStream zipOut = new ZipOutputStream(resp.getOutputStream());
            Map<String, Object> extParam = new HashMap<>();
            extParam.put("jdbcUrl", jdbcUrl);
            extParam.put("jdbcPassword", jdbcPassword);
            extParam.put("jdbcUsername", jdbcUsername);
            extParam.put("packageName", packageName);
            extParam.put("projectName", projectName);
            extParam.put("projectDesc", projectDesc);
            for (IGenerator generator : generators) {
                generator.execute(tables, extParam, zipOut);
            }
            zipOut.finish();
            zipOut.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}