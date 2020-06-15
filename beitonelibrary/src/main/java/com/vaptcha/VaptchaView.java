package com.vaptcha;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.vaptcha.utils.CirclePointLoadView;
import com.vaptcha.utils.CountDownTimer;
import com.vaptcha.utils.NetworkUtils;
import com.vaptcha.utils.NoDoubleClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.betatown.mobile.beitonelibrary.R;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;
import static com.vaptcha.Constans.outage_url;
import static java.lang.Math.abs;

/**
 * Created by tyl
 * 2018/10/30/030
 * Describe:
 */

public class VaptchaView extends RelativeLayout {
    boolean isAnimating = false;//是否正在执行动画
    private static RelativeLayout rl_tips;
    private TextView tv_tips;
    private ImageView iv_tips;
    private static int height;
    private DrawLine drawline;
    private Paint mPaint;
    private Path mPath;
    //手指按下的位置
    private float touchtX, touchY;
    private ValueAnimator open_animator;
    private ValueAnimator close_animator;
    private String mVid = "5e096eee4c6b8b41680b8d30";
    private static String mLang = "zh-CN";
    private static String mScene;
    private CirclePointLoadView loadView;
    private ImageView iv_vaptcha_img;
    private ImageView ib_refresh;
    private ImageView iv_mark;
    private Boolean isShowLoading = false;
    private Animation rotate;
    private static String deviceID = "";
    private TextView tv_ad;
    private long start_time = 0;
    private String mChallenge;
    private String msg_success = "";
    private String msg_error = "";
    private String msg_default = "";
    private String msg_exception = "";
    private String msg_not_network = "";
    private JSONArray cdn_servers;
    private Boolean isExection = false;
    private String ver_success_time = "";
    private String ver_success_user = "";
    private String mType = "";
    private String mColor = "";
    private ProgressBar button_progress;
    private ImageView iv_button_log;
    private Context mContext;
    private View vaptcha_button;
    private RelativeLayout vaptcha_view;
    private RelativeLayout rl_char_layout;
    private TextView bn_char_sure;
    private EditText edit_char;
    private String msg_length;
    private TextView button_verfiy;
    private Dialog hideVerfiyDialog;
    private TextView tv_hide_pass;
    private ProgressBar progress_hide;
    private ProgressBar progress_hide_finish;
    private boolean guide = false;
    private Boolean isOutAge = false;
    private String mOutAgeUrl = "";
    private String msg_verfiying;
    private String msg_verfiy_success;
    private String msg_start_verfiy;
    private String msg_sure;
    private String mOutageImgId;
    private int canvas_width = 0;
    private int canvas_height = 0;
    private String ver_score;
    private vaptchaListener vaptcha_listener;
    private String char_type;
    private Boolean bnIsClick = false;
    private TextView tv_title;
    private boolean netWorkAvailable;
    private RelativeLayout vaptcha_button1;

    public void setVaptchaListener(vaptchaListener listener) {
        this.vaptcha_listener = listener;
    }

    public VaptchaView(Context context) {
        super(context);
    }

    public VaptchaView(Context context, String type, String vid, String scene) {
        super(context);
        mContext = context;
        mVid = vid;
        mScene = scene;
        mType = type;
        init(context);
    }

    public VaptchaView(Context context, String type, String vid, String scene, Boolean isOutAge, String outage_url) {
        super(context);
        mContext = context;
        mVid = vid;
        mScene = scene;
        mType = type;
        this.isOutAge = isOutAge;
        mOutAgeUrl = outage_url;
        init(context);
    }

    public VaptchaView(Context context, String type, String vid, String scene, String lang) {
        super(context);
        mContext = context;
        mVid = vid;
        mLang = lang;
        mScene = scene;
        mType = type;
        init(context);
    }

    public VaptchaView(Context context, String type, String vid, String scene, String lang, Boolean isOutAge, String outage_url) {
        super(context);
        mContext = context;
        mVid = vid;
        mLang = lang;
        mScene = scene;
        mType = type;
        this.isOutAge = isOutAge;
        mOutAgeUrl = outage_url;
        init(context);
    }

    public VaptchaView(Context context, String type, String vid, String scene, String lang, String color) {
        super(context);
        mContext = context;
        mVid = vid;
        mLang = lang;
        mScene = scene;
        mType = type;
        mColor = color;
        init(context);
    }

    public VaptchaView(Context context, String type, String vid, String scene, String lang, String color, Boolean isOutAge, String outage_url) {
        super(context);
        mContext = context;
        mVid = vid;
        mLang = lang;
        mScene = scene;
        mType = type;
        mColor = color;
        this.isOutAge = isOutAge;
        mOutAgeUrl = outage_url;
        init(context);
    }

