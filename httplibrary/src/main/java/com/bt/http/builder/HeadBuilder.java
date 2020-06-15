package com.bt.http.builder;


import com.bt.http.OkHttpUtils;
import com.bt.http.request.OtherRequest;
import com.bt.http.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
