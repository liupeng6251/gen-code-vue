package org.qvit.lp.admin.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * freemarker tool
 *
 * @author xuxueli 2018-05-02 19:56:00
 */
public class FreemarkerUtil {
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

    /**
     * freemarker config
     */
    private static Configuration freemarkerConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    static{
        try {
            freemarkerConfig.setClassLoaderForTemplateLoading(FreemarkerUtil.class.getClassLoader(),"/templates/generator/");
            freemarkerConfig.setNumberFormat("#");
            freemarkerConfig.setClassicCompatible(true);
            freemarkerConfig.setDefaultEncoding("UTF-8");
            freemarkerConfig.setLocale(Locale.CHINA);
            freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * process Template Into String
     *
     * @param template
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processTemplateIntoString(Template template, Object model)
            throws IOException, TemplateException {

        StringWriter result = new StringWriter();
        template.process(model, result);
        return result.toString();
    }

    /**
     * process String
     *
     * @param templateName
     * @param params
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processString(String templateName, Map<String, Object> params)
            throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate(templateName);
        String htmlText = processTemplateIntoString(template, params);
        return htmlText;
    }


}
