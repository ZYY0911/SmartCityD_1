package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.Weather;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 10:52
 */
public class WeatherAdapter extends ArrayAdapter<Weather> {


    public WeatherAdapter(@NonNull Context context, @NonNull List<Weather> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.weather_item, parent, false);
            holder = new ViewHolder();
            holder.itemRl = convertView.findViewById(R.id.item_rl);
            holder.itemImage = convertView.findViewById(R.id.item_image);
            holder.itemWd = convertView.findViewById(R.id.item_wd);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Weather weather = getItem(position);
        holder.itemRl.setText(weather.getDate());
        String arr[] = weather.getTemperature().split("~");
        holder.itemWd.setText(arr[0] + "℃/" + arr[1] + "℃");
        switch (weather.getWeather()) {
            case "晴":
                holder.itemImage.setImageResource(R.mipmap.qing);
                break;
            case "阴":
                holder.itemImage.setImageResource(R.mipmap.yintian);
                break;
            case "多云":
                holder.itemImage.setImageResource(R.mipmap.duoyun);
                break;
            case "雨":
                holder.itemImage.setImageResource(R.mipmap.yu);
                break;
            case "霾":
                holder.itemImage.setImageResource(R.mipmap.wumai);
                break;
            case "雪":
                holder.itemImage.setImageResource(R.mipmap.xue);
                break;

        }

        return convertView;
    }

    static class ViewHolder {

        private TextView itemRl;
        private ImageView itemImage;
        private TextView itemWd;
    }

    private void initView() {

    }
}
