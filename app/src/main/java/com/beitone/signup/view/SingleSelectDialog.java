package com.beitone.signup.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beitone.signup.R;

import java.util.List;

import cn.betatown.mobile.beitonelibrary.adapter.listener.OnRecyclerItemClickListener;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseRecyclerAdapter;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseViewHolderHelper;

public class SingleSelectDialog extends Dialog {

    private OnSingleSelectListener mOnSingleSelectListener;

    private TextListAdapter mTextListAdapter;

    private String defaultData;

    public SingleSelectDialog(@NonNull Context context,
                              OnSingleSelectListener onSingleSelectListener) {
        super(context, R.style.ActionSheetDialogStyle);
        mOnSingleSelectListener = onSingleSelectListener;
        initView(context);
    }

    private void initView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_singlelist, null);
        setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.dimen_300dp);
        //layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.dimen_230dp);
        contentView.setLayoutParams(layoutParams);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);


        findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        findViewById(R.id.tvCommit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnSingleSelectListener != null) {
                    mOnSingleSelectListener.onSingleSelect(mTextListAdapter.getItem(mTextListAdapter.getCheckedPosition()),
                            mTextListAdapter.getCheckedPosition());
                }
                dismiss();
            }
        });
        RecyclerView rvSingleList = contentView.findViewById(R.id.rvSingleList);
        rvSingleList.setLayoutManager(new LinearLayoutManager(context));
        mTextListAdapter = new TextListAdapter(rvSingleList);
        rvSingleList.setAdapter(mTextListAdapter);
        mTextListAdapter.setOnRVItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View itemView, int position) {
                mTextListAdapter.setCheckedPosition(position);
                if (mOnSingleSelectListener != null) {
                    mOnSingleSelectListener.onSingleSelect(mTextListAdapter.getItem(mTextListAdapter.getCheckedPosition()),
                            mTextListAdapter.getCheckedPosition());
                }
                dismiss();
            }
        });
    }

    public void setData(List<String> data) {
        setData(data , null);
    }

    public void setData(List<String> data, String defaultData) {
        this.defaultData = defaultData;
        mTextListAdapter.setData(data);
    }

    class TextListAdapter extends BaseRecyclerAdapter<String> {

        public TextListAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_text);
        }


        @Override
        protected void fillData(BaseViewHolderHelper helper, int position, String model) {
            helper.setText(R.id.tvContent, model);
            if (defaultData != null && defaultData.equals(model)) {
                helper.setTextColorRes(R.id.tvContent, R.color.colorAccent);
            } else {
                helper.setTextColorRes(R.id.tvContent, R.color.gray33);
            }
        }
    }


    public interface OnSingleSelectListener {
        void onSingleSelect(String text, int position);
    }


}
