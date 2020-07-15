package com.huanozong.sale.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import com.huanozong.sale.R;

public class PopView extends View {


    //圆角半径
    float ridus;
    //背景颜色
    int  popu_bg;
    //画笔
    Paint paint;

    public PopView(Context context) {
        this(context,null);
    }
    public PopView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }
    public PopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PopView, 0, 0);
        ridus = a.getDimension(R.styleable.PopView_ridus, 5);
        popu_bg = a.getColor(R.styleable.PopView_popu_bg, 0xFF4081);
        a.recycle();
        //初始化画笔
        paint = new Paint();
        paint.setColor(popu_bg);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置填充
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        //设置防抖动
        paint.setDither(true);
    }

    int widthSize;
    int heightSize;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得当前view的宽高限制的类型
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获得view的默认尺寸
        widthSize= MeasureSpec.getSize(widthMeasureSpec);
        heightSize= MeasureSpec.getSize(heightMeasureSpec);
        //判断view的mode类型
        //这种是 warp_parent 类型 就是view的宽高不确定，所以我们要给view 赋值。实在 dimen.xml 里面写的
        if (widthMode==MeasureSpec.AT_MOST&&heightMode==MeasureSpec.AT_MOST){
            widthSize = 100;
            heightSize = 200;
        }
        //最后把宽，高设置到view中
        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(popu_bg);
        //画矩形
        RectF rectF = new RectF(0,0,widthSize,heightSize-20);
        canvas.drawRoundRect(rectF,ridus,ridus,paint);
        //画三角形（这里是基于path路径的绘制）
        Path path = new Path();
        path.moveTo((widthSize/2)-50, heightSize-20);
        path.lineTo(widthSize / 2, heightSize);
        path.lineTo((widthSize/2)+50, heightSize-20);
        path.close();
        canvas.drawPath(path, paint);

    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }
}
