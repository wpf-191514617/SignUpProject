package com.beitone.signup.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseFragment;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.model.WorkApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.adapter.listener.OnRecyclerItemClickListener;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseRecyclerAdapter;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseViewHolderHelper;
import cn.betatown.mobile.beitonelibrary.util.DateStyle;
import cn.betatown.mobile.beitonelibrary.util.DateUtil;

public class WorkFragment extends BaseFragment {

    @BindView(R.id.layoutSelectProject)
    LinearLayout layoutSelectProject;
    @BindView(R.id.rvWork)
    RecyclerView rvWork;
    @BindView(R.id.tvProjectName)
    TextView tvProjectName;
    @BindView(R.id.tvCurrentTime)
    TextView tvCurrentTime;
    @BindView(R.id.tvCurrentDateAndWeek)
    TextView tvCurrentDateAndWeek;
    @BindView(R.id.ivSignUp)
    ImageView ivSignUp;
    @BindView(R.id.tvSignUp)
    TextView tvSignUp;
    @BindView(R.id.layoutSignUp)
    LinearLayout layoutSignUp;

    private int[] mTaskDrawable = {R.drawable.ic_work_1, R.drawable.ic_work_2,
            R.drawable.ic_work_3, R.drawable.ic_work_4,
            R.drawable.ic_work_1, R.drawable.ic_work_6, R.drawable.ic_work_3, R.drawable.ic_work_4};

    private WorkListAdapter mWorkListAdapter;

    private UserInfoResponse mUserInfoResponse;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_work;
    }

    @Override
    protected void initViewAndData() {

        setText(tvCurrentDateAndWeek, DateUtil.DateToString(new Date(),
                DateStyle.YYYY_MM_DD_CN) + " " + DateUtil.getWeek(new Date()).getChineseName());

        mHandler.sendEmptyMessage(1);

        rvWork.setLayoutManager(new GridLayoutManager(getActivity() , 4));
        mWorkListAdapter = new WorkListAdapter(rvWork);
        rvWork.setAdapter(mWorkListAdapter);
        mWorkListAdapter.setOnRVItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View itemView, int position) {

            }
        });
        refreshData();

    }


    private void refreshData() {
        mUserInfoResponse = UserHelper.getInstance().getCurrentInfo();
        setText(tvProjectName, mUserInfoResponse.getB_project_name());

        if (mUserInfoResponse.getToday_sign_num() > 0) {
            ivSignUp.setImageResource(R.drawable.ic_sign1);
            tvSignUp.setTextColor(Color.parseColor("#999999"));
            setText(tvSignUp, "已打卡");
        } else {
            ivSignUp.setImageResource(R.drawable.ic_signup);
            tvSignUp.setTextColor(Color.parseColor("#ff575cd1"));
            setText(tvSignUp, "打卡");
        }

        List<WorkApp> appList = new ArrayList<>();
        appList.add(new WorkApp("4" , R.drawable.ic_work_1 , "我的考勤"));
        if (mUserInfoResponse.getType().equals("3")){
            appList.add(new WorkApp("3" , R.drawable.ic_work_2 , "工资上传"));
        }
        mWorkListAdapter.setData(appList);
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Date date = DateUtil.addSecond(new Date(), 1);
                    setText(tvCurrentTime, DateUtil.DateToString(date,
                            DateStyle.HH_MM));
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                    break;
            }
        }
    };


    @OnClick(R.id.layoutSignUp)
    public void onViewClicked() {
        if (mUserInfoResponse.getToday_sign_num() > 0){
            return;
        }
        
    }

    class WorkListAdapter extends BaseRecyclerAdapter<WorkApp> {

        public WorkListAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_work);
        }

        @Override
        protected void fillData(BaseViewHolderHelper helper, int position, WorkApp model) {
            helper.setText(R.id.tvWork, model.appName)
                    .setImageResource(R.id.ivWork, model.appIcon);
        }
    }


}
