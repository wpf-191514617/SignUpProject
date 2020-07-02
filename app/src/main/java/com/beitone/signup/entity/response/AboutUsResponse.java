package com.beitone.signup.entity.response;

public class AboutUsResponse {


    /**
     * phone : 400-080-0800
     * weixin : 406603460
     * en_copyright : Copyright © 2020, First branch of Sinohydro bureau 4,. Ltd. All Rights Reserved.
     * email : 635104032@qq.com
     * web : www.houchi.com
     * cn_copyright : © 2020 版权归中国水电四局第一分局所有
     * version : {"updateDesc":"修正部分显示问题","id":2,"createTime":"2016-03-02 14:58:28","updateUrl":"https://m.beitone.com/upload_file_dir/apk/beitone.apk","isUpgrade":"2","versionCode":1,"showtime":"","versionType":"1","versionName":"V1.0.2"}
     */

    private String phone;
    private String weixin;
    private String en_copyright;
    private String email;
    private String web;
    private String cn_copyright;
    private VersionBean version;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getEn_copyright() {
        return en_copyright;
    }

    public void setEn_copyright(String en_copyright) {
        this.en_copyright = en_copyright;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getCn_copyright() {
        return cn_copyright;
    }

    public void setCn_copyright(String cn_copyright) {
        this.cn_copyright = cn_copyright;
    }

    public VersionBean getVersion() {
        return version;
    }

    public void setVersion(VersionBean version) {
        this.version = version;
    }

    public static class VersionBean {
        /**
         * updateDesc : 修正部分显示问题
         * id : 2
         * createTime : 2016-03-02 14:58:28
         * updateUrl : https://m.beitone.com/upload_file_dir/apk/beitone.apk
         * isUpgrade : 2
         * versionCode : 1
         * showtime :
         * versionType : 1
         * versionName : V1.0.2
         */

        private String updateDesc;
        private int id;
        private String createTime;
        private String updateUrl;
        private String isUpgrade;
        private int versionCode;
        private String showtime;
        private String versionType;
        private String versionName;

        public String getUpdateDesc() {
            return updateDesc;
        }

        public void setUpdateDesc(String updateDesc) {
            this.updateDesc = updateDesc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateUrl() {
            return updateUrl;
        }

        public void setUpdateUrl(String updateUrl) {
            this.updateUrl = updateUrl;
        }

        public String getIsUpgrade() {
            return isUpgrade;
        }

        public void setIsUpgrade(String isUpgrade) {
            this.isUpgrade = isUpgrade;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getShowtime() {
            return showtime;
        }

        public void setShowtime(String showtime) {
            this.showtime = showtime;
        }

        public String getVersionType() {
            return versionType;
        }

        public void setVersionType(String versionType) {
            this.versionType = versionType;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }
    }
}
