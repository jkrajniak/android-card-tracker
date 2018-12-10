package pl.jkrajniak.cardtracker.migrations;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import pl.jkrajniak.cardtracker.model.AppDatabase;

import static org.junit.Assert.assertNotEquals;
import static pl.jkrajniak.cardtracker.model.AppDatabase.MIGRATION_1_2;
import static pl.jkrajniak.cardtracker.model.AppDatabase.MIGRATION_2_3;
import static pl.jkrajniak.cardtracker.model.AppDatabase.MIGRATION_3_4;
import static pl.jkrajniak.cardtracker.model.AppDatabase.MIGRATION_4_5;
import static pl.jkrajniak.cardtracker.model.AppDatabase.MIGRATION_5_6;
import static pl.jkrajniak.cardtracker.model.AppDatabase.MIGRATION_6_7;

@RunWith(AndroidJUnit4.class)
public class DatabaseMigration6To7Test {
    private static final String TEST_DB_NAME = "test-db";
    // Helper for creating Room databases and migrations
    @Rule
    public MigrationTestHelper testHelper =
            new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                    AppDatabase.class.getCanonicalName(),
                    new FrameworkSQLiteOpenHelperFactory());

    @Test
    public void migrationFrom6To7_updateTimestamp() throws IOException {
        SupportSQLiteDatabase db = testHelper.createDatabase(TEST_DB_NAME, 6);

        // insert some valid data
        populateData(db);
        HashMap<Integer, Long> oldTimestamps = getTimestamps(db);
        db.close();

        // Open in version 7 and run migrations.
        db = testHelper.runMigrationsAndValidate(
                TEST_DB_NAME,
                7,
                true,
                MIGRATION_1_2,
                MIGRATION_2_3,
                MIGRATION_3_4,
                MIGRATION_4_5,
                MIGRATION_5_6,
                MIGRATION_6_7);
        HashMap<Integer, Long> newTimestamps = getTimestamps(db);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        for (Integer uid : oldTimestamps.keySet()) {
            Long oldTimestamp = oldTimestamps.get(uid);
            Long newTimestamp = newTimestamps.get(uid);
            assertNotEquals(oldTimestamp, newTimestamp);
            Calendar cNew = Calendar.getInstance();
            cNew.setTimeInMillis(newTimestamp);
            Calendar cOld = Calendar.getInstance();
            cOld.setTimeInMillis(oldTimestamp);
            Log.i("Card", String.format("old = %d (%s) new = %d (%s)",
                    oldTimestamp,
                    format1.format(cOld.getTime()),
                    newTimestamp,
                    format1.format(cNew.getTime())));
        }
    }

    private HashMap<Integer, Long> getTimestamps(SupportSQLiteDatabase db) {
        HashMap<Integer, Long> uidToTimestamp = new HashMap<>();
        Cursor cursor = db.query("SELECT * FROM CardHistory ORDER BY card_id, uid");
        if (cursor != null) {
            int columnUidIndex = cursor.getColumnIndex("uid");
            int columnTimestampIndex = cursor.getColumnIndex("timestamp");
            if (cursor.moveToFirst()) {
                do {
                    int uid = cursor.getInt(columnUidIndex);
                    long timestamp = cursor.getLong(columnTimestampIndex);
                    uidToTimestamp.put(uid, timestamp);
                } while (cursor.moveToNext());
            }
        }
        return uidToTimestamp;
    }

    private void populateData(SupportSQLiteDatabase db) {
        // insert card
        ContentValues value = new ContentValues();
        value.put("uid", 1);
        value.put("name", "card1");
        value.put("requiredNumTransactions", 2);
        value.put("currentNumTransactions", 0);
        value.put("cycleStartsOnDay", 10);
        value.put("updated", false);
        db.insert("Card", SQLiteDatabase.CONFLICT_REPLACE, value);

        value.put("uid", 2);
        value.put("name", "card2");
        value.put("requiredNumTransactions", 2);
        value.put("currentNumTransactions", 0);
        value.put("cycleStartsOnDay", 10);
        value.put("updated", false);
        db.insert("Card", SQLiteDatabase.CONFLICT_REPLACE, value);

        // populate history
        for (int i = 0; i < 3; i++) {
            ContentValues historyValue = new ContentValues();
            historyValue.put("uid", i);
            historyValue.put("card_id", 1);
            historyValue.put("num_transactions", i + 5);
            historyValue.put("timestamp", 0);
            db.insert("CardHistory", SQLiteDatabase.CONFLICT_REPLACE, historyValue);
        }
        for (int i = 3; i < 5; i++) {
            ContentValues historyValue = new ContentValues();
            historyValue.put("uid", i);
            historyValue.put("card_id", 2);
            historyValue.put("num_transactions", i + 5);
            historyValue.put("timestamp", 0);
            db.insert("CardHistory", SQLiteDatabase.CONFLICT_REPLACE, historyValue);
        }
    }
}
