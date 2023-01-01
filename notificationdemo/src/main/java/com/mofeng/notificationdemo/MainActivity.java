package com.mofeng.notificationdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Spinner nfSpinner;
    EditText nfTitle,nfContent;
    Button sendBtn;
    //存放渠道类型名称，"不重要通知","最低的通知","稍微重要的通知","默认通知","重要通知"
    List<String> noticeList;
    //渠道ID数组
    String [] channelIds={"notificationDemo11","notificationDemo22","notificationDemo33",
            "notificationDemo44","notificationDemo55"};
    //渠道类型数组
    int [] noticeImportance={
            NotificationManager.IMPORTANCE_NONE,
            NotificationManager.IMPORTANCE_MIN,
            NotificationManager.IMPORTANCE_LOW,
            NotificationManager.IMPORTANCE_DEFAULT,
            NotificationManager.IMPORTANCE_HIGH
    };

    private void findViews(){
        nfSpinner = (Spinner)findViewById(R.id.nfChannel);
        nfTitle = (EditText)findViewById(R.id.nfTitle);
        nfContent = (EditText)findViewById(R.id.nfContent);
        sendBtn = (Button)findViewById(R.id.sendBtn);
    }

    private Notification createNotification(NotificationManager manager,String channelId,
                                            String title,String content){
        //创建一个通知构造器
        Notification.Builder builder=new Notification.Builder(MainActivity.this);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            builder=new Notification.Builder(MainActivity.this,channelId);
        }
        //构建PendingIntent，当点击通知时，跳转到消息阅读页面；
        Intent cIntent=new Intent(MainActivity.this,MsgActivity.class);
        //使用bundle传递通知标题及内容参数
        Bundle bundle=new Bundle();
        bundle.putString("title",title);
        bundle.putString("content",content);
        cIntent.putExtras(bundle);
        //PendingIntent的请求码，采用随机数，避免重复
        Random random=new Random();
        PendingIntent pIntent=PendingIntent.getActivity(MainActivity.this,random.nextInt(1000),
                cIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        //设置消息元素
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.small);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.big));
        builder.setAutoCancel(true);
        builder.setUsesChronometer(true);  //设置计时器
        builder.setContentIntent(pIntent);  //设置点击意图
        int index=nfSpinner.getSelectedItemPosition();
        builder.setSubText(nfSpinner.getSelectedItem().toString());  //将通知级别名作为副标题
        return  builder.build();
    }

    View.OnClickListener sendBtnListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel=null;  //定义通道变量
            //根据用户所选的重要性级别，重新设置渠道的重要性登记
            int index=nfSpinner.getSelectedItemPosition();
            //开发者还可以额外配置渠道的特性
            //如果系统版本在8.0及其以上版本，才可以使用通道
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                //首先从管理器中获取通道，如果通道不存在，重新创建一个，因为一个通道只能创建一次
                channel=manager.getNotificationChannel(channelIds[index]);
                if (channel==null){
                    //参数依次为：渠道号，名称，重要性等级
                    channel=new NotificationChannel(channelIds[index],"新渠道",
                            noticeImportance[index]);
                    manager.createNotificationChannel(channel);  //创建渠道
                }
            }
            //获取用户输入的通知标题和内容
            String content=nfContent.getText().toString();
            String title=nfTitle.getText().toString();
            if (content.equals("") || title.equals("")){
                Toast.makeText(MainActivity.this,"请输入标题和内容！",Toast.LENGTH_SHORT).show();
                return;
            }
            Notification noti=createNotification(manager,channelIds [index],title,content);
            Random random=new Random();
            manager.notify(random.nextInt(1000),noti);  //使用随机数，避免渠道id重复
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();  //获取页面组件
        //初始化Spinner的适配器数据
        noticeList=new ArrayList<String>();
        noticeList.add("不重要通知");
        noticeList.add("最低的通知");
        noticeList.add("稍微重要的通知");
        noticeList.add("默认通知");
        noticeList.add("重要通知");
        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,noticeList);
        nfSpinner.setAdapter(adapter);
        sendBtn.setOnClickListener(sendBtnListener);
    }
}
