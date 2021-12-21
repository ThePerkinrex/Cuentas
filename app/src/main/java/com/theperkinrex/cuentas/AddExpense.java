package com.theperkinrex.cuentas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class AddExpense extends AppCompatActivity {
//    private static final String EXPENSE_NAME_EXTRA = "EXPENSE_NAME";
//    private static final String EXPENSE_PRICE_EXTRA = "EXPENSE_PRICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
    }

    public void switchExpense(View view) {
        SwitchMaterial s = findViewById(R.id.EarningOrExpense);
        if (s.isChecked()) {
            ((TextView) findViewById(R.id.EarningOrExpenseLabelText)).setText(R.string.expense);
            ((ImageView) findViewById(R.id.EarningOrExpenseIcon)).setImageResource(R.drawable.expense);
            s.setTrackTintList(view.getResources().getColorStateList(R.color.expenseTranslucent, view.getContext().getTheme()));
            s.setThumbTintList(view.getResources().getColorStateList(R.color.expense, view.getContext().getTheme()));
        }else {
            ((TextView) findViewById(R.id.EarningOrExpenseLabelText)).setText(R.string.earning);
            ((ImageView) findViewById(R.id.EarningOrExpenseIcon)).setImageResource(R.drawable.income);
            s.setTrackTintList(view.getResources().getColorStateList(R.color.incomeTranslucent, view.getContext().getTheme()));
            s.setThumbTintList(view.getResources().getColorStateList(R.color.income, view.getContext().getTheme()));
        }
    }

    public void add(View view) {
        TextInputEditText name = findViewById(R.id.ExpenseName);
        TextInputEditText quantity = findViewById(R.id.ExpenseQuantity);
//        RadioGroup expense_or_earning = findViewById(R.id.EarningOrExpense);
//        int radioId = expense_or_earning.getCheckedRadioButtonId();
//        if (radioId == -1) {
//            return;
//        }
        boolean isExpense = ((SwitchCompat)findViewById(R.id.EarningOrExpense)).isChecked();//radioId == R.id.radioButtonExpense;
        if (name.getText() == null) {
            return;
        }
        String nameS = name.getText().toString();
        if (nameS.isEmpty()) {
            return;
        }
        if (quantity.getText() == null) {
            return;
        }
        String q = quantity.getText().toString();
        if (q.isEmpty() || q.startsWith("-")) {
            return;
        }
        int quantityInt = (int) (Float.parseFloat(q) * 100);
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