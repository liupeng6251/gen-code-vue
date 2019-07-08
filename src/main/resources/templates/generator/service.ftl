package ${classInfo.servicePackage()};

import  ${classInfo.packagePath}.core.page.Pager;
import ${classInfo.entityPackage()}.${classInfo.name}Entity;
import ${classInfo.paramPackage()}.${classInfo.name}Param;
import ${classInfo.dtoPackage()}.${classInfo.name}Dto;
import java.util.List;
/**
* ${classInfo.desc}
*
* Created by liupeng6251@163.com on '${.now?string('yyyy-MM-dd HH:mm:ss')}'.
*/
public interface I${classInfo.name}Service {

    /**
    * 新增
    */
    boolean insert(${classInfo.name}Dto dto);

    /**
    * 新增or更新： ON DUPLICATE KEY UPDATE
    */
    boolean insertOrUpdate(${classInfo.name}Dto dto);

    /**
    * 批量新增： ON DUPLICATE KEY UPDATE
    */
    int insertBatch(${r"List<"}${classInfo.name}${r"Dto>"} dtos);

    /**
    * 删除
    */
    public boolean delete(${classInfo.primaryKey.typeClassName} ${classInfo.primaryKey.name});

    /**
    * 更新
    */
    boolean  update(${classInfo.name}Dto dto);

    /**
    * 查询
    */
    ${classInfo.name}Dto seletBy${classInfo.primaryKey.name?cap_first}(${classInfo.primaryKey.typeClassName} ${classInfo.primaryKey.name});

    /**
    * 分页查询
    */
    Pager<${classInfo.name}Dto> selectPage(${classInfo.name}Param param);

    /**
    * 列表查询
    */
    List<${classInfo.name}Dto> selectList(${classInfo.name}Param param);

}
