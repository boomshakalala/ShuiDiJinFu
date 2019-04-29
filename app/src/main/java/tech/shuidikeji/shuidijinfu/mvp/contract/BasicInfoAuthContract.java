package tech.shuidikeji.shuidijinfu.mvp.contract;



import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.pojo.BasicInfoConfigPojo;
import tech.shuidikeji.shuidijinfu.pojo.ProvincePojo;

public interface BasicInfoAuthContract {
    interface IBasicInfoAuthView extends LocationContract.ILocationView{
        void showBasicInfoAuthConfig(BasicInfoConfigPojo data);
        void showProvinceConfig(List<ProvincePojo> options1,List<List<String>> options2,List<List<List<String>>> options3);
        void showCommitSuccess();
    }

    interface IBasicInfoAuthModel extends LocationContract.ILocationModel{
        Observable<Map<String, Map<String,Integer>>> getBasicAuthConfig();

        Observable<List<ProvincePojo>> getProvinceConfig();

        Observable<Object> commitBasicInfo(String jobCompany, String jobAddress, String jobProvince,
                                           String jobCity, String jobDist,String qq, int maritalStatus,
                                           int social, int job,int jobType);

    }
}
