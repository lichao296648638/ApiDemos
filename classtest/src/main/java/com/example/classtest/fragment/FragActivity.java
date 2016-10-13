package com.example.classtest.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.classtest.R;

/**
 * @author Neal 2016-10-10
 * @description 展示Fragment用法的Activity
 */
public class FragActivity extends FragmentActivity implements FragmentA.OnFragButtonListener,
FragmentA.OnQuestionListener{

    //碎片管理器
    FragmentManager mFm;

    //碎片事务
    FragmentTransaction mFt;

    //碎片A(小李)
    FragmentA mFragmentA;

    //碎片C
    FragmentC fragmentC;

    //碎片B
    FragmentB fragmentB;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag);

        //实例化碎片管理器
        mFm = getSupportFragmentManager();
        //实现一个监听返回栈变化的侦听器
        mFm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Toast.makeText(FragActivity.this, "返回栈变化!", Toast.LENGTH_SHORT).show();
            }
        });
        addFragmentA();
    }

    /**
     * @description 向碎片管理器中添加碎片B
     */
    private void addFragmentB() {
        //实例化碎片B
        FragmentB fragmentB = new FragmentB();
        //实例化一个碎片事务
        mFt = mFm.beginTransaction();
        //执行一个添加碎片的事务
        mFt.add(R.id.fl_container_b, fragmentB, "tagB");
        //设置一个过渡动画
        mFt.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //将此事务添加到返回栈中
        mFt.addToBackStack(null);
        //提交事务
        mFt.commit();
    }

    /**
     * @description 向碎片管理器中添加碎片A
     */
    private void addFragmentA() {
        //实例化碎片A
        if(mFragmentA == null){
            mFragmentA = new FragmentA();
        }
        //实例化一个碎片事务
        mFt = mFm.beginTransaction();
        //执行一个添加碎片的事务
        mFt.add(R.id.fl_container_a, mFragmentA, "tagA");
        //设置一个过渡动画
        mFt.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //将此事务添加到返回栈中
        mFt.addToBackStack(null);
        //提交事务
        mFt.commit();
    }


    /**
     * @description 向碎片管理器中添加碎片C
     */
    private void addFragmentC() {
        //实例化碎片C
        if (fragmentC == null)
            fragmentC = new FragmentC();
        //实例化一个碎片事务
        mFt = mFm.beginTransaction();
        //执行一个添加碎片的事务
        mFt.add(R.id.fl_container_b, fragmentC, "tagC");
        //将此事务添加到返回栈中
        mFt.addToBackStack(null);
        //提交事务
        mFt.commit();
    }

    /**
     * @description 替换目标容器的碎片至B
     */
    private void replaceFragmentB() {
        //实例化碎片B
        if (fragmentB == null)
            fragmentB = new FragmentB();
        //实例化一个碎片事务
        mFt = mFm.beginTransaction();
        //执行一个替换碎片的事务
        mFt.replace(R.id.fl_container_b, fragmentB);
        //将此事务添加到返回栈中
        mFt.addToBackStack(null);
        //提交事务
        mFt.commit();
    }

    /**
     * @description 替换目标容器的碎片至C
     */
    private void replaceFragmentC() {
        //实例化碎片C
        if (fragmentC == null)
            fragmentC = new FragmentC();
        //实例化一个碎片事务
        mFt = mFm.beginTransaction();
        //执行一个替换碎片的事务
        mFt.replace(R.id.fl_container_b, fragmentC);
        //将此事务添加到返回栈中
        mFt.addToBackStack(null);
        //提交事务
        mFt.commit();
    }


    /**
     * @description 显示碎片C
     */
    private void showFragmentC() {
        //判断是否需要显示,为空说明从未添加,进行添加
        if (fragmentC == null) {
            //实例化fragmentC
            fragmentC = new FragmentC();
            //实例化一个碎片事务
            mFt = mFm.beginTransaction();
            //执行一个添加碎片的事务
            mFt.add(R.id.fl_container_b, fragmentC);
            //将此事务添加到返回栈中
            mFt.addToBackStack(null);
            //提交事务
            mFt.commit();
        }
        //不为空说明曾经添加到过碎片管理器中,只需展示即可
        else {
            //判断当前Fragment是否隐藏
            if (!fragmentC.isHidden())
                return;
            //实例化一个碎片事务
            mFt = mFm.beginTransaction();
            //执行一个显示碎片的事务
            mFt.show(fragmentC);
            //将此事务添加到返回栈中
            mFt.addToBackStack(null);
            //提交事务
            mFt.commitNowAllowingStateLoss();
        }
    }

    /**
     * @description 隐藏碎片C
     */
    private void hideFragmentC() {
        //判断是否需要隐藏,为空说明从未添加,已隐藏
        if (fragmentC == null) {
            return;
        } else {
            //判断当前Fragment是否隐藏
            if (fragmentC.isHidden())
                return;
            //实例化一个碎片事务
            mFt = mFm.beginTransaction();
            //执行一个隐藏碎片的事务
            mFt.hide(fragmentC);
            //将此事务添加到返回栈中
            mFt.addToBackStack(null);
            //提交事务
            mFt.commit();
        }
    }


    /**
     * @param view
     * @description 控件点击监听
     */
    public void click(View view) {
        switch (view.getId()) {
            //Remove B 按钮
            case R.id.bt_remove_b:
                //找到某个Fragement
                Fragment fragmentB = mFm.findFragmentByTag("tagB");
                //防止重复删除
                if (fragmentB != null) {
                    //开启一个碎片事务
                    mFt = mFm.beginTransaction();
                    //移除对应碎片
                    mFt.remove(fragmentB);
                    //设置一个过渡动画
                    mFt.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    //将此事务添加到返回栈中
                    mFt.addToBackStack(null);
                    //提交事务
                    mFt.commit();
                }
                break;
            //Add B 按钮
            case R.id.bt_add_b:
                addFragmentB();
                break;
            //Add C 按钮
            case R.id.bt_add_c:
                addFragmentC();
                break;
            //Replace B 按钮
            case R.id.bt_replace_b:
                replaceFragmentB();
                break;
            //Replace C 按钮
            case R.id.bt_replace_c:
                replaceFragmentC();
                break;
            //Hide C 按钮
            case R.id.bt_hide_c:
                hideFragmentC();
                break;
            //Show C 按钮
            case R.id.bt_show_c:
                showFragmentC();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        addFragmentB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        askQuestion();
    }

    @Override
    public void OnFragClick(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnQuestionFinish(final int result) {
        //由于该任务是在子线程中返回,不能直接操作UI,需要返回主线程
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FragActivity.this, "结果" + result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 模拟一个小明问小李问题的异步操作
     */
    private void askQuestion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mFragmentA.executeQuestion(1, 1);
            }
        }).start();
        //问题问完了,可以去玩了
        Toast.makeText(this, "问题问完了,可以去玩了", Toast.LENGTH_SHORT).show();
    }

}
