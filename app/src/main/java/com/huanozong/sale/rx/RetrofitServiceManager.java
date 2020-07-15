package com.huanozong.sale.rx;

import android.os.Environment;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.huanozong.sale.api.HttpService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceManager {
private static final int DEFAULT_TIME_OUT = 10;//超时时间5s
private static final int DEFAULT_READ_TIME_OUT = 10;//读取时间
private static final int DEFAULT_WRITE_TIME_OUT = 10;//读取时间
private Retrofit mRetrofit;


private RetrofitServiceManager(){
    //OkHttpClient配置
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
    builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
    builder.writeTimeout(DEFAULT_WRITE_TIME_OUT,TimeUnit.SECONDS);
    builder.cache(new Cache(new File(Environment.getExternalStorageDirectory() + "/RxJavaDemo"),1024*1024*10));
    //cookie持久化管理
//    builder.cookieJar(new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(App.getInstance())));

//    addInterceptor(builder);


    mRetrofit = new Retrofit.Builder()
            .client(builder.build())
            .baseUrl(HttpService.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
}

/**
 * 添加各种拦截器
 * @param
 */
//private void addInterceptor(OkHttpClient.Builder builder) {
//    // 添加日志拦截器
//    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//    HttpHeaderInterceptor httpHeaderInterceptor = new HttpHeaderInterceptor.Builder().build();
//    //日志拦截
//    builder.addInterceptor(loggingInterceptor);
//    //头部参数拦截
//    builder.addInterceptor(httpHeaderInterceptor);
//    //缓存拦截
//    builder.addInterceptor(new HttpCacheInterceptor());
//    //请求参数拦截
//    builder.addInterceptor(new CommonParamsInterceptor());
//}

//单例 饿汉模式
private static class SingletonHolder{
    private static RetrofitServiceManager retrofitServiceManager = new RetrofitServiceManager();
}

public static RetrofitServiceManager getInstance(){
    return SingletonHolder.retrofitServiceManager;
}

//获取Service实例
public <T> T creat(Class<T> tClass){
    return mRetrofit.create(tClass);
}

}