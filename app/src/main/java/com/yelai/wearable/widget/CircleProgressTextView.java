package com.yelai.wearable.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;

import com.yelai.wearable.R;

public class CircleProgressTextView extends AppCompatTextView {

    //1. 实现一个文字两种颜色中的 不变色的画笔、变色的画笔、当前的进度

    //绘制不变色的画笔
    private Paint emptyPaint;
    //绘制变色的画笔
    private Paint fullPaint;
    //当前的进度
    private float mCurrentProgress = 0.0f ; //0.5f：文字左边的一半是黑色，右边一半是红色

    private Paint mTextPaintGreen;

    private Paint mTextPaintWhite;

    private float strokeWidth = 2.0f;

    public CircleProgressTextView(Context context) {
        this(context,null);
    }

    public CircleProgressTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint(context, attrs) ;
    }


    /**
     * 初始化画笔
     * @param context
     * @param attrs
     */
    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressTextView);

        //此处颜色传递默认值，防止你在布局文件中没有指定颜色
        int originColor = typedArray.getColor(R.styleable.CircleProgressTextView_cptv_empty_color, getTextColors().getDefaultColor());
        int changeColor = typedArray.getColor(R.styleable.CircleProgressTextView_cptv_full_color, getTextColors().getDefaultColor());

        strokeWidth =  typedArray.getDimension(R.styleable.CircleProgressTextView_cptv_stroke_width, 2);

        mCurrentProgress =  typedArray.getDimension(R.styleable.CircleProgressTextView_cptv_progress, 0);


        emptyPaint = getPaintByColor(originColor) ;
        fullPaint = getPaintByColor(changeColor) ;

        fullPaint.setStrokeWidth(strokeWidth);

        mTextPaintGreen = getPaintByColor(changeColor) ;

        mTextPaintWhite = getPaintByColor(originColor) ;

        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/HanYiYaKuHei-1.ttf");


        mTextPaintGreen.setTypeface(typeface);
        mTextPaintWhite.setTypeface(typeface);


