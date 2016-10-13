package com.example.classtest.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.classtest.R;

/**
 * Created by lichao on 16/10/10.
 * FragmentA
 */

public class FragmentA extends Fragment implements View.OnClickListener{
    /**
     * 碎片中按钮点击的回调接口
     */
    interface OnFragButtonListener{
        /**
         * 当Fragment中的按钮被点击时调用
         */
        void OnFragClick(String data);
    }

    /**
     * 碎片中解决问题回调接口
     */
    interface OnQuestionListener{
        /**
         * 当Fragment中的问题解决完毕时被调用
         * @param result 计算完毕后的结果
         */
        void OnQuestionFinish(int result);
    }

    //本Fragment中的自定义按钮点击监听者
    OnFragButtonListener mOnFragButtonListener;

    //本Fragment中的自定义解决问题监听者
    OnQuestionListener mOnQuestionListener;

    //本Fragment中的某个按钮
    Button bt_a;
    //Activity中的某个按钮
    Button bt_activity;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        //装载Fragment中的组件
        bt_a = (Button) view.findViewById(R.id.bt_a);
        //设置点击监听
        bt_a.setOnClickListener(this);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

         //如果context并未实现接口,则捕获类型转换错误异常
        try {
            mOnFragButtonListener = (OnFragButtonListener) context;
            mOnQuestionListener = (OnQuestionListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //通过getActivity来找到对应的View
        bt_activity = (Button) getActivity().findViewById(R.id.bt_add_b);
        //修改Acvitiy中的View
        bt_activity.setText("已被修改");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Fragment中的Button A按钮
            case R.id.bt_a:
                //执行回调
                mOnFragButtonListener.OnFragClick("测试参数");
                break;
        }
    }

    /**
     * 解决问题的方法,加法怎么算
     * @param operation1 加数1
     * @param operation2 加数2
     */
    public void executeQuestion(int operation1, int operation2) {
        //模拟一个耗时操作(小李忙手头工作去了)
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //解决小明的问题加法怎么算
        mOnQuestionListener.OnQuestionFinish(operation1 + operation2);
    }
}
