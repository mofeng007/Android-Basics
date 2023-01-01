package com.mofeng.fragmentdemo;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    Button b1,b2,b3,b4,b5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getComponents();
        resetBtnColor(b1);
        switchFragment(new J1Fragment());
        setListener();
    }

    private void getComponents(){
        b1=findViewById(R.id.bt1);
        b2=findViewById(R.id.bt2);
        b3=findViewById(R.id.bt3);
        b4=findViewById(R.id.bt4);
        b5=findViewById(R.id.bt5);
    }

    private void switchFragment(Fragment fragment){
        FragmentManager manager=this.getSupportFragmentManager();
        FragmentTransaction fT=manager.beginTransaction();
        fT.replace(R.id.fContainer,fragment);
        fT.commit();
    }

    private void resetBtnColor(Button b){
        b1.setTextColor(Color.WHITE);
        b2.setTextColor(Color.RED);
        b3.setTextColor(Color.GREEN);
        b4.setTextColor(Color.BLUE);
        b5.setTextColor(Color.YELLOW);

    }

    private void setListener(){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBtnColor(b1);
                switchFragment(new J1Fragment());
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBtnColor(b2);
                switchFragment(new J2Fragment());
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBtnColor(b3);
                switchFragment(new J3Fragment());
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBtnColor(b4);
                switchFragment(new J4Fragment());
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBtnColor(b5);
                switchFragment(new J5Fragment());
            }
        });
    }
}
