package com.example.classtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

/**
 * @author Neal
 * @description Acitivy的基类
 */
public abstract class BaseActivity extends Activity {

    //Activity的销毁标记
    public final static int RESULT_EXIT = 0x100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //初始化Activity
        setContentView(setContent());
        initView();
        setListener();
        Log.i("lichao", "onCreate");
    }

    /**
     * @description 设置当前页的布局文件
     */
    protected abstract int setContent();

    /**
     * @description 加载控件
     */
    protected abstract void initView();

    /**
     * @description 设置监听
     */
    protected abstract void setListener();

    /**
     * @description 一键退出功能发起函数
     */
    protected void oneKeyExit() {
        //当某个Acitivity被销毁时给之前的Activity传一个销毁标记,用于一键退出
        setResult(RESULT_EXIT);
        //关闭当前Activity
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断上一个Activity传过来的值是否是销毁标记
        if(resultCode == RESULT_EXIT){
            oneKeyExit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lichao", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lichao", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lichao", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lichao", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lichao", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("lichao", "onRestart");

    }
}
