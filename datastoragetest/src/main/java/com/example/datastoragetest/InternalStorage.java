package com.example.datastoragetest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class InternalStorage extends Activity {
    String FILE_NAME = "hello_file";
    String str_fileContent = "hello internal storage";
    TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);
        tv_content = (TextView) findViewById(R.id.tv_fileContent);

        writeFile(str_fileContent);

        readFile(str_fileContent);

    }

    private void writeFile(String fileName) {
        try {
            FileOutputStream fop = openFileOutput(fileName, Context.MODE_PRIVATE);
            fop.write(str_fileContent.getBytes("UTF-8"));
            fop.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFile(String fileName) {
        try {
            FileInputStream fip = openFileInput(fileName);
            String temp = "";
            byte[] buffer = new byte[1024];
            while (fip.read(buffer) != -1) {
                temp += new String(buffer);
            }
            temp += "\n";
            InputStream ip = getResources().openRawResource(R.raw.rawfile);
            while (ip.read(buffer) != -1) {
                temp += new String(buffer);
            }

            fip.close();
            ip.close();

            tv_content.setText(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
