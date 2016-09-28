package com.example.otherclient;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.feicui.contacts.MyContactsContract;

/**
 * @description 通讯大全ContentProvider的客户端
 * @author Neal 2016-09-12
 */
public class MainActivity extends Activity {
    //用来显示数据的控件
    TextView tv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_data = (TextView) findViewById(R.id.tv_data);

        queryProvider();


    }

    /**
     * @description 查询通讯大全的ContentProvider
     */
    private void queryProvider() {
        //获取providerclient的Intent中的uri
        if(getIntent().getData() != null) {
            //取出Intent中的uri
            Uri phoneNumberUri = getIntent().getData();
            //与普通的ContentProvider查询数据的方式相同
            Cursor cursor = getContentResolver().query(
                    phoneNumberUri,//自定义的uri地址
                    new String[]{"*"},
                    null,
                    null,
                    null
            );
            //临时保存数据的字符
            String temp = "";
            //判断游标状态是否正常
            if (cursor != null && cursor.getCount() > 0) {
                //移动游标到第一行
                cursor.moveToFirst();
                //获得电话号码的列下标
                int i_phoneNumberIndex = cursor.getColumnIndexOrThrow("phonenumber");
                //遍历游标
                do {
                    //取到对应下标的电话号码
                    String phoneNumber = cursor.getString(
                            i_phoneNumberIndex
                    );
                    //拼接临时字符
                    temp += "PhoneNumber:" + phoneNumber + "\n" + "\n";

                } while (cursor.moveToNext());
                //更新UI
                tv_data.setText(temp);
            }
        }



    }




}
