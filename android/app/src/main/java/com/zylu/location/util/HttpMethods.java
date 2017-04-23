package com.zylu.location.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zylu.location.util.Constants.BASE_URL;

/**
 * Created by Larry on 2017/2/12.
 */

public class HttpMethods {
    private static final int DEFAULT_TIMEOUT = 5;
    private static Retrofit retrofit = null;

    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Accept-Encoding", "gzip, deflate, br")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                                .build();

                        return chain.proceed(request);
                    }
                })
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static Retrofit getInstance(){
        if(retrofit == null) {
            new HttpMethods();
        }
        return retrofit;
    }

}
