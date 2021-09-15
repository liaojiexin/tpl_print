package com.example.base.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("实例节点")
public class CaseNode {
    @ApiModelProperty("实例id")
    private String caseid;
    @ApiModelProperty("实例名称")
    private String casename;
    @ApiModelProperty("创建时间")
    private Date createtime;
    @ApiModelProperty("修改时间")
    private Date updatetime;
    @ApiModelProperty("使用模板id")
    private String tplid;
    @ApiModelProperty("是否为叶子节点")
    private Boolean isfile;
    @ApiModelProperty("父级id")
    private String parentid;
    @ApiModelProperty("实例内容")
    private String filecontent;

    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid == null ? null : caseid.trim();
    }

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getTplid() {
        return tplid;
    }

    public void setTplid(String tplid) {
        this.tplid = tplid == null ? null : tplid.trim();
    }

    public Boolean getIsfile() {
        return isfile;
    }

    public void setIsfile(Boolean isfile) {
        this.isfile = isfile;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getFilecontent() {
        return filecontent;
    }

    public void setFilecontent(String filecontent) {
        this.filecontent = filecontent == null ? null : filecontent.trim();
    }

    @Override
    public String toString() {
        return "CaseNode{" +
                "caseid='" + caseid + '\'' +
                ", casename='" + casename + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", tplid='" + tplid + '\'' +
                ", isfile=" + isfile +
                ", parentid='" + parentid + '\'' +
                ", filecontent='" + filecontent + '\'' +
                '}';
    }
}