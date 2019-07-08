package ${classInfo.serviceImplPackage()};

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.collections4.CollectionUtils;
import ${classInfo.adapterPackage()}.${classInfo.name}Adapter;
import  ${classInfo.packagePath}.core.page.Pager;
import ${classInfo.dtoPackage()}.${classInfo.name}Dto;
import ${classInfo.entityPackage()}.${classInfo.name}Entity;
import ${classInfo.mapperPackage()}.${classInfo.name}Mapper;
import ${classInfo.paramPackage()}.${classInfo.name}Param;
import  ${classInfo.servicePackage()}.I${classInfo.name}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




/**
* ${classInfo.desc}
*
* Created by liupeng6251@163.com on '${.now?string('yyyy-MM-dd HH:mm:ss')}'.
*/
@Service
public class ${classInfo.name}ServiceImpl implements I${classInfo.name}Service {

	@Autowired
	private ${classInfo.name}Mapper ${classInfo.name?uncap_first}Mapper;


	/**
	* 新增
	*/
	public boolean insert(${classInfo.name}Dto dto){
		${classInfo.name}Entity entity = ${classInfo.name}Adapter.adapterDto(dto);
		return ${classInfo.name?uncap_first}Mapper.insertSelective(entity) > 0;
    }

	/**
	* 新增or更新： ON DUPLICATE KEY UPDATE
	*/
	public boolean insertOrUpdate(${classInfo.name}Dto dto){
		${classInfo.name}Entity entity = ${classInfo.name}Adapter.adapterDto(dto);
		return ${classInfo.name?uncap_first}Mapper.insertOrUpdate(entity) > 0;
	}

	/**
	* 批量新增： ON DUPLICATE KEY UPDATE
	*/
	public int insertBatch(${r"List<"}${classInfo.name}${r"Dto>"} dtos){
		List<${classInfo.name}Entity> entitys = ${classInfo.name}Adapter.adapterDto(dtos);
		return ${classInfo.name?uncap_first}Mapper.insertBatch(entitys);
    }

	/**
	* 删除
	*/
	public boolean delete(${classInfo.primaryKey.typeClassName} ${classInfo.primaryKey.name}){
		return ${classInfo.name?uncap_first}Mapper.deleteByPrimaryKey(${classInfo.primaryKey.name}) > 0;
	}

	/**
	* 更新
	*/
	public boolean update(${classInfo.name}Dto dto){
		${classInfo.name}Entity entity = ${classInfo.name}Adapter.adapterDto(dto);
		return ${classInfo.name?uncap_first}Mapper.updateByPrimaryKeySelective(entity) > 0;
	}

	/**
	* 查询
	*/
	public ${classInfo.name}Dto seletBy${classInfo.primaryKey.name?cap_first}(${classInfo.primaryKey.typeClassName} ${classInfo.primaryKey.name}){
		${classInfo.name}Entity entity = ${classInfo.name?uncap_first}Mapper.selectByPrimaryKey(${classInfo.primaryKey.name});
		if(entity ==null){
        	return null;
		}
		return ${classInfo.name}Adapter.adapterEntity(entity);
	}

	/**
	* 分页查询
	*/
	public Pager<${classInfo.name}Dto> selectPage(${classInfo.name}Param param){
        long count = ${classInfo.name?uncap_first}Mapper.countByParam(param);
		List<${classInfo.name}Dto> list = new ArrayList();
        if(count == 0){
			return  Pager.builder(list).current(param.getPage()).total(count).create();
		}
		list = selectList(param);
        return Pager.builder(list).current(param.getPage()).total(count).create();
    }

    /**
    * 列表查询
    */
    public List<${classInfo.name}Dto> selectList(${classInfo.name}Param param){
        List<${classInfo.name}Entity> entitys = ${classInfo.name?uncap_first}Mapper.selectByParam(param);
        if(CollectionUtils.isNotEmpty(entitys)){
            return ${classInfo.name}Adapter.adapterEntity(entitys);
        }
		return new ArrayList(0);
	}
}
