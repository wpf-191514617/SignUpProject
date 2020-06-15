package cn.betatown.mobile.beitonelibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.betatown.mobile.beitonelibrary.R;
import cn.betatown.mobile.beitonelibrary.util.BaseAppManager;
import cn.betatown.mobile.beitonelibrary.viewcontroller.VaryViewHelperController;

import static cn.betatown.mobile.beitonelibrary.base.BaseAppcomtActivity.TransitionMode.*;
import static cn.betatown.mobile.beitonelibrary.base.BaseAppcomtActivity.TransitionMode.LEFT;

public abstract class BaseAppcomtActivity extends AppCompatActivity {

    private VaryViewHelperController mVaryViewHelperController = null;

    private boolean isJumpTo = false;

    public enum TransitionMode{
        LEFT,RIGHT,TOP,BOTTOM,SCALE,FADE
    }

    private Handler baseHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()){
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);
        if (getContentViewLayoutId() != 0) {
            setContentView(getContentViewLayoutId());
        }
        BaseAppManager.getInstance().addActivity(this);
        initConfig();
    }


    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    private void initConfig() {
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        initViewAndData();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }


    protected void reStoreView() {
        if (null != mVaryViewHelperController) {
            mVaryViewHelperController.restoreView();
        }
    }

    public void switchView(String msg) {
        switchView(msg, null);
    }


    public void switchView(String msg, View.OnClickListener onClickListener) {
        switchView(msg, 0, onClickListener);
    }


    public void switchView(String msg, @DrawableRes int drawableId) {
        switchView(msg, drawableId, null);
    }

    public void switchView(String msg, @DrawableRes int drawableId,
                           View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("必须传入一个目标视图加载控制器, 请重写 getLoadingTargetView()");
        }
        mVaryViewHelperController.switchView(msg, drawableId, onClickListener);
    }

    public void switchGifView(String msg, @DrawableRes int drawableId,
                              View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("必须传入一个目标视图加载控制器, 请重写 getLoadingTargetView()");
        }
        mVaryViewHelperController.switchGifView(msg, drawableId, onClickListener);
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void jumpTo(Class<?> clazz) {
        jumpTo(clazz, null);
    }


    protected void jumpTo(Class<?> clazz, Bundle bundle) {
        if (isJumpTo){
            return;
        }
        isJumpTo = true;
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        baseHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isJumpTo = false;
            }
        },800);
    }

    protected void jumpToForResult(Class<?> clazz, int requestCode) {
        jumpToForResult(clazz, requestCode, null);
    }

    protected void jumpToForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        if (isJumpTo){
            return;
        }
        isJumpTo = true;
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        baseHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isJumpTo = false;
            }
        },800);
    }

    protected void jumpToThenKill(Class<?> clazz) {
        jumpToThenKill(clazz, null);
    }

   /* @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration , resources.getDisplayMetrics());
        return resources;
    }*/

    protected void jumpToThenKill(Class<?> clazz, Bundle bundle) {
        /*jumpTo(clazz, bundle);
        this.finish();*/

        if (isJumpTo) {
            return;
        }
        isJumpTo = true;
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        baseHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isJumpTo = false;
                finish();
            }
        }, 800);

    }

    protected void showToast(int resourceId) {
        showToast(getString(resourceId));
    }

    protected void showToast(String text) {
        if (!TextUtils.isEmpty(text))
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    protected abstract int getContentViewLayoutId();

    protected void getBundleExtras(Bundle extras) {

    }

    protected abstract void initViewAndData();

    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseAppManager.getInstance().removeActivity(this);
    }

}
