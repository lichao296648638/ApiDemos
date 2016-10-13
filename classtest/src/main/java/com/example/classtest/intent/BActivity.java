package com.example.classtest.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

/**
 * @author Neal 2016-09-13
 * @description 展示自定义Action的页面
 */
public class BActivity extends BaseActivity {
    //显式数据的UI
    TextView tv_data;

    @Override
    protected int setContent() {
        return R.layout.activity_b;
    }

    @Override
    protected void initView() {
        tv_data = (TextView) findViewById(R.id.tv_data);

        getIntentData();
    }

    @Override
    protected void setListener() {

    }

    /**
     * @description 解析当前Intent中附带的数据
     */
    private void getIntentData() {
        //获得当前的Intent
        Intent mIntent = getIntent();
        //获得Intent中存取的Bundle对象
        Bundle mBundle = mIntent.getExtras();
        //解析数据
        if(mBundle != null)
        tv_data.setText(
                " Value: " + mBundle.getString("Key1") + "\n" +
                " Value: " + mBundle.getString("Key2") + "\n"
        );
        //若获得的序列化数据不为空
        if(mIntent.getParcelableExtra("data") != null){
            //取出序列化数据并且强制转换
            Data mData = mIntent.getParcelableExtra("data");
            tv_data.setText(mData.i + "");
        }

    }

    /**
     * @description 启动ActivityC
     * @param view 被点击的视图
     */
    public void startActivityC(View view) {
        //显示指定要启动的组件
        Intent mIntent = new Intent(this, CActivity.class);
        //设置目标组件不压入栈中的标志
//        mIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        //启动目标组件
        startActivityForResult(mIntent, 0);
    }

}
