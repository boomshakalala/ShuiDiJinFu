package tech.shuidikeji.shuidijinfu.pojo;

import java.io.Serializable;

public class IdCardPojo implements Serializable {
    private String name;
    private String nation;
    private String number;
    private String sex;
    private String birth;
    private String address;

    public String getName() {
        return name;
    }

    public String getNation() {
        return nation;
    }

    public String getNumber() {
        return number;
    }

    public String getSex() {
        return sex;
    }

    public String getBirth() {
        return birth;
    }

    public String getAddress() {
        return address;
    }
}
