package com.theperkinrex.cuentas;


import android.os.SystemClock;
import android.util.Log;

import java.util.GregorianCalendar;
import java.util.Locale;

public class Expense {
    // Price is stored in cents
    public int price;
    public String name;
    public GregorianCalendar time;

    public Expense(int price, String name) {
        this.price = price;
        this.name = name;
        this.time = new GregorianCalendar(Locale.getDefault());
    }
}
