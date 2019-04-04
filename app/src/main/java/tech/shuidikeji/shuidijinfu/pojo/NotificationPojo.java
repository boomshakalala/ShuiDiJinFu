package tech.shuidikeji.shuidijinfu.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationPojo implements Serializable {
    private ArrayList<String> notification;
    private Company company;
    private String activity_url;

    public ArrayList<String> getNotification() {
        return notification;
    }

    public Company getCompany() {
        return company;
    }

    public String getActivity_url() {
        return activity_url;
    }

    public static class Company implements Serializable{
        private String product_name;
        private String company_name;
        private String service_phone;
        private String abbreviation;
        private String legal_person;
        private String idcard;
        private String company_address;
        private String service_wx;

        public String getProduct_name() {
            return product_name;
        }

        public String getCompany_name() {
            return company_name;
        }

        public String getService_phone() {
            return service_phone;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public String getLegal_person() {
            return legal_person;
        }

        public String getIdcard() {
            return idcard;
        }

        public String getCompany_address() {
            return company_address;
        }

        public String getService_wx() {
            return service_wx;
        }
    }
}
