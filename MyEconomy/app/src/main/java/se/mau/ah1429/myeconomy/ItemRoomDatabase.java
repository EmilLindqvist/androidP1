package se.mau.ah1429.myeconomy;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Item.class}, version = 1)
public abstract class ItemRoomDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();

    private static volatile ItemRoomDatabase INSTANCE;

    static ItemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDatabase.class, "item_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ItemDao mDao;

        PopulateDbAsync(ItemRoomDatabase db) {
            mDao = db.itemDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Item item = new Item("Brudar","income", "övrigt", "3/2/2-2017",123);
            mDao.insert(item);
            item = new Item("Cola","income", "Lön", "25/25/25-2113",123);
            mDao.insert(item);
            return null;
        }
    }
}