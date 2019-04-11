package tech.shuidikeji.shuidijinfu.pojo;

import java.io.Serializable;

public class BorrowListPojo implements Serializable {
    private String borrow_id;
    private String sn;
    private int status;
    private String borrow_amount;
    private String type;
    private String create_time;
    private String addition_amount;
    private String overdue_days;
    private String truely_repay_time;
    private String status_txt;

    public String getBorrow_id() {
        return borrow_id;
    }

    public String getSn() {
        return sn;
    }

    public int getStatus() {
        return status;
    }

    public String getBorrow_amount() {
        return borrow_amount;
    }

    public String getType() {
        return type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getAddition_amount() {
        return addition_amount;
    }

    public String getOverdue_days() {
        return overdue_days;
    }

    public String getTruely_repay_time() {
        return truely_repay_time;
    }

    public String getStatus_txt() {
        return status_txt;
    }
}
