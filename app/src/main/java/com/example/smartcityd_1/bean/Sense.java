package com.example.smartcityd_1.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 11:09
 */
public class Sense {

    /**
     * RESULT : S
     * temperature : 31
     * illumination : 3872
     * co2 : 1933
     * pm2.5 : 54
     */

    private String RESULT;
    private int temperature;
    private int illumination;
    private int co2;
    @SerializedName("pm2.5")
    private int _$Pm25250; // FIXME check this code

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getIllumination() {
        return illumination;
    }

    public void setIllumination(int illumination) {
        this.illumination = illumination;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int get_$Pm25250() {
        return _$Pm25250;
    }

    public void set_$Pm25250(int _$Pm25250) {
        this._$Pm25250 = _$Pm25250;
    }
}
