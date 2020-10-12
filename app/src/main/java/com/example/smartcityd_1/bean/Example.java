package com.example.smartcityd_1.bean;

import java.io.Serializable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 16:21
 */
public class Example  implements Serializable {

    /**
     * caseid : 1
     * casetitle : 大爱湖北行，千里送爱心
     * casepicture : http://localhost:8080/mobileA/images/fpcasepic1.jpg
     * helperr : 严义军
     * reporttime : 2020-10-02 00:00:00
     * readnum : 9000
     * thumbup : 2003
     * userid : 1
     */

    private int caseid;
    private String casetitle;
    private String casepicture;
    private String helperr;
    private String reporttime;
    private int readnum;
    private int thumbup;
    private int userid;

    public int getCaseid() {
        return caseid;
    }

    public void setCaseid(int caseid) {
        this.caseid = caseid;
    }

    public String getCasetitle() {
        return casetitle;
    }

    public void setCasetitle(String casetitle) {
        this.casetitle = casetitle;
    }

    public String getCasepicture() {
        return casepicture;
    }

    public void setCasepicture(String casepicture) {
        this.casepicture = casepicture;
    }

    public String getHelperr() {
        return helperr;
    }

    public void setHelperr(String helperr) {
        this.helperr = helperr;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public int getReadnum() {
        return readnum;
    }

    public void setReadnum(int readnum) {
        this.readnum = readnum;
    }

    public int getThumbup() {
        return thumbup;
    }

    public void setThumbup(int thumbup) {
        this.thumbup = thumbup;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
