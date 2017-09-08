package com.gogh.fortest.test;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/9/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/9/2016 do fisrt create. </li>
 */
public class ResponseEntity implements Serializable {

    /**
     * data : {"code":"N000000","codeV":null,"count":-1,"desc":"","longTime":1481271217381,"message":"","msg":"","result":[{"contentList":[{"contentId":null,"entranceContentFocus":"年度文艺神作","entranceContentImage":"http://pic.kor.domybox.com/2016/11/11/18/11/27/1478855487662.jpg","entranceContentName":"路边野餐","seq":1},{"contentId":null,"entranceContentFocus":"包贝尔痴情怂包备胎转正","entranceContentImage":"http://pic.kor.domybox.com/2016/09/01/10/39/25/1472693965486.jpg","entranceContentName":"陆垚知马俐","seq":2}],"entranceAppAction":"com.hiveview.premiere","entranceAppName":"com.hiveview.premiere","entranceAppVersion":"","entranceId":1,"entranceName":"极清首映","entranceType":0,"entranceUrl":"","installStyle":0,"seq":1},{"contentList":[{"contentId":null,"entranceContentFocus":"汤唯吴秀波再续情缘","entranceContentImage":"http://pic.kor.domybox.com/2016/11/11/18/12/36/1478855556465.jpg","entranceContentName":"北京遇上西雅图2","seq":1},{"contentId":null,"entranceContentFocus":"海清 黄磊变荧屏夫妻","entranceContentImage":"http://pic.kor.domybox.com/2016/12/09/10/45/22/1481247922271.jpg","entranceContentName":"小别离","seq":2},{"contentId":null,"entranceContentFocus":"最吸粉的综艺 鹿晗厨艺首秀","entranceContentImage":"http://pic.kor.domybox.com/2016/08/19/20/31/47/1471663907262.jpg","entranceContentName":"我去上学啦第二季","seq":3}],"entranceAppAction":"com.hiveview.cloudscreen.vipvideo","entranceAppName":"com.hiveview.cloudscreen.vipvideo","entranceAppVersion":"","entranceId":2,"entranceName":"大麦影视","entranceType":0,"entranceUrl":"","installStyle":0,"seq":2},{"contentList":[{"contentId":null,"entranceContentFocus":"热辣资讯 应有尽有","entranceContentImage":"http://pic.kor.domybox.com/2016/09/06/11/55/47/1473130547326.jpg","entranceContentName":"电视服务","seq":2}],"entranceAppAction":"com.hiveview.cloudscreen.videolive","entranceAppName":"com.hiveview.cloudscreen.videolive","entranceAppVersion":"","entranceId":4,"entranceName":"直播频道","entranceType":0,"entranceUrl":"","installStyle":0,"seq":3},{"contentList":[{"contentId":null,"entranceContentFocus":"哈利学语文","entranceContentImage":"http://pic.kor.domybox.com/2016/09/28/12/04/32/1475031872694.jpg","entranceContentName":"哈利学语文","seq":1},{"contentId":null,"entranceContentFocus":"欢众捕鱼疯狂版","entranceContentImage":"http://pic.kor.domybox.com/2016/09/28/11/05/31/1475028331160.jpg","entranceContentName":"欢众捕鱼疯狂版","seq":3}],"entranceAppAction":"com.hiveview.cloudscreen.appstore","entranceAppName":"com.hiveview.cloudscreen.appstore","entranceAppVersion":"","entranceId":3,"entranceName":"应用游戏","entranceType":0,"entranceUrl":"","installStyle":0,"seq":4},{"contentList":[{"contentId":null,"entranceContentFocus":"我的家庭学习平台","entranceContentImage":"http://pic.kor.domybox.com/2016/06/30/18/41/22/1467337282578.png","entranceContentName":"鹏云课堂","seq":1},{"contentId":null,"entranceContentFocus":"做好预习保持领先","entranceContentImage":"http://pic.kor.domybox.com/2016/09/01/11/13/01/1472695981710.jpg","entranceContentName":"鹏云课堂","seq":2}],"entranceAppAction":"com.peng.pengyun_domyboxintegration","entranceAppName":"com.peng.pengyun_domyboxintegration","entranceAppVersion":"2.6.0.3936","entranceId":7,"entranceName":"鹏云课堂","entranceType":1,"entranceUrl":"http://app.kor.domybox.com/2016/09/06/19/09/07/1473156547407.apk","installStyle":1,"seq":5}],"resultex":null,"startTime":null,"stringTime":"2016-12-09 17:13:37.381","success":true}
     */

    private DataBean data;

