package com.example.smartcityd_1.bean;

import java.io.Serializable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 17:56
 */
public class ValavgePeople implements Serializable {

    /**
     * villiagerid : 1
     * name : 史明祥
     * villid : 1
     */

    private int villiagerid;
    private String name;
    private int villid;

    public int getVilliagerid() {
        return villiagerid;
    }

    public void setVilliagerid(int villiagerid) {
        this.villiagerid = villiagerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVillid() {
        return villid;
    }

    public void setVillid(int villid) {
        this.villid = villid;
    }
}
