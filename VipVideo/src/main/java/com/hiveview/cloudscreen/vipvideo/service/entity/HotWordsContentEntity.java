package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/12/24
 * @Description
 */
public class HotWordsContentEntity extends HiveviewBaseEntity {
    private String albumName;
    private String bigBackPic;
    private int chnId;
    private int contentType;
    private String cornerPic;
    private Double score;
    private int contentId;
    private int hotId;

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getBigBackPic() {
        return bigBackPic;
    }

    public void setBigBackPic(String bigBackPic) {
        this.bigBackPic = bigBackPic;
    }

    public int getChnId() {
        return chnId;
    }

    public void setChnId(int chnId) {
        this.chnId = chnId;
    }

    public String getCornerPic() {
        return cornerPic;
    }

    public void setCornerPic(String cornerPic) {
        this.cornerPic = cornerPic;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getHotId() {
        return hotId;
    }

    public void setHotId(int hotId) {
        this.hotId = hotId;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
}
