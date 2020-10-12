package com.example.smartcityd_1.util;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/11 at 18:21
 */
public class MyViewPager implements ViewPager.PageTransformer {
    private ViewPager viewPager;
    private int TOffest;

    public MyViewPager(Context context) {
        this.TOffest = dp2px(context, 180);
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (viewPager == null) {
            viewPager = (ViewPager) page.getParent();
        }
        int leftScreen = page.getLeft() - viewPager.getScrollX();
        int currtneXInViewPager = leftScreen + page.getMeasuredWidth() / 2;
        int offsetX = currtneXInViewPager - viewPager.getMeasuredWidth() / 2;
        float offsetRate = (float) offsetX * 0.38f / viewPager.getMeasuredWidth();
        float secaleFactor = 1 - Math.abs(offsetRate);
        if (secaleFactor > 0) {
            page.setScaleX(secaleFactor);
            page.setScaleY(secaleFactor);
            page.setTranslationX(-TOffest * offsetRate);
        }

    }

    private int dp2px(Context context, float values) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (values * m + 0.5f);
    }
}
