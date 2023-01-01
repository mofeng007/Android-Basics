package com.mofeng.test1;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    TextView tv_name;
    TextView tv_num1;
    TextView tv_num2;
    EditText ed_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_name=findViewById(R.id.textView3);
        tv_num1=findViewById(R.id.num1);
        tv_num2=findViewById(R.id.num2);
        ed_result=findViewById(R.id.result);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String username=bundle.getString("username");
        tv_name.setText(username+"：欢迎使用！");

        //生成随机数；生成一个从 1-100 的整数
        int num1=(int)(Math.random()*100+1);
        int num2=(int)(Math.random()*100+1);
        tv_num1.setText(""+num1);
        tv_num2.setText(""+num2);
    }

    //生成随机数；生成一个从 1-100 的整数
    public void creatNum(View v){
        int num1=(int)(Math.random()*100+1);
        int num2=(int)(Math.random()*100+1);
        tv_num1.setText(""+num1);
        tv_num2.setText(""+num2);
    }

    public void test(View v){
        Intent intent1=new Intent(LoginActivity.this,TestActivity.class);
        String num1=tv_num1.getText().toString();
        String num2=tv_num2.getText().toString();
        String result=ed_result.getText().toString();
        Bundle bundle=new Bundle();
        bundle.putString("num1",num1);
        bundle.putString("num2",num2);
        bundle.putString("result",result);
        intent1.putExtras(bundle);
        startActivity(intent1);
    }
}