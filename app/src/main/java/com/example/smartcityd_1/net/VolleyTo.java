package com.example.smartcityd_1.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.smartcityd_1.AppClient;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 7:33
 */
public class VolleyTo extends Thread {
    private String URl = "http://" + AppClient.sharedPreferences.getString(AppClient.IP, "192.168.155.106") + ":" + AppClient.sharedPreferences.getString(AppClient.PORT, "8080") + "/mobileA/";
    private boolean isLoop;
    private int time;
    private JSONObject jsonObject = new JSONObject();
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            if (msg.what == 1) {
                volleyLo.onResponse((JSONObject) msg.obj);
            } else {
                volleyLo.onErrorResponse((VolleyError) msg.obj);
            }
            return false;
        }
    });
    private VolleyLo volleyLo;
    private ProgressDialog dialog;

    public VolleyTo setDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setTitle("提示");
        dialog.setMessage("网络请求中....");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return this;
    }

    public VolleyTo setVolleyLo(VolleyLo volleyLo) {
        this.volleyLo = volleyLo;
        return this;
    }

    public VolleyTo setUrl(String URl) {
        this.URl += URl;
        return this;
    }

    public VolleyTo setLoop(boolean loop) {
        isLoop = loop;
        return this;
    }

    public VolleyTo setTime(int time) {
        this.time = time;
        return this;
    }

    public VolleyTo setJsonObject(String k, Object v) {
        try {
            jsonObject.put(k, v);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }


    @Override
    public void run() {
        super.run();
        do {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URl, jsonObject
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Message message = new Message();
                    message.obj = jsonObject;
                    message.what = 1;
                    if (dialog == null) {
                        handler.sendMessage(message);
                    } else {
                        handler.sendMessageDelayed(message, 1000);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Message message = new Message();
                    message.obj = volleyError;
                    message.what = 2;
                    if (dialog == null) {
                        handler.sendMessage(message);
                    } else {
                        handler.sendMessageDelayed(message, 1000);
                    }
                }
            });
            AppClient.add(jsonObjectRequest);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (isLoop);
    }
}
