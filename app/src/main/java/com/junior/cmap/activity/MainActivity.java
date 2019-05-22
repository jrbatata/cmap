package com.junior.cmap.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.junior.cmap.R;

public class MainActivity extends AppCompatActivity {
    private String tag = "click";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        getSupportActionBar().hide();
        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                open_window();
            }
        });
    }

    public void open_window(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
