package cn.betatown.mobile.beitonelibrary.adapter.listener;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/1/2.
 * recyclerView的itemview中的子View长按事件的监听
 */

public interface OnListItemChildLongClickListener {
    boolean onItemChildLongClick(ViewGroup parent, View childView, int position);
}
