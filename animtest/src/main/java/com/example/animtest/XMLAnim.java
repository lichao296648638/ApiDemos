package com.example.animtest;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class XMLAnim extends Activity {

    Button bt_switch;
    LinearLayout ll_container;
    ShapeHolder colorBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_xmlanim);

        bt_switch = (Button) findViewById(R.id.bt_switch);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);

        ll_container.addView(new MyAnimView(this));

    }

    class MyAnimView extends View {


        private final int GREEN = 0xff00ff00;
        private final int BLUE = 0xff0000ff;
        private List<ShapeHolder> balls = new ArrayList<ShapeHolder>();

        public MyAnimView(Context context) {
            super(context);
            ValueAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", GREEN, BLUE);
            colorAnim.setDuration(3000);
            colorAnim.setRepeatCount(ValueAnimator.INFINITE);
            colorAnim.setRepeatMode(ValueAnimator.REVERSE);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.start();


            OvalShape circle = new OvalShape();
            circle.resize(200f, 200f);
            ShapeDrawable shapeDrawable = new ShapeDrawable(circle);
            colorBall = new ShapeHolder(shapeDrawable);
            colorBall.setX(0f);
            colorBall.setY(getHeight() / 2);

            Keyframe kf1 = Keyframe.ofFloat(200f, 400f);
            Keyframe kf2 = Keyframe.ofFloat(400f, 200f);
            Keyframe kf3 = Keyframe.ofFloat(200f, 300f);
            PropertyValuesHolder widhtHolder = PropertyValuesHolder.ofKeyframe("width", kf1, kf2, kf3);

            Keyframe kf5 = Keyframe.ofFloat(200f, 400f);
            Keyframe kf6 = Keyframe.ofFloat(400f, 200f);
            Keyframe kf7 = Keyframe.ofFloat(200f, 600f);
            Keyframe kf8 = Keyframe.ofFloat(700f, 0f);
            PropertyValuesHolder heightHolder = PropertyValuesHolder.ofKeyframe("height", kf5, kf6, kf7, kf8);
//
//            Keyframe kf9 = Keyframe.ofInt(0xffff0000, 0xff00ff00);
//            Keyframe kf10 = Keyframe.ofInt(0xff00ff00, 0xff0000ff);
//            Keyframe kf11 = Keyframe.ofInt(0xff0000ff, 0xffff0000);
//            PropertyValuesHolder colorHolder = PropertyValuesHolder.ofKeyframe("color", kf9, kf10, kf11);

            ObjectAnimator colorBallAnim = ObjectAnimator.ofPropertyValuesHolder(colorBall, widhtHolder);
            colorBallAnim.setDuration(2000);
            colorBallAnim.setRepeatMode(ValueAnimator.RESTART);
            colorBallAnim.setRepeatCount(ValueAnimator.INFINITE);

            colorBallAnim.start();
        }

        @Override
        protected void onDraw(Canvas canvas) {

            for (ShapeHolder shapeHolder : balls) {
                canvas.save();
                canvas.translate(shapeHolder.getX() - 50f, shapeHolder.getY() - 50f);
                shapeHolder.getShape().draw(canvas);
                canvas.restore();
            }
            canvas.save();
            canvas.translate(colorBall.getX(), colorBall.getY());
            colorBall.getShape().draw(canvas);
            canvas.restore();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN ||
                    event.getAction() == MotionEvent.ACTION_MOVE) {

                final ShapeHolder bouncer = addBall(event.getX(), event.getY());
                AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(XMLAnim.this, R.animator.bouncer);
                anim.setTarget(bouncer);

                AnimatorSet lastSet = (AnimatorSet) anim.getChildAnimations().get(1);

                ObjectAnimator lastAnim = (ObjectAnimator) lastSet.getChildAnimations().get(1);

                lastAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        balls.remove(((ObjectAnimator) animation).getTarget());
                    }
                });

                anim.start();
                return true;
            }

            return false;
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

    public void switchActivity(View view) {
        finish();
    }
}
