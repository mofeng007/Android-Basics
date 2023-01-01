package com.mofeng.intentdemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    EditText ed_user;
    EditText ed_pwd;
    MyHandler handler;
    TextView tv_p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_user=findViewById(R.id.ed_username);
        ed_pwd=findViewById(R.id.ed_password);
        tv_p=findViewById(R.id.textView3);
        handler=new MyHandler();
    }

    public void userLogin(View v){
        String username=ed_user.getText().toString();
        String password=ed_pwd.getText().toString();
        //两种传数据的方法
        Intent intent=new Intent(this,SecondActivity.class);
        //方一
        //intent.putExtra("user",username);
        //intent.putExtra("pwd",password);
        //方二,推荐使用
        Bundle bundle=new Bundle();
        bundle.putString("user",username);
        bundle.putString("pwd",password);
        intent.putExtras(bundle);
        //startActivity(intent);
        //接收返回结果数据
        startActivityForResult(intent,1001);
    }

    //接收第二个activity返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001){
            Bundle bundle=data.getExtras();
            String tel=bundle.getString("tel");
            TextView tv_tel=findViewById(R.id.textView1);
            tv_tel.setText("接收到的电话号码："+tel);
        }
    }

    class MyHandler extends Handler{
        //接收消息
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String p=msg.arg1+"%";
            tv_p.setText(p);
        }
    }

    class MyThread extends Thread{
        @Override
        public void run(){
            for(int i=0;i<100;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //不允许直接在线程中修改界面组件
                Message msg=new Message();
                msg.arg1=i;
                //发送消息
                handler.sendMessage(msg);
            }
        }
    }

    public void dowload(View v){
        tv_p=findViewById(R.id.textView3);
        MyThread myThread=new MyThread();
        myThread.start();
    }

}
