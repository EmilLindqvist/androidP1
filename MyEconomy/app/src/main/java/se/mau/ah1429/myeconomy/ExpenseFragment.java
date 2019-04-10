package se.mau.ah1429.myeconomy;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ExpenseFragment extends Fragment {

    ItemViewModel mItemViewModel;
    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        final ItemListAdapter adapter = new ItemListAdapter(getContext(), new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                final View mView = getLayoutInflater().inflate(R.layout.alert_item,null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                ImageView imageView = mView.findViewById(R.id.imageViewCat);

                TextView textViewAlert = mView.findViewById(R.id.textViewAlert);
                textViewAlert.setText("Title: " + item.getItem() +" \nValue: "+  item.getValue() + " \nDate: "+item.getDate());
                switch(item.getCategory()) {
                    case "Other":
                        imageView.setImageResource(R.drawable.ic_other);
                        break;
                    case "Spare time":
                        imageView.setImageResource(R.drawable.ic_hobby);
                        break;
                    case "Living":
                        imageView.setImageResource(R.drawable.ic_home);
                        break;
                    case "Travel":
                        imageView.setImageResource(R.drawable.ic_travel);
                        break;
                    case "Food":
                        imageView.setImageResource(R.drawable.ic_food);
                        break;
                    default:

                }
                imageView.setVisibility(View.VISIBLE);
            }
        });

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        mItemViewModel.getAllExpenseItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable final List<Item> items) {
                // Update the cached copy of the words in the adapter.
                adapter.setItems(items);
            }
        });



        RecyclerView recyclerView = view.findViewById(R.id.expenseview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }
}
