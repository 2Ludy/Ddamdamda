package com.ddam.damda.common.util;
//com.ssafy.edu.util.PageRequest
public class PageRequest {
	private int pageNum;
	private int pageSize;
	private String keyword;
    private String search;
	public PageRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PageRequest(int pageNum, int pageSize, String keyword, String search) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.keyword = keyword;
		this.search = search;
	}
	@Override
	public String toString() {
		return "PageRequest [pageNum=" + pageNum + ", pageSize=" + pageSize + ", keyword=" + keyword + ", search="
				+ search + "]";
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	
}
