package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.OrderTitle;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 8:40
 */
public class OrderAdapter extends ArrayAdapter<OrderTitle> {

    public OrderAdapter(@NonNull Context context, @NonNull List<OrderTitle> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_item, parent, false);
            holder = new ViewHolder();
            holder.itemNum = convertView.findViewById(R.id.item_num);
            holder.itemType = convertView.findViewById(R.id.item_type);
            holder.itemTime = convertView.findViewById(R.id.item_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderTitle orderTitle = getItem(position);
        holder.itemNum.setText(orderTitle.getId() + "");
        holder.itemTime.setText(orderTitle.getDate());
        holder.itemType.setText(orderTitle.getType());
        return convertView;
    }

    static class ViewHolder {

        private TextView itemNum;
        private TextView itemType;
        private TextView itemTime;
    }

    private void initView() {
    }
}
