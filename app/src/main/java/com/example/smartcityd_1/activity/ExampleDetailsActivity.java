package com.example.smartcityd_1.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.Example;
import com.example.smartcityd_1.bean.UserInfo;
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
 * @Create by 张瀛煜 on 2020/10/12 at 16:31
 */
public class ExampleDetailsActivity extends AppCompatActivity {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private TextView tvTitle;
    private ImageView ivImage;
    private TextView tvMsg;
    private TextView tvLook;
    private Button btSubmit;

    private Example example;
    private TextView tvTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_details_layout);
        initView();
        title.setText("扶贫案例");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        example = (Example) getIntent().getSerializableExtra("info");
        title1.setText("查看人数：" + example.getReadnum());

        VolleyImage volleyImage = new VolleyImage();
        volleyImage.setUrl(example.getCasepicture())
                .setVolleyLoImage(new VolleyLoImage() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        ivImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        tvTitle.setText(example.getCasetitle());
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getUserInfo")
                .setJsonObject("userid", example.getUserid())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        UserInfo userInfo = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                , UserInfo.class);

                        tvMsg.setText(example.getCaseContent() + "\n发布者：" + userInfo.getName());
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();

        tvLook.setText("点赞人数：" + example.getThumbup());
        tvTime.setText("发布日期" + example.getReporttime());

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyTo volleyTo = new VolleyTo();
                volleyTo.setUrl("fpThumbup")
                        .setJsonObject("caseid", example.getCaseid())
                        .setVolleyLo(new VolleyLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                if (jsonObject.optString("RESULT").equals("S")) {
                                    tvLook.setText("点赞人数：" + (example.getThumbup() + 1));
                                    Util.showToast("点赞成功", ExampleDetailsActivity.this);
                                } else {
                                    Util.showToast("点赞失败", ExampleDetailsActivity.this);
                                }

                            }

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Util.showToast("点赞失败", ExampleDetailsActivity.this);
                            }
                        }).start();
            }
        });

    }

    private void initView() {
        itemChange = findViewById(R.id.item_change);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        tvTitle = findViewById(R.id.tv_title);
        ivImage = findViewById(R.id.iv_image);
        tvMsg = findViewById(R.id.tv_msg);
        tvLook = findViewById(R.id.tv_look);
        btSubmit = findViewById(R.id.bt_submit);
        tvTime = findViewById(R.id.tv_time);
    }
}
