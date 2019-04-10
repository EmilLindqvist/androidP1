package se.mau.ah1429.myeconomy;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ResultFragment extends Fragment {

    ItemViewModel mItemViewModel;

    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        int expense;
        int income;
        TextView textIncome = view.findViewById(R.id.textViewIncome);
        TextView textExpense = view.findViewById(R.id.textViewExpense);
        TextView textResult = view.findViewById(R.id.textViewResult);
        String totalIncome;

        final ItemListAdapter adapter = new ItemListAdapter(getContext(), new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {

            }
        });
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        //Integer i = mItemViewModel.getTotal();
        //mItemViewModel.getTotal().observe(this, Observer { user -> Log.i("", user.ToString()) };
       // int i = 0;
        //i = mItemViewModel.getmTotals();
        // Create the observer which updates the UI.
        //observerar UI för total income
        final Observer<Integer> totalObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer newTotal) {
                // Update the UI, in this case, a TextView.
                textIncome.setText("Total Income:" + String.valueOf(newTotal));
            }
        };

        final Observer<Integer> totalObserverExp = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer newTotalexp) {
                // Update the UI, in this case, a TextView.
                textExpense.setText("Total Result:" + String.valueOf(newTotalexp));
            }
        };


        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mItemViewModel.getTotal().observe(this, totalObserver);
        mItemViewModel.getTotalExpense().observe(this, totalObserverExp);
        //income = Integer.valueOf(textExpense.getText().toString());
        //expense = Integer.valueOf(textIncome.getText().toString());
        //textResult.setText(String.valueOf(income-expense));
       //mItemViewModel.getAllIncomeItems();
      // mItemViewModel.getAllExpenseItems();

        return view;
    }
}

