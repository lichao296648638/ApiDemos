package com.example.datastoragetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CacheFile extends Activity {

    TextView tv_fileContent;
    Button bt_deleteCache;
    String FILE_NAME = "cache file";
    String data = "hello cache file";
    String FILE_PATH = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_storage);
        //find views
        tv_fileContent = (TextView) findViewById(R.id.tv_fileContent);
        bt_deleteCache = (Button) findViewById(R.id.bt_deleteCache);

        writeCache(FILE_NAME);
        readCache(FILE_NAME);


        bt_deleteCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCache(getCacheDir().getPath() + "/" + FILE_NAME);
            }
        });
    }

    private void writeCache(String fileName) {
        File file = new File(getCacheDir(), fileName);
        try {
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(data.getBytes("UTF-8"));
            fop.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void readCache(String fileName) {
        File file = new File(getCacheDir(), fileName);
        try {
            FileInputStream fip = new FileInputStream(file);
            String temp = "";
            byte[] buffer = new byte[1024];
            while (fip.read(buffer) != -1) {
                temp += new String(buffer);
            }
            fip.close();
            FILE_PATH = file.getPath();
            temp += "\n\n";
            temp += "getFilesDir() = " + getFilesDir() + "\n\n";
            temp += "getDir() = " + getDir("cache", MODE_PRIVATE).getPath() + "\n\n";
            File cachePath = getCacheDir();
            for (File cacheFile : cachePath.listFiles())
            temp += "fileList()'s file = " + cacheFile.getPath() + "\n\n";
            tv_fileContent.setText(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void deleteCache(String path){
        File file = new File(path);
        file.delete();
    }
}
