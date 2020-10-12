package com.example.smartcityd_1.bean;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 15:21
 */
public class DutByDempart {

    /**
     * num : 126
     * hospitalId : 3
     * departmentId : 1
     * time : 2020-10-3 周六 下午14：00
     * type : 普通门诊
     * doctorId : 21
     */

    private int num;
    private String hospitalId;
    private String departmentId;
    private String time;
    private String type;
    private String doctorId;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
