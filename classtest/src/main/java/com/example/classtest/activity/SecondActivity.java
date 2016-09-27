package com.example.classtest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

public class SecondActivity extends BaseActivity {
    //显示数据的控件
    TextView tv_data;
    //当前Activity标志
    public static final String TAG = "SecondActivity";

    @Override
    protected int setContent() {
        return R.layout.activity_second;
    }

    @Override
    protected void initView() {
        tv_data = (TextView) findViewById(R.id.tv_data);


        parseIntent();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //若当前动作是按键抬起
        if(event.getAction() == KeyEvent.ACTION_UP){
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                //装载返回给上一个Activity的返回值
                Intent mIntent = new Intent();
                //根据不同的操作类型执行操作
                switch (tv_data.getText().toString()) {
                    case "Login":
                        //设置返回码和返回数据
                        mIntent.putExtra(TAG, "登陆成功!");
                        setResult(Activity.RESULT_OK, mIntent);
                        break;
                    case "Regist":
                        //设置返回码和返回数据
                        mIntent.putExtra(TAG, "注册成功!");
                        setResult(Activity.RESULT_OK, mIntent);
                        break;
                    default:
                        //设置返回码和返回数据
                        mIntent.putExtra(TAG, "未知操作!");
                        setResult(Activity.RESULT_CANCELED, mIntent);
                        break;
                }
                //关闭当前页
                finish();
            }
        }
        return false;
    }

    /**
     * @description 退出当前Activity,并且返回数据给上一个Activity
     * @param view 点击的视图
     */
    public void Exit(View view) {
        //装载返回给上一个Activity的返回值
        Intent mIntent = new Intent();
        //根据不同的操作类型执行操作
        switch (tv_data.getText().toString()) {
            case "Login":
                //设置返回码和返回数据
                mIntent.putExtra(TAG, "登陆成功!");
                setResult(Activity.RESULT_OK, mIntent);
                break;
            case "Regist":
                //设置返回码和返回数据
                mIntent.putExtra(TAG, "注册成功!");
                setResult(Activity.RESULT_OK, mIntent);
                break;
            default:
                //设置返回码和返回数据
                mIntent.putExtra(TAG, "未知操作!");
                setResult(Activity.RESULT_CANCELED, mIntent);
                break;
        }
        //关闭当前页
        finish();

    }

    /**
     * @description 解析Intent的数据
     */
    public void parseIntent() {
        //获取上个页面传入的Intent
        Intent mIntent = getIntent();
        //获得操作类型
        String operation = mIntent.getStringExtra(FirstActivity.TAG);
        //根据不同的操作类型执行操作
        switch (operation) {
            case "Login":
                tv_data.setText("Login");
                break;
            case "Regist":
                tv_data.setText("Regist");
                break;
        }
    }
}
