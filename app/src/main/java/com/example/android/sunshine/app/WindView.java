/*
 * Copyright (c) 2016. Libero Strategies, LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and Confidential
 */

package com.example.android.sunshine.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by pink on 10/21/2016.
 */

public class WindView extends TextView {
    private Paint mPaint = new Paint();
    private float mStartX = 800;
    private float mStartY = 100;
    private float mRadius = 50;
    private float mBand = 30;
//    private float mSpeed;
//    private String mDirection;

    public WindView(Context context) {
        super(context);
        init(null, 0);
    }

    public WindView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public WindView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4.5f);
//        mPaint.setTypeface(Typeface.MONOSPACE);
//        mPaint.setTextAlign(Paint.Align.LEFT);
//        mPaint.setTextSize(24);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(Color.BLUE);
        paint.setTextSize(22);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        // Draw north indicator.
        canvas.drawText("N", mStartX, mStartY - mRadius, paint);

        // Draw wind data.
//        canvas.drawText(Float.toString(mSpeed), mStartX, mStartY, paint);

        // Draw circle.
        canvas.drawCircle(
                mStartX,
                mStartY,
                mRadius,
                mPaint);
//                getMeasuredWidth()/2 - 10,
//                getMeasuredHeight()/2 - 10,
//                30, mPaint);
        canvas.drawCircle(
                mStartX,
                mStartY,
                mRadius + mBand,
                mPaint);

        drawWindNeedle(canvas);
        super.onDraw(canvas);
    }

    private Canvas drawWindNeedle(Canvas canvas) {
        // Init end of needle pointer.
        float stopX = mStartX;
        float stopY = mStartY;

        // Get direction value from the end of the entire formatted wind value.
        String formattedWind = getText().toString();
        String direction = formattedWind.substring(formattedWind.lastIndexOf(" ")+1, formattedWind.length());
        switch (direction) {
            case "NNE":
            case "ENE":
            case "NE":
                stopX = mStartX + mRadius;
                stopY = mStartY;
                break;
            case "E":
                stopX = mStartX + mRadius + mBand;
                stopY = mStartY;
                break;
            case "ESE":
            case "SSE":
            case "SE":
                stopX = mStartX + mRadius;
                stopY = mStartY + mRadius;
                break;
            case "S":
                stopX = mStartX;
                stopY = mStartY + mRadius + mBand;
                break;
            case "SSW":
            case "WSW":
            case "SW":
                stopX = 0;
                stopY = mStartY + mRadius;
                break;
            case "W":
                stopX = mStartX - mRadius - mBand;
                stopY = mStartY;
                break;
            case "WNW":
            case "NNW":
            case "NW":
                stopX = mStartX - mRadius;
                stopY = mStartY - mRadius;
                break;
            case "N":
                stopX = mStartX;
                stopY = mStartY - mRadius - mBand;
                break;
            default:
                stopX = mStartX;
                stopY = mStartY;
                break;
        }

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.5f);
        canvas.drawLine(mStartX, mStartY, stopX, stopY, paint);
        return canvas;
    }

}
