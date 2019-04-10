package se.mau.ah1429.myeconomy;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;

    private LiveData<List<Item>> mAllItems;
    private LiveData<List<Item>> mIncomeItems;
    private LiveData<List<Item>> mExpenseItems;
    private LiveData<Integer> mTotal;
    private LiveData<Integer> mTotalExpense;


    public ItemViewModel (Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
        mIncomeItems = mRepository.getAllIncomeItems();
        mExpenseItems = mRepository.getAllExpenseItems();
        mTotal = mRepository.getTotal();
        mTotalExpense = mRepository.getTotalExpense();
    }

    LiveData<List<Item>> getAllItems() { return mAllItems; }
    LiveData<List<Item>> getAllIncomeItems() { return mIncomeItems; }
    LiveData<List<Item>> getAllExpenseItems() { return mExpenseItems; }
    LiveData<Integer> getTotal() { return mTotal; }
    LiveData<Integer> getTotalExpense() { return mTotalExpense; }


    public void insert(Item item) { mRepository.insert(item); }
}