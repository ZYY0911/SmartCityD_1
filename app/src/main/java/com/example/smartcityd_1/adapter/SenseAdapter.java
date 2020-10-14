package com.example.smartcityd_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcityd_1.R;
import com.example.smartcityd_1.bean.Sense;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/13 at 16:10
 */
public class SenseAdapter extends ArrayAdapter<Sense> {
    private Sense sense;

    public SenseAdapter(@NonNull Context context, Sense sense) {
        super(context, 0);
        this.sense = sense;
    }


    private String[] arr = {"紫外线指数", "感冒指数", "穿衣指数", "运动指数", "空气扩散指数"};

    @Override
    public int getCount() {
        return 5;
    }

    int valeu;

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
        String strContent = "";
        String strMsg = "";
        switch (position) {
            case 0:
                valeu = sense.getIllumination();
                if (valeu < 1000) {
                    strContent = "弱";
                    strMsg = "辐射较弱，涂擦SPF12~15、PA+护肤品";
                } else if (valeu > 3000) {
                    strContent = "强";
                    strMsg = "尽量减少外出，需要涂抹高倍数防晒霜";
                } else {
                    strContent = "中等";
                    strMsg = "涂擦SPF大于15、PA+防晒护肤品";
                }
                break;
            case 1:
                valeu = sense.getTemperature();
                if (valeu < 8) {
                    strContent = "较易发";
                    strMsg = "温度低，风较大，较易发生感冒，注意防护";
                } else {
                    strContent = "少发";
                    strMsg = " 无明显降温，感冒机率较低";
                }
                break;
            case 2:
                valeu = sense.getTemperature();
                if (valeu < 12) {
                    strContent = "冷";
                    strMsg = "建议穿长袖衬衫、单裤等服装";
                } else if (valeu > 21) {
                    strContent = "热";
                    strMsg = "适合穿T恤、短薄外套等夏季服装";
                } else {
                    strContent = "舒适";
                    strMsg = "建议穿短袖衬衫、单裤等服装";
                }
                break;
            case 3:
                valeu = sense.getCo2();
                if (valeu < 3000) {
                    strContent = "适宜";
                    strMsg = "气候适宜，推荐您进行户外运动";
                } else if (valeu > 6000) {
                    strContent = "较不宜";
                    strMsg = "空气氧气含量低，请在室内进";
                } else {
                    strContent = "中";
                    strMsg = "易感人群应适当减少室外活动";
                }
                break;
            case 4:
                valeu = sense.get_$Pm25250();
                if (valeu < 30) {
                    strContent = "优";
                    strMsg = "空气质量非常好，非常适合户外活动，趁机出去多呼吸新鲜空气";
                } else if (valeu > 100) {
                    strContent = "污染";
                    strMsg = "空气质量差，不适合户外活动";
                } else {
                    strContent = "良";
                    strMsg = "易感人群应适当减少室外活动";
                }
                break;
        }

        holder.itemTitle.setText(arr[position]+"(" + valeu+")");
        holder.itemContext.setText(strContent);
        holder.itemMsg.setText(strMsg);

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
