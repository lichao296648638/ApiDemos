package com.example.classtest.intent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.example.classtest.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Neal 2016-09-13
 * @description 展示intent用法的demo
 */
public class AActivity extends Activity {

    //两组自定义Action
    private final String NEWACTION = "com.feicui.lichao.NEWACTION";
    private final String OLDACTION = "com.feicui.lichao.OLDACTION";

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
        //给Intent设置要启动的类(跨application)
        mIntent.setClassName(
                "com.example.intent",
                "com.example.intent.EActivity");
        //启动这一组件
        startActivity(mIntent);

    }

    /**
     * @param view 点击的视图
     * @description 通过指定Action来隐式启动BActivity
     */
    public void startBActivityByAction(View view) {

        //声明一个默认的intent
        Intent mIntent = new Intent();
        //指定需要匹配的操作
//        mIntent.setAction("com.example.intent.3");
        mIntent.setAction(Intent.ACTION_SEND);
        //指定mime类型
        mIntent.setType("text/plain");
        //放入额外信息
        mIntent.putExtra(Intent.EXTRA_EMAIL, "296648638@qq.com");
        //启动这一组件
        if (mIntent.resolveActivity(getPackageManager()) != null)
            startActivity(mIntent);
    }

    /**
     * @param view 点击的视图
     * @description 通过指定Data和MIME来隐式启动某些组件
     */
    public void startActivityByDataAndType(View view) {
        //确定图片在设备中的位置
        File file = new File(
                //文件的目录,外部共有存储器中的图片目录
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "1850.jpg"
        );
        //如果图片存在
        if (file.exists()) {
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

    /**
     * @param view
     * @description 启动ActivityB, 并传递附加信息
     */
    public void startActivityB(View view) {
        //显式指定一个组件
        Intent mIntent = new Intent(this, BActivity.class);
        //需传递的信息
        Bundle mBundle = new Bundle();
        mBundle.putString("Key1", "键1的值");
        mBundle.putString("Key2", "键2的值");
        //将信息组放入Intent中
        mIntent.putExtras(mBundle);
        //启动目标组件
        startActivity(mIntent);
    }

    /**
     * @param view 被点击的视图
     * @description 检查所有应用中有无相匹配的Activity
     */
    public void checkAcitivty(View view) {
        //创建一个隐式的Intent
        Intent mIntent = new Intent();
        //设置其动作
        mIntent.setAction(Intent.ACTION_SEND);
        //设置数据类型
        mIntent.setType("text/plain");
        //创建意图选择器
        Intent mChooser = Intent.createChooser(mIntent, "选择应用");
        //判断所有系统组件中有无与其相匹配的组件
        if (mIntent.resolveActivity(getPackageManager()) != null) {
            //存在该组件则开启
            startActivity(mChooser);
        } else {
            //不存在弹出一个吐司
            Toast.makeText(this, "未找到匹配组件", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * @param view 可点击的视图
     * @description 多重过滤器匹配
     */

    public void multipleFilter(View view) {
        //两组可过滤的Intent
        Intent newIntent = new Intent(NEWACTION);
        Intent oldIntent = new Intent(OLDACTION);
        //启动之一检查是否可过滤
        startActivity(oldIntent);
    }


    /**
     * @param view 被点击的视图
     * @description 通过匹配某个/些intent过滤器来启动组件
     */
    public void startActivityByFilter(View view) {
        //指定一个隐式的Intent
        Intent mIntent = new Intent();
        //设置动作
//        mIntent.setAction(Intent.ACTION_VIEW);
        //设置数据类型
        mIntent.setType("image/*");
        //检查并开启组件
        Toast.makeText(this, mIntent.getAction(), Toast.LENGTH_LONG).show();
        if (mIntent.resolveActivity(getPackageManager()) != null)
            startActivity(mIntent);
    }

    /**
     * @param view 点击的视图
     * @description 测试Intent过滤方法
     */
    public void startIntentTestActivity(View view) {
        /*
         * 1.指定的Intent中的Action或Category,只要某个过
         * 滤器中存在相应的内容(过滤器中的元素可以超出,但不可缺少)
         * 则会匹配成功,但是不可单独使用Category进行匹配,必须与Action结合
         *
         * 2.Intent可以使用单独的Data来进行匹配,使用该Intent时,实际上默认添加了类似
         * VIEW和EDIT之类的操作
         *
         * 3.若Data中只指定了MIME类型,则默认的Uri会匹配Content和File架构,若显式指定
         * uri,则必须匹配该uri
         *
         * 4.隐式Intent过滤器中必须有Action,否则不会正确匹配
         */

//        //Intent中含有目标过滤器中的某个对应Action,则会成功匹配
//        //指定一个隐式的Intent
//        Intent mIntent = new Intent(Intent.ACTION_VIEW);
//        //启动对应组件
//        startActivity(mIntent);

//        //Intent中含有目标过滤器中的某个对应Category,则会成功匹配
//        //指定一个隐式的Intent
//        Intent mIntent = new Intent();
//        //指定它的操作
//        mIntent.setAction("dddd");
//        //指定它的类别
//        mIntent.addCategory("com.feicui.lichao.MYCATEGORY");
//        mIntent.setDataAndType(Uri.parse("content://fsdfdsf"), "video/*");
//        //启动对应组件
//        startActivity(mIntent);


//        //指定一个隐式的Intent
//        Intent mIntent = new Intent();
//        //指定它的数据类别
//        mIntent.setDataAndType(Uri.parse("content://fsdfdsf"), "video/*");
//        //启动对应组件
//        startActivity(mIntent);
//
//        //检查对应的Acitivity
//        List<ResolveInfo> list =
//                getPackageManager().
//                        queryIntentActivities(
//                                mIntent,
//                                PackageManager.MATCH_DEFAULT_ONLY);
    }

}
