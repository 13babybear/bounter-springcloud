package cn.bounter.common.model;

import cn.bounter.common.util.BeanUtil;
import com.google.common.base.CaseFormat;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BaseServiceImpl<T> implements BaseService<T> {

	//默认页号
	private static final Integer DEFAULT_PAGE_NUM = 1;
	//默认每页大小
	private static final Integer DEFAULT_PAGE_SIZE = 10;
	
	@Autowired
	private BaseDao<T> baseDao;

	private ThreadLocal<List<?>> listThreadLocal = new ThreadLocal<>();
	private ThreadLocal<Map<String, Object>> reqMapThreadLocal = new ThreadLocal<>();

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
	public List<T> findAll(T t) {
		return baseDao.selectAll(t);
	}


	@Override
	public BaseServiceImpl<T> select(Map<String, Object> reqMap) {
		reqMap = setParam(reqMap);
		this.reqMapThreadLocal.set(reqMap);

		List<T> list =  baseDao.selectList(reqMap);
		this.listThreadLocal.set(list);
		return this;
	}

	@Override
	public BaseService<T> map(Function<Map<String, Object>, Map<String, Object>> mapper) {
		List<?> list = list();
		Map<String, Object> reqMap = reqMap();
		//包含属性
		String[] includeFields = reqMap.get("columns") == null ? null : String.valueOf(reqMap.get("columns")).split(",");
		//排除属性
		String[] excludeFields = reqMap.get("ignoreColumns") == null ? null : String.valueOf(reqMap.get("ignoreColumns")).split(",");
		if (mapper == null) {
			this.listThreadLocal.set(list.stream().map(t -> BeanUtil.toMap(t, includeFields, excludeFields)).collect(Collectors.toList()));
		} else {
			this.listThreadLocal.set(list.stream().map(t -> BeanUtil.toMap(t, includeFields, excludeFields)).map(mapper).collect(Collectors.toList()));
		}
		return this;
	}


	@Override
	public List<T> list() {
		return (List<T>) this.listThreadLocal.get();
	}

	@Override
	public Map<String, Object> reqMap() {
		return this.reqMapThreadLocal.get();
	}

	@Override
	public PageResp page() {
		List<?> list = list();

		//如果实体类没转成Map，则把实体类转成Map
		if(!(list.get(0) instanceof Map)) {
			//转成map，并放入list中
			map(null);
			//获取最新的list
			list = list();
		}

		if(list == null || list.isEmpty()) {
			return null;
		}

		Map<String, Object> reqMap = reqMap();
		return new PageResp().records(list).total(count(reqMap));
	}

	@Override
	public Integer count(Map<String, Object> reqMap) {
		reqMap = setParam(reqMap);
		return baseDao.count(reqMap);
	}


	/**
	 * 设置分页和搜索参数
	 * @param reqMap
	 * @return
	 */
	private Map<String, Object> setParam(Map<String, Object> reqMap) {
		if(reqMap == null) {
			return null;
		}

		// 设置分页偏移量
		if(reqMap.get("pageNum") != null && reqMap.get("pageSize") != null) {
			Integer pageNum = null;
			Integer pageSize = null;
			try {
				pageNum = Integer.parseInt(String.valueOf(reqMap.get("pageNum")));
				if(pageNum < 1) {
					pageNum = DEFAULT_PAGE_NUM;
				}
			} catch (Exception e) {
				e.printStackTrace();
				pageNum = DEFAULT_PAGE_NUM;
			}
			try {
				pageSize = Integer.parseInt(String.valueOf(reqMap.get("pageSize")));
				if(pageSize < 1) {
					pageSize = DEFAULT_PAGE_SIZE;
				}
			} catch (Exception e) {
				e.printStackTrace();
				pageSize = DEFAULT_PAGE_SIZE;
			}
			reqMap.put("pageSize", pageSize);
			reqMap.put("offset", (pageNum -1) * pageSize);
		}

		//设置orderBy，将驼峰命名法转换成下划线
        if (reqMap.get("orderBy") != null) {
            reqMap.put("orderBy", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,String.valueOf(reqMap.get("orderBy"))));
        }

		//设置搜索关键字
		if(reqMap.get("keyword") != null) {
			reqMap.put("keyword", "%" + reqMap.get("keyword") + "%");
		}

		return reqMap;
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
