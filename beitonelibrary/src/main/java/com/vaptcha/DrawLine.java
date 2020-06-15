package com.vaptcha;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class DrawLine extends View {
    private Paint mPaint;
    private Path mPath;
    private Paint point_paint;
    private int x;
    private int y;

    public DrawLine(Context context) {
        super(context);
    }

    public DrawLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas!=null&&mPaint!=null&&mPath!=null){
            canvas.drawPath(mPath, mPaint);
        }
        if (point_paint!=null){
            Log.e("tyl","line_onDraw");
            canvas.drawPoint(x,y,point_paint);
        }
    }

    public void Mdraw(Path mPath, Paint mPaint) {

        this.mPaint=mPaint;
        this.mPath=mPath;
        invalidate();
    }

    public void potintDraw(Path mPath, Paint mPaint){
        Log.e("tyl","potintDraw");
        this.mPaint=mPaint;
        this.mPath=mPath;
        invalidate();
    }

    //对外提供的方法，重新绘制
    public void reset(Path mPath) {
        mPath.reset();
        invalidate();
    }

}