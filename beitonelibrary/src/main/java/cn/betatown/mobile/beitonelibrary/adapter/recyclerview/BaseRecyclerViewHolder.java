package cn.betatown.mobile.beitonelibrary.adapter.recyclerview;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import cn.betatown.mobile.beitonelibrary.adapter.listener.OnRecyclerItemClickListener;
import cn.betatown.mobile.beitonelibrary.adapter.listener.OnRecyclerItemLongClickListener;


/**
 * Created by Administrator on 2018/1/2.
 */

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private OnRecyclerItemClickListener mRecyclerItemClickListener;
    private OnRecyclerItemLongClickListener mRecyclerItemLongClickListener;
    private BaseViewHolderHelper mViewHolderHelper;
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mRecyclerAdapter;

    public BaseRecyclerViewHolder(BaseRecyclerAdapter recyclerAdapter , RecyclerView recyclerView ,
                                  View itemView , OnRecyclerItemClickListener itemClickListener ,
                                  OnRecyclerItemLongClickListener itemLongClickListener) {
        super(itemView);
        mRecyclerAdapter = recyclerAdapter;
        mRecyclerView = recyclerView;
        mContext = recyclerView.getContext();
        mRecyclerItemClickListener = itemClickListener;
        mRecyclerItemLongClickListener = itemLongClickListener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == BaseRecyclerViewHolder.this.itemView.getId() &&
                        mRecyclerItemClickListener != null){
                    mRecyclerItemClickListener.onItemClick(mRecyclerView , view , getAdapterPositionWrapper());
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (v.getId() == BaseRecyclerViewHolder.this.itemView.getId() && null != mRecyclerItemLongClickListener) {
                    return mRecyclerItemLongClickListener.onItemLongClick(mRecyclerView, v, getAdapterPositionWrapper());
                }
                return false;
            }
        });

        mViewHolderHelper = new BaseViewHolderHelper(mRecyclerView , this);
    }

    public BaseViewHolderHelper getViewHolderHelper(){
        return mViewHolderHelper;
    }

    public int getAdapterPositionWrapper() {
        if (mRecyclerAdapter.getHeadersCount() > 0) {
            return getAdapterPosition() - mRecyclerAdapter.getHeadersCount();
        } else {
            return getAdapterPosition();
        }
    }


}
