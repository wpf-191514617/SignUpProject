package com.beitone.signup.ui.home;

import android.widget.ImageView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseRecyclerFragment;
import com.beitone.signup.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseRecyclerAdapter;
import cn.betatown.mobile.beitonelibrary.adapter.recyclerview.BaseViewHolderHelper;

public class HomeListFragment extends BaseRecyclerFragment {
    @Override
    protected void onRefresh() {
        loadData();
    }

    @Override
    protected void onLoadMore() {

    }

    private void loadData() {
        refreshLayout.setEnableLoadMore(false);
        List<String> data = new ArrayList<>();
        for (char i = 'A'; i < 'Z'; i++) {
            data.add("");
        }
        setData(data);
    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        return new HomeListAdapter(recyclerView);
    }

    class HomeListAdapter extends BaseRecyclerAdapter<String> {

        private String image = "http://t8.baidu.com/it/u=2247852322," +
                "986532796&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1592982013&t" +
                "=6b9665d35290d10610cb77598cb5d3f3";

        public HomeListAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_home);
        }

        @Override
        protected void fillData(BaseViewHolderHelper helper, int position, String model) {
            ImageView ivHome = helper.getImageView(R.id.ivHome);
            //设置图片圆角角度
            RoundedCorners roundedCorners= new RoundedCorners(8);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

            Glide.with(mContext).load(image).apply(options).into(ivHome);

        }
    }

}
