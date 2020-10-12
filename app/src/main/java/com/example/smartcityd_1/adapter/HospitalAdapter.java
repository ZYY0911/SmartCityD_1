package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.HospitalList;
import com.example.smartcityd_1.net.VolleyImage;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyLoImage;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.OnClickItem;
import com.example.smartcityd_1.util.Util;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 11:47
 */
public class HospitalAdapter extends ArrayAdapter<HospitalList> {

    public HospitalAdapter(@NonNull Context context, @NonNull List<HospitalList> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hostpialo_item, parent, false);
            holder = new ViewHolder();
            holder.itemImage = convertView.findViewById(R.id.item_image);
            holder.itemName = convertView.findViewById(R.id.item_name);
            holder.ratingBar = convertView.findViewById(R.id.rating_bar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HospitalList hospitalList = getItem(position);
        VolleyImage volleyImage = new VolleyImage();
        volleyImage.setUrl(hospitalList.getPicture())
                .setVolleyLoImage(new VolleyLoImage() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        holder.itemImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        holder.itemName.setText(hospitalList.getHospitalName());
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getRankByHospitalId")
                .setJsonObject("hospitalId", hospitalList.getHospitalId())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONObject jsonObject1 = jsonObject.optJSONArray(Util.Rows).optJSONObject(0);
                        holder.ratingBar.setRating(Float.parseFloat(jsonObject1.optString("rank")));
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClick(position,"");
            }
        });
        return convertView;
    }


    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    static class ViewHolder {

        private ImageView itemImage;
        private TextView itemName;
        private RatingBar ratingBar;
    }

    private void initView() {
    }
}
