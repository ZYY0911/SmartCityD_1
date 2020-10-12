package com.example.smartcityd_1.net;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 7:38
 */
public interface VolleyLo {
    void onResponse(JSONObject jsonObject);

    void onErrorResponse(VolleyError volleyError);
}
