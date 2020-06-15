package cn.betatown.mobile.beitonelibrary.http.callback;

import cn.betatown.mobile.beitonelibrary.http.BaseResponse;

/**
 * Created by Administrator on 2018/1/9.
 *
 * 网络请求返回
 *
 */

public abstract class OnHttpCallBack<M> {


    public abstract void onResult(M data);

    public void onError(String msg){}

    public void onFailed(String msg){}

    public abstract M parseNetResponse(String response);

    public abstract BaseResponse getResponseGenericity(String result);

    public void onNetError(){

    }

}
