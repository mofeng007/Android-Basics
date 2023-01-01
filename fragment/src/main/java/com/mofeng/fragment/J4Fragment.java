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
 * @create 2022-10-18 18:54
 **/
public class J4Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        final View view=inflater.inflate(R.layout.layout_four,container,false);
        ImageView j4=(ImageView) view.findViewById(R.id.imageView4);
        j4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getContext(),"热烈庆祝！第四金！",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
