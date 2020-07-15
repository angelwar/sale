package com.huanozong.sale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.huanozong.sale.R;
import com.huanozong.sale.api.APIService;
import com.huanozong.sale.api.HttpService;
import com.huanozong.sale.bean.LoginData;
import com.huanozong.sale.util.SharedPreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {


    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (SharedPreferencesUtil.queryUserID(this)!=-1){
            //id非等于默认值，保存过id数据，则直接销毁改页面，进入主页面
            startActivity(new Intent(this,HeadActivity.class));
            LoginActivity.this.finish();
            return;
        }
        initView();
    }

    private void initView() {
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
    }


    public void onLogin(View view){

        if (TextUtils.isEmpty(username.getText().toString())||TextUtils.isEmpty(password.getText().toString())){
            Toast.makeText(this,"请输入账号密码",Toast.LENGTH_SHORT).show();
            return;
        }
        //开始登陆
        doLogin(username.getText().toString(),password.getText().toString());
    }

    private void doLogin(String s, String s1) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<LoginData> call = service.doLogin(s, s1);
        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                Log.e("tag","doLogin on success "+response.body().toString());

                if (response.body().isSuccess()){
                    //登录成功 保存用户id
                    SharedPreferencesUtil.addUserID(LoginActivity.this,response.body().getData().getId());
                    //保存用户username
                    SharedPreferencesUtil.addUserName(LoginActivity.this,response.body().getData().getUsername());
                    //保存是否是管理员
                    SharedPreferencesUtil.setAdmin(LoginActivity.this,response.body().getData().getAdmin_id()==7);
                    Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this,HeadActivity
                            .class));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                Log.e("tag","doLogin on fail"+t.toString());
            }
        });
    }
}
