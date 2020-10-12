package com.example.smartcityd_1.bean;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 10:51
 */
public class Weather  {

    /**
     * date : 2020-10-12
     * weather : 霾
     * temperature : 11
     */

    private String date;
    private String weather;
    private int temperature;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
