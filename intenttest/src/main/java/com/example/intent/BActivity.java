package com.example.intent;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

/**
 * @author Neal 2016-09-13
 * @description 展示自定义Action的页面
 */
public class BActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }

    public void startActivityC(View view) {
        Intent mIntent = new Intent(this, CActivity.class);
        startActivity(mIntent);
    }
}
