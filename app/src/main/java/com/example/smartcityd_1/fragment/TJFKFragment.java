package com.example.smartcityd_1.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 8:53
 */
public class TJFKFragment extends Fragment {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private EditText etMsg;
    private TextView tvNum;
    private Button btSave;

    public TJFKFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public TJFKFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static TJFKFragment newInstance(AppHomeActivity appHomeActivity) {
        return new TJFKFragment(appHomeActivity);
    }

    MyCenterFragment centerFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tjfk_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        centerFragment = (MyCenterFragment) appHomeActivity.map.get("个人中心");
        title.setText("意见反馈");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("个人中心"));
            }
        });
        etMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                        String leangh = s.toString();
                        if (leangh.length() >= 151) {
                            Util.showToast("只能输入150字", getActivity());
                            etMsg.setText(leangh.substring(0, 150));
                            etMsg.setSelection(150);
                            return;
                        }
                        tvNum.setText(leangh.length() + "/150字");
                }
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //{"userid":"1","content":"内容","time":"yyyy.MM.dd HH:mm:ss"}
                VolleyTo volleyTo = new VolleyTo();
                volleyTo.setUrl("publicOpinion")
                        .setJsonObject("userid", centerFragment.UserId)
                        .setJsonObject("content", etMsg.getText().toString())
                        .setJsonObject("time", Util.simpleDate("yyyy.MM.dd HH:mm:ss", new Date()))
                        .setVolleyLo(new VolleyLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                if (jsonObject.optString("RESULT").equals("S")) {
                                    Util.showDialog("保存成功", getActivity());
                                    etMsg.setText("");
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden){
            etMsg.setText("");
        }
        super.onHiddenChanged(hidden);

    }

    private void setView() {

    }

    private void initView() {
        itemChange = getView().findViewById(R.id.item_change);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        etMsg = getView().findViewById(R.id.et_msg);
        tvNum = getView().findViewById(R.id.tv_num);
        btSave = getView().findViewById(R.id.bt_save);
    }
}

