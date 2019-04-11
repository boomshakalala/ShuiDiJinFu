package tech.shuidikeji.shuidijinfu.pojo;

import java.io.Serializable;

public class AuthListPojo implements Serializable {

    private int verify_choice_id;
    private String name;
    private String code;
    private int status;
    private String success_img_url;
    private String failure_img_url;
    private String identification;
    private int is_opertional;
    private String valid_date;
    private int valid_status;

    public int getVerify_choice_id() {
        return verify_choice_id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public String getSuccess_img_url() {
        return success_img_url;
    }

    public String getFailure_img_url() {
        return failure_img_url;
    }

    public String getIdentification() {
        return identification;
    }

    public int getIs_opertional() {
        return is_opertional;
    }

    public String getValid_date() {
        return valid_date;
    }

    public int getValid_status() {
        return valid_status;
    }
}
