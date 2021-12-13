package com.unila.ilkomp.produktify.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unila.ilkomp.produktify.models.AktivitasSelesaiModel;
import com.unila.ilkomp.produktify.models.AktivitasBerlangsungModel;

import java.util.ArrayList;

public class AktivitasDBHelper {
    private Context context;
    private DatabaseHelper databaseHelper;

    public AktivitasDBHelper(Context context){
        this.context=context;
        databaseHelper=new DatabaseHelper(context);
    }

    public boolean tambahAktivitasBaru(AktivitasBerlangsungModel aktivitasBerlangsungModel){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_JUDUL, aktivitasBerlangsungModel.getJudulAktivitas());
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_KONTEN, aktivitasBerlangsungModel.getKontenAktivitas());
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_LABEL, aktivitasBerlangsungModel.getLabelAktivitas());
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_DATE, aktivitasBerlangsungModel.getAktivitasDate());
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_TIME, aktivitasBerlangsungModel.getAktivitasTime());
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_STATUS,DatabaseHelper.COL_DEFAULT_STATUS);
        sqLiteDatabase.insert(DatabaseHelper.TABLE_AKTIVITAS_NAME,null,contentValues);
        sqLiteDatabase.close();
        return true;
    }

    public int hitungAktivitas(){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        String count="SELECT " + DatabaseHelper.COL_AKTIVITAS_ID + " FROM " + DatabaseHelper.TABLE_AKTIVITAS_NAME + " WHERE " + DatabaseHelper.COL_AKTIVITAS_STATUS +"=?";
        Cursor cursor=sqLiteDatabase.rawQuery(count,new String[]{DatabaseHelper.COL_DEFAULT_STATUS});
        return cursor.getCount();
    }

    public int hitungAktivitasSelesai(){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        String count="SELECT " + DatabaseHelper.COL_AKTIVITAS_ID + " FROM " + DatabaseHelper.TABLE_AKTIVITAS_NAME + " WHERE " + DatabaseHelper.COL_AKTIVITAS_STATUS +"=?";
        Cursor cursor=sqLiteDatabase.rawQuery(count,new String[]{DatabaseHelper.COL_STATUS_SELESAI});
        return cursor.getCount();
    }

    public ArrayList<AktivitasBerlangsungModel> fetchSemuaAktivitas(){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        ArrayList<AktivitasBerlangsungModel> aktivitasBerlangsungModels =new ArrayList<>();
        String query="SELECT * FROM " + DatabaseHelper.TABLE_AKTIVITAS_NAME +" INNER JOIN " + DatabaseHelper.TABLE_LABEL_NAME +" ON " + DatabaseHelper.TABLE_AKTIVITAS_NAME +"."+DatabaseHelper.COL_AKTIVITAS_LABEL +"="+
                DatabaseHelper.TABLE_LABEL_NAME +"."+DatabaseHelper.COL_LABEL_ID + " WHERE " + DatabaseHelper.COL_AKTIVITAS_STATUS +"=? ORDER BY " + DatabaseHelper.TABLE_AKTIVITAS_NAME +"."+DatabaseHelper.COL_AKTIVITAS_ID + " ASC";
        Cursor cursor=sqLiteDatabase.rawQuery(query,new String[]{DatabaseHelper.COL_DEFAULT_STATUS});
        while (cursor.moveToNext()){
            AktivitasBerlangsungModel aktivitasBerlangsungModel =new AktivitasBerlangsungModel();
            aktivitasBerlangsungModel.setAktivitasID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_ID)));
            aktivitasBerlangsungModel.setJudulAktivitas(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_JUDUL)));
            aktivitasBerlangsungModel.setKontenAktivitas(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_KONTEN)));
            aktivitasBerlangsungModel.setLabelAktivitas(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LABEL_JUDUL)));
            aktivitasBerlangsungModel.setAktivitasDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_DATE)));
            aktivitasBerlangsungModel.setAktivitasTime(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_TIME)));
            aktivitasBerlangsungModels.add(aktivitasBerlangsungModel);
        }
        cursor.close();
        sqLiteDatabase.close();
        return aktivitasBerlangsungModels;
    }

    public ArrayList<AktivitasSelesaiModel> fetchAktivitasSelesai(){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        ArrayList<AktivitasSelesaiModel> aktivitasSelesaiModels =new ArrayList<>();
        String query="SELECT * FROM " + DatabaseHelper.TABLE_AKTIVITAS_NAME +" INNER JOIN " + DatabaseHelper.TABLE_LABEL_NAME +" ON " + DatabaseHelper.TABLE_AKTIVITAS_NAME +"."+DatabaseHelper.COL_AKTIVITAS_LABEL +"="+
                DatabaseHelper.TABLE_LABEL_NAME +"."+DatabaseHelper.COL_LABEL_ID + " WHERE " + DatabaseHelper.COL_AKTIVITAS_STATUS +"=? ORDER BY " + DatabaseHelper.TABLE_AKTIVITAS_NAME +"."+DatabaseHelper.COL_AKTIVITAS_ID + " DESC";
        Cursor cursor=sqLiteDatabase.rawQuery(query,new String[]{DatabaseHelper.COL_STATUS_SELESAI});
        while (cursor.moveToNext()){
            AktivitasSelesaiModel aktivitasSelesaiModel =new AktivitasSelesaiModel();
            aktivitasSelesaiModel.setAktivitasID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_ID)));
            aktivitasSelesaiModel.setJudulAktivitas(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_JUDUL)));
            aktivitasSelesaiModel.setKontenAktivitas(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_KONTEN)));
            aktivitasSelesaiModel.setLabelAktivitas(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LABEL_JUDUL)));
            aktivitasSelesaiModel.setAktivitasDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_DATE)));
            aktivitasSelesaiModel.setAktivitasTime(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_TIME)));
            aktivitasSelesaiModels.add(aktivitasSelesaiModel);
        }
        cursor.close();
        sqLiteDatabase.close();
        return aktivitasSelesaiModels;
    }

    public boolean updateAktivitas(AktivitasBerlangsungModel aktivitasBerlangsungModel){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_JUDUL, aktivitasBerlangsungModel.getJudulAktivitas());
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_KONTEN, aktivitasBerlangsungModel.getKontenAktivitas());
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_LABEL, aktivitasBerlangsungModel.getLabelAktivitas());
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_DATE, aktivitasBerlangsungModel.getAktivitasDate());
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_TIME, aktivitasBerlangsungModel.getAktivitasTime());
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_STATUS,DatabaseHelper.COL_DEFAULT_STATUS);
        sqLiteDatabase.update(DatabaseHelper.TABLE_AKTIVITAS_NAME,contentValues,DatabaseHelper.COL_AKTIVITAS_ID +"=?",new String[]{String.valueOf(aktivitasBerlangsungModel.getAktivitasID())});
        sqLiteDatabase.close();
        return true;
    }

    public boolean makeSelesai(int aktivitasID){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.COL_AKTIVITAS_STATUS,DatabaseHelper.COL_STATUS_SELESAI);
        sqLiteDatabase.update(DatabaseHelper.TABLE_AKTIVITAS_NAME,contentValues,DatabaseHelper.COL_AKTIVITAS_ID +"=?",
                new String[]{String.valueOf(aktivitasID)});
        sqLiteDatabase.close();
        return true;
    }

    public boolean removeAktivitas(int aktivitasID){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        sqLiteDatabase.delete(DatabaseHelper.TABLE_AKTIVITAS_NAME,DatabaseHelper.COL_AKTIVITAS_ID +"=?",new String[]{String.valueOf(aktivitasID)});
        sqLiteDatabase.close();
        return true;
    }

    public boolean removeAktivitasSelesai(){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        sqLiteDatabase.delete(DatabaseHelper.TABLE_AKTIVITAS_NAME,DatabaseHelper.COL_AKTIVITAS_STATUS +"=?",new String[]{DatabaseHelper.COL_STATUS_SELESAI});
        sqLiteDatabase.close();
        return true;
    }

    public String fetchJudulAktivitas(int aktivitasID){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        String query="SELECT " + DatabaseHelper.COL_AKTIVITAS_JUDUL + " FROM " + DatabaseHelper.TABLE_AKTIVITAS_NAME + " WHERE " + DatabaseHelper.COL_AKTIVITAS_ID +"=?";
        Cursor cursor=sqLiteDatabase.rawQuery(query,new String[]{String.valueOf(aktivitasID)});
        String title="";
        if(cursor.moveToFirst()){
            title=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_JUDUL));
        }
        cursor.close();
        sqLiteDatabase.close();
        return title;
    }

    public String fetchKontenAktivitas(int aktivitasID){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        String query="SELECT " + DatabaseHelper.COL_AKTIVITAS_KONTEN + " FROM " + DatabaseHelper.TABLE_AKTIVITAS_NAME + " WHERE " + DatabaseHelper.COL_AKTIVITAS_ID +"=?";
        Cursor cursor=sqLiteDatabase.rawQuery(query,new String[]{String.valueOf(aktivitasID)});
        String content="";
        if(cursor.moveToFirst()){
            content=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_KONTEN));
        }
        cursor.close();
        sqLiteDatabase.close();
        return content;
    }

    public String fetchAktivitasDate(int aktivitasID){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        String query="SELECT " + DatabaseHelper.COL_AKTIVITAS_DATE + " FROM " + DatabaseHelper.TABLE_AKTIVITAS_NAME + " WHERE " + DatabaseHelper.COL_AKTIVITAS_ID +"=?";
        Cursor cursor=sqLiteDatabase.rawQuery(query,new String[]{String.valueOf(aktivitasID)});
        String date="";
        if(cursor.moveToFirst()){
            date=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_DATE));
        }
        cursor.close();
        sqLiteDatabase.close();
        return date;
    }

    public String fetchAktivitasTime(int aktivitasID){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        String query="SELECT " + DatabaseHelper.COL_AKTIVITAS_TIME + " FROM " + DatabaseHelper.TABLE_AKTIVITAS_NAME + " WHERE " + DatabaseHelper.COL_AKTIVITAS_ID +"=?";
        Cursor cursor=sqLiteDatabase.rawQuery(query,new String[]{String.valueOf(aktivitasID)});
        String time="";
        if(cursor.moveToFirst()){
            time=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_AKTIVITAS_TIME));
        }
        cursor.close();
        sqLiteDatabase.close();
        return time;
    }
}
