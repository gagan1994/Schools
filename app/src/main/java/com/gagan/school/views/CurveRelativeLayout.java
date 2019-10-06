package com.gagan.school.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Gagan S Patil on 1/10/19.
 */
public class CurveRelativeLayout extends RelativeLayout {
    public CurveRelativeLayout(Context context) {
        super(context);
    }

    public CurveRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurveRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint p = new Paint();
        RectF rectF = new RectF(50, 20, 100, 80);
        p.setColor(Color.BLACK);
        canvas.drawArc(rectF, 90, 45, true, p);
    }
}
