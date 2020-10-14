package com.example.smartcityd_1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.fragment.AllServiceFragment;
import com.example.smartcityd_1.fragment.ChooseDempartFragment;
import com.example.smartcityd_1.fragment.ChooseTimeFragment;
import com.example.smartcityd_1.fragment.GhFinshFragment;
import com.example.smartcityd_1.fragment.HomeFragment;
import com.example.smartcityd_1.fragment.HostpitalFragment;
import com.example.smartcityd_1.fragment.JzkDetailsFragment;
import com.example.smartcityd_1.fragment.JzkFragment;
import com.example.smartcityd_1.fragment.MotifMyInfoFragment;
import com.example.smartcityd_1.fragment.MotifPwd;
import com.example.smartcityd_1.fragment.MyCenterFragment;
import com.example.smartcityd_1.fragment.MyOrderFragment;
import com.example.smartcityd_1.fragment.MzFragemnt;
import com.example.smartcityd_1.fragment.NewsFragment_Fp;
import com.example.smartcityd_1.fragment.TJFKFragment;
import com.example.smartcityd_1.fragment.WeatherFragment;
import com.example.smartcityd_1.fragment1.JzfpFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AppHomeActivity extends AppCompatActivity {

    private LinearLayout titleLayout;
    private TextView tvTitle;
    private EditText etSearch;
    private FrameLayout frameLayout;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        map = new HashMap<>();
        map.put("1", HomeFragment.newInstance(this));
        map.put("个人中心", MyCenterFragment.newInstance(this));
        map.put("个人信息", MotifMyInfoFragment.newInstance(this));
        map.put("修改密码", MotifPwd.newInstance(this));
        map.put("我的订单", MyOrderFragment.newInstance(this));
        map.put("修改密码", MotifPwd.newInstance(this));
        map.put("意见反馈", TJFKFragment.newInstance(this));
        map.put("全部服务", AllServiceFragment.newInstance(this));
        map.put("实时天气", WeatherFragment.newInstance(this));
        map.put("门诊预约", MzFragemnt.newInstance(this));
        map.put("门诊预约1", HostpitalFragment.newInstance(this));
        map.put("就诊卡", JzkFragment.newInstance(this));
        map.put("就诊卡1", JzkDetailsFragment.newInstance(this));
        map.put("就诊室", ChooseDempartFragment.newInstance(this));
        map.put("就诊时间", ChooseTimeFragment.newInstance(this));
        map.put("挂号", GhFinshFragment.newInstance(this));

        map.put("精准扶贫", JzfpFragment.newInstance(this));
        map.put("新闻", NewsFragment_Fp.newInstance(this));
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        switchFragemnt(map.get("1"));
                        break;
                    case R.id.action_center:
                        switchFragemnt(map.get("个人中心"));
                        break;
                    case R.id.action_service:
                        switchFragemnt(map.get("全部服务"));
                        break;
                    case R.id.action_fp:
                        switchFragemnt(map.get("精准扶贫"));
                        break;
                }
                return true;
            }
        });
        switchFragemnt(map.get("1"));
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    startActivity(new Intent(AppHomeActivity.this, SearchNewsActivity.class));
                    etSearch.setText("");
                    return true;
                }
                return false;
            }
        });
    }


    public Map<String, Fragment> map;
    private Fragment currentFragment = new Fragment();

    public void switchFragemnt(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.frame_layout, fragment, fragment.getClass().getName());

        } else {
            transaction.hide(currentFragment).show(fragment);
        }
        if (!fragment.getClass().getName().equals(map.get("1").getClass().getName())) {
            titleLayout.setVisibility(View.GONE);
        } else {
            titleLayout.setVisibility(View.VISIBLE);
        }
        currentFragment = fragment;
        transaction.commit();
    }

    private void initView() {
        titleLayout = findViewById(R.id.title_layout);
        tvTitle = findViewById(R.id.tv_title);
        etSearch = findViewById(R.id.et_search);
        frameLayout = findViewById(R.id.frame_layout);
        bottomNav = findViewById(R.id.bottom_nav);
    }

    public void itemChangeClick() {
        switchFragemnt(map.get("1"));
    }
}