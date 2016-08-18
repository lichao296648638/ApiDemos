package com.example.animtest.drawable;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.animtest.R;

public class DrawableAnim extends Activity {

    ImageView iv_fish_xml, iv_fish_code;
    AnimationDrawable xmlDrawable, codeDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_drawable);

        iv_fish_xml = (ImageView) findViewById(R.id.iv_fish_xml);
        iv_fish_code = (ImageView) findViewById(R.id.iv_fish_code);
        iv_fish_xml.setBackgroundResource(R.drawable.goldfish);

        xmlDrawable = (AnimationDrawable) iv_fish_xml.getBackground();

        codeDrawable = new AnimationDrawable();
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_01), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_02), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_03), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_04), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_05), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_06), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_07), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_08), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_09), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_10), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_11), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_12), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_13), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_14), 400);
        codeDrawable.addFrame(getResources().getDrawable(R.drawable.fish_15), 400);
        iv_fish_code.setBackgroundDrawable(codeDrawable);

        codeDrawable.setOneShot(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            xmlDrawable.stop();
            xmlDrawable.selectDrawable(0);
            xmlDrawable.start();

            codeDrawable.stop();
            codeDrawable.selectDrawable(0);
            codeDrawable.start();
            return true;
        }
        return super.onTouchEvent(event);
    }
}
