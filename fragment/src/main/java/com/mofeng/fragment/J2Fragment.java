package com.mofeng.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

/**
 * @author 陌风
 * @create 2022-10-18 18:52
 **/
public class J2Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.layout_two,container,false);
        ImageView j2=(ImageView) view.findViewById(R.id.imageView2);
        j2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"热烈庆祝！第二金！",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
