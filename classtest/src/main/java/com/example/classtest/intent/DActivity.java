package com.example.classtest.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

public class DActivity extends BaseActivity {


    @Override
    protected int setContent() {
        return R.layout.activity_d;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    /**
     * @description 启动ActivityE
     * @param view 被点击的视图
     */
    public void startActivityE(View view) {

        //显示指定要启动的组件
        Intent mIntent = new Intent(this, EActivity.class);
        //设定启动标记,该标记表示,存在跟目标Activity相同taskAffinity的栈,则压入,否则与默认效果相同
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        //设定启动标记,该标记表示,目标栈顶有相同的Activity不重新实例化该Activity
        mIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //指定要压入的任务栈
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //启动目标组件
        startActivity(mIntent);
    }
}
