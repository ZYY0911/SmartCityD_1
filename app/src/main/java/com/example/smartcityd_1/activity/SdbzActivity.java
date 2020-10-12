package com.example.smartcityd_1.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.AppClient;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.adapter.MyHeplerAdapter;
import com.example.smartcityd_1.bean.MyHelper;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 17:14
 */
public class SdbzActivity extends AppCompatActivity {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private ListView listView;
    private AppClient appClient;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_fragment);

        initView();
        title.setText("收到请求");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        appClient = (AppClient) getApplication();
        userId = appClient.getUserId(AppClient.username);
        setVoley();
    }

    List<MyHelper> helpers;

    private void setVoley() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("fpSeekByUserid")
                .setJsonObject("userid", userId)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        helpers = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<MyHelper>>() {
                                }.getType());
                        listView.setAdapter(new MyHeplerAdapter(SdbzActivity.this, helpers));
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }


    private void initView() {
        itemChange = findViewById(R.id.item_change);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        listView = findViewById(R.id.list_view);
    }
}
