package com.mofeng.intentdemo;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView tv_info=findViewById(R.id.textView2);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String user=bundle.getString("user");
        String pwd=bundle.getString("pwd");
        tv_info.setText("接收到的信息："+user+"\t"+pwd);
    }

    public void submitTel(View v){
        EditText et_tel=findViewById(R.id.editTextTextPersonName);
        String tel=et_tel.getText().toString();
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putString("tel",tel);
        intent.putExtras(bundle);
        setResult(1,intent);
        finish();
    }


}