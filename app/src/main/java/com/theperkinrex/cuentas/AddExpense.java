package com.theperkinrex.cuentas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class AddExpense extends AppCompatActivity {
//    private static final String EXPENSE_NAME_EXTRA = "EXPENSE_NAME";
//    private static final String EXPENSE_PRICE_EXTRA = "EXPENSE_PRICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
    }

    public void add(View view) {
        EditText name = findViewById(R.id.ExpenseName);
        EditText quantity = findViewById(R.id.ExpenseQuantity);
        RadioGroup expense_or_earning = findViewById(R.id.EarningOrExpense);
        int radioId = expense_or_earning.getCheckedRadioButtonId();
        if (radioId == -1) {
            return;
        }
        boolean isExpense = radioId == R.id.radioButtonExpense;
        String nameS = name.getText().toString();
        if (nameS.isEmpty()) {
            return;
        }
        String q = quantity.getText().toString();
        if (q.isEmpty() || q.startsWith("-")) {
            return;
        }
        int quantityInt = Integer.parseInt(q);
        if (isExpense) {
            quantityInt = -quantityInt;
        }
//        Intent i = new Intent();
//        i.putExtra(EXPENSE_NAME_EXTRA, nameS);
//        i.putExtra(EXPENSE_PRICE_EXTRA, quantityInt);
//        setResult(RESULT_OK, i);
        Expense e = new Expense(quantityInt, nameS);
        Log.i("NewExpenseActivity", "Trying top add expense {name: " + e.name +", price: " + e.price + "}");

        ExpensesAdapter.getInstance().addExpense(e);
        ExpensesAdapter.getInstance().setNewExpenseAdded();
        finish();
    }

    public void cancel(View v) {
        finish();
    }
}