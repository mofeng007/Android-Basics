package com.mofeng.fileiodemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

/**
 * @author 陌风
 * @create 2022-10-24 22:29
 **/
public class StudentOPHelper extends SQLiteOpenHelper {
    //数据库
    public StudentOPHelper(@Nullable Context context,   int version) {
        super(context,"book.db",null,version);
    }

    //创建表
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table t_user(id integer primary key autoincrement,"+
                                        "username varchar(50),"+
                                        "password varchar(20)"+
                                        ")";
        sqLiteDatabase.execSQL(sql);
    }

    //版本升级
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("ecut","更新数据库");
        String sql="alter table t_user add age integer";
        sqLiteDatabase.execSQL(sql);
    }
}
