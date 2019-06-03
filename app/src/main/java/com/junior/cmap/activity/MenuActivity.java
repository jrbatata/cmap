package com.junior.cmap.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.junior.cmap.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
    }
}
