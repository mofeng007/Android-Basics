package com.mofeng.guipro;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    List<Map<String,Object>> myLists;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myLists=new ArrayList<Map<String,Object>>();
        listView=findViewById(R.id.listView1);

        String[] titles={"旅游","唱跳","音乐","文学"};
        String[] authors={"张三","李四","王五","赵六"};
        int[] imageId={R.drawable.t1,R.drawable.t1,R.drawable.t1,R.drawable.t1};

        for (int i=0;i<titles.length;i++){
            Map<String,Object> myMap=new HashMap<String,Object>();
            myMap.put("title",titles[i]);
            myMap.put("author",authors[i]);
            myMap.put("id",imageId[i]);

            myLists.add(myMap);
        }

        int[] ids=new int[]{R.id.imageview1,R.id.textview1,R.id.textview2};
        String[] keys={"id","title","author"};
        SimpleAdapter sa=new SimpleAdapter(this,myLists,R.layout.item_view,keys,ids);
        listView.setAdapter(sa);

    }

    public void showListView(View v){
        Intent intent=new Intent(this,ListViewActivity.class);
        startActivity(intent);
    }

    public void showGridView(View v){
        Intent intent=new Intent(this,GridViewActivity.class);
        startActivity(intent);
    }
}
