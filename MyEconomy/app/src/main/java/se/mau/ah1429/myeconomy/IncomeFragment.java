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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class IncomeFragment extends Fragment {

    ItemViewModel mItemViewModel;

    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        final ItemListAdapter adapter = new ItemListAdapter(getContext(), new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                final View mView = getLayoutInflater().inflate(R.layout.alert_item,null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                ImageView img = mView.findViewById(R.id.imageViewCat);
                img.setVisibility(View.INVISIBLE);

                TextView textViewAlert = mView.findViewById(R.id.textViewAlert);
                textViewAlert.setText("Title: " + item.getItem() +" \nValue: "+  item.getValue() + " \nCategory: "+item.getCategory() + " \nDate: "+item.getDate());
                img.setVisibility(View.INVISIBLE);
            }
        });

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        mItemViewModel.getAllIncomeItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable final List<Item> items) {
                // Update the cached copy of the words in the adapter.
                adapter.setItems(items);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.incomeview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}