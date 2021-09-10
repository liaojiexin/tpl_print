package com.example.base.pojo;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class PageParam {

    private int pageSize;   //每页大小

    private int pageNum;    //当前页数

    private long allNum;     //全部多少条信息

    private List<?> content;    //数据模型

    public List<?> getContent() {
        return content;
    }

    public void setContent(List<?> content) {
        this.content = content;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public long getAllNum() {
        return allNum;
    }

    public void setAllNum(long allNum) {
        this.allNum = allNum;
    }
}
