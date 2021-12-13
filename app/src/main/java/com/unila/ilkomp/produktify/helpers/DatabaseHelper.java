package com.unila.ilkomp.produktify.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="produktify";

    public static final String TABLE_LABEL_NAME ="label";
    public static final String COL_LABEL_ID ="label_id";
    public static final String COL_LABEL_JUDUL ="label_judul";

    public static final String TABLE_AKTIVITAS_NAME ="aktivitas";
    public static final String COL_AKTIVITAS_ID ="aktivitas_id";
    public static final String COL_AKTIVITAS_JUDUL ="aktivitas_judul";
    public static final String COL_AKTIVITAS_KONTEN ="aktivitas_konten";
    public static final String COL_AKTIVITAS_LABEL ="aktivitas_label";
    public static final String COL_AKTIVITAS_DATE ="aktivitas_date";
    public static final String COL_AKTIVITAS_TIME ="aktivitas_time";
    public static final String COL_AKTIVITAS_STATUS ="aktivitas_status";
    public static final String COL_DEFAULT_STATUS="berlangsung";
    public static final String COL_STATUS_SELESAI ="selesai";

    public static final String FORCE_FOREIGN_KEY="PRAGMA foreign_keys=ON";

    private static final String CREATE_LABEL_TABLE ="CREATE TABLE IF NOT EXISTS " + TABLE_LABEL_NAME +"("+
            COL_LABEL_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
            COL_LABEL_JUDUL +" TEXT NOT NULL UNIQUE"+")";

    private static final String CREATE_AKTIVITAS_TABLE ="CREATE TABLE IF NOT EXISTS " + TABLE_AKTIVITAS_NAME +"("+
            COL_AKTIVITAS_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
            COL_AKTIVITAS_JUDUL +" TEXT NOT NULL,"+ COL_AKTIVITAS_KONTEN +" TEXT NOT NULL,"+
            COL_AKTIVITAS_LABEL +" INTEGER NOT NULL,"+ COL_AKTIVITAS_DATE +" TEXT NOT NULL,"+
            COL_AKTIVITAS_TIME +" TEXT NOT NULL,"+ COL_AKTIVITAS_STATUS +" TEXT NOT NULL DEFAULT " + COL_DEFAULT_STATUS+
            ",FOREIGN KEY("+ COL_AKTIVITAS_LABEL +") REFERENCES "+ TABLE_LABEL_NAME +"("+ COL_LABEL_ID +") ON UPDATE CASCADE ON DELETE CASCADE"+")";

    private static final String DROP_LABEL_TABLE ="DROP TABLE IF EXISTS " + TABLE_LABEL_NAME;
    private static final String DROP_AKTIVITAS_TABLE ="DROP TABLE IF EXISTS " + TABLE_AKTIVITAS_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LABEL_TABLE);
        sqLiteDatabase.execSQL(CREATE_AKTIVITAS_TABLE);
        sqLiteDatabase.execSQL(FORCE_FOREIGN_KEY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_LABEL_TABLE);
        sqLiteDatabase.execSQL(DROP_AKTIVITAS_TABLE);
        onCreate(sqLiteDatabase);
    }
}
