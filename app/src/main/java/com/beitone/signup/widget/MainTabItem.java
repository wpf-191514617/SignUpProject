package com.beitone.signup.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.beitone.signup.model.TabItem;

import cn.betatown.mobile.beitonelibrary.R;

public class MainTabItem extends LinearLayout {

    private ImageView tab_icon ;
    private TextView tab_title;

    private TabItem mTabitem;

    public MainTabItem(Context context) {
        this(context, null);
    }

    public MainTabItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public MainTabItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setData(TabItem tabItem){
        mTabitem = tabItem;
        tab_title.setText(tabItem.tabText);
        tab_icon.setImageResource(tabItem.mDefault);
    }

    public void setSelect(boolean isSelect){
        if (isSelect){
            tab_icon.setImageResource(mTabitem.mSelect);
            tab_title.setTextColor(Color.parseColor("#6262F7"));
        } else {
            tab_icon.setImageResource(mTabitem.mDefault);
            tab_title.setTextColor(Color.parseColor("#333333"));
        }
    }

    private void initView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.comui_tab_view , this);
        tab_icon = contentView.findViewById(R.id.tab_icon);
        tab_title = contentView.findViewById(R.id.tab_title);
    }
}
