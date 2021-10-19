package com.example.base.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@ApiModel("分页")
public class PageParam {
    @ApiModelProperty("每页大小")
    private int pageSize;   //每页大小
    @ApiModelProperty("当前页数")
    private int pageNum;    //当前页数
    @ApiModelProperty("全部信息数")
    private long total;     //全部多少条信息
    @ApiModelProperty("当前页内容")
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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
