package com.example.animtest.property;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.animtest.R;
import com.example.animtest.ShapeHolder;
import com.example.animtest.drawable.DrawableAnim;
import com.example.animtest.layouttransition.Transition;
import com.example.animtest.view.ViewAnim;

import java.util.ArrayList;
import java.util.List;

public class CodeAnim extends Activity implements View.OnClickListener {

    LinearLayout ll_container;
    Button bt_switch;
    Button bt_view;
    Button bt_drawable;
    Button bt_transition;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_codeanim);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        ll_container.addView(new MyAnimView(this));
        bt_switch = (Button) findViewById(R.id.bt_switch);
        bt_view = (Button) findViewById(R.id.bt_view);
        bt_drawable = (Button) findViewById(R.id.bt_drawable);
        bt_transition = (Button) findViewById(R.id.bt_transition);

        bt_switch.setOnClickListener(this);
        bt_view.setOnClickListener(this);
        bt_drawable.setOnClickListener(this);
        bt_transition.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_switch:
                intent = new Intent(this, XMLAnim.class);
                startActivity(intent);
                break;
            case R.id.bt_view:
                intent = new Intent(this, ViewAnim.class);
                startActivity(intent);
                break;
            case R.id.bt_drawable:
                intent = new Intent(this, DrawableAnim.class);
                startActivity(intent);
                break;
            case R.id.bt_transition:
                intent = new Intent(this, Transition.class);
                startActivity(intent);
                break;
        }
    }

    class MyAnimView extends View {

        private final int RED = 0xffff8080;
        private final int BLUE = 0xff8080ff;
        private List<ShapeHolder> balls = new ArrayList<ShapeHolder>();


        public MyAnimView(Context context) {
            super(context);
            ValueAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", RED, BLUE);
            colorAnim.setDuration(3000);
            colorAnim.setRepeatCount(ValueAnimator.INFINITE);
            colorAnim.setRepeatMode(ValueAnimator.REVERSE);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN ||
                    event.getAction() == MotionEvent.ACTION_MOVE) {
                final ShapeHolder ball = addBall(event.getX(), event.getY());
                float currY = event.getY();
                float currX = event.getX();
                float h = getHeight();
                float currWidth = ball.getWidth();
                float currHeight = ball.getHeight();
                float endY = getHeight() - 50f;
                int duration = (int) ((h - currY) / h * 500);
                ValueAnimator downAnim = ObjectAnimator.ofFloat(ball, "y", currY, endY);
                downAnim.setInterpolator(new AccelerateInterpolator());
                downAnim.setDuration(duration);

                ValueAnimator suqashAnim1 = ObjectAnimator.ofFloat(ball, "x", currX, currX - 25f);
                suqashAnim1.setInterpolator(new DecelerateInterpolator());
                suqashAnim1.setRepeatCount(1);
                suqashAnim1.setRepeatMode(ValueAnimator.REVERSE);
                suqashAnim1.setDuration(duration / 4);

                ValueAnimator suqashAnim2 = ObjectAnimator.ofFloat(ball, "width", currWidth, currWidth + 25f);
                suqashAnim2.setInterpolator(new DecelerateInterpolator());
                suqashAnim2.setRepeatCount(1);
                suqashAnim2.setRepeatMode(ValueAnimator.REVERSE);
                suqashAnim2.setDuration(duration / 4);

                ValueAnimator strechAnim1 = ObjectAnimator.ofFloat(ball, "y", endY, endY + 25f);
                strechAnim1.setInterpolator(new DecelerateInterpolator());
                strechAnim1.setRepeatCount(1);
                strechAnim1.setRepeatMode(ValueAnimator.REVERSE);
                strechAnim1.setDuration(duration / 4);

                ValueAnimator strechAnim2 = ObjectAnimator.ofFloat(ball, "height", currHeight, currHeight - 25f);
                strechAnim2.setInterpolator(new DecelerateInterpolator());
                strechAnim2.setRepeatCount(1);
                strechAnim2.setRepeatMode(ValueAnimator.REVERSE);
                strechAnim2.setDuration(duration / 4);

                ValueAnimator upAnim = ObjectAnimator.ofFloat(ball, "y", endY, currY);
                upAnim.setInterpolator(new DecelerateInterpolator());
                upAnim.setDuration(duration);

                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(ball, "alpha", 1f, 0);
                fadeAnim.setDuration(duration / 4);
                fadeAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        balls.remove(((ObjectAnimator) animation).getTarget());
                    }
                });

                AnimatorSet bouncer = new AnimatorSet();
                bouncer.play(downAnim).before(suqashAnim1);
                bouncer.play(suqashAnim1).with(suqashAnim2);
                bouncer.play(suqashAnim1).with(strechAnim1);
                bouncer.play(suqashAnim1).with(strechAnim2);
                bouncer.play(upAnim).after(suqashAnim1);
                bouncer.play(fadeAnim).after(upAnim);
                bouncer.start();
                return true;
            }

            return false;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (ShapeHolder shapeHolder : balls) {
                canvas.save();
                canvas.translate(shapeHolder.getX() - 25f, shapeHolder.getY() - 25f);
                shapeHolder.getShape().draw(canvas);
                canvas.restore();
            }
        }

        private ShapeHolder addBall(float x, float y) {
            OvalShape circle = new OvalShape();
            circle.resize(50f, 50f);

            //设置坐标
            ShapeDrawable shapeDrawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(shapeDrawable);
            shapeHolder.setX(x);
            shapeHolder.setY(y);
            //设置渐变色
            Paint paint = shapeDrawable.getPaint();
            int RED = (int) (Math.random() * 255);
            int GREEN = (int) (Math.random() * 255);
            int BLUE = (int) (Math.random() * 255);
            int color = 0xff000000 | RED << 16 | GREEN << 8 | BLUE;
            int darkColor = 0xff000000 | RED / 4 << 16 | GREEN / 4 << 8 | BLUE / 4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f, 50f, color, darkColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            shapeHolder.setPaint(paint);
            //添加小球
            balls.add(shapeHolder);
            return shapeHolder;
        }
    }
}
