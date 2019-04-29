package tech.shuidikeji.shuidijinfu.pojo;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;
import java.util.List;

public class BasicInfoConfigPojo implements Serializable {
    private List<ItemPojo> jobType;
    private List<ItemPojo> job;
    private List<ItemPojo> maritalStatus;
    private List<ItemPojo> social;

    public BasicInfoConfigPojo(List<ItemPojo> jobType, List<ItemPojo> job, List<ItemPojo> maritalStatus, List<ItemPojo> social) {
        this.jobType = jobType;
        this.job = job;
        this.maritalStatus = maritalStatus;
        this.social = social;
    }

    public List<ItemPojo> getJobType() {
        return jobType;
    }

    public List<ItemPojo> getJob() {
        return job;
    }

    public List<ItemPojo> getMaritalStatus() {
        return maritalStatus;
    }

    public List<ItemPojo> getSocial() {
        return social;
    }

    public static class ItemPojo implements Serializable, IPickerViewData {
       private int code;
       private String text;

        public ItemPojo(int code, String text) {
            this.code = code;
            this.text = text;
        }

        public int getCode() {
           return code;
       }

       public String getText() {
           return text;
       }

        @Override
        public String getPickerViewText() {
            return text;
        }
    }
}
