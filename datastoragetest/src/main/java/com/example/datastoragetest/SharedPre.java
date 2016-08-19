package com.example.datastoragetest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SharedPre extends Activity {

    public static final String PREFS_NAME ="MyPrefsFile";
    boolean mSilentMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedpre);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        boolean silent = settings.getBoolean("silentMode",false);

    }

    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("silentMode", mSilentMode);

        // Commit the edits!
        editor.commit();
    }

}
