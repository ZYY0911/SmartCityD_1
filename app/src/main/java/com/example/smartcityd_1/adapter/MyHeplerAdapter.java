package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.MyHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 17:18
 */
public class MyHeplerAdapter extends ArrayAdapter<MyHelper> {

    public MyHeplerAdapter(@NonNull Context context, @NonNull List<MyHelper> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.help_item, parent, false);
            holder = new ViewHolder();
            holder.itemTitle = convertView.findViewById(R.id.item_title);
            holder.itemContext = convertView.findViewById(R.id.item_context);
            holder.itemMsg = convertView.findViewById(R.id.item_msg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MyHelper helper = getItem(position);
        holder.itemTitle.setText(helper.getSeektitle());
        holder.itemContext.setText("求助人：" + helper.getSeeker() + "\n" +
                "求助内容：" + helper.getSeekcontent());
        holder.itemMsg.setText("联系电话：" + helper.getTel() + "     时间：" + helper.getSeektime());

        return convertView;
    }

    static class ViewHolder {

        private TextView itemTitle;
        private TextView itemContext;
        private TextView itemMsg;
    }

    private void initView() {
    }
}
