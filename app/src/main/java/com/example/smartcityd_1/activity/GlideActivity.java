package com.example.smartcityd_1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.smartcityd_1.AppClient;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.fragment_glid.GlideFragment;
import com.example.smartcityd_1.fragment_glid.GlideFragment2;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/11 at 18:20
 */
public class GlideActivity extends AppCompatActivity {
    private List<Fragment> fragments;
    private ViewPager viewPager;
    private int images[] = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.e, R.mipmap.d};
    private LinearLayout layout;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glide_layout);
        initView();
        preferences = AppClient.sharedPreferences;
        if (preferences.getBoolean(AppClient.IsFirst, true)) {
            preferences.edit().putBoolean(AppClient.IsFirst, false).commit();
            fragments = new ArrayList<>();
            for (int i = 0; i < images.length; i++) {
                if (i == images.length - 1) {
                    fragments.add(new GlideFragment2(images[i]));
                } else {
                    fragments.add(new GlideFragment(images[i]));
                }
                ImageView imageView = new ImageView(GlideActivity.this);
                if (i == 0) {
                    imageView.setImageResource(R.drawable.tv_select);
                } else {
                    imageView.setImageResource(R.drawable.tv_select_no);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
                layoutParams.setMargins(20, 0, 20, 0);
                imageView.setLayoutParams(layoutParams);
                layout.addView(imageView);
            }
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
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < layout.getChildCount(); i++) {
                        ImageView imageView = (ImageView) layout.getChildAt(i);
                        if (i == position) {
                            imageView.setImageResource(R.drawable.tv_select);
                        } else {
                            imageView.setImageResource(R.drawable.tv_select_no);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            startActivity(new Intent(GlideActivity.this, AppHomeActivity.class));
            finish();
        }
    }


    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        layout = findViewById(R.id.layout);
    }
}
