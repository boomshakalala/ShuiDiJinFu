package tech.shuidikeji.shuidijinfu.pojo;

import java.io.Serializable;
import java.util.List;

public class AppConfigPojo implements Serializable {
    /**
     * 主页面底部导航栏菜单
     */
    private List<Menu> menu ;

    /**
     * 我的页面菜单选项
     */
    private List<UserMenu> user_menu ;

    /**
     *
     */
    private String loan_market_url;

    /**
     * 审核状态:1已上架 2审核中
     */
    private int check_status;

    /**
     * 首页主题 1 方块样式 2 转盘样式
     */
    private String theme;

    /**
     * 秒支付配置信息
     */
    private BeeCloud beecloud;

    public List<Menu> getMenu() {
        return menu;
    }

    public List<UserMenu> getUser_menu() {
        return user_menu;
    }

    public String getLoan_market_url() {
        return loan_market_url;
    }

    public int getCheck_status() {
        return check_status;
    }

    public String getTheme() {
        return theme;
    }

    public BeeCloud getBeecloud(){
        return this.beecloud;
    }

    @Override
    public String toString() {
        return "AppConfigPojo{" +
                "menu=" + menu +
                ", user_menu=" + user_menu +
                ", loan_market_url='" + loan_market_url + '\'' +
                ", check_status=" + check_status +
                ", theme='" + theme + '\'' +
                ", beecloud=" + beecloud +
                '}';
    }

    public static class Menu implements Serializable{
        /**
         * 名称
         */
        private String name;

        /**
         * 中文名称
         */
        private String cn_name;

        /**
         * 选中图片
         */
        private String img_on_focus;

        /**
         * 未选中图片
         */
        private String img_on_blur;

        public String getName() {
            return name;
        }


        public String getCn_name() {
            return cn_name;
        }



        public String getImg_on_focus() {
            return img_on_focus;
        }



        public String getImg_on_blur() {
            return img_on_blur;
        }

        @Override
        public String toString() {
            return "Menu{" +
                    "name='" + name + '\'' +
                    ", cn_name='" + cn_name + '\'' +
                    ", img_on_focus='" + img_on_focus + '\'' +
                    ", img_on_blur='" + img_on_blur + '\'' +
                    '}';
        }
    }

    public static class UserMenu implements Serializable{
        /**
         * 名称
         */
        private String name;

        /**
         * 中文名称
         */
        private String cn_name;

        /**
         * 图片
         */
        private String img;

        public String getName() {
            return name;
        }

        public String getCn_name() {
            return cn_name;
        }

        public String getImg() {
            return img;
        }

        @Override
        public String toString() {
            return "UserMenu{" +
                    "name='" + name + '\'' +
                    ", cn_name='" + cn_name + '\'' +
                    ", img='" + img + '\'' +
                    '}';
        }
    }

    public static class BeeCloud implements Serializable{
        private String app_id;

        private String app_secret;

        private String master_secret;

        private String wx_app_id;

        public String getApp_id() {
            return app_id;
        }


        public String getApp_secret() {
            return app_secret;
        }


        public String getMaster_secret() {
            return master_secret;
        }


        public String getWx_app_id() {
            return wx_app_id;
        }

        @Override
        public String toString() {
            return "BeeCloud{" +
                    "app_id='" + app_id + '\'' +
                    ", app_secret='" + app_secret + '\'' +
                    ", master_secret='" + master_secret + '\'' +
                    ", wx_app_id='" + wx_app_id + '\'' +
                    '}';
        }
    }
}
