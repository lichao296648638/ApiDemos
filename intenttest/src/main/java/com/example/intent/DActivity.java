package com.example.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
    }

    public void startActivityE(View view) {
        Intent mIntent = new Intent(this, EActivity.class);
        startActivity(mIntent);
    }
}
