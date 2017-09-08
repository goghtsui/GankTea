package com.hiveview.cloudscreen.vipvideo.service.entity;

public class ServiceTimeEntity extends HiveviewBaseEntity {
    private String code;
    private Long longTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getLongTime() {
        return longTime;
    }

    public void setLongTime(Long longTime) {
        this.longTime = longTime;
    }
}
