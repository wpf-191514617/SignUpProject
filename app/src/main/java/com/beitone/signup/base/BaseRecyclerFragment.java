package com.beitone.signup.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beitone.signup.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.betatown.mobile.beitonelibrary.adapter.AdapterUtil;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseRecyclerAdapter;

public abstract class BaseRecyclerFragment extends BaseFragment implements OnRefreshListener,
        OnLoadMoreListener {

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    protected SmartRefreshLayout refreshLayout;
    Unbinder unbinder;

    protected BaseRecyclerAdapter mAdapter;

    protected int mCurrentPage = 1;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.recyclerview;
    }

    @Override
    protected void initViewAndData() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        initLayoutManager();

        mAdapter = getAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();
        refreshLayout.autoRefresh(300);
    }

    protected void initLayoutManager() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    protected void finishLoad() {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
        }
    }

    protected void setData(List<?> data) {
        finishLoad();
        if (mCurrentPage == 1) {
            if (AdapterUtil.isListNotEmpty(data)) {
                mAdapter.setData(data);
            } else {
                mAdapter.clear();
                showNormal();
            }
        } else {
            if (AdapterUtil.isListNotEmpty(data)) {
                mAdapter.addMoreData(data);
            } else {
                if (refreshLayout != null)
                    refreshLayout.setEnableLoadMore(false);
            }
        }
    }

    @Override
    protected View getLoadingTargetView() {
        return getView().findViewById(R.id.refreshLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
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

    protected abstract void onRefresh();

    protected abstract void onLoadMore();

    protected abstract BaseRecyclerAdapter getAdapter();

    @Override
    protected void onClickReload() {
        super.onClickReload();
        refreshLayout.autoRefresh();
    }
}
