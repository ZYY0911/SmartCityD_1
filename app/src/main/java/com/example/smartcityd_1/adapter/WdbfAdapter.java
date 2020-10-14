package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.ValageInfo;
import com.example.smartcityd_1.bean.VilageLsit;
import com.example.smartcityd_1.bean.Wdbf;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 17:26
 */
public class WdbfAdapter extends ArrayAdapter<Wdbf> {

    public WdbfAdapter(@NonNull Context context, @NonNull List<Wdbf> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
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
        final Wdbf wdbf = getItem(position);
        holder.itemTitle.setText(wdbf.getApplydesc());
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("fpVillageList")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        List<VilageLsit> v = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<VilageLsit>>() {
                                }.getType());
                        for (int i = 0; i < v.size(); i++) {
                            VilageLsit vilageLsit = v.get(i);
                            if (vilageLsit.getVillid() == wdbf.getVillid()) {

                                holder.itemContext.setText(
                                        "村名：" + vilageLsit.getVillname() + "地址：" + vilageLsit.getVilladdress());
                            }
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        VolleyTo volleyTo1 = new VolleyTo();
        volleyTo1.setUrl("fpApplyStateById")
                .setJsonObject("stateid",wdbf.getApplystate())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONObject jsonObject1 = jsonObject.optJSONArray(Util.Rows).optJSONObject(0);
                        holder.itemMsg.setText("申请状态：" + jsonObject1.optString("statename")+"   开始时间：" + wdbf.getStarttime());
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
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
