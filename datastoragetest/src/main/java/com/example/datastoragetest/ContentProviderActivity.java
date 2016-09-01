package com.example.datastoragetest;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.UserDictionary;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ContentProviderActivity extends AppCompatActivity {

    TextView tv_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        tv_data = (TextView) findViewById(R.id.tv_data);

        queryProvider();
    }

    private void queryProvider() {

        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );
        String temp = "";
        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            int i_displaynameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME),
                    i_heightIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT),
                    i_idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            do {
                String displayname = cursor.getString(
                        i_displaynameIndex
                );
                String height = cursor.getString(
                        i_heightIndex
                );
                String id = cursor.getString(
                        i_idIndex
                );

               temp += "displayname:" + displayname + "\n" +
                        "height:" + height + "\n" +
                        "id:" + id + "\n" + "\n";

            } while (cursor.moveToNext());
            tv_data.setText(temp);
        }


    }
}
