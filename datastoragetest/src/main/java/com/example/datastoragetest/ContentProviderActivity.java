package com.example.datastoragetest;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.UserDictionary;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ContentProviderActivity extends AppCompatActivity {

    TextView tv_data;
    EditText et_search;
    Button bt_search;
    String[] mProjection = new String[]{
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
    };

    String mSelectionClause = null;

    String[] mSelectionArgs = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        tv_data = (TextView) findViewById(R.id.tv_data);
        et_search = (EditText) findViewById(R.id.et_search);
        bt_search = (Button) findViewById(R.id.bt_search);
        queryType();
//        delete();
//        update();
//        insert();
//        queryProvider();
//        querySingleRow();

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

    private void querySingleRow() {
        Uri singleUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 32);
        Cursor cursor = getContentResolver().query(
                singleUri,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        int i_displaynameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME),
                i_heightIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT),
                i_idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

        String displayname = cursor.getString(
                i_displaynameIndex
        );
        String height = cursor.getString(
                i_heightIndex
        );
        String id = cursor.getString(
                i_idIndex
        );
        String temp = "";
        temp += "displayname:" + displayname + "\n" +
                "height:" + height + "\n" +
                "id:" + id + "\n" + "\n";
        tv_data.setText(temp);
    }

    public void search(View view) {
        String mSearchString = et_search.getText().toString();

        if (TextUtils.isEmpty(mSearchString)) {
            mSelectionClause = null;
            mSelectionArgs[0] = "";
        } else {
            mSelectionClause = MediaStore.Images.Media._ID + " = ?";
            mSelectionArgs[0] = mSearchString;
        }


        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                mProjection,
                mSelectionClause,
                mSelectionArgs,
                null);


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int i_displaynameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME),
                    i_idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

            String displayname = cursor.getString(
                    i_displaynameIndex
            );
            String id = cursor.getString(
                    i_idIndex
            );
            String temp = "";
            temp += "displayname:" + displayname + "\n" +
                    "id:" + id + "\n" + "\n";
            tv_data.setText(temp);
        }
    }

    private void insert() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media._ID, 99);
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    private void update() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.HEIGHT, "100");
        getContentResolver().update(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues,
                MediaStore.Images.Media._ID + "=36",
                null);
    }

    private void delete() {
        getContentResolver().delete(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.Media._ID + "=36",
                null);
    }

    private void queryType(){
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        tv_data.setText(cursor.getType(0) + "\n"
                + getContentResolver().getType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI));

    }
}
