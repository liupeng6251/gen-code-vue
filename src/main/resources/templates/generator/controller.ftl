package ${classInfo.controllerPackage()};

import  ${classInfo.packagePath}.core.page.Pager;
import ${classInfo.packagePath}.core.result.Result;
import ${classInfo.dtoPackage()}.${classInfo.name}Dto;
import ${classInfo.paramPackage()}.${classInfo.name}Param;
import ${classInfo.servicePackage()}.I${classInfo.name}Service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
/**
*
* Created by liupeng6251@163.com on '${.now?string('yyyy-MM-dd HH:mm:ss')}'.
*/
@Controller
@RequestMapping("${classInfo.businessModule}/${classInfo.name?uncap_first}")
public class ${classInfo.name}Controller {

    @Resource
    private I${classInfo.name}Service ${classInfo.name?uncap_first}Service;

    /**
    * 新增
    */
    @RequestMapping("/save.do")
    @ResponseBody
    public Object save(@RequestBody ${classInfo.name}Dto param){
        boolean success = ${classInfo.name?uncap_first}Service.insert(param);
        return Result.result(success);
    }

    /**
    * 删除
    */
    @RequestMapping("/delete.do")
    @ResponseBody
    public Object delete(${classInfo.primaryKey.typeClassName} ${classInfo.primaryKey.name}){
        boolean success = ${classInfo.name?uncap_first}Service.delete(${classInfo.primaryKey.name});
        return Result.result(success);
    }

    /**
    * 更新
    */
    @RequestMapping("/update.do")
    @ResponseBody
    public Object update(@RequestBody ${classInfo.name}Dto dto){
        boolean success = ${classInfo.name?uncap_first}Service.update(dto);
        return Result.result(success);
    }

    /**
    * Load查询
    */
    @RequestMapping("/detail.do")
    @ResponseBody
    public Object detail(${classInfo.primaryKey.typeClassName} ${classInfo.primaryKey.name}){
        ${classInfo.name}Dto dto = ${classInfo.name?uncap_first}Service.seletBy${classInfo.primaryKey.name?cap_first}(${classInfo.primaryKey.name});
         return Result.result(dto);
    }

    /**
    * 分页查询
    */
    @RequestMapping("/pageList.do")
    @ResponseBody
    public Object pageList(@RequestBody ${classInfo.name}Param param) {
        Pager<${classInfo.name}Dto> page = ${classInfo.name?uncap_first}Service.selectPage(param);
        Map<String, Object> result = new HashMap<>();
        result.put("draw", param.getDraw());
        result.put("recordsTotal", page.getRecords());
        result.put("recordsFiltered", page.getRecords());
        result.put("data", page.getRows());
        return result;
    }

    @RequestMapping("/list.do")
    public String list(){
      return "${classInfo.businessModule}/${classInfo.name?uncap_first}";
    }
}
