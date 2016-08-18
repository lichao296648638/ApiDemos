package com.example.animtest.view;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.animtest.R;

public class ViewAnim extends Activity {

    TextView tv_viewAnimXML;
    TextView tv_viewAnimCode;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_view_anim);

        tv_viewAnimXML = (TextView) findViewById(R.id.tv_viewAnimXML);
        tv_viewAnimCode = (TextView) findViewById(R.id.tv_viewAnimCode);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.rolling);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv_viewAnimXML.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv_viewAnimXML.startAnimation(anim);

        Animation roateAnim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        roateAnim.setDuration(700);
        roateAnim.setInterpolator(new AccelerateInterpolator());

        Animation transAnim = new TranslateAnimation(0, 400, 0, 400);
        transAnim.setStartOffset(700);
        transAnim.setDuration(1000);
        transAnim.setInterpolator(new AccelerateInterpolator());

        Animation scalAnim = new ScaleAnimation(1f, 2f, 1f, 2f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        scalAnim.setStartOffset(700);
        scalAnim.setDuration(1000);
        scalAnim.setInterpolator(new AccelerateInterpolator());

        Animation alphaAnim = new AlphaAnimation(1.0f, 0.0f);
        alphaAnim.setStartOffset(700);
        alphaAnim.setDuration(1000);
        alphaAnim.setInterpolator(new AccelerateInterpolator());

        final AnimationSet allSet = new AnimationSet(false);
        AnimationSet childSet = new AnimationSet(false);
        childSet.addAnimation(transAnim);
        childSet.addAnimation(scalAnim);
        childSet.addAnimation(alphaAnim);

        allSet.addAnimation(roateAnim);
        allSet.addAnimation(childSet);
        allSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv_viewAnimCode.startAnimation(animation);
//                allSet.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv_viewAnimCode.startAnimation(allSet);
//        tv_viewAnimCode.setAnimation(allSet);
//        allSet.start();
    }
}
