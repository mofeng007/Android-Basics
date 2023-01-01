package com.mofeng.curriculumdesign;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity implements OnClickListener {
    // 获取界面中显示歌曲标题、作者文本框
    TextView title, author, currentTime, totalTime;
    // 播放/暂停、停止按钮
    ImageButton play;
    ImageView lastMusic, nextMusic;
    // 进度条
    ProgressBar progressBar;

    ActivityReceiver activityReceiver;

    public static final String CTL_ACTION =
            "org.xr.action.CTL_ACTION";
    public static final String UPDATE_ACTION =
            "org.xr.action.UPDATE_ACTION";
    // 定义音乐的播放状态，0x11代表没有播放；0x12代表正在播放；0x13代表暂停
    int status = 0x11;
    String[] titleStrs = new String[]{"Legends Never Die", "约定", "谁来剪月光"};
    String[] authorStrs = new String[]{"英雄联盟", "周蕙","陈奕迅"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取程序界面界面中的两个按钮
        play = (ImageButton) this.findViewById(R.id.play);
        lastMusic = this.findViewById(R.id.lastMusic);
        nextMusic = this.findViewById(R.id.nextMusic);
        title = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);
        currentTime = findViewById(R.id.currentTime);
        totalTime = findViewById(R.id.totalTime);
        progressBar = findViewById(R.id.progressBar);


        // 为两个按钮的单击事件添加监听器
        play.setOnClickListener(this);
        lastMusic.setOnClickListener(this);
        nextMusic.setOnClickListener(this);

        activityReceiver = new ActivityReceiver();
        // 创建IntentFilter
        IntentFilter filter = new IntentFilter();
        // 指定BroadcastReceiver监听的Action
        filter.addAction(UPDATE_ACTION);
        // 注册BroadcastReceiver
        registerReceiver(activityReceiver, filter);

        Intent intent = new Intent(this, MusicService.class);
        // 启动后台Service
        startService(intent);
    }

    // 自定义的BroadcastReceiver，负责监听从Service传回来的广播
    public class ActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取Intent中的update消息，update代表播放状态
            int update = intent.getIntExtra("update", -1);
            // 获取Intent中的current消息，current代表当前正在播放的歌曲
            int current = intent.getIntExtra("current", -1);
            int totalPosition = intent.getIntExtra("totalTime", -1);
            int currentPosition = intent.getIntExtra("currentTime", -1);
            Log.d("activityReceiver", "收到广播");
            Log.d("activityReceiver", "current:" + current + " totalPosition:" + totalPosition + " currentPosition:" + currentPosition + " update:" + update);
            if (current >= 0) {
                title.setText(titleStrs[current]);
                author.setText(authorStrs[current]);
            }

            if (totalPosition >= 0) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.CHINA);
                Date date = new Date(totalPosition);
                String formatTime = simpleDateFormat.format(date);
                totalTime.setText(formatTime);
            }

            if (currentPosition >= 0) {
                double process = ((double)currentPosition / totalPosition)*100;
                Log.d("activityReceiver", "当前进度:" + (double)currentPosition/totalPosition);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.CHINA);
                Date date = new Date(currentPosition);
                String formatTime = simpleDateFormat.format(date);
                progressBar.setProgress((int) process);
                currentTime.setText(formatTime);
            }

            switch (update) {
                case 0x11:
                    play.setImageResource(R.drawable.play);
                    status = 0x11;
                    break;
                // 控制系统进入播放状态
                case 0x12:
                    // 播放状态下设置使用暂停图标
                    play.setImageResource(R.drawable.pause);
                    // 设置当前状态
                    status = 0x12;
                    break;
                // 控制系统进入暂停状态
                case 0x13:
                    // 暂停状态下设置使用播放图标
                    play.setImageResource(R.drawable.play);
                    // 设置当前状态
                    status = 0x13;
                    break;
            }
        }
    }

    @Override
    public void onClick(View source) {
        // 创建Intent
        Intent intent = new Intent("org.xr.action.CTL_ACTION");
        switch (source.getId()) {
            // 按下播放/暂停按钮
            case R.id.play:
                intent.putExtra("control", 1);
                break;
            case R.id.lastMusic:
                intent.putExtra("control", 3);
            case R.id.nextMusic:
                intent.putExtra("control", 2);
        }
        // 发送广播，将被Service组件中的BroadcastReceiver接收到
        sendBroadcast(intent);
    }
}

