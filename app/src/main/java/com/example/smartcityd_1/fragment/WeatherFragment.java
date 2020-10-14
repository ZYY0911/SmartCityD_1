package com.example.smartcityd_1.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.adapter.SenseAdapter;
import com.example.smartcityd_1.adapter.WeatherAdapter;
import com.example.smartcityd_1.bean.Sense;
import com.example.smartcityd_1.bean.Weather;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.MyGirdView;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 10:40
 */
public class WeatherFragment extends Fragment {
    private AppHomeActivity appHomeActivity;
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private TextView tvWeather;
    private TextView tvTemputer;
    private TextView tvDate;
    private MyGirdView girdWeather;
    private Button btSave;
    private Thread thread;
    private MyGirdView girdSnese;

    public WeatherFragment() {
    }

    public WeatherFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static WeatherFragment newInstance(AppHomeActivity appHomeActivity) {
        return new WeatherFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_layout, container, false);
    }


    private boolean isLoop = true;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setWeather();
        thread = new Thread(new MyThread());
        thread.start();
        title.setText("实时天气");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("1"));
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoop = false;
                setWeather();
                setVolley_Sense();
            }
        });

    }

    class MyThread extends Thread {
        @Override
        public void run() {
            while (isLoop) {
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            setVolley_Sense();
            return false;
        }
    });
    Sense sense;

    private void setVolley_Sense() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("get_sense")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        sense = new Gson().fromJson(jsonObject.toString(), Sense.class);

                        girdSnese.setAdapter(new SenseAdapter(getActivity(), sense));

                        Log.i("aa", "onResponse: ");
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("aaa", "onErrorResponse: " + volleyError.getMessage());

                    }
                }).start();
    }


    List<Weather> weathers;

    private void setWeather() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("get_weather_info")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        tvDate.setText("日期：" + jsonObject.optString("date"));
                        tvTemputer.setText("气温：" + jsonObject.optString("temperature") + "℃");
                        tvWeather.setText("天气：" + jsonObject.optString("weather"));

                        weathers = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<Weather>>() {
                                }.getType());
                        girdWeather.setAdapter(new WeatherAdapter(getActivity(), weathers));
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        isLoop = !hidden;
        if (!hidden) {
            if (!thread.isAlive()) {
                thread = new Thread(new Thread());
                thread.start();
            }
        }
        super.onHiddenChanged(hidden);
    }

    private void initView() {
        itemChange = getView().findViewById(R.id.item_change);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        tvWeather = getView().findViewById(R.id.tv_weather);
        tvTemputer = getView().findViewById(R.id.tv_temputer);
        tvDate = getView().findViewById(R.id.tv_date);
        btSave = getView().findViewById(R.id.bt_save);
        girdWeather = getView().findViewById(R.id.gird_weather);
        girdSnese = getView().findViewById(R.id.gird_snese);
    }
}
