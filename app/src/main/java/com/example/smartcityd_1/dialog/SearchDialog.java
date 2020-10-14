package com.example.smartcityd_1.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.activity.ReadNewActivity;
import com.example.smartcityd_1.activity.UrlService;
import com.example.smartcityd_1.adapter.AllServiceItemAdapter;
import com.example.smartcityd_1.bean.ServiceInfo;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.MyGirdView;
import com.example.smartcityd_1.util.OnClickItem;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 9:37
 */
public class SearchDialog extends DialogFragment {
    private String msg;
    private MyGirdView searchGird;
    private TextView tvFinish;

    private AppHomeActivity appHomeActivity;

    public SearchDialog(String msg, AppHomeActivity appHomeActivity) {
        this.msg = msg;
        this.appHomeActivity = appHomeActivity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.search_dialog, container, false);

    }

    List<String> strings;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setVolley_Type();
        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    private void setVolley_Type() {
        strings = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getAllServiceType")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray(Util.Rows);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            strings.add(jsonArray.optString(i));
                        }
                        setVolley_All();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    List<ServiceInfo> serviceInfos;

    private void setVolley_All() {
        serviceInfos = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            VolleyTo volleyTo = new VolleyTo();
            final int finalI = i;
            if (i == strings.size() - 1) {
                volleyTo.setDialog(getContext());
            }
            volleyTo.setUrl("getServiceByType")
                    .setJsonObject("serviceType", strings.get(i))
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            List<ServiceInfo> serviceInfosList = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                    , new TypeToken<List<ServiceInfo>>() {
                                    }.getType());
                            serviceInfos.addAll(serviceInfosList);
                            if (finalI == strings.size() - 1) {
                                setAdapter();
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
        }
    }

    private void setAdapter() {
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        for (int i = 0; i < serviceInfos.size(); i++) {
            ServiceInfo serviceInfo = serviceInfos.get(i);
            if (serviceInfo.getServiceName().contains(msg)) {
                serviceInfoList.add(serviceInfo);
            }
        }
        AllServiceItemAdapter allServiceItemAdapter = new AllServiceItemAdapter(getContext(), serviceInfoList);
        searchGird.setAdapter(allServiceItemAdapter);
        allServiceItemAdapter.setOnClickItem(new OnClickItem() {
            @Override
            public void onClick(int position, String name) {
                if (name.equals("新闻中心")) {
                    Intent intent = new Intent(getActivity(), ReadNewActivity.class);
                    intent.putExtra("info", "新闻");
                    startActivity(intent);
                } else if (name.equals("实时天气")) {
                    appHomeActivity.switchFragemnt(appHomeActivity.map.get("实时天气"));
                } else if (name.equals("门诊预约")) {
                    appHomeActivity.switchFragemnt(appHomeActivity.map.get("门诊预约"));
                } else {
                    Intent intent = new Intent(getActivity(), UrlService.class);
                    intent.putExtra("info", position);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
                getDialog().dismiss();
            }
        });
    }

    private void initView() {
        searchGird = getView().findViewById(R.id.search_gird);
        tvFinish = getView().findViewById(R.id.tv_finish);
    }
}
