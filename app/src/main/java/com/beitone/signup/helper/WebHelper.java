package com.beitone.signup.helper;

import com.beitone.signup.entity.WebEntity;

import java.util.HashMap;

import cn.betatown.mobile.beitonelibrary.http.BaseProvider;

public class WebHelper {


    public static WebEntity getArticleDetails(String id) {
        WebEntity webEntity = new WebEntity();
        webEntity.head = new HashMap<>();
        webEntity.title = "文章详情";
        webEntity.url = BaseProvider.BaseUrl +  "/pro/html/other/media.html?id="+id;
        return webEntity;
    }

    public static WebEntity getCalendar() {
        WebEntity webEntity = new WebEntity();
        webEntity.head = new HashMap<>();
        webEntity.title = "";
        webEntity.url = BaseProvider.BaseUrl +  "/pro/html/calendar/calendar.html";
        return webEntity;
    }

    public static WebEntity getSalaryList() {
        WebEntity webEntity = new WebEntity();
        webEntity.head = new HashMap<>();
        webEntity.title = "工资上传";
        webEntity.url = BaseProvider.BaseUrl +  "/pro/html/list/salaryList.html";
        return webEntity;
    }

    public static WebEntity getQuite() {
        WebEntity webEntity = new WebEntity();
        webEntity.head = new HashMap<>();
        webEntity.title = "退场审核";
        webEntity.url = BaseProvider.BaseUrl +  "/pro/html/audit/audit_list.html";
        return webEntity;
    }



    public static WebEntity getItemAnalysis() {
        WebEntity webEntity = new WebEntity();
        webEntity.head = new HashMap<>();
        webEntity.title = "项目综合分析";
        webEntity.url = BaseProvider.BaseUrl +  "/pro/html/item/itemAnalysis.html";
        return webEntity;
    }

    public static WebEntity getItemAnalysis2() {
        WebEntity webEntity = new WebEntity();
        webEntity.head = new HashMap<>();
        webEntity.title = "项目综合分析";
        webEntity.url = BaseProvider.BaseUrl +  "/pro/html/item/itemAnalysis2.html";
        return webEntity;
    }


    public static WebEntity getItemAnalysisDetail(String id) {
        WebEntity webEntity = new WebEntity();
        webEntity.head = new HashMap<>();
        webEntity.title = "项目综合分析";
        webEntity.url = BaseProvider.BaseUrl +  "/pro/html/item/itemAnalysisDetail.html?id=" + id;
        return webEntity;
    }

    public static WebEntity getProject() {
        WebEntity webEntity = new WebEntity();
        webEntity.head = new HashMap<>();
        webEntity.title = "切换工地";
        webEntity.url = BaseProvider.BaseUrl +  "/pro/html/project/project.html";
        return webEntity;
    }

    public static WebEntity getUserProtocol() {
        WebEntity webEntity = new WebEntity();
        webEntity.head = new HashMap<>();
        webEntity.title = "用户协议";
        webEntity.url = BaseProvider.BaseUrl +  "/pro/html/regist_agreement.html";
        return webEntity;
    }

    public static WebEntity getPrivacyPolicy() {
        WebEntity webEntity = new WebEntity();
        webEntity.head = new HashMap<>();
        webEntity.title = "隐私政策";
        webEntity.url = BaseProvider.BaseUrl +  "/pro/html/privacy_agreement.html";
        return webEntity;
    }

}
