package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.HospitalDemp;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 15:10
 */
public class ChooseDempartAdapter extends ArrayAdapter<HospitalDemp> {

    public ChooseDempartAdapter(@NonNull Context context, @NonNull List<HospitalDemp> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_text, parent, false);
            TextView itemText = convertView.findViewById(R.id.item_text);
            itemText.setEllipsize(TextUtils.TruncateAt.END);
            itemText.setMaxLines(2);
            HospitalDemp hospitalDemp = getItem(position);
            itemText.setText(hospitalDemp.getDeptName() + "\n" + hospitalDemp.getDesc());
        }
        return convertView;
    }

    private void initView() {

    }
}
