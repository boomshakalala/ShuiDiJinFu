package tech.shuidikeji.shuidijinfu.mvp.presenter;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import tech.shuidikeji.shuidijinfu.http.RespObserver;
import tech.shuidikeji.shuidijinfu.mvp.contract.BasicInfoAuthContract;
import tech.shuidikeji.shuidijinfu.mvp.model.BasicInfoAuthModel;
import tech.shuidikeji.shuidijinfu.pojo.BasicInfoConfigPojo;
import tech.shuidikeji.shuidijinfu.pojo.ProvincePojo;
import tech.shuidikeji.shuidijinfu.utils.RxUtils;

public class BasicInfoAuthPresenter extends LocationPresenter<BasicInfoAuthContract.IBasicInfoAuthView, BasicInfoAuthContract.IBasicInfoAuthModel> {
    public BasicInfoAuthPresenter(BasicInfoAuthContract.IBasicInfoAuthView view) {
        super(view, new BasicInfoAuthModel());
    }

    public void getBasicInfoAuthConfig(){
        getView().showProgressDialog();
        mModel.getBasicAuthConfig().compose(RxUtils.transform(getView())).subscribe(new RespObserver<Map<String,Map<String,Integer>>>() {
            @Override
            public void onResult(Map<String,Map<String,Integer>> data) {
                getView().showBasicInfoAuthConfig(genConfigData(data));
                getProvinceConfig();
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }

    public void commitBasicInfo(String jobCompany, String jobAddress, String jobProvince,
                                String jobCity, String jobDist,String qq, int maritalStatus,
                                int social, int job,int jobType){
        getView().showProgressDialog();
        mModel.commitBasicInfo(jobCompany,jobAddress,jobProvince,jobCity,jobDist,qq,maritalStatus,social,job,jobType).compose(RxUtils.transform(getView())).subscribe(new RespObserver<Object>() {
            @Override
            public void onResult(Object data) {
                getView().showCommitSuccess();
            }

            @Override
            public void onError(int errCode, String errMsg) {
                getView().dismissProgressDialog();
                getView().showError(errMsg);
            }
        });
    }

    private void getProvinceConfig(){
        mModel.getProvinceConfig().compose(RxUtils.transform(getView())).subscribe(new Observer<List<ProvincePojo>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<ProvincePojo> provincePojos) {
                getView().dismissProgressDialog();
                List<List<String>> options2 = new ArrayList<>();
                List<List<List<String>>> options3 = new ArrayList<>();
                //遍历省份
                for (int i = 0; i < provincePojos.size(); i++) {
                    //该省的城市列表（第二级）
                    List<String> cityList = new ArrayList<>();
                    //该省的所有地区列表（第三极）
                    List<List<String>> province_AreaList = new ArrayList<>();
                    //遍历该省份的所有城市
                    for (int c = 0; c < provincePojos.get(i).getCity().size(); c++) {
                        String cityName = provincePojos.get(i).getCity().get(c).getName();
                        //添加城市
                        cityList.add(cityName);
                        //该城市的所有地区列表
                        ArrayList<String> city_AreaList = new ArrayList<>();
                        //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                        if (provincePojos.get(i).getCity().get(c).getArea() == null
                                || provincePojos.get(i).getCity().get(c).getArea().size() == 0) {
                            city_AreaList.add("");
                        } else {
                            city_AreaList.addAll(provincePojos.get(i).getCity().get(c).getArea());
                        }
                        city_AreaList.addAll(provincePojos.get(i).getCity().get(c).getArea());
                        //添加该省所有地区数据
                        province_AreaList.add(city_AreaList);
                    }
                    //添加城市数据
                    options2.add(cityList);
                    //添加地区数据
                    options3.add(province_AreaList);
                }
                getView().showProvinceConfig(provincePojos,options2,options3);
            }

            @Override
            public void onError(Throwable e) {
                getView().dismissProgressDialog();
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private List<BasicInfoConfigPojo.ItemPojo> genConfigItemData(Map<String,Integer> jsonObject){
        List<BasicInfoConfigPojo.ItemPojo> result = new ArrayList<>();
        for (Map.Entry<String,Integer> entry : jsonObject.entrySet()){
            result.add(new BasicInfoConfigPojo.ItemPojo(entry.getValue(),entry.getKey()));
        }
        return result;
    }

    private BasicInfoConfigPojo genConfigData(Map<String,Map<String,Integer>>  jsonObject){

        List<BasicInfoConfigPojo.ItemPojo> jobType = genConfigItemData(Objects.requireNonNull(jsonObject.get("job_type")));
        List<BasicInfoConfigPojo.ItemPojo> job = genConfigItemData(Objects.requireNonNull(jsonObject.get("job")));
        List<BasicInfoConfigPojo.ItemPojo> maritalStatus = genConfigItemData(Objects.requireNonNull(jsonObject.get("marital_status")));
        List<BasicInfoConfigPojo.ItemPojo> social = genConfigItemData(Objects.requireNonNull(jsonObject.get("social")));
        return new BasicInfoConfigPojo(jobType,job,maritalStatus,social);
    }
}
