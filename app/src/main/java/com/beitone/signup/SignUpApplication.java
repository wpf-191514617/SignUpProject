package com.beitone.signup;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.baidu.aip.FaceEnvironment;
import com.baidu.aip.FaceSDKManager;
import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.baidu.idl.facesdk.FaceTracker;
import com.beitone.face.APIService;
import com.beitone.face.Config;
import com.beitone.face.exception.FaceError;
import com.beitone.face.model.AccessToken;
import com.beitone.face.utils.OnResultListener;
import com.beitone.signup.ui.MainActivity;
import com.beitone.signup.ui.account.LoginActivity;
import com.bt.http.OkHttpUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.multidex.MultiDex;
import cn.betatown.mobile.beitonelibrary.base.BaseApplication;
import okhttp3.OkHttpClient;

public class SignUpApplication extends BaseApplication {

    private Handler handler = new Handler(Looper.getMainLooper());

    private final List<Activity> mActivities =
            Collections.synchronizedList(new LinkedList<Activity>());

    public static final float VALUE_BRIGHTNESS = 40.0F;
    public static final float VALUE_BLURNESS = 0.7F;
    public static final float VALUE_OCCLUSION = 0.6F;
    public static final int VALUE_HEAD_PITCH = 15;
    public static final int VALUE_HEAD_YAW = 15;
    public static final int VALUE_HEAD_ROLL = 15;
    public static final int VALUE_CROP_FACE_SIZE = 400;
    public static final int VALUE_MIN_FACE_SIZE = 120;
    public static final float VALUE_NOT_FACE_THRESHOLD = 0.6F;

    private static SignUpApplication mApplication;

    public static SignUpApplication getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initRegister();
        mApplication = this;
        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
         * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）。
         */
        UMConfigure.init(this, "5efea96ddbc2ec0820ff6576", "sd4j", UMConfigure.DEVICE_TYPE_PHONE,
                "");
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

        registerLifecycleCallbacks();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.hostnameVerifier((s, sslSession) -> true)
                //.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .connectTimeout(60000L, TimeUnit.MILLISECONDS)
                .readTimeout(60000L, TimeUnit.MILLISECONDS)
                .writeTimeout(60000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);

    }


    public void initToken() {
        APIService.getInstance().init(this);
        APIService.getInstance().setGroupId(Config.groupID);
        // 用ak，sk获取token, 调用在线api，如：注册、识别等。为了ak、sk安全，建议放您的服务器，
        APIService.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                Log.i("wtf", "AccessToken->" + result.getAccessToken());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Toast.makeText(SignUpApplication.this, "启动成功", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onError(FaceError error) {
                Log.e("xx", "AccessTokenError:" + error);
                error.printStackTrace();

            }
        }, this, Config.apiKey, Config.secretKey);

    }


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


    /**
     * 初始化SDK
     */
    public void initLib() {
        // 为了android和ios 区分授权，appId=appname_face_android ,其中appname为申请sdk时的应用名
        // 应用上下文
        // 申请License取得的APPID
        // assets目录下License文件名
        com.baidu.idl.face.platform.FaceSDKManager.getInstance().initialize(this,
                Config.licenseID, Config.licenseFileName);

        setFaceConfig1();
    }


    public void initRegister() {
        FaceSDKManager.getInstance().init(this, Config.licenseID, Config.licenseFileName);
        setFaceConfig();
    }


    private void setFaceConfig1() {
        List<LivenessTypeEnum> livenessList = new ArrayList<>();
        /*livenessList.add(LivenessTypeEnum.Eye);
        livenessList.add(LivenessTypeEnum.Mouth);
        livenessList.add(LivenessTypeEnum.HeadUp);
        livenessList.add(LivenessTypeEnum.HeadDown);
        livenessList.add(LivenessTypeEnum.HeadLeft);
        livenessList.add(LivenessTypeEnum.HeadRight);
        livenessList.add(LivenessTypeEnum.HeadLeftOrRight);*/

        FaceConfig config =
                com.baidu.idl.face.platform.FaceSDKManager.getInstance().getFaceConfig();
        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整
        config.setLivenessTypeList(livenessList);
        config.setLivenessRandom(true);
        config.setBlurnessValue(com.baidu.idl.face.platform.FaceEnvironment.VALUE_BLURNESS);
        config.setBrightnessValue(com.baidu.idl.face.platform.FaceEnvironment.VALUE_BRIGHTNESS);
        config.setCropFaceValue(com.baidu.idl.face.platform.FaceEnvironment.VALUE_CROP_FACE_SIZE);
        config.setHeadPitchValue(com.baidu.idl.face.platform.FaceEnvironment.VALUE_HEAD_PITCH);
        config.setHeadRollValue(com.baidu.idl.face.platform.FaceEnvironment.VALUE_HEAD_ROLL);
        config.setHeadYawValue(com.baidu.idl.face.platform.FaceEnvironment.VALUE_HEAD_YAW);
        config.setMinFaceSize(com.baidu.idl.face.platform.FaceEnvironment.VALUE_MIN_FACE_SIZE);
        config.setNotFaceValue(com.baidu.idl.face.platform.FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        config.setOcclusionValue(com.baidu.idl.face.platform.FaceEnvironment.VALUE_OCCLUSION);
        config.setCheckFaceQuality(true);
        config.setFaceDecodeNumberOfThreads(2);

        com.baidu.idl.face.platform.FaceSDKManager.getInstance().setFaceConfig(config);
    }


    private void setFaceConfig() {
        FaceTracker tracker = FaceSDKManager.getInstance().getFaceTracker(this);  //
        // .getFaceConfig();
        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整

        // 模糊度范围 (0-1) 推荐小于0.7
        tracker.set_blur_thr(FaceEnvironment.VALUE_BLURNESS);
        // 光照范围 (0-1) 推荐大于40
        tracker.set_illum_thr(FaceEnvironment.VALUE_BRIGHTNESS);
        // 裁剪人脸大小
        tracker.set_cropFaceSize(FaceEnvironment.VALUE_CROP_FACE_SIZE);
        // 人脸yaw,pitch,row 角度，范围（-45，45），推荐-15-15
        tracker.set_eulur_angle_thr(FaceEnvironment.VALUE_HEAD_PITCH,
                FaceEnvironment.VALUE_HEAD_ROLL,
                FaceEnvironment.VALUE_HEAD_YAW);

        // 最小检测人脸（在图片人脸能够被检测到最小值）80-200， 越小越耗性能，推荐120-200
        tracker.set_min_face_size(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        //
        tracker.set_notFace_thr(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        // 人脸遮挡范围 （0-1） 推荐小于0.5
        tracker.set_occlu_thr(FaceEnvironment.VALUE_OCCLUSION);
        // 是否进行质量检测
        tracker.set_isCheckQuality(true);
        // 是否进行活体校验
        tracker.set_isVerifyLive(true);
    }

    private void registerLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                /*if (activity != null && activity instanceof LoginActivity) {
                    return;
                }
                synchronized (mActivities) {
                    if (!mActivities.contains(activity)) {
                        mActivities.add(activity);
                    }
                }*/
                if (activity instanceof MainActivity) {

                    //发起通知...

                    unregisterActivityLifecycleCallbacks(this);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (activity != null && activity instanceof MainActivity) {

                }

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                /*synchronized (mActivities) {
                    if (mActivities.contains(activity)) {
                        mActivities.remove(activity);
                    }
                }*/
            }
        });
    }

}
