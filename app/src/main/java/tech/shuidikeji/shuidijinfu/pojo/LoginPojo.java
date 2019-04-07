package tech.shuidikeji.shuidijinfu.pojo;

import java.io.Serializable;

public class LoginPojo implements Serializable {
    private String user_id;
    private String token;

    public String getUser_id() {
        return user_id;
    }

    public String getToken() {
        return token;
    }
}