    public VaptchaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VaptchaView);
        mVid = typedArray.getString(R.styleable.VaptchaView_vaptcha_vid);
        String lang = typedArray.getString(R.styleable.VaptchaView_vaptcha_lang);
        if (lang != null && !TextUtils.isEmpty(lang)) {
            mLang = lang;
        }
        mScene = typedArray.getString(R.styleable.VaptchaView_vaptcha_scene);
        mType = typedArray.getString(R.styleable.VaptchaView_vaptcha_type);
        mColor = typedArray.getString(R.styleable.VaptchaView_vaptcha_color);
        mOutAgeUrl = typedArray.getString(R.styleable.VaptchaView_vaptcha_outage_url);
        isOutAge = typedArray.getBoolean(R.styleable.VaptchaView_vaptcha_isOutAge, false);
        typedArray.recycle();
        init(context);
    }

    private List<Integer> xList;
    private List<Integer> yList;
    private List<Integer> timeList;


    private void init(final Context context) throws VaptchaException {
        if (mVid == null || TextUtils.isEmpty(mVid)) {
            throw new VaptchaException("vid is null!");
        }
        if (mScene == null || TextUtils.isEmpty(mScene)) {
            throw new VaptchaException("scene is null!");
        }
        if (mType == null || TextUtils.isEmpty(mType)) {
            throw new VaptchaException("type is null!");
        }
        if (isOutAge && mOutAgeUrl == null) {
            throw new VaptchaException("outage is null!");
        }
        if (isOutAge && TextUtils.isEmpty(mOutAgeUrl)) {
            throw new VaptchaException("outage is null!");
        }
        deviceID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID) + "45489787";
        if (deviceID == null || TextUtils.isEmpty(deviceID)) {
            deviceID = "";
        }
        netWorkAvailable = NetworkUtils.isNetWorkAvailable(mContext);
        xList = new ArrayList<>();
        yList = new ArrayList<>();
        timeList = new ArrayList<>();
        switch (mType) {
            case "embed":
                showEmbed();
                break;
            case "popup":
                showClick();
                break;
            case "invisible":
                hideVerfiy();
                break;
        }
    }

    public void vaptchaReset() {
        if (edit_char != null) {
            edit_char.setText("");
        }
        switch (mType) {
            case "embed":
                getDownTime();
                break;
            case "popup":
                netWorkAvailable = NetworkUtils.isNetWorkAvailable(mContext);
                if (!netWorkAvailable && mType.equals("popup")) {
                    return;
                }
                if (vaptcha_button1 != null) {
                    vaptcha_button1.setEnabled(true);
                }

                bnIsClick = false;
                if (iv_button_log != null) {
                    iv_button_log.setVisibility(VISIBLE);
                }
                if (button_progress != null) {
                    button_progress.setVisibility(GONE);
                }
                if (iv_button_log != null) {
                    iv_button_log.setImageResource(R.drawable.icon);
                }
                initLanguage();
                if (button_verfiy != null) {
                    button_verfiy.setText(msg_start_verfiy);
                    GradientDrawable myGrad = (GradientDrawable) button_verfiy.getBackground();
                    myGrad.setColor(Color.parseColor("#3D8AFF"));
                }
                break;
            case "invisible":
                hideVerfiy();
                break;
        }
    }

    private void getDownTime() {
        netWorkAvailable = NetworkUtils.isNetWorkAvailable(mContext);
        if (!netWorkAvailable) {
            showNotNeTMsg();
            return;
        }
        showLoadView();
        if (isOutAge) {
            getOutAge(false);
        } else {
            OkGo.<String>post(Constans.downtime_url).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    String result = response.body();
                    if (result != null && !TextUtils.isEmpty(result)) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.getString("active") != null) {
                                Boolean active = jsonObject.getBoolean("active");//判断是否是宕机模式
                                if (active) {
                                    getOutAge(false);
                                } else {
                                    getConfig();
                                }
                            } else {
                                showExectionMsg();
                            }
                        } catch (JSONException e) {
                            showExectionMsg();
                            e.printStackTrace();
                        }
                    } else {
                        showExectionMsg();
                    }
                }
                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    showExectionMsg();
                }
            });
        }
    }

    private void getOutAge(final Boolean isRefresh) {



        OkGo.<String>get(outage_url).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    String code = jsonObject.getString("code");
                    if (code.equals("0103")) {
                        mOutageImgId = jsonObject.getString("imgid");
                        mChallenge = jsonObject.getString("challenge");

                        if (mType.equals("embed")) {
                            initVaptchaImg(outage_url + mOutageImgId + ".jpg", false, "outage", "");
                        } else if (mType.equals("popup")) {
                            if (hideVerfiyDialog != null) {
                                hideVerfiyDialog.dismiss();
                            }
                            showVaptchaDialog(outage_url + mOutageImgId + ".jpg", false, "outage", "");
                        } else if (mType.equals("invisible")) {
                            if (hideVerfiyDialog != null) {
                                hideVerfiyDialog.dismiss();
                            }
                            showVaptchaDialog(outage_url + mOutageImgId + ".jpg", false, "outage", "");
                        }
                    } else {
                        showExectionMsg();
                    }
                } catch (JSONException e) {
                    showExectionMsg();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });
    }

    private void getConfig() {
        String config_url = Constans.cofig_url + "config/v1" + "?id=" + mVid + "&type=" + mType + "&scene=" + mScene + "" +
                "&offline_server=https://www.vaptchadowntime.com/dometime&deviceid=" + deviceID;
        OkGo.<String>get(config_url).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String code = jsonObject.getString("code");
                        if (code != null && !TextUtils.isEmpty(code) && code.equals("0103")) {
                            guide = jsonObject.getBoolean("guide");
                            String vip = jsonObject.getString("vip");
                            cdn_servers = jsonObject.getJSONArray("cdn_servers");
                            String api_server = jsonObject.getString("api_server");
                            if (api_server != null && !TextUtils.isEmpty(api_server)) {
                                Constans.main_url = "https://" + api_server + "/";
                            }
                            mChallenge = jsonObject.getString("challenge");
                            char_type = jsonObject.getString("type");
                            if (char_type.equals("character")) {
                                refresh(false);
                            } else {
                                if (mType.equals("popup") || mType.equals("invisible")) {
                                    getClick();
                                } else {
                                    refresh(false);
                                }
                            }
                            if (guide) {
                                if (iv_mark != null) {
                                    iv_mark.setVisibility(VISIBLE);
                                }
                            } else {
                                if (iv_mark != null) {
                                    iv_mark.setVisibility(GONE);
                                }
                            }
                        } else {
                            showExectionMsg();
                        }
                    } catch (JSONException e) {
                        showExectionMsg();
                        e.printStackTrace();
                    }
                } else {
                    showExectionMsg();
                }
            }
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                showExectionMsg();
            }
        });
    }


    private void getClick() {
        String click_url = Constans.main_url + "click/v1" + "?id=" + mVid + "&challenge=" + mChallenge + "&deviceid=" + deviceID;
        OkGo.<String>get(click_url).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    String code = jsonObject.getString("code");
                    String token = jsonObject.getString("token");
                    if (code.equals("0103")) {
                        if (mType.equals("popup")) {
                            bnStopVerfiy();
                        }
                        if (mType.equals("invisible")) {
                            if (tv_hide_pass != null && progress_hide != null) {
                                tv_hide_pass.setVisibility(VISIBLE);
                                progress_hide_finish.setVisibility(getVisibility());
                                progress_hide.setVisibility(INVISIBLE);
                                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_hide_pass);
                                animation.setFillAfter(true);//android动画结束后停在结束位置
                                tv_hide_pass.startAnimation(animation);
                                if (hideVerfiyDialog != null) {
                                    hideVerfiyDialog.dismiss();
                                }
                            }
                        }
                        vaptcha_listener.onSuccess(token);
                    } else if (code.equals("0104")) {
                        String frequency = jsonObject.getString("frequency");
                        String adlinkword = jsonObject.getString("adlinkword");
                        String adlink = jsonObject.getString("adlink");
                        String img = jsonObject.getString("img");
                        if (mType.equals("popup")) {
                            showVaptchaDialog(img, false, adlinkword, adlink);
                        }
                        if (mType.equals("invisible")) {
                            if (hideVerfiyDialog != null) {
                                hideVerfiyDialog.dismiss();
                            }
                            showVaptchaDialog(img, false, adlinkword, adlink);
                        }
                    } else {
                        showExectionMsg();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });
    }

    private void hideVerfiy() {
        if (!netWorkAvailable) {
            initLanguage();
            Toast.makeText(mContext, msg_not_network, Toast.LENGTH_LONG).show();
            return;
        }

        hideVerfiyDialog = new Dialog(mContext, R.style.Theme_Light_Dialog);
        RelativeLayout hide_progress = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.hide_progress, null);
        //获得dialog的window窗口
        hideVerfiyDialog.setContentView(hide_progress);
        hideVerfiyDialog.getWindow().setDimAmount(0);//设置昏暗度为0
        hideVerfiyDialog.setCanceledOnTouchOutside(true);
