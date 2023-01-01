package com.mofeng.curriculumdesign2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView name;
    private TextView phone;
    private Button btnAdd;
    private Button btnDel;
    private Button btnUqd;
    private Button btnSel;
    private String uPhone;
    private String uName;
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private TextView show;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHelper = new MyHelper(this);
        init();
    }

    private void init() {
        show = (TextView) findViewById(R.id.show);
        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        btnAdd = (Button) findViewById(R.id.insert);
        btnDel = (Button) findViewById(R.id.delete);
        btnUqd = (Button) findViewById(R.id.update);
        btnSel = (Button) findViewById(R.id.select);
        btnAdd.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnUqd.setOnClickListener(this);
        btnSel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select:
                db = myHelper.getReadableDatabase();
                Cursor cursor = db.query("information", null, null, null, null, null, null);
                if (cursor.getCount() == 0) {
                    Toast.makeText(this, "没有数据", Toast.LENGTH_LONG).show();
                } else {
                    cursor.moveToFirst();
                    show.setText("Name:" + cursor.getString(1) + "Tel:" + cursor.getString(2));
                }
                while (cursor.moveToNext()) {
                    show.append("\n" + "Name" + cursor.getString(1) + "Tel" + cursor.getString(2));
                }
                cursor.close();
                db.close();
                break;
            case R.id.insert:
                uName = name.getText().toString();
                uPhone = phone.getText().toString();
                db = myHelper.getReadableDatabase();
                contentValues = new ContentValues();
                contentValues.put("name", uName);
                contentValues.put("phone", uPhone);
                db.insert("information", null, contentValues);
                Toast.makeText(this,"添加成功",Toast.LENGTH_LONG ).show();
                db.close();
                break;
            case R.id.update:
                db = myHelper.getReadableDatabase();
                contentValues = new ContentValues();
                contentValues.put("phone", uPhone = phone.getText().toString());
                db.update("information", contentValues, "name=?", new String[]{name.getText().toString()});
                Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
                db.close();
                break;
            case R.id.delete:
                db = myHelper.getReadableDatabase();
                db.delete("information", null, null);
                Toast.makeText(this, "信息已经删除", Toast.LENGTH_LONG).show();
                show.setText("");
                db.close();
                break;
        }
    }

}
