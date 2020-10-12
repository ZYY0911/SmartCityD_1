package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.OrderDetails;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 8:49
 */
public class OrderDetailAdapter extends ArrayAdapter<OrderDetails> {

    public OrderDetailAdapter(@NonNull Context context, @NonNull List<OrderDetails> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_drtails_item, parent, false);
            holder = new ViewHolder();
            holder.itemSjmc = convertView.findViewById(R.id.item_sjmc);
            holder.itemSpmc = convertView.findViewById(R.id.item_spmc);
            holder.itemPrice = convertView.findViewById(R.id.item_price);
            holder.itemNnm = convertView.findViewById(R.id.item_nnm);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderDetails orderDetails = getItem(position);
        holder.itemNnm.setText(orderDetails.getCount());
        holder.itemPrice.setText(orderDetails.getPrice() + "");
        holder.itemSpmc.setText(orderDetails.getCommodity());
        holder.itemSjmc.setText(orderDetails.getBusiness());
        return convertView;
    }

    static class ViewHolder {

        private TextView itemSjmc;
        private TextView itemSpmc;
        private TextView itemPrice;
        private TextView itemNnm;
    }

    private void initView() {
    }
}
