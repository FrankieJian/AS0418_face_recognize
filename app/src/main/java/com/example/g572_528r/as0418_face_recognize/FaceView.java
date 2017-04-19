package com.example.g572_528r.as0418_face_recognize;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by yls on 2017/4/18.
 */

public class FaceView extends ImageView {
    private Paint mPaint;
    private Rect rect;

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(this.rect != null){
            canvas.drawRect(rect, mPaint);
        }
    }

    public void drawFace(Rect rect){
        this.rect = rect;
        invalidate();
    }
}
