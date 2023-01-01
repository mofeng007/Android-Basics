package com.mofeng.fileiodemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.*;

public class MainActivity extends AppCompatActivity {
    Button b_write,b_read,b_login;
    StudentOPHelper stu_dbhelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stu_dbhelper=new StudentOPHelper(this,2);

        stu_dbhelper=new StudentOPHelper(this,1);
        sqLiteDatabase=stu_dbhelper.getWritableDatabase();

        String sql="insert into t_user values(null,'张三','admin')";
        sqLiteDatabase.execSQL(sql);

        sql="insert into t_user values(null,'李四','admin',12)";
        sqLiteDatabase.execSQL(sql);


        b_login=findViewById(R.id.bt3);
        b_write=findViewById(R.id.bt2);
        b_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //内部文件写操作
                try {
                    OutputStream os=openFileOutput("aaa.txt", Context.MODE_APPEND);
                    os.write("你好".getBytes());
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //sharedPreference写操作
                SharedPreferences sp=getSharedPreferences("set",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sp.edit();
                editor.putString("ip","192.168.1.10");
                editor.putString("name","张三");
                editor.putString("password","admin");
                editor.commit();

                //外部文件写操作
                //这个目录就是安卓下面的
                File root=getExternalFilesDir("");
                String msg=root.getAbsolutePath();
                Toast.makeText(MainActivity.this,msg,1).show();
                Log.i("msg",msg);
                try {
                    OutputStream os=new FileOutputStream(new File(root,"test.txt"));
                    os.write("这是一个测试！".getBytes());
                    os.close();

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        b_read=findViewById(R.id.bt1);
        b_read.setOnClickListener(new View.OnClickListener() {
           //内部文件读操作
            @Override
            public void onClick(View v) {
                try {
                    InputStream is=openFileInput("aaa.txt");
                    byte[] buf=new byte[1024];
                    is.read(buf);
                    String msg=new String(buf);
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
                    is.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //SharedPreferences读操作
                SharedPreferences sp=getSharedPreferences("set",Context.MODE_PRIVATE);
                String ip=sp.getString("ip","127.0.0.1");
                String username=sp.getString("name","admin");
                String password=sp.getString("password","123456");
                EditText et_ip=findViewById(R.id.textView1);
                EditText et_name=findViewById(R.id.textView2);
                EditText et_pass=findViewById(R.id.textView3);
                et_ip.setText(ip);
                et_name.setText(username);
                et_pass.setText(password);


            }
        });
    }

    public void Login(View v){
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
