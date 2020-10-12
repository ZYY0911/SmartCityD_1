package com.example.smartcityd_1.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.FpNews;
import com.example.smartcityd_1.net.VolleyImage;
import com.example.smartcityd_1.net.VolleyLoImage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 16:00
 */
public class NewDetailsActivity extends AppCompatActivity {
    private FpNews fpNews;
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private ImageView ivImage;
    private TextView tvMsg;
    private TextView tvLook;
    private TextView btSubmit;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_details_layout);

        initView();
        fpNews = (FpNews) getIntent().getSerializableExtra("info");
        tvTitle.setText(fpNews.getNewstitle());
        title.setText("新闻");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        VolleyImage volleyImage = new VolleyImage();
        volleyImage.setUrl(fpNews.getNewspicture())
                .setVolleyLoImage(new VolleyLoImage() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        ivImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        tvMsg.setText(fpNews.getNewscontent());
        //tvLook.setText("查看人数："+fpNews.getReadnum());
        title1.setText("查看人数：" + fpNews.getReadnum());

    }

    private void initView() {
        itemChange = findViewById(R.id.item_change);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        ivImage = findViewById(R.id.iv_image);
        tvMsg = findViewById(R.id.tv_msg);
        tvLook = findViewById(R.id.tv_look);
        btSubmit = findViewById(R.id.bt_submit);
        tvTitle = findViewById(R.id.tv_title);
    }
}
