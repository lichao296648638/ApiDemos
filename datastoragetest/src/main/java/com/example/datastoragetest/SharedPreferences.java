package com.example.datastoragetest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.datastoragetest.sqlite.FeedEntry;
import com.example.datastoragetest.sqlite.FeedReaderDBHelper;

public class SharedPreferences extends Activity implements View.OnClickListener {


    public static final String PREFS_NAME = "MyPrefsFile";
    boolean mSilentMode = true;
    TextView tv_silent_mode, tv_database;
    Button bt_clear_sp, bt_internal_storage, bt_cache, bt_external_storage;
    android.content.SharedPreferences settings;
    FeedReaderDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedpre);

        mDbHelper = new FeedReaderDBHelper(getApplicationContext());

        //find views
        tv_silent_mode = (TextView) findViewById(R.id.tv_silentMode);
        bt_clear_sp = (Button) findViewById(R.id.bt_clear_sp);
        bt_internal_storage = (Button) findViewById(R.id.bt_internal_storage);
        bt_cache = (Button) findViewById(R.id.bt_cache);
        bt_external_storage = (Button) findViewById(R.id.bt_external_storage);
        tv_database = (TextView) findViewById(R.id.tv_database);
        // Restore preferences
        settings = getSharedPreferences(PREFS_NAME, 0);
        mSilentMode = settings.getBoolean("silentMode", false);

        //Set clear listener
        bt_clear_sp.setOnClickListener(this);
        bt_internal_storage.setOnClickListener(this);
        bt_cache.setOnClickListener(this);
        bt_external_storage.setOnClickListener(this);

        //Set silent mode
        tv_silent_mode.setText(mSilentMode + "");

        writeDatabase();
        readDatabase();
        deleteDatabase();
        updateDatabase();

    }

    private void writeDatabase() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FeedEntry.COLUMN_NAME_ENTRY_ID, 1);
        values.put(FeedEntry.COLUMN_NAME_TITLE, "My Title");
        db.insert(FeedEntry.TABLE_NAME,
                FeedEntry.COLUMN_NAME_TITLE,
                values);
    }


    private void readDatabase() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.query(
                FeedEntry.TABLE_NAME,
                new String[]{FeedEntry._ID},
                FeedEntry._ID + ">3",
                null,
                null,
                null,
                FeedEntry._ID
        );
        c.moveToFirst();
        long itemId = c.getLong(
                c.getColumnIndexOrThrow(FeedEntry._ID)
        );
        tv_database.setText(itemId + "");

    }

    private void deleteDatabase() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(FeedEntry.TABLE_NAME,
                FeedEntry._ID + "=8",
                null);
    }

    private void updateDatabase() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, "Update");
        db.update(FeedEntry.TABLE_NAME,
                values,
                FeedEntry._ID + "=10",
                null);
    }


    @Override
    protected void onStop() {
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        android.content.SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        android.content.SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("silentMode", true);

        // Commit the edits!
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_clear_sp:
                android.content.SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("silentMode", false);
                // Commit the edits!
                editor.commit();
                mSilentMode = settings.getBoolean("silentMode", false);
                //Set silent mode
                tv_silent_mode.setText(mSilentMode + "");
                break;
            case R.id.bt_internal_storage:
                intent = new Intent(SharedPreferences.this, InternalStorage.class);
                startActivity(intent);
                break;
            case R.id.bt_cache:
                intent = new Intent(SharedPreferences.this, CacheFile.class);
                startActivity(intent);
                break;
            case R.id.bt_external_storage:
                intent = new Intent(SharedPreferences.this, ExternalStorage.class);
                startActivity(intent);
                break;
        }
    }
}