//        hideVerfiyDialog.show();
        Window window = hideVerfiyDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.dialogStyle);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        tv_hide_pass = (TextView) hide_progress.findViewById(R.id.tv_hide_pass);
        progress_hide = (ProgressBar) hide_progress.findViewById(R.id.progress_hide);
        progress_hide_finish = (ProgressBar) hide_progress.findViewById(R.id.progress_hide_finish);
        progress_hide.setVisibility(VISIBLE);
        progress_hide_finish.setVisibility(INVISIBLE);
        tv_hide_pass.setVisibility(INVISIBLE);

    }

    public void show() {
        if (mType.equals("invisible") && hideVerfiyDialog != null) {
            hideVerfiyDialog.show();
            getDownTime();
        }
    }

    private void showEmbed() {
        vaptcha_view = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_vaptcha_view, this);
        vaptcha_view.setOnTouchListener(vaptchaTuchaLister);
        initVaptchaView();
    }

    RelativeLayout va_button;

    private void showClick() {
        Log.e("tyl", "showClick");
        if (vaptcha_button == null) {
            va_button = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_vaptcha_button, this);

            vaptcha_button1 = (RelativeLayout) va_button.findViewById(R.id.vaptcha_button);
            button_progress = (ProgressBar) va_button.findViewById(R.id.button_progress);
            iv_button_log = (ImageView) va_button.findViewById(R.id.iv_button_log);
            button_verfiy = (TextView) va_button.findViewById(R.id.button_verfiy);
            ImageView iv_button_help = (ImageView) va_button.findViewById(R.id.iv_button_help);

            if (!netWorkAvailable && mType.equals("popup")) {
                Log.e("tyl", "showClick--------");
                GradientDrawable myGrad = (GradientDrawable) button_verfiy.getBackground();
                myGrad.setColor(Color.argb((int) (0.8 * 255), 238, 60, 64));
                initLanguage();
                button_verfiy.setText(msg_not_network);
                vaptcha_button1.setEnabled(false);
                return;
            } else {
                vaptcha_button1.setEnabled(true);
            }
            vaptcha_button1.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    netWorkAvailable = NetworkUtils.isNetWorkAvailable(mContext);
                    Log.e("tyl", "vaptcha_button1");
                    if (!netWorkAvailable && mType.equals("popup")) {
                        Log.e("tyl", "showClick--------");
                        GradientDrawable myGrad = (GradientDrawable) button_verfiy.getBackground();
                        myGrad.setColor(Color.argb((int) (0.8 * 255), 238, 60, 64));
                        initLanguage();
                        button_verfiy.setText(msg_not_network);
                        vaptcha_button1.setEnabled(false);
                        return;
                    } else {
                        vaptcha_button1.setEnabled(true);
                        if (!bnIsClick) {
                            bnIsClick = true;
                            bnStartVerfiy();
                            getDownTime();
                        }
                    }
                }
            });
            iv_button_help.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(Constans.help_url));//Url 就是你要打开的网址
                    intent.setAction(Intent.ACTION_VIEW);
                    mContext.startActivity(intent); //启动浏览器
                }
            });
        }
        if (iv_button_log != null) {
            iv_button_log.setVisibility(VISIBLE);
        }
        if (button_progress != null) {
            button_progress.setVisibility(GONE);
        }
        if (iv_button_log != null) {
            iv_button_log.setImageResource(R.drawable.icon);
        }
        initLanguage();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button_verfiy.setText(msg_start_verfiy);
            }
        });

        GradientDrawable myGrad = (GradientDrawable) button_verfiy.getBackground();
        myGrad.setColor(Color.parseColor("#3D8AFF"));

    }

    Dialog vaptchaDialog;

    private void showVaptchaDialog(String img, Boolean isRefresh, String adlinkword, String adlink) {
        if (vaptchaDialog == null) {
            vaptchaDialog = new Dialog(mContext, R.style.Theme_Light_Dialog);
            //获得dialog的window窗口
            vaptcha_button = LayoutInflater.from(mContext).inflate(R.layout.layout_vaptcha, null);
            Window window = vaptchaDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.dialogStyle);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
            vaptchaDialog.setContentView(vaptcha_button);
            ImageView iv_cancel = (ImageView) vaptcha_button.findViewById(R.id.iv_cancel);
            drawline = (DrawLine) vaptcha_button.findViewById(R.id.drawline);

            tv_title = (TextView) vaptcha_button.findViewById(R.id.tv_title);

            iv_cancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    vaptchaReset();
                    if (mhandle != null) {
                        mhandle.removeCallbacks(timeRunable);
                    }
                    if (rl_tips != null) {
                        rl_tips.setVisibility(VISIBLE);
                    }
                    vaptchaDialog.dismiss();
                }
            });
            drawline.setOnTouchListener(vaptchaTuchaLister);
        }
        if (!vaptchaDialog.isShowing()) {
            vaptchaDialog.show();
        }
        switch (mType) {
            case "embed":
                initVaptchaView();
                break;
            case "popup":
                initVaptchaView();
                initVaptchaImg(img, isRefresh, adlinkword, adlink);
                break;
            case "invisible":
                initVaptchaView();
                initVaptchaImg(img, isRefresh, adlinkword, adlink);
                break;
        }

    }

    RelativeLayout rl_vaptcha_view = null;

    private void initVaptchaView() {
        initPaint();
        initLanguage();
        VaptchaView.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        rotate = AnimationUtils.loadAnimation(mContext, R.anim.rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        initView();
        if (mType.equals("embed")) {
            getDownTime();
        }
//        ib_refresh.setVisibility(VISIBLE);
        bn_char_sure.setText(msg_sure);
        edit_char.setHint(msg_length);

        if (tv_title != null) {
            tv_title.setText(msg_default);
        }

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int width_dp = px2dip(mContext, width);
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) rl_vaptcha_view.getLayoutParams();
        boolean pad = isPad(mContext);
        if (pad) {
            if (width_dp > 400) {
                int width_px = dip2px(mContext, 400);
                layoutParams.width = width_px;
                layoutParams.height = (int) (width_px * 0.575);
            } else {
                layoutParams.width = width;
                int hei = (int) (width * 0.575);
                layoutParams.height = hei;
            }
            rl_vaptcha_view.setLayoutParams(layoutParams);
        } else {
            if (width_dp > 400) {
                int width_px = dip2px(mContext, 400);
                layoutParams.width = width_px;
                layoutParams.height = (int) (width_px * 0.575);
            } else {
                layoutParams.width = width;
                int hei = (int) (width * 0.575);
                layoutParams.height = hei;
            }
            rl_vaptcha_view.setLayoutParams(layoutParams);
        }
        if (mType.equals("embed")) {
            if (isOutAge) {
                ViewGroup.LayoutParams layoutParams1 = rl_vaptcha_view.getLayoutParams();
                canvas_width = layoutParams1.width;
                canvas_height = layoutParams1.height;
            }
        }
        if (isOutAge && drawline != null) {
            ViewGroup.LayoutParams layoutParams1 = rl_vaptcha_view.getLayoutParams();
            drawline.setLayoutParams(layoutParams1);
            canvas_height = layoutParams1.height;
            canvas_width = layoutParams1.width;
        }

        ib_refresh.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (rotate != null) {
                    ib_refresh.startAnimation(rotate);
                } else {
                    ib_refresh.setAnimation(rotate);
                    ib_refresh.startAnimation(rotate);
                }
                ver_count = 1;
                refresh(true);
            }
        });

        iv_mark.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (rl_char_layout.getVisibility() == VISIBLE) {
                    intent.setData(Uri.parse(Constans.outage_help_url));
                } else {
                    intent.setData(Uri.parse(Constans.help_url));//Url 就是你要打开的网址
                }
                intent.setAction(Intent.ACTION_VIEW);
                mContext.startActivity(intent); //启动浏览器
            }
        });

        if (guide) {
            iv_mark.setVisibility(VISIBLE);
        } else {
            iv_mark.setVisibility(GONE);
        }

        rl_tips.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                netWorkAvailable = NetworkUtils.isNetWorkAvailable(mContext);
                if (!netWorkAvailable) {
                    if (mType.equals("embed")) {
                        showNotNeTMsg();
                    }
                    return;
                }

                if (mType.equals("popup") || mType.equals("invisible")) {
                    if (!isOutAge) {
                        if (vaptchaDialog != null) {
                            if (iv_button_log != null) {
                                iv_button_log.setVisibility(VISIBLE);
                            }
                            if (button_progress != null) {
                                button_progress.setVisibility(GONE);
                            }
                            if (iv_button_log != null) {
                                iv_button_log.setImageResource(R.drawable.icon);
                            }
                            initLanguage();
                            if (button_verfiy != null) {
                                button_verfiy.setText(msg_start_verfiy);
                                GradientDrawable myGrad = (GradientDrawable) button_verfiy.getBackground();
                                myGrad.setColor(Color.parseColor("#3D8AFF"));
                            }
                            bnIsClick = false;
                            if (rl_tips != null) {
                                rl_tips.setVisibility(VISIBLE);
                            }
                            vaptchaDialog.dismiss();
                        } else {
                            isExection = false;
                            ver_count = 1;
                            ver_all_count = 1;
                            getDownTime();
                        }
                    } else {
                        isExection = false;
                        ver_count = 1;
                        ver_all_count = 1;
                        getDownTime();
                    }
                } else {
                    isExection = false;
                    ver_count = 1;
                    ver_all_count = 1;
                    getDownTime();
                }

            }
        });
        bn_char_sure.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String value = edit_char.getText().toString();
                if (value != null && !TextUtils.isEmpty(value)) {
                    if (value.length() == 3) {
                        showLoadView();
                        verification(3, true);
                    } else {
                        showLengthError();
                    }
                }
            }
        });

        isExection = false;
        if (mhandle == null) {   //计时器
            mhandle = new Handler();
            mhandle.removeCallbacks(timeRunable);
            mhandle.postDelayed(timeRunable, 1000);
        }

    }

    private void initView() {
        if (mType.equals("popup") || mType.equals("invisible")) {
            netWorkAvailable = NetworkUtils.isNetWorkAvailable(mContext);
            if (!netWorkAvailable) {
                showClick();
                return;
            }
        }
        switch (mType) {
            case "embed":
                ib_refresh = (ImageView) vaptcha_view.findViewById(R.id.ib_refresh);
                iv_mark = (ImageView) vaptcha_view.findViewById(R.id.iv_mark);
                tv_ad = (TextView) vaptcha_view.findViewById(R.id.tv_ad);
                rl_tips = (RelativeLayout) vaptcha_view.findViewById(R.id.rl_tips);
                tv_tips = (TextView) vaptcha_view.findViewById(R.id.tv_tips);
                iv_tips = (ImageView) vaptcha_view.findViewById(R.id.iv_tips);
                loadView = (CirclePointLoadView) vaptcha_view.findViewById(R.id.LoadView);
                iv_vaptcha_img = (ImageView) vaptcha_view.findViewById(R.id.iv_vaptcha_img);
                rl_vaptcha_view = (RelativeLayout) vaptcha_view.findViewById(R.id.rl_vaptcha_view);
                rl_char_layout = (RelativeLayout) vaptcha_view.findViewById(R.id.rl_char_layout);
                bn_char_sure = (TextView) vaptcha_view.findViewById(R.id.bn_char_sure);
                edit_char = (EditText) vaptcha_view.findViewById(R.id.edit_char);
                break;
            case "popup":

                ib_refresh = (ImageView) vaptcha_button.findViewById(R.id.ib_refresh);
                iv_mark = (ImageView) vaptcha_button.findViewById(R.id.iv_mark);
                tv_ad = (TextView) vaptcha_button.findViewById(R.id.tv_ad);
                rl_tips = (RelativeLayout) vaptcha_button.findViewById(R.id.rl_tips);
                tv_tips = (TextView) vaptcha_button.findViewById(R.id.tv_tips);
                iv_tips = (ImageView) vaptcha_button.findViewById(R.id.iv_tips);
                loadView = (CirclePointLoadView) vaptcha_button.findViewById(R.id.LoadView);
                iv_vaptcha_img = (ImageView) vaptcha_button.findViewById(R.id.iv_vaptcha_img);
                rl_vaptcha_view = (RelativeLayout) vaptcha_button.findViewById(R.id.rl_vaptcha_view);
                rl_char_layout = (RelativeLayout) vaptcha_button.findViewById(R.id.rl_char_layout);
                bn_char_sure = (TextView) vaptcha_button.findViewById(R.id.bn_char_sure);
                edit_char = (EditText) vaptcha_button.findViewById(R.id.edit_char);
                break;
            case "invisible":
                ib_refresh = (ImageView) vaptcha_button.findViewById(R.id.ib_refresh);
                iv_mark = (ImageView) vaptcha_button.findViewById(R.id.iv_mark);
                tv_ad = (TextView) vaptcha_button.findViewById(R.id.tv_ad);
                rl_tips = (RelativeLayout) vaptcha_button.findViewById(R.id.rl_tips);
                tv_tips = (TextView) vaptcha_button.findViewById(R.id.tv_tips);
                iv_tips = (ImageView) vaptcha_button.findViewById(R.id.iv_tips);
                loadView = (CirclePointLoadView) vaptcha_button.findViewById(R.id.LoadView);
                iv_vaptcha_img = (ImageView) vaptcha_button.findViewById(R.id.iv_vaptcha_img);
                rl_vaptcha_view = (RelativeLayout) vaptcha_button.findViewById(R.id.rl_vaptcha_view);
                rl_char_layout = (RelativeLayout) vaptcha_button.findViewById(R.id.rl_char_layout);
                bn_char_sure = (TextView) vaptcha_button.findViewById(R.id.bn_char_sure);
                edit_char = (EditText) vaptcha_button.findViewById(R.id.edit_char);
                break;
        }
    }


    private Handler mhandle = null;
    private long currentSecond = 0;//当前毫秒数
    TimeRunable timeRunable = new TimeRunable();

    private class TimeRunable implements Runnable {
        @Override
        public void run() {
            synchronized (VaptchaView.this) {
                currentSecond = currentSecond + 1000;
                if (currentSecond < 180 * 1000) {
                    //递归调用本runable对象，实现每隔一秒一次执行任务
                    mhandle.postDelayed(this, 1000);
                } else {
                    refresh(true);
                }
            }
        }
    }

    private void showLoadView() {
        Log.e("tyl", "showLoadView");
        isShowLoading = true;
        if (loadView != null) {
            loadView.setVisibility(VISIBLE);
            loadView.startLoad();
        }
        if (char_type != null && char_type.equals("character")) {
        } else {
            if (iv_vaptcha_img != null) {
                iv_vaptcha_img.setVisibility(INVISIBLE);
            }
        }
        if (rl_tips != null && !mType.equals("embed")) {
            rl_tips.setVisibility(GONE);
        }

        if (ib_refresh != null) {
            ib_refresh.setVisibility(INVISIBLE);
        }
        if (iv_mark != null && guide == true) {
            iv_mark.setVisibility(GONE);
        }
    }

    private void stopLoadView() {
        isShowLoading = false;
        if (loadView != null) {
            loadView.stopLoad();
//            loadView.setVisibility(GONE);
        }

        if (iv_vaptcha_img != null) {
            iv_vaptcha_img.setVisibility(VISIBLE);
        }
        if (char_type != null && !char_type.equals("character")) {
            if (rl_tips != null) {
                rl_tips.setVisibility(VISIBLE);
            }
        }
        if (ib_refresh != null) {
            ib_refresh.setVisibility(VISIBLE);
        }

        if (iv_mark != null && guide == true) {
            iv_mark.setVisibility(VISIBLE);
        }
    }

    private final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private int pxDistance = 5;

    private OnTouchListener vaptchaTuchaLister = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (isShowLoading) {
                return true;
            }
            if (isExection) {
                return true;
            }
            if (char_type != null && char_type.equals("character")) {
                return true;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                        lastClickTime = currentTime;
                        touchtX = event.getX();
                        touchY = event.getY();
                        //设置原点
                        mPath.moveTo(touchtX, touchY);
                        start_time = System.currentTimeMillis();
                        xList.clear();
                        yList.clear();
                        timeList.clear();
                        if (touchtX - event.getX() > pxDistance || event.getX() - touchtX > pxDistance) {
                            if (touchY - event.getY() > pxDistance || event.getY() - touchY > pxDistance) {
                                xList.add((int) touchtX);
                                yList.add((int) touchY);
                                timeList.add((int) (System.currentTimeMillis() - start_time));
                                touchtX = event.getX();
                                touchY = event.getY();
                            }
                        }
                        if (isAnimating && open_animator != null) {
                            open_animator.cancel();
                        }
                        if (isAnimating && close_animator != null) {
                            close_animator.cancel();
                        }
                        if (mType.equals("invisible")) {
                            drawline.Mdraw(mPath, mPaint);
                        }
                        //关闭动画
                        animateClose(rl_tips);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    switch (mType) {
                        case "embed":
                            reset();
                            break;
                        case "popup":
                            if (drawline != null) {
                                drawline.reset(mPath);
                            }
                            break;
                        case "invisible":
                            if (drawline != null) {
                                drawline.reset(mPath);
                            }
                            break;
                    }
                    if (touchtX - event.getX() > pxDistance || event.getX() - touchtX > pxDistance) {
                        if (touchY - event.getY() > pxDistance || event.getY() - touchY > pxDistance) {
                            xList.add((int) touchtX);
                            yList.add((int) touchY);
                            timeList.add((int) (System.currentTimeMillis() - start_time));
                            touchtX = event.getX();
                            touchY = event.getY();
                        }
                    }
                    if (open_animator != null && open_animator.isRunning()) {
                        open_animator.cancel();
                    }
                    if (close_animator != null && close_animator.isRunning()) {
                        close_animator.cancel();
                    }
                    verification(System.currentTimeMillis() - start_time, false);

                    break;
                case MotionEvent.ACTION_CANCEL:
                    if (isAnimating && open_animator != null) {
                        open_animator.cancel();
                    }
                    if (isAnimating && close_animator != null) {
                        close_animator.cancel();
                    }
                    if (touchtX - event.getX() > pxDistance || event.getX() - touchtX > pxDistance) {
                        if (touchY - event.getY() > pxDistance || event.getY() - touchY > pxDistance) {
                            xList.add((int) touchtX);
                            yList.add((int) touchY);
                            timeList.add((int) (System.currentTimeMillis() - start_time));
                            touchtX = event.getX();
                            touchY = event.getY();
                        }
                    }
                    verification(System.currentTimeMillis() - start_time, false);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (touchtX - event.getX() > pxDistance || event.getX() - touchtX > pxDistance) {
                        if (touchY - event.getY() > pxDistance || event.getY() - touchY > pxDistance) {
                            xList.add((int) touchtX);
                            yList.add((int) touchY);
                            timeList.add((int) (System.currentTimeMillis() - start_time));
                            touchtX = event.getX();
                            touchY = event.getY();
                        }
                    }
                    //连线
                    mPath.lineTo(event.getX(), event.getY());
                    //刷新view
                    if (mType.equals("invisible")) {
                        drawline.Mdraw(mPath, mPaint);

                    } else {
                        invalidate();
                    }
                    break;
            }
            //返回true，消费事件
            return true;
        }
    };

    private int ver_count = 1;
    private int ver_all_count = 1;


    private void verification(final long draw_time, Boolean isChar) {
        String ver = "";
        if (isChar) {
            ver = edit_char.getText().toString();
            verfiy(ver, draw_time);
        } else {
            if (xList == null || xList.size() == 0 || yList == null || yList.size() == 0 || timeList == null || timeList.size() == 0) {
                showDefaultMsg();
                return;
            }
            if (xList.size() < 10) {
                showDefaultMsg();
                return;
            }
            if (isOutAge) {
                Integer first_x = xList.get(0);
                Integer first_y = yList.get(0);

                Integer last_x = xList.get(xList.size() - 1);
                Integer last_y = yList.get(yList.size() - 1);
                Integer c_x = 0;
                Integer c_y = 0;
                int e_area = 0;
                int e_index = 0;
                //1920*1080
                for (int i = 1; i < xList.size() - 1; i++) {
                    int area = getArea(first_x, first_y, xList.get(i), yList.get(i), last_x, last_y);
                    if (area > e_area) {
                        e_area = area;
                        e_index = i;
                    }
                }
                Integer e_x = xList.get(e_index);
                Integer e_y = yList.get(e_index);
                int e_time = timeList.get(e_index);
                int b_area = 0;
                int b_index = 0;
                int b_time = 0;

                for (int i = 1; i < xList.size() - 1; i++) {
                    int area = getArea(first_x, first_y, xList.get(i), yList.get(i), e_x, e_y);
                    if (area > b_area) {
                        b_area = area;
                        b_index = i;
                        b_time = timeList.get(i);
                    }
                }
//                b_x = xList.get(b_index);
//                b_y = yList.get(b_index);
                int c_area = 0;
                int c_index = 0;
                int c_time = 0;

                for (int i = 1; i < xList.size() - 1; i++) {
                    int area = getArea(last_x, last_y, xList.get(i), yList.get(i), e_x, e_y);
                    if (area > c_area) {
                        c_area = area;
                        c_index = i;
                        c_time = timeList.get(i);
                    }
                }
                if (b_area > c_area) {
                    c_x = xList.get(b_index);
                    c_y = yList.get(b_index);
                    c_time = b_time;
                } else {
                    c_x = xList.get(c_index);
                    c_y = yList.get(c_index);
                }
                if (mType.equals("embed") && !isOutAge) {
                    canvas_width = vaptcha_view.getMeasuredWidth();
                    canvas_height = vaptcha_view.getMeasuredHeight();
                }
                if (mType.equals("popup") && !isOutAge) {
                    canvas_width = vaptcha_button.getMeasuredWidth();
                    canvas_height = vaptcha_button.getMeasuredHeight();
                }
                if (mType.equals("invisible") && !isOutAge) {
                    canvas_width = vaptcha_button.getMeasuredWidth();
                    canvas_height = vaptcha_button.getMeasuredHeight();
                }
                String str_verfiy = "";
                str_verfiy = getRegion(first_x, first_y);
                if (e_time > c_time) {
                    str_verfiy += getRegion(c_x, c_y);
                    str_verfiy += getRegion(e_x, e_y);
                } else {
                    str_verfiy += getRegion(e_x, e_y);
                    str_verfiy += getRegion(c_x, c_y);
                }
                str_verfiy += getRegion(last_x, last_y);
                verfiy(str_verfiy, draw_time);

            } else {
                String x = "";
                String y = "";
                String time = "";

                for (int i = 0; i < xList.size(); i++) {
                    x += to20Jinzhi(xList.get(i));
                    y += to20Jinzhi(yList.get(i));
                    time += to20Jinzhi(timeList.get(i));
                }
                ver = x + y + time;
                verfiy(ver, draw_time);
            }
        }
    }

    private void verfiy(String ver, final long draw_time) {
        String verify_url = "";
        if (isOutAge) {
            verify_url = mOutAgeUrl + "?action=verify&challenge=" + mChallenge + "&v=" + ver;
        } else {
            verify_url = Constans.main_url + "/verify/v1" + "?id=" + mVid + "&challenge=" + mChallenge + "&v=" + ver + "&drawtime=" + draw_time + "&deviceid=" + deviceID;
        }

        OkGo.<String>get(verify_url).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                stopLoadView();
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    currentSecond = 0;
                    String code = jsonObject.getString("code");
                    if (code != null && !TextUtils.isEmpty(code)) {
                        if (!isOutAge) {
                            String frequency = jsonObject.getString("frequency");
                            ver_score = jsonObject.getString("score");
                            String passline = jsonObject.getString("passline");
                        }
                        String token = jsonObject.getString("token");
                        if (code.equals("0103")) {
                            if (!isOutAge) {
                                ver_success_user = ver_score;
                            }
                            java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.0");
                            ver_success_time = myformat.format(draw_time / 1000f) + "";
                            showSuccessMsg();
                            if (vaptchaDialog != null) {
                                CountDownTimer timer = new CountDownTimer(1200, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        if (mhandle != null) {
                                            mhandle.removeCallbacks(timeRunable);
                                        }
                                        if (rl_tips != null) {
                                            rl_tips.setVisibility(VISIBLE);
                                        }
                                        vaptchaDialog.dismiss();
                                    }
                                }.start();
                            }
                            if (mType.equals("popup")) {
                                bnStopVerfiy();
                            }
                            if (vaptcha_listener != null) {
                                vaptcha_listener.onSuccess(token);
                            }

                        } else if (code.equals("0104")) {

                            if (ver_all_count >= 10) {
                                ver_all_count = 1;
                                showExectionMsg();
                                if (vaptcha_listener != null) {
                                    vaptcha_listener.onExection();
                                }
                                return;
                            } else {
                                if (vaptcha_listener != null) {
                                    vaptcha_listener.onError();
                                }
                                ++ver_all_count;
                                if (ver_count >= 2) {
                                    ver_count = 1;
                                    if (isOutAge) {
                                        getOutAge(false);
                                    } else {
                                        refresh(true);
                                    }
                                    return;
                                } else {
                                    ++ver_count;
                                }
                                showErrorMsg();
                            }
                        } else {
                            if (vaptcha_listener != null) {
                                vaptcha_listener.onExection();
                            }
                            showExectionMsg();
                        }
                    } else {
                        if (vaptcha_listener != null) {
                            vaptcha_listener.onExection();
                        }
                        showExectionMsg();
                    }
                } catch (JSONException e) {
                    if (vaptcha_listener != null) {
                        vaptcha_listener.onExection();
                    }
                    showExectionMsg();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                showExectionMsg();
            }
        });
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (canvas != null && mPath != null && mPaint != null) {
            if (drawline != null) {
                drawline.Mdraw(mPath, mPaint);
            } else {
                canvas.drawPath(mPath, mPaint);
            }
        }
    }

    private void refresh(final Boolean isRefresh) {
        String type = "";
        if (!isRefresh) {
            type = "get";
        } else {
            showLoadView();
            type = "refresh";
        }
        if (isOutAge) {
            getOutAge(isRefresh);
        } else {
            String get_url = Constans.main_url + type + "/v1?id=" + mVid + "&challenge=" + mChallenge + "&deviceid=" + deviceID;

            OkGo.<String>get(get_url).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String code = jsonObject.getString("code");
                        stopLoadView();
                        if (code != null && !TextUtils.isEmpty(code) && code.equals("0103")) {
                            String frequency = jsonObject.getString("frequency");
                            String adlinkword = jsonObject.getString("adlinkword");
                            final String adlink = jsonObject.getString("adlink");
                            String img = jsonObject.getString("img");

                            if (char_type != null && char_type.equals("character")) {
                                if (mType.equals("popup")) {
                                    showVaptchaDialog(img, false, adlinkword, adlink);
                                }
                                if (mType.equals("invisible")) {
                                    if (hideVerfiyDialog != null) {
                                        hideVerfiyDialog.dismiss();
                                    }
                                    showVaptchaDialog(img, false, adlinkword, adlink);
                                } else {
                                    initVaptchaImg(img, isRefresh, adlinkword, adlink);
                                }
                            } else {
                                initVaptchaImg(img, isRefresh, adlinkword, adlink);
                            }
                        } else if (code != null && code.equals("0104")) {
                            showErrorMsg();
                        } else {
                            showExectionMsg();
                        }
                    } catch (JSONException e) {
                        stopLoadView();
                        showExectionMsg();
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    stopLoadView();
                    showExectionMsg();
                }
            });
        }
    }

    private void initVaptchaImg(String img, Boolean isRefresh, String adlinkword, final String adlink) {
        try {
            if (iv_vaptcha_img != null) {
                if (isOutAge) {
                    Glide.with(mContext).load(img).into(iv_vaptcha_img);
                } else {
                    Glide.with(mContext).load("https://" + cdn_servers.get(0).toString() + "/" + img).into(iv_vaptcha_img);
                }
            }

            if (isRefresh) {

            } else {
                stopLoadView();
            }
            if (char_type != null && char_type.equals("character")) {
                if (rl_char_layout != null) {
                    rl_char_layout.setVisibility(VISIBLE);
                }
            }
//            if (char_type != null) {
//                if (!char_type.equals("character")) {
//                    showDefaultMsg();
//                }
//            } else {
            showDefaultMsg();
//            }

            if (adlinkword != null && !TextUtils.isEmpty(adlinkword)) {
                tv_ad.setVisibility(VISIBLE);
                tv_ad.setText(adlinkword);
                tv_ad.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (adlink != null && !TextUtils.isEmpty(adlink)) {
                            Intent intent = new Intent();
                            intent.setData(Uri.parse(adlink));
                            intent.setAction(Intent.ACTION_VIEW);
                            mContext.startActivity(intent);
                        }
                    }
                });
                if (isOutAge) {
                    GradientDrawable myGrad = (GradientDrawable) tv_ad.getBackground();
                    myGrad.setColor(Color.parseColor("#4f000000"));
                }
            } else {
                if (tv_ad != null) {
                    tv_ad.setVisibility(GONE);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 10进制转20进制
     * data 传入的10进制
     */
    private static String to20Jinzhi(int data) {
        String str = "abcdefgh234lmntuwxyz";//自动字符 多少字符为多少进制
        int scale = str.length(); //转化目标进制
        String s = "";
        if (data == 0) {
            return str.charAt(0) + "";
        }
        while (data > 0) {
            if (data < scale) {
                s = str.charAt(data) + s;
                data = 0;
            } else {
                int r = data % scale;
                s = str.charAt(r) + s;
                data = (data - r) / scale;
            }
        }
//        字符不足3位前面补—线 自己定义
        if (s.length() < 3) {
            s = "_" + s;
        }
        return s;
    }

    //对外提供的方法，重新绘制
    private void reset() {
        mPath.reset();
        invalidate();
    }

    //初始化
    private void initPaint() {
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setColor(Color.parseColor("#00BFFF"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        mPaint.setStrokeWidth(i);
    }

    public VaptchaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void showDefaultMsg() {
        if (char_type != null && char_type.equals("character")) {
            return;
        }
        initView();
        if (mType.equals("popup") || mType.equals("invisible")) {
            rl_tips.setVisibility(GONE);
            return;
        } else {
            rl_tips.setVisibility(VISIBLE);
        }
        tv_tips.setText(msg_default);
        isExection = false;
        tv_tips.setTextColor(Color.parseColor("#666666"));
        tv_tips.getPaint().setFlags(0);
        iv_tips.setImageResource(R.drawable.icon_edit);
        rl_tips.setBackgroundColor(Color.argb((int) (0.8 * 255), 255, 255, 255));
        rl_tips.setEnabled(false);
        rl_tips.setVisibility(VISIBLE);
        animateOpen(rl_tips);
        stopLoadView();

    }

    private void showSuccessMsg() {
        initLanguage();
        initView();
        tv_tips.setText(msg_success);
        tv_tips.setTextColor(Color.WHITE);
        tv_tips.getPaint().setFlags(0);
        isExection = true;
        ib_refresh.setVisibility(INVISIBLE);
        rl_tips.setVisibility(VISIBLE);
        iv_tips.setImageResource(R.drawable.icon_success);
        rl_tips.setBackgroundColor(Color.argb((int) (0.8 * 255), 0, 213, 159));
        rl_tips.setEnabled(false);
        animateOpen(rl_tips);
        if (mhandle != null) {
            mhandle.removeCallbacks(timeRunable);
        }
//        stopLoadView();
    }

    private void showErrorMsg() {
        tv_tips.setText(msg_error);
        isExection = false;
        initView();
        tv_tips.setTextColor(Color.WHITE);
        tv_tips.getPaint().setFlags(0);
        iv_tips.setImageResource(R.drawable.icon_error);
        rl_tips.setBackgroundColor(Color.argb((int) (0.8 * 255), 238, 60, 64));
        rl_tips.setVisibility(VISIBLE);
        animateOpen(rl_tips);
        rl_tips.setEnabled(false);
        stopLoadView();
        if (vaptcha_listener != null) {
            vaptcha_listener.onError();
        }
    }

    private void showNotNeTMsg() {
        initView();
        if (tv_tips != null) {
            tv_tips.setText(msg_not_network);
        }
        isExection = false;
        if (tv_tips != null) {
            tv_tips.setTextColor(Color.WHITE);
            tv_tips.getPaint().setFlags(0);
        }
        if (iv_tips != null) {
            iv_tips.setImageResource(R.drawable.icon_error);
        }
        if (rl_tips != null) {
            rl_tips.setBackgroundColor(Color.argb((int) (0.8 * 255), 238, 60, 64));
            rl_tips.setVisibility(VISIBLE);
            animateOpen(rl_tips);
            rl_tips.setEnabled(false);
        }
        if (iv_vaptcha_img != null) {
            iv_vaptcha_img.setImageResource(R.drawable.img_default);
        }

        stopLoadView();
        if (vaptcha_listener != null) {
            vaptcha_listener.onError();
        }
    }

    private void showExectionMsg() {
        initView();
        if (ib_refresh != null) {
            ib_refresh.setVisibility(INVISIBLE);
        }
        isExection = true;
        if (tv_tips != null) {
            tv_tips.setText(msg_exception);
            tv_tips.setTextColor(Color.WHITE);
            tv_tips.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        }
        if (iv_tips != null) {
            iv_tips.setImageResource(R.drawable.icon_error);
        }
        if (rl_tips != null) {
            rl_tips.setBackgroundColor(Color.argb((int) (0.8 * 255), 238, 60, 64));
            rl_tips.setVisibility(VISIBLE);
            rl_tips.setEnabled(true);
            animateOpen(rl_tips);
        }
        if (iv_vaptcha_img != null) {
            iv_vaptcha_img.setImageResource(R.drawable.img_default);
        }
        if (vaptcha_listener != null) {
            vaptcha_listener.onExection();
        }
    }

    private void showLengthError() {
        initView();
        tv_tips.setText(msg_length);
        isExection = false;
        tv_tips.setTextColor(Color.WHITE);
        tv_tips.getPaint().setFlags(0);
        iv_tips.setImageResource(R.drawable.icon_error);
        rl_tips.setBackgroundColor(Color.argb((int) (0.8 * 255), 238, 60, 64));
        animateOpen(rl_tips);
        rl_tips.setEnabled(false);
        stopLoadView();
    }

    private void bnStartVerfiy() {
        iv_button_log.setVisibility(GONE);
        button_progress.setVisibility(VISIBLE);
        button_verfiy.setText(msg_verfiying);
        GradientDrawable myGrad = (GradientDrawable) button_verfiy.getBackground();
        myGrad.setColor(Color.parseColor("#3D8AFF"));
    }

    private void bnStopVerfiy() {
        if (iv_button_log != null) {
            iv_button_log.setVisibility(VISIBLE);
        }
        if (button_progress != null) {
            button_progress.setVisibility(GONE);
        }
        if (button_verfiy != null) {
            button_verfiy.setText(msg_verfiy_success);
            GradientDrawable myGrad = (GradientDrawable) button_verfiy.getBackground();
            myGrad.setColor(Color.parseColor("#00D59F"));
        }
        if (iv_button_log != null) {
            iv_button_log.setImageResource(R.drawable.icon_success);
        }
    }

    private void animateOpen(RelativeLayout view) {
        view.setVisibility(View.VISIBLE);
        open_animator = createDropAnimator(view, 0, height);
        open_animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }
        });
        open_animator.start();
    }

    private void animateClose(final RelativeLayout view) {
        int origHeight = view.getHeight();
        close_animator = createDropAnimator(view, origHeight, 0);
        close_animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                isAnimating = false;
            }
        });
        close_animator.start();
    }

    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    private void initLanguage() {
        if (mLang == null || TextUtils.isEmpty(mLang)) {
            if (getResources().getConfiguration().locale.getCountry().equals("CN")) {
                mLang = "zh-Hans";
            }
            if (getResources().getConfiguration().locale.getCountry().equals("TW")) {
                mLang = "zh-Hant";
            }
            if (getResources().getConfiguration().locale.getCountry().equals("UK")) {
                mLang = "en";
            }
            if (getResources().getConfiguration().locale.getCountry().equals("US")) {
                mLang = "en";
            }
        }

        switch (mLang) {
            case "zh-Hans":
                msg_default = "请绘制图中手势完成人机验证";
                msg_verfiying = "智能检测中...";
                msg_verfiy_success = "验证通过";
                if (char_type != null && char_type.equals("character")) {
                    msg_success = "验证通过";
                } else {
                    msg_success = "验证通过，用时" + ver_success_time + "秒，超过" + ver_success_user + "%的用户";
                }
                msg_error = "人机验证未通过，请再试一次";
                msg_length = "请填写图中的3个字符";
                msg_exception = "请求失败，点击重试";
                msg_start_verfiy = "点击验证";
                msg_sure = "确定";
                msg_not_network = "请检查网络是否连接";
                break;

            case "zh-Hant":
                msg_default = "請繪製圖中手勢完成人機驗證";
                msg_verfiy_success = "驗證通過";

                if (char_type != null && char_type.equals("character")) {
                    msg_success = "驗證通過";
                } else {
                    msg_success = "驗證通過，用時" + ver_success_time + "秒，超過" + ver_success_user + "%的用戶";
                }
                msg_error = "人機驗證未通過，請再試一次";
                msg_length = "請填寫圖中的3個字元";
                msg_exception = "請求失敗，點擊重試";
                msg_start_verfiy = "點擊驗證";
                msg_sure = "確定";
                msg_not_network = "請檢查網絡是否連接";
                break;

            case "en":
                msg_default = "Please draw the gesture in the picture to complete the man-machine verification";
                msg_verfiying = "Intelligent detection...";
                msg_verfiy_success = "Verify through";
                if (char_type != null && char_type.equals("character")) {
                    msg_success = "Verify through";
                } else {
                    msg_success = "Verify through，Time of use" + ver_success_time + "s，Exceed" + ver_success_user + "%Users";
                }

                msg_error = "The human-machine verification has not been passed. Please try again";
                msg_length = "Please fill in the 3 characters in the picture";
                msg_exception = "Request failed, click Retry";
                msg_start_verfiy = "Click verifying";
                msg_sure = "Sure";
                msg_not_network = "Please check if the network is connected.";
                break;

        }
    }


    private boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private int dip2px(Context context, float dpValue) {
        if (context != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } else {
            return 0;
        }
    }

    private int getArea(int x1, int y1, int x2, int y2, int x3, int y3) {
        int Area = 0;
        Area = abs((x1 * y2 + x2 * y3 + x3 * y1 - x1 * y3 - x2 * y1 - x3 * y2) / 2);
        return Area;
    }

    private String getRegion(int x, int y) {

        String[] strings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        int x_index = 0;
        int y_index = 0;
        String region = "0";

        if (x >= 0 && x < canvas_width * 0.25) {
            x_index = 0;
        } else if (x >= canvas_width * 0.25 && x < canvas_width * 0.5) {
            x_index = 1;
        } else if (x >= canvas_width * 0.5 && x < canvas_width * 0.75) {
            x_index = 2;
        } else if (x >= canvas_width * 0.75 && x <= canvas_width) {
            x_index = 3;
        }
        if (y >= 0 && y < canvas_height * 0.25) {
            y_index = 0;
        } else if (y >= canvas_height * 0.25 && y < canvas_height * 0.5) {
            y_index = 1;
        } else if (y >= canvas_height * 0.5 && y < canvas_height * 0.75) {
            y_index = 2;
        } else if (y >= canvas_height * 0.75 && y <= canvas_height) {
            y_index = 3;
        }
        if (x_index == 0) {
            if (y_index == 0) {
                region = strings[0];
            }
            if (y_index == 1) {
                region = strings[4];
            }
            if (y_index == 2) {
                region = strings[8];
            }
            if (y_index == 3) {
                region = strings[12];
            }
        }
        if (x_index == 1) {
            if (y_index == 0) {
                region = strings[1];
            }
            if (y_index == 1) {
                region = strings[5];
            }
            if (y_index == 2) {
                region = strings[9];
            }
            if (y_index == 3) {
                region = strings[13];
            }
        }
        if (x_index == 2) {
            if (y_index == 0) {
                region = strings[2];
            }
            if (y_index == 1) {
                region = strings[6];
            }
            if (y_index == 2) {
                region = strings[10];
            }
            if (y_index == 3) {
                region = strings[14];
            }
        }
        if (x_index == 3) {
            if (y_index == 0) {
                region = strings[3];
            }
            if (y_index == 1) {
                region = strings[7];
            }
            if (y_index == 2) {
                region = strings[11];
            }
            if (y_index == 3) {
                region = strings[15];
            }
        }
        return region;
    }

}
