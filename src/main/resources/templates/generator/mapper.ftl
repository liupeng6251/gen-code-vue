package ${classInfo.mapperPackage()};

import org.apache.ibatis.annotations.Mapper;
import ${classInfo.entityPackage()}.${classInfo.name}Entity;
import ${classInfo.paramPackage()}.${classInfo.name}Param;
import java.util.List;
import java.io.Serializable;

import org.apache.ibatis.annotations.Param;


/**
*  ${classInfo.desc}
*
*  Created by liupeng6251@163.com on '${.now?string('yyyy-MM-dd HH:mm:ss')}'.
*/

@Mapper
public interface ${classInfo.name}Mapper {

    int deleteByPrimaryKey(Serializable id);

    int insertSelective(${classInfo.name}Entity record);

    int insertBatch(List<${classInfo.name}Entity> var1);

    int insertOrUpdate(${classInfo.name}Entity record);

    List<${classInfo.name}Entity> selectByParam(${classInfo.name}Param param);

    long countByParam(${classInfo.name}Param param);

     ${classInfo.name}Entity selectByPrimaryKey(Serializable id);

    int updateByPrimaryKeySelective(${classInfo.name}Entity record);

 }