package cn.betatown.mobile.beitonelibrary.http.callback;

import cn.betatown.mobile.beitonelibrary.http.BaseResponse;

public abstract class OnStringCallback extends OnHttpCallBack {

    @Override
    public Object parseNetResponse(String response) {
        return null;
    }

    @Override
    public BaseResponse getResponseGenericity(String result) {
        return null;
    }
}
