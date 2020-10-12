package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.Example;
import com.example.smartcityd_1.bean.FpNews;
import com.example.smartcityd_1.bean.UserInfo;
import com.example.smartcityd_1.net.VolleyImage;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyLoImage;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 16:22
 */
public class ExampleAdapter extends ArrayAdapter<Example> {
    public ExampleAdapter(@NonNull Context context, @NonNull List<Example> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
            holder = new ViewHolder();
            holder.itemImage = convertView.findViewById(R.id.item_image);
            holder.itemTitle = convertView.findViewById(R.id.item_title);
            holder.itemContext = convertView.findViewById(R.id.item_context);
            holder.itemMsg = convertView.findViewById(R.id.item_msg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Example example = getItem(position);
        // {
        //            "caseid": 1,
        //            "casetitle": "大爱湖北行，千里送爱心",
        //            "casepicture": "http://localhost:8080/mobileA/images/fpcasepic1.jpg",
        //            "helperr": "严义军",
        //            "reporttime": "2020-10-02 00:00:00",
        //            "readnum": 9000,
        //            "thumbup": 2003,
        //            "userid": 1
        //        }
        holder.itemTitle.setText(example.getCasetitle());
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getUserInfo")
                .setJsonObject("userid", example.getUserid())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        UserInfo userInfo = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                , UserInfo.class);
                        holder.itemContext.setText("主要参与人：" + userInfo.getName());
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        holder.itemContext.setText("发布日期：" + example.getReporttime());
        holder.itemMsg.setText("查看人数：" + example.getReadnum() + "\n点赞人数：" + example.getThumbup());

        VolleyImage volleyImage = new VolleyImage();
        volleyImage.setUrl(example.getCasepicture())
                .setVolleyLoImage(new VolleyLoImage() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        holder.itemImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        return convertView;
    }

    static class ViewHolder {

        private ImageView itemImage;
        private TextView itemTitle;
        private TextView itemContext;
        private TextView itemMsg;
    }

}
