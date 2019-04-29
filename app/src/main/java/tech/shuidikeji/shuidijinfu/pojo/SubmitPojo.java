package tech.shuidikeji.shuidijinfu.pojo;

import java.io.Serializable;

public class SubmitPojo implements Serializable {
    private String title;
    private String message;
    private String btn_info;
    private String borrow_id;
    private String next;

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getBtn_info() {
        return btn_info;
    }

    public String getBorrow_id() {
        return borrow_id;
    }

    public String getNext() {
        return next;
    }
}
