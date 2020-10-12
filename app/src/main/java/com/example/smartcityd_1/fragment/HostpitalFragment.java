package com.example.smartcityd_1.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.bean.HospitalList;
import com.example.smartcityd_1.bean.ImagesHospital;
import com.example.smartcityd_1.net.VolleyImage;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyLoImage;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 14:27
 */
public class HostpitalFragment extends Fragment {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private ViewFlipper viewFlipper;
    private TextView tvMsg;
    private Button btSave;

    public HostpitalFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public HostpitalFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static HostpitalFragment newInstance(AppHomeActivity appHomeActivity) {
        return new HostpitalFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hostpital_fragment, container, false);
    }

    private HospitalList hospitalList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("门诊预约");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("门诊预约"));
            }
        });
        initShow();
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("就诊卡"));

            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            initShow();
        }
        super.onHiddenChanged(hidden);
    }

    private void initShow() {
        hospitalList = ((MzFragemnt) (appHomeActivity.map.get("门诊预约"))).hospitalList;
        setImage();
        tvMsg.setText(hospitalList.getDesc());
    }


    ImagesHospital imagesHospital;
    List<String> strings;

    private void setImage() {
        strings = new ArrayList<>();
        viewFlipper.removeAllViews();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getImagesByHospitalId")
                .setJsonObject("hospitalId", hospitalList.getHospitalId())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        imagesHospital = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                , ImagesHospital.class);
                        strings.add(imagesHospital.getImage1());
                        strings.add(imagesHospital.getImage2());
                        strings.add(imagesHospital.getImage3());
                        strings.add(imagesHospital.getImage4());
                        for (int i = 0; i < strings.size(); i++) {
                            final ImageView imageView = new ImageView(getActivity());
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            VolleyImage volleyImage = new VolleyImage();
                            volleyImage.setUrl(strings.get(i))
                                    .setVolleyLoImage(new VolleyLoImage() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            imageView.setImageBitmap(bitmap);
                                            viewFlipper.addView(imageView);
                                        }

                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {

                                        }
                                    }).start();
                        }

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void initView() {
        itemChange = getView().findViewById(R.id.item_change);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        viewFlipper = getView().findViewById(R.id.view_flipper);
        tvMsg = getView().findViewById(R.id.tv_msg);
        btSave = getView().findViewById(R.id.bt_save);
    }
}
