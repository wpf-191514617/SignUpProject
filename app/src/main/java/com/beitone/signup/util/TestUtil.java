package com.beitone.signup.util;

import com.beitone.signup.BannerData;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    private static final String[] bannerData = {"https://ss0.bdstatic" +
            ".com/70cFvHSh_Q1YnxGkpoWK1HF6hhy" +
            "/it" +
            "/u=1976353132,2982690918&fm=26&gp=0.jpg"
            , "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1168500212," +
            "1749295737&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3228637033," +
                    "1874189684&fm=26&gp=0.jpg"};


    public static List<BannerData> getBannerData() {
        List<BannerData> datas = new ArrayList<>();
        for (String datum : bannerData) {
            datas.add(new BannerData(datum));
        }
        return datas;
    }

}
