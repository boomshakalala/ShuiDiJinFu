package tech.shuidikeji.shuidijinfu.http;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import tech.shuidikeji.shuidijinfu.BuildConfig;
import tech.shuidikeji.shuidijinfu.constants.PreferenceConstant;
import tech.shuidikeji.shuidijinfu.utils.MD5Utils;
import tech.shuidikeji.shuidijinfu.utils.SPUtils;

public class AppendUrlParamInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        HttpUrl.Builder builder = oldRequest
                .url()
                .newBuilder();

        String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        HttpUrl newUrl = builder.addQueryParameter("timestamp", timestamp)
                .addQueryParameter("sign", MD5Utils.MD5(timestamp + BuildConfig.API_KEY))
                .addQueryParameter("token", SPUtils.getString(PreferenceConstant.TOKEN,""))
                .build();

        Request newRequest = oldRequest
                .newBuilder()
                .url(newUrl)
                .build();

        return chain.proceed(newRequest);
    }
}