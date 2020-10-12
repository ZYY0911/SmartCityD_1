package com.example.smartcityd_1.fragment_glid;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.dialog.NetDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/11 at 19:55
 */
public class GlideFragment2 extends Fragment {
    private ImageView itemImage;

    private int layout;
    private Button btSetting;
    private Button btMain;

    public GlideFragment2(int layout) {
        this.layout = layout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.glid_fragment2, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        itemImage.setImageResource(layout);
        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetDialog dialog = new NetDialog();
                dialog.show(getChildFragmentManager(), "");

            }
        });
        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AppHomeActivity.class));
                getActivity().finish();
            }
        });
    }

    private void initView() {
        itemImage = getView().findViewById(R.id.item_image);
        btSetting = getView().findViewById(R.id.bt_setting);
        btMain = getView().findViewById(R.id.bt_main);
    }
}
