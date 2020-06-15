package cn.betatown.mobile.beitonelibrary.viewcontroller.callback;

/**
 * 作者：王鹏飞
 * 邮箱：15291967179@163.com
 * 描述：
 */
public interface BaseView
{
    /**
     * loading状态
     */
    void showLoading();

    /**
     *
     * @param msg
     */
    void showError(String msg);
    
    void showNetError();
    
    void showNormal();
    
}
