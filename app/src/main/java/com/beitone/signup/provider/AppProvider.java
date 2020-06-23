package com.beitone.signup.provider;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.betatown.mobile.beitonelibrary.http.BaseProvider;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;

public class AppProvider extends BaseProvider {

    public static void loadProject(Context context, OnJsonCallBack onJsonCallBack) {
        post(context, "/project/getProjects.htm", onJsonCallBack);
    }


    public static void getProjectTeams(Context context,
                                       String projectId, OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("b_project_id", projectId);
        post(context, "/projectteam/getProjectTeams.htm", map, onJsonCallBack);
    }


    public static void getWorkTypeList(Context context, OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("key", "B_WORKER_TYPE_OF_WORK");
        post(context, "/usysparam/getListByKey.htm", map, onJsonCallBack);
    }


    public static void uploadImage(Context context, List<File> files,
                                   String kind, String subkind, OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("kind", kind);
        map.put("subkind", subkind);
        postFiles(context, files, "/files/doSave.htm", map, onJsonCallBack);
    }


    public static void loadAppIndexData(Context context, OnJsonCallBack onJsonCallBack) {
        post(context, "/article/getAppIndexData.htm", onJsonCallBack);
    }

    public static void loadAppIndexArticle(Context context,
                                           String type, int page, OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("page", String.valueOf(page));
        post(context, "/article/getMoreArticle.htm", map, onJsonCallBack);
    }

}
