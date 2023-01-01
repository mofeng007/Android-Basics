package com.mofeng.notificationdemo;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MsgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        //获取Intent对象，并读取通知传过来的参数
        Intent intent=getIntent();
        String content=intent.getStringExtra("content");
        String title=intent.getStringExtra("title");
        //获取页面组件，将通知内容显示在页面上
        TextView msgContent=findViewById(R.id.msgContent);
        TextView msgTitle=findViewById(R.id.msgTitle);
        msgContent.setText(content);
        msgTitle.setText(title);
        Button returnBtn=findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}