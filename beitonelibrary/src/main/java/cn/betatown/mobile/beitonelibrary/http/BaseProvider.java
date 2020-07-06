package cn.betatown.mobile.beitonelibrary.http;

import android.text.TextUtils;

import com.bt.http.OkHttpUtils;
import com.bt.http.builder.GetBuilder;
import com.bt.http.builder.OkHttpRequestBuilder;
import com.bt.http.builder.PostFormBuilder;
import com.bt.http.builder.PostStringBuilder;
import com.bt.http.callback.BitmapCallback;
import com.bt.http.callback.StringCallback;
import com.bt.http.request.RequestCall;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import cn.betatown.mobile.beitonelibrary.adapter.AdapterUtil;
import cn.betatown.mobile.beitonelibrary.base.BaseApplication;
import cn.betatown.mobile.beitonelibrary.http.callback.OnHttpCallBack;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.GsonUtil;
import cn.betatown.mobile.beitonelibrary.util.Trace;
import okhttp3.Call;

public class BaseProvider {

    private static final String TAG = "btHttp";

    public static final int LIMIT = 20;

    //private static final String BaseUrl = "http://home.tx06.com:91";

    public static final String BaseUrl = "http://a.tx06.com:91/";

    protected BaseProvider() {
    }

    public static void get(Object tag, String url, OnHttpCallBack onHttpCallBack) {
        get(tag, url, null, onHttpCallBack);
    }

    protected static void get(Object tag, String url, Map<String, String> params,
                              OnHttpCallBack onHttpCallBack) {
        request(tag, OkHttpUtils.get(), url, params, onHttpCallBack);
    }

    protected static void post(Object tag, String url, OnHttpCallBack onHttpCallBack) {
        post(tag, url, null, onHttpCallBack);
    }

    protected static void post(Object tag, String url, Map<String, String> params,
                               OnHttpCallBack onHttpCallBack) {
        request(tag, OkHttpUtils.post(), url, params, onHttpCallBack);
    }

    protected static void postString(Object tag, String url, Map<String, String> params,
                                     OnHttpCallBack onHttpCallBack) {
        request(tag, OkHttpUtils.postString(), url, params, onHttpCallBack);
    }

    protected static void postFiles(Object tag, List<File> files, String url,
                                    Map<String, String> params,
                                    OnHttpCallBack onHttpCallBack) {
        PostFormBuilder formBuilder = OkHttpUtils.post();
        if (AdapterUtil.isListNotEmpty(files)) {
            for (File file : files) {
                formBuilder.addFile("file", file.getName(), file);
            }
        }
        request(tag, formBuilder, url, params, onHttpCallBack);
    }

    private static void request(Object tag, OkHttpRequestBuilder requestBuilder, String url,
                                Map<String, String> params, OnHttpCallBack onHttpCallBack) {
        try {
            if (tag != null) {
                requestBuilder.tag(tag);
            }

            if (TextUtils.isEmpty(url)) {
                throw new IllegalArgumentException("访问 url 不能为空");
            }

            requestBuilder.url(BaseUrl + url);
            requestBuilder.addHeader("User-Agent", "android-okhttp");
            if (!TextUtils.isEmpty(BaseApplication.getSession())){
                String session =
                        "JSESSIONID=" + BaseApplication.getSession()+ ";Domain=a.tx06.com;" +
                                "Path=/";
                requestBuilder.addHeader("Cookie", session);
                requestBuilder.addHeader("Referer", "http://a.tx06.com:91/");
            }

            if (params != null && params.size() > 0) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    if (params.get(key) == null) {
                        params.put(key, "");
                    } else {
                        params.put(key, String.valueOf(params.get(key)));
                    }
                }

                if (requestBuilder instanceof GetBuilder) {
                    ((GetBuilder) requestBuilder).params(params);
                } else if (requestBuilder instanceof PostFormBuilder) {
                    ((PostFormBuilder) requestBuilder).params(params);
                } else if (requestBuilder instanceof PostStringBuilder) {
                    ((PostStringBuilder) requestBuilder).content(GsonUtil.GsonString(params));
                }
            }
            execute(requestBuilder, onHttpCallBack);
        } catch (Exception e) {
            e.printStackTrace();
            onHttpCallBack.onError(e.getLocalizedMessage());
        }
    }


    private static void execute(final OkHttpRequestBuilder requestBuilder,
                                final OnHttpCallBack onHttpCallBack) {
        Trace.d("onHttp", "url---" + requestBuilder.getUrl());
        Trace.d("onHttp", "params---" + GsonUtil.GsonString(requestBuilder.getParams()));
        RequestCall requestCall = requestBuilder.build();
        requestCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (onHttpCallBack != null) {
                    if (e instanceof TimeoutException ||
                            e instanceof IOException) {
                        onHttpCallBack.onFailed("连接似乎有问题，请检查网络");
                        return;
                    }
                    onHttpCallBack.onError(e.getMessage());
                }
            }

            @Override
            public void onResponse(String result, int id) {
                if (onHttpCallBack == null) {
                    return;
                }

                if (onHttpCallBack instanceof OnJsonCallBack) {
                    BaseResponse response = onHttpCallBack.getResponseGenericity(result);

                    if (!TextUtils.isEmpty(response.sessionId)) {
                        BaseApplication.setSession(response.sessionId);
                    }

                    if (response != null && !TextUtils.isEmpty(response.code)) {
                        if (response.code.equals("200") || response.code.toLowerCase().equals("ok"
                        )) {
                            Object responseResult = response.data;
                            String res = null;
                            if (responseResult != null) {
                                res = responseResult.toString();
                            }
                            Trace.d("onHttp", "result---" + res);
                            Object obj = onHttpCallBack.parseNetResponse(res);
                            onHttpCallBack.onResult(obj);
                        } else {
                            if (requestBuilder.getUrl().contains("/accountBank/front/checkcredit" +
                                    ".html")) {
                                if (response.code.equals("1")) {
                                    onHttpCallBack.onResult("checkFailed");
                                    return;
                                }
                            }

                            onHttpCallBack.onFailed(response.msg);
                        }
                    } else {
                        onHttpCallBack.onFailed(response.msg);
                    }
                } else {
                    onHttpCallBack.onResult(result);
                }
            }
        });
    }


    protected static void getBitMap(Object tag, String url, Map<String, String> params,
                                    BitmapCallback onHttpCallBack) {
        try {
            OkHttpRequestBuilder requestBuilder = OkHttpUtils.post();

            if (tag != null) {
                requestBuilder.tag(tag);
            }
            if (TextUtils.isEmpty(url)) {
                throw new IllegalArgumentException("访问 url 不能为空");
            }

            requestBuilder.url(url);

            if (params != null && params.size() > 0) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    if (params.get(key) == null) {
                        params.put(key, "");
                    } else {
                        params.put(key, URLEncoder.encode(params.get(key), "UTF-8"));
                    }
                }

                if (requestBuilder instanceof GetBuilder) {
                    ((GetBuilder) requestBuilder).params(params);
                } else if (requestBuilder instanceof PostFormBuilder) {
                    ((PostFormBuilder) requestBuilder).params(params);
                } else if (requestBuilder instanceof PostStringBuilder) {

                }
            }

            getBitmap(requestBuilder, onHttpCallBack);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    protected static void getBitmap(OkHttpRequestBuilder requestBuilder,
                                    BitmapCallback bitmapCallback) {
        requestBuilder.build().execute(bitmapCallback);
    }


}
