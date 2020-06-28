package com.beitone.signup.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;
import com.beitone.signup.model.WorkApp;
import com.beitone.signup.provider.AppProvider;
import com.beitone.signup.ui.MainActivity;
import com.beitone.signup.ui.account.FaceDetectActivity;
import com.beitone.signup.ui.account.FaceSignActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.adapter.listener.OnRecyclerItemClickListener;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseRecyclerAdapter;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseViewHolderHelper;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.DateStyle;
import cn.betatown.mobile.beitonelibrary.util.DateUtil;
import cn.betatown.mobile.beitonelibrary.util.Trace;

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

    private WorkListAdapter mWorkListAdapter;

    private UserInfoResponse mUserInfoResponse;

    private final int REQUEST_SIGN = 99;

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
        checkLocation();
    }

    private void checkLocation() {
        AppProvider.checkIsInSignRegion(getActivity(), new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                jumpToForResult(FaceSignActivity.class ,REQUEST_SIGN);
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                showToast(msg);
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                showToast(msg);
            }
        });
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void onEventComming(EventData eventData) {
        super.onEventComming(eventData);
        switch (eventData.CODE){
            case EventCode
                    .CODE_USERINFO_SUCCESS:
                refreshData();
                break;
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
