package com.example.smartcityd_1.bean;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/12 at 7:58
 */
public class UserInfo  {

    /**
     * id : 372401199008080808
     * name : 小T
     * avatar : http://localhost:8080/mobileA/images/user2.png
     * phone : 13805312222
     * sex : 女
     */

    private String id;
    private String name;
    private String avatar;
    private String phone;
    private String sex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
