package com.example.datastoragetest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ExternalStorage extends Activity implements View.OnClickListener {
    TextView tv_state;
    Button bt_mk_album_dir, bt_mk_private_dir, bt_mk_private_sd_dir, bt_mk_cache_dir;
    String ALBUME_NAME = "Album Dirctory";
    String PRIVATE_FILE_NAME = "Private Directory";
    String CACHE_FILE_NAME = "Cache Directory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        tv_state = (TextView) findViewById(R.id.tv_state);
        bt_mk_album_dir = (Button) findViewById(R.id.bt_mk_album_dir);
        bt_mk_private_dir = (Button) findViewById(R.id.bt_mk_private_dir);
        bt_mk_private_sd_dir = (Button) findViewById(R.id.bt_mk_private_sd_dir);
        bt_mk_cache_dir = (Button) findViewById(R.id.bt_mk_cache_dir);

        bt_mk_album_dir.setOnClickListener(this);
        bt_mk_private_dir.setOnClickListener(this);
        bt_mk_private_sd_dir.setOnClickListener(this);
        bt_mk_cache_dir.setOnClickListener(this);
        tv_state.setText(
                "Writeable state is + " + externalWriteable() + "\n" +
                        "Readable state is + " + externalReadable()
        );
    }

    private boolean externalWriteable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
            return true;
        else
            return false;
    }

    private boolean externalReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
            return true;
        else
            return false;
    }

    private void getAlbumDir(String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                fileName);
        if(!file.exists())
        if (!file.mkdirs()) {
            Toast.makeText(this, "Create Directory failed!", Toast.LENGTH_LONG).show();
        }
    }

    private void getPirvateDir(String fileName){
        File file = new File(getExternalFilesDir(null), fileName);
        if(!file.exists())
        if (!file.mkdirs()) {
            Toast.makeText(this, "Create Directory failed!", Toast.LENGTH_LONG).show();
        }
    }

    private void getPirvateSDDir(String fileName){
        File file = new File(getExternalFilesDirs(null)[1], fileName);
        if(!file.exists())
            if (!file.mkdirs()) {
                Toast.makeText(this, "Create Directory failed!", Toast.LENGTH_LONG).show();
            }
    }

    private void getCacheDir(String fileName){
        File file = new File(getExternalCacheDirs()[1], fileName);
        if(!file.exists())
            if (!file.mkdirs()) {
                Toast.makeText(this, "Create Directory failed!", Toast.LENGTH_LONG).show();
            }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_mk_album_dir:
                getAlbumDir(ALBUME_NAME);
                break;
            case R.id.bt_mk_private_dir:
                getPirvateDir(PRIVATE_FILE_NAME);
                break;
            case R.id.bt_mk_private_sd_dir:
                getPirvateSDDir(PRIVATE_FILE_NAME);
                break;
            case R.id.bt_mk_cache_dir:
                getCacheDir(CACHE_FILE_NAME);
                break;
        }
    }
}
