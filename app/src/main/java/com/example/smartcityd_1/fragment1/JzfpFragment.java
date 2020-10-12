package com.example.smartcityd_1.fragment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.activity.FpEamplActivity;
import com.example.smartcityd_1.activity.ValageActivity;
import com.example.smartcityd_1.activity.WorkActivty;
import com.example.smartcityd_1.fragment.JzkFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 15:45
 */
public class JzfpFragment extends Fragment {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private LinearLayout layoutExample;
    private ImageView itemPhoto;
    private TextView itemName;
    private LinearLayout layoutNew;
    private LinearLayout layoutValage;
    private LinearLayout layoutWork;

    public JzfpFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public JzfpFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static JzfpFragment newInstance(AppHomeActivity appHomeActivity) {
        return new JzfpFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.jzfp_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("精准扶贫");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.itemChangeClick();
            }
        });
        layoutNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("新闻"));
            }
        });
        layoutExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FpEamplActivity.class));
            }
        });
        layoutValage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ValageActivity.class));
            }
        });
        layoutWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WorkActivty.class));
            }
        });
    }

    private void initView() {
        itemChange = getView().findViewById(R.id.item_change);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        layoutExample = getView().findViewById(R.id.layout_example);
        itemPhoto = getView().findViewById(R.id.item_photo);
        itemName = getView().findViewById(R.id.item_name);
        layoutNew = getView().findViewById(R.id.layout_new);
        layoutValage = getView().findViewById(R.id.layout_valage);
        layoutWork = getView().findViewById(R.id.layout_work);
    }
}
