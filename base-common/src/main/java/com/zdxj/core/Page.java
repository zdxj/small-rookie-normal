package com.zdxj.core;

import java.io.Serializable;

/**
 * @Author zhaodx
 * @Date 2019/6/22 17:20
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 5996819594586049616L;
    /* 当前页*/
    private int currentPage = 1;
    /* 每页显示的条数*/
    private int pageSize = 10;
    /* 总记录条数*/
    private int totalRecords = 0;
    /* 查询的开始记录索引*/
    private int startIndex = 0;
    /* 总页数*/
    private int totalPageCount = 0;
    /* 分好页的结果集*/
    private T records;

    public Page () {}

    /**
     * 要想使用此类，必须提供2个参数
     *
     * @param startIndex  开始记录数
     * @param totalRecords 总记录条数
     */
    public Page(int startIndex, int pageSize ,int totalRecords) {
        this.startIndex = startIndex;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
        //计算开始记录索引
        this.currentPage = (startIndex/pageSize)+1;
        //计算总页数
        this.totalPageCount = totalRecords % pageSize == 0 ? totalRecords / pageSize : totalRecords / pageSize + 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public T getRecords() {
        return records;
    }

    public void setRecords(T records) {
        this.records = records;
    }
}
