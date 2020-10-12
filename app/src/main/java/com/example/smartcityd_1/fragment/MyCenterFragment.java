package com.example.smartcityd_1.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.AppClient;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.activity.GlideActivity;
import com.example.smartcityd_1.bean.UserInfo;
import com.example.smartcityd_1.bean.UserNum;
import com.example.smartcityd_1.net.VolleyImage;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyLoImage;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/11 at 21:03
 */
public class MyCenterFragment extends Fragment {

    private AppHomeActivity appHomeActivity;
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private ImageView ivPhoto;
    private TextView tvName;
    private LinearLayout layoutInfo;
    private LinearLayout layoutPwd;
    private LinearLayout layoutOrder;
    private LinearLayout layoutYjfk;
    private Button btSave;

    public MyCenterFragment() {
    }

    public MyCenterFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static MyCenterFragment newInstance(AppHomeActivity appHomeActivity) {
        return new MyCenterFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_center_layout, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.itemChangeClick();
            }
        });
        title.setText("个人中心");
        setVolley();
        layoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("个人信息"));
            }
        });
        layoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("我的订单"));
            }
        });
        layoutPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("修改密码"));
            }
        });
        layoutYjfk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("意见反馈"));
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),GlideActivity.class));
                appHomeActivity.finish();
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            setVolley();
        }
        super.onHiddenChanged(hidden);

    }

    List<UserNum> userNums;

    private void setVolley() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getUsers")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        userNums = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<UserNum>>() {
                                }.getType());
                        setVolley_Info();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    public UserInfo userInfo;

    private void setVolley_Info() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getUserInfo")
                .setJsonObject("userid", getUserId(AppClient.username))
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        userInfo = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                , UserInfo.class);
                        setMyInfos();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();

    }

    private void setMyInfos() {
        VolleyImage volleyImage = new VolleyImage();
        volleyImage.setUrl(userInfo.getAvatar())
                .setVolleyLoImage(new VolleyLoImage() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        ivPhoto.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        tvName.setText("昵称：" + userInfo.getName());
        UserId = getUserId(AppClient.username);
    }

    public String UserId;


    private String getUserId(String username) {
        for (int i = 0; i < userNums.size(); i++) {
            UserNum userNum = userNums.get(i);
            if (userNum.getUsername().equals(username)) {
                return userNum.getUserid();
            }
        }
        return "1";
    }


    private void initView() {
        itemChange = getView().findViewById(R.id.item_change);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        ivPhoto = getView().findViewById(R.id.iv_photo);
        tvName = getView().findViewById(R.id.tv_name);
        layoutInfo = getView().findViewById(R.id.layout_info);
        layoutPwd = getView().findViewById(R.id.layout_pwd);
        layoutOrder = getView().findViewById(R.id.layout_order);
        layoutYjfk = getView().findViewById(R.id.layout_yjfk);
        btSave = getView().findViewById(R.id.bt_save);
    }
}
