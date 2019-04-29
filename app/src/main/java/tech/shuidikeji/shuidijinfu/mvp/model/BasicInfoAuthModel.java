package tech.shuidikeji.shuidijinfu.mvp.model;

import android.content.res.AssetManager;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import tech.shuidikeji.shuidijinfu.mvp.contract.BasicInfoAuthContract;
import tech.shuidikeji.shuidijinfu.pojo.ProvincePojo;
import tech.shuidikeji.shuidijinfu.utils.AppUtils;

public class BasicInfoAuthModel extends LocationModel implements BasicInfoAuthContract.IBasicInfoAuthModel {
    @Override
    public Observable<Map<String, Map<String,Integer>>> getBasicAuthConfig() {
        return mService.getBasicAuthConfig();
    }

    public Observable<List<ProvincePojo>> getProvinceConfig(){
        return Observable.create(emitter -> {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                AssetManager assetManager = AppUtils.getAppContext().getAssets();
                BufferedReader bf = new BufferedReader(new InputStreamReader(
                        assetManager.open("province.json")));
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            List<ProvincePojo> result = new ArrayList<>();
            JSONArray data = new JSONArray(stringBuilder.toString());
            for (int i = 0; i < data.length(); i++) {
                ProvincePojo item = gson.fromJson(data.optJSONObject(i).toString(),ProvincePojo.class);
                result.add(item);
            }
            emitter.onNext(result);
        });
    }

    public Observable<Object> commitBasicInfo(String jobCompany, String jobAddress, String jobProvince,
                                              String jobCity, String jobDist,String qq, int maritalStatus,
                                              int social, int job,int jobType) {
        return mService.commitBasicInfo(jobCompany,jobAddress,jobProvince,jobCity,jobDist,qq,maritalStatus,social,job,jobType);

    }
}
