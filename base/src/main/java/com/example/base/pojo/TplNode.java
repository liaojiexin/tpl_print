package com.example.base.pojo;

import java.util.Date;

public class TplNode {
    //模板id
    private String tplid;
    //模板名称
    private String tplname;
    //创建时间
    private Date createtime;
    //修改时间
    private Date updatetime;
    //模板类型
    private String tpltype;
    //是文件还是文件夹
    private Boolean isfile;
    //父级id
    private String parentid;

    public String getTplid() {
        return tplid;
    }

    public void setTplid(String tplid) {
        this.tplid = tplid == null ? null : tplid.trim();
    }

    public String getTplname() {
        return tplname;
    }

    public void setTplname(String tplname) {
        this.tplname = tplname == null ? null : tplname.trim();
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

    public String getTpltype() {
        return tpltype;
    }

    public void setTpltype(String tpltype) {
        this.tpltype = tpltype == null ? null : tpltype.trim();
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
}