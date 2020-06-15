package cn.betatown.mobile.beitonelibrary.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import cn.betatown.mobile.beitonelibrary.adapter.listener.OnListItemChildClickListener;
import cn.betatown.mobile.beitonelibrary.adapter.listener.OnListItemChildLongClickListener;
import cn.betatown.mobile.beitonelibrary.adapter.listener.OnRecyclerItemClickListener;
import cn.betatown.mobile.beitonelibrary.adapter.listener.OnRecyclerItemLongClickListener;

/**
 * Created by Administrator on 2018/1/2.
 */

public abstract class BaseRecyclerAdapter<M> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected int mDefaultItemLayoutId;
    protected Context mContext;
    protected List<M> mData;
    protected OnListItemChildClickListener mOnRecyclerItemChildClickListener;
    protected OnListItemChildLongClickListener mOnRecyclerItemChildLongClickListener;
    protected OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    protected OnRecyclerItemLongClickListener mOnRecyclerItemLongClickListener;

    protected RecyclerView mRecyclerView;

    /**
     * 当前选中的索引
     */
    protected int mCheckedPosition = -1;

    private HeaderAndFooterAdapter mHeaderAndFooterAdapter;


    public BaseRecyclerAdapter(RecyclerView recyclerView){
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        mData = new ArrayList<>();
    }

    public BaseRecyclerAdapter(RecyclerView recyclerView, int defaultItemLayoutId) {
        this(recyclerView);
        mDefaultItemLayoutId = defaultItemLayoutId;
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder viewHolder = new BaseRecyclerViewHolder(this,
                mRecyclerView, LayoutInflater.from(mContext).inflate(viewType, parent, false),
                mOnRecyclerItemClickListener, mOnRecyclerItemLongClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildClickListener(mOnRecyclerItemChildClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildLongClickListener(mOnRecyclerItemChildLongClickListener);
        setItemChildListener(viewHolder.getViewHolderHelper(), viewType);
        return viewHolder;
    }

    protected void setItemChildListener(BaseViewHolderHelper helper, int viewType) {
    }


    @Override
    public int getItemViewType(int position) {
        if (mDefaultItemLayoutId == 0) {
            throw new RuntimeException("请在 " + this.getClass().getSimpleName() + " 中重写 getItemViewType 方法返回布局资源 id，或者使用 BGARecyclerViewAdapter 两个参数的构造方法 BGARecyclerViewAdapter(RecyclerView recyclerView, int itemLayoutId)");
        }
        return mDefaultItemLayoutId;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        fillData(holder.getViewHolderHelper(), position, getItem(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * 设置item的点击事件监听器
     *
     * @param onItemClickListener
     */
    public void setOnRVItemClickListener(OnRecyclerItemClickListener onItemClickListener) {
        mOnRecyclerItemClickListener = onItemClickListener;
    }

    /**
     * 设置item的长按事件监听器
     *
     * @param onItemLongClickListener
     */
    public void setOnRVItemLongClickListener(OnRecyclerItemLongClickListener onItemLongClickListener) {
        mOnRecyclerItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 设置item中的子控件点击事件监听器
     *
     * @param onItemChildClickListener
     */
    public void setOnItemChildClickListener(OnListItemChildClickListener onItemChildClickListener) {
        mOnRecyclerItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 设置item中的子控件长按事件监听器
     *
     * @param onItemChildLongClickListener
     */
    public void setOnItemChildLongClickListener(OnListItemChildLongClickListener onItemChildLongClickListener) {
        mOnRecyclerItemChildLongClickListener = onItemChildLongClickListener;
    }


    /**
     * 获取指定索引位置的数据模型
     *
     * @param position
     * @return
     */
    public M getItem(int position) {
        return mData.get(position);
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public List<M> getData() {
        return mData;
    }

    public final void notifyItemRangeInsertedWrapper(int positionStart, int itemCount) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemRangeInserted(positionStart, itemCount);
        } else {
            mHeaderAndFooterAdapter.notifyItemRangeInserted(mHeaderAndFooterAdapter.getHeadersCount() + positionStart, itemCount);
        }
    }

    /**
     * 在集合头部添加新的数据集合（下拉从服务器获取最新的数据集合，例如新浪微博加载最新的几条微博数据）
     *
     * @param data
     */
    public void addNewData(List<M> data) {
        if (isListNotEmpty(data)) {
            mData.addAll(0, data);
            notifyItemRangeInsertedWrapper(0, data.size());
        }
    }


    /**
     * 在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
     *
     * @param data
     */
    public void addMoreData(List<M> data) {
        if (isListNotEmpty(data)) {
            int positionStart = mData.size();
            mData.addAll(mData.size(), data);
            notifyItemRangeInsertedWrapper(positionStart, data.size());
        }
    }



    public final void notifyDataSetChangedWrapper() {
        if (mHeaderAndFooterAdapter == null) {
            notifyDataSetChanged();
        } else {
            mHeaderAndFooterAdapter.notifyDataSetChanged();
        }
    }

    public boolean isListNotEmpty(List list){
        return list != null && !list.isEmpty();
    }


    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param data
     */
    public void setData(List<M> data) {
        if (isListNotEmpty(data)) {
            mData = data;
        } else {
            mData.clear();
        }
        notifyDataSetChangedWrapper();
    }

    /**
     * 清空数据列表
     */
    public void clear() {
        mData.clear();
        notifyDataSetChangedWrapper();
    }

    public final void notifyItemRemovedWrapper(int position) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemRemoved(position);
        } else {
            mHeaderAndFooterAdapter.notifyItemRemoved(mHeaderAndFooterAdapter.getHeadersCount() + position);
        }
    }

    /**
     * 删除指定索引数据条目
     *
     * @param position
     */
    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemovedWrapper(position);
    }

    /**
     * 删除指定数据条目。该方法在 ItemTouchHelper.Callback 的 onSwiped 方法中调用
     *
     * @param viewHolder
     */
    public void removeItem(RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        if (mHeaderAndFooterAdapter != null) {
            mData.remove(position - mHeaderAndFooterAdapter.getHeadersCount());
            mHeaderAndFooterAdapter.notifyItemRemoved(position);
        } else {
            removeItem(position);
        }
    }

    /**
     * 删除指定数据条目
     *
     * @param model
     */
    public void removeItem(M model) {
        removeItem(mData.indexOf(model));
    }

    public final void notifyItemInsertedWrapper(int position) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemInserted(position);
        } else {
            mHeaderAndFooterAdapter.notifyItemInserted(mHeaderAndFooterAdapter.getHeadersCount() + position);
        }
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param model
     */
    public void addItem(int position, M model) {
        mData.add(position, model);
        notifyItemInsertedWrapper(position);
    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model
     */
    public void addFirstItem(M model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model
     */
    public synchronized void addLastItem(M model) {
        addItem(mData.size(), model);
    }

    public final void notifyItemChangedWrapper(int position) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemChanged(position);
        } else {
            mHeaderAndFooterAdapter.notifyItemChanged(mHeaderAndFooterAdapter.getHeadersCount() + position);
        }
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location
     * @param newModel
     */
    public void setItem(int location, M newModel) {
        mData.set(location, newModel);
        notifyItemChangedWrapper(location);
    }

    /**
     * 替换指定数据条目
     *
     * @param oldModel
     * @param newModel
     */
    public void setItem(M oldModel, M newModel) {
        setItem(mData.indexOf(oldModel), newModel);
    }

    public final void notifyItemMovedWrapper(int fromPosition, int toPosition) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemMoved(fromPosition, toPosition);
        } else {
            mHeaderAndFooterAdapter.notifyItemMoved(mHeaderAndFooterAdapter.getHeadersCount() + fromPosition, mHeaderAndFooterAdapter.getHeadersCount() + toPosition);
        }
    }

    /**
     * 移动数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void moveItem(int fromPosition, int toPosition) {
        notifyItemChangedWrapper(fromPosition);
        notifyItemChangedWrapper(toPosition);

        // 要先执行上面的 notifyItemChanged,然后再执行下面的 moveItem 操作

        mData.add(toPosition, mData.remove(fromPosition));
        notifyItemMovedWrapper(fromPosition, toPosition);
    }

    /**
     * 移动数据条目的位置。该方法在 ItemTouchHelper.Callback 的 onMove 方法中调用
     *
     * @param from
     * @param to
     */
    public void moveItem(RecyclerView.ViewHolder from, RecyclerView.ViewHolder to) {
        int fromPosition = from.getAdapterPosition();
        int toPosition = to.getAdapterPosition();
        if (mHeaderAndFooterAdapter != null) {
            mHeaderAndFooterAdapter.notifyItemChanged(fromPosition);
            mHeaderAndFooterAdapter.notifyItemChanged(toPosition);

            // 要先执行上面的 notifyItemChanged,然后再执行下面的 moveItem 操作

            mData.add(toPosition - mHeaderAndFooterAdapter.getHeadersCount(), mData.remove(fromPosition - mHeaderAndFooterAdapter.getHeadersCount()));
            mHeaderAndFooterAdapter.notifyItemMoved(fromPosition, toPosition);
        } else {
            moveItem(fromPosition, toPosition);
        }
    }

    /**
     * @return 获取第一个数据模型
     */
    public
    @Nullable
    M getFirstItem() {
        return getItemCount() > 0 ? getItem(0) : null;
    }

    /**
     * @return 获取最后一个数据模型
     */
    public
    @Nullable
    M getLastItem() {
        return getItemCount() > 0 ? getItem(getItemCount() - 1) : null;
    }


    public void addHeaderView(View headerView) {
        getHeaderAndFooterAdapter().addHeaderView(headerView);
    }

    public void addFooterView(View footerView) {
        getHeaderAndFooterAdapter().addFooterView(footerView);
    }

    public void removeHeaderView(View view) {
        getHeaderAndFooterAdapter().removeHeaderView(view);
    }

    public void removeFooterView(View view) {
        getHeaderAndFooterAdapter().removeFooterView(view);
    }

    public int getHeadersCount() {
        return mHeaderAndFooterAdapter == null ? 0 : mHeaderAndFooterAdapter.getHeadersCount();
    }

    public int getFootersCount() {
        return mHeaderAndFooterAdapter == null ? 0 : mHeaderAndFooterAdapter.getFootersCount();
    }

    public HeaderAndFooterAdapter getHeaderAndFooterAdapter() {
        if (mHeaderAndFooterAdapter == null) {
            synchronized (BaseRecyclerAdapter.this) {
                if (mHeaderAndFooterAdapter == null) {
                    mHeaderAndFooterAdapter = new HeaderAndFooterAdapter(this);
                }
            }
        }
        return mHeaderAndFooterAdapter;
    }

    /**
     * 是否是头部或尾部
     *
     * @param viewHolder
     * @return
     */
    public boolean isHeaderOrFooter(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() < getHeadersCount() || viewHolder.getAdapterPosition() >= getHeadersCount() + getItemCount();
    }


    /**
     * 设置当前选中索引
     *
     * @param position
     */
    public void setCheckedPosition(int position) {
        if (position == mCheckedPosition) {
            return;
        }

        int lastCheckedPosition = mCheckedPosition;
        mCheckedPosition = position;

        if (mCheckedPosition < getData().size()) {
            notifyItemChangedWrapper(mCheckedPosition);
        }
        if (lastCheckedPosition < getData().size()) {
            notifyItemChangedWrapper(lastCheckedPosition);
        }
    }

    /**
     * 获取当前选中的索引
     *
     * @return
     */
    public int getCheckedPosition() {
        return mCheckedPosition;
    }


    protected abstract void fillData(BaseViewHolderHelper helper, int position, M model);

}
