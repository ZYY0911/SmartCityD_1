package com.example.smartcityd_1.bean;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 16:58
 */
public class ValageInfo  {

    /**
     * detail_id : 1
     * villid : 1
     * enviroment_pic : http://localhost:8080/mobileA/images/envirpic1.jpg
     * reporttime : 2020-06-04 00:00:00
     * report : 谭疏祺
     * readernum : 300
     */

    private int detail_id;
    private int villid;
    private String enviroment_pic;
    private String reporttime;
    private String report;
    private int readernum;

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public int getVillid() {
        return villid;
    }

    public void setVillid(int villid) {
        this.villid = villid;
    }

    public String getEnviroment_pic() {
        return enviroment_pic;
    }

    public void setEnviroment_pic(String enviroment_pic) {
        this.enviroment_pic = enviroment_pic;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public int getReadernum() {
        return readernum;
    }

    public void setReadernum(int readernum) {
        this.readernum = readernum;
    }
}
