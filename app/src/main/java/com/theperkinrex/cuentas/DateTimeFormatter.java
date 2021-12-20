package com.theperkinrex.cuentas;

import android.content.res.Resources;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTimeFormatter {
    public static String formatTime(Resources r, Calendar t) {
        Calendar current = new GregorianCalendar(Locale.getDefault());
//        Log.d("DATEFORMATTER", current.getTime().getTime() + "");
//        Log.d("DATEFORMATTER2", t.getTime().getTime() + "");
        long currentTime = current.getTime().getTime()/1000;
        long diff = currentTime - t.getTime().getTime()/1000;
        String res;
        if (diff < 60) {
            res = r.getString(R.string.seconds_ago, diff);
        }else if (diff < 3600) {
            res = r.getString(R.string.minutes_ago, diff/60);
        }else{
            Calendar currentDay = (Calendar)  current.clone();
            currentDay.set(Calendar.HOUR_OF_DAY, 0);
            currentDay.set(Calendar.MINUTE, 0);
            currentDay.set(Calendar.SECOND, 0);
            currentDay.set(Calendar.MILLISECOND, 0);
            Calendar tDay = (Calendar) t.clone();
            tDay.set(Calendar.HOUR_OF_DAY, 0);
            tDay.set(Calendar.MINUTE, 0);
            tDay.set(Calendar.SECOND, 0);
            tDay.set(Calendar.MILLISECOND, 0);
            long dayDiff = (currentDay.getTime().getTime() - tDay.getTime().getTime()) / (1000 * 60 * 60 * 24);

            if (dayDiff == 0) {
                res = r.getString(R.string.today_at, new SimpleDateFormat("hh:mm").format(t.getTime()));
            }else if (dayDiff == 1) {
                res = r.getString(R.string.yesterday_at, new SimpleDateFormat("hh:mm").format(t.getTime()));
            }else{
                res = new SimpleDateFormat("dd/MM/yyyy").format(t.getTime());
            }
        }
        return res;
    }
}
