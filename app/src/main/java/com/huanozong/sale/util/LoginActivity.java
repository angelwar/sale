package com.huanozong.sale.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Handler handler;
    private Handler handler1 = new Handler()
    {
        public void handleMessage(Message paramAnonymousMessage)
        {
            new AlertDialog.Builder(LoginActivity.this).setTitle("����").setMessage("����������������������������QQ 5132709").setIcon(2131165283).setOnDismissListener(new DialogInterface.OnDismissListener()
            {
                public void onDismiss(DialogInterface paramAnonymous2DialogInterface)
                {
                    LoginActivity.this.finish();
                }
            }).create().show();
        }
    };

}
