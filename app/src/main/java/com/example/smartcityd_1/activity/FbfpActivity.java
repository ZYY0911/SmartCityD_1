package com.example.smartcityd_1.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;

import org.json.JSONObject;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 17:02
 */
public class FbfpActivity extends AppCompatActivity {
    private int id;
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private Button btSave;
    private EditText etTitle;
    private EditText etDe;
    private EditText etMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fbal_layout);
        id = getIntent().getIntExtra("index", 1);

        initView();
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("我要扶贫");
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyTo volleyTo = new VolleyTo();
                volleyTo.setUrl("fpApply")
                        //{applytitle:"c",applydesc:"cc",applycontent:"ccc",villid:1,starttime:"2020-8-8",helpdesc:"888",applystate:2,userid:1}
                        .setJsonObject("applytitle", etTitle.getText().toString())
                        .setJsonObject("applydesc", etDe.getText().toString())
                        .setJsonObject("applycontent", etMsg.getText().toString())
                        .setJsonObject("villid", id)
                        .setJsonObject("starttime", Util.simpleDate("yyyy-MM-dd", new Date()))
                        .setJsonObject("helpdesc", "999")
                        .setJsonObject("applystate", 2)
                        .setJsonObject("userid", 1)
                        .setVolleyLo(new VolleyLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                if (jsonObject.optString("RESULT").equals("S")) {
                                    Util.showToast("发布成功", FbfpActivity.this);
                                    finish();
                                } else {
                                    Util.showToast("发布失败", FbfpActivity.this);
                                }

                            }

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Util.showToast("发布失败", FbfpActivity.this);

                            }
                        }).start();
            }
        });


    }

    private void initView() {
        itemChange = findViewById(R.id.item_change);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        btSave = findViewById(R.id.bt_save);
        etTitle = findViewById(R.id.et_title);
        etDe = findViewById(R.id.et_de);
        etMsg = findViewById(R.id.et_msg);
    }
}
