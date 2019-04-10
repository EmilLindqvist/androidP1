package se.mau.ah1429.myeconomy;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Query("SELECT * from item_table ORDER BY item ASC")
    LiveData<List<Item>> getAllItems();

    @Query("SELECT * from item_table WHERE type LIKE 'income'")
    LiveData<List<Item>> getAllIncomeItems();

    @Query("SELECT * from item_table WHERE type LIKE 'expense'")
    LiveData<List<Item>> getAllExpenseItems();

    @Query("SELECT COALESCE(sum(COALESCE(value,0)), 0) From item_table WHERE type LIKE 'income'")
    LiveData<Integer> getTotal();

    @Query("SELECT COALESCE(sum(COALESCE(value,0)), 0) From item_table WHERE type LIKE 'expense'")
    LiveData<Integer> getTotalExpense();



}