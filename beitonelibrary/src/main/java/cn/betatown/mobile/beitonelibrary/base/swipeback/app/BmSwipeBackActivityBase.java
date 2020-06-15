package cn.betatown.mobile.beitonelibrary.base.swipeback.app;


import cn.betatown.mobile.beitonelibrary.base.swipeback.BmSwipeBackLayout;

/**
 * @author Yrom
 */
public interface BmSwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract BmSwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();

}
