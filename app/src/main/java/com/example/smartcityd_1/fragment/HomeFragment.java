package com.example.smartcityd_1.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.VolleyError;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.activity.AppHomeActivity;
import com.example.smartcityd_1.activity.ReadNewActivity;
import com.example.smartcityd_1.activity.UrlService;
import com.example.smartcityd_1.adapter.HomeServiceAdapter;
import com.example.smartcityd_1.adapter.HoutThemeAdapter;
import com.example.smartcityd_1.bean.HomeImages;
import com.example.smartcityd_1.bean.NewsList;
import com.example.smartcityd_1.bean.ServiceWeight;
import com.example.smartcityd_1.net.VolleyImage;
import com.example.smartcityd_1.net.VolleyLo;
import com.example.smartcityd_1.net.VolleyLoImage;
import com.example.smartcityd_1.net.VolleyTo;
import com.example.smartcityd_1.util.MyGirdView;
import com.example.smartcityd_1.util.OnClickItem;
import com.example.smartcityd_1.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/11 at 20:23
 */
public class HomeFragment extends Fragment {
    private AppHomeActivity appHomeActivity;
    private ViewFlipper viewFlipper;
    private MyGirdView girdView;
    private GridView girdTheme;
    private LinearLayout layoutNew;
    private LinearLayout newsLayout;

    public HomeFragment() {
    }

    public HomeFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static HomeFragment newInstance(AppHomeActivity appHomeActivity) {
        return new HomeFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setVolley_Image();
        setVolley_Service();
        setVolley_Subject();
        setVolley_News();
    }

    List<NewsList> newsLists;

