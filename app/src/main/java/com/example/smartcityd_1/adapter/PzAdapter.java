package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.example.smartcityd_1.R;
import com.example.smartcityd_1.util.OnClickItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/3 at 11:51
 */
public class PzAdapter extends ArrayAdapter<Bitmap> {

    public PzAdapter(@NonNull Context context, @NonNull List<Bitmap> objects) {
        super(context, 0, objects);
    }

    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (position == getCount() - 1) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pz_item, parent, false);
            RelativeLayout relativeLayout = convertView.findViewById(R.id.item_layout);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem.onClick(position, "");
                }
            });
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pz_item2, parent, false);
            ImageView itemImage = convertView.findViewById(R.id.item_image);
            itemImage.setImageBitmap(getItem(position));
        }
        return convertView;
    }

    private void initView() {
    }
}
