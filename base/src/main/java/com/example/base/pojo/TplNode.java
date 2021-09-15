package com.example.base.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("模板节点")
public class TplNode {
    @ApiModelProperty("模板id")
    private String tplid;
    @ApiModelProperty("模板名称")
    private String tplname;
    @ApiModelProperty("创建时间")
    private Date createtime;
    @ApiModelProperty("修改时间")
    private Date updatetime;
    @ApiModelProperty("模板类型")
    private String tpltype;
    @ApiModelProperty("是否为叶子节点")
    private Boolean isfile;
    @ApiModelProperty("父级id")
    private String parentid;
    @ApiModelProperty("文件存储位置")
    private String filepath;
    @ApiModelProperty("文件名称")
    private String filename;

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

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "TplNode{" +
                "tplid='" + tplid + '\'' +
                ", tplname='" + tplname + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", tpltype='" + tpltype + '\'' +
                ", isfile=" + isfile +
                ", parentid='" + parentid + '\'' +
                '}';
    }
}