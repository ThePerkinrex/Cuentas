package com.theperkinrex.cuentas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.constraitLayout).setBackgroundColor(getResources().getColor(R.color.white, getTheme()));

        mRecyclerView = findViewById(R.id.expenseGainView);

        mLayoutManager = new LinearLayoutManager(this);

        ExpensesAdapter.getInstance().setTotal((TextView)findViewById(R.id.sumTextView));
        addExampleData();
        Drawable d = ExpensesAdapter.getInstance().getGraph(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(ExpensesAdapter.getInstance());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        ((ImageView)findViewById(R.id.graphView)).setImageDrawable(d);
        ((ImageView)findViewById(R.id.graphView)).refreshDrawableState();
    }

    public void refresh(View view) {
        Log.d("REFRESH", "Refreshing");
        ExpensesAdapter.getInstance().notifyDataSetChanged();
    }



    public void add(View view) {
        Intent intent = new Intent(this, AddExpense.class);
//        intent.putExtra(EXPENSE_ADAPTER_EXTRA, mAdapter);
        startActivity(intent);
    }


    private void addExampleData() {
//        Expense e = new Expense(-100, "Copa");
//        e.time.set(2021, 7, 25);
//        ExpensesAdapter.getInstance().addExpense(e);
//        ExpensesAdapter.getInstance().addExpense(new Expense(1000,"Something"));
//        int date = 25;
//        for (int i = 0; i<50; i++, date++) {
//            Expense t = new Expense((int) ((Math.random() - 0.5) * 10000), "Test " + i);
//            t.time.set(2021, 7, date);
//            ExpensesAdapter.getInstance().addExpense(t);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ExpensesAdapter.getInstance().consumeNewExpenseAdded()) {
            mLayoutManager.scrollToPosition(0);
        }
    }
}