package com.zdxj.core.mapper;

import java.util.List;
import java.util.Map;

import com.zdxj.core.entity.BaseEntity;

public interface BaseMapper<T extends BaseEntity> {

    /**
     * 添加非null字段
     * @param entity
     * @return
     */
    int saveEntity(T entity);

    /**
     * 更新非null字段
     * @param entity
     * @return
     */
    int updateEntity(T entity);

    /**
     * 根据UUID获取对象
     * @param id
     * @return
     */
    T getById(Integer id);

    /**
     * 根据条件获取对象集合
     * @param maps
     * @return
     */
    List<T> listByCondition(Map<String, Object> maps);

    /**
     * 根据条件获取对象集合数量
     * @param maps
     * @return
     */
    int countByCondition(Map<String, Object> maps);

    /**
     * 根据条件获取对象UUID集合
     * @param maps
     * @return
     */
    List<Integer> listIds(Map<String, Object> maps);

    /**
     * 根据UUID删除对象
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据UUID集合删除对象
     * @param list
     * @return
     */
    int deleteByIds(List<Integer> list);
   
}
