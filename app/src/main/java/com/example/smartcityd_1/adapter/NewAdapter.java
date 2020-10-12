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
import com.example.smartcityd_1.bean.FpNews;
import com.example.smartcityd_1.net.VolleyImage;
import com.example.smartcityd_1.net.VolleyLoImage;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 15:56
 */
public class NewAdapter extends ArrayAdapter<FpNews> {

    public NewAdapter(@NonNull Context context, @NonNull List<FpNews> objects) {
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
        FpNews fpNews = getItem(position);
        holder.itemTitle.setText(fpNews.getNewstitle());
        holder.itemContext.setText(fpNews.getNewscontent());
        holder.itemMsg.setText("查看人数：" + fpNews.getReadnum() + "\n发布时间：" + fpNews.getReporttime());

        VolleyImage volleyImage = new VolleyImage();
        volleyImage.setUrl(fpNews.getNewspicture())
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

    private void initView() {
    }
}
