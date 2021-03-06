package com.theperkinrex.cuentas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ViewHolder> {
    private List<Expense> expenses;
    private TextView total = null;
    private ImageView graph = null;
    private static ExpensesAdapter self = null;
    private boolean newExpenseAdded;

    private ExpensesAdapter() {
        this.expenses = new ArrayList<Expense>();
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                if (total != null) {
                    total.setText(String.format("%.2f", ((float) getTotal()) / 100f )+ " €");
                }
                if (graph != null) {
//                    Log.i("REDRAW", "REDRAW REQUESTED")
;                    graph.invalidate();
                }
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                onChanged();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                onChanged();
            }
        });
        newExpenseAdded = false;
    }
//    public ExpensesAdapter(List<Expense> expenses) {
//        this.expenses = expenses;
//    }
    public void setTotal(TextView total) {
        this.total = total;
    }
    public void setGraph(ImageView graph) {
        this.graph = graph;
    }

    public static ExpensesAdapter getInstance() {
        if (self == null) {
            self = new ExpensesAdapter();
        }
        return self;
    }

    public void addExpense(Expense e) {
        this.notifyItemInserted(insertExpense(e));
    }

    public void removeExpense(int i) {
        this.expenses.remove(i);
        this.notifyItemRemoved(i);
    }

    public GraphDrawable getGraph(Context ctx) {
        return new GraphDrawable(this.expenses, ctx);
    }

    private int insertExpense(Expense e) {
        int i = 0;
        for(; i < this.expenses.size() && e.time.compareTo(this.expenses.get(i).time) < 0; i++);
//        Log.i("ExpenseAdapter", "Added expense {name: " + e.name +", price: " + e.price + "}");
        expenses.add(i, e);
        return i;
    }

    public void setNewExpenseAdded() {
        newExpenseAdded = true;
    }

    public boolean consumeNewExpenseAdded() {
        boolean r = newExpenseAdded;
        newExpenseAdded = false;
        return r;
    }

    public int getTotal() {
        int tot = 0;
        for (Expense e : expenses) {
            tot += e.price;
        }
        return tot;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_expense_gain_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Log.d("ExpenseAdapter", ">> " + position);

        holder.set(expenses.get(position));
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView priceTextView;
        private final TextView nameTextView;
        private final TextView dateTextView;
        private final ImageView iconView;
        private final Resources res;
        private final View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            res = itemView.getContext().getResources();
            priceTextView = itemView.findViewById(R.id.priceTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            iconView = itemView.findViewById(R.id.iconView);
            view = itemView;
        }

        @SuppressLint("SetTextI18n")
        public void set(Expense e) {
//            ((CardView) view.findViewById(R.id.card)).setCardBackgroundColor(res.getColor(R.color.blackBg, view.getContext().getTheme()));
            nameTextView.setText(e.name);
            dateTextView.setText(DateTimeFormatter.formatTime(res, e.time));
            String prefix = "";
            // TODO Add color & more
            int price = e.price;
            if (e.price > 0) {
                prefix = "+ ";
                iconView.setImageResource(R.drawable.income);
            }else if (e.price < 0) {
                prefix = "- ";
                price = -price;
                iconView.setImageResource(R.drawable.expense);
            }else {

                iconView.setImageResource(R.drawable.stable);
            }

            priceTextView.setText(prefix + String.format("%.2f", ((float) price) / 100f) + " €");

        }


    }
}
