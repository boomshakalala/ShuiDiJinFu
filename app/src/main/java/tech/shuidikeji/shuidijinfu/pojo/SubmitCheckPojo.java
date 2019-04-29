package tech.shuidikeji.shuidijinfu.pojo;

import java.io.Serializable;

public class SubmitCheckPojo implements Serializable {
    private String next;
    private String borrow_amout;
    private String borrow_days;

    public String getNext() {
        return next;
    }

    public String getBorrow_amout() {
        return borrow_amout;
    }

    public String getBorrow_days() {
        return borrow_days;
    }
}
