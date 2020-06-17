package com.beitone.signup.ui.home;

import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseFragment;

import java.util.Arrays;

import butterknife.BindView;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseRecyclerAdapter;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseViewHolderHelper;

public class WorkFragment extends BaseFragment {

    @BindView(R.id.layoutSelectProject)
    LinearLayout layoutSelectProject;
    @BindView(R.id.rvWork)
    RecyclerView rvWork;

    private int[] mTaskDrawable = {R.drawable.ic_work_1 , R.drawable.ic_work_2 , R.drawable.ic_work_3,R.drawable.ic_work_4,
            R.drawable.ic_work_1,R.drawable.ic_work_6,R.drawable.ic_work_3,R.drawable.ic_work_4};

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_work;
    }

    @Override
    protected void initViewAndData() {
        String[] workArray = getResources().getStringArray(R.array.work_array);
        rvWork.setLayoutManager(new GridLayoutManager(getActivity() , 4));
        WorkListAdapter listAdapter = new WorkListAdapter(rvWork);
        rvWork.setAdapter(listAdapter);
        listAdapter.setData(Arrays.asList(workArray));
    }

    class WorkListAdapter extends BaseRecyclerAdapter<String>{

        public WorkListAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_work);
        }

        @Override
        protected void fillData(BaseViewHolderHelper helper, int position, String model) {
            helper.setText(R.id.tvWork , model)
                    .setImageResource(R.id.ivWork , mTaskDrawable[position]);
        }
    }




}
