package com.mofeng.fragment;

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
      //  resetBtnColor(b1);
        switchFragement(new J1Fragment());
        setListener();
    }


    public void getComponents(){
        b1=(Button) findViewById(R.id.bt1);
        b2=(Button) findViewById(R.id.bt2);
        b3=(Button) findViewById(R.id.bt3);
        b4=(Button) findViewById(R.id.bt4);
        b5=(Button) findViewById(R.id.bt5);
    }

    public void switchFragement(Fragment fragment){
        FragmentManager manager=this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=manager.beginTransaction();
        fragmentTransaction.replace(R.id.fContainer,fragment);
        fragmentTransaction.commit();
    }

   /* public void resetBtnColor(Button b){
        b1.setTextColor(Color.WHITE);
        b2.setTextColor(Color.BLUE);
        b3.setTextColor(Color.GREEN);
        b4.setTextColor(Color.YELLOW);
        b5.setTextColor(Color.RED);
    }*/

    private void setListener() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // resetBtnColor(b1);
                switchFragement(new J1Fragment());
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // resetBtnColor(b2);
                switchFragement(new J2Fragment());
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // resetBtnColor(b3);
                switchFragement(new J3Fragment());
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // resetBtnColor(b4);
                switchFragement(new J4Fragment());
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // resetBtnColor(b5);
                switchFragement(new J5Fragment());
            }
        });
    }
}
