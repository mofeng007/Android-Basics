package com.mofeng.studentdatabasedemo;

import android.content.Context;
 import android.database.sqlite.SQLiteDatabase;
 import android.database.sqlite.SQLiteDatabase.CursorFactory;
 import android.database.sqlite.SQLiteOpenHelper;
 
 public class StudentDBHelper extends SQLiteOpenHelper {
 
     // 数据库相关常量
     private static final String DATABASE_NAME = "student_db";
     private static final String TABLE_NAME = "student";
     private static final int DATABASE_VERSION=1;

     //构造方法
     public StudentDBHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
     }

     /**
      * 在onCreate方法中，创建表，添加一部分表记录
      * @param db
      */
     @Override
     public void onCreate(SQLiteDatabase db) {
         String strSQL = "create table "+ TABLE_NAME
        		+" (sid integer primary key autoincrement,"
        		+" stu_no varchar(100), name varchar(100),"
        		+" clazz varchar(100), publish date)";
         db.execSQL(strSQL);
         String sql = "insert into " +TABLE_NAME
        		 +" values(null, '202001','李富贵','计算机1班','2020-06-13')";
         db.execSQL(sql);
         sql = "insert into " +TABLE_NAME
        		 +" values(null, '202102','万集合','计算机1班','2021-06-13')";
         db.execSQL(sql);
         sql = "insert into " +TABLE_NAME
        		 +" values(null, '201903','付鹏志','计算机1班','2019-06-14')";
         db.execSQL(sql);
         sql = "insert into " +TABLE_NAME
        		 +" values(null, '202004','白苍奇','物联网1班','2020-07-13')";
         db.execSQL(sql);
         sql = "insert into " +TABLE_NAME
        		 +" values(null, '202105','令柯','计算机2班','2021-06-13')";
         db.execSQL(sql);
         sql = "insert into " +TABLE_NAME
        		 +" values(null, '202006','胡建设','物联网1班','2020-05-13')";
         db.execSQL(sql);
         sql = "insert into " +TABLE_NAME
        		 +" values(null, '202107','张长治','计算机2班','2021-06-13')";
         db.execSQL(sql);
         sql = "insert into " +TABLE_NAME
        		 +" values(null, '202008','葛根秀','计算机2班','2020-06-13')";
         db.execSQL(sql);
         sql = "insert into " +TABLE_NAME
        		 +" values(null, '202109','邢捕头','计算机1班','2021-06-13')";
         db.execSQL(sql);
     }
     
     @Override
     public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {
 
     }
 }