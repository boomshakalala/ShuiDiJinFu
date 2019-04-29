package tech.shuidikeji.shuidijinfu.pojo;

import java.io.Serializable;
import java.util.List;

public class CityListPojo implements Serializable {

    private String title;
    private String code;
    private String fuiou_area_id;
    private List<ChildrenPojo> children;

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getFuiou_area_id() {
        return fuiou_area_id;
    }

    public List<ChildrenPojo> getChildren() {
        return children;
    }

    public static class ChildrenPojo{
        private String title;
        private String code;
        private int pid;

        public String getTitle() {
            return title;
        }

        public String getCode() {
            return code;
        }

        public int getPid() {
            return pid;
        }
    }
}
