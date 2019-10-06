package com.gagan.school.library.view;

/**
 * Created by Gagan S Patil on 6/10/19.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gagan.school.R;


public class CustomFontLoaderTextView extends TextView {
    private static boolean isLoadCalled = true;
    private boolean isLoading = false;
    private int width, height, x, y, rX, rY, rWidth, rHeight;
    private Paint loadingPaint;
    private Paint ripplePaint;
    private float minWidth = -1;
    private String textVal = null;
    private int[] arrays = new int[]{android.R.attr.text};
    private LinearLayout.LayoutParams originalLinearParams;
    private RelativeLayout.LayoutParams originalRelativeParams;

    public CustomFontLoaderTextView(Context context) {
        super(context);
        initCustomLoader(context, null, 0);
    }

    public CustomFontLoaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCustomLoader(context, attrs, 0);
    }

    public CustomFontLoaderTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initCustomLoader(context, attrs, defStyle);
    }

    private void initCustomLoader(final Context context, AttributeSet attrs, int defStyle) {
        TypedArray a1 = context.obtainStyledAttributes(
                attrs,
                R.styleable.min_loading_size,
                0, 0);
        minWidth = a1.getDimension(0, -1);
        TypedArray a2 = context.obtainStyledAttributes(
                attrs,
                this.arrays,
                0, 0);
        textVal = a2.getString(0);
        loadingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //loadingPaint.setColor(context.getColor(R.color.loading_color));
        loadingPaint.setColor(Color.parseColor("#F2F2F2"));
        ripplePaint = new Paint();
        //ripplePaint.setColor(context.getColor(R.color.ripple_color));
        ripplePaint.setColor(Color.parseColor("#F6F7F9"));
        startLoading();
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stopLoading();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isLoading) {
            super.onDraw(canvas);
        } else {
            canvas.drawRect((float) x, (float) y, (float)x + width, (float) y + height, loadingPaint);
            canvas.drawRect((float)rX, rY, (float) rX + rWidth, (float) rY + rHeight, ripplePaint);
            rX = (rX < width) ? rX + 5 : x - rWidth;
            post(rippleRunner);
        }
    }

    private Runnable rippleRunner = () -> invalidate();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        x = 0;
        y = 0;
        rWidth = width - 5;
        rHeight = height;
    }

    public void startLoading() {
        if(!isLoadCalled)
            return;
        isLoading = true;
        rX = x - rWidth;
        rY = y;
        invalidate();
    }

    public void stopLoading() {
        if(!isLoadCalled)
            return;
        isLoading = false;
        invalidate();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public static boolean isIsLoadCalled() {
        return isLoadCalled;
    }

    public static void setIsLoadCalled(boolean isLoadCalled) {
        CustomFontLoaderTextView.isLoadCalled = isLoadCalled;
    }

    private void makeText(String s) {

        Log.w("CustomText", s);
    }
}