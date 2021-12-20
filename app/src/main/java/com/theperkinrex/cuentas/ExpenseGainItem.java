package com.theperkinrex.cuentas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenseGainItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseGainItem extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PRICE = "price";
    private static final String ARG_NAME = "name";

    // TODO: Rename and change types of parameters
    private int price;
    private String name;

    public ExpenseGainItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param price Expense cost.
     * @param name Expense name.
     * @return A new instance of fragment ExpenseGainItem.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpenseGainItem newInstance(String price, String name) {
        ExpenseGainItem fragment = new ExpenseGainItem();
        Bundle args = new Bundle();
        args.putString(ARG_PRICE, price);
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            price = getArguments().getInt(ARG_PRICE);
            name = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_expense_gain_item, container, false);
        // Inflate the layout for this fragment
        return v;
    }
}