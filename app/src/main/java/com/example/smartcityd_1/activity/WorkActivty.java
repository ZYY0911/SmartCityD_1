package com.example.smartcityd_1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcityd_1.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 17:09
 */
public class WorkActivty extends AppCompatActivity {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private LinearLayout layoutSdbz;
    private LinearLayout layoutWbfd;
    private LinearLayout layoutRhzf;
    private LinearLayout layoutWdal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_layout);
        initView();
        title.setText("工作台");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layoutSdbz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkActivty.this, SdbzActivity.class));
            }
        });
        layoutWbfd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkActivty.this, WdbfActivity.class));
            }
        });
        layoutRhzf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkActivty.this, RhzfAvtivity.class));

            }
        });
        layoutWdal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkActivty.this, WdalFbActrivity.class));

            }
        });
    }


    private void initView() {
        itemChange = findViewById(R.id.item_change);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        layoutSdbz = findViewById(R.id.layout_sdbz);
        layoutWbfd = findViewById(R.id.layout_wbfd);
        layoutRhzf = findViewById(R.id.layout_rhzf);
        layoutWdal = findViewById(R.id.layout_wdal);
    }
}
