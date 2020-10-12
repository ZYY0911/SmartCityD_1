package com.example.smartcityd_1.activity;

import android.os.Bundle;
import android.view.View;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.VilageLsit;
import com.example.smartcityd_1.fragment1.valvagFragment;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.MyViewPager;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 16:38
 */
public class ValageActivity extends AppCompatActivity {
    private List<Fragment> fragments;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valage_layout);
        initView();
        fragments = new ArrayList<>();
        setVoley();
    }

    List<VilageLsit> vilageLsits;

    private void setVoley() {

        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("fpVillageList")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        vilageLsits = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<VilageLsit>>() {
                                }.getType());
                        for (int i = 0; i < vilageLsits.size(); i++) {

                            fragments.add(new valvagFragment(vilageLsits.get(i)));
                            viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                                @NonNull
                                @Override
                                public Fragment getItem(int position) {
                                    return fragments.get(position);
                                }

                                @Override
                                public int getCount() {
                                    return fragments.size();
                                }
                            });
                            viewPager.setPageTransformer(false, new MyViewPager(ValageActivity.this));
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }


    private void initView() {
        viewPager = findViewById(R.id.view_pager);
    }
}
