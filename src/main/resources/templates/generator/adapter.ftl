package ${classInfo.adapterPackage()};

import org.apache.commons.collections4.CollectionUtils;
import ${classInfo.entityPackage()}.${classInfo.name}Entity;
import ${classInfo.paramPackage()}.${classInfo.name}Param;
import ${classInfo.dtoPackage()}.${classInfo.name}Dto;
import org.springframework.beans.BeanUtils;
import java.util.ArrayList;
import java.util.List;

/**
数据转换类
* ${classInfo.desc} Adapter
*
* Created by liupeng6251@163.com on '${.now?string('yyyy-MM-dd HH:mm:ss')}'.
*/
public class ${classInfo.name}Adapter {

    public static ${classInfo.name}Entity adapterDto(${classInfo.name}Dto dto){
       ${classInfo.name}Entity entity = new ${classInfo.name}Entity();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }

    public static ${classInfo.name}Dto adapterEntity(${classInfo.name}Entity  entity){
        ${classInfo.name}Dto dto =new ${classInfo.name}Dto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public static List<${classInfo.name}Entity> adapterDto(List<${classInfo.name}Dto> dtos){
        List< ${classInfo.name}Entity> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(dtos)){
            return result;
        }
        dtos.stream().forEach(o ->
            result.add(adapterDto(o))
        );
        return result;
    }

    public static List<${classInfo.name}Dto> adapterEntity(List<${classInfo.name}Entity> entitys){
        List<${classInfo.name}Dto> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(entitys)){
            return result;
        }
        entitys.stream().forEach(o ->
            result.add(adapterEntity(o))
        );
        return result;
    }

}
