package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.Jzk;
import com.example.smartcityd_1.util.OnClickItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 14:47
 */
public class JzkAdapter extends ArrayAdapter<Jzk> {

    public JzkAdapter(@NonNull Context context, @NonNull List<Jzk> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.jzk_item, parent, false);
            holder = new ViewHolder();
            holder.itemName = convertView.findViewById(R.id.item_name);
            holder.itemId = convertView.findViewById(R.id.item_id);
            holder.itemTel = convertView.findViewById(R.id.item_tel);
            holder.itemBl = convertView.findViewById(R.id.item_bl);
            holder.itemImage = convertView.findViewById(R.id.item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Jzk jzk = getItem(position);
        holder.itemName.setText("姓名：" + jzk.getName());
        holder.itemId.setText("身份证号：" + jzk.getID());
        holder.itemTel.setText("手机号：" + jzk.getTel());
        holder.itemBl.setText("病历号：" + jzk.getCaseId());
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClick(position, "1");
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClick(position, "2");

            }
        });
        return convertView;
    }

    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    static class ViewHolder {

        private TextView itemName;
        private TextView itemId;
        private TextView itemTel;
        private TextView itemBl;
        private ImageView itemImage;
    }

    private void initView() {
    }
}

