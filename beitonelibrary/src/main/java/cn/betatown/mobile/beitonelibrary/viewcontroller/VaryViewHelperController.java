/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.betatown.mobile.beitonelibrary.viewcontroller;

import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.betatown.mobile.beitonelibrary.R;


public class VaryViewHelperController {

    private IVaryViewHelper helper;

    private AnimationDrawable animationDrawable;

    private ImageView ivLoading;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    public VaryViewHelperController(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    public void switchView(String msg){
        switchView(msg , 0);
    }

    public void switchView(String msg, int drawableId){
        switchView(msg , drawableId , null);
    }


    public void switchGifView(String msg, int drawableId, View.OnClickListener onClickListener){
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!TextUtils.isEmpty(msg)) {
            textView.setText(msg);
        }
        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        if (drawableId != 0){
            Glide.with(layout.getContext()).asGif().load(drawableId).into(imageView);
        }

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        switchContentView(layout);
    }


    public void switchView(String msg, int drawableId, View.OnClickListener onClickListener){
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!TextUtils.isEmpty(msg)) {
            textView.setText(msg);
        }
        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        if (drawableId != 0){
            imageView.setImageResource(drawableId);
        }

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        switchContentView(layout);
    }

    public void switchContentView(View contentView){
        helper.showLayout(contentView);
    }

    public void restoreView() {
        helper.restoreView();
    }
}
