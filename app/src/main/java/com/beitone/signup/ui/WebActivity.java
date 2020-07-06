package com.beitone.signup.ui;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.beitone.signup.R;
import com.beitone.signup.SignUpApplication;
import com.beitone.signup.base.BaseWebActivity;
import com.beitone.signup.entity.WebEntity;
import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;
import com.beitone.signup.provider.UserProvider;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.just.agentweb.AgentWebConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import cn.betatown.mobile.beitonelibrary.adapter.AdapterUtil;
import cn.betatown.mobile.beitonelibrary.permission.Acp;
import cn.betatown.mobile.beitonelibrary.permission.AcpListener;
import cn.betatown.mobile.beitonelibrary.permission.AcpOptions;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class WebActivity extends BaseWebActivity {

    private TextView tvTilte;

    private WebEntity mWebEntity;

    public static final String KEY_WEB = "webKey";

    private boolean isShare = false;
    private boolean isSign = false;
    private int shareImg;

    private View barView, lineTitle;
    private int REQUEST_SELECT_GETIMG = 17;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mWebEntity = bundle.getParcelable(KEY_WEB);
            isShare = bundle.getBoolean("isShare", false);
            isSign = bundle.getBoolean("isSign", false);
           // shareImg = bundle.getInt("shareImg", R.drawable.app_icon);
        }
        setContentView(R.layout.activity_web1);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.common_toolbar);
        barView = findViewById(R.id.barView);
        lineTitle = findViewById(R.id.lineTitle);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            tvTilte = (TextView) findViewById(R.id.tvTitle);
            if (isSign){
                mToolbar.setNavigationIcon(R.drawable.ic_back_white);
                mToolbar.setBackgroundColor(Color.parseColor("#6161f7"));
                tvTilte.setTextColor(Color.WHITE);
                barView.setVisibility(View.VISIBLE);
                lineTitle.setVisibility(View.GONE);
                StateAppBar.setStatusBarColor(this, ContextCompat.getColor(this,
                        R.color.colorAccent1));
            } else {
                StateAppBar.setStatusBarLightMode(this, Color.WHITE);
                mToolbar.setNavigationIcon(R.drawable.ic_back);
                lineTitle.setVisibility(View.GONE);
            }

        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAgentWeb.back()) {
                    onBackPressed();
                }
            }
        });
        if (mWebEntity != null) {
            tvTilte.setText(mWebEntity.title);
        }

        /*if (mWebEntity.url.contains("views/customer/invite_friend.htm")) {
            mToolbar.inflateMenu(R.menu.menu_invalite);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.menu_invalite){
                        *//*mAgentWeb.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                                + "document.getElementsByTagName('html')[0].innerHTML+'</head>');
                                ");*//*
                        ToastUtil.showToast("yaoqing");
                        return true;
                    }
                    return false;
                }
            });
        }*/
    //    enableCookies(mAgentWeb.getWebCreator().getWebView());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*if (mWebEntity.url.contains("invite_friend.htm")) {
            getMenuInflater().inflate(R.menu.menu_invalite, menu);
        }else if (isShare){
            getMenuInflater().inflate(R.menu.menu_share, menu);
        }*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*if (item.getItemId() == R.id.menu_invalite) {
            mAgentWeb.getJsAccessEntrace().quickCallJs("inviteRecordBtnClick");
            return true;
        } else if (item.getItemId() == R.id.menu_share){
            String desc = "";
            if (shareImg == R.drawable.img_transfer_detail){
                desc = "转账充值额度更高、安全便捷、操作简易";
            } else if (shareImg == R.drawable.img_share_transfer){
                desc = "转账充值最低得5元提现手续费抵扣金。";
            }
            UMengHelper.getInstance().share(this, tvTilte.getText().toString(), mWebEntity.url, shareImg, desc);
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void onEventComming(EventData eventData) {
        super.onEventComming(eventData);
        switch (eventData.CODE){
            case EventCode.CODE_GET_IMGSRC:
                selectImage();
                break;
        }


        /*if (eventData.CODE == EventCode.CODE_WEB) {
            WebResponse webResponse = (WebResponse) eventData.DATA;
            if (TextUtils.isEmpty(webResponse.msg) && webResponse.flag.equals("1")) {
                //ToastUtil.showToast("操作成功");
            } else {
                //  ToastUtil.showToast(webResponse.msg);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable("resultData" , webResponse);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        } else if (eventData.CODE == EventCode.CODE_SHARE){
            ShareWebEntity webEntity = (ShareWebEntity) eventData.DATA;
            showShareDialog(webEntity);
        }else if (eventData.CODE == EventCode.CODE_OPENBANKACCOUNTPAGE){
            Intent intent = new Intent(this , DepositoryInfoActivity.class);
            startActivity(intent);
            finish();
        }*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_GETIMG && resultCode == Activity.RESULT_OK){
            if (data != null) {
                ArrayList<String> images = data.getStringArrayListExtra(
                        ImageSelector.SELECT_RESULT);
                if (AdapterUtil.isListNotEmpty(images)) {

                }
            }
        }
    }

    private void selectImage() {
        Acp.getInstance(this).request(new AcpOptions.Builder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA).build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        ImageSelector.builder()
                                .useCamera(true) // 设置是否使用拍照
                                .setSingle(true)  //设置是否单选
                                .start(WebActivity.this, REQUEST_SELECT_GETIMG); //
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        showToast("权限拒绝");
                    }
                });
    }

    @Override
    protected String getUrlString() {
        if (mWebEntity != null) {
            return mWebEntity.url;
        }
        return "";
    }

    /*private void showShareDialog(ShareWebEntity webEntity) {
        UserProvider.getInvitedInfo(this, new OnJsonCallBack<InvitedResponse>() {
            @Override
            public void onResult(InvitedResponse data) {
                if (data != null){
                    new ShareDialog(WebActivity.this , data, webEntity).show();
                }
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                Toast.makeText(WebActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                Toast.makeText(WebActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getIndicatorColor() {
        return Color.parseColor("#00000000");
    }


    @Override
    protected void setTitle(WebView view, String title) {
        super.setTitle(view, title);
        /*if (!TextUtils.isEmpty(title)) {
            if (title.length() > 10) {
                title = title.substring(0, 10).concat("...");
            }
        }
        tvTilte.setText(title);*/
        WebBackForwardList forwardList = view.copyBackForwardList();
        WebHistoryItem item = forwardList.getCurrentItem();
        if (item != null) {
            // setActionBarTitle(item.getTitle());
            tvTilte.setText(item.getTitle());
        }

    }

    @Override
    protected int getIndicatorHeight() {
        return 0;
    }

    @Nullable
    @Override
    protected String getUrl() {
        if (!TextUtils.isEmpty(SignUpApplication.getSession())) {
           /* String session =
                    "JSESSIONID=" + BToneApplication.getSession() + ";JSESSION_P2P=" +
                    BToneApplication.getSession();*/
            /*AgentWebConfig.syncCookie(mWebEntity.url,
                    "JSESSIONID=" + BToneApplication.getSession() + ";JSESSION_P2P=" +
                            BToneApplication.getSession());


             enableCookies(getWebView());
            syncCookie(this,mWebEntity.url);
                    */

        }

        AgentWebConfig.syncCookie(mWebEntity.url,
                "JSESSIONID=" + SignUpApplication.getSession() + ";Domain=a.tx06.com;Path=/");

        if (mWebEntity != null) {
            return mWebEntity.url;
        }
        return "https://www.baidu.com/";
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {

            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }



    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) findViewById(R.id.webParent);
    }

    @Override
    protected void showContentView() {
        super.showContentView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.layoutLoading).setVisibility(View.GONE);
            }
        }, 300);
    }

    @Override
    protected void showloadingview() {
        super.showloadingview();
        findViewById(R.id.layoutLoading).setVisibility(View.VISIBLE);
    }
}
