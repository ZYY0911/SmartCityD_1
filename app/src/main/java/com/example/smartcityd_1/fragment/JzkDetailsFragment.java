package com.example.smartcityd_1.fragment;

import android.os.Bundle;
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
import com.example.smartcityd_1.bean.Jzk;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 14:52
 */
public class JzkDetailsFragment extends Fragment {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private EditText tvName;
    private EditText tvSex;
    private EditText tvId;
    private EditText tvBirth;
    private EditText tvTel;
    private EditText tvAddress;
    private Button btSave;

    public JzkDetailsFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public JzkDetailsFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static JzkDetailsFragment newInstance(AppHomeActivity appHomeActivity) {
        return new JzkDetailsFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.jzk_details_fragment, container, false);
    }

    int index;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("就诊卡");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("就诊卡"));
            }
        });
        setShow();
    }

    private void setShow() {
        index = ((JzkFragment) (appHomeActivity.map.get("就诊卡"))).index;
        title.setText(index == 1 ? "完善就诊卡" : "创建就诊卡");
        if (index == 1) {
            final Jzk jzk = ((JzkFragment) (appHomeActivity.map.get("就诊卡"))).jzk;
            tvName.setText(jzk.getName());
            tvAddress.setText(jzk.getAddress());
            tvBirth.setText(jzk.getBirthday());
            tvSex.setText(jzk.getSex());
            tvTel.setText(jzk.getTel());
            tvId.setText(jzk.getID());
            tvId.setEnabled(false);
            btSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VolleyTo volleyTo = new VolleyTo();
                    volleyTo.setUrl("updateCase")
                            //{"caseid1":"1111","caseid2":"2222","name"="李昕","sex"="男","ID"="371402201006060606","birthday"="2020-6-6","tel"="77777777777","address"="紫薇园"}
                            .setJsonObject("caseid1", jzk.getCaseId())
                            .setJsonObject("caseid2", "")
                            .setJsonObject("name", tvName.getText().toString())
                            .setJsonObject("sex", tvSex.getText().toString())
                            .setJsonObject("ID", tvId.getText().toString())
                            .setJsonObject("birthday", tvBirth.getText().toString())
                            .setJsonObject("tel", tvTel.getText().toString())
                            .setJsonObject("address", tvAddress.getText().toString())
                            .setVolleyLo(new VolleyLo() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    if (jsonObject.optString("RESULT").equals("S")) {
                                        Util.showToast("修改成功", getActivity());
                                        appHomeActivity.switchFragemnt(appHomeActivity.map.get("就诊卡"));
                                    } else {
                                        Util.showToast("修改失败", getActivity());
                                    }
                                }

                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Util.showToast("修改失败", getActivity());

                                }
                            }).start();
                }
            });
        } else {
            tvId.setEnabled(true);
            tvId.setText("");
            tvTel.setText("");
            tvSex.setText("");
            tvBirth.setText("");
            tvAddress.setText("");
            tvName.setText("");
            VolleyTo volleyTo = new VolleyTo();
            //{name:"杨仪涵",sex:"女",ID:"371402201002078824",birthday:"2010-2-7",tel:"15905346666",address:"八一小区"}
            volleyTo.setUrl("createCase")
                    .setJsonObject("name", tvName.getText().toString())
                    .setJsonObject("sex", tvSex.getText().toString())
                    .setJsonObject("ID", tvId.getText().toString())
                    .setJsonObject("birthday", tvBirth.getText().toString())
                    .setJsonObject("tel", tvTel.getText().toString())
                    .setJsonObject("address", tvAddress.getText().toString())
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            if (jsonObject.optString("RESULT").equals("S")) {
                                Util.showToast("添加成功", getActivity());
                                appHomeActivity.switchFragemnt(appHomeActivity.map.get("就诊卡"));

                            } else {
                                Util.showToast("添加失败", getActivity());
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Util.showToast("添加失败", getActivity());
                        }
                    }).start();
        }


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            setShow();
        }
        super.onHiddenChanged(hidden);

    }

    private void initView() {
        itemChange = getView().findViewById(R.id.item_change);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        tvName = getView().findViewById(R.id.tv_name);
        tvSex = getView().findViewById(R.id.tv_sex);
        tvId = getView().findViewById(R.id.tv_id);
        tvBirth = getView().findViewById(R.id.tv_birth);
        tvTel = getView().findViewById(R.id.tv_tel);
        tvAddress = getView().findViewById(R.id.tv_address);
        btSave = getView().findViewById(R.id.bt_save);
    }
}
