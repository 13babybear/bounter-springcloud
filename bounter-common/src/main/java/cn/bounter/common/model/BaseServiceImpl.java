package cn.bounter.common.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseServiceImpl<T> implements BaseService<T> {

	//默认页号
	private static final Integer DEFAULT_PAGE_NUM = 1;
	//默认每页大小
	private static final Integer DEFAULT_PAGE_SIZE = 10;
	
	@Autowired
	private BaseDao<T> baseDao;

	@Override
	public T findById(long id) {
		return baseDao.selectById(id);
	}

	@Override
	public String findName(long id) {
		return baseDao.selectName(id);
	}
	
	@Override
	public T findOne(T t) {
		return baseDao.selectOne(t);
	}

	@Override
	public T findOneByAny(T t) {
		return baseDao.selectOneByAny(t);
	}
	
	@Override
	public List<T> findAll(T t) {
		return baseDao.selectAll(t);
	}

	@Override
	public PageResp<T> findList(Map<String, Object> reqMap) {
		reqMap = setPaging(reqMap);

		List<T> list =  baseDao.selectList(reqMap);

		if(list == null || list.isEmpty()) {
			return null;
		}

		return new PageResp<T>().records(list).total(baseDao.countPage(reqMap));
	}

	@Override
	public PageResp<T> findListByAny(Map<String, Object> reqMap) {
		reqMap = setPaging(reqMap);

		List<T> list =  baseDao.selectListByAny(reqMap);

		if(list == null || list.isEmpty()) {
			return null;
		}

		return new PageResp<T>().records(list).total(baseDao.countPageByAny(reqMap));
	}

	/**
	 * 设置分页参数
	 * @param reqMap
	 * @return
	 */
	private Map<String, Object> setPaging(Map<String, Object> reqMap) {
		if(reqMap == null) {
			return null;
		}

		// 设置分页偏移量
		if(reqMap.get("pageNum") != null && reqMap.get("pageSize") != null) {
			Integer pageNum = null;
			Integer pageSize = null;
			try {
				pageNum = Integer.parseInt((String)reqMap.get("pageNum"));
				if(pageNum < 1) {
					pageNum = DEFAULT_PAGE_NUM;
				}
			} catch (Exception e) {
				pageNum = DEFAULT_PAGE_NUM;
			}
			try {
				pageSize = Integer.parseInt((String)reqMap.get("pageSize"));
				if(pageSize < 1) {
					pageSize = DEFAULT_PAGE_SIZE;
				}
			} catch (Exception e) {
				pageSize = DEFAULT_PAGE_SIZE;
			}
			reqMap.put("pageSize", pageSize);
			reqMap.put("offset", (pageNum -1) * pageSize);
		}

		//设置搜索关键字
		if(reqMap.get("keyword") != null) {
			reqMap.put("keyword", "%" + reqMap.get("keyword") + "%");
		}

		return reqMap;
	}
	
	@Override
	public Integer count(T t) {
		return baseDao.count(t);
	}
	
	@Override
	public void add(T t) {
		baseDao.insert(t);
	}

	@Override
	public void batchAdd(List<T> list) {
		baseDao.batchInsert(list);
	}
	
	@Override
	public void modify(T t) {
		baseDao.update(t);
	}

	@Override
	public void deleteById(long id) {
		baseDao.deleteById(id);
	}

	@Override
	public T findFirst(T t) {
		return baseDao.selectFirst(t);
	}

	@Override
	public T findLast(T t) {
		return baseDao.selectLast(t);
	}

	@Override
	public void delete(T t) {
		baseDao.delete(t);
	}

}
