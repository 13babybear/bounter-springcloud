package cn.bounter.common.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;


/**
 * Service公共基类，封装了常用的服务层方法
 * @author simon
 *
 */
public interface BaseService<T> {
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	T findById(long id);
	
	/**
	 * 根据id查询名字
	 * @param id
	 * @return
	 */
	String findName(long id);
	
	/**
	 * 查询单个
	 * @param t
	 * @return
	 */
	T findOne(T t);

	/**
	 * 查询第一个
	 * @param t
	 * @return
	 */
	T findFirst(T t);
	
	/**
	 * 查询最后一个
	 * @param t
	 * @return
	 */
	T findLast(T t);

	/**t
	 * 全量查询
	 * @param t
	 * @return
	 */
	List<T> findAll(T t);


	/**
	 * 条件查询
	 * @param reqMap
	 * @return
	 */
	BaseService<T> select(Map<String, Object> reqMap);

	/**
	 * 查询结果转map
	 * @param mapper		自定义映射器
	 * @return
	 */
	BaseService<?> map(Function<Map<String, Object>, Map<String, Object>> mapper);


	/**
	 * 获取分页形式的查询结果
	 * @return
	 */
	PageResp page();

	/**
	 * 获取列表形式查询结果
	 * @return
	 */
	List<T> list();

	/**
	 * 获取请求参数
	 * @return
	 */
	Map<String, Object> reqMap();


	/**
	 * 统计总记录数
	 * @param reqMap
	 * @return
	 */
	Integer count(Map<String, Object> reqMap);


	/**
	 * 新增
	 * @param t
	 */
	void add(T t);

	/**
	 * 批量新增
	 * @param list
	 */
	void batchAdd(List<T> list);
	
	/**
	 * 修改
	 * @param t
	 */
	void modify(T t);
	
	/**
	 * 根据id删除
	 * @param id
	 */
	void deleteById(long id);
	
	/**
	 * 删除
	 * @param t
	 */
	void delete(T t);
}
