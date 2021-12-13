package com.unila.ilkomp.produktify.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unila.ilkomp.produktify.models.LabelModel;

import java.util.ArrayList;

public class LabelDBHelper {
    private Context context;
    private DatabaseHelper databaseHelper;

    public LabelDBHelper(Context context){
        this.context=context;
        databaseHelper=new DatabaseHelper(context);
    }

    public boolean tambahLabelBaru(LabelModel labelModel){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.COL_LABEL_JUDUL, labelModel.getJudulLabel());
        sqLiteDatabase.insert(DatabaseHelper.TABLE_LABEL_NAME,null,contentValues);
        sqLiteDatabase.close();
        return true;
    }

    public boolean labelExists(String judulLabel){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        String query="SELECT " + DatabaseHelper.COL_LABEL_JUDUL + " FROM " +
                DatabaseHelper.TABLE_LABEL_NAME + " WHERE " + DatabaseHelper.COL_LABEL_JUDUL +"=?";
        Cursor cursor=sqLiteDatabase.rawQuery(query,new String[]{judulLabel});
        return (cursor.getCount()>0)?true:false;
    }

    public int countLabel(){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        String query="SELECT " + DatabaseHelper.COL_LABEL_ID + " FROM " + DatabaseHelper.TABLE_LABEL_NAME;
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        return cursor.getCount();
    }

    public ArrayList<LabelModel> fetchLabel(){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        ArrayList<LabelModel> labelModels =new ArrayList<>();
        String query="SELECT * FROM " + DatabaseHelper.TABLE_LABEL_NAME + " ORDER BY " + DatabaseHelper.COL_LABEL_ID + " DESC";
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        while (cursor.moveToNext()){
            LabelModel labelModel =new LabelModel();
            labelModel.setLabelID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_LABEL_ID)));
            labelModel.setJudulLabel(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LABEL_JUDUL)));
            labelModels.add(labelModel);
        }
        cursor.close();
        sqLiteDatabase.close();
        return labelModels;
    }

    public boolean hapusLabel(int labelID){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        sqLiteDatabase.execSQL(DatabaseHelper.FORCE_FOREIGN_KEY);
        sqLiteDatabase.delete(DatabaseHelper.TABLE_LABEL_NAME,DatabaseHelper.COL_LABEL_ID +"=?",
                new String[]{String.valueOf(labelID)});
        sqLiteDatabase.close();
        return true;
    }

    public boolean saveLabel(LabelModel labelModel){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.COL_LABEL_JUDUL, labelModel.getJudulLabel());
        sqLiteDatabase.update(DatabaseHelper.TABLE_LABEL_NAME,contentValues,DatabaseHelper.COL_LABEL_ID +"=?",
                new String[]{String.valueOf(labelModel.getLabelID())});
        sqLiteDatabase.close();
        return true;
    }

    public ArrayList<String> fetchLabelStrings(){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        ArrayList<String> labelModels=new ArrayList<>();
        String query="SELECT " + DatabaseHelper.COL_LABEL_JUDUL + " FROM " + DatabaseHelper.TABLE_LABEL_NAME;
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        while (cursor.moveToNext()){
            labelModels.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LABEL_JUDUL)));
        }
        cursor.close();
        sqLiteDatabase.close();
        return labelModels;
    }

    public String fetchJudulLabel(int labelID){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        String fetchTitle="SELECT " + DatabaseHelper.COL_LABEL_JUDUL + " FROM " + DatabaseHelper.TABLE_LABEL_NAME
                + " WHERE " + DatabaseHelper.COL_LABEL_ID +"=?";
        Cursor cursor=sqLiteDatabase.rawQuery(fetchTitle,new String[]{String.valueOf(labelID)});
        String title="";
        if(cursor.moveToFirst()){
            title=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LABEL_JUDUL));
        }
        cursor.close();
        sqLiteDatabase.close();
        return title;
    }

    public int fetchLabelID(String judulLabel){
        SQLiteDatabase sqLiteDatabase=this.databaseHelper.getReadableDatabase();
        String fetchTitle="SELECT " + DatabaseHelper.COL_LABEL_ID + " FROM " + DatabaseHelper.TABLE_LABEL_NAME
                + " WHERE " + DatabaseHelper.COL_LABEL_JUDUL +"=?";
        Cursor cursor=sqLiteDatabase.rawQuery(fetchTitle,new String[]{judulLabel});
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_LABEL_ID));
    }
}
