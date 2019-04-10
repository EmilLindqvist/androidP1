package se.mau.ah1429.myeconomy;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ItemRepository {

    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;
    private LiveData<List<Item>> mIncomeItems;
    private LiveData<List<Item>> mExpenseItems;
    private LiveData<Integer> mTotal;
    private LiveData<Integer> mTotalExpense;
    private ExecutorService exService;

    ItemRepository(Application application) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItems();
        mExpenseItems = mItemDao.getAllExpenseItems();
        mIncomeItems = mItemDao.getAllIncomeItems();
        mTotal= mItemDao.getTotal();
        mTotalExpense = mItemDao.getTotalExpense();
        exService = Executors.newSingleThreadExecutor();
    }

    LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }
    LiveData<List<Item>> getAllIncomeItems() {
        return mIncomeItems;
    }
    LiveData<List<Item>> getAllExpenseItems() {
        return mExpenseItems;
    }
    LiveData<Integer> getTotal() {
        return mTotal;
    }
    LiveData<Integer> getTotalExpense() {
        return mTotalExpense;
    }


    public void insert (Item item) {
        exService.execute(()-> {
            mItemDao.insert(item);
        });
    }


}
