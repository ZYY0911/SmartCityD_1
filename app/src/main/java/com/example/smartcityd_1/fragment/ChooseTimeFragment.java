package com.example.smartcityd_1.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.adapter.GhAdapter;
import com.example.smartcityd_1.bean.DutByDempart;
import com.example.smartcityd_1.bean.HospitalDemp;
import com.example.smartcityd_1.bean.HospitalList;
import com.example.smartcityd_1.bean.Jzk;
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
 * @Create by 张瀛煜 on 2020/10/12 at 15:13
 */
public class ChooseTimeFragment extends Fragment {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private TextView tvZj;
    private TextView tvPt;
    private TextView tvZjDate;
    private LinearLayout layoutPt;
    private ListView listView;

    public ChooseTimeFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public ChooseTimeFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static ChooseTimeFragment newInstance(AppHomeActivity appHomeActivity) {
        return new ChooseTimeFragment(appHomeActivity);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_teim_fragment, container, false);

    }

    public HospitalList hospitalList;
    public HospitalDemp hospitalDemp;
    public Jzk jzk;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("预约挂号");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("就诊室"));
            }
        });
        tvZj.setTextColor(Color.RED);
        setView();
    }

    private void setView() {
        hospitalList = ((MzFragemnt) (appHomeActivity.map.get("门诊预约"))).hospitalList;
        hospitalDemp = ((ChooseDempartFragment) (appHomeActivity.map.get("就诊室"))).hospitalDemp;
        jzk = ((JzkFragment) (appHomeActivity.map.get("就诊卡"))).jzk;
        tvZj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvZj.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvPt.setTextColor(Color.BLACK);
                tvZj.setBackgroundResource(R.drawable.text_line);
                tvPt.setBackgroundResource(R.drawable.text_no_line);
                tvZjDate.setVisibility(View.VISIBLE);
                layoutPt.setVisibility(View.GONE);
            }
        });
        tvPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvZj.setTextColor(Color.BLACK);
                tvPt.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvPt.setBackgroundResource(R.drawable.text_line);
                tvZj.setBackgroundResource(R.drawable.text_no_line);
                tvZjDate.setVisibility(View.GONE);
                layoutPt.setVisibility(View.VISIBLE);
            }
        });
        setVoley();
    }

    List<DutByDempart> dutByDemparts;
    public DutByDempart dutByDempart;

    private void setVoley() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getDutyByDepartmentId")
                .setJsonObject("hospitalId", hospitalList.getHospitalId())
                .setJsonObject("departmentId", hospitalDemp.getDeptId())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        dutByDemparts = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<DutByDempart>>() {
                                }.getType());
                        Log.i("aaa", "onResponse: " + dutByDemparts.size());
                        GhAdapter adapter = new GhAdapter(getActivity(), dutByDemparts, hospitalDemp.getDeptName());
                        listView.setAdapter(adapter);
                        adapter.setOnClickItem(new OnClickItem() {
                            @Override
                            public void onClick(int position, String name) {
                                dutByDempart = dutByDemparts.get(position);
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("提示\n预约后不可修改!");
                                builder.setMessage("您确定要预约" + name + "\n" + dutByDempart.getTime() + "");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        appHomeActivity.switchFragemnt(appHomeActivity.map.get("挂号"));
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
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
        tvZj = getView().findViewById(R.id.tv_zj);
        tvPt = getView().findViewById(R.id.tv_pt);
        tvZjDate = getView().findViewById(R.id.tv_zj_date);
        layoutPt = getView().findViewById(R.id.layout_pt);
        listView = getView().findViewById(R.id.list_view);
    }
}


