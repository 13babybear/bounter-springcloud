package cn.bounter.common.model;

import java.util.List;

/**
 * 封装数据库分页查询结果
 * @author simon
 *
 */
public class PageResp {
	
	//分页的总记录条数
	private Integer total;

	//数据记录
    private List<?> records;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<?> getRecords() {
		return records;
	}

	public void setRecords(List<?> records) {
		this.records = records;
	}

	public Integer total() {
		return total;
	}

	public PageResp total(Integer total) {
		this.total = total;
		return this;
	}

	public List<?> records() {
		return records;
	}

	public PageResp records(List<?> records) {
		this.records = records;
		return this;
	}
}
