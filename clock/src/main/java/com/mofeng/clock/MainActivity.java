package com.mofeng.clock;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView tv_show;
    int second;
    int minute;
    int hour;
//    MyHandle handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show=findViewById(R.id.tv_showTime);
//        handle=new MyHandle();
    }


//    class MyHandle extends Handler{
//        public void handleMessage(Message msg){
//            tv_show.setText("00:00:"+(msg.arg1>10?msg.arg1:"0"+msg.arg1));
//        }
//    }



    public void startClock(View v){
        //多线程来实现
        new Thread(){
            @Override
            public void run() {
                //12小时
                for (int i=0;i<43200;i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    second++;
                    if (second>=60){
                        minute++;
                        second=0;
                    }
                    if (minute>=60){
                        hour++;
                        minute=0;
                    }
//                    TimerRunnable timerRunnable=new TimerRunnable();
                    runOnUiThread( new Runnable(){
                        @Override
                        public void run() {
                            tv_show.setText((hour>=10?hour:"0"+hour)+":"+(minute>=10?minute:"0"+minute)+":"+(second>=10?second:"0"+second));
                        }
                    });
//                    Message msg=new Message();
//                    msg.arg1=i;
//                    handle.sendMessage(msg);
                }
            };
        }.start();

        //第二种方法
//        Timer timer=new Timer();
//        TimerTask timerTask=new TimerTask() {
//            @Override
//            public void run() {
//                second++;
//                    if (second>=60){
//                       minute++;
//                        second=0;
//                    }
//                    if (minute>=60){
//                        hour++;
//                        minute=0;
//                    }
//                runOnUiThread( new Runnable(){
//                        @Override
//                        public void run() {
//                            tv_show.setText((hour>=10?hour:"0"+hour)+":"+(minute>=10?minute:"0"+minute)+":"+(second>=10?second:"0"+second));
//                        }
//                    });
//            }
//        };
//        //三秒后开始，一秒执行一次
//        timer.schedule(timerTask,3000,1);

    }
}
