package com.example.classtest.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

/**
 * @author Neal 2016-10-12
 * @description 用来展示布局加载器的Activity
 */
public class InflaterActivity extends BaseActivity {

    //当前线性布局
    LinearLayout ll_inflater;

    @Override
    protected int setContent() {
        return R.layout.activity_inflater;
    }

    @Override
    protected void initView() {
        ll_inflater = (LinearLayout) findViewById(R.id.ll_inflater);

//        addText1();
//        addText2();
        addText3();
    }

    @Override
    protected void setListener() {

    }

    /**
     * 把一个文本添加到当前布局,inflate只有resource参数
     */
    private void addText1() {
        //实例化加载器
        LayoutInflater inflater = LayoutInflater.from(this);
        //加载一个文本布局,由于没有父容器,则layout_text中的layout相关属性会失效
        TextView tv_data = (TextView) inflater.inflate(R.layout.layout_text, null);
        //添加到当前线性布局
        ll_inflater.addView(tv_data);
    }

    /**
     * 把一个文本添加到当前布局,inflate三个参数都有,但是attachToRoot为false
     */
    private void addText2() {
        //实例化加载器
        LayoutInflater inflater = LayoutInflater.from(this);
        //加载一个文本布局,此时attachToRoot为false,root作为resource的ViewGroup
        //但是resource不会添加到root中
        TextView tv_data = (TextView) inflater.inflate(R.layout.layout_text, ll_inflater, false);
        //手动添加文本到当前线性布局中
        ll_inflater.addView(tv_data);
    }

    /**
     * 把一个文本添加到当前布局,inflate三个参数都有,但是attachToRoot为true
     */
    private void addText3() {
        //实例化加载器
        LayoutInflater inflater = LayoutInflater.from(this);
        //加载一个文本布局,此时attachToRoot为false,root作为resource的ViewGroup
        //resource会自动添加到root中
       View view = inflater.inflate(R.layout.layout_text, ll_inflater, true);
    }
}
