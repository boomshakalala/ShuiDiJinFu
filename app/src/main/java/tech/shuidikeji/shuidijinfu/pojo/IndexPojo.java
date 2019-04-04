package tech.shuidikeji.shuidijinfu.pojo;

import java.io.Serializable;

public class IndexPojo implements Serializable {
    private int total;
    private int finnal;
    private String top_text;
    private String foot_text;

    public int getTotal() {
        return total;
    }

    public int getFinnal() {
        return finnal;
    }

    public String getTop_text() {
        return top_text;
    }

    public String getFoot_text() {
        return foot_text;
    }
}
