package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/1/8
 * @Description
 */
public class SubjectVideoSetVoEntity extends HiveviewBaseEntity {
    private int contentCategory;//1是点播，4是直播

    public int getContentCategory() {
        return contentCategory;
    }

    public void setContentCategory(int contentCategory) {
        this.contentCategory = contentCategory;
    }

    private String contentPic;
    private AlbumEntity videoSetVo;
    private SubjectLiveVoEntity liveVo;

    public void setLiveVo(SubjectLiveVoEntity liveVo) {
        this.liveVo = liveVo;
    }

    public SubjectLiveVoEntity getLiveVo() {
        return liveVo;
    }

    public String getContentPic() {
        return contentPic;
    }

    public void setContentPic(String contentPic) {
        this.contentPic = contentPic;
    }

    public AlbumEntity getVideoSetVo() {
        return videoSetVo;
    }

    public void setVideoSetVo(AlbumEntity videoSetVo) {
        this.videoSetVo = videoSetVo;
    }
}
