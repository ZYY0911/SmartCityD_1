package com.example.smartcityd_1.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.adapter.HospitalAdapter;
import com.example.smartcityd_1.bean.HospitalList;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.MyGirdView;
import com.example.smartcityd_1.util.OnClickItem;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 11:33
 */
public class MzFragemnt extends Fragment {
    private LinearLayout titleLayout;
    private EditText etSearch;
    private Button btSubmit;
    private MyGirdView mzGird;

    public MzFragemnt() {

    }

    private AppHomeActivity appHomeActivity;

    public MzFragemnt(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static MzFragemnt newInstance(AppHomeActivity appHomeActivity) {
        return new MzFragemnt(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mzfragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setVolley();
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    return true;
                }
                return false;
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etSearch.getText())){
                    setHospialAdapter();
                    return;
                }
                List<HospitalList> hospitalListList = new ArrayList<>();
                for (int i = 0; i < hospitalLists.size(); i++) {
                    HospitalList hospitalList = hospitalLists.get(i);
                    if (hospitalList.getHospitalName().contains(etSearch.getText().toString())) {
                        hospitalListList.add(hospitalList);
                    }
                }

                HospitalAdapter adapter = new HospitalAdapter(getActivity(), hospitalListList);
                mzGird.setAdapter(adapter);
//        mzGird.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                hospitalList = hospitalLists.get(position);
//                appHomeActivity.switchFragemnt(appHomeActivity.map.get("门诊预约1"));
//            }
//        });
                adapter.setOnClickItem(new OnClickItem() {
                    @Override
                    public void onClick(int position, String name) {
                        hospitalList = hospitalLists.get(position);
                        appHomeActivity.switchFragemnt(appHomeActivity.map.get("门诊预约1"));
                    }
                });
            }
        });
    }

    private void setVolley() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("hospitalList")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        hospitalLists = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<HospitalList>>() {
                                }.getType());
                        setHospialAdapter();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    HospitalList hospitalList;

    private void setHospialAdapter() {
        HospitalAdapter adapter = new HospitalAdapter(getActivity(), hospitalLists);
        mzGird.setAdapter(adapter);
//        mzGird.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                hospitalList = hospitalLists.get(position);
//                appHomeActivity.switchFragemnt(appHomeActivity.map.get("门诊预约1"));
//            }
//        });
        adapter.setOnClickItem(new OnClickItem() {
            @Override
            public void onClick(int position, String name) {
                hospitalList = hospitalLists.get(position);
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("门诊预约1"));
            }
        });
    }

    List<HospitalList> hospitalLists;


    private void initView() {
        titleLayout = getView().findViewById(R.id.title_layout);
        etSearch = getView().findViewById(R.id.et_search);
        btSubmit = getView().findViewById(R.id.bt_submit);
        mzGird = getView().findViewById(R.id.mz_gird);
    }
}
