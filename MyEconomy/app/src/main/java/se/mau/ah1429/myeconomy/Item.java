package se.mau.ah1429.myeconomy;
import android.arch.persistence.room.ColumnInfo;

import android.arch.persistence.room.Entity;

import android.arch.persistence.room.PrimaryKey;

import android.support.annotation.NonNull;

@Entity(tableName = "item_table")

public class Item {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "item")
    private String mItem;
    private String type;
    private String category;
    private String date;
    private int value;

    public Item(String item, String type, String category, String date, int value) {
        this.mItem = item;
        this.type = type;
        this.category = category;
        this.date = date;
        this.value = value;
    }

    public String getItem(){return this.mItem;}

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public int getValue() {
        return value;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }
}