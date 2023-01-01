package com.getphone_contacts;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.phone.apple.getphone_contacts.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static int REQUEST_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        askContactsPermission();
    }


    @OnClick(R.id.but_phone)
    public void but_phone() {
        if (!ButtonUtils.isFastDoubleClick(R.id.but_phone)) {
            startActivity(new Intent(this, PhoneBookActivity.class));
        }
    }

    @OnClick(R.id.but_about)
    public void but_about() {
        if (!ButtonUtils.isFastDoubleClick(R.id.but_about)) {
            startActivity(new Intent(this, AboutActivity.class));
        }
    }

    //动态权限申请方法
    private void askContactsPermission(){
        String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE,Manifest.permission.WRITE_CONTACTS};//权限列表
        //   动态申请权限
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int permission2=ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_CONTACTS);
        if ((permission != PackageManager.PERMISSION_GRANTED)&&(permission2!=PackageManager.PERMISSION_GRANTED)) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this, PERMISSIONS_STORAGE, REQUEST_CONTACTS);
        }
    }

    //用户授权结果接收,如果用户拒绝授权，则弹出消息，并退出程序
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CONTACTS){
            if(grantResults.length<=0 || grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"用户拒绝授权，程序无法运行！",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}
