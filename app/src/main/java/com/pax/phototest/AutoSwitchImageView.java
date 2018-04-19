package com.pax.phototest;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.util.Random;

@SuppressLint("AppCompatCustomView")
public class AutoSwitchImageView extends ImageView {
    private int[] images;
    private int index;
    private MyTask task;
    private boolean isCircle;

    private static final long AUTO_SWITCH_TIME = 2500;
    private Random mRandom;

    public AutoSwitchImageView(Context context) {
        super(context, null);
    }

    public AutoSwitchImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public AutoSwitchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public class MyTask implements Runnable {
        private boolean isFinished;
        public MyTask() {
            isFinished = false;
        }

        @Override
        public void run() {
            if (index == images.length - 1 && !isCircle) {
                removeCallbacks(this);
                return;
            }
            switchWithAnim(this);
        }

        public void start() {
            postDelayed(this, AUTO_SWITCH_TIME);
        }

        public void cancel() {
            this.isFinished = true;
            removeCallbacks(this);
            if (mLastBitmap != null) {
                mLastBitmap.recycle();
            }
        }

        public boolean isFinished() {
            return isFinished;
        }
    }

    private Bitmap mLastBitmap;
    /**
     * 执行动画实现图片的切换
     * @param task
     */
    private void switchWithAnim(final MyTask task) {
        if (task.isFinished()) {
            return;
        }

        // 随机的中心点
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int diameter = centerX > centerY ? getHeight()/2 : getWidth()/2;

        if (mRandom == null) {
            mRandom = new Random();
        }
        int pivotX = centerX/2 + mRandom.nextInt(diameter);
        int pivotY = centerY/2 + mRandom.nextInt(diameter);
        setPivotX(pivotX);
        setPivotY(pivotY);
        animate().scaleX(1.5f).scaleY(1.5f).setDuration(2500)
                .setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // 下一张图片渐渐出现
                if (index == images.length - 1 && isCircle) {
                    index = -1;
                }
                //setImageResource(images[++index]);
                if (mLastBitmap != null) {
                    mLastBitmap.recycle();
                }
                mLastBitmap = ImageUtils.readBitMap(getContext(), images[++index]);
                setImageBitmap(mLastBitmap);
                setScaleX(1);
                setScaleY(1);
                AlphaAnimation anim = new AlphaAnimation(0, 1);
                anim.setDuration(830);
                anim.setFillAfter(true);
                startAnimation(anim);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        postDelayed(task, AUTO_SWITCH_TIME);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }

    /**
     * 设置图片数据
     * @param images
     * @return
     */
    public AutoSwitchImageView setImages(int[] images) {
        this.images = images;
        if (images != null && images.length > 0) {
            //setImageResource(images[index]);
            setImageBitmap(ImageUtils.readBitMap(getContext(), images[index]));
        }
        return this;
    }

    /**
     * 启动线程，执行任务
     */
    public MyTask startTask() {
        if (images == null || images.length == 0) {
            return null;
        }
        if (task != null) {
            removeCallbacks(task);
            task = null;
        }
        task = new MyTask();
        task.start();
        return task;
    }

    /**
     * 设置是否循环切换图片 
     * @param isCircle 
     * @return
     */
    public AutoSwitchImageView setIsCircle(boolean isCircle) {
        this.isCircle = isCircle;
        return this;
    }
}