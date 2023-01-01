package com.mofeng.test1;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class TestActivity extends AppCompatActivity {
    TextView tv_num1;
    TextView tv_num2;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tv_num1=findViewById(R.id.t_num1);
        tv_num2=findViewById(R.id.t_num2);
        tv_result=findViewById(R.id.t_result);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String num1=bundle.getString("num1");
        String num2=bundle.getString("num2");
        String result=bundle.getString("result");
        int x=Integer.parseInt(num1);
        int y=Integer.parseInt(num2);
        int r=Integer.parseInt(result);
        tv_num1.setText("你的答案："+num1+"+"+num2+"="+result);
        tv_num2.setText("正确答案："+num1+"+"+num2+"="+(x+y));
        if(x+y==r){
            tv_result.setText("回答正确！继续加油喔");
        }else {
            tv_result.setText("回答错误！请再接再厉！");
        }
    }

    public void btReturn(View v){
        Intent intent=new Intent(TestActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}