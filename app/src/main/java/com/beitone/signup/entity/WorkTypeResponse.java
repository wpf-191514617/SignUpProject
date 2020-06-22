package com.beitone.signup.entity;

public class WorkTypeResponse {


    /**
     * mname : 队伍负责人
     * neededitor : null
     * value : 1
     * keymemo : null
     * orderby : 1
     * keyname : 工种
     * key : B_WORKER_TYPE_OF_WORK
     * isuse : 是
     */

    private String mname;
    private String neededitor;
    private String value;
    private String keymemo;
    private int orderby;
    private String keyname;
    private String key;
    private String isuse;

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getNeededitor() {
        return neededitor;
    }

    public void setNeededitor(String neededitor) {
        this.neededitor = neededitor;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKeymemo() {
        return keymemo;
    }

    public void setKeymemo(String keymemo) {
        this.keymemo = keymemo;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    public String getKeyname() {
        return keyname;
    }

    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIsuse() {
        return isuse;
    }

    public void setIsuse(String isuse) {
        this.isuse = isuse;
    }
}
