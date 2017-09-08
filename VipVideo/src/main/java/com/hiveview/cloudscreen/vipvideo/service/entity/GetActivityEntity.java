package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * Created by wangbei on 2016/10/31.
 */
public class GetActivityEntity extends HiveviewBaseEntity {

    private Integer id;         		//自增ID
    private String name;         		//VIP活动名称
    private String desc;         		//VIP活动信息
    private String pic;         		//VIP活动图片
    private Integer conditionType;     	//领取条件：1无条件领取，2关注微信领取，3绑定手机领取，4关注微信并绑定手机领取
    private Integer activityType;      	// 活动类型：1固定有效时长，2固定截止日期
    private Integer durationYear;      	// 固定有效时长-年
    private Integer durationMonth;     	//固定有效时长-月
    private Integer durationDay;       	//固定有效时长-天
    private String durationEnd;        	//固定截止日期
    private Integer duration; 			//VIP活动有效时长

    private Integer userRange; //用户范围：1全部用户，2某日之前的用户，3某日之后的用户，4某日和某日之间的用户(取之前和之后这段时间)
    private String beforeDate; // 某日之前
    private String afterDate; // 某日之后

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getConditionType() {
        return conditionType;
    }

    public void setConditionType(Integer conditionType) {
        this.conditionType = conditionType;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Integer getDurationYear() {
        return durationYear;
    }

    public void setDurationYear(Integer durationYear) {
        this.durationYear = durationYear;
    }

    public Integer getDurationMonth() {
        return durationMonth;
    }

    public void setDurationMonth(Integer durationMonth) {
        this.durationMonth = durationMonth;
    }

    public Integer getDurationDay() {
        return durationDay;
    }

    public void setDurationDay(Integer durationDay) {
        this.durationDay = durationDay;
    }

    public String getDurationEnd() {
        return durationEnd;
    }

    public void setDurationEnd(String durationEnd) {
        this.durationEnd = durationEnd;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getUserRange() {
        return userRange;
    }

    public void setUserRange(Integer userRange) {
        this.userRange = userRange;
    }

    public String getBeforeDate() {
        return beforeDate;
    }

    public void setBeforeDate(String beforeDate) {
        this.beforeDate = beforeDate;
    }

    public String getAfterDate() {
        return afterDate;
    }

    public void setAfterDate(String afterDate) {
        this.afterDate = afterDate;
    }
}
