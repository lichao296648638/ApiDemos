package com.example.classtest.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

public class CActivity extends BaseActivity {

    @Override
    protected int setContent() {
        return R.layout.activity_c;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    /**
     * @description 启动ActivityA
     * @param view 被点击的视图
     */
    public void startActivityA(View view) {
        //显示指定要启动的组件
        Intent mIntent = new Intent(this, AActivity.class);
        //启动目标组件
        startActivity(mIntent);
    }

    /**
     * @description 启动ActivityB
     * @param view 被点击的视图
     */
    public void startActivityB(View view) {
        //显示指定要启动的组件
        Intent mIntent = new Intent(this, BActivity.class);
        //设定启动标记,该标记表示,若栈中存在目标Activity,则启动该Acitivity并销毁在它之上的Activity
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //启动目标组件
        startActivity(mIntent);
    }

    /**
     * @description 启动ActivityD
     * @param view 被点击的视图
     */
    public void startActivityD(View view) {
        //显示指定要启动的组件
        Intent mIntent = new Intent(this, DActivity.class);
        //设定启动标记,该标记表示,存在跟目标Activity相同taskAffinity的栈,则压入,否则与默认效果相同
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //启动目标组件
        startActivityForResult(mIntent, 0);
    }

    /**
     * @description 一键退出功能
     * @param view 点击的控件
     */
    public void oneKeyExit(View view) {
        oneKeyExit();
    }

}
