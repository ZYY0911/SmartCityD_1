package com.example.smartcityd_1.fragment1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.ValageDetailsActivity;
import com.example.smartcityd_1.bean.VilageLsit;
import com.example.smartcityd_1.net.VolleyImage;
import com.example.smartcityd_1.net.VolleyLoImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 16:45
 */
public class valvagFragment extends Fragment {
    private ImageView itemImage;
    private TextView itemName;
    private TextView itemMsg;
    private TextView itemMore;
    private VilageLsit vilageLsit;

    public valvagFragment(VilageLsit vilageLsit) {
        this.vilageLsit = vilageLsit;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.valage_item, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        VolleyImage volleyImage = new VolleyImage();
        volleyImage.setUrl(vilageLsit.getVillpicture())
                .setVolleyLoImage(new VolleyLoImage() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        itemImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        itemName.setText(vilageLsit.getVillname() + "" +
                "\n地址：" + vilageLsit.getVilladdress());
        itemMsg.setText(vilageLsit.getVilldesc());
        itemMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ValageDetailsActivity.class);
                intent.putExtra("info",vilageLsit);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {

        }
        super.onHiddenChanged(hidden);
    }

    private void initView() {
        itemImage = getView().findViewById(R.id.item_image);
        itemName = getView().findViewById(R.id.item_name);
        itemMsg = getView().findViewById(R.id.item_msg);
        itemMore = getView().findViewById(R.id.item_more);
    }
}
