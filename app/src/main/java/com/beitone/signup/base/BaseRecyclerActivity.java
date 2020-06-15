package com.beitone.signup.base;


import android.view.View;

import com.beitone.signup.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.betatown.mobile.beitonelibrary.adapter.AdapterUtil;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseRecyclerAdapter;

public abstract class BaseRecyclerActivity extends BmSwipebackActivity implements OnRefreshListener,
        OnLoadMoreListener {

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    protected SmartRefreshLayout refreshLayout;

    protected BaseRecyclerAdapter mAdapter;

    protected int mCurrentPage = 1;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_recycler;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        setTitle(getTitleText());
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        initLayoutManager();
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        mAdapter = getAdapter();
        recyclerView.setAdapter(mAdapter);
        refreshLayout.autoRefresh(300);
    }

    protected void initLayoutManager() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void setData(List<?> data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finishLoad();
                if (mCurrentPage == 1) {
                    if (!AdapterUtil.isListNotEmpty(data)) {
                        showNormal();
                    } else {
                        mAdapter.setData(data);
                    }
                } else {
                    if (!AdapterUtil.isListNotEmpty(data)) {
                        refreshLayout.setEnableLoadMore(false);
                    } else {
                        mAdapter.addMoreData(data);
                    }
                }
            }
        });

    }

    protected void finishLoad() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        onLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        refreshLayout.setEnableLoadMore(true);
        onRefresh();
    }

    @Override
    protected View getLoadingTargetView() {
        return findViewById(R.id.refreshLayout);
    }

    protected abstract void onRefresh();

    protected abstract void onLoadMore();

    protected abstract BaseRecyclerAdapter getAdapter();

    protected abstract String getTitleText();
}
