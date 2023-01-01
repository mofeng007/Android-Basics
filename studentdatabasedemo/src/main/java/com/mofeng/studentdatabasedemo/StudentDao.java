package com.mofeng.studentdatabasedemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
 public class StudentDao {
     private SQLiteDatabase db;
     public SQLiteDatabase getDB(){
    	 return db;
     }
     public StudentDao(SQLiteOpenHelper dbHelper) {
         db = dbHelper.getWritableDatabase();
     }

     /**
      * 查询，并将结果存入list中。
      * @param list
      * @param strSQL
      */
     public void execQuery(List<Map<String,Object>> list,final String strSQL) {
         try {
             Cursor cursor = db.rawQuery(strSQL, null);
             cursor.moveToFirst();
             list.clear();
             while (!cursor.isAfterLast()) {
            	 Map<String,Object> map = new HashMap<String,Object>();
            	 map.put("sid", cursor.getInt(0));
            	 map.put("stu_no", cursor.getString(1));
            	 map.put("name", cursor.getString(2));
            	 map.put("clazz", cursor.getString(3));
            	 map.put("publish", cursor.getString(4));
            	 list.add(map);
                 cursor.moveToNext();
             }
         }catch (RuntimeException e) {
             e.printStackTrace();
         }
     }

     /**
      * 执行sql语句，
      * @param strSQL
      * @return
      */
     public boolean execSQL(final String strSQL) {
         db.beginTransaction();  //
         try {
             db.execSQL(strSQL);
             db.setTransactionSuccessful();  //
             return true;
         } catch (RuntimeException e) {
             e.printStackTrace();
             return false;
         }finally {  
             db.endTransaction();    //
         }  
 
     }
 }
