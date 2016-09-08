package com.example.classtest.datastorage;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * @author Neal 2016-09-06
 * @description 内容提供者功能演示demo类
 */

public class ContentProviderActivity extends BaseActivity {

    //用来显示获取的数据内容
    TextView tv_data;
    //用来获取用户输入的编辑文本框
    EditText et_search;
    //执行查找的按钮
    Button bt_search;
    //显示查询数据的列表
    ListView lv_cursor;
    //简易游标适配器
    SimpleCursorAdapter mSimpleCursorAdapter;

    @Override
    protected int setContent() {
        return R.layout.activity_content_provider;
    }

    @Override
    protected void initView() {
        tv_data = (TextView) findViewById(R.id.tv_data);
        et_search = (EditText) findViewById(R.id.et_search);
        bt_search = (Button) findViewById(R.id.bt_search);
        lv_cursor = (ListView) findViewById(R.id.lv_cursor);

//        queryImage();

//        querySingleImage();

//        insertNewImage();

//        updateImage();

//        deleteImages();

        batchOperateContacts();
    }

    @Override
    protected void setListener() {

    }

    /**
     * @param view 点击的视图
     * @description 使用ContentProvider的方式查询手机中的图片信息
     */
    public void queryImage(View view) {
        //查询条件语句
        String mSlectionClause = null;
        //查询条件的参数
        String[] mSlectionArgs = {""};
        //用户输入的查询关键词
        String mSearch;
        //构造查询的投影
        String[] mProjection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME//图片显示的名字
        };
        //如若查询条件(WHERE)为空,则返回所有的列
        if (TextUtils.isEmpty(mSearch = et_search.getText().toString())) {
            //构造查询条件语句
            mSlectionClause = null;
            //构造查询条件的参数
            mSlectionArgs = null;
        } else {
            //构造查询条件语句
            mSlectionClause = MediaStore.Images.Media._ID + " = ?";
            //构造查询条件的参数
            mSlectionArgs[0] = mSearch;
        }

