package com.beitone.signup.ui.home;

import android.widget.ImageView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseRecyclerFragment;
import com.beitone.signup.entity.response.ArticleResponse;
import com.beitone.signup.provider.AppProvider;
import com.beitone.signup.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseRecyclerAdapter;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseViewHolderHelper;
import cn.betatown.mobile.beitonelibrary.http.BaseProvider;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.Trace;

public class HomeListFragment extends BaseRecyclerFragment {

    private String mType;

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

            helper.setText(R.id.tvTitle , model.getTitle())
                    .setText(R.id.tvPublishTime , model.getCreatetime());

            ImageView ivHome = helper.getImageView(R.id.ivHome);
            //设置图片圆角角度
            RoundedCorners roundedCorners = new RoundedCorners(8);
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300,
                    300);
            Glide.with(mContext).load(BaseProvider.BaseUrl + model.getImg()).apply(options).into(ivHome);

        }
    }

}
