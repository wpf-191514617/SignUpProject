package cn.betatown.mobile.beitonelibrary.http;

public class BaseResponse<T> {

    public String code;

    public String msg;

    public long time;

    public String sessionId;

    public T data;


}
