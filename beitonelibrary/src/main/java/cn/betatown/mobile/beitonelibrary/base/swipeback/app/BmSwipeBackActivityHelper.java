package cn.betatown.mobile.beitonelibrary.base.swipeback.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import cn.betatown.mobile.beitonelibrary.R;
import cn.betatown.mobile.beitonelibrary.base.swipeback.BmSwipeBackLayout;


/**
 * @author Yrom
 */
public class BmSwipeBackActivityHelper {
    private Activity mActivity;

    private BmSwipeBackLayout mSwipeBackLayout;

    public BmSwipeBackActivityHelper(Activity activity) {
        mActivity = activity;
    }

    @SuppressWarnings("deprecation")
    public void onActivityCreate() {
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = (BmSwipeBackLayout) LayoutInflater.from(mActivity).inflate(
               R.layout.swipeback_layout, null);
    }

    public void onPostCreate() {
        mSwipeBackLayout.attachToActivity(mActivity);
    }

    public View findViewById(int id) {
        if (mSwipeBackLayout != null) {
            return mSwipeBackLayout.findViewById(id);
        }
        return null;
    }

    public BmSwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }
}
