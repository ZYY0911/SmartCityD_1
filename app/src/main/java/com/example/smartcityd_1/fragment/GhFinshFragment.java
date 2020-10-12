package com.example.smartcityd_1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.bean.DutByDempart;
import com.example.smartcityd_1.bean.HospitalDemp;
import com.example.smartcityd_1.bean.HospitalList;
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
 * @Create by 张瀛煜 on 2020/10/12 at 15:25
 */
public class GhFinshFragment extends Fragment {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private TextView tvMsg;
    private Button btSave;

    public GhFinshFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public GhFinshFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static GhFinshFragment newInstance(AppHomeActivity appHomeActivity) {
        return new GhFinshFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gh_finsih_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("预约挂号");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("就诊时间"));
            }
        });
        setView();
        // ks = getIntent().getStringExtra("ks");
        //        carInfo = (CarInfo) getIntent().getSerializableExtra("carInfos");
        //        dutByDempart = (DutByDempart) getIntent().getSerializableExtra("doctor");
        //        VolleyTo volleyTo = new VolleyTo();
        //        volleyTo.setUrl("appointment")
        //                //{"pid":"371402199902041133","name":"赵子涵","phone":"13505347777","doctorId":2,"appTime":"2020-10-2 周四 下午14：00"}
        //                .setJsonObject("pid", carInfo.getID())
        //                .setJsonObject("name", carInfo.getName())
        //                .setJsonObject("phone", carInfo.getTel())
        //                .setJsonObject("doctorId", dutByDempart.getDoctorId())
        //                .setJsonObject("appTime", dutByDempart.getTime())
        //                .setVolleyLo(new VolleyLo() {
        //                    @Override
        //                    public void onResponse(JSONObject jsonObject) {
        //                        if (jsonObject.optString("RESULT").equals("S")) {
        //                            tvMsg.setText("预约结果"+"\r\n\r\n"+
        //                                    "预约科室：" + ks + "\r\n"
        //                                    + "门诊类型：" + dutByDempart.getType() + "\r\n"
        //                                    + "预约时间：" + jsonObject.optJSONObject("data")
        //                                    .optString("appTime"));
        //                        } else {
        //                            Utils.showToast("预约失败", YyghSuccsssFulActivity.this);
        //                        }
        //                    }
        //
        //                    @Override
        //                    public void onErrorResponse(VolleyError volleyError) {
        //                        Utils.showToast("预约失败", YyghSuccsssFulActivity.this);
        //
        //                    }
        //                }).start();
        //
        //        itemChange.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                finish();
        //            }
        //        });
        //        title.setText("预约挂号");
        //
        //        tvOnline.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                AppClient.finallAll2();
        //            }
        //        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("门诊预约"));
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (
                !hidden
        ) {
            setView();
        }
        super.onHiddenChanged(hidden);

    }

    public HospitalList hospitalList;
    public HospitalDemp hospitalDemp;
    public Jzk jzk;
    private DutByDempart dutByDempart;

    private void setView() {
        hospitalList = ((MzFragemnt) (appHomeActivity.map.get("门诊预约"))).hospitalList;
        hospitalDemp = ((ChooseDempartFragment) (appHomeActivity.map.get("就诊室"))).hospitalDemp;
        jzk = ((JzkFragment) (appHomeActivity.map.get("就诊卡"))).jzk;
        dutByDempart = ((ChooseTimeFragment) (appHomeActivity.map.get("就诊时间"))).dutByDempart;
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("appointment")
                //{"pid":"371402199902041133","name":"赵子涵","phone":"13505347777","doctorId":2,"appTime":"2020-10-2 周四 下午14：00"}
                .setJsonObject("pid", jzk.getID())
                .setJsonObject("name", jzk.getName())
                .setJsonObject("phone", jzk.getTel())
                .setJsonObject("doctorId", dutByDempart.getDoctorId())
                .setJsonObject("appTime", dutByDempart.getTime())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if (jsonObject.optString("RESULT").equals("S")) {
                            tvMsg.setText("预约结果" + "\r\n\r\n" +
                                    "预约科室：" + hospitalDemp.getDeptName() + "\r\n"
                                    + "门诊类型：" + dutByDempart.getType() + "\r\n"
                                    + "预约时间：" + jsonObject.optJSONObject("data")
                                    .optString("appTime"));
                        } else {
                            Util.showToast("预约失败", getActivity());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Util.showToast("预约失败", getActivity());

                    }
                }).start();


    }

    private void initView() {
        itemChange = getView().findViewById(R.id.item_change);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        tvMsg = getView().findViewById(R.id.tv_msg);
        btSave = getView().findViewById(R.id.bt_save);
    }
}
