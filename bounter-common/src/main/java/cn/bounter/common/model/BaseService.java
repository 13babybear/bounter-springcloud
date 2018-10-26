package cn.bounter.common.model;

import java.util.List;
import java.util.Map;
import java.util.Set;


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
	 * 查询所有
	 * @param t
	 * @return
	 */
	List<T> findAll(T t);

	/**
	 * 查询列表
	 * @param reqMap
	 * @return
	 */
	List<T> findList(Map<String, Object> reqMap);

	/**
	 * 查询
	 * @param reqMap
	 * @return
	 */
	BaseService<T> select(Map<String, Object> reqMap);

	/**
	 * 选择字段
	 * @return
	 */
	BaseService<T> slice();

	/**
	 * 获取列表
	 * @return
	 */
	List<?> list();

	/**
	 * 获取请求参数
	 * @return
	 */
	Map<String, Object> reqMap();

//	PageResp paging(Map<String, Object> reqMap, List<?> list);

	/**
	 * 获取分页结果
	 * @return
	 */
	PageResp page();

	/**
	 * 获取分页结果
	 * @param reqMap
	 * @param list
	 * @return
	 */
	@Deprecated
	PageResp paging(Map<String, Object> reqMap, List<?> list);

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
