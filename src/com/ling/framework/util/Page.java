package com.ling.framework.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象. 包含当前页数据及分页信息如总记录数.
 */
public class Page<T> implements Serializable {
	private static final long serialVersionUID = -7382038498874118963L;
	
	private int  limit = 20;  // 每页的记录数
	private long start;       // 当前页第一条数据在List中的位置,从0开始
	private long total;       // 总记录数
	
	private List<T> datas = new ArrayList<T>();
	
	public Page() {
		this(0, 0, 20, new ArrayList<T>());
	}

	/**
	 * 默认构造方法.
	 *
	 * @param start	 本页数据在数据库中的起始位置
	 * @param totalSize 数据库中总记录条数
	 * @param pageSize  本页容量
	 * @param data	  本页包含的数据
	 */
	public Page(long start, long total, int limit, List<T> datas) {
		this.limit = limit;
		this.start = start;
		this.total = total;
		this.datas.addAll(datas);
	}

	/**
	 * 取总记录数.
	 */
	public long getTotal() {
		return this.total;
	}

	/**
	 * 取总页数.
	 */
	public long getPageTotal() {
		if (total % limit == 0) {
			return total / limit;
		}
		return total / limit + 1;
	}

	/**
	 * 取每页数据容量.
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * 取当前页中的记录.
	 */
	public List<T> getResult() {
		return datas;
	}

	/**
	 * 取该页当前页码,页码从1开始.
	 */
	public long getCurrentPage() {
		return start / limit + 1;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return getCurrentPage() < getPageTotal() - 1;
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasPreviousPage() {
		return getCurrentPage() > 1;
	}

	/**
	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
	 *
	 * @see #getStartOfPage(int,int)
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, 20);
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 *
	 * @param page   从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int page, int limit) {
		return (page - 1) * limit;
	}
}