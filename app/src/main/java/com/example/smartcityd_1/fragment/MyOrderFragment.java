package com.example.smartcityd_1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.activity.OrderDetailsActivity;
import com.example.smartcityd_1.adapter.OrderAdapter;
import com.example.smartcityd_1.bean.OrderTitle;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 8:29
 */
public class MyOrderFragment extends Fragment {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private ListView listOrder;

    public MyOrderFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public MyOrderFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static MyOrderFragment newInstance(AppHomeActivity appHomeActivity) {
        return new MyOrderFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("我的订单");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("个人中心"));
            }
        });
        setView();
    }

    List<String> strings;

    private void setView() {
        strings = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getAllOrderType")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray(Util.Rows);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            strings.add(jsonArray.optString(i));
                        }
                        setVolley_OrderList();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    List<OrderTitle> orderTitles;

    private void setVolley_OrderList() {
        orderTitles = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            VolleyTo volleyTo = new VolleyTo();
            final int finalI = i;
            volleyTo.setUrl("getOrderByType")
                    .setJsonObject("type", strings.get(i))
                    .setDialog(getActivity())
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            List<OrderTitle> orderTitles1 = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                    , new TypeToken<List<OrderTitle>>() {
                                    }.getType());
                            orderTitles.addAll(orderTitles1);
                            if (finalI == strings.size() - 1) {
                                listOrder.setAdapter(new OrderAdapter(getActivity(), orderTitles));
                                listOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                                        intent.putExtra("date", orderTitles.get(position));
                                        startActivity(intent);
                                    }
                                });
                            }

                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
        }
    }


    private void initView() {
        itemChange = getView().findViewById(R.id.item_change);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        listOrder = getView().findViewById(R.id.list_order);
    }
}
