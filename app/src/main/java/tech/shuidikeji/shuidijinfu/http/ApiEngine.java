package tech.shuidikeji.shuidijinfu.http;


import com.trello.rxlifecycle2.LifecycleTransformer;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import tech.shuidikeji.shuidijinfu.constants.UrlConstant;
import tech.shuidikeji.shuidijinfu.utils.AppUtils;
import tech.shuidikeji.shuidijinfu.utils.JsonUtils;
import tech.shuidikeji.shuidijinfu.utils.log.LogUtil;

public class ApiEngine {
    private static final int DEFAULT_TIMEOUT = 15;////请求超时时长，单位秒
    private static final int DEFAULT_CACHE_SIZE = 1024 * 1024 * 20; //大小默认20Mb

    private volatile static ApiEngine mInstance;

    private Retrofit mRetrofit;

    private ApiEngine(){
        File cacheFile = new File(AppUtils.getAppContext().getCacheDir(), "OkHttpCache");
        Cache cache = new Cache(cacheFile, DEFAULT_CACHE_SIZE);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置请求超时时长
                .addInterceptor(getHttpLoggingInterceptor())//启用Log日志
                .addInterceptor(new AppendUrlParamInterceptor())//添加公共参数
                .sslSocketFactory(createSSLSocketFactory())//设置https访问(验证证书)
                .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cache(cache);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(UrlConstant.API_HOST)
                //配置转化库，采用Gson
                .addConverterFactory(GsonConverterFactory.create())
                //配置回调库，采用RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //设置OKHttpClient为网络客户端
                .client(okHttpClientBuilder.build()).build();
    }


    public static ApiEngine getInstance() {
        if (mInstance == null) {
            synchronized (ApiEngine.class) {
                if (mInstance == null) {
                    mInstance = new ApiEngine();
                }
            }
        }
        return mInstance;
    }

    public ApiService getApiService() {
        return mRetrofit.create(ApiService.class);
    }

    private HttpLoggingInterceptor getHttpLoggingInterceptor(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
            try {
                String text = URLDecoder.decode(message, "utf-8");
                JsonUtils jsonUtils = new JsonUtils();
                if (!jsonUtils.validate(text)){
                    LogUtil.d("HttpLog",text);
                }else {
                    LogUtil.json("HttpLog", text);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                LogUtil.e("HttpLog", message);
            }

        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{createTrustAllManager()}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {

        }
        return sslSocketFactory;
    }

    private X509TrustManager createTrustAllManager() {
        X509TrustManager tm = null;
        try {
            tm =   new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    //do nothing，接受任意客户端证书
                }
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    //do nothing，接受任意服务端证书
                }
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
        } catch (Exception e) {

        }
        return tm;
    }





}
