package com.beitone.signup.entity.response;

public class ArticleResponse {


    /**
     * id : 4
     * createtime : 2020-06-01 11:30:00
     * title : 46个Excel表格高级教程
     * must_study_time : 0
     * status : 2
     * img : /images/test/4.jpg
     * study_user_num : 0
     * is_study : 2
     * video_duration : null
     * video : /images/test/1.mp4
     * context_type : 2
     */

    private String id;
    private String createtime;
    private String title;
    private String must_study_time;
    private String status;
    private String img;
    private String study_user_num;
    private String is_study;
    private String video_duration;
    private String video;
    private String context_type;

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

    public String getMust_study_time() {
        return must_study_time;
    }

    public void setMust_study_time(String must_study_time) {
        this.must_study_time = must_study_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStudy_user_num() {
        return study_user_num;
    }

    public void setStudy_user_num(String study_user_num) {
        this.study_user_num = study_user_num;
    }

    public String getIs_study() {
        return is_study;
    }

    public void setIs_study(String is_study) {
        this.is_study = is_study;
    }

    public String getVideo_duration() {
        return video_duration;
    }

    public void setVideo_duration(String video_duration) {
        this.video_duration = video_duration;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getContext_type() {
        return context_type;
    }

    public void setContext_type(String context_type) {
        this.context_type = context_type;
    }
}
