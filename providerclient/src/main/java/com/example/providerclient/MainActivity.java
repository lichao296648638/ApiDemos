package com.example.providerclient;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView tv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_data = (TextView) findViewById(R.id.tv_data);
        insertProvider();
        deleteProvider();
        updateProvider();
        queryProvider();

    }

    private void queryProvider() {

        Cursor cursor = getContentResolver().query(
                Uri.parse("content://com.feicui.contacts/PhoneType"),
                null,
                null,
                null,
                null
        );
        String temp = "";
        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            int i_typeNameIndex = cursor.getColumnIndexOrThrow(TypeEntry.COLUMMNS_NAME_TYPE);
            do {
                String typeName = cursor.getString(
                        i_typeNameIndex
                );

                temp += "TypeName:" + typeName + "\n" + "\n";

            } while (cursor.moveToNext());
            tv_data.setText(temp);
        }


    }

    private void insertProvider() {
        ContentValues mContentValues = new ContentValues();
        mContentValues.put(TypeEntry.COLUMMNS_NAME_TYPE, "自定义类型");

        getContentResolver().insert(
                Uri.parse("content://com.feicui.contacts/PhoneType"),
                mContentValues);

    }

    private void deleteProvider() {
        String mSelection = TypeEntry._ID + "=4";

        getContentResolver().delete(
                Uri.parse("content://com.feicui.contacts/PhoneType"),
                mSelection,
                null);
    }

    private void updateProvider() {
        String mSelection = TypeEntry._ID + "=3";
        ContentValues mContentValues = new ContentValues();
        mContentValues.put(TypeEntry.COLUMMNS_NAME_TYPE, "被修改");

        getContentResolver().update(
                Uri.parse("content://com.feicui.contacts/PhoneType"),
                mContentValues,
                mSelection,
                null);

    }


}
