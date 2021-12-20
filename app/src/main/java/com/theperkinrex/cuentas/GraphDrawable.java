package com.theperkinrex.cuentas;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class GraphDrawable extends Drawable {
    private static final float MARGIN = 10f;
    private final Paint redPaint;
    private final Paint greenPaint;
    private final Paint greyPaint;
    private final Paint zeroPaint;
    private final List<Expense> expenses;


    public GraphDrawable(List<Expense> expenses) {
        this.expenses = expenses;
        Paint stroke = new Paint();
        stroke.setStrokeWidth(4);
        redPaint = new Paint(stroke);
        redPaint.setARGB(255, 0x84, 0x1E, 0x1E);
        greenPaint = new Paint(stroke);
        greenPaint.setARGB(255, 0x1E, 0x84, 0x26);
        greyPaint = new Paint(stroke);
        greyPaint.setARGB(255, 125, 125, 125);
        zeroPaint = new Paint();
        zeroPaint.setStrokeWidth(2);
        zeroPaint.setARGB(200,175,175,175);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        float x = MARGIN;
//        int w = getBounds().width() - 2 * x;
        int start = 0;
        int max_y = 0;
        int min_y = 0;
        for (int i = expenses.size() - 1; i >= 0; i--) {
            start += expenses.get(i).price;
            max_y = Math.max(max_y, start);
            min_y = Math.min(min_y, start);
        }
        canvas.drawLine(0, ((float) getBounds().height() - 2 * MARGIN) - ((((float) (-min_y)) * (((float) getBounds().height() - 2 * MARGIN) / (float) (max_y - min_y)))) + MARGIN, getBounds().width(), ((float) getBounds().height() - 2 * MARGIN) - ((((float) (-min_y)) * (((float) getBounds().height() - 2 * MARGIN) / (float) (max_y - min_y)))) + MARGIN, zeroPaint);

        long min_time = expenses.get(expenses.size() - 1).time.getTime().getTime(); // Oldest
        long max_time = expenses.get(0).time.getTime().getTime() - min_time; // NOW
        start = 0;
//        Log.i("LINE_PREP", "MaxY: " + max_y + "   height: " + getBounds().height());
        for (int i = expenses.size() - 1; i >= 0; i--) {
            Expense e = expenses.get(i);
            int fut_m = start + e.price;
            float fut_x = (float) ((double) (e.time.getTime().getTime() - min_time) * ((double) (getBounds().width() - 2 * MARGIN)) / (double) max_time) + MARGIN;
            Paint p = greyPaint;
            if (e.price < 0) {
                p = redPaint;
            } else if (e.price > 0) {
                p = greenPaint;
            }
//            Log.i("LINE_START_Y", ((float) getBounds().height()) + "  " + ((((float) (start - min_y)) * (((float) getBounds().height() - 20f) / (float) (max_y - min_y)) + 10f)));
            float sy = ((float) getBounds().height() - 2 * MARGIN) - ((((float) (start - min_y)) * (((float) getBounds().height() - 2 * MARGIN) / (float) (max_y - min_y)))) + MARGIN;
            float ey = ((float) getBounds().height() - 2 * MARGIN) - ((((float) (fut_m - min_y)) * (((float) getBounds().height() - 2 * MARGIN) / (float) (max_y - min_y)))) + MARGIN;
//            Log.i("LINE", x + "," + sy + " ::: " + fut_x + "," + ey);
            canvas.drawLine(x, sy, fut_x, ey, p);
            start = fut_m;
            x = fut_x;
        }
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
