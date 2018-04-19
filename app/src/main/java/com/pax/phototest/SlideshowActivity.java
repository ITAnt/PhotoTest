package com.pax.phototest;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class SlideshowActivity extends Activity {
    public static final int[] IMAGE_IDS = {R.raw.test1, R.raw.test2, R.raw.test3, R.raw.test4};
    private AutoSwitchImageView.MyTask mMyTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        AutoSwitchImageView autoImage = findViewById(R.id.iv_test);
        mMyTask = autoImage.setImages(IMAGE_IDS).setIsCircle(true).startTask();
        autoImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SlideshowActivity.this.finish();
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyTask != null) {
            mMyTask.cancel();
        }
    }
}
