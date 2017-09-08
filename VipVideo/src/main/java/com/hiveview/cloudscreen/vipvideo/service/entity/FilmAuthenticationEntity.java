package com.hiveview.cloudscreen.vipvideo.service.entity;

import java.util.List;

public class FilmAuthenticationEntity extends HiveviewBaseEntity {
    private String code;
    private String backgroundPic;
    private String stringTime;
    private boolean success;
    private List<OnDemandResultEntity> dianboList;

    public String getBackgroundPic() {
        return backgroundPic;
    }

    public void setBackgroundPic(String backgroundPic) {
        this.backgroundPic = backgroundPic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStringTime() {
        return stringTime;
    }

    public void setStringTime(String stringTime) {
        this.stringTime = stringTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<OnDemandResultEntity> getDianboList() {
        return dianboList;
    }

    public void setDianboList(List<OnDemandResultEntity> dianboList) {
        this.dianboList = dianboList;
    }
}
