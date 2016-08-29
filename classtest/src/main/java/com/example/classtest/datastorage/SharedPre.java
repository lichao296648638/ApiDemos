package com.example.classtest.datastorage;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;
import com.example.classtest.datastorage.sqlite.MyOpenHelper;
import com.example.classtest.datastorage.sqlite.TypeEntry;

/**
 * @author Neal 2016-08-25
 * @description SharedPreferences的使用demo
 */
public class SharedPre extends BaseActivity implements View.OnClickListener {

    //Shared Preferences获取的值
    TextView tv_spvalue;
    //更改SharedPreferences值的按钮
    Button bt_writeSp;
    //跳转到InternalStorage页面的按钮
    Button bt_internal;
    //跳转到Cache file 页面的按钮
    Button bt_cache;
    //ExternalStorage  页面的按钮
    Button bt_external;

    //Shared Preferences文件名
    private final String SPFILE_NAME = "spfile";
    //SharedPrenferences存入的布尔值key
    private final String BOOL_NAME = "myBoolean";

    @Override
    protected int setContent() {
        return R.layout.activity_shared_preferences;
    }

    @Override
    protected void initView() {
        tv_spvalue = (TextView) findViewById(R.id.tv_spValue);
        bt_writeSp = (Button) findViewById(R.id.bt_writeSp);
        bt_internal = (Button) findViewById(R.id.bt_internal);
        bt_cache = (Button) findViewById(R.id.bt_cache);
        bt_external = (Button) findViewById(R.id.bt_external);
        readSPValue();

        createDatabase();

    }

    @Override
    protected void setListener() {
        bt_writeSp.setOnClickListener(this);
        bt_internal.setOnClickListener(this);
        bt_cache.setOnClickListener(this);
        bt_external.setOnClickListener(this);
    }

    /**
     * @description 向SharedPreferences中写入值
     */
    private void writeSPValue() {
        //获得Sharedpreferences的实例，参数1为文件名， 参数二为文件模式
        //默认模式为0，即MODE_PRIVATE,有对应文件则读取，无则创建
        SharedPreferences sp = getSharedPreferences(SPFILE_NAME, 0);
        //获得Sharedpreferences的编辑器
        SharedPreferences.Editor editor = sp.edit();
        //向Sharedpreferences添加一个key为"myBoolean"的布尔值
        editor.putBoolean(BOOL_NAME, true);
        //提交数据
        editor.commit();
        //读取sp中的对应boolean数据
        boolean temp = sp.getBoolean(BOOL_NAME, false);
        //设置到UI中去展示
        tv_spvalue.setText(temp + "");
    }

    /**
     * @description 读取SharedPreferences中的值
     */
    private void readSPValue() {
        //获得Sharedpreferences的实例，参数1为文件名， 参数二为文件模式
        //默认模式为0，即MODE_PRIVATE,有对应文件则读取，无则创建
        SharedPreferences sp = getSharedPreferences(SPFILE_NAME, 0);
        //读取sp中的对应boolean数据
        boolean temp = sp.getBoolean(BOOL_NAME, false);
        //设置到UI中去展示
        tv_spvalue.setText(temp + "");
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_writeSp://Wirte a true value
                writeSPValue();
                break;
            case R.id.bt_internal://Start internal storage activity
                //跳转到内部存储器demo页面
                intent = new Intent(SharedPre.this, InternalStorage.class);
                startActivity(intent);
                break;
            case R.id.bt_cache://Start cache file activity
                //跳转到缓存demo页面
                intent = new Intent(SharedPre.this, CacheFile.class);
                startActivity(intent);
                break;
            case R.id.bt_external://Start external storage activity
                //跳转到外部存储器demo页面
                intent = new Intent(SharedPre.this, ExternalStorage.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 创建数据库
     */
    private void createDatabase() {
        //获得openhleper实例
        MyOpenHelper mHelper = new MyOpenHelper(this);
        //通过帮助类实例化一个可写入的数据库对象
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //准备好待写入的值
        ContentValues contentValues = new ContentValues();
        contentValues.put(TypeEntry.COLUMMNS_NAME_TYPE, "本地电话");
        contentValues.put(TypeEntry.COLUMMNS_NAME_SUBTABLE, "localservice");
        //向数据库中插入值
        db.insert(TypeEntry.TABLE_NAME,//表名
                  null,//当插入空行或者contentvalues为空时替代的空数据内容
                  contentValues);//插入的值
    }
}
