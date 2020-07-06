package com.beitone.signup.ui;

import android.content.Context;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.beitone.signup.R;
import com.beitone.signup.SignUpApplication;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.entity.WebEntity;
import com.beitone.signup.helper.WebHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.betatown.mobile.beitonelibrary.base.BaseApplication;
import cn.betatown.mobile.beitonelibrary.widget.BrowserLayout;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class WebActivity1 extends BaseActivity {

    @BindView(R.id.blLayout)
    BrowserLayout blLayout;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_web2;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        StateAppBar.translucentStatusBar(this,true);
        WebEntity webEntity = WebHelper.getCalendar();
        synCookies(this, webEntity.url);
        blLayout.loadUrl(webEntity.url);
    }


    /**
     * 同步一下cookie
     */
    public void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
//        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, "JSESSIONID=" + SignUpApplication.getSession() + ";Domain=a.tx06.com;Path=/");
        CookieSyncManager.getInstance().sync();
    }

}
