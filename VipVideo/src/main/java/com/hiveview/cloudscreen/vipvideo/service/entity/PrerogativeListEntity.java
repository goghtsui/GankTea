package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * Created by wangbei on 2015/11/17.
 */
public class PrerogativeListEntity extends HiveviewBaseEntity{
    private Integer id;
    //特权名称
    private String name;
    //状态 0未发布1已发布
    private Integer status;
    //是否展现在播放页 0否1是
    private Integer isShow;
    //顺序
    private Integer seq;
    //特权图片
    private String image;
    //创建时间
    private String createTime;
    //修改时间
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "PrerogativeListEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", isShow=" + isShow +
                ", seq=" + seq +
                ", image='" + image + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}