package com.example.smartcityd_1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.AppClient;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.adapter.JzkAdapter;
import com.example.smartcityd_1.bean.Jzk;
import com.example.smartcityd_1.bean.UserInfo;
import com.example.smartcityd_1.bean.UserNum;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.OnClickItem;
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
 * @Create by 张瀛煜 on 2020/10/12 at 14:37
 */
public class JzkFragment extends Fragment {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private ListView listView;
    private Button btSave;

    public JzkFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public JzkFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static JzkFragment newInstance(AppHomeActivity appHomeActivity) {
        return new JzkFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.jzk_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("就诊人卡片");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("门诊预约1"));
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 2;
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("就诊卡1"));

            }
        });
        setVolley();
    }

    private void setShow() {
        setVollet_Jzk();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            setShow();
        }
        super.onHiddenChanged(hidden);

    }

    List<Jzk> jzks;
    public int index = 1;
    public Jzk jzk;

    private void setVollet_Jzk() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("showCaseById")
                .setJsonObject("ID", userInfo.getId())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        jzks = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<Jzk>>() {
                                }.getType());
                        JzkAdapter adapter = new JzkAdapter(getActivity(), jzks);
                        listView.setAdapter(adapter);
                        adapter.setOnClickItem(new OnClickItem() {
                            @Override
                            public void onClick(int position, String name) {
                                if (name.equals("1")) {
                                    jzk = jzks.get(position);
                                    appHomeActivity.switchFragemnt(appHomeActivity.map.get("就诊室"));

                                    //双箭头
                                } else {
                                    index = 1;
                                    jzk = jzks.get(position);
                                    appHomeActivity.switchFragemnt(appHomeActivity.map.get("就诊卡1"));
                                }
                            }
                        });
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
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
                        setShow();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();

    }

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
        listView = getView().findViewById(R.id.list_view);
        btSave = getView().findViewById(R.id.bt_save);
    }
}
