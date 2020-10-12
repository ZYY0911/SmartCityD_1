package com.example.smartcityd_1.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.AppClient;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.ValavgePeople;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 17:59
 */
public class SubmitInfosActivity extends AppCompatActivity {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private LinearLayout layoutStart;
    private TextView tvStart;
    private LinearLayout layoutEnd;
    private TextView tvEnd;
    private EditText etMsg;
    private Button btSave;
    private ValavgePeople valavgePeople;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_layout);

        initView();
        valavgePeople = (ValavgePeople) getIntent().getSerializableExtra("info");
        title.setText("提交走访");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showDiaog(tvStart, layoutStart);
        showDiaog(tvEnd, layoutEnd);
        final AppClient appClient = (AppClient) getApplication();
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyTo volleyTo = new VolleyTo();
                volleyTo.setUrl("fpInterviewPlan")
                        //{"villiagerid":"2","starttime":"2020-8-1","endtime":"2020-9-1","intent":"对口帮扶","userid":2}
                        .setJsonObject("villiagerid", valavgePeople.getVilliagerid())
                        .setJsonObject("starttime", tvStart.getText())
                        .setJsonObject("endtime", tvEnd.getText())
                        .setJsonObject("intent", etMsg.getText().toString())
                        .setJsonObject("userid", appClient.getUserId(AppClient.username))
                        .setVolleyLo(new VolleyLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                if (jsonObject.optString("RESULT").equals("S")) {
                                    Util.showToast("发布成功", SubmitInfosActivity.this);
                                    finish();
                                } else {
                                    Util.showToast("发布失败", SubmitInfosActivity.this);
                                }

                            }

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Util.showToast("发布失败", SubmitInfosActivity.this);

                            }
                        }).start();
            }
        });

    }

    private void showDiaog(final TextView textView, LinearLayout linearLayout) {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SubmitInfosActivity.this
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textView.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, 2020, 9, 12);
                datePickerDialog.show();
            }
        });
    }

    private void initView() {
        itemChange = findViewById(R.id.item_change);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        layoutStart = findViewById(R.id.layout_start);
        tvStart = findViewById(R.id.tv_start);
        layoutEnd = findViewById(R.id.layout_end);
        tvEnd = findViewById(R.id.tv_end);
        etMsg = findViewById(R.id.et_msg);
        btSave = findViewById(R.id.bt_save);
    }
}
