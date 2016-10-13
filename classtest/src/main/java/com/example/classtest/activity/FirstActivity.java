package com.example.classtest.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.widget.TextView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

/**
 * @author Neal 2016-09-26
 * @description Activity传值demo
 */
public class FirstActivity extends BaseActivity {
    //测试请求码
    private final int REQUEST_CODE_LOGIN = 0;
    private final int REQUEST_CODE_REGIST = 1;
    private final int REQUEST_CODE_OTHER = 2;
    private final int REQUEST_CODE_READ = 3;


    //数据显示控件
    TextView tv_data;
    //当前Activity标志
    public static final String TAG = "SecondActivity";

    @Override
    protected int setContent() {
        return R.layout.activity_first;
    }

    @Override
    protected void initView() {
        tv_data = (TextView) findViewById(R.id.tv_data);
    }

    @Override
    protected void setListener() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判断返回码和请求码码
        switch (resultCode){

            case Activity.RESULT_OK://处理成功
                switch (requestCode){
                    case REQUEST_CODE_LOGIN://登陆
                        //更新UI
                        tv_data.setText(data.getStringExtra(SecondActivity.TAG));
                        break;
                    case REQUEST_CODE_REGIST://注册
                        //更新UI
                        tv_data.setText(data.getStringExtra(SecondActivity.TAG));
                        break;
                    case REQUEST_CODE_READ://访问联系人
                        //根据返回的data中的uri查询uri对应数据库中的Contacts.DISPLAY_NAME列
                        Cursor cursor = getContentResolver().query(
                                data.getData(),
                                new String[] {Contacts.DISPLAY_NAME},
                                null,
                                null,
                                null);
                        //遍历游标
                        if (cursor.moveToFirst()) { // True if the cursor is not empty
                            //确定Contacts.DISPLAY_NAME在行中的下标
                            int columnIndex = cursor.getColumnIndex(Contacts.DISPLAY_NAME);
                            //获得Contacts.DISPLAY_NAME列对应的值
                            String name = cursor.getString(columnIndex);
                            //更新UI
                            tv_data.setText(name);
                        }
                        break;
                }
                break;
            case Activity.RESULT_CANCELED://处理失败或者取消
                //更新UI
                tv_data.setText(data.getStringExtra(SecondActivity.TAG));
                break;
        }

    }


    /**
     * @description 模拟登陆操作
     * @param view 点击的视图
     */
    public void Login(View view) {
        //显示指定启动一个activity的intent
        Intent mIntent = new Intent(this, SecondActivity.class);
        //模拟一个登陆操作
        mIntent.putExtra(TAG, "Login");
        //启动返回结果的Activity
        startActivityForResult(mIntent, REQUEST_CODE_LOGIN);
    }


    /**
     * @description 模拟注册操作
     * @param view 点击的视图
     */
    public void Regist(View view) {
        //显示指定启动一个activity的intent
        Intent mIntent = new Intent(this, SecondActivity.class);
        //模拟一个登陆操作
        mIntent.putExtra(TAG, "Regist");
        //启动返回结果的Activity
        startActivityForResult(mIntent, REQUEST_CODE_REGIST);
    }

    /**
     * @description 模拟其他操作
     * @param view 点击的视图
     */
    public void Other(View view) {
        //显示指定启动一个activity的intent
        Intent mIntent = new Intent(this, SecondActivity.class);
        //模拟一个登陆操作
        mIntent.putExtra(TAG, "Other");
        //启动返回结果的Activity
        startActivityForResult(mIntent, REQUEST_CODE_OTHER);
    }

    /**
     * @description 访问联系人
     * @param view 点击的视图
     */
    public void readContact(View view) {
        //隐式启动选择联系人Activity
        Intent mIntent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
        //启动返回结果的Activity
        startActivityForResult(mIntent, REQUEST_CODE_READ);
    }
}
