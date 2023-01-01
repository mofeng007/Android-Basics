package com.mofeng.musiccontroldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    //页面组件变量
    Button begin,stop,play,pause;
    ImageView faceImage;
    ProgressBar pb;
    TextView musicName,musicTime;
    Intent intent=null;  //启动服务，绑定服务，停止服务用的intent变量
    boolean isBegin=false;  //服务绑定的状态变量，默认为未绑定
    boolean isPlaying=false;  //音乐播放的状态变量，默认为未播放
    //定义Runnable变量，并实例化，重写run
    // 根据服务对象获取时间长度和当前播放值，修改进度条，计算剩余时间并格式；
    Runnable freshRunnable=new Runnable() {
        @Override
        public void run() {
            //如果播放器已启动，且处于播放状态，则更新进度条
            if (isBegin && isPlaying){
                int total=control.getDuration();  //根据服务控制类获取总时长
                int current=control.getCurrentPosition();  //根据服务控制类获取当前进度
                pb.setMax(total);
                pb.setProgress(current);
                musicTime.setText(formatTime(total-current));
                //停止音乐，停止服务
                if (current>=total)
                    stopService();
            }
        }
    };

    //进度条刷新线程，通过runOnUiThread()执行freshRunnable接口对象；
    class FreshThread extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MainActivity.this.runOnUiThread(freshRunnable);
            }
        }
    }
    //刷新进度条线程的全局变量，用于音乐暂停时，杀死线程
    FreshThread freshThread=null;
    //绑定服务返回对象的变量
    private MusicControlService.Controller control;
    //定义服务链对象，实现其抽象方法
    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            control=(MusicControlService.Controller) iBinder;
            //获取返回对象后，根据对象状态，修改面板图片、进度可见性、标志变量的值；
            if (control.isPlaying()){
                isBegin=true;  //设置服务绑定状态
                isPlaying=true;  //设置正在播放的状态
                //设置进度条相关的三个组件可见
                musicName.setVisibility(View.VISIBLE);
                musicTime.setVisibility(View.VISIBLE);
                pb.setVisibility(View.VISIBLE);
                //设置面板播放gif动画
                Glide.with(MainActivity.this).load(R.drawable.playing).into(faceImage);
                //实例化并启动刷新进度条线程
                freshThread=new FreshThread();
                freshThread.start();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    //定义方法，获取所有的组件
    private void findView(){
        begin=findViewById(R.id.begin);
        stop=findViewById(R.id.stop);
        play=findViewById(R.id.play);
        pause=findViewById(R.id.pause);
        faceImage=findViewById(R.id.faceImage);
        pb=findViewById(R.id.pb);
        musicName=findViewById(R.id.musicName);
        musicTime=findViewById(R.id.musicTime);
        //默认设置以下三项不可见
        pb.setVisibility(View.INVISIBLE);
        musicTime.setVisibility(View.INVISIBLE);
        musicName.setVisibility(View.INVISIBLE);
    }

    //时间格式化方法
    private String formatTime(int time){
        int sec=time/1000;
        int min=sec/60;
        sec=sec%60;
        StringBuffer sb=new StringBuffer();
        sb.append(min).append(":");
        if (sec>9)
            sb.append(sec);
        else
            sb.append("0").append(sec);
        return sb.toString();
    }

    //初始化服务，即先绑定再启动服务，即使页面退出，服务仍然继续，同时修改状态变量和面板图片
    private void initService(){
        intent=new Intent(MainActivity.this,MusicControlService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
        startService(intent);
        isBegin=true;
        faceImage.setImageResource(R.drawable.play);
    }

    //定义启动按钮监听器,点击启动按钮，如果服务没启动或者已停止，则重新绑定并启动服务，
    private View.OnClickListener beginListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //如果还没有绑定service，则启动service
            if (!isBegin){
                initService();  //绑定并启动服务
                faceImage.setImageResource(R.drawable.play);
                isBegin=true;
                musicName.setVisibility(View.VISIBLE);
                musicTime.setVisibility(View.VISIBLE);
                pb.setVisibility(View.VISIBLE);
                pb.setProgress(0);  //点击启动时，默认播放进度条未0
            }
        }
    };

    //定义播放按钮监听器,
    private View.OnClickListener playListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(isBegin){ //如果已经绑定服务，并且没有播放，切换图片，开始播放
                if(!isPlaying) {
                    faceImage.setImageResource(R.drawable.playing);
                    Glide.with(MainActivity.this).load(R.drawable.playing).into(faceImage);
                    control.play();
                    //把进度信息显示出来
                    musicName.setVisibility(View.VISIBLE);
                    musicTime.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.VISIBLE);
                    isPlaying = true;
                    freshThread = new FreshThread();
                    freshThread.start();
                }
            }else {
                String msg = "请先启动播放器，然后播放!";
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }
    };
    //暂停按钮监听器
    private View.OnClickListener pauseListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            // 如果正在播放，则播放器暂停，修改面板和状态变量，停止进度条刷新线程
            if(isPlaying){
                faceImage.setImageResource(R.drawable.play);
                control.pause();
                isPlaying = false;
                if(freshThread!=null && freshThread.isAlive())
                    freshThread.interrupt();
            }
        }
    };
    //停止按钮监听器
    private View.OnClickListener stopListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            stopService();
        }
    };
    private void stopService(){
        if(isBegin){  //如果播放器启动，及服务启动，则停止服务，修改状态
            // TODO Auto-generated method stub
            faceImage.setImageResource(R.drawable.preplay);
            control.stop();  //先停止音乐
            unbindService(mConnection); //解绑服务，
            stopService(intent);  //停止服务
            control = null;
            //修改状态，停止进度条刷新线程，隐藏进度条
            isBegin = false;
            isPlaying = false;
            if(freshThread!=null && freshThread.isAlive())
                freshThread.interrupt();
            musicTime.setVisibility(View.INVISIBLE);
            musicName.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        //页面创建时，默认绑定并启动服务，目的是获取服务对象，以获取后台音乐的播放状态，
        initService();
        //注册按钮的监听器
        begin.setOnClickListener(beginListener);
        play.setOnClickListener(playListener);
        pause.setOnClickListener(pauseListener);
        stop.setOnClickListener(stopListener);
    }
}
