package com.pax.phototest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by zhanzc on 2018/3/16.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_slideshow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // slide show
                startActivity(new Intent(MainActivity.this, SlideshowActivity.class));
            }
        });
    }
}
