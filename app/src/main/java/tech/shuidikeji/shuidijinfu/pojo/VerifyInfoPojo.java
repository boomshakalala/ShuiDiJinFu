package tech.shuidikeji.shuidijinfu.pojo;

import java.io.Serializable;

public class VerifyInfoPojo implements Serializable {
    private int status;
    private String status_text;
    private int status_bankcard;
    private String status_bankcard_text;
    private int has_buy;
    private String report_price;
    private int borrow_status;
    private String borrow_id;

    public int getStatus() {
        return status;
    }

    public String getStatus_text() {
        return status_text;
    }

    public int getStatus_bankcard() {
        return status_bankcard;
    }

    public String getStatus_bankcard_text() {
        return status_bankcard_text;
    }

    public int getHas_buy() {
        return has_buy;
    }

    public String getReport_price() {
        return report_price;
    }

    public int getBorrow_status() {
        return borrow_status;
    }

    public String getBorrow_id() {
        return borrow_id;
    }
}