    public ResponseEntity() {
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataBean {
        /**
         * code : N000000
         * codeV : null
         * count : -1
         * desc :
         * longTime : 1481271217381
         * message :
         * msg :
         * result : [{"contentList":[{"contentId":null,"entranceContentFocus":"年度文艺神作","entranceContentImage":"http://pic.kor.domybox.com/2016/11/11/18/11/27/1478855487662.jpg","entranceContentName":"路边野餐","seq":1},{"contentId":null,"entranceContentFocus":"包贝尔痴情怂包备胎转正","entranceContentImage":"http://pic.kor.domybox.com/2016/09/01/10/39/25/1472693965486.jpg","entranceContentName":"陆垚知马俐","seq":2}],"entranceAppAction":"com.hiveview.premiere","entranceAppName":"com.hiveview.premiere","entranceAppVersion":"","entranceId":1,"entranceName":"极清首映","entranceType":0,"entranceUrl":"","installStyle":0,"seq":1},{"contentList":[{"contentId":null,"entranceContentFocus":"汤唯吴秀波再续情缘","entranceContentImage":"http://pic.kor.domybox.com/2016/11/11/18/12/36/1478855556465.jpg","entranceContentName":"北京遇上西雅图2","seq":1},{"contentId":null,"entranceContentFocus":"海清 黄磊变荧屏夫妻","entranceContentImage":"http://pic.kor.domybox.com/2016/12/09/10/45/22/1481247922271.jpg","entranceContentName":"小别离","seq":2},{"contentId":null,"entranceContentFocus":"最吸粉的综艺 鹿晗厨艺首秀","entranceContentImage":"http://pic.kor.domybox.com/2016/08/19/20/31/47/1471663907262.jpg","entranceContentName":"我去上学啦第二季","seq":3}],"entranceAppAction":"com.hiveview.cloudscreen.vipvideo","entranceAppName":"com.hiveview.cloudscreen.vipvideo","entranceAppVersion":"","entranceId":2,"entranceName":"大麦影视","entranceType":0,"entranceUrl":"","installStyle":0,"seq":2},{"contentList":[{"contentId":null,"entranceContentFocus":"热辣资讯 应有尽有","entranceContentImage":"http://pic.kor.domybox.com/2016/09/06/11/55/47/1473130547326.jpg","entranceContentName":"电视服务","seq":2}],"entranceAppAction":"com.hiveview.cloudscreen.videolive","entranceAppName":"com.hiveview.cloudscreen.videolive","entranceAppVersion":"","entranceId":4,"entranceName":"直播频道","entranceType":0,"entranceUrl":"","installStyle":0,"seq":3},{"contentList":[{"contentId":null,"entranceContentFocus":"哈利学语文","entranceContentImage":"http://pic.kor.domybox.com/2016/09/28/12/04/32/1475031872694.jpg","entranceContentName":"哈利学语文","seq":1},{"contentId":null,"entranceContentFocus":"欢众捕鱼疯狂版","entranceContentImage":"http://pic.kor.domybox.com/2016/09/28/11/05/31/1475028331160.jpg","entranceContentName":"欢众捕鱼疯狂版","seq":3}],"entranceAppAction":"com.hiveview.cloudscreen.appstore","entranceAppName":"com.hiveview.cloudscreen.appstore","entranceAppVersion":"","entranceId":3,"entranceName":"应用游戏","entranceType":0,"entranceUrl":"","installStyle":0,"seq":4},{"contentList":[{"contentId":null,"entranceContentFocus":"我的家庭学习平台","entranceContentImage":"http://pic.kor.domybox.com/2016/06/30/18/41/22/1467337282578.png","entranceContentName":"鹏云课堂","seq":1},{"contentId":null,"entranceContentFocus":"做好预习保持领先","entranceContentImage":"http://pic.kor.domybox.com/2016/09/01/11/13/01/1472695981710.jpg","entranceContentName":"鹏云课堂","seq":2}],"entranceAppAction":"com.peng.pengyun_domyboxintegration","entranceAppName":"com.peng.pengyun_domyboxintegration","entranceAppVersion":"2.6.0.3936","entranceId":7,"entranceName":"鹏云课堂","entranceType":1,"entranceUrl":"http://app.kor.domybox.com/2016/09/06/19/09/07/1473156547407.apk","installStyle":1,"seq":5}]
         * resultex : null
         * startTime : null
         * stringTime : 2016-12-09 17:13:37.381
         * success : true
         */

        private String code;
        private Object codeV;
        private int count;
        private String desc;
        private long longTime;
        private String message;
        private String msg;
        private Object resultex;
        private Object startTime;
        private String stringTime;
        private boolean success;
        private List<ResultBean> result;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Object getCodeV() {
            return codeV;
        }

        public void setCodeV(Object codeV) {
            this.codeV = codeV;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public long getLongTime() {
            return longTime;
        }

        public void setLongTime(long longTime) {
            this.longTime = longTime;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getResultex() {
            return resultex;
        }

        public void setResultex(Object resultex) {
            this.resultex = resultex;
        }

        public Object getStartTime() {
            return startTime;
        }

        public void setStartTime(Object startTime) {
            this.startTime = startTime;
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

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * contentList : [{"contentId":null,"entranceContentFocus":"年度文艺神作","entranceContentImage":"http://pic.kor.domybox.com/2016/11/11/18/11/27/1478855487662.jpg","entranceContentName":"路边野餐","seq":1},{"contentId":null,"entranceContentFocus":"包贝尔痴情怂包备胎转正","entranceContentImage":"http://pic.kor.domybox.com/2016/09/01/10/39/25/1472693965486.jpg","entranceContentName":"陆垚知马俐","seq":2}]
             * entranceAppAction : com.hiveview.premiere
             * entranceAppName : com.hiveview.premiere
             * entranceAppVersion :
             * entranceId : 1
             * entranceName : 极清首映
             * entranceType : 0
             * entranceUrl :
             * installStyle : 0
             * seq : 1
             */

            private String entranceAppAction;
            private String entranceAppName;
            private String entranceAppVersion;
            private int entranceId;
            private String entranceName;
            private int entranceType;
            private String entranceUrl;
            private int installStyle;
            private int seq;
            private List<ContentListBean> contentList;

            public String getEntranceAppAction() {
                return entranceAppAction;
            }

            public void setEntranceAppAction(String entranceAppAction) {
                this.entranceAppAction = entranceAppAction;
            }

            public String getEntranceAppName() {
                return entranceAppName;
            }

            public void setEntranceAppName(String entranceAppName) {
                this.entranceAppName = entranceAppName;
            }

            public String getEntranceAppVersion() {
                return entranceAppVersion;
            }

            public void setEntranceAppVersion(String entranceAppVersion) {
                this.entranceAppVersion = entranceAppVersion;
            }

            public int getEntranceId() {
                return entranceId;
            }

            public void setEntranceId(int entranceId) {
                this.entranceId = entranceId;
            }

            public String getEntranceName() {
                return entranceName;
            }

            public void setEntranceName(String entranceName) {
                this.entranceName = entranceName;
            }

            public int getEntranceType() {
                return entranceType;
            }

            public void setEntranceType(int entranceType) {
                this.entranceType = entranceType;
            }

            public String getEntranceUrl() {
                return entranceUrl;
            }

            public void setEntranceUrl(String entranceUrl) {
                this.entranceUrl = entranceUrl;
            }

            public int getInstallStyle() {
                return installStyle;
            }

            public void setInstallStyle(int installStyle) {
                this.installStyle = installStyle;
            }

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }

            public List<ContentListBean> getContentList() {
                return contentList;
            }

            public void setContentList(List<ContentListBean> contentList) {
                this.contentList = contentList;
            }

            public static class ContentListBean {
                /**
                 * contentId : null
                 * entranceContentFocus : 年度文艺神作
                 * entranceContentImage : http://pic.kor.domybox.com/2016/11/11/18/11/27/1478855487662.jpg
                 * entranceContentName : 路边野餐
                 * seq : 1
                 */

                private Object contentId;
                private String entranceContentFocus;
                private String entranceContentImage;
                private String entranceContentName;
                private int seq;

                public Object getContentId() {
                    return contentId;
                }

                public void setContentId(Object contentId) {
                    this.contentId = contentId;
                }

                public String getEntranceContentFocus() {
                    return entranceContentFocus;
                }

                public void setEntranceContentFocus(String entranceContentFocus) {
                    this.entranceContentFocus = entranceContentFocus;
                }

                public String getEntranceContentImage() {
                    return entranceContentImage;
                }

                public void setEntranceContentImage(String entranceContentImage) {
                    this.entranceContentImage = entranceContentImage;
                }

                public String getEntranceContentName() {
                    return entranceContentName;
                }

                public void setEntranceContentName(String entranceContentName) {
                    this.entranceContentName = entranceContentName;
                }

                public int getSeq() {
                    return seq;
                }

                public void setSeq(int seq) {
                    this.seq = seq;
                }

                @Override
                public String toString() {
                    return "ContentListBean{" +
                            "contentId=" + contentId +
                            ", entranceContentFocus='" + entranceContentFocus + '\'' +
                            ", entranceContentImage='" + entranceContentImage + '\'' +
                            ", entranceContentName='" + entranceContentName + '\'' +
                            ", seq=" + seq +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "ResultBean{" +
                        "contentList=" + contentList +
                        ", entranceAppAction='" + entranceAppAction + '\'' +
                        ", entranceAppName='" + entranceAppName + '\'' +
                        ", entranceAppVersion='" + entranceAppVersion + '\'' +
                        ", entranceId=" + entranceId +
                        ", entranceName='" + entranceName + '\'' +
                        ", entranceType=" + entranceType +
                        ", entranceUrl='" + entranceUrl + '\'' +
                        ", installStyle=" + installStyle +
                        ", seq=" + seq +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "code='" + code + '\'' +
                    ", codeV=" + codeV +
                    ", count=" + count +
                    ", desc='" + desc + '\'' +
                    ", longTime=" + longTime +
                    ", message='" + message + '\'' +
                    ", msg='" + msg + '\'' +
                    ", resultex=" + resultex +
                    ", startTime=" + startTime +
                    ", stringTime='" + stringTime + '\'' +
                    ", success=" + success +
                    ", result=" + result +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "data=" + data +
                '}';
    }
}
