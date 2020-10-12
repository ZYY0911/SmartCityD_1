package com.example.smartcityd_1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.ServiceInfo;
import com.example.smartcityd_1.util.MyGirdView;
import com.example.smartcityd_1.util.OnClickItem;

import java.util.List;
import java.util.Map;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 9:27
 */
public class AllServiceAdapter extends BaseExpandableListAdapter {
    private List<String> strings;
    private Map<String, List<ServiceInfo>> map;

    public AllServiceAdapter(List<String> strings, Map<String, List<ServiceInfo>> map) {
        this.strings = strings;
        this.map = map;
    }

    @Override
    public int getGroupCount() {
        return strings.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        TextView itemText = convertView.findViewById(R.id.item_text);
        itemText.setText(strings.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sevice_zi, parent, false);
        MyGirdView girdViewIte = convertView.findViewById(R.id.gird_view_item);
        AllServiceItemAdapter allServiceItemAdapter = new AllServiceItemAdapter(parent.getContext(), map.get(strings.get(groupPosition)));
        girdViewIte.setAdapter(allServiceItemAdapter);
        allServiceItemAdapter.setOnClickItem(new OnClickItem() {
            @Override
            public void onClick(int position, String name) {
                onClickItem.onClick(position, name);
            }
        });
        return convertView;
    }

    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void initView() {


    }
}

