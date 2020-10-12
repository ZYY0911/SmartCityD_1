package com.example.smartcityd_1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.activity.ReadNewActivity;
import com.example.smartcityd_1.activity.SearchNewsActivity;
import com.example.smartcityd_1.activity.UrlService;
import com.example.smartcityd_1.adapter.AllServiceAdapter;
import com.example.smartcityd_1.adapter.HomeServiceAdapter;
import com.example.smartcityd_1.bean.ServiceInfo;
import com.example.smartcityd_1.bean.ServiceWeight;
import com.example.smartcityd_1.dialog.SearchDialog;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
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
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 9:06
 */
public class AllServiceFragment extends Fragment {
    private LinearLayout titleLayout;
    private EditText etSearch;
    private Button btSubmit;
    private ExpandableListView expandList;

    public AllServiceFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public AllServiceFragment(AppHomeActivity appHomeActivity) {

        this.appHomeActivity = appHomeActivity;
    }

    public static AllServiceFragment newInstance(AppHomeActivity appHomeActivity) {
        return new AllServiceFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_service_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setVolley_Type();
//        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//
//                    return true;
//                }
//                return false;
//            }
//        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDialog dialog = new SearchDialog(etSearch.getText().toString());
                dialog.show(getChildFragmentManager(), "");
            }
        });
    }

    List<String> strings;

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

    Map<String, List<ServiceInfo>> map;

    private void setVolley_All() {
        map = new HashMap<>();
        for (int i = 0; i < strings.size(); i++) {
            VolleyTo volleyTo = new VolleyTo();
            final int finalI = i;
            volleyTo.setUrl("getServiceByType")
                    .setJsonObject("serviceType", strings.get(i))
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            List<ServiceInfo> serviceInfos = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                    , new TypeToken<List<ServiceInfo>>() {
                                    }.getType());
                            map.put(strings.get(finalI), serviceInfos);
                            if (map.size() == strings.size() - 1) {
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
        AllServiceAdapter allServiceAdapter = new AllServiceAdapter(strings, map);
        expandList.setAdapter(allServiceAdapter);
        allServiceAdapter.setOnClickItem(new OnClickItem() {
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
                    intent.putExtra("name",name);
                    startActivity(intent);
                }

                // if (name.equals("更多服务")) {
                //                                    appHomeActivity.switchFragemnt(appHomeActivity.map.get("全部服务"));
                //                                } else if (name.equals("实时天气")) {
                //                                    appHomeActivity.switchFragemnt(appHomeActivity.map.get("实时天气"));
                //                                } else if (name.equals("门诊预约")) {
                //                                    appHomeActivity.switchFragemnt(appHomeActivity.map.get("门诊预约"));
                //                                } else {
                //                                    Intent intent = new Intent(getActivity(), UrlService.class);
                //                                    intent.putExtra("info", Integer.parseInt(serviceWeights.get(position).getId()));
                //                                    intent.putExtra("name",name);
                //                                    startActivity(intent);
                //                                }
            }
        });
    }


    private void initView() {
        titleLayout = getView().findViewById(R.id.title_layout);
        etSearch = getView().findViewById(R.id.et_search);
        btSubmit = getView().findViewById(R.id.bt_submit);
        expandList = getView().findViewById(R.id.expand_list);
        expandList.setGroupIndicator(null);
    }
}
