package com.example.smartcityd_1.dialog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.smartcityd_1.AppClient;
import com.example.smartcityd_1.R;
import com.example.smartcityd_1.util.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/9 at 20:04
 */
public class NetDialog extends DialogFragment {
    private EditText etIp;
    private EditText etPort;
    private Button btSave;
    private Button btExit;
    private ImageView ivClose;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.net_dialog, container, false);
    }

    SharedPreferences preferences;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        preferences = AppClient.sharedPreferences;
        getDialog().setCanceledOnTouchOutside(false);
        etIp.setText(preferences.getString(AppClient.IP, "192.168.155.106"));
        etPort.setText(preferences.getString(AppClient.PORT, "8080"));
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPort.getText()) || TextUtils.isEmpty(etIp.getText())) {
                    Util.showDialog("端口号或IP不能为空", getContext());
                    return;
                }
                preferences.edit().putString(AppClient.PORT, etPort.getText().toString()).apply();
                preferences.edit().putString(AppClient.IP, etIp.getText().toString()).apply();
                Util.showToast("设置成功", getContext());
                getDialog().dismiss();
            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }

    private void initView() {
        etIp = getView().findViewById(R.id.et_ip);
        etPort = getView().findViewById(R.id.et_port);
        btSave = getView().findViewById(R.id.bt_save);
        btExit = getView().findViewById(R.id.bt_exit);
        ivClose = getView().findViewById(R.id.iv_close);
    }
}
