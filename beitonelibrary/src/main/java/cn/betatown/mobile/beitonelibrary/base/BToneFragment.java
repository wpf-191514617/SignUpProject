package cn.betatown.mobile.beitonelibrary.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cn.betatown.mobile.beitonelibrary.viewcontroller.VaryViewHelperController;

public abstract class BToneFragment extends Fragment {

    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInVisible = true;

    private boolean isPrepared;

    private VaryViewHelperController mVaryViewHelperController;

    private boolean isJumpTo = false;

    private Handler baseHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), container, false);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
        initViewAndData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInVisible();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInVisible) {
                isFirstInVisible = false;
                onFirstUserInVisible();
            } else {
                onUserInVisible();
            }
        }
    }


    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }


    public void reStoreView() {
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


    protected void jumpTo(Class<?> clazz) {
        jumpTo(clazz, null);
    }

    protected void jumpTo(Class<?> clazz, Bundle bundle) {
        if (isJumpTo) {
            return;
        }
        isJumpTo = true;
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        baseHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isJumpTo = false;
            }
        }, 800);
    }

    protected void jumpToForResult(Class<?> clazz, int requestCode) {
        jumpToForResult(clazz, requestCode, null);
    }

    protected void jumpToForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        if (isJumpTo) {
            return;
        }
        isJumpTo = true;
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        baseHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isJumpTo = false;
            }
        }, 800);
    }

    protected void jumpToThenKill(Class<?> clazz) {
        jumpToThenKill(clazz, null);
    }

    protected void jumpToThenKill(Class<?> clazz, Bundle bundle) {
        /*jumpTo(clazz, bundle);
        getActivity().finish();*/
        if (isJumpTo) {
            return;
        }
        isJumpTo = true;
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        baseHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isJumpTo = false;
                getActivity().finish();
            }
        }, 800);
    }


    protected void showToast(int resourceId) {
        showToast(getString(resourceId));
    }

    protected void showToast(String text) {
        try {
            if (!TextUtils.isEmpty(text))
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    /**
     * 当前fragment可见时调用， like    onResume
     */
    protected void onUserVisible() {
    }

    /**
     * like onPause
     */
    protected void onUserInVisible() {
    }

    /**
     * fragment 第一次 可见的时候， 做初始化 或 数据的刷新（只调用一次）
     */
    protected void onFirstUserVisible() {
    }

    /**
     * 第一次 当fragment是不可见的时候
     */
    protected void onFirstUserInVisible() {

    }

    protected abstract int getContentViewLayoutID();

    protected abstract void initViewAndData();

    protected View getLoadingTargetView() {
        return null;
    }


}
