package com.example.smartcityd_1.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.AppClient;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.adapter.PzAdapter;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.OnClickItem;
import com.example.smartcityd_1.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 17:39
 */
public class WdalFbActrivity extends AppCompatActivity {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private EditText etTitle;
    private EditText etDe;
    private GridView girdView;
    private Button btSave;
    private List<Bitmap> bitmaps;
    private PzAdapter adapter;

    private String arr[] = {"a", "b", "c"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wdalfb_layout);
        initView();
        bitmaps = new ArrayList<>();
        bitmaps.add(null);
        adapter = new PzAdapter(WdalFbActrivity.this, bitmaps);
        adapter.setOnClickItem(new OnClickItem() {
            @Override
            public void onClick(int position, String name) {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(openCameraIntent, 1);
            }
        });
        girdView.setAdapter(adapter);


        title.setText("我的案例发布");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final AppClient appClient = (AppClient) getApplication();


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyTo volleyTo = new VolleyTo();
                volleyTo.setUrl("fpPublicCase")

                        //{casetitle:"111",casepicture:"a.jpg",reporttime:"2020-10-2","userid":1}
                        .setJsonObject("casetitle", etTitle.getText().toString())
                        .setJsonObject("casepicture", "a.jpg")
                        .setJsonObject("reporttime", Util.simpleDate("yyyy-MM-dd", new Date()))
                        .setJsonObject("userid", appClient.getUserId(AppClient.username))
                        .setVolleyLo(new VolleyLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                if (jsonObject.optString("RESULT").equals("S")) {
                                    Util.showToast("发布成功", WdalFbActrivity.this);
                                    finish();
                                } else {
                                    Util.showToast("发布失败", WdalFbActrivity.this);
                                }
                            }

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Util.showToast("发布失败", WdalFbActrivity.this);

                            }
                        }).start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    bitmaps.add(0, bm);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        itemChange = findViewById(R.id.item_change);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        etTitle = findViewById(R.id.et_title);
        etDe = findViewById(R.id.et_de);
        girdView = findViewById(R.id.gird_view);
        btSave = findViewById(R.id.bt_save);
    }
}
