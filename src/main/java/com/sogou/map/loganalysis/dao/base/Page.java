package com.sogou.map.loganalysis.dao.base;

import java.util.List;

public class Page<E> {
	
	private int start;
	private int limit;
	private int totalCount;
	private List<E> list;
	
	public Page(int start, int limit, int totalCount, List<E> list) {
		this.start = start;
		this.limit = limit;
		this.totalCount = totalCount;
		this.list = list;
	}
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<E> getList() {
		return list;
	}
	public void setList(List<E> list) {
		this.list = list;
	}
	
	

}
