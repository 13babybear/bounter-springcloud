package cn.bounter.common.model;

import java.util.List;
import java.util.Map;


/**
 * Dao公共基类，封装了常用的持久层方法
 * @author simon
 *
 * @param <T>
 */
public interface BaseDao<T> {
	
	T selectById(long id);
	
	String selectName(long id);
	
	T selectOne(T t);
	
	T selectFirst(T t);
	
	T selectLast(T t);
	
	List<T> selectAll(T t);
	
	List<T> selectList(Map<String, Object> reqMap);
	
	Integer count(Map<String, Object> reqMap);
	
	void insert(T t);
	
	void batchInsert(List<T> list);
	
	void delete(T t);
	
	void deleteById(long id);
	
	void update(T t);
	
}