//        paint.setTextSize(textSize);//设置字体大小
//        paint.setTypeface(typeface);//设置字体类型



        typedArray.recycle();
    }


    /**
     * 根据颜色获取画笔
     * @param color
     * @return
     */
    private Paint getPaintByColor(int color) {
        Paint paint = new Paint() ;
        //给画笔设置颜色
        paint.setColor(color);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        //设置字体的大小 就是TextView的大小
        paint.setTextSize(getTextSize());
        return paint;
    }


    /**
     *  一个文字两种颜色
     *   利用clipRect的 API，可以裁剪 左边用一个画笔去画，右边用另一个画笔去画，不断的改变中间值
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //在这里，我们不能调用系统的super.onDraw(canvas) ，因为这里它调用的是系统TextView的画文字，我们需要自己去画，所以就注释掉它
//        super.onDraw(canvas);

        //获取中间位置
//        int middle = (int)(mCurrentProgress*getWidth()) ;
//
//
//        if (mDirection == Direction.LEFT_TO_RIGHT){  //从左到右 --> 左边是红色 右边是黑色
//            //绘制不变色
//            drawText(canvas , fullPaint , 0 , middle);
//            //绘制变色的  从中间值到整个宽度  左、上、右、下
//            drawText(canvas , emptyPaint , middle , getWidth());
//        }else{                       //从右到左
//            //绘制不变色
//            drawText(canvas , fullPaint , getWidth()-middle , getWidth());
//            //绘制变色的  从中间值到整个宽度  左、上、右、下
//            drawText(canvas , emptyPaint , 0 , getWidth()-middle);
//        }


        drawOther(canvas);

        int middle = (int)(mCurrentProgress*getHeight()) ;

//        drawTextHeight(canvas , mTextPaintGreen , 0 , middle);
//        //绘制变色的  从中间值到整个宽度  左、上、右、下
//        drawTextHeight(canvas , mTextPaintWhite , middle , getHeight());

//        drawTextHeight(canvas , mTextPaintWhite , 0 , middle);
//        //绘制变色的  从中间值到整个宽度  左、上、右、下
//        drawTextHeight(canvas , mTextPaintGreen , middle , getHeight());


//        if (mDirection == Direction.LEFT_TO_RIGHT){  //从左到右 --> 左边是红色 右边是黑色
//            //绘制不变色
//            drawTextHeight(canvas , fullPaint , 0 , middle);
//            //绘制变色的  从中间值到整个宽度  左、上、右、下
//            drawTextHeight(canvas , emptyPaint , middle , getHeight());
//        }else{                       //从右到左
//            //绘制不变色
            drawTextHeight(canvas , mTextPaintWhite , getHeight()-middle , getHeight());
            //绘制变色的  从中间值到整个宽度  左、上、右、下
            drawTextHeight(canvas , mTextPaintGreen , 0 , getHeight()-middle);
//        }




    }

    public void drawOther(Canvas canvas){

        float fix = strokeWidth/2;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(fix,fix,getWidth() - fix,getHeight() -fix,0,360,false, emptyPaint);
        }

        fullPaint.setStyle(Paint.Style.STROKE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(fix,fix,getWidth() -fix,getHeight()-fix,0,360,false, fullPaint);
        }
        fullPaint.setStyle(Paint.Style.FILL);


        float initAngle = 90;

        float delta = 180 * mCurrentProgress;

        float startAngle = (initAngle - delta)%360;

        float sweepAngle = 2 * delta;



//
//        float startAngle = 270;
//
//        float delta = 180 * 0.8f;
//
//        startAngle = (270 + delta);
//
//        float endAngle = 270 - delta;
//
//        float sweepAngle = startAngle - endAngle;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(fix,fix,getWidth()-fix,getHeight() -fix,startAngle,sweepAngle,false, fullPaint);
        }

//        canvas.drawArc(0,0,getWidth(),getHeight(),100f,200,false,fullPaint);



//        mPaint.setStyle(Paint.Style.FILL);//设置画圆弧的画笔的属性为描边(空心)，个人喜欢叫它描边，叫空心有点会引起歧义
//        mPaint.setStrokeWidth(mCircleWidth);




    }


    /**
     *  画文字
     * @param canvas
     * @param paint  画笔
     * @param start  开始位置
     * @param end  结束位置
     */
    public void drawTextHeight(Canvas canvas , Paint paint , int start  , int end){

        canvas.save(); //保存画布

        Rect rect = new Rect(0, start, getWidth(), end);
        canvas.clipRect(rect);  //裁剪区域

        /** 画文字的套路 */
        // 我们自己来画
        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        /** 下边计算方法都是套路 */
        // 获取字体的宽度
        int x = getWidth() / 2 - bounds.width() / 2;
        // 基线baseLine
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        /*public void drawText(@NonNull String text, int start, int end, float x, float y,
        @NonNull Paint paint)*/
        canvas.drawText(text, x, baseLine, paint);// 这么画其实还是只有一种颜色


        canvas.restore();  //释放画布
    }



    /**
     *  画文字
     * @param canvas
     * @param paint  画笔
     * @param start  开始位置
     * @param end  结束位置
     */
    public void drawText(Canvas canvas , Paint paint , int start  , int end){

        canvas.save(); //保存画布

        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);  //裁剪区域

        /** 画文字的套路 */
        // 我们自己来画
        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        /** 下边计算方法都是套路 */
        // 获取字体的宽度
        int x = getWidth() / 2 - bounds.width() / 2;
        // 基线baseLine
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        /*public void drawText(@NonNull String text, int start, int end, float x, float y,
        @NonNull Paint paint)*/
        canvas.drawText(text, x, baseLine, paint);// 这么画其实还是只有一种颜色


        canvas.restore();  //释放画布
    }

    public void setCurrentProgress(float currentProgress){
        this.mCurrentProgress = currentProgress ;
        invalidate();
    }


    public void setEmptyColorRes(@ColorRes int color){
        int colorValue = ContextCompat.getColor(getContext(), color);
        this.emptyPaint.setColor(colorValue);
        this.mTextPaintWhite.setColor(colorValue);

    }

    public void setFullColorRes(@ColorRes int color){
        int colorValue = ContextCompat.getColor(getContext(), color);
        this.fullPaint.setColor(colorValue);
        this.mTextPaintGreen.setColor(colorValue);
    }



}
