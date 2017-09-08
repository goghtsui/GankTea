package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/12/29
 * @Description
 */
public class MessageEntity extends HiveviewBaseEntity {
    private String messageDesc;
    private String pic;

    public String getMessageDesc() {
        return messageDesc;
    }

    public void setMessageDesc(String messageDesc) {
        this.messageDesc = messageDesc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
