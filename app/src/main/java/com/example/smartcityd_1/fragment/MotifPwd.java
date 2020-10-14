package com.example.smartcityd_1.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 8:19
 */
public class MotifPwd extends Fragment {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private EditText etPwd;
    private EditText etNewPwd;
    private EditText etNewPwd2;
    private Button btSave;

    public MotifPwd() {

    }

    private AppHomeActivity appHomeActivity;

    public MotifPwd(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static MotifPwd newInstance(AppHomeActivity appHomeActivity) {
        return new MotifPwd(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.motif_pwd, container, false);
    }

    private MyCenterFragment centerFragment;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        title.setText("修改密码");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("个人中心"));
            }
        });
        setView();


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            etNewPwd.setText("");
            etNewPwd2.setText("");
            etPwd.setText("");
            setView();
        }
        super.onHiddenChanged(hidden);
    }

    private void setView() {
        centerFragment = (MyCenterFragment) appHomeActivity.map.get("个人中心");
        setVolley_PWd();
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPwd.getText()) || TextUtils.isEmpty(etNewPwd.getText())) {
                    Util.showDialog("原密码新密码不能为空", getActivity());
                    return;
                }
                String pwd = etPwd.getText().toString();
                String nwePwd = etNewPwd.getText().toString();
                String newPwd1 = etNewPwd2.getText().toString();
                if (!password.equals(pwd)) {
                    Util.showDialog("原密码不正确", getActivity());
                    return;
                }
                if (!newPwd1.equals(nwePwd)) {
                    Util.showDialog("两次密码不一致", getActivity());
                    return;
                }
                VolleyTo volleyTo = new VolleyTo();
                volleyTo.setUrl("setPwd")
                        .setJsonObject("userid", centerFragment.UserId)
                        .setJsonObject("password", newPwd1)
                        .setDialog(getActivity())
                        .setVolleyLo(new VolleyLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                if (jsonObject.optString("RESULT").equals("S")) {
                                    Util.showDialog("保存成功", getActivity());
                                } else {
                                    Util.showDialog("保存失败", getActivity());
                                }
                            }

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Util.showDialog("保存失败", getActivity());
                            }
                        }).start();
            }
        });
    }

    String password;

    private void setVolley_PWd() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getPwd")
                .setJsonObject("userid", centerFragment.UserId)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        password = jsonObject.optString("password");

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void initView() {
        itemChange = getView().findViewById(R.id.item_change);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        etPwd = getView().findViewById(R.id.et_pwd);
        etNewPwd = getView().findViewById(R.id.et_new_pwd);
        etNewPwd2 = getView().findViewById(R.id.et_new_pwd2);
        btSave = getView().findViewById(R.id.bt_save);
    }
}
