package com.afeka.liadk.battleship;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper implements GameSettingsInterface {

    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "Winners.db";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    private static final String TABLE_EASY = Level.Easy.toString();
    private static final String TABLE_MEDIUM = Level.Medium.toString();
    private static final String TABLE_HARD = Level.Hard.toString();

    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_POINT = "points";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTable(TABLE_EASY));
        sqLiteDatabase.execSQL(createTable(TABLE_MEDIUM));
        sqLiteDatabase.execSQL(createTable(TABLE_HARD));
    }

    private String getLevelString(Level level) {
        switch (level) {
            case Easy:
                return Level.Easy.toString();
            case Medium:
                return Level.Medium.toString();
            case Hard:
                return Level.Hard.toString();
        }
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private String createTable(String level) {
        return "CREATE TABLE " + level + "("
                + KEY_USER_ID + " TEXT PRIMARY KEY,"
                + KEY_USER_NAME + " TEXT,"
                + KEY_USER_POINT + " INTEGER" + ")";
    }

    public void insertWinner(Level level, String name, int point) {
        String choosenLevel = getLevelString(level);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int id = numberOfRows(choosenLevel);
        contentValues.put(KEY_USER_ID, id);
        contentValues.put(KEY_USER_NAME, name);
        contentValues.put(KEY_USER_POINT, point);
        db.insert(choosenLevel, null, contentValues);
    }

    private int numberOfRows(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, table);
    }

    public ArrayList getData(Level level, int numberOfPlayers) {
        String choosenLevel = getLevelString(level);
        SQLiteDatabase db = this.getReadableDatabase();

        String sortOrder = KEY_USER_POINT + " , " + KEY_USER_NAME + " DESC";

        Cursor cursor = db.query(
                choosenLevel,
                new String[]{KEY_USER_NAME, KEY_USER_POINT},
                null,
                null,
                null,
                null,
                sortOrder,
                String.valueOf(numberOfPlayers)
        );
        ArrayList topWinners = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_USER_NAME));
            int point = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_USER_POINT));
            topWinners.add(new String[]{name, String.valueOf(point)});
        }
        cursor.close();
        return topWinners;
    }
}