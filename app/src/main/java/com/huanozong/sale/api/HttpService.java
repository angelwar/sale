package com.huanozong.sale.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpService {

    public static final String API_URL = "http://app.hzmtkj.com/";


    public  static void getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
    }


    public static APIService getService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpService.API_URL) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(APIService.class);
    }

//    public Observable<LoginData> doLogin(String username, String password){
//        return  service.doLogin(username,password);
//    }

}
