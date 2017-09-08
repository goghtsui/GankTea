package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @author xieyi
 * @ClassName RecommendListEntity
 * @Description 点播首页推荐位实体
 * @date 2014-9-1 下午2:15:08
 */
public class RecommendListEntity extends HiveviewBaseEntity {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer contentId;
    private Integer contentType;
    private Integer position;
    private String contentFocus;
    private String contentName;
    private String contentImg;
    private String contentUpdate;
    private Integer contentTotal;
    private Integer isTxtShow;
    private Integer seq;
    private Integer isEffective;
    private Long createTime;
    private Long updateTime;
    private Integer cid;
    private String cname;
    private Integer videoId;
    private String cpId;

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    private CornerEntity cornerContentApiVo;

    public CornerEntity getCornerContentApiVo() {
        return cornerContentApiVo;
    }

    public void setCornerContentApiVo(CornerEntity cornerContentApiVo) {
        this.cornerContentApiVo = cornerContentApiVo;
    }

    public Integer getCid() {
        if (null == cid) {
            return 0;
        }
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getId() {
        if (null == id) {
            return 0;
        }
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContentTotal() {
        if (null == contentTotal) {
            return 0;
        }
        return contentTotal;
    }

    public void setContentTotal(Integer contentTotal) {
        this.contentTotal = contentTotal;
    }

    public Integer getSeq() {
        if (null == seq) {
            return 0;
        }
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getIsEffective() {
        if (null == isEffective) {
            return 0;
        }
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }

    public Long getCreateTime() {
        if (null == createTime) {
            return 0L;
        }
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        if (null == updateTime) {
            return 0L;
        }
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getContentType() {
        if (null == contentType) {
            return 0;
        }
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getContentId() {
        if (null == contentId) {
            return 0;
        }
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getPosition() {
        if (null == position) {
            return 0;
        }
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getContentFocus() {
        return contentFocus;
    }

    public void setContentFocus(String contentFocus) {
        this.contentFocus = contentFocus;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public String getContentUpdate() {
        return contentUpdate;
    }

    public void setContentUpdate(String content_update) {
        this.contentUpdate = content_update;
    }

    public Integer getIsTxtShow() {
        if (null == isTxtShow) {
            return 0;
        }
        return isTxtShow;
    }

    public void setIsTxtShow(Integer is_txt_show) {
        this.isTxtShow = is_txt_show;
    }

}
