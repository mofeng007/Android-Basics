package com.viewpagertext.views;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.animation.LinearInterpolator;

/**
 * name: 小龙虾
 * time: 2019/04/19
 * Type: 动画实现的效果
 */

public class Lead {
    private AnimatorSet animatorSet;
    private Animator.AnimatorListener animatorListener;
    private int mDuration;

    public Lead(int duration) {
        mDuration = duration;
    }


    public void start(final LeadTextView textView) {

        final Runnable animate = new Runnable() {
            @Override
            public void run() {

                textView.setSinking(true);

                // horizontal animation. 200 = wave.png width
                ObjectAnimator maskXAnimator = ObjectAnimator.ofFloat(textView, "maskX", 0, 200);
                maskXAnimator.setRepeatCount(ValueAnimator.INFINITE);
                maskXAnimator.setDuration(1000);
                maskXAnimator.setStartDelay(0);

                int h = textView.getHeight();

                // vertical animation
                // maskY = 0 -> wave vertically centered
                // repeat mode REVERSE to go back and forth
                ObjectAnimator maskYAnimator = ObjectAnimator.ofFloat(textView, "maskY", h / 2, -h / 2);
                maskYAnimator.setRepeatCount(ValueAnimator.INFINITE);
                maskYAnimator.setRepeatMode(ValueAnimator.REVERSE);
                maskYAnimator.setDuration(2850);
                maskYAnimator.setStartDelay(0);

                // now play both animations together
                animatorSet = new AnimatorSet();
                animatorSet.playTogether(maskXAnimator, maskYAnimator);
                animatorSet.setInterpolator(new LinearInterpolator());
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        textView.setSinking(false);

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            textView.postInvalidate();
                        } else {
                            textView.postInvalidateOnAnimation();
                        }

                        animatorSet = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });


                if (animatorListener != null) {
                    animatorSet.addListener(animatorListener);
                }

                animatorSet.start();
            }
        };

        if (!textView.isSetUp()) {
            textView.setAnimationSetupCallback(new LeadTextView.AnimationSetupCallback() {
                @Override
                public void onSetupAnimation(final LeadTextView target) {
                    animate.run();
                }
            });
        } else {
            animate.run();
        }
    }

    public void cancel() {
        if (animatorSet != null) {
            animatorSet.cancel();
        }
    }
}
