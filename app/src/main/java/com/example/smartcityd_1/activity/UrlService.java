package com.example.smartcityd_1.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 10:24
 */
public class UrlService extends AppCompatActivity {
    private WebView webView;
    private ImageView itemChange;
    private TextView title;
    private TextView title1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_layout);
        initView();
        title.setText(getIntent().getStringExtra("name"));
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.i("aaa", "onCreate: " + getIntent().getIntExtra("info", 1));
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getServiceUrl")
                .setJsonObject("serviceid", getIntent().getIntExtra("info", 1))
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        webView.loadUrl(jsonObject.optString("url"));

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void initView() {
        webView = findViewById(R.id.web_view);
        itemChange = findViewById(R.id.item_change);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
    }
}
