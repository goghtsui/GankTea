package com.hiveview.cloudscreen.vipvideo.service.entity;

public class VideoChannelEntity extends HiveviewBaseEntity {

    /**
     * @Fields serialVersionUID:TODO
     */
    private static final long serialVersionUID = -3105659021730326533L;

    private Integer id;
    private String channelName;
    private Integer channelType;
    private Integer showCategory;
    private Integer isMultichip;
    private Integer isHasDetail;
    private Integer isHorizontal;
    private Integer isSpecific;
    private Integer parentCid;
    private Integer parentCtype;
    private String parentApkName;

    //布局参数
    private int size = 1;
    private int row;//行
    private int col;//列
    private boolean isLast;//时候行尾

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getIsMultichip() {
        return null != isMultichip ? isMultichip : 0;
    }

    public void setIsMultichip(int isMultichip) {
        this.isMultichip = isMultichip;
    }

    public int getIsHasDetail() {
        return null != isHasDetail ? isHasDetail : 0;
    }

    public void setIsHasDetail(int isHasDetail) {
        this.isHasDetail = isHasDetail;
    }

    public int getIsHorizontal() {
        return isHorizontal;
    }

    public void setIsHorizontal(int isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    public int getIsSpecific() {
        return null != isSpecific ? isSpecific : 0;
    }

    public void setIsSpecific(int isSpecific) {
        this.isSpecific = isSpecific;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public int getShowCategory() {
        return null != showCategory ? showCategory : 0;
    }

    public void setShowCategory(int showCategory) {
        this.showCategory = showCategory;
    }

    public Integer getParentCid() {
        return parentCid;
    }

    public void setParentCid(Integer parentCid) {
        this.parentCid = parentCid;
    }

    public Integer getParentCtype() {
        return parentCtype;
    }

    public void setParentCtype(Integer parentCtype) {
        this.parentCtype = parentCtype;
    }

    public String getParentApkName() {
        return parentApkName;
    }

    public void setParentApkName(String parentApkName) {
        this.parentApkName = parentApkName;
    }
}
