package com.example.smartcityd_1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.activity.NewDetailsActivity;
import com.example.smartcityd_1.adapter.NewAdapter;
import com.example.smartcityd_1.bean.FpNews;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 15:53
 */
public class NewsFragment_Fp extends Fragment {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private ListView listView;

    public NewsFragment_Fp() {
    }

    private AppHomeActivity appHomeActivity;

    public NewsFragment_Fp(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }


    public static NewsFragment_Fp newInstance(AppHomeActivity appHomeActivity) {
        return new NewsFragment_Fp(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("新闻");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragemnt(appHomeActivity.map.get("精准扶贫"));
            }
        });

        setView();
    }

    List<FpNews> fpNews;

    private void setView() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("fpNewsList")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        fpNews = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<FpNews>>() {
                                }.getType());
                        Collections.sort(fpNews, new Comparator<FpNews>() {
                            @Override
                            public int compare(FpNews o1, FpNews o2) {
                                //2020-04-03 00:00:00
                                Date date = null;
                                Date date1 = null;
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                try {
                                    date = format.parse(o1.getReporttime());
                                    date1 = format.parse(o2.getReporttime());

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                return date1.compareTo(date);
                            }
                        });
                        listView.setAdapter(new NewAdapter(getActivity(), fpNews));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getActivity(), NewDetailsActivity.class);
                                intent.putExtra("info", fpNews.get(position));
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            setView();
        }
        super.onHiddenChanged(hidden);
    }

    private void initView() {
        itemChange = getView().findViewById(R.id.item_change);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        listView = getView().findViewById(R.id.list_view);
    }
}
