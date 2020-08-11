package com.zdxj.core.service;

import com.zdxj.core.Page;
import com.zdxj.core.entity.BaseEntity;

import java.util.List;
import java.util.Map;


public interface BaseService<T extends BaseEntity> {

    /**
     * 保存非null字段
     * @param entity
     * @return
     */
	Integer saveEntity(T entity) ;
    
    /**
     * 更新非null字段
     * @param entity
     * @return
     */
	Integer updateEntity(T entity) ;
    

    /**
     * 根据UUID获取对象
     * @param id
     * @return
     */
    T getById(Integer id) ;

    /**
     * 获取所有对象集合
     * @return
     */
    List<T> listAll() ;

    /**
     * 根据条件获取对象集合
     * @param maps
     * @return
     */
    List<T> listByCondition(Map<String, Object> maps) ;

    /**
     * 根据条件获取对象集合
     * @param maps
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<T> listByConditionWithPage(Map<String, Object> maps, int startIndex, int pageSize) ;

    /**
     * 根据分页获取对象集合
     * @param maps
     * @param startIndex
     * @param pageSize
     * @return
     */
    Page<List<T>> listByPage(Map<String, Object> maps, int startIndex, int pageSize) ;

    /**
     * 获取所有对象集合数量
     * @return
     */
    int countAll() ;

    /**
     * 根据条件获取对象集合数量
     * @param maps
     * @return
     */
    int countByCondition(Map<String, Object> maps) ;

    /**
     * 获取所有对象UUID集合
     * @return
     */
    List<Integer> listIds() ;

    /**
     * 根据条件获取对象UUID集合
     * @param maps
     * @return
     */
    List<Integer> listIds(Map<String, Object> maps) ;

    /**
     * 根据UUID删除对象
     * @param id
     * @return
     */
    Integer deleteById(Integer id) ;

    /**
     * 根据UUID集合删除对象
     * @param ids
     * @return
     */
    Integer deleteByIds(List<Integer> ids) ;
}
