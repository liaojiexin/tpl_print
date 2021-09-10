package com.example.base.pojo;

import java.util.Date;

public class CaseNode {
    //实例id
    private String caseid;
    //创建时间
    private Date createtime;
    //修改时间
    private Date updatetime;
    //使用的模板id
    private String tplid;
    //是文件还是文件夹
    private Boolean isfile;
    //父级id
    private String parentid;
    //文件内容
    private String filecontent;

    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid == null ? null : caseid.trim();
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
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", tplid='" + tplid + '\'' +
                ", isfile=" + isfile +
                ", parentid='" + parentid + '\'' +
                ", filecontent='" + filecontent + '\'' +
                '}';
    }
}