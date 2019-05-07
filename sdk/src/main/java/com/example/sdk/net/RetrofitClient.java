package com.example.sdk.net;


import android.content.Context;
import android.text.TextUtils;

import com.example.sdk.BuildConfig;
import com.example.sdk.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 接口基本IP配置
 */

public class RetrofitClient {
    private static final int DEFAULT_TIMEOUT = 20;
    public static final String BASE_URL = "https://www.wanandroid.com";//WanAndroid的开放API
    public static String baseUrl = BASE_URL;
    private static Context mContext;
    private File httpCacheDirectory;
    private Cache cache = null;
    private OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private OkHttpClient.Builder httpBuilder;
    private BaseApiService apiService;
    private HttpLoggingInterceptor httpLoggingInterceptor;

    private RetrofitClient() { }

    private RetrofitClient(Context context) {
        this(context, baseUrl);
    }

    private RetrofitClient(Context context, String url) {
        this(context, url, null);
    }

    private RetrofitClient(Context context, String url, Map<String, String> headers) {
        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }
        //OkHttp缓存
        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "rocky_cache");
        }
        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            LogUtils.e("OKHttp Could not create http cache", e);
        }
        //创建OkHttp对象，是Retrofit的拓展之一
        httpBuilder = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //如果不是正式包,添加拦截打印:请求/响应行 + 头 + 体
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtils.i("RetrofitLog retrofitBack = " + message);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpBuilder.addInterceptor(httpLoggingInterceptor);
        }
        //生成okHttpClient
        okHttpClient = httpBuilder.build();

        //生成retrofit对象
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }

    //保持Retrofit全局单例
    private static class SingletonHolder {
        private static final RetrofitClient INSTANCE = new RetrofitClient(mContext);
    }

    public static RetrofitClient getInstance(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
        return SingletonHolder.INSTANCE;
    }

    //创建接口工作对象
    public RetrofitClient createBaseApi() {
        apiService = create(BaseApiService.class);
        return this;
    }

    private <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    //得到网络工作对象
    public Flowable post(String url, Map<String, String> parameters) {
        return apiService.post(url, parameters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //注册
    public Flowable getRegisterData(Map<String, String> parameters) {
        String user=parameters.get("user");
        String password=parameters.get("password");
        String repassword=parameters.get("repassword");
        return apiService.getRegisterData(user,password,repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //登陆
    //注册
    public Flowable getSigninData(Map<String, String> parameters) {
        String user=parameters.get("user");
        String password=parameters.get("password");
        return apiService.getSigninData(user,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
