package cn.betatown.mobile.beitonelibrary.base.swipeback.app;

import android.app.Activity;


import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import cn.betatown.mobile.beitonelibrary.base.swipeback.BmSwipeBackLayout;
import cn.betatown.mobile.beitonelibrary.base.swipeback.BmSwipebackUtils;

/**
 * Created by laysionqet on 2018/4/24.
 */
public class BmSwipeBackListenerActivityAdapter implements BmSwipeBackLayout.SwipeListenerEx {
    private final WeakReference<Activity> mActivity;




    public BmSwipeBackListenerActivityAdapter(@NonNull Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public void onScrollStateChange(int state, float scrollPercent) {

    }

    @Override
    public void onEdgeTouch(int edgeFlag) {
        Activity activity = mActivity.get();
        if (null != activity) {
            BmSwipebackUtils.convertActivityToTranslucent(activity);
        }
    }

    @Override
    public void onScrollOverThreshold() {

    }

    @Override
    public void onContentViewSwipedBack() {
        Activity activity = mActivity.get();
        if (null != activity && !activity.isFinishing()) {
            activity.finish();
            activity.overridePendingTransition(0, 0);
        }
    }
}
