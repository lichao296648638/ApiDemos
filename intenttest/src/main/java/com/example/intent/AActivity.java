package com.example.intent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;

/**
 * @author Neal 2016-09-13
 * @description 展示intent用法的demo
 */
public class AActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
    }

    /**
     * @param view 点击的视图
     * @description 通过组件名称启动某个Activity(显式)
     */
    public void startActivityByComponentName(View view) {
        //声明一个默认的intent
        Intent mIntent = new Intent();
        //一个指向系统设置页的组件名
        ComponentName mSetting = new ComponentName(
                "com.android.settings",
                "com.android.settings.Settings"
        );
        //给Intent设置要启动的组件名称(显式Intent),系统设置页
        mIntent.setComponent(mSetting);
        //启动这一组件
        startActivity(mIntent);

    }

    /**
     * @param view 点击的视图
     * @description 通过类启动某个Activity(显式)
     */
    public void startActivityBySetClass(View view) {
        //声明一个默认的intent
        Intent mIntent = new Intent();
        //给Intent设置要启动的类(本application内)
        mIntent.setClass(this, BActivity.class);
        //启动这一组件
        startActivity(mIntent);

    }

    /**
     * @param view 点击的视图
     * @description 通过类名启动某个Activity(显式)
     */
    public void startActivityBySetClassName(View view) {
        //声明一个默认的intent
        Intent mIntent = new Intent();
        //给Intent设置要启动的类(跨application),启动联系人程序
        mIntent.setClassName(
                "com.android.contacts",
                "com.android.contacts.activities.PeopleActivity");
        //启动这一组件
        startActivity(mIntent);

    }

    /**
     * @description 通过指定Action来隐式启动BActivity
     * @param view 点击的视图
     */
    public void startBActivityByAction(View view) {

        //声明一个默认的intent
        Intent mIntent = new Intent();
        //指定需要匹配的操作
        mIntent.setAction("com.example.intent.3");
//        mIntent.setAction(Intent.ACTION_VIEW);
        //启动这一组件
        if(mIntent.resolveActivity(getPackageManager()) != null)
        startActivity(mIntent);
    }

    /**
     * @description 通过指定Data和MIME来隐式启动某些组件
     * @param view 点击的视图
     */
    public void startActivityByDataAndType(View view) {
        //确定图片在设备中的位置
        File file = new File(
                //文件的目录,外部共有存储器中的图片目录
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "1850.jpg"
        );
        //如果图片存在
        if(file.exists()){
            //返回该文件的URI
            Uri mUri = Uri.fromFile(file);
            //声明一个默认的intent
            Intent mIntent = new Intent();
            //指定需要匹配的操作
            mIntent.setAction(Intent.ACTION_VIEW);
            //设置Intent的Data和MIME类型
            mIntent.setDataAndType(mUri, "image/*");
            //启动这一组件
            startActivity(mIntent);
        }

    }

    public void startActivityB(View view) {
        Intent mIntent = new Intent(this, BActivity.class);
        startActivity(mIntent);
    }

    public void startActivityByChooser(View view) {

        Intent mIntent = new Intent();
        mIntent.setAction(Intent.ACTION_VIEW);
        mIntent.setData(Uri.parse("http://www.baidu.com"));
        Intent mChooser = Intent.createChooser(mIntent, "选择浏览器");
        if (mChooser.resolveActivity(getPackageManager()) != null) {
            startActivity(mChooser);
        }

    }
}
