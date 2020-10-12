package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_text, parent, false);
        TextView itemText = convertView.findViewById(R.id.item_text);
        Weather weather = getItem(position);
        itemText.setText("天气情况：" + weather.getWeather() + "      气温:" + weather.getTemperature() + "℃\n日期："+weather.getDate());
        return convertView;
    }

    private void initView() {

    }
}
