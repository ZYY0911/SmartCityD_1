package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.DutByDempart;
import com.example.smartcityd_1.util.OnClickItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 15:22
 */
public class GhAdapter extends ArrayAdapter<DutByDempart> {
    private String name;

    public GhAdapter(@NonNull Context context, @NonNull List<DutByDempart> objects, String name) {
        super(context, 0, objects);
        this.name = name;
    }


    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.gh_item, parent, false);
        TextView itemMsg = convertView.findViewById(R.id.item_msg);
        Button itemBt = convertView.findViewById(R.id.item_bt);
        DutByDempart dutByDempart = getItem(position);
        // {
        //            "num": 1,
        //            "hospitalId": "1",
        //            "departmentId": "1",
        //            "time": "2020-10-1 周四 下午14：00",
        //            "type": "普通门诊",
        //            "doctorId": "1"
        //        }
        itemMsg.setText(dutByDempart.getTime() + "," + name);
        itemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClick(position, name);
            }
        });
        return convertView;
    }
}
