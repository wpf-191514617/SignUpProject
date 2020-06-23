package com.beitone.signup.entity.response;

public class UploadFileResponse {


    /**
     * subkind : card_photo
     * id : id9141270789
     * createtime : 2020-06-23 10:51:31
     * memo :
     * name : 1592880665120826.jpeg
     * path : /upload/2020-06-23/a18c08ae-f8b4-4b37-9386-5e6a72c28eab.jpeg
     * belongingid :
     * url :
     * kind : b_worker
     */

    private String subkind;
    private String id;
    private String createtime;
    private String memo;
    private String name;
    private String path;
    private String belongingid;
    private String url;
    private String kind;

    public String getSubkind() {
        return subkind;
    }

    public void setSubkind(String subkind) {
        this.subkind = subkind;
    }

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBelongingid() {
        return belongingid;
    }

    public void setBelongingid(String belongingid) {
        this.belongingid = belongingid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
