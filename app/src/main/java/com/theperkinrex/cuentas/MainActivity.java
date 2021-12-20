package com.theperkinrex.cuentas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";


    protected RecyclerView mRecyclerView;
    protected ExpensesAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<Expense> dataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.constraitLayout).setBackgroundColor(getResources().getColor(R.color.white, getTheme()));

        mRecyclerView = findViewById(R.id.expenseGainView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExpensesAdapter();
        Drawable d = mAdapter.getGraph();
        addExampleData();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        ((TextView)findViewById(R.id.sumTextView)).setText(mAdapter.getTotal() + " €");
        ((ImageView)findViewById(R.id.graphView)).setImageDrawable(d);
        ((ImageView)findViewById(R.id.graphView)).refreshDrawableState();
    }

    public void refresh(View view) {
        Log.d("REFRESH", "Refreshing");
        mAdapter.notifyDataSetChanged();
    }

    private void addExampleData() {

        Expense e = new Expense(-1, "Copa");
        e.time.set(2021, 7, 25);
        mAdapter.addExpense(e);
        mAdapter.addExpense(new Expense(10,"Something"));
        int date = 25;
        for (int i = 0; i<50; i++, date++) {
            Expense t = new Expense((int) ((Math.random() - 0.5) * 100), "Test " + i);
            t.time.set(2021, 7, date);
            mAdapter.addExpense(t);
        }
    }
}