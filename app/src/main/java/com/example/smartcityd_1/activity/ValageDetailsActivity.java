package com.example.smartcityd_1.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.ValageInfo;
import com.example.smartcityd_1.bean.VilageLsit;
import com.example.smartcityd_1.net.VolleyImage;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyLoImage;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 16:52
 */
public class ValageDetailsActivity extends AppCompatActivity {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private ImageView ivPhoto;
    private TextView tvMsg;
    private TextView itemMore;
    private VilageLsit vilageLsit;
    private ValageInfo valageInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valage_details_layout);

        initView();
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vilageLsit = (VilageLsit) getIntent().getSerializableExtra("info");
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("fpVilliageInfo")
                .setJsonObject("villid", vilageLsit.getVillid())

                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        valageInfo = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                , ValageInfo.class);
                        setView();

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();

    }

    private void setView() {
        VolleyImage volleyImage = new VolleyImage();
        volleyImage.setUrl(valageInfo.getEnviroment_pic())
                .setVolleyLoImage(new VolleyLoImage() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        ivPhoto.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        title.setText(vilageLsit.getVillname());
        tvMsg.setText("介绍：" + vilageLsit.getVilldesc() + "\n" +
                "发布时间：" + valageInfo.getReporttime() + "\n" +
                "发布人：" + valageInfo.getReport() + "\n" +
                "查看数量：" + valageInfo.getReadernum());
        itemMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ValageDetailsActivity.this, FbfpActivity.class);
                intent.putExtra("index", vilageLsit.getVillid());
                startActivity(intent);
            }
        });

    }

    private void initView() {
        itemChange = findViewById(R.id.item_change);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        ivPhoto = findViewById(R.id.iv_photo);
        tvMsg = findViewById(R.id.tv_msg);
        itemMore = findViewById(R.id.item_more);
    }
}
