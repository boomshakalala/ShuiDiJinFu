package tech.shuidikeji.shuidijinfu.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import tech.shuidikeji.shuidijinfu.R;


public class LoadView extends AppCompatImageView {
    private float rotateDegrees;
    private Runnable updateViewRunnable;
    private int frameTime;
    private boolean needToUpdateView;
    private int src = R.mipmap.progress_bar;

    public LoadView(Context context) {
        this(context,null,0);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        this.setImageResource(src);
        this.updateViewRunnable = new Runnable() {
            @Override
            public void run() {
                rotateDegrees += 30.0f;
                frameTime = 100;
                rotateDegrees = rotateDegrees< 360.0f?rotateDegrees:rotateDegrees - 360.0f;
                invalidate();
                if (needToUpdateView){
                    postDelayed(this,frameTime);
                }
            }
        };
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        canvas.rotate(rotateDegrees,getWidth()/2,getHeight()/2);
        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        needToUpdateView = true;
        post(updateViewRunnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        needToUpdateView = false;
        super.onDetachedFromWindow();
    }
}
