package com.example.smartcityd_1.fragment;

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
import com.example.smartcityd_1.adapter.ChooseDempartAdapter;
import com.example.smartcityd_1.bean.HospitalDemp;
import com.example.smartcityd_1.bean.HospitalList;
import com.example.smartcityd_1.net.VolleyLo;
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
 * @Create by 张瀛煜 on 2020/10/12 at 15:04
 */
public class ChooseDempartFragment extends Fragment {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private ListView listView;

    public ChooseDempartFragment() {
    }

    private AppHomeActivity appHomeActivity;

    public ChooseDempartFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static ChooseDempartFragment newInstance(AppHomeActivity appHomeActivity) {
        return new ChooseDempartFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_fragment, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("科室选择");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("就诊卡"));
            }
        });
        setView();
    }

    List<HospitalDemp> hospitalDemps;
    private HospitalList hospitalList;
    public HospitalDemp hospitalDemp;

    private void setView() {
        hospitalList = ((MzFragemnt) (appHomeActivity.map.get("门诊预约"))).hospitalList;
        setVolley_Demp();
    }

    private void setVolley_Demp() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getHospitalDepartmentByHospitalId")
                .setDialog(getActivity())
                .setJsonObject("hospitalId", hospitalList.getHospitalId())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        hospitalDemps = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<HospitalDemp>>() {
                                }.getType());
                        listView.setAdapter(new ChooseDempartAdapter(getActivity(), hospitalDemps));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                hospitalDemp = hospitalDemps.get(position);
                                appHomeActivity.switchFragemnt(appHomeActivity.map.get("就诊时间"));

                            }
                        });

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
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
        listView = getView().findViewById(R.id.list_view);
    }
}

