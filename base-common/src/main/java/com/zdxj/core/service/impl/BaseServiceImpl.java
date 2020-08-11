package com.zdxj.core.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.zdxj.core.Page;
import com.zdxj.core.entity.BaseEntity;
import com.zdxj.core.mapper.BaseMapper;
import com.zdxj.core.service.BaseService;
import com.zdxj.util.RedisUtils;
import com.zdxj.util.ToolUtils;


public class BaseServiceImpl<M extends BaseMapper<T>,T extends BaseEntity> implements BaseService<T> {

	private final String REDIS_KEY_PRE;
	public BaseServiceImpl(String redis_pre_key) {
		this.REDIS_KEY_PRE = redis_pre_key;
	}
	
	@Autowired
	private RedisUtils redisUtils;
	
	@Autowired
	private M baseMapper;

	public M getBaseMapper() {
		return baseMapper;
	}

	@Override
	public Integer saveEntity(T entity) {
		this.checkXss(entity, this.ignoreXssFields());
		Date date = new Date();
		entity.setCreateTime(date);
		entity.setUpdateTime(date);
		baseMapper.saveEntity(entity);
		return entity.getId();
	}

	@Override
	public Integer updateEntity(T entity) {
		this.checkXss(entity, this.ignoreXssFields());
		entity.setUpdateTime(new Date());
		redisUtils.del(this.REDIS_KEY_PRE + entity.getId());
		this.updateCache(entity);
		baseMapper.updateEntity(entity);
		return entity.getId();
	}
	/**
	 * 不进行checkXss的字段
	 * @return
	 */
	public List<String> ignoreXssFields() {
		return null;
	}

	public void updateCache(T entity) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(Integer id) {
		Object object = redisUtils.get(REDIS_KEY_PRE + id);
		if (object != null) {
			return (T) object;
		}
		T entity = baseMapper.getById(id);
		if (entity != null) {
			redisUtils.set(REDIS_KEY_PRE + id, entity);
			return entity;
		}
		return null ;
	}

	@Override
	public List<T> listAll() {
		return this.listByCondition(null);
	}

	@Override
	public List<T> listByCondition(Map<String, Object> maps) {
		maps = this.handlerSortMap(maps);
		return baseMapper.listByCondition(maps);
	}

	@Override
	public List<T> listByConditionWithPage(Map<String, Object> maps, int startIndex, int pageSize) {
		maps.put("startIndex", startIndex < 0 ? 0 : startIndex);
		maps.put("pageSize", pageSize < 0 ? 5 : pageSize);
		return this.listByCondition(maps);
	}

	@Override
	public Page<List<T>> listByPage(Map<String, Object> maps, int startIndex, int pageSize) {
		startIndex = startIndex < 0 ? 0 : startIndex;
		pageSize = pageSize < 0 ? 5 : pageSize;
		int count = baseMapper.countByCondition(maps);
		Page<List<T>> page = new Page<List<T>>(startIndex, pageSize, count);
		if (count > 0) {
			maps.put("startIndex", startIndex);
			maps.put("pageSize", pageSize);
			List<T> list = this.listByCondition(maps);
			page.setRecords(list);
		}
		return page;
	}

	@Override
	public int countAll() {
		return baseMapper.countByCondition(null);
	}

	@Override
	public int countByCondition(Map<String, Object> maps) {
		return baseMapper.countByCondition(maps);
	}

	@Override
	public List<Integer> listIds() {
		return baseMapper.listIds(null);
	}

	@Override
	public List<Integer> listIds(Map<String, Object> maps) {
		return baseMapper.listIds(maps);
	}

	@Override
	public Integer deleteById(Integer id) {
		redisUtils.del(this.REDIS_KEY_PRE + id);
		this.deleteCache(id);
		return baseMapper.deleteById(id);
	}

	public void deleteCache(Integer id) {}

	@Override
	public Integer deleteByIds(List<Integer> list) {
		this.deleteCaches(list);
		List<String> ids = new ArrayList<String>();
		for (Integer id : list) {
			ids.add(this.REDIS_KEY_PRE + id);
		}
		redisUtils.del(ids);
		return baseMapper.deleteByIds(list);
	}

	public void deleteCaches(List<Integer> list) {}


	/**
	 * 对象所有的string类型的字段都过滤xss
	 * @param entity
	 * @param ignoreList 忽略过滤的字段
	 * @return
	 */
	private T checkXss(T entity,List<String> ignoreList) {
		Field[] field = entity.getClass().getDeclaredFields();
		try {
			for (int j = 0; j < field.length; j++) {
				boolean isFinal = Modifier.isFinal(field[j].getModifiers());
				if(isFinal) continue ;
				String name = field[j].getName();
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				String type = field[j].getGenericType().toString();
				if (!type.equals("class java.lang.String")) {
					continue ;
				}
				Method m = entity.getClass().getMethod("get" + name);
				String value = (String) m.invoke(entity);
				if (StringUtils.isBlank(value)) {
					continue ;
				}
				m = entity.getClass().getMethod("set" + name, String.class);
				if (ignoreList == null || !ignoreList.contains(field[j].getName())) {
					m.invoke(entity, ToolUtils.checkStr(value.trim()));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("数据转换错误：" + e.getMessage());
		}
		return entity ;
	}

	private Map<String, Object> handlerSortMap(Map<String,Object> maps){
		
		Map<String, String> orderbys = new LinkedHashMap<String, String>();
		if(maps == null || maps.size()<=0) {
			maps = new HashMap<String, Object>();
			orderbys.put("id", "desc");
			maps.put("orderbys", orderbys);
			return maps;
		}
		if (maps.get("orderBy") == null) {
			orderbys.put("id", "desc");
			maps.put("orderbys", orderbys);
			return maps;
		}
		String[] sortArrs = ((String) maps.get("orderBy")).split(";");
		if(sortArrs == null || sortArrs.length<=0) {
			orderbys.put("id", "desc");
			maps.put("orderbys", orderbys);
			return maps;
		}
		for(String sort:sortArrs) {
			String[] sorts = sort.split(",");
			if(StringUtils.isNotBlank(sorts[0]) && StringUtils.isNotBlank(sorts[1])) {
				orderbys.put(ToolUtils.checkStr(sorts[0]), ToolUtils.checkStr(sorts[1]));
				maps.put("orderbys", orderbys);
			}
		}
		maps.remove("orderBy");
		if (maps.get("orderbys") == null) {
			orderbys.put("id", "desc");
			maps.put("orderbys", orderbys);
		}
		return maps ;
	}
}
