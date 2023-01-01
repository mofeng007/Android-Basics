package com.mofeng.guipro;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] hobbys={"体育","旅游","文学","音乐","体育","旅游","文学","音乐","阅读"};
    int imageId[]={R.drawable.j2,R.drawable.j3,R.drawable.j4,R.drawable.j5,R.drawable.j6,R.drawable.j7,R.drawable.j8,R.drawable.j9,R.drawable.j10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        List<Map<String,Object>> myList=new ArrayList<Map<String,Object>>();

        for(int i=0;i<imageId.length;i++){
            Map<String,Object> myMap=new HashMap<String, Object>();
            myMap.put("hobby",hobbys[i] );
            myMap.put("image", imageId[i]);
            myList.add(myMap);

        }
        String[] keys={"image","hobby"};
        int[] ids={R.id.imageview1,R.id.textview1};

        SimpleAdapter sa=new SimpleAdapter(this,myList,R.layout.item_view,keys,ids);

        ListView listView=(ListView) findViewById(R.id.listView2);
        listView.setAdapter(sa);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Toast.makeText(this,"你选择了"+hobbys[arg2], Toast.LENGTH_LONG).show();

    }

}