package com.ncj.maillist;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;

import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.ncj.maillist.DBHelper.DBHelper;
import com.ncj.maillist.Dao.Imp.MaillistDaoImp;
import com.ncj.maillist.Dao.MaillistDao;
import com.ncj.maillist.Entity.Maillist;

import java.util.LinkedList;
import java.util.List;

import static android.os.Build.*;

public class MainActivity extends AppCompatActivity {

    ImageView addbutton;
    ScrollView lv;
    MaillistDao mld;
    EditText searchtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if (VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_PERMISSION_STORAGE = 100;
            String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_PERMISSION_STORAGE);
                    return;
                }
            }
        }
    }
    public void init(){
        searchtext = (EditText) findViewById(R.id.searchtext);
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                lv.removeAllViews();
                List<Maillist> ml = mld.SearchLikeMaillist(searchtext.getText().toString());
                show(ml);
            }
        });
        mld = new MaillistDaoImp(getApplicationContext());
        addbutton = (ImageView) findViewById(R.id.addicon);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddmailActivity.class);
                startActivity(intent);
            }
        });
        lv = (ScrollView) findViewById(R.id.maillist);
        refresh();
    }
    public void refresh(){
        lv.removeAllViews();
        lv.post(new Runnable() {
            @Override
            public void run() {
                List<Maillist> ml = mld.getMaillist();
               show(ml);
            }
        });
    }
    public void show(List<Maillist> ml){
        LinearLayout l = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        l.setLayoutParams(layoutParams);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(20,20,20,20);
        for(Maillist t: ml) {
            int id = t.getId();
            String name = t.getName();
            TextView tv = new TextView(getApplicationContext());
            tv.setText(name);
            tv.setId(id);
            tv.setTextSize(30);
            layoutParams.setMargins(0,5,0,5);
            tv.setLayoutParams(layoutParams);
            tv.setPadding(20,20,20,20);
            tv.setBackgroundColor(Color.parseColor("#E8E8E8"));
            tv.setGravity(Gravity.LEFT);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),EditActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("id",view.getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showNormalDialog(String.valueOf(view.getId()));
                    return false;
                }
            });
            l.addView(tv);
        }
        lv.addView(l);
    }
//    public List<Maillist> getMaillist(){
//        MaillistDao mld = new MaillistDaoImp(getApplicationContext());
//        List<Maillist> l =  mld.getMaillist();
//        for(Maillist t :l){
//            System.out.println(t.toString());
//        }
//        return l;
//    }
    private void showNormalDialog(final String id){
        final AlertDialog.Builder deleteplanDialog =
                new AlertDialog.Builder(MainActivity.this);
        deleteplanDialog.setMessage("是否删除该联系人？");
        deleteplanDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mld.DelMail(id)){
                            refresh();
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_LONG).show();
                        }else{
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(),"删除失败",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        deleteplanDialog.setNegativeButton("否",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        deleteplanDialog.show();
    }
}
