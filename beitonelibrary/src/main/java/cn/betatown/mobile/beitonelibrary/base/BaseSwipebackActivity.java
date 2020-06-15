package cn.betatown.mobile.beitonelibrary.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import cn.betatown.mobile.beitonelibrary.base.swipeback.BmSwipeBackLayout;
import cn.betatown.mobile.beitonelibrary.base.swipeback.BmSwipebackUtils;
import cn.betatown.mobile.beitonelibrary.base.swipeback.app.BmSwipeBackActivityBase;
import cn.betatown.mobile.beitonelibrary.base.swipeback.app.BmSwipeBackActivityHelper;


public abstract class BaseSwipebackActivity extends BaseAppcomtActivity implements BmSwipeBackActivityBase {

    private BmSwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new BmSwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View view = super.findViewById(id);
        if (view == null && mHelper != null){
            return mHelper.findViewById(id);
        }
        return view;
    }

    @Override
    public BmSwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        BmSwipebackUtils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
