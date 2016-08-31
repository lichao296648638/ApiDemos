package com.example.animtest.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.animtest.R;

public class Transition extends Activity {

    LinearLayout ll_container;
    ListView lv_transition;
    int i, j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
//        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        lv_transition = (ListView) findViewById(R.id.lv_transition);

        lv_transition.setAdapter(new TransitionAdapter(this));
//        setTransition();
    }

    public void addButton(View view) {
        i++;
        Button button = new Button(this);
        button.setText("button" + i);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        if (i > 2)
            ll_container.addView(button, 1);
        else
            ll_container.addView(button);
    }

    public void removeButton(View view) {
        if (i-- > 0) {
            ll_container.removeViewAt(0);
        }

    }

    private void setTransition() {
        LayoutTransition transition = new LayoutTransition();
        ll_container.setLayoutTransition(transition);

        ValueAnimator appear = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f);
        appear.setDuration(transition.getDuration(LayoutTransition.APPEARING));
        transition.setAnimator(LayoutTransition.APPEARING, appear);

        ValueAnimator disappear = ObjectAnimator.ofFloat(null, "rotationY", 0f, 90f, 0f);
        appear.setDuration(transition.getDuration(LayoutTransition.APPEARING));
        transition.setAnimator(LayoutTransition.DISAPPEARING, disappear);

        transition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        transition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);


        PropertyValuesHolder left = PropertyValuesHolder.ofInt("left", 0, 0);
        PropertyValuesHolder top = PropertyValuesHolder.ofInt("top", 0, 0);
        PropertyValuesHolder right = PropertyValuesHolder.ofInt("right", 0, 0);
        PropertyValuesHolder bottom = PropertyValuesHolder.ofInt("bottom", 0, 0);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 2f, 1f);
        ValueAnimator changein = ObjectAnimator.ofPropertyValuesHolder(ll_container, left, top, right, bottom, scaleX);
        changein.setDuration(transition.getDuration(LayoutTransition.CHANGE_APPEARING));
        changein.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((View) ((ObjectAnimator) animation).getTarget()).setScaleX(1f);
            }
        });
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING, changein);


        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changein);
    }

}
