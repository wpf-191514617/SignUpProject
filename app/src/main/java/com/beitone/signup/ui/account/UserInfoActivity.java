package com.beitone.signup.ui.account;

import android.view.View;

import com.beitone.signup.R;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UserHelper;
import com.squareup.picasso.Picasso;

import java.io.File;

import cn.betatown.mobile.beitonelibrary.http.BaseProvider;

public class UserInfoActivity extends ImproveInformationActivity {

    @Override
    protected void onVerifyFace() {

    }

    @Override
    protected void selectImage() {

    }

    @Override
    protected void initUserInfoData() {
        setTitle("个人资料");
        btnSubmit.setVisibility(View.GONE);
        UserInfoResponse infoResponse = UserHelper.getInstance().getCurrentInfo();
        inputName.setShow(infoResponse.getName());
        inputIDCard.setShow(infoResponse.getCard_num());
        inputPhone.setShow(infoResponse.getPhone());
        inputProject.setShow(infoResponse.getB_project_name());
        inputConstructionTeam.setShow(infoResponse.getB_project_team_name());
        inputTypeOfWork.setShow(infoResponse.getType_of_work_name());
        Picasso.get().load(BaseProvider.BaseUrl + infoResponse.getFace_photo()).into(ivFaceAuth);
        Picasso.get().load(BaseProvider.BaseUrl + infoResponse.getCard_photo()).into(ivIdCard);
//        Glide.with(this).load(BaseProvider.BaseUrl + infoResponse.getFace_photo()).into(ivFaceAuth);
//        Glide.with(this).load(BaseProvider.BaseUrl + infoResponse.getCard_photo()).into(ivIdCard);
        switch (infoResponse.getType()) {
            case "1":
                inputIDCard.setVisibility(View.GONE);
                findViewById(R.id.lineIdcard).setVisibility(View.GONE);
                inputProject.setVisibility(View.GONE);
                findViewById(R.id.lineProject).setVisibility(View.GONE);
                inputConstructionTeam.setVisibility(View.GONE);
                findViewById(R.id.lineTeam).setVisibility(View.GONE);
                inputTypeOfWork.setInputLable("职务");
                findViewById(R.id.layoutBottom).setVisibility(View.GONE);
                findViewById(R.id.spacingView).setVisibility(View.GONE);
                inputTypeOfWork.setShow(infoResponse.getJob_name());
                break;
            case "2":
                inputConstructionTeam.setVisibility(View.GONE);
                findViewById(R.id.lineTeam).setVisibility(View.GONE);
                inputTypeOfWork.setInputLable("职务");
                findViewById(R.id.layoutBottom).setVisibility(View.GONE);
                findViewById(R.id.spacingView).setVisibility(View.GONE);
                inputTypeOfWork.setShow(infoResponse.getJob_name());
                break;
        }
    }

    @Override
    protected void loadProject() {

    }

    @Override
    protected void loadConstructionTeam() {

    }

    @Override
    protected void loadWorkTypeList() {

    }
}
