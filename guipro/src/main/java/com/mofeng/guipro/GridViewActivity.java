package com.mofeng.guipro;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] apps={"QQ","微信","钉钉","淘宝","京东","抖音"};
    int imageId[]={R.drawable.j2,R.drawable.j3,R.drawable.j4,R.drawable.j5,R.drawable.j6,R.drawable.j7};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        List<Map<String,Object>> myList=new ArrayList<Map<String,Object>>();

        for(int i=0;i<imageId.length;i++){
            Map<String,Object> myMap=new HashMap<String, Object>();
            myMap.put("app",apps[i] );
            myMap.put("image", imageId[i]);
            myList.add(myMap);

        }
        String[] keys={"image","app"};
        int[] ids={R.id.imageview1,R.id.textview1};
        SimpleAdapter sa=new SimpleAdapter(this,myList,R.layout.item_view,keys,ids);
        GridView gridView=(GridView) findViewById(R.id.girdView1);
        gridView.setAdapter(sa);

        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Toast.makeText(this,"你选择了"+apps[arg2], Toast.LENGTH_LONG).show();

    }

}
