package com.beitone.signup.entity.response;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.util.List;

import cn.betatown.mobile.beitonelibrary.http.BaseProvider;

public class AppIndexDataResponse {


    private List<BannerBean> banner;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public static class BannerBean extends SimpleBannerInfo {
        /**
         * id : 352
         * createtime : 2020-04-26 16:39:29
         * title : 测试2
         * sort : 1
         * memo : 22222222222233333333333333
         * jumpurl : doOrder11
         * isdelete : 2
         * showtime : 2020-06-22 22:57:02
         * type : 1
         * url : /upload/2020-06-22/5dbfbb37-df09-4b3e-b79d-6a09a935b72c.jpg
         */

        private String id;
        private String createtime;
        private String title;
        private int sort;
        private String memo;
        private String jumpurl;
        private String isdelete;
        private String showtime;
        private String type;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getJumpurl() {
            return jumpurl;
        }

        public void setJumpurl(String jumpurl) {
            this.jumpurl = jumpurl;
        }

        public String getIsdelete() {
            return isdelete;
        }

        public void setIsdelete(String isdelete) {
            this.isdelete = isdelete;
        }

        public String getShowtime() {
            return showtime;
        }

        public void setShowtime(String showtime) {
            this.showtime = showtime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public Object getXBannerUrl() {
            return BaseProvider.BaseUrl + url;
        }
    }
}
