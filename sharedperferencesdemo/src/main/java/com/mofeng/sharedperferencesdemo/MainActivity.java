package com.mofeng.sharedperferencesdemo;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final String fileName="config";
    EditText serverName, ipAddress, pwd,userName;
    Button button1,button2;

    private void initComponents(){
        serverName = (EditText)findViewById(R.id.serverName);
        userName = (EditText)findViewById(R.id.userName);
        ipAddress = (EditText)findViewById(R.id.ipAddress);
        pwd = (EditText)findViewById(R.id.pwd);
        button1=(Button) findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
    }

    View.OnClickListener button1Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences sp = getSharedPreferences(fileName, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("serverName",serverName.getText().toString());
            editor.putString("ipAddress",ipAddress.getText().toString());
            editor.putString("userName",userName.getText().toString());
            editor.putString("pwd",pwd.getText().toString());
            editor.commit();
            Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener button2Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences sp = getSharedPreferences(fileName, Activity.MODE_PRIVATE);
            serverName.setText(sp.getString("serverName",""));
            userName.setText(sp.getString("userName",""));
            pwd.setText(sp.getString("pwd",""));
            ipAddress.setText(sp.getString("ipAddress",""));
            Toast.makeText(MainActivity.this,"加载成功",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        button1.setOnClickListener(button1Listener);
        button2.setOnClickListener(button2Listener);
    }
}
