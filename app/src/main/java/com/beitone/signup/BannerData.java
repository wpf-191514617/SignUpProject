package com.beitone.signup;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

public class BannerData extends SimpleBannerInfo {

    public String image;

    public BannerData(String image) {
        this.image = image;
    }

    @Override
    public Object getXBannerUrl() {
        return image;
    }
}
