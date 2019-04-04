package tech.shuidikeji.shuidijinfu.http;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;


public class ThrowableHandler {

    /**
     * 处理异常从而得到异常类型以及异常提示
     */
    public static HttpThrowable handleThrowable(Throwable throwable) {
        if (throwable instanceof HttpException) {
            return new HttpThrowable(HttpThrowable.HTTP_ERROR, "网络(协议)错误", throwable);
        } else if (throwable instanceof JsonParseException || throwable instanceof JSONException || throwable instanceof ParseException) {
            return new HttpThrowable(HttpThrowable.PARSE_ERROR, "解析错误", throwable);
        } else if (throwable instanceof UnknownHostException) {
            return new HttpThrowable(HttpThrowable.NO_NET_ERROR, "DNS解析错误(无网络)", throwable);
        } else if (throwable instanceof SocketTimeoutException) {
            return new HttpThrowable(HttpThrowable.TIME_OUT_ERROR, "连接超时错误", throwable);
        } else if (throwable instanceof ConnectException) {
            return new HttpThrowable(HttpThrowable.CONNECT_ERROR, "连接错误", throwable);
        } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
            return new HttpThrowable(HttpThrowable.SSL_ERROR, "证书验证错误", throwable);
        } else {
            return new HttpThrowable(HttpThrowable.UNKNOWN, throwable.getMessage(), throwable);
        }
    }


}