package com.example.smartcityd_1.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.adapter.PhotoAdapter;
import com.example.smartcityd_1.net.VolleyImage;
import com.example.smartcityd_1.net.VolleyLoImage;
import com.example.smartcityd_1.util.MyGirdView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 8:14
 */
public class MotifImage extends AppCompatActivity {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private LinearLayout lauyoutPhoto;
    private ImageView ivPhoto;
    private MyGirdView girdView;

    private Integer images[] = {R.mipmap.user1, R.mipmap.user2,
            R.mipmap.user3, R.mipmap.user4, R.mipmap.user5, R.mipmap.user6, R.mipmap.user7, R.mipmap.user8};
    List<Integer> integers;
    private int index = 0;
    private String infos;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motif_images);

        integers = new ArrayList<>();
        initView();
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("选择头像");
        infos = getIntent().getStringExtra("infos");
        for (int i = 0; i < images.length; i++) {
            integers.add(images[i]);
        }
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("date", ivPhoto.getTag() == null ? "" : ivPhoto.getTag().toString());
                intent.putExtra("index", index);
                setResult(ivPhoto.getTag() == null ? RESULT_CANCELED : RESULT_OK, intent);
                finish();
            }
        });
        VolleyImage volleyImage = new VolleyImage();
        volleyImage.setUrl(infos)
                .setVolleyLoImage(new VolleyLoImage() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        ivPhoto.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        girdView.setAdapter(new PhotoAdapter(MotifImage.this, integers));
        girdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ivPhoto.setImageResource(integers.get(position));
                ivPhoto.setTag("user" + (position + 1) + ".png");
                index = position;
            }
        });
    }

    private void initView() {
        itemChange = findViewById(R.id.item_change);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        lauyoutPhoto = findViewById(R.id.lauyout_photo);
        ivPhoto = findViewById(R.id.iv_photo);
        girdView = findViewById(R.id.gird_view);
    }
}
