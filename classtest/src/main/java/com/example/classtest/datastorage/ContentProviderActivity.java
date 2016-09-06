package com.example.classtest.datastorage;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

import org.w3c.dom.Text;

/**
 * @author Neal 2016-09-06
 * @description 内容提供者功能演示demo类
 */

public class ContentProviderActivity extends BaseActivity {

    //用来显示获取的数据内容
    TextView tv_data;

    @Override
    protected int setContent() {
        return R.layout.activity_content_provider;
    }

    @Override
    protected void initView() {
        tv_data = (TextView) findViewById(R.id.tv_data);

//        queryImage();

        querySingleImage();
    }

    @Override
    protected void setListener() {

    }

    /**
     * @description 使用ContentProvider的方式查询手机中的图片信息
     */
    private void queryImage() {
        //查询指定uri的ContentProvider，返回一个游标
        Cursor mCursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,//用来查询公共图片的uri（相当于一个表名）
                null, //相当于COLUMNS,这里null指的是返回整张表，使用"*"可能会报错
                null,//相当于WHERE
                null,//相当于WHERE ARGS
                null
        );

        //判断游标状态,不为空且有数据时开始遍历
        if(mCursor != null && mCursor.getCount() > 0){
            //确定需要的数据的下标，减少IndexOrThrow的调用，提高效率
            int idIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int heightIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
            //游标移动到第一行，否则游标的位置会在-1停留
            mCursor.moveToFirst();
            //用来存储临时拼接数据的字符串
            String temp = "";
            do {
                String id = mCursor.getString(idIndex);
                String height = mCursor.getString(heightIndex);
                temp += "id = " + id + "\n" + "height = " + height + "\n\n";
            }while (mCursor.moveToNext());
            //关闭游标
            mCursor.close();
            //设置UI
            tv_data.setText(temp);
        }


       }


    /**
     * @description 使用ContentProvider的方式查询手机中的单张图片信息
     */
    private void querySingleImage() {
        //指定查询某一行的uri
        Uri singleUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 69);

        //查询指定uri的ContentProvider，返回一个游标
        Cursor mCursor = getContentResolver().query(
                singleUri,//用来查询公共图片的uri（相当于一个表名）
                null, //相当于COLUMNS,这里null指的是返回整张表，使用"*"可能会报错
                null,//相当于WHERE
                null,//相当于WHERE ARGS
                null
        );

        //判断游标状态,不为空且有数据时开始遍历
        if(mCursor != null && mCursor.getCount() > 0){
            //确定需要的数据的下标，减少IndexOrThrow的调用，提高效率
            int idIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int heightIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
            //游标移动到第一行，否则游标的位置会在-1停留
            mCursor.moveToFirst();
            //用来存储临时拼接数据的字符串
            String temp = "";
            do {
                String id = mCursor.getString(idIndex);
                String height = mCursor.getString(heightIndex);
                temp += "id = " + id + "\n" + "height = " + height + "\n\n";
            }while (mCursor.moveToNext());
            //关闭游标
            mCursor.close();
            //设置UI
            tv_data.setText(temp);
        }


    }

}
