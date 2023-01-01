package com.mofeng.curriculumdesign2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author 陌风
 * @create 2022-12-06 19:44
 **/
public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, "test.db", null, 1);
    }

    //当数据库第一次创建的时候执行
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE information(_id INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR(20),phone VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
