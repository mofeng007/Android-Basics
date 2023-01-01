package com.mofeng.test1;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    EditText ed_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_username=findViewById(R.id.username);
    }

    public void userLogin(View v){
        String username=ed_username.getText().toString();
        Intent intent=new Intent(MainActivity.this, LoginActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("username",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
