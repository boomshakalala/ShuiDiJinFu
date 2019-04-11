package tech.shuidikeji.shuidijinfu.pojo;

import java.io.Serializable;

public class BankCardPojo implements Serializable {
    private String bankcard_bank;
    private String bankcard_number;
    private String bankcard_img;

    public String getBankcard_bank() {
        return bankcard_bank;
    }

    public String getBankcard_number() {
        return bankcard_number;
    }

    public String getBankcard_img() {
        return bankcard_img;
    }
}
