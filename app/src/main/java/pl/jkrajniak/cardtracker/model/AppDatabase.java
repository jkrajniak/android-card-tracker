package pl.jkrajniak.cardtracker.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

@Database(entities = {Card.class, CardHistory.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoCard daoCard();

    private static volatile AppDatabase INSTANCE;

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // no issue
        }
    };
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Card ADD COLUMN updated INTEGER NOT NULL DEFAULT 0");
        }
    };
    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS CardHistory (uid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, card_id INTEGER NOT NULL, num_transactions INTEGER NOT NULL)");
        }
    };
    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }
    };
    public static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE CardHistory ADD COLUMN timestamp INTEGER NOT NULL DEFAULT 0");
        }
    };
    public static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        private Calendar getCalendar() {
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR, 11);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.DAY_OF_MONTH, 10);
            return today;
        }

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // get the current set of values;
            Cursor cursor = database.query("SELECT * FROM CardHistory ORDER BY card_id, uid DESC");
            int lastCardID = -1;
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            HashMap<Integer, Long> uidToTimestamp = new HashMap<>();
            if (cursor != null) {
                int columnUidIndex = cursor.getColumnIndex("uid");
                int columntCardIdIndex = cursor.getColumnIndex("card_id");
                Calendar today = getCalendar();
                if (cursor.moveToFirst()) {
                    do {
                        int uid = cursor.getInt(columnUidIndex);
                        int cardId = cursor.getInt(columntCardIdIndex);
                        if (cardId != lastCardID) {
                            today = getCalendar();
                            lastCardID = cardId;
                        }
                        today.add(Calendar.MONTH, -1);
                        Log.i("Card", format1.format(today.getTime()));
                        uidToTimestamp.put(uid, today.getTimeInMillis());
                    } while (cursor.moveToNext());
                }
            }
            if (!uidToTimestamp.isEmpty()) {
                for (Integer uid : uidToTimestamp.keySet()) {
                    long newTimestamp = uidToTimestamp.get(uid);
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(newTimestamp);
                    Log.i("Card", String.format("uid %d = %d (%s)", uid, newTimestamp, format1.format(c.getTime())));
                    Log.i("Card", String.format("UPDATE CardHistory SET timestamp = %d WHERE uid = %d", newTimestamp, uid));
                    database.execSQL(String.format("UPDATE CardHistory SET timestamp = %d WHERE uid = %d", newTimestamp, uid));
                }
            }
        }
    };

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "card-db")
                            .addMigrations(
                                    MIGRATION_1_2,
                                    MIGRATION_2_3,
                                    MIGRATION_3_4,
                                    MIGRATION_4_5,
                                    MIGRATION_5_6,
                                    MIGRATION_6_7
                            )
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
