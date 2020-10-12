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
import com.example.smartcityd_1.adapter.WeatherAdapter;
import com.example.smartcityd_1.bean.Sense;
import com.example.smartcityd_1.bean.Weather;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.MyGirdView;
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
    private TextView tvGm;
    private TextView tvCy;
    private TextView tvYd;
    private TextView tvKqks;
    private Button btSave;
    private Thread thread;
    private TextView tvZwx;

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
        Log.i("aaa", "setVolley_Sense: ");
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("get_sense")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        sense = new Gson().fromJson(jsonObject.toString(), Sense.class);
                        setSnese(sense);
                        Log.i("aa", "onResponse: ");
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("aaa", "onErrorResponse: " + volleyError.getMessage());

                    }
                }).start();
    }

    private void setSnese(Sense sense) {
        int valeu = sense.get_$Pm25250();
        if (valeu < 30) {
            tvKqks.setText("空气污染扩散指数：" + valeu + " \n    优\n空气质量非常好，非常适合户外活动，趁机出去多呼吸新鲜空气");
        } else if (valeu > 100) {
            tvKqks.setText("空气污染扩散指数：" + valeu + " \n    污染\n空气质量差，不适合户外活动");
        } else {
            tvKqks.setText("空气污染扩散指数：" + valeu + " \n     良\n易感人群应适当减少室外活动");
        }
        valeu = sense.getCo2();
        if (valeu < 3000) {
            tvYd.setText("运动指数：" + valeu + "  \n    适宜\n气候适宜，推荐您进行户外运动");
        } else if (valeu > 6000) {
            tvYd.setText("运动指数：" + valeu + "  \n    较不宜\n空气氧气含量低，请在室内进");
        } else {

            tvYd.setText("运动指数：" + valeu + "  \n    中\n易感人群应适当减少室外活动");
        }
        valeu = sense.getTemperature();
        if (valeu < 12) {
            tvCy.setText("穿衣指数：" + valeu + "  \n    冷\n建议穿长袖衬衫、单裤等服装");
        } else if (valeu > 21) {
            tvCy.setText("穿衣指数：" + valeu + "  \n    热\n适合穿T恤、短薄外套等夏季服装");
        } else {
            tvCy.setText("穿衣指数：" + valeu + "  \n    舒适\n建议穿短袖衬衫、单裤等服装");
        }
        if (valeu < 8) {
            tvGm.setText("感冒指数：" + valeu + "  \n    较易发\n温度低，风较大，较易发生感冒，注意防护");
        } else {
            tvGm.setText("感冒指数：" + valeu + "  \n    少发\n无明显降温，感冒机率较低");
        }
        valeu = sense.getIllumination();
        if (valeu < 1000) {
            tvZwx.setText("紫外线指数：" + valeu + "  \n    弱\n辐射较弱，涂擦SPF12~15、PA+护肤品");
        } else if (valeu > 21) {
            tvZwx.setText("紫外线指数：" + valeu + "  \n    强\n尽量减少外出，需要涂抹高倍数防晒霜");
        } else {
            tvZwx.setText("紫外线指数：" + valeu + "  \n    中等\n涂擦SPF大于15、PA+防晒护肤品");
        }
        isLoop = true;
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

                        weathers = new Gson().fromJson(jsonObject.optJSONArray("ROW_DEATIL").toString()
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
        Log.i("aaa", "onHiddenChanged: " + hidden);
        super.onHiddenChanged(hidden);
    }

    private void initView() {
        itemChange = getView().findViewById(R.id.item_change);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        tvWeather = getView().findViewById(R.id.tv_weather);
        tvTemputer = getView().findViewById(R.id.tv_temputer);
        tvDate = getView().findViewById(R.id.tv_date);
        girdWeather = getView().findViewById(R.id.gird_weather);
        tvGm = getView().findViewById(R.id.tv_gm);
        tvCy = getView().findViewById(R.id.tv_cy);
        tvYd = getView().findViewById(R.id.tv_yd);
        tvKqks = getView().findViewById(R.id.tv_kqks);
        btSave = getView().findViewById(R.id.bt_save);
        tvZwx = getView().findViewById(R.id.tv_zwx);
    }
}
