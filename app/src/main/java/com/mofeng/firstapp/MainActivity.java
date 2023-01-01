package com.mofeng.firstapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Activity_Life","创建Activity.onCreate()...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Activity_Life","可见状态，onStart()...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Activity_Life","获得焦点，能进行交互，onResume()...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Activity_Life","部分可见或不可见状态，onPause()...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Activity_Life","完全不可见状态，onStop()...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Activity_Life","重新可见状态，onRestart()...");
    }

    public void register(View v){
        Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }


    public void second(View v){
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        startActivity(intent);
    }

    public void third(View v){
        Intent intent=new Intent(MainActivity.this,ThirdActivity.class);
        startActivity(intent);
    }



}