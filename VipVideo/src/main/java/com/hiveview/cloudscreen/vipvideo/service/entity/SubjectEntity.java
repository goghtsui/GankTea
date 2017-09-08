package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @author xieyi
 * @ClassName SubjectEntity
 * @Description 单个专题实体
 * @date 2014-9-8 下午5:43:51
 */
public class SubjectEntity extends HiveviewBaseEntity {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;


    private String subjectDesc; //专题描述
    private Integer subjectId; //专题id
    private String subjectName; //专题名字
    private Integer subjectSize; //专题内容大小
    private CornerEntity cornerContentApiVo;

    private String apkBagName;

    public String getApkBagName() {
        return apkBagName;
    }

    public void setApkBagName(String apkBagName) {
        this.apkBagName = apkBagName;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getSubjectVerBgImg() {
        return subjectVerBgImg;
    }

    public void setSubjectVerBgImg(String subjectVerBgImg) {
        this.subjectVerBgImg = subjectVerBgImg;
    }

    public String getSubjectVerPic() {
        return subjectVerPic;
    }

    public void setSubjectVerPic(String subjectVerPic) {
        this.subjectVerPic = subjectVerPic;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    private String apkName;
    private String createTime;
    private String imgSize;
    private boolean isOnline;
    private String seq;
    private String subjectVerBgImg; //专题背景图片(竖图)
    private String subjectVerPic; //专题图片(竖图)
    private String templateId;
    private String updateTime;

    private String subjectHorBgImg; //专题背景图片(横图)
    private String subjectHorPic; //专题图片(横图)


    public CornerEntity getCornerContentApiVo() {
        return cornerContentApiVo;
    }

    public void setCornerContentApiVo(CornerEntity cornerContentApiVo) {
        this.cornerContentApiVo = cornerContentApiVo;
    }

    public String getSubjectDesc() {
        return subjectDesc;
    }

    public void setSubjectDesc(String subjectDesc) {
        this.subjectDesc = subjectDesc;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getSubjectSize() {
        return subjectSize;
    }

    public void setSubjectSize(Integer subjectSize) {
        this.subjectSize = subjectSize;
    }

    public String getSubjectHorBgImg() {
        return subjectHorBgImg;
    }

    public void setSubjectHorBgImg(String subjectHorBgImg) {
        this.subjectHorBgImg = subjectHorBgImg;
    }

    public String getSubjectHorPic() {
        return subjectHorPic;
    }

    public void setSubjectHorPic(String subjectHorPic) {
        this.subjectHorPic = subjectHorPic;
    }
}
