package io.github.krtkush.lineartimer;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by kartikeykushwaha on 18/12/16.
 */
public class ArcProgressAnimation extends Animation {

    private LinearTimerView linearTimerView;

    private float startingAngle;
    private float endingAngle;
    private TimerListener timerListener;
    private boolean isFinished;

    /**
     * Instantiates a new Arc progress animation.
     *
     * @param linearTimerView the linear timer view
     * @param endingAngle     the ending angle
     * @param timerListener   the timer listener
     */
    public ArcProgressAnimation(LinearTimerView linearTimerView, int endingAngle,
                                TimerListener timerListener) {
        this.startingAngle = linearTimerView.getPreFillAngle();
        this.endingAngle = endingAngle;
        this.linearTimerView = linearTimerView;
        this.timerListener = timerListener;

        this.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    @Override
    public void setDuration(long durationMillis) {
        super.setDuration(durationMillis);
        if(durationMillis <= 5000L) this.setInterpolator(new AccelerateDecelerateInterpolator());
        else this.setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float finalAngle = startingAngle + ((endingAngle - startingAngle) * (interpolatedTime*1.001f));
        linearTimerView.setPreFillAngle(finalAngle);
        linearTimerView.requestLayout();

        // If interpolatedTime = 0.0 -> Animation has started.
        // If interpolatedTime = 1.0 -> Animation has completed.
        if(interpolatedTime == 1.0) {
            if(isFinished)
                timerListener.animationComplete();
            isFinished = true;
        }

    }

    /**
     * Interface to inform the implementing class about events wrt timer.
     */
    public interface TimerListener {
        /**
         * Animation complete.
         */
        void animationComplete();
    }
}
