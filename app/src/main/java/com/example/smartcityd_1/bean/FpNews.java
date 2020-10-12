package com.example.smartcityd_1.bean;

import java.io.Serializable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 15:56
 */
public class FpNews  implements Serializable {

    /**
     * newsid : 1
     * newstitle : 松口古镇：“活化”客家古驿道 激发乡村新活力
     * newsreporter : 王海龙
     * reporttime : 2020-04-03 00:00:00
     * newspicture : http://localhost:8080/mobileA/images/fpnewspic1.jpg
     * newscontent : 近年来，松口镇结合乡村振兴战略，加大古驿道文化内涵挖掘力度和保护开发力度，并以此为抓手改善提升农村人居环境，带动农村经济发展，助推乡村振兴。
     * readnum : 34
     */

    private int newsid;
    private String newstitle;
    private String newsreporter;
    private String reporttime;
    private String newspicture;
    private String newscontent;
    private int readnum;

    public int getNewsid() {
        return newsid;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getNewsreporter() {
        return newsreporter;
    }

    public void setNewsreporter(String newsreporter) {
        this.newsreporter = newsreporter;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public String getNewspicture() {
        return newspicture;
    }

    public void setNewspicture(String newspicture) {
        this.newspicture = newspicture;
    }

    public String getNewscontent() {
        return newscontent;
    }

    public void setNewscontent(String newscontent) {
        this.newscontent = newscontent;
    }

    public int getReadnum() {
        return readnum;
    }

    public void setReadnum(int readnum) {
        this.readnum = readnum;
    }
}
