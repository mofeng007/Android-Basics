package com.mofeng.viewpager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    List<View> viewLists;
    ViewPager viewPager;
    List<String> tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager1);
        viewLists = new ArrayList<>();
        tabs=new ArrayList<String>();
        tabs.add("第一");
        tabs.add("第二");
        tabs.add("第三");

        //它的作用是把 xml 布局转换为对应的 View 对象
        LayoutInflater lf = getLayoutInflater();
        View view1 = lf.inflate(R.layout.viewpager1, null);
        View view2 = lf.inflate(R.layout.viewpager2, null);
        View view3 = lf.inflate(R.layout.viewpager3, null);

        viewLists.add(view1);
        viewLists.add(view2);
        viewLists.add(view3);

        MyViewPagerAdapter mpa = new MyViewPagerAdapter(viewLists,tabs);

        //设置适配器
        viewPager.setAdapter(mpa);
        Button bt1=findViewById(R.id.button1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("温馨提示");
                builder.setMessage("是否真的退出？");
                builder.setIcon(R.drawable.ic_launcher_foreground);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                        MainActivity.this.finish();
                    }
                });
                builder.setNegativeButton("否",null);

                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        Button bt2=findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pdialog=new ProgressDialog(MainActivity.this);
                pdialog.setMessage("正在下载ing...");
                pdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pdialog.setMax(100);
                Timer timer=new Timer();
                timer.schedule(new TimerTask() {
                    int progress=0;
                    @Override
                    public void run() {
                        progress+=10;
                        pdialog.setProgress(progress);
                    }
                },0,500);
                pdialog.show();
            }
        });
    }

}
