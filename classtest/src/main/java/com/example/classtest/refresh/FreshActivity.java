package com.example.classtest.refresh;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import java.util.logging.LogRecord;

/**
 * @author Neal 2016-10-10
 * @description 展示定时刷新ListView的示例
 */
public class FreshActivity extends BaseActivity {

    //要展现的自定义列表
    ListView lv_data;

    //测试数据
    ArrayList<String> mDatas;
    //自定义适配器
    MyAdapter myAdapter;
    //用来接收发消息的Handler
    Handler myHanlder;

    @Override
    protected int setContent() {
        return R.layout.activity_fresh;
    }

    @Override
    protected void initView() {

        lv_data = (ListView) findViewById(R.id.lv_data);
         mDatas = new ArrayList<String>();
        for (int i = 0;i < 10;i ++) {
            //将int转化成String
            String mData = String.valueOf(i);
            //加入数据列表
            mDatas.add(mData);
        }

        //装载适配器
        myAdapter = new MyAdapter(this, mDatas);

        //设置适配器
        lv_data.setAdapter(myAdapter);

        //实现一个异步消息接收者
        myHanlder = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //判断消息类型
                switch (msg.what) {
                    //更新ListView UI的消息
                    case 0x001:
                        //刷新列表
                        myAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };

        //实现一个定时任务执行者
        Timer mTimer = new Timer();
        //开启定时任务
        mTimer.schedule(new MyTimerTask(), 3000, 3000);

    }

    @Override
    protected void setListener() {

    }


    /**
     * @description 自定义时间任务
     */
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            //装载测试数据
            for (int i = 0;i < 10;i ++) {
                //将int转化成String
                String mData = String.valueOf(i);
                //加入数据列表
                mDatas.add(i, mData);
            }
            //先构建一条消息
            Message mMessage = Message.obtain();
            //设置消息内容
            mMessage.what = 0x001;
            //发送消息
            myHanlder.sendMessage(mMessage);
        }
    }


}
