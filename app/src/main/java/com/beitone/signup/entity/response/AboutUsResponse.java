package com.beitone.signup.entity.response;

public class AboutUsResponse {


    /**
     * phone : 400-080-0800
     * weixin : 406603460
     * email : 635104032@qq.com
     * web : www.houchi.com
     * version : {"updateDesc":"优复已知Bug。","id":26,"createTime":"2020-04-27 11:00:34","updateUrl":"https://www.beitone.com/upload_file_dir/apk/beitone.apk","isUpgrade":"1","versionCode":40,"showtime":"2020-04-27 11:00:21","versionType":"1","versionName":"v3.1.3"}
     */

    private String phone;
    private String weixin;
    private String email;
    private String web;
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

    public VersionBean getVersion() {
        return version;
    }

    public void setVersion(VersionBean version) {
        this.version = version;
    }

    public static class VersionBean {
        /**
         * updateDesc : 优复已知Bug。
         * id : 26
         * createTime : 2020-04-27 11:00:34
         * updateUrl : https://www.beitone.com/upload_file_dir/apk/beitone.apk
         * isUpgrade : 1
         * versionCode : 40
         * showtime : 2020-04-27 11:00:21
         * versionType : 1
         * versionName : v3.1.3
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