        //查询指定uri的ContentProvider，返回一个游标
        Cursor mCursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,//用来查询公共图片的uri（相当于一个表名）
                mProjection, //相当于COLUMNS,这里null指的是返回整张表，使用"*"可能会报错
                mSlectionClause,//相当于WHERE
                mSlectionArgs,//相当于WHERE ARGS
                null
        );

        //如若游标没有正确实例化
        if (mCursor == null) {
            //打印一条错误日志
            Log.e("lichao", "您根据ID查询的照片内容游标并没有正确实例化!");
        }
        //如若查询不到任何行
        else if (mCursor.getCount() < 1) {
            //弹出一个吐司
            Toast.makeText(this, "没有匹配的数据,请重新输入!", Toast.LENGTH_LONG).show();
        }
        //如果正确返回了数据
        else {
            //确定需要的数据的下标，减少IndexOrThrow的调用，提高效率
            int idIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int displayIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            //游标移动到第一行，否则游标的位置会在-1停留
            mCursor.moveToFirst();
            //用来存储临时拼接数据的字符串
            String temp = "";
            do {
                String id = mCursor.getString(idIndex);
                String display = mCursor.getString(displayIndex);
                temp += "id = " + id + "\n" + "display = " + display + "\n\n";
            } while (mCursor.moveToNext());

            //构建简易适配器展示数据的控件列表
            int[] mImageListItems = new int[]{
                    R.id.tv_id,
                    R.id.tv_display
            };
            //初始化简易游标适配器
            mSimpleCursorAdapter = new SimpleCursorAdapter(
                    this,                 //上下文
                    R.layout.item_cursor, //子item布局
                    mCursor,              //保存查询结果的游标
                    mProjection,          //查询的列
                    mImageListItems,      //展示查询列每行内容的控件组
                    0);

            //设置UI
            lv_cursor.setAdapter(mSimpleCursorAdapter);
            //关闭游标
//            mCursor.close();
        }
    }


    /**
     * @description 使用ContentProvider的方式查询手机中的单张图片信息
     */
    private void querySingleImage() {
        //指定查询某一行的uri
        Uri singleUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 69);

        //查询指定uri的ContentProvider，返回一个游标
        Cursor mCursor = getContentResolver().query(
                singleUri,//用来查询公共图片的uri（相当于一个表名）
                null, //相当于COLUMNS,这里null指的是返回整张表，使用"*"可能会报错
                null,//相当于WHERE
                null,//相当于WHERE ARGS
                null
        );

        //判断游标状态,不为空且有数据时开始遍历
        if (mCursor != null && mCursor.getCount() > 0) {
            //确定需要的数据的下标，减少IndexOrThrow的调用，提高效率
            int idIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int heightIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
            //游标移动到第一行，否则游标的位置会在-1停留
            mCursor.moveToFirst();
            //用来存储临时拼接数据的字符串
            String temp = "";
            do {
                String id = mCursor.getString(idIndex);
                String height = mCursor.getString(heightIndex);
                temp += "id = " + id + "\n" + "height = " + height + "\n\n";
            } while (mCursor.moveToNext());
            //关闭游标
            mCursor.close();
            //设置UI
            tv_data.setText(temp);
        }


    }

    /**
     * @description 在MediaStore.Images.Media.EXTERNAL_CONTENT_URI中插入一条新数据(一张新图)
     */
    private void insertNewImage() {
        //用来保存插入数据返回的uri
        Uri mNewUri = null;
        //一条待插入的数据
        ContentValues mContentValues = new ContentValues();
        //设置插入数据的内容
        mContentValues.put(MediaStore.Images.Media.TITLE, "Neal");
        mContentValues.put(MediaStore.Images.Media.HEIGHT, "200");
        //插入数据返回一条当前数据的uri
        mNewUri =
                getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, //插入的uri
                        mContentValues//插入的数据
                );
        //返回该条uri的_ID值
        tv_data.setText(ContentUris.parseId(mNewUri) + "");

    }

    /**
     * @description 更新相册中某张图片的信息
     */
    private void updateImage() {
        //构建选择语句
        String mSelectionClause = MediaStore.Images.Media.DISPLAY_NAME + " like ? ";
        //构建选择语句条件
        String[] mSelectionArgs = {"Neal"};
        //待更新的值
        ContentValues mContentValues = new ContentValues();
        //设置更新的值内容
        mContentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "已更改");
        //更新数据
        getContentResolver().update(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, //需要更新的uri
                mContentValues, //需要更新的值
                mSelectionClause, //选择语句
                mSelectionArgs //选择语句参数
        );
    }

    /**
     * @descrption 删除相册中的某些图片
     */
    private void deleteImages() {
        //删除的行
        int mRowsDeleted = 0;
        //构建删除的条件

        String mSelectionClause = MediaStore.Images.Media.DISPLAY_NAME + " like ? "
                + " or " + MediaStore.Images.Media.TITLE + " like ? " + " or " +
                MediaStore.Images.Media.TITLE + " like ? ";
        //构建删除条件的参数
        String[] mSelectionArgs = {"Neal", "Neal", "147%"};
        //执行删除,返回删除的行数
        mRowsDeleted = getContentResolver().delete(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, //需要删除的内容的表对应的uri
                mSelectionClause, //删除条件
                mSelectionArgs //删除条件参数
        );
        //更新UI
        tv_data.setText(mRowsDeleted + "");


    }

    /**
     * @description 批量操作通讯录数据
     */
    private void batchOperateContacts() {
        //定义一个储存了批量操作的集合
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        //添加一个插入新通讯录数据的操作
        ops.add(
                ContentProviderOperation
                        .newInsert(ContactsContract.RawContacts.CONTENT_URI)//要插入的uri
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)//ACCOUNT_TYPE列,必选
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)//ACCOUNT_NAME列,必选
                        .build()//构建

        );

        //构建选择语句
        String mSelectionClause = ContactsContract.RawContacts._ID + "=1";
        //添加一个删除某条通讯录数据的操作
        ops.add(
                ContentProviderOperation
                        .newDelete(ContactsContract.RawContacts.CONTENT_URI)//要删除的uri
                        .withSelection(mSelectionClause, null)//删除的选择语句
                        .build()//构建
        );

        //添加一个通讯录断点查询的操作
        ops.add(
                ContentProviderOperation
                        .newAssertQuery(ContactsContract.RawContacts.CONTENT_URI)//要查询的uri
                        .withSelection(mSelectionClause, null)//查询的选择语句
                        .withExpectedCount(0)
                        .build()//构建
        );

        //通过之前下下标为0的的ContentProviderOperation返回的
        // Uri中的ContactsContract.Data.RAW_CONTACT_ID作为
        // 选择条件插入数据
        //data表与raw_contacts是关联的关系
        ops.add(
                ContentProviderOperation
                        //要插入数据的uri
                        .newInsert(ContactsContract.Data.CONTENT_URI)
                        //根据批处理的第一次操作所返回的ContactsContract.Data.CONTACT_ID来进行插入数据操作
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        //DATA1代表了电话号码,给该联系人插入一条电话数据
                        .withValue(ContactsContract.Data.DATA1, "123456")
                        //在给Data表插入数据时必须制定该条数据的MIME类型
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .build()
        );


        try {
            //保存批处理的操作结果
            ContentProviderResult[] results =
                    //执行批处理操作
                    getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            //保存操作结果的临时变量
            String temp = "";
            //遍历结果
            for (int i = 0;i < results.length;i ++) {
                temp += results[i].toString() + "\n\n";
            }
            //更新UI
            tv_data.setText(temp);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }

    }

}
