package tech.shuidikeji.shuidijinfu.http;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        HttpResult result = gson.fromJson(response, HttpResult.class);
        if (result.getCode() == 200 ){
          return gson.fromJson(gson.toJson(result.getData()),type);
        }else {
            throw new ResultException(result.getCode(),result.getMessage(),gson.toJson(result.getData()));
        }
    }
}