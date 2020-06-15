package cn.betatown.mobile.beitonelibrary.adapter.listener;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/1/2.
 *
 * recyclerview 中item中的子View的点击事件监听
 *
 */

public interface OnListItemChildClickListener {
    void onItemChildClick(ViewGroup parent, View childView, int position);
}
