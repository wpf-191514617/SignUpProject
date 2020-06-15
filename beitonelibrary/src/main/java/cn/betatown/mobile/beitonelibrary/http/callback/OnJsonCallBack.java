package cn.betatown.mobile.beitonelibrary.http.callback;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.StringReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;

import cn.betatown.mobile.beitonelibrary.http.BaseResponse;

/**
 * Created by Administrator on 2018/1/9.
 * <p>
 * <p>
 * 网络请求返回 json
 */

public abstract class OnJsonCallBack<M> extends OnHttpCallBack<M> {

    @Override
    public M parseNetResponse(String response) {
        try {
            Type type = this.getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type beanType = parameterizedType.getActualTypeArguments()[0];
                if (beanType == String.class) {
                    return (M) response;
                } else {
                    JsonReader jsonReader = new JsonReader(new StringReader(response));
                    jsonReader.setLenient(true);
                    Gson gson =
                            new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                    return gson.fromJson(jsonReader, beanType);
                }
            } else {
                return (M) response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BaseResponse getResponseGenericity(String result) {
        BaseResponse response = new BaseResponse();
        try {
            JSONObject jsonObject = new JSONObject(result);
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key.equals("code")) {
                    response.code = jsonObject.optString("code");
                } else if (key.equals("msg")) {
                    response.msg = jsonObject.getString("msg");
                } else if (key.equals("data")) {
                    response.data = jsonObject.getString("data");
                } else if (key.equals("time")) {
                    response.time = jsonObject.getLong("time");
                } else if (key.equals("sessionId")){
                    response.sessionId = jsonObject.getString("sessionId");
                }
            }
        } catch (Exception e) {
            response.code = "-1";
            response.msg = ("服务端异常");
        }
        return response;
    }
}
