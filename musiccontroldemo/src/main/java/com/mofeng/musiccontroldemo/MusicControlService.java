package com.mofeng.musiccontroldemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;

/**
 * @author 陌风
 * @create 2022-11-02 22:33
 **/
public class MusicControlService extends Service {
    private MediaPlayer mp;  //播放器组件，在onCreate方法中实例化
    int resId=R.raw.xsd;  //默认播放音乐资源id

    //内部类，播放器控制类
    public class Controller extends Binder{
        //设置新的资源
        public void setRes(int res){
            resId=res;
        }
        public void pause(){
            if (mp != null && mp.isPlaying())
                mp.pause();
        }
        public void play (){
            if (mp!=null)
                mp.start();
        }
        public void stop (){
            if (mp!=null)
                mp.stop();
        }
        //返回当前mediaplay的播放状态
        public boolean isPlaying(){
            if (mp!=null)
                return mp.isPlaying();
            else
                return false;
        }
        //返回音乐总长度
        public int getDuration(){
            return mp.getDuration();
        }
        //返回当前进度
        public int getCurrentPosition(){
            return mp.getCurrentPosition();
        }
    }

    //实现该抽象方法，当服务绑定时，返回播放器控制对象
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Controller();
    }
    //不论该服务被启动和绑定多少次，该方法只会执行一次，因此在此实例化播放器
    public void onCreate(){
        super.onCreate();
        mp=MediaPlayer.create(this,resId);
    }
    public void onDestroy(){
        super.onDestroy();
        if (mp!=null)
            mp.release();
        mp=null;
    }
}
