package cn.bounter.common.model;

import java.util.List;

/**
 * 封装数据库分页查询结果
 * @author simon
 *
 * @param <T>
 */
public class PageResp<T> {
	
	//分页的总记录条数
	private Integer total;

	//数据记录
    private List<T> records;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public Integer total() {
		return total;
	}

	public PageResp<T> total(Integer total) {
		this.total = total;
		return this;
	}

	public List<T> records() {
		return records;
	}

	public PageResp<T> records(List<T> records) {
		this.records = records;
		return this;
	}
}
