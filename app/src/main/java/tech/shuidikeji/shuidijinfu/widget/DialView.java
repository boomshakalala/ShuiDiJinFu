package tech.shuidikeji.shuidijinfu.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class DialView extends View {
    //短刻度长度
    private int short_line_size = 20;
    //长刻度长度
    private int long_line_size = 35;
    //没有选中短刻度颜色
    private int unchecked_short_line_color = 0x75ffffff;
    //选中短刻度颜色
    private int checked_short_line_color = 0xffffffff;
    //短刻度宽度
    private int short_line_width = 3;
    //长短刻度相差
    private int lone_short_differ = long_line_size - short_line_size;
    //半径
    private int radius;
    //平分成几大块
    private int equally_long_num = 6;
    //每一大份平分成几小分
    private int equally_short_num = 10;
    //所有角度
    private int all_angle = 180;
    //选中的角度
    private int checked_angle = 0;
    //开始旋转角度
    private int start_change_angle = -90;
    private float one_angle = (float) all_angle / (float) (equally_long_num * equally_short_num);
    //View的宽高
    private int view_width;

    //画线的画笔
    private Paint line_paint = new Paint();
    //指针画笔
    private Paint zhizhen_paint = new Paint();
    //只是三角形绘制路径
    private Path mPath = new Path();
    //绘制字体画笔
    private Paint text_paint = new Paint();
    private int text_color = 0xffffffff;
    private float text_size = 34;


    public DialView(Context context) {
        super(context);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //抗锯齿
        line_paint.setAntiAlias(true);
        line_paint.setColor(unchecked_short_line_color);
        //宽度
        line_paint.setStrokeWidth(short_line_width);
        line_paint.setStrokeCap(Paint.Cap.ROUND);

        zhizhen_paint.setColor(0xffffffff);
        zhizhen_paint.setAntiAlias(true);
        zhizhen_paint.setStrokeCap(Paint.Cap.ROUND);

        text_paint.setColor(text_color);
        text_paint.setTextSize(sp2px(text_size));


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        radius = width_size / 2;

        //根据不同模式，计算控件高度
        if (height_mode == MeasureSpec.EXACTLY) {

        } else {
            float jiaodu = 90 - (all_angle - 180) / 2;
            int new_height = (int) (radius + Math.cos(Math.toRadians(jiaodu)) * radius);
            height_size = new_height;

            if (height_mode == MeasureSpec.AT_MOST) {
                if (new_height > height_size) {
                    height_size = height_size;
                } else {
                    height_size = new_height;
                }
            }
        }
        height_size = height_size + 4 * short_line_width;

        view_width = width_size;

        setMeasuredDimension(width_size, height_size);

        mPath.moveTo(0, -radius + lone_short_differ + 40);
        mPath.lineTo(-10, -radius + lone_short_differ + 80);
        mPath.lineTo(10, -radius + lone_short_differ + 80);
        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //是否绘制三角指示图标
        int draw_sanjiao = 0;

        canvas.save();
        canvas.translate(view_width / 2, radius);
        canvas.rotate(start_change_angle);

        //总共分成了几分（多少格）
        int change_num = (int) (all_angle / one_angle);
        //计算第几个开始没有选择
        int checked_num = (int) (checked_angle / one_angle);
        for (int i = 0; i <= change_num; i++) {

            if (i >= checked_num && draw_sanjiao == 0) {
                canvas.drawPath(mPath, zhizhen_paint);
                draw_sanjiao++;
            }

            if (checked_angle != 0) {
                if ((float) i * one_angle > checked_angle) {
                    line_paint.setColor(unchecked_short_line_color);
                } else {
                    line_paint.setColor(checked_short_line_color);
                }
            }


            if (i % 10 == 0) {
                //绘制长线
                canvas.drawLine(0, -radius, 0, -radius + long_line_size, line_paint);
            } else {
                //绘制短线
                canvas.drawLine(0, -radius + lone_short_differ,
                        0, -radius + short_line_size + lone_short_differ, line_paint);
            }
            canvas.rotate(one_angle);
        }
        canvas.restore();
    }




    /**
     * 设置刻度盘数值
     */
    public void setNum(int num) {
        if (num > all_angle) {
            num = all_angle;
        }
        checked_angle = num;
        invalidate();
    }

    /**
     * sp转px
     *
     * @return
     */
    private int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());
    }


}
