package com.theperkinrex.cuentas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.GregorianCalendar;
import java.util.List;

public class GraphDrawable extends Drawable {
    private static final float MARGIN = 10f;
    private final Paint redPaint;
    private final Paint greenPaint;
    private final Paint greyPaint;
    private final Paint zeroPaint;
    private final List<Expense> expenses;


    public GraphDrawable(List<Expense> expenses, Context ctx) {
        this.expenses = expenses;
        Paint stroke = new Paint();
        stroke.setStrokeWidth(4);
        redPaint = new Paint(stroke);
//        redPaint.setARGB(255, 0x84, 0x1E, 0x1E);
        redPaint.setColor(ctx.getResources().getColor(R.color.expense, ctx.getTheme()));
        greenPaint = new Paint(stroke);
//        greenPaint.setARGB(255, 0x1E, 0x84, 0x26);
        greenPaint.setColor(ctx.getResources().getColor(R.color.income, ctx.getTheme()));
        greyPaint = new Paint(stroke);
        greyPaint.setARGB(255, 125, 125, 125);
        greyPaint.setTextSize(60);
        zeroPaint = new Paint();
        zeroPaint.setStrokeWidth(2);
        zeroPaint.setARGB(200, 175, 175, 175);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (expenses.size() > 1) {
//            Log.i("GRAPH", "REDRAWING");
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

        }else{
            canvas.drawText("Not enough data", 10,100, greyPaint);
        }

//        float w = getBounds().width() - 2 * MARGIN;
//        int start = 0;
//        int max_y = 10;
//        int min_y = -10;
//        long min_diff = Long.MAX_VALUE;
//        for (int i = expenses.size() - 1; i >= 0; i--) {
//            start += expenses.get(i).price;
//            max_y = Math.max(max_y, start);
//            min_y = Math.min(min_y, start);
//            if (i != 0) {
//                long diff = expenses.get(i-1).time.getTime().getTime() - expenses.get(i).time.getTime().getTime();
//                if (diff <= 1) {
//                    Log.i("Graph", "Diff: " + diff);
//                    Log.i("Graph", "[-1] Name: " + expenses.get(i-1).name + " T: " + expenses.get(i-1).time.getTime());
//                    Log.i("Graph", "[+0] Name: " + expenses.get(i).name + " T: " + expenses.get(i).time.getTime());
//                }
//                min_diff = Math.min(min_diff, diff / 1000);
//
//            }
//        }
//
//        start = 0;
//        canvas.drawLine(0, ((float) getBounds().height() - 2 * MARGIN) - ((((float) (-min_y)) * (((float) getBounds().height() - 2 * MARGIN) / (float) (max_y - min_y)))) + MARGIN, getBounds().width(), ((float) getBounds().height() - 2 * MARGIN) - ((((float) (-min_y)) * (((float) getBounds().height() - 2 * MARGIN) / (float) (max_y - min_y)))) + MARGIN, zeroPaint);
//        if (expenses.size() > 0) {
//            long min_time = expenses.get(expenses.size() - 1).time.getTime().getTime() / 1000; // Oldest
//            long max_time = GregorianCalendar.getInstance().getTime().getTime() / 1000 ; // NOW
//            long diff = max_time - min_time;
//            double step = ((double) min_diff) / ((double) diff) * ((double) w);
//            Log.i("Graph", "MinDiff: " + min_diff + " Diff: " + diff);
//            for (int i = expenses.size() - 1; i >= 0; i--) {
//                Expense e = expenses.get(i);
//                int fut = start + e.price;
//                Paint p = greyPaint;
//                if (e.price < 0) {
//                    p = redPaint;
//                } else if (e.price > 0) {
//                    p = greenPaint;
//                }
//                float sy = ((float) getBounds().height() - 2 * MARGIN) - ((((float) (start - min_y)) * (((float) getBounds().height() - 2 * MARGIN) / (float) (max_y - min_y)))) + MARGIN;
//                float ey = ((float) getBounds().height() - 2 * MARGIN) - ((((float) (fut - min_y)) * (((float) getBounds().height() - 2 * MARGIN) / (float) (max_y - min_y)))) + MARGIN;
//                double boxw = Math.max(step * 0.8, 5d);
//                double x = ((double) e.time.getTime().getTime() / 1000 - min_time) / ((double) diff) * (double) w + MARGIN;// + boxw / 2d;
//                canvas.drawRoundRect((float) (x - boxw / 2f), sy, (float) (x + boxw / 2f), ey, (float) (boxw / 4.0), (float) (boxw / 4.0), p);
//                Log.i("Graph", "start: " + sy + " x: " + x + " step: " + step + " height: " + getBounds().height() + " width: " + getBounds().width());
//                start = fut;
//            }
//        }
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
