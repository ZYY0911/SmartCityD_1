package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.VilageLsit;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 17:50
 */
public class rhzfAdapter extends ArrayAdapter<VilageLsit> {

    public rhzfAdapter(@NonNull Context context, @NonNull List<VilageLsit> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_text, parent, false);
            TextView itemText = convertView.findViewById(R.id.item_text);
            itemText.setText(getItem(position).getVillname());

        return convertView;
    }

    private void initView() {

    }
}
