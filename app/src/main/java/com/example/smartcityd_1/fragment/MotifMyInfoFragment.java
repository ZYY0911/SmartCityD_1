package com.example.smartcityd_1.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.activity.MotifImage;
import com.example.smartcityd_1.bean.UserInfo;
import com.example.smartcityd_1.net.VolleyImage;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyLoImage;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 8:00
 */
public class MotifMyInfoFragment extends Fragment {
    private AppHomeActivity appHomeActivity;
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private LinearLayout layoutInfo;
    private EditText etName;
    private EditText etSex;
    private EditText etTel;
    private EditText etIds;
    private ImageView ivPhoto;
    private Button btSave;

    public MotifMyInfoFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public MotifMyInfoFragment() {
    }

    public static MotifMyInfoFragment newInstance(AppHomeActivity appHomeActivity) {
        return new MotifMyInfoFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.motif_my_info, container, false);
    }

    UserInfo userInfo;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("个人信息");
        setView();
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("个人中心"));
            }
        });
    }

    MyCenterFragment myCenterFragment;

    private void setView() {
        myCenterFragment = (MyCenterFragment) appHomeActivity.map.get("个人中心");
        userInfo = myCenterFragment.userInfo;
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
        etName.setText(userInfo.getName());
        etTel.setText(userInfo.getPhone());
        etIds.setText(userInfo.getId());
        etSex.setText(userInfo.getSex());
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyTo volleyTo = new VolleyTo();
                volleyTo.setUrl("setUserInfo")
                        //{userid:"1","name":"","avatar":"","phone":"phone","id":"1","gender":""}
                        .setJsonObject("userid", myCenterFragment.UserId)
                        .setJsonObject("name", etName.getText().toString())
                        .setJsonObject("avatar", ivPhoto.getTag() == null ? "" : ivPhoto.getTag().toString())
                        .setJsonObject("phone", etTel.getText().toString())
                        .setJsonObject("id", "")
                        .setJsonObject("gender", etSex.getText().toString())
                        .setDialog(getActivity())
                        .setVolleyLo(new VolleyLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                if (jsonObject.optString("RESULT").equals("S")) {
                                    Util.showDialog("修改成功", getActivity());
                                } else {
                                    Util.showDialog("修改失败", getActivity());
                                }
                            }

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                            }
                        }).start();
            }
        });
        layoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MotifImage.class);
                intent.putExtra("infos", userInfo.getAvatar());
                startActivityForResult(intent, 1);
            }
        });
        StringBuilder stringBuilder = new StringBuilder(userInfo.getId());
        etIds.setText(stringBuilder.replace(3, 14, "************").toString());

    }


    private Integer images[] = {R.mipmap.user1, R.mipmap.user2,
            R.mipmap.user3, R.mipmap.user4, R.mipmap.user5, R.mipmap.user6, R.mipmap.user7, R.mipmap.user8};

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == getActivity().RESULT_OK) {
                    ivPhoto.setImageResource(images[data.getIntExtra("index", 0)]);
                    ivPhoto.setTag(data.getStringExtra("date").equals("")?ivPhoto.getTag().toString()
                            :data.getStringExtra("date"));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            setView();
        }
        super.onHiddenChanged(hidden);
    }

    private void initView() {
        itemChange = getView().findViewById(R.id.item_change);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        layoutInfo = getView().findViewById(R.id.layout_info);
        etName = getView().findViewById(R.id.et_name);
        etSex = getView().findViewById(R.id.et_sex);
        etTel = getView().findViewById(R.id.et_tel);
        etIds = getView().findViewById(R.id.et_ids);
        ivPhoto = getView().findViewById(R.id.iv_photo);
        btSave = getView().findViewById(R.id.bt_save);
    }
}