    private void setVolley_News() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getNEWsList")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        newsLists = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<NewsList>>() {
                                }.getType());
                        setVolley_NewsType();

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    List<String> newTypes;

    private void setVolley_NewsType() {
        newTypes = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getAllNewsType")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray(Util.Rows);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            newTypes.add(jsonObject1.optString("newstype"));
                        }
                        setVolley_Recommend();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();

    }

    List<Integer> recommendNews;

    private void setVolley_Recommend() {
        recommendNews = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getRecommend")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray(Util.Rows);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            recommendNews.add(Integer.parseInt(jsonObject1.optString("newsid")));
                        }
                        setNewTypeLayout();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setNewTypeLayout() {
        layoutNew.removeAllViews();
        newTypes.add(0, "推荐");
        Log.i("aaa", "setNewTypeLayout: " + strings.size());
        for (int i = 0; i < newTypes.size(); i++) {
            final TextView textView = new TextView(getActivity());
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setTextSize(20);
            textView.setText(newTypes.get(i));
            textView.setGravity(Gravity.CENTER);
            if (i == 0) {
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                textView.setBackgroundResource(R.drawable.text_line);
            }
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < layoutNew.getChildCount(); j++) {
                        TextView textView = (TextView) layoutNew.getChildAt(j);
                        if (j == finalI) {
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            textView.setBackgroundResource(R.drawable.text_line);
                        } else {
                            textView.setTextColor(Color.parseColor("#333333"));
                            textView.setBackgroundResource(R.drawable.text_no_line);
                        }
                    }
                    String type = textView.getText().toString();
                    setLayoutType(type);
                }
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, 0, 40, 0);
            textView.setLayoutParams(layoutParams);
            layoutNew.addView(textView);
        }
        setLayoutType("推荐");
    }

    private void setLayoutType(String type) {
        List<NewsList> newsLists1 = new ArrayList<>();
        for (int i = 0; i < newsLists.size(); i++) {
            NewsList newsList = newsLists.get(i);
            if (type.equals("推荐")) {
                if (getTj(Integer.parseInt(newsList.getNewsid()))) {
                    newsLists1.add(newsList);
                }
            } else {
                if (newsList.getNewsType().equals(type)) {
                    newsLists1.add(newsList);
                }
            }
        }
        newsLayout.removeAllViews();
        for (int i = 0; i < newsLists1.size(); i++) {
            final NewsList newsList = newsLists1.get(i);
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.news_item, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 5, 0, 5);
            view.setLayoutParams(layoutParams);
            final ViewHolder holder = new ViewHolder();
            holder.itemImage = view.findViewById(R.id.item_image);
            holder.itemTitle = view.findViewById(R.id.item_title);
            holder.itemContext = view.findViewById(R.id.item_context);
            holder.itemMsg = view.findViewById(R.id.item_msg);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
                }
            });
            VolleyImage volleyImage = new VolleyImage();
            volleyImage.setUrl(newsList.getPicture())
                    .setVolleyLoImage(new VolleyLoImage() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            holder.itemImage.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
            holder.itemTitle.setText(newsList.getTitle());
            holder.itemContext.setText(newsList.getAbstractX());
            VolleyTo volleyTo = new VolleyTo();
            volleyTo.setUrl("getCommitById")
                    .setJsonObject("id", newsList.getNewsid())
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            JSONArray jsonArray = jsonObject.optJSONArray(Util.Rows);
                            final int all = jsonArray.length();
                            VolleyTo volleyTo1 = new VolleyTo();
                            volleyTo1.setUrl("getNewsInfoById")
                                    .setJsonObject("newsid", newsList.getNewsid())
                                    .setVolleyLo(new VolleyLo() {
                                        @Override
                                        public void onResponse(JSONObject jsonObject) {
                                            holder.itemMsg.setText("总评：" + all + "\n发布日期：" + jsonObject.optJSONArray(Util.Rows).optJSONObject(0).optString("publicTime"));

                                        }

                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {

                                        }
                                    }).start();

                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ReadNewActivity.class);
                    intent.putExtra("info", "新闻");
                    startActivity(intent);
                }
            });
            newsLayout.addView(view);
        }

    }

    static class ViewHolder {

        private ImageView itemImage;
        private TextView itemTitle;
        private TextView itemContext;
        private TextView itemMsg;
    }

    private boolean getTj(int id) {
        for (int i = 0; i < recommendNews.size(); i++) {
            if (id == recommendNews.get(i))
                return
                        true;
        }
        return false;
    }


    List<String> strings;

    private void setVolley_Subject() {
        strings = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getAllSubject")
                .setDialog(getActivity())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String jsonArray[] = jsonObject.optString("ROWS_DETAIL").replace("[", "").replace("]", "").split(",");
                        for (int i = 0; i < jsonArray.length; i++) {
                            strings.add(jsonArray[i].trim());
                        }
                        girdTheme.setAdapter(new HoutThemeAdapter(getActivity(), strings));
                        girdTheme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                appHomeActivity.switchFragemnt(appHomeActivity.map.get("1"));
                            }
                        });
                        girdTheme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getActivity(), ReadNewActivity.class);
                                intent.putExtra("info", "热门主题");
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    List<ServiceWeight> serviceWeights;

    private void setVolley_Service() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getServiceInOrder")
                .setJsonObject("order", 2)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        serviceWeights = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<ServiceWeight>>() {
                                }.getType());
                        HomeServiceAdapter adapter = new HomeServiceAdapter(getActivity(), serviceWeights);
                        girdView.setAdapter(adapter);
                        adapter.setOnClickItem(new OnClickItem() {
                            @Override
                            public void onClick(int position, String name) {
                                if (name.equals("更多服务")) {
                                    appHomeActivity.switchFragemnt(appHomeActivity.map.get("全部服务"));
                                } else if (name.equals("实时天气")) {
                                    appHomeActivity.switchFragemnt(appHomeActivity.map.get("实时天气"));
                                } else if (name.equals("门诊预约")) {
                                    appHomeActivity.switchFragemnt(appHomeActivity.map.get("门诊预约"));
                                } else {
                                    Intent intent = new Intent(getActivity(), UrlService.class);
                                    intent.putExtra("info", Integer.parseInt(serviceWeights.get(position).getId()));
                                    intent.putExtra("name",name);
                                    startActivity(intent);
                                }
                            }
                        });
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }


    List<HomeImages> homeImages;

    private void setVolley_Image() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getImages")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        homeImages = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<HomeImages>>() {
                                }.getType());
                        setImageLayout();

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();

    }

    private void setImageLayout() {
        for (int i = 0; i < homeImages.size(); i++) {
            final ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            VolleyImage volleyImage = new VolleyImage();
            HomeImages homeImage = homeImages.get(i);
            volleyImage.setUrl(homeImage.getPath())
                    .setVolleyLoImage(new VolleyLoImage() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            imageView.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ReadNewActivity.class);
                    intent.putExtra("info", "新闻");
                    startActivity(intent);
                }
            });
            viewFlipper.addView(imageView);
        }
    }

    private void initView() {
        viewFlipper = getView().findViewById(R.id.view_flipper);
        girdView = getView().findViewById(R.id.gird_view);
        girdTheme = getView().findViewById(R.id.gird_theme);
        layoutNew = getView().findViewById(R.id.layout_new);
        newsLayout = getView().findViewById(R.id.news_layout);
    }
}
