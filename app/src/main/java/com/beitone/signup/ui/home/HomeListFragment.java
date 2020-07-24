package com.beitone.signup.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.library.YLCircleImageView;
import com.beitone.signup.R;
import com.beitone.signup.base.BaseRecyclerFragment;
import com.beitone.signup.entity.response.ArticleResponse;
import com.beitone.signup.helper.WebHelper;
import com.beitone.signup.provider.AppProvider;
import com.beitone.signup.ui.WebActivity;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseRecyclerAdapter;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseViewHolderHelper;
import cn.betatown.mobile.beitonelibrary.http.BaseProvider;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.DateStyle;
import cn.betatown.mobile.beitonelibrary.util.DateUtil;
import cn.betatown.mobile.beitonelibrary.util.Trace;

public class HomeListFragment extends BaseRecyclerFragment {

    private String mType;

    public HomeListFragment(){
    }

    public HomeListFragment(String type) {
        mType = type;
    }

    @Override
    protected void onRefresh() {
        loadData();
    }

    @Override
    protected void onLoadMore() {
        loadData();
    }

    private void loadData() {
        AppProvider.loadAppIndexArticle(getActivity(), mType, mCurrentPage,
                new OnJsonCallBack<List<ArticleResponse>>() {
                    @Override
                    public void onResult(List<ArticleResponse> data) {
                        setData(data);
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        setData(null);
                        showToast(msg);
                    }

                    @Override
                    public void onFailed(String msg) {
                        super.onFailed(msg);
                        setData(null);
                        showToast(msg);
                    }
                });
    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        return new HomeListAdapter(recyclerView);
    }

    class HomeListAdapter extends BaseRecyclerAdapter<ArticleResponse> {


        public HomeListAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_home);
        }

        @Override
        protected void fillData(BaseViewHolderHelper helper, int position, ArticleResponse model) {

            String publishTime = model.getCreatetime();
            String[] array = publishTime.split(" ");
            if (array != null && array.length > 0) {
                publishTime = array[0];
            }
            Date date = DateUtil.StringToDate(publishTime , DateStyle.YYYY_MM_DD);
            helper.setText(R.id.tvTitle, model.getTitle())
                    .setText(R.id.tvPublishTime, DateUtil.DateToString(date , DateStyle.MM_DD_CN));

            TextView tvStudy = helper.getTextView(R.id.tvStudy);

            YLCircleImageView ivHome = helper.getView(R.id.ivHome);
            //设置图片圆角角度
           /* RoundedCorners roundedCorners =
                    new RoundedCorners(getResources().getDimensionPixelSize(R.dimen.dimen_8dp));
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            RequestOptions options =
                    RequestOptions.bitmapTransform(roundedCorners).override(getResources()
                    .getDimensionPixelSize(R.dimen.dimen_105dp),
                            getResources().getDimensionPixelSize(R.dimen.dimen_76dp));*/
           // Glide.with(mContext).load(BaseProvider.BaseUrl + model.getImg()).into(ivHome);

            Picasso.get().load(BaseProvider.BaseUrl + model.getImg()).into(ivHome);

            switch (model.getIs_study()) {
                case "0":
                    tvStudy.setVisibility(View.INVISIBLE);
                    break;
                case "1":
                    tvStudy.setVisibility(View.VISIBLE);
                    tvStudy.setSelected(false);
                    tvStudy.setText("已学习");
                    break;
                case "2":
                    tvStudy.setVisibility(View.VISIBLE);
                    tvStudy.setSelected(true);
                    tvStudy.setText("立即学习");
                    break;
            }
            helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(WebActivity.KEY_WEB,
                            WebHelper.getArticleDetails(model.getId()));
                    jumpTo(WebActivity.class, bundle);
                }
            });
        }
    }

}
