package com.example.smartcityd_1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.adapter.ValagePeopleAdatere;
import com.example.smartcityd_1.bean.ValavgePeople;
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
 * @Create by 张瀛煜 on 2020/10/12 at 17:53
 */
public class ChooseHzActivity extends AppCompatActivity {
    private int valageId;
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private ListView listView;

    List<ValavgePeople> valavgePeople;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_fragment);
        initView();

        valageId = getIntent().getIntExtra("index", 1);
        title.setText(getIntent().getStringExtra("name"));
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("fpVilliagerListByVillid")
                .setJsonObject("villid", valageId)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        valavgePeople = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<ValavgePeople>>() {
                                }.getType());
                        listView.setAdapter(new ValagePeopleAdatere(ChooseHzActivity.this, valavgePeople));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(ChooseHzActivity.this, SubmitInfosActivity.class);
                                intent.putExtra("info", valavgePeople.get(position));
                                startActivity(intent);
                            }
                        });
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
